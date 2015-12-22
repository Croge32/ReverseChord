package com.reversechord;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

import com.google.gson.Gson;

import android.R.mipmap;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ChordDisplay extends Activity {
	
	private final String FIND_URL = "http://107.170.115.92:8080/SongService/resources/chord/";
	//private final String IMAGE_URL = "http://107.170.115.92:8080/SongService/resources/image/";
	private final String IMAGE_URL = "http://107.170.115.92:8080/SongService/ChordImages/";
	private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
	private final String SONG_LITE_URL = "http://107.170.115.92:8080/SongService/resources/songLite/";
	//private final String TEST = "{\"image_data\": \"iVBORw0KGgoAAAANSUhEUgAAAGkAAABkCAQAAAAvFdZvAAAAAmJLR0QA/4ePzL8AAAAJb0ZGcwAAACQAAAAQAAzV478AAAAJcEhZcwAACxMAAAsTAQCanBgAAAAJdnBBZwAABFgAAAPdADeQFosAAAS0SURBVHja7ZpdaBxVGIafTTZxQ4iaRGuNCEWhJSbBbhrEgMoiXtQLEYoIai14UVQoCiIKknar5ErxQmkEi5YiwR8siNULfwomVak0tVmWVuNFjRbN2pqamtBsm20yXmx2fjbOzmTPzHw7ZZ6Fw8z59px8757J4XtnBrwnxYdMMsunPEK9D/MHTANDaKbPBP3SKanyGhozPMc6mujmfTTm2CCdlArdLJKn19TzMRofBZtE3NPZtlLHXo6benbSxslgJXnLcTTukU7CW86i0SqdhLcsoHl8KVdBnaeznQOuvbIk/QbcZulp4jHWSYtU4RU0Xrf0PIzGhHRaKnSyyDyd+nkzJ9DYKZ2WGnvRmOZJ1tLCvRxF4zTXSCelRhNfWGq8n01rFlribGWEP5nlK56W39IjIgIjxo8c4AG+5l3W+vIX7iPHHAe4IbjxA+Q5z2l+9UUQ/MQkF7jMD1WWtFWNn2SK6ap/RScKnOIJCkzwQhDj48AtJGimjvM+SZolRoIc89wU1Pjv+Z0s/3LIJ0nPU6DAKd7j0aDGH+Rb4mxmnjt9EvUg0xQs9ySCHR9Rc8T0o7R0Kg68vHpJmnTOrjN1oGTUa32NYLfbLxrF/wgjFb6XAp/iKfpIEAcuk2eMw7bjVy1p1OFqjfkSz9Kp5xCnhbtptdmsV33hyZClhzi08Uepp4Ek42qTSkpKF038eg5ZK50uXgqnpKt4qnjJHWaPNdLADhrDKKm7dF/2dvaVx1rpCqOkTaWVOLMy1khfGCX5hpykYyzYxhYYC6Okk8zYxs6pPDmUk3SJtyj8b6TAmzaRGpcEg5woHVpKgwyvqkwruz30kilbjwJj3KE2qfSOl2Q3OS6ioXGRKQZUBRkrniYlVIlDPWtIAd/wN4u240fd2gujEo851Lr28XrW0AF0cNY2pUrjl/iLHDHOsIp625m0w29gH0+TI88SS+SZYsDz+d3FTag+/zH7nQQ3sost0jen1LaHLD3E+xlnniPFt5888DuSkpb9zn520c5B3in1K/odOUm639nAZ+R5g2Qpouh35CR1W99DaecX40TJ78hJ2mRdiccZNE6U/I6cJAs9tPOJnAoL1W/ix1ggUTy8mWfYYY4p+R1Vql8l3e/08zbPcskcU/I7cpJ0vzPM/VxAM26qK/odOUm637mV2PJnGUW/IynJJ78jK8kXv6OKF37Jnd9R9Vue+iUnP+TO71Tvx6qk1v2Qh36pBv1QGQmamDG3lbeHLD3EuzhCvob8UBl5vmO9ua0kadkPDTNEGx+wv9Qv7IdWMEuHubWXpPuhJMPk2cfGUkTYDzlhL8nih5p5kS+NU1E/5IT99mDyQy1McjUPGbFG+mrsP8qEq+phjuvYxpB0rsqSyp7/fE67cSLqh6qXpPuhCe6ikc2MGjFRP1S9JN0PbWMP/7Cd7aWIsB9awfXWtlL1MMgWknDU2L6LCPuhMpJsZMTcVi6IehmniwZTT4GMvH2wkCFjbZ12vBr0Q0648UvB+KFA/VIwfsgzv2R+H6/Sr5AmJh53ifSzWh+IJIWBSFIYiCSFgUhSGIgkhYFIUhiohffx3MUDfR8vmLhrIr8UBiJJYSCSFAauQEn/Ady7ftrYoxxqAAAAJXRFWHRkYXRlOmNyZWF0ZQAyMDE0LTA0LTA4VDA3OjQ2OjUzKzAwOjAwBR9PBgAAACV0RVh0ZGF0ZTptb2RpZnkAMjAxNC0wNC0wOFQwNzo0Njo1MyswMDowMHRC97oAAAAASUVORK5CYII=\",\"timestamp\": 1396943213942}";
	private String note;
	private String fixedNote;
	private String variation;
	private String namestr;
	private String abbrstr;
	private String intervalstr;
	private String scalestr;
	private User activeUser = new User();
	private TextView name;
	private TextView abbr;
	private TextView interval;
	private TextView scale;
	private View save;
	private View listen;
	private DisplayChord display;
	private DisplayChordOne displayOne;
	private Datasource datasource;
	private MediaPlayer mPlayer = new MediaPlayer();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chord_display);
		datasource = new Datasource(this);
		
		if (datasource.getActiveUser() != null) {
			activeUser = datasource.getActiveUser();
			System.out.println(activeUser.getEmail()+" user");
			invalidateOptionsMenu();
		}
		
		Intent intent = getIntent();
		note = intent.getStringExtra("note");
		variation = intent.getStringExtra("variation");
		
		name = (TextView) findViewById(R.id.name);
		abbr = (TextView) findViewById(R.id.abbr);
		interval = (TextView) findViewById(R.id.intervalVal);
		scale = (TextView) findViewById(R.id.scaleVal);
		save = (View) findViewById(R.id.save);
		listen = (View) findViewById(R.id.listen);
		
		save.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if (datasource.getActiveUser() == null) {
         		   Toast.makeText(getBaseContext(), "Please log in to save chords!", Toast.LENGTH_SHORT).show();
				} else {
					final Dialog d = new Dialog(ChordDisplay.this);
					d.setContentView(R.layout.choose_song);
					d.setTitle("Choose Song");
					
					final ListView list = (ListView) d.findViewById(R.id.songList);
					datasource.openRead();
					//ArrayList<String> songArray = datasource.liteDisplay();
					ArrayList<String> songArray = datasource.serverSongDisplay();
					songArray.add("...New Song...");
					datasource.close();
					
					final ArrayAdapter<String> songAdapter = new ArrayAdapter<String>(getBaseContext(),
							android.R.layout.simple_list_item_1,
							songArray);
					list.setAdapter(songAdapter);
					
					list.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> arg0, View v, int position,
								long arg3) 
						{	
							String name = list.getItemAtPosition(position).toString();
							if (name.equals("...New Song...")) {
								save();
								d.dismiss();
							} else {
								String[] split = name.split(":");
								int songId = datasource.getSongId(split[0]);
								int id;
								if (fixedNote.contains("#")) {
									String root = fixedNote.substring(0, 1);
									root = root+"SHARP";
									id = (com.reversechord.Note.valueOf(root)).ordinal();
								} else {
									id = (com.reversechord.Note.valueOf(fixedNote)).ordinal();
								}
								ServerNote n = new ServerNote(fixedNote);
//								Interval i = new Interval(intervalstr);
//								Sca s = new Sca(scalestr);
//								Abbr a = new Abbr(abbrstr);
//								Variety var = new Variety(namestr, i, s, a);
								//ServerChord c = new ServerChord(n, var);
//								DBChord chord = new DBChord(fixedNote+" "+namestr, fixedNote+abbrstr, intervalstr, scalestr, songId);
//								ChordLite lite = new ChordLite(songId, fixedNote, abbrstr);
								ChordsInSong chordInSong = new ChordsInSong(songId, namestr, fixedNote);
								AddChordAsync async = new AddChordAsync();
								async.execute(chordInSong);
				         		datasource.openWrite();
				             	datasource.insertChordsInSong(chordInSong);
				             	datasource.close();
				             	Toast.makeText(getBaseContext(), "Chord saved successfully", Toast.LENGTH_SHORT).show();
								d.dismiss();
							}
						}
					});
					
					d.show();
				}
			}
		});
		
		listen.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				soundForChord(fixedNote, abbrstr);
			}
		});
		
		try {
			note = URLEncoder.encode(note, "UTF-8");
			variation = URLEncoder.encode(variation, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String chordImgString = note+"/"+note+variation+".png";
		
		FindAsync find = new FindAsync();
		find.execute();
		try {
			if (find.get() == 1) {
				name.setText(fixedNote+" "+namestr);
				abbr.setText(fixedNote+abbrstr);
				interval.setText(intervalstr);
				scale.setText(scalestr);
			} else {
				name.setText(fixedNote+" "+namestr);
				abbr.setText(fixedNote+abbrstr);
				interval.setText(intervalstr);
				scale.setText(scalestr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new DownloadImageTask((ImageView) findViewById(R.id.chordImg))
        .execute(IMAGE_URL+chordImgString);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		datasource.getAllUsersLocal();
		if (activeUser.getStatus() == 1) {
			System.out.println("status 1");
			menu.getItem(0).setVisible(true);
			menu.getItem(1).setVisible(true);
			menu.getItem(2).setVisible(false);
			menu.getItem(3).setVisible(true);
		} else {
			System.out.println("status 0");
			menu.getItem(0).setVisible(true);
			menu.getItem(1).setVisible(true);
			menu.getItem(2).setVisible(true);
			menu.getItem(3).setVisible(false);
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.view:
			Intent songList = new Intent(ChordDisplay.this, SongList.class);
			startActivity(songList);
			System.out.println("pressed");
			return true;

		case R.id.save:
			save();
			return true;
			
		case R.id.logIn:
			datasource.getAllUsersLocal();
			alertDisplay();
			return true;
		case R.id.logOut:
			datasource.getAllUsersLocal();
			System.out.println(datasource.getStatus(activeUser)+" status before?");
			datasource.logOut(activeUser);
			System.out.println(datasource.getStatus(activeUser)+" status after log out?");
			datasource.getAllUsersLocal();
			return true;
		}
		return false;
	}
		
//	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//	    ImageView bmImage;
//
//	    public DownloadImageTask(ImageView bmImage) {
//	        this.bmImage = bmImage;
//	    }
//
//	    protected Bitmap doInBackground(String... urls) {
//	    	Bitmap mIcon11 = null;
//	    	String url = urls[0];
//			System.out.println(url);
//			Gson gson = new Gson();
//			
//			try {
//				URL obj = new URL(url);
//				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//		 
//				// optional default is GET
//				con.setRequestMethod("GET");
//				
//				int responseCode = con.getResponseCode();
//				System.out.println("Response Code : " + responseCode);
//				
//				if (responseCode == 200) {
//					
//		 
//					BufferedReader in = new BufferedReader(
//													new InputStreamReader(con.getInputStream()));
//					String inputLine;
//					StringBuffer response = new StringBuffer();
//			 
//					while ((inputLine = in.readLine()) != null) {
//						response.append(inputLine);
//					}
//					in.close();
//			 
//					//print result
//					System.out.println(response.toString());
//					
//					Image img = gson.fromJson(response.toString(), Image.class);
//					byte[] data = Base64.decode(img.getImageData(), Base64.DEFAULT);
//					mIcon11 = BitmapFactory.decodeByteArray(data, 0, data.length);
//				
//				}
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//	        return mIcon11;
//	    }
//
//	    protected void onPostExecute(Bitmap result) {
//	        bmImage.setImageBitmap(result);
//	    }
//	
//	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
	
	private class FindAsync extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			
			String url = FIND_URL+note+"/"+variation;
			System.out.println(url);
			Gson gson = new Gson();
			int code = 0;
			
			display = new DisplayChord();
			displayOne = new DisplayChordOne();
			
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 
				// optional default is GET
				con.setRequestMethod("GET");
				
				int responseCode = con.getResponseCode();
				System.out.println("Response Code : " + responseCode);
				
				if (responseCode == 200) {
		 
					BufferedReader in = new BufferedReader(
													new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
			 
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					
					System.out.println(response.toString());
					
					if (variation.equals("7b5") || variation.equals("7%235") || variation.equals("m7b5")
						|| variation.equals("dim") || variation.equals("dim7") || variation.equals("aug")) {
							displayOne = gson.fromJson(response.toString(), DisplayChordOne.class);
							fixedNote = displayOne.chord.note.name;
							namestr = displayOne.chord.variety.type;
							abbrstr = displayOne.chord.variety.abbreviation.abbreviation;
							intervalstr = displayOne.chord.variety.interval.degrees;
							scalestr = displayOne.chord.variety.scale.name;
							code = 1;
						} else {
							display = gson.fromJson(response.toString(), DisplayChord.class);
							fixedNote = display.chord.get(0).note.name;
							namestr = display.chord.get(0).variety.getType();
							abbrstr = display.chord.get(0).variety.getAbbreviation().getName();
							intervalstr = display.chord.get(0).variety.getInterval().getDegrees();
							scalestr = display.chord.get(0).variety.getScale().getName();
							code = 2;
						}
					
					//print result
					System.out.println(response.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return code;
		}
	}
	
	public static Bitmap scaleBitmap(Bitmap bitmapToScale, float newWidth, float newHeight) {   
		if(bitmapToScale == null)
		    return null;
		//get the original width and height
		int width = bitmapToScale.getWidth();
		int height = bitmapToScale.getHeight();
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();

		// resize the bit map
		matrix.postScale(newWidth / width, newHeight / height);

		// recreate the new Bitmap and set it back
		return Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix, true);
	}
	
//	public class SongAsync extends AsyncTask<ServerSong, Void, Integer> {
//		
//		@Override
//		protected Integer doInBackground(SongLite... params) {
//
//			String url = SONG_LITE_URL;
//			System.out.println(url+" url");
//			Gson gson = new Gson();
//			int code = 0;
//			
//			try {
//				URL obj = new URL(url);
//				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//				
//				String json = gson.toJson(params[0], SongLite.class);
//				System.out.println(json+" json");
//		 
//				// optional default is POST
//				con.setRequestMethod("POST");
//				con.setRequestProperty("Accept", "application/json");
//				con.setRequestProperty("Content-type", "application/json");
//				
//	
//				// Send post request
//				con.setDoOutput(true);
//				OutputStreamWriter wr = new OutputStreamWriter(
//						con.getOutputStream(), "UTF-8");
//				wr.write(json);
//				wr.flush();
//				wr.close();
//				
//				int responseCode = con.getResponseCode();
//				System.out.println("Response Code : " + responseCode);
//				
//				if (responseCode == 200) {
//		 
//					BufferedReader in = new BufferedReader(
//													new InputStreamReader(con.getInputStream()));
//					String inputLine;
//					StringBuffer response = new StringBuffer();
//			 
//					while ((inputLine = in.readLine()) != null) {
//						response.append(inputLine);
//					}
//					in.close();
//				
//					//print result
//					System.out.println(response.toString());
//				}
//		 
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			return code;
//		}
//	}
	
	public void alertDisplay() {
		
		int userCount = datasource.getUserCount();
		
		if (userCount == 0) {
			AlertDialog.Builder b = new AlertDialog.Builder(this);
			b.setTitle("Welcome!");
			LayoutInflater inflater = this.getLayoutInflater();
			View v = inflater.inflate(R.layout.create_user, null);
			final EditText email = (EditText) v.findViewById(R.id.email);
			final EditText pass = (EditText) v.findViewById(R.id.password);
			final EditText confirmPass = (EditText) v.findViewById(R.id.confirmPassword);
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
											alert.dismiss();
											Toast.makeText(getBaseContext(),
													"Account created! You are now logged in as "+activeUser.getEmail(),
													Toast.LENGTH_SHORT).show();
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
		} else {
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
										alert.dismiss();
										Toast.makeText(getBaseContext(),
												"You are now logged in as "+activeUser.getEmail(),
												Toast.LENGTH_SHORT).show();
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
	}

	public class AuthAsync extends AsyncTask<String, Void, Integer> {
		
		@Override
		protected Integer doInBackground(String... params) {
			
			String url = MainActivity.AUTH_URL;
			Gson gson = new Gson();
			int code = 0;
			
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				
				User user = new User(params[0], params[1]);
				
				String json = gson.toJson(user, User.class);
				System.out.println(json+" json");
		 
				// optional default is POST
				con.setRequestMethod("POST");
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
	
			String url = MainActivity.CREATE_URL;
			Gson gson = new Gson();
			int code = 0;
			
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				
				User user = new User(params[0], params[1]);
				
				String json = gson.toJson(user, User.class);
				System.out.println(json+" json");
		 
				// optional default is POST
				con.setRequestMethod("POST");
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
	
	public void save() {
		if (datasource.getActiveUser() == null) {
  		   Toast.makeText(getBaseContext(), "Please log in to save songs!", Toast.LENGTH_SHORT).show();
  	   	} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(ChordDisplay.this);
				builder.setTitle("Save Song");
				builder.setMessage("Name your song!");
				final EditText name = new EditText(builder.getContext());
				
				builder.setView(name);
	
				builder.setPositiveButton("Save Song", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                   
	                	ServerSong s = new ServerSong(activeUser.getUserId(), name.getText().toString());
	                	
	                	datasource.openWrite();
	             	   	datasource.insertServerSong(s);
	             	   	
	             	   	SongAsync async = new SongAsync();
	             	   	async.execute(s);
	             	   	
	             	   	datasource.close();
	             	   	Toast.makeText(getBaseContext(), "Song saved successfully", Toast.LENGTH_SHORT).show();
	                }
	            });
				builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                }
	            });
				AlertDialog alert = builder.create();
				
				alert.show();
  	   	}
	}
	
	public void soundForChord(String note, String abbr) {
		String root = "";
		String ab = "";
		if (mPlayer.isPlaying()) {
			mPlayer.stop();
		}
		String chord = "";
		if (note.contains("#")) {
			root = note.substring(0, 1);
			System.out.println(root+" root");
			root = root.concat("sharp");
		} else {
			root = note;
		}
		if (abbr.contains("#")) {
			StringBuffer sb = new StringBuffer();
			sb.append(abbr);
			int index = sb.indexOf("#");
			sb.replace(index, index+1, "sharp");
			ab = sb.toString();
			System.out.println(ab+" new abbr");
		} else {
			ab = abbr;
		}
		chord = root+ab;
		chord = chord.toLowerCase();
		System.out.println(chord+" chord");
		
		int resId = getBaseContext().getResources().getIdentifier(chord, "raw", getBaseContext().getPackageName());
		System.out.println(resId+" resId");
		mPlayer = MediaPlayer.create(getBaseContext(), resId);
		mPlayer.start();
	}
}
