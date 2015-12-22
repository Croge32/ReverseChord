package com.reversechord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reversechord.SongList.DeleteSongAsync;
import com.reversechord.Strum.Duration;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EditSong extends Activity {	
	private Datasource datasource;
	private DynamicListView dragChords;
	private StableArrayAdapter stableAdapter;
	private ListView chords;
	private ArrayList<String> chordArray;
	private ArrayList<ChordsInSong> chordInSongArray;
	private ArrayAdapter<String> editAdapter;
	private View order;
	private View listen;
	private TextView bpmText;
	private String selected;
	private int songId;
	private String song;
	private int chordInSongId = 0;
	private int bpm = 120;
	private MediaPlayer mPlayer = new MediaPlayer();
	private ArrayList<Strum> strums = new ArrayList<Strum>();
	public final static String BASE_URL = "http://107.170.115.92:8080/SongService/resources/chordInSong";
	public final static String STRUM_URL = "http://107.170.115.92:8080/SongService/resources/strum/";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_song);
		datasource = new Datasource(this);
		Intent intent = getIntent();
		songId = intent.getIntExtra("songId", 0);
		song = intent.getStringExtra("song");
		
		dragChords = (DynamicListView) findViewById(R.id.dragChords);
		//chords = (ListView) findViewById(R.id.chords);
		chordArray = new ArrayList<String>();
		chordInSongArray = new ArrayList<ChordsInSong>();
		
		RhythmAsync async = new RhythmAsync();
		async.execute();
		
		order = (View) findViewById(R.id.order);
		listen = (View) findViewById(R.id.listen);
		bpmText = (TextView) findViewById(R.id.bpm);
		
		bpmText.setText("BPM: "+bpm);
		
		datasource.openRead();
		chordArray = datasource.songToArray(songId);
		for (int i=0; i<chordArray.size(); i++) {
			System.out.println(chordArray.get(i)+" song array row");
		}
		datasource.close();
		
		datasource.openRead();
		chordInSongArray = datasource.songToChordArray(songId);
		for (int i=0; i<chordInSongArray.size(); i++) {
			System.out.println(chordInSongArray.get(i).getChordInSongId()+" server id index "+i);
			System.out.println(chordInSongArray.get(i).getNote()+" note index "+i);
			System.out.println(chordInSongArray.get(i).getVariety()+" variety index "+i);
			System.out.println(chordInSongArray.get(i).getOrder()+" order index "+i);
		}
		datasource.close();
		
		//editAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chordArray);
		//chords.setAdapter(editAdapter);
		
		stableAdapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, chordArray);
		dragChords.setChordListChord(chordInSongArray);
		dragChords.setCheeseList(chordArray);
		dragChords.setAdapter(stableAdapter);
		dragChords.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		order.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int flag = 0;
				for (int i=0; i<chordInSongArray.size(); i++) {
					if (chordInSongArray.get(i).getOrder()-1 == i) {
						System.out.println("matches index "+i);
						if (i == chordInSongArray.size()-1) {
							flag = 1;
						}
					} else {
						break;
					}
				}
				if (flag == 1) {
					Toast.makeText(getBaseContext(), "Order isn't changed!", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(), "Order has changed!", Toast.LENGTH_SHORT).show();
					for (int i=0; i<chordInSongArray.size(); i++) {
						chordInSongArray.get(i).setOrder(i+1);
						UpdateOrderAsync update = new UpdateOrderAsync();
						update.execute(chordInSongArray.get(i));
						try {
							if (update.get() == 1) {
								System.out.println("Success");
								datasource.replaceChordInSong(chordInSongArray.get(i));
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		bpmText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder b = new AlertDialog.Builder(EditSong.this);
				b.setTitle("Set BPM");
				final EditText edit = new EditText(EditSong.this);
				b.setView(edit);
				b.setPositiveButton("Set Beats Per Minute", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						bpm = Integer.valueOf(edit.getText().toString());
						bpmText.setText("BPM: "+bpm);
					}
				});
				b.show();
			}
		});
		
		listen.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				playChordsWithRhythm();
			}
		});
		
//		chords.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View v,
//					int position, long id) {
//				System.out.println("long click");
//				final int index = position;
//				AlertDialog.Builder b = new AlertDialog.Builder(EditSong.this);
//				b.setTitle("Edit Chords");
//				b.setPositiveButton("OtherThing", new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				});
//				b.setNeutralButton("Delete", new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				});
//				b.setNegativeButton("Listen", new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				});
//				final AlertDialog alert = b.create();
//				alert.setOnShowListener(new DialogInterface.OnShowListener() {		
//					@Override
//					public void onShow(DialogInterface dialog) {
//						Button pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//						Button neut = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
//						Button neg = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
//						neut.setOnClickListener(new View.OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								int chordInSongId = datasource.getChordInSongId(chordInSongArray.get(index), songId);
//								DeleteSongAsync delete = new DeleteSongAsync();
//								delete.execute(chordInSongId);
//								try {
//									if (delete.get() == 1) {
//										datasource.openRead();
//										chordArray = datasource.songToArray(songId);
//										datasource.close();
//										editAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, chordArray);
//										chords.setAdapter(editAdapter);
//									}
//								} catch (InterruptedException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} catch (ExecutionException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//						});
//						neg.setOnClickListener(new View.OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								String abbr = datasource.getAbbrFromVariety(chordInSongArray.get(index).getVariety());
//								System.out.println(abbr+" abbr");
//								soundForChord(chordInSongArray.get(index).getNote(), abbr);
//							}
//						});
//					}
//				});
//				alert.show();
//				return false;
//			}
//		});
//		
//		chords.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View v, int position,
//					long arg3) 
//			{	
//				//
//			}
//		});
		
		dragChords.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) 
				{	
				System.out.println("long click");
				final int index = position;
				chordInSongId = chordInSongArray.get(position).getChordInSongId();
				AlertDialog.Builder b = new AlertDialog.Builder(EditSong.this);
				b.setTitle("Edit Chords");
				b.setPositiveButton("Set Strum Pattern", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				b.setNeutralButton("Delete", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				b.setNegativeButton("Listen", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				final AlertDialog alert = b.create();
				alert.setOnShowListener(new DialogInterface.OnShowListener() {		
					@Override
					public void onShow(DialogInterface dialog) {
						Button pos = alert.getButton(DialogInterface.BUTTON_POSITIVE);
						Button neut = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
						Button neg = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
						neut.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								//int chordInSongId = datasource.getChordInSongId(chordInSongArray.get(index), songId);
								DeleteSongAsync delete = new DeleteSongAsync();
								//delete.execute(chordInSongId);
								delete.execute(chordInSongArray.get(index));
								try {
									if (delete.get() == 1) {
										datasource.openRead();
										chordArray = datasource.songToArray(songId);
										datasource.close();
										stableAdapter = new StableArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, chordArray);
										dragChords.setAdapter(stableAdapter);
									}
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								alert.dismiss();
							}
						});
						neg.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								String abbr = datasource.getAbbrFromVariety(chordInSongArray.get(index).getVariety());
								System.out.println(abbr+" abbr");
								soundForChord(chordInSongArray.get(index).getNote(), abbr);
								alert.dismiss();
							}
						});
						pos.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent strum = new Intent(EditSong.this, SetStrumPattern.class);
								strum.putExtra("chordInSongId", chordInSongId);
								System.out.println(chordInSongId+" chordinsongid");
								startActivityForResult(strum, 1);
								alert.dismiss();
							}
						});
					}
				});
				alert.show();
			}
		});
	}
	
	public class DeleteSongAsync extends AsyncTask<ChordsInSong, Void, Integer> {

		@Override
		protected Integer doInBackground(ChordsInSong... params) {
			
			String url = BASE_URL+"/"+params[0].getChordInSongId();
			
			Gson gson = new Gson();
			int code = 0;
			
			System.out.println(url+" url from delete");

			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("DELETE");				
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
					
					System.out.println(response.toString()+" response");
					
					datasource.removeDeletedChord(params[0].getChordInSongId());
					//datasource.decrementOrderAfter(params[0]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			invalidateOptionsMenu();
			return code;
		}
	}
	
	public class UpdateOrderAsync extends AsyncTask<ChordsInSong, Void, Integer> {

		@Override
		protected Integer doInBackground(ChordsInSong... params) {
			
			String url = BASE_URL;
			
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			int code = 0;
			
			System.out.println(url+" url from orderUpdate");

			ChordsInSong chord = params[0];			
			String json = gson.toJson(chord, ChordsInSong.class);
			System.out.println(json+" json from orderUpdate");
			
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

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
					
					System.out.println(response.toString()+" response");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			invalidateOptionsMenu();
			return code;
		}
	}
	
	public class RhythmAsync extends AsyncTask<Void, Void, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			
			String url = STRUM_URL+songId;
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
					System.out.println(response.toString()+" json from server");
					
					StrumRetriever retrieve = gson.fromJson(response.toString(), StrumRetriever.class);
					for (int i=0; i<retrieve.strum.size(); i++) {
						strums = (ArrayList) retrieve.strum;
					}
				
				}
		 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return code;
		}
	}
	
	public void soundForChord(String note, String abbr) {
		if (mPlayer.isPlaying()) {
			mPlayer.stop();
		}
		String chord = "";
		if (note.contains("#")) {
			String root = note.substring(0, 1);
			System.out.println(root+" root");
			chord = root.concat("sharp")+abbr;
		} else {
			chord = note+abbr;
		}
		chord = chord.toLowerCase();
		System.out.println(chord+" chord");
		
		int resId = getBaseContext().getResources().getIdentifier(chord, "raw", getBaseContext().getPackageName());
		System.out.println(resId+" resId");
		mPlayer = MediaPlayer.create(getBaseContext(), resId);
		mPlayer.start();
	}
	
	public void playChordsWithRhythm() {
		double bpmDouble = (double) bpm;
		
		System.out.println(bpm+" bpm");
		
		double quarterNoteTime = (60.0/bpmDouble)*1000;
		System.out.println(quarterNoteTime+" quarterNoteTime");
		double wholeNoteTime = quarterNoteTime*4;
		System.out.println(wholeNoteTime+" wholeNoteTime");
		double halfNoteTime = quarterNoteTime*2;
		System.out.println(halfNoteTime+" halfNoteTime");
		double eighthNoteTime = quarterNoteTime/2;
		System.out.println(eighthNoteTime+" eighthNoteTime");
		
		ArrayList<Double> times = new ArrayList<Double>();
		for (int i=0; i<strums.size(); i++) {
			if (strums.get(i).duration == Duration.whole) {
				times.add(wholeNoteTime);
			} else if (strums.get(i).duration == Duration.half) {
				times.add(halfNoteTime);
			} else if (strums.get(i).duration == Duration.fourth) {
				times.add(quarterNoteTime);
			} else if (strums.get(i).duration == Duration.eighth) {
				times.add(eighthNoteTime);
			}
		}
		
		for (int i=0; i<chordInSongArray.size(); i++) {
			System.out.println(chordInSongArray.size()+" size");
			String chord = "";
			String note = chordInSongArray.get(i).getNote();
			String abbr = datasource.getAbbrFromVariety(chordInSongArray.get(i).getVariety());
			if (note.contains("#")) {
				String root = note.substring(0, 1);
				System.out.println(root+" root");
				chord = root.concat("sharp")+abbr;
			} else {
				chord = note+abbr;
			}
			chord = chord.toLowerCase();
			System.out.println(chord+" chord");
			
			int resId = getBaseContext().getResources().getIdentifier(chord, "raw", getBaseContext().getPackageName());
			System.out.println(resId+" resId");
			
			System.out.println("NEW CHORD MPLAYER");
			
			for (int j=0; j<times.size(); j++) {
				mPlayer = MediaPlayer.create(getBaseContext(), resId);
				System.out.println("TIMES ITERATION");
				mPlayer.start();
				try {
					Thread.sleep((int)times.get(j).doubleValue());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mPlayer.stop();
			}
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				strums = (ArrayList<Strum>) data.getSerializableExtra("strums");
				for (int i=0; i<strums.size(); i++) {
					System.out.println(strums.get(i).duration+" duration");
					System.out.println(strums.get(i).direction+" direction");
				}
			}
		}
	}
}
