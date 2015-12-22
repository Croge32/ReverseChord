package com.reversechord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private View find;
	private View input;
	private View songs;
	private Datasource datasource;
	private User activeUser = new User();
	public final static String BASE_URL = "http://107.170.115.92:8080/SongService/resources/";
	public final static String CREATE_URL = "http://107.170.115.92:8080/SongService/resources/user";
	public final static String AUTH_URL = "http://107.170.115.92:8080/SongService/resources/user/v1";
	//public final static String GET_URL = "http://107.170.115.92:8080/SongService/resources/songLite/user/";
	public final static String GET_URL = "http://107.170.115.92:8080/SongService/resources/song/user/";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		datasource = new Datasource(this);
		
		alertDisplay();
		
		deleteAndSync();
		
		find = (View) findViewById(R.id.searchButton);
		input = (View) findViewById(R.id.guitarButton);
		songs = (View) findViewById(R.id.musicButton);
		
		find.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent findIntent = new Intent(MainActivity.this, ChordFind.class);
				startActivity(findIntent);
			}
		});
		
		input.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent inputIntent = new Intent(MainActivity.this, ReverseFind.class);
				startActivity(inputIntent);
			}
		});
		
		songs.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if (datasource.getActiveUser() == null) {
					Toast.makeText(getBaseContext(),
							"Can't view songs unless logged in!",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent songList = new Intent(MainActivity.this, SongList.class);
					startActivity(songList);					
				}
			}
		});
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		System.out.println("on prepare called");
		System.out.println(activeUser.toString());
		if (datasource.getStatus(activeUser) == 1) {
			System.out.println("status 1");
			menu.getItem(0).setVisible(false);
			menu.getItem(1).setVisible(false);
			menu.getItem(2).setVisible(false);
			menu.getItem(3).setVisible(true);
			menu.getItem(4).setVisible(false);
		} else {
			System.out.println("status 0");
			menu.getItem(0).setVisible(false);
			menu.getItem(1).setVisible(false);
			menu.getItem(2).setVisible(true);
			menu.getItem(3).setVisible(false);
			menu.getItem(4).setVisible(true);
		}
		System.out.println(activeUser.toString());
		return true;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Actions taken for button press
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.logIn:
				datasource.getAllUsersLocal();
				logIn();
				return true;
			case R.id.logOut:
				datasource.getAllUsersLocal();
				System.out.println(datasource.getStatus(activeUser)+" status before?");
				datasource.logOut(activeUser);
				System.out.println(datasource.getStatus(activeUser)+" status after log out?");
				if (datasource.getStatus(activeUser) == 0) {
					Toast.makeText(getBaseContext(), "Log out successful", Toast.LENGTH_SHORT).show();
				}
				datasource.getAllUsersLocal();
				return true;
				
			case R.id.create:
				datasource.getAllUsersLocal();
				create();
				return true;
				
			}
			return false;
		}
	
	public class AuthAsync extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			
			String url = AUTH_URL;
			Gson gson = new Gson();
			int code = 0;
			
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				
				User user = new User(params[0], params[1]);
				
				String json = gson.toJson(user, User.class);
				System.out.println(json+" json");
		 
				// optional default is POST
				con.setRequestMethod("PUT");
				con.setRequestProperty("Accept", "application/json");
				con.setRequestProperty("Content-type", "application/json");
				
	
				// Send post request
				con.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						con.getOutputStream(), "UTF-8");
				wr.write(json);
				wr.flush();
				wr.close();
				
				int responseCode = con.getResponseCode();
				System.out.println("Response Code : " + responseCode);
				
				if (responseCode == 200) {
		 
					code = 1;
					BufferedReader in = new BufferedReader(
													new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
			 
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					
					activeUser = gson.fromJson(response.toString(), User.class);
					System.out.println(activeUser.toString()+" user");
					
					if (!datasource.userExists(activeUser)) {
						datasource.insertUser(activeUser);
					}
					
					System.out.println(datasource.getStatus(activeUser)+" status before?");
					System.out.println(activeUser.getStatus()+" active user status before?");
					datasource.logIn(activeUser);
					System.out.println(datasource.getStatus(activeUser)+" status after log in?");
					System.out.println(activeUser.getStatus()+" active user status after?");
					
					System.out.println(response.toString()+" response");
					
					datasource.getAllUsersLocal();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			invalidateOptionsMenu();
			return code;
		}
	}
	
	public class CreateAsync extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			
			String url = CREATE_URL;
			Gson gson = new Gson();
			int code = 0;
			
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				
				User user = new User(params[0], params[1]);
				
				String json = gson.toJson(user, User.class);
				System.out.println(json+" json");
		 
				// optional default is POST
				con.setRequestMethod("PUT");
				con.setRequestProperty("Accept", "application/json");
				con.setRequestProperty("Content-type", "application/json");
				
	
				// Send post request
				con.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						con.getOutputStream(), "UTF-8");
				wr.write(json);
				wr.flush();
				wr.close();
				
				int responseCode = con.getResponseCode();
				System.out.println("Response Code : " + responseCode);
				
				if (responseCode == 200) {
		 
					code = 1;
					BufferedReader in = new BufferedReader(
													new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
			 
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					
					activeUser = gson.fromJson(response.toString(), User.class);
					System.out.println(activeUser.toString()+" user");
					datasource.insertUser(activeUser);
					
					System.out.println(datasource.getStatus(activeUser)+" status before?");
					System.out.println(activeUser.getStatus()+" active user status before?");
					datasource.logIn(activeUser);
					System.out.println(datasource.getStatus(activeUser)+" status after log in?");
					System.out.println(activeUser.getStatus()+" active user status after?");
				
					//print result
					System.out.println(response.toString());
					datasource.getAllUsersLocal();
				}
		 
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			invalidateOptionsMenu();
			return code;
		}
	}
	
	public class GetAsync extends AsyncTask<User, Void, Integer> {

		@Override
		protected Integer doInBackground(User... params) {
			
			User user = params[0];
			String url = GET_URL+user.getUserId()+"?verbose=true";
			System.out.println(url+" url from get");
			Gson gson = new Gson();
			int code = 0;
			
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 
				// optional default is POST
				con.setRequestMethod("GET");
				
				int responseCode = con.getResponseCode();
				System.out.println("Response Code : " + responseCode);
				
				if (responseCode == 200) {
		 
					code = 1;
					BufferedReader in = new BufferedReader(
													new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
			 
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					
					//print result
					
					response = correctJson(response);
					System.out.println(response.toString()+" json from server");
					
					
					//SongRetriever s = gson.fromJson(response.toString(), SongRetriever.class);
					//datasource.songRetrieverPopulate(s);
					
					ServerSongRetriever s = gson.fromJson(response.toString(), ServerSongRetriever.class);
					for (int i=0; i<s.song.size(); i++) {
						System.out.println(s.song.get(i).getSongId()+" song id index "+i);
						System.out.println(s.song.get(i).getSongName()+" song name index "+i);
					}
					datasource.serverSongRetrieverPopulate(s);
				
				}
		 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			invalidateOptionsMenu();
			return code;
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("ON DESTROY");
		System.out.println(datasource.getStatus(activeUser)+" status before?");
		datasource.logOut(activeUser);
		System.out.println(datasource.getStatus(activeUser)+" status after log out?");
		datasource.getAllUsersLocal();
	}
	
//	@Override
//	protected void onStop() {
//		super.onStop();
//		System.out.println("ON STOP");
//		System.out.println(datasource.getStatus(activeUser)+" status before?");
//		datasource.logOut(activeUser);
//		System.out.println(datasource.getStatus(activeUser)+" status after log out?");
//		datasource.getAllUsersLocal();
//	}
	
	public void alertDisplay() {
		
		int userCount = datasource.getUserCount();
		
		if (userCount == 0) {
			create();
		} else {
			logIn();
		}
	}
	
	public void logIn() {
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Log in");
		LayoutInflater inflater = this.getLayoutInflater();
		View v = inflater.inflate(R.layout.login, null);
		final EditText email = (EditText) v.findViewById(R.id.email);
		final EditText pass = (EditText) v.findViewById(R.id.password);
		b.setView(v);
		b.setPositiveButton("Log In",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		b.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		final AlertDialog alert = b.create();
		alert.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
					Button button = alert
							.getButton(DialogInterface.BUTTON_POSITIVE);
					button.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							System.out.println("on click");
							AuthAsync async = new AuthAsync();
							async.execute(email.getText().toString(), pass.getText().toString());
							try {
								if (async.get() == 0) {
									Toast.makeText(getBaseContext(),
											"Username or password doesn't match, please try again",
											Toast.LENGTH_SHORT).show();
								} else {
									RCOpenHelper rc = new RCOpenHelper(getBaseContext());
									SQLiteDatabase db = rc.getWritableDatabase();
									rc.clearSongsAndChords(db);
									alert.dismiss();
									Toast.makeText(getBaseContext(),
											"You are now logged in as "+activeUser.getEmail(),
											Toast.LENGTH_SHORT).show();
									GetAsync get = new GetAsync();
									get.execute(activeUser);
								}
							} catch(Exception e) {
								e.printStackTrace();
							}
						}						
					});
			}
		});	
		alert.show();
	}
	
	public void create() {
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Welcome!");
		LayoutInflater inflater = this.getLayoutInflater();
		View v = inflater.inflate(R.layout.create_user, null);
		final EditText email = (EditText) v.findViewById(R.id.email);
		final EditText pass = (EditText) v.findViewById(R.id.password);
		final EditText confirmPass = (EditText) v.findViewById(R.id.confirmPassword);
		b.setView(v);
		b.setPositiveButton("Create",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		b.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		final AlertDialog alert = b.create();
		
		alert.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
					Button button = alert
							.getButton(DialogInterface.BUTTON_POSITIVE);
					button.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (!pass.getText().toString().equals(confirmPass.getText().toString())) {
								Toast.makeText(getBaseContext(),
										"Oops! Your password doesn't match! Please re-enter your password",
										Toast.LENGTH_SHORT).show();
							} else {
								CreateAsync async = new CreateAsync();
								async.execute(email.getText().toString(), pass.getText().toString());
								try {
									if (async.get() == 0) {
										Toast.makeText(getBaseContext(),
												"Username or password doesn't match, please try again",
												Toast.LENGTH_SHORT).show();
									} else {
										RCOpenHelper rc = new RCOpenHelper(getBaseContext());
										SQLiteDatabase db = rc.getWritableDatabase();
										rc.clearSongsAndChords(db);
										alert.dismiss();
										Toast.makeText(getBaseContext(),
												"Account created! You are now logged in as "+activeUser.getEmail(),
												Toast.LENGTH_SHORT).show();
										GetAsync get = new GetAsync();
										get.execute(activeUser);
									}
								} catch(Exception e) {
									e.printStackTrace();
								}
							}
						}						
					});
			}
		});		
		alert.show();
	}
	
	public StringBuffer correctJson(StringBuffer buff) {
		StringBuffer result = new StringBuffer();
		int start = 0, loc = 0, end = 0;
		
		System.out.println(buff.toString()+" before");
		System.out.println(buff.length()+" length");
		
		for (int i=0; i<buff.length(); i++) {			
			System.out.println(i+" i");
			System.out.println(start+" start");
			loc = buff.indexOf("g\":{", start);
			System.out.println(loc+" loc");
			if (loc == -1) {
				break;
			}
			end = buff.indexOf("\"}}", start);
			System.out.println(end+" end");
			buff.insert(loc+3, "[");
			buff.insert(end+3, "]");
			start = end+1;
		}
		
		System.out.println(buff.toString()+" after");
		
		result = buff;
		
		return result;
	}

	public class SyncAsync extends AsyncTask<String, Void, Integer> {
		
		public final static String BASE_URL = "http://107.170.115.92:8080/SongService/resources/";
		public final String[] sync = {"note", "abbreviation", "scale", "interval", "variety"};

		@Override
		protected Integer doInBackground(String... params) {
			
			int code = 0;
			for (int i=0; i<sync.length;i++) {
				String url = BASE_URL+sync[i];
				System.out.println(url+" url from get");
				Gson gson = new Gson();
				
				try {
					URL obj = new URL(url);
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			 
					// optional default is POST
					con.setRequestMethod("GET");
					
					int responseCode = con.getResponseCode();
					System.out.println("Response Code : " + responseCode);
					
					if (responseCode == 200) {
			 
						code = 1;
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();
				 
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();
						
						if (sync[i] == "note") {
							NoteArray notes = gson.fromJson(response.toString(), NoteArray.class);
							System.out.println(notes.note.size()+" size of notes list");
							datasource.openWrite();
							for (int j=0; j<notes.note.size(); j++) {
								datasource.insertServerNote(notes.note.get(j));
								System.out.println("inserted");
							}
							datasource.close();
						}
						else if (sync[i] == "abbreviation") {
							AbbrArray abbrs = gson.fromJson(response.toString(), AbbrArray.class);
							datasource.openWrite();
							for (int j=0; j<abbrs.abbreviation.size(); j++) {
								datasource.insertAbbr(abbrs.abbreviation.get(j));
							}
							datasource.close();
						} else if (sync[i] == "scale") {
							ScaArray scas = gson.fromJson(response.toString(), ScaArray.class);
							datasource.openWrite();
							for (int j=0; j<scas.scale.size(); j++) {
								datasource.insertScale(scas.scale.get(i));
							}
							datasource.close();
						} else if (sync[i] == "interval") {
							IntervalArray ints = gson.fromJson(response.toString(), IntervalArray.class);
							datasource.openWrite();
							for (int j=0; j<ints.interval.size(); j++) {
								datasource.insertInterval(ints.interval.get(j));
							}
							datasource.close();
						} else if (sync[i] == "variety") {
							VarietyArray vars = gson.fromJson(response.toString(), VarietyArray.class);
							datasource.openWrite();
							for (int j=0; j<vars.variety.size(); j++) {
								System.out.println(vars.variety.get(j).getType()+" variety name index "+j);
								datasource.insertVariety(vars.variety.get(j));
							}
							datasource.close();
						}
						
						System.out.println(response.toString()+" json");
					
					}
			 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return code;
		}

	}
	
	public void deleteAndSync() {
		RCOpenHelper rc = new RCOpenHelper(this);
		SQLiteDatabase db = rc.getWritableDatabase();
		rc.reset(db);
		
		SyncAsync sync = new SyncAsync();
		sync.execute();
		try {
			int code = sync.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
