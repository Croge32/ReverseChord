package com.reversechord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ReverseFind extends Activity {
	
	private String string = "";
	private int fret = 0;
	private int songNum;
	private ChordNick chordNick;
	private String chordNickString;
	private DBChord chordNickNames;
	private String DBChordString;
	private ArrayList<String> pair;
	private View E;
	private View A;
	private View D;
	private View G;
	private View B;
	private View e;
	private View ESelected;
	private View ASelected;
	private View DSelected;
	private View GSelected;
	private View BSelected;
	private View eSelected;
	private View submit;
	private Datasource datasource;
	private User activeUser = new User();
	private String rootString;
	private String abbrStr;
	private Variety schord;
	private MediaPlayer mPlayer = new MediaPlayer();
	
	private String url;
	private final String INTERVAL_URL = "http://107.170.115.92:8080/SongService/resources/variety/interval/";
	private final String SONG_LITE_URL = "http://107.170.115.92:8080/SongService/resources/songLite/";
	
	private int selected = 0;
	private final int FRET0 = 0;
	private final int FRET1 = 260;
	private final int FRET2 = 694;
	private final int FRET3 = 1119;
	private final int FRET4 = 1495;
	private final int FRET5 = 1835;
	private final int FRET6 = 2158;
	private final int FRET7 = 2459;
	private final int FRET8 = 2731;
	private final int FRET9 = 2949;
	private final int FRET10 = 3157;
	private final int FRET11 = 3337;
	private final int FRET12 = 3509;
	private final int FRET13 = 3661;
	private final int FRET14 = 3799;
	private final int FRET15 = 3930;
	private final int E_INITIAL_X_SELECTION = 270;
	private final int A_INITIAL_X_SELECTION = 350;
	private final int D_INITIAL_X_SELECTION = 447;
	private final int G_INITIAL_X_SELECTION = 546;
	private final int B_INITIAL_X_SELECTION = 633;
	private final int e_INITIAL_X_SELECTION = 717;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reverse_find);
		datasource = new Datasource(this);
		
		if (datasource.getActiveUser() != null) {
			activeUser = datasource.getActiveUser();
			System.out.println(activeUser.getEmail()+" user");
			invalidateOptionsMenu();
		}
		
		System.out.println("dp: "+pxToDp(50)+" px: 50");
		System.out.println("px: "+dpToPx(628)+" dp: 50");
		
		pair = new ArrayList<String>();
		System.out.println(Note.flatten(Note.C)+" expect B");
		System.out.println(Note.flatten(Note.GSHARP)+" expect G");
		System.out.println(Note.flatten(Note.A)+" expect GSHARP");
		System.out.println(Note.sharpen(Note.C)+" expect CSHARP");
		System.out.println(Note.sharpen(Note.D)+" expect DSHARP");
		System.out.println(Note.sharpen(Note.GSHARP)+" expect A");
		
		E = (View) findViewById(R.id.E);
		A = (View) findViewById(R.id.A);
		D = (View) findViewById(R.id.D);
		G = (View) findViewById(R.id.G);
		B = (View) findViewById(R.id.B);
		e = (View) findViewById(R.id.e);
		ESelected = (View) findViewById(R.id.ESelected);
		ASelected = (View) findViewById(R.id.ASelected);
		DSelected = (View) findViewById(R.id.DSelected);
		GSelected = (View) findViewById(R.id.GSelected);
		BSelected = (View) findViewById(R.id.BSelected);
		eSelected = (View) findViewById(R.id.eSelected);
		submit = (View) findViewById(R.id.submit);
		
		E.setOnTouchListener(new OnTouchListener() {
			@Override
	        public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (ESelected.isShown() == false) {
						ESelected.setVisibility(View.VISIBLE);
					}
					ESelected.setX(E_INITIAL_X_SELECTION);
					System.out.println("String E");
					System.out.println(event.getY()+" y");
					string = "E";
					getFret(event.getY());
					ESelected.setY(selected);
					System.out.println(ESelected.getX()+" x");
					ESelected.setX(ESelected.getX() - (fret + 5));
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("E")) {
							pair.remove(i);
						}
					}		
					pair.add(string+fret);
					
					for (int i=0; i<pair.size(); i++) {
						System.out.println(pair.get(i)+" in pair index "+i);
					}
				}
				if (pair.size() > 2) {
					submit.setVisibility(View.VISIBLE);
				}
				return true;
	        }
		});		
		A.setOnTouchListener(new OnTouchListener() {
			@Override
	        public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (ASelected.isShown() == false) {
						ASelected.setVisibility(View.VISIBLE);
					}
					ASelected.setX(A_INITIAL_X_SELECTION);
					System.out.println("String A");
					System.out.println(event.getY()+" y");
					string = "A";
					getFret(event.getY());
					ASelected.setY(selected);
					System.out.println(ASelected.getX()+" x");
					ASelected.setX(ASelected.getX() - fret);
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("A")) {
							pair.remove(i);
						}
					}		
					pair.add(string+fret);
					
					for (int i=0; i<pair.size(); i++) {
						System.out.println(pair.get(i)+" in pair index "+i);
					}
				}
				if (pair.size() > 2) {
					submit.setVisibility(View.VISIBLE);
				}
				return true;
	        }
		});		
		D.setOnTouchListener(new OnTouchListener() {
			@Override
	        public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (DSelected.isShown() == false) {
						DSelected.setVisibility(View.VISIBLE);
					}
					DSelected.setX(D_INITIAL_X_SELECTION);
					System.out.println("String D");
					System.out.println(event.getY()+" y");
					string = "D";
					getFret(event.getY());
					DSelected.setY(selected);
					System.out.println(DSelected.getX()+" x");
					if (fret >= 8) {
						DSelected.setX(DSelected.getX() - (fret - 7));
					}
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("D")) {
							pair.remove(i);
						}
					}		
					pair.add(string+fret);
					
					for (int i=0; i<pair.size(); i++) {
						System.out.println(pair.get(i)+" in pair index "+i);
					}
				}
				if (pair.size() > 2) {
					submit.setVisibility(View.VISIBLE);
				}
				return true;
	        }
		});		
		G.setOnTouchListener(new OnTouchListener() {
			@Override
	        public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (GSelected.isShown() == false) {
						GSelected.setVisibility(View.VISIBLE);
					}
					GSelected.setX(G_INITIAL_X_SELECTION);
					System.out.println("String G");
					System.out.println(event.getY()+" y");
					string = "G";
					getFret(event.getY());
					GSelected.setY(selected);
					System.out.println(GSelected.getX()+" x");
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("G")) {
							pair.remove(i);
						}
					}		
					pair.add(string+fret);
					
					for (int i=0; i<pair.size(); i++) {
						System.out.println(pair.get(i)+" in pair index "+i);
					}
				}
				if (pair.size() > 2) {
					submit.setVisibility(View.VISIBLE);
				}
				return true;
	        }
		});		
		B.setOnTouchListener(new OnTouchListener() {
			@Override
	        public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (BSelected.isShown() == false) {
						BSelected.setVisibility(View.VISIBLE);
					}
					BSelected.setX(B_INITIAL_X_SELECTION);
					System.out.println(BSelected.getX()+" x");
					System.out.println("String B");
					System.out.println(event.getY()+" y");
					string = "B";
					getFret(event.getY());
					BSelected.setY(selected);
					if (fret > 7) {
						BSelected.setX(BSelected.getX() + (fret-7));
					}
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("B")) {
							pair.remove(i);
						}
					}		
					pair.add(string+fret);
					
					for (int i=0; i<pair.size(); i++) {
						System.out.println(pair.get(i)+" in pair index "+i);
					}
				}
				if (pair.size() > 2) {
					submit.setVisibility(View.VISIBLE);
				}
				return true;
	        }
		});		
		e.setOnTouchListener(new OnTouchListener() {
			@Override
	        public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (eSelected.isShown() == false) {
						eSelected.setVisibility(View.VISIBLE);
					}
					eSelected.setX(e_INITIAL_X_SELECTION);
					System.out.println(eSelected.getX()+" x");
					System.out.println("String e");
					System.out.println(event.getY()+" y");
					string = "e";
					getFret(event.getY());
					System.out.println(eSelected.getX()+" x");
					eSelected.setY(selected);
					if (fret > 7) {
						eSelected.setX(eSelected.getX() + (fret - 7));
					}
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("e")) {
							pair.remove(i);
						}
					}		
					pair.add(string+fret);
					
					for (int i=0; i<pair.size(); i++) {
						System.out.println(pair.get(i)+" in pair index "+i);
					}
				}
				if (pair.size() > 2) {
					submit.setVisibility(View.VISIBLE);
				}
				return true;
	        }
		});
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
				Note root = getRoot(pair);
				rootString = root.name();
				Chord c = build(pair);
				ArrayList<Note> cList = c.getNotes();
				String notes = c.toString();				
				Scale key = new Scale(getRoot(pair));
				
				ArrayList<Note> keyList = Scale.toList(key);
				ArrayList<Integer> intervals = new ArrayList<Integer>();
				
				for (int i=0; i<keyList.size(); i++) {
					for (int j=0; j<cList.size(); j++) {
						if (cList.get(j) == keyList.get(i)) {
							if (intervals.contains(i+1) == false) {								
								intervals.add(i+1);
							}
						}
					}
				}
				
				String interval = Scale.intervalConvert(intervals);
				String encodedInterval = ""; 
				System.out.println(interval+" interval converted");
				try {
					encodedInterval = URLEncoder.encode(interval, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				System.out.println(encodedInterval+" interval encoded");
				
				FindWithIntervalASYNC async = new FindWithIntervalASYNC();
				System.out.println("executing async");
				async.execute(encodedInterval);
				schord = new Variety();
				try {
					schord = async.get();
				}
				catch(Exception e) {
						
				}
				
				System.out.println(schord.toString()+" chord from outside async");
				
				String name = schord.getType();
				String abbr = schord.getAbbreviation().getName();
				abbrStr = abbr;
				String scale = schord.getScale().getName();
				name = getRoot(pair).toString()+" "+name;
				abbr = getRoot(pair).toString()+abbr;
				
				datasource.openRead();
				songNum = datasource.getSongNumber();
				datasource.close();
				
				final DBChord chord = new DBChord(name, abbr, interval, scale, songNum);
				chordNick = ChordNick.fromArrayList(pair);
				chordNickString = chordNick.toString();
				chordNickNames = new DBChord(name, abbr, interval, scale, songNum);
				DBChordString = chordNickNames.toString();
				
				System.out.println(chordNick.toString()+" chord nick");
				
				System.out.println(scale+" scale");
				
				AlertDialog.Builder builder = new AlertDialog.Builder(ReverseFind.this);
				LayoutInflater inflater = getLayoutInflater();
				View view = inflater.inflate(R.layout.alert, null);
				
				builder.setView(view);
				TextView chordName = (TextView) view.findViewById(R.id.chordName);
				TextView abbrView = (TextView) view.findViewById(R.id.abbr);
				TextView intervalDisplay = (TextView) view.findViewById(R.id.interval);
				TextView notesView = (TextView) view.findViewById(R.id.notes);
				TextView scaleView = (TextView) view.findViewById(R.id.scale);
				
				chordName.setText(name);
				abbrView.setText("("+abbr+")");
				intervalDisplay.setText(interval);
				notesView.setText(notes);
				scaleView.setText(scale);
				
				builder.setTitle("Chord Found!");
				builder.setMessage("Your chord is...");
				builder.setPositiveButton("Save chord", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                   }
	               });
				builder.setNeutralButton("Listen", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                   }
	               });
				builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                   }
	               });
				final AlertDialog alert = builder.create();
				alert.setOnShowListener(new DialogInterface.OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
							Button pos = alert
									.getButton(DialogInterface.BUTTON_POSITIVE);
							Button neut = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
							if (schord.getType().equals("Variation not found")) {
								pos.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
								pos.setEnabled(false);
								neut.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
								neut.setEnabled(false);
							}
							pos.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									if (datasource.getActiveUser() == null) {
				                		   Toast.makeText(getBaseContext(), "Please log in to save chords!", Toast.LENGTH_SHORT).show();
				                	} else {
				                		final Dialog d = new Dialog(ReverseFind.this);
				    					d.setContentView(R.layout.choose_song);
				    					d.setTitle("Choose Song");
				    					
				    					final ListView list = (ListView) d.findViewById(R.id.songList);
				    					datasource.openRead();
				    					//ArrayList<String> songArray = datasource.liteDisplay();
				    					ArrayList<String> songArray = datasource.serverSongDisplay();
				    					songArray.add("...New Song...");
				    					datasource.close();
				    					
				    					ArrayAdapter<String> songAdapter = new ArrayAdapter<String>(getBaseContext(),
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
					    							chord.setSongId(songId);
					    							ChordLite lite = new ChordLite(songId, rootString, abbrStr);
					    							ChordsInSong chord = new ChordsInSong(songId, schord.getType(), rootString);
					    							AddChordAsync async = new AddChordAsync();
					    							async.execute(chord);
					    			         		datasource.openWrite();
					    			             	datasource.insertChordsInSong(chord);
					    			             	datasource.close();
					    			             	Toast.makeText(getBaseContext(), "Chord saved successfully", Toast.LENGTH_SHORT).show();
					    							d.dismiss();
					    							alert.dismiss();
				    							}
				    						}
				    					});
				    					
				    					d.show();
				    				}
				                }				
							});	
							neut.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									soundForChord(rootString, abbrStr);
								}
							});
					}
				});	
				alert.show();
				
				
				pair.clear();
				submit.setVisibility(View.INVISIBLE);
				ESelected.setVisibility(View.INVISIBLE);
				ASelected.setVisibility(View.INVISIBLE);
				DSelected.setVisibility(View.INVISIBLE);
				GSelected.setVisibility(View.INVISIBLE);
				BSelected.setVisibility(View.INVISIBLE);
				eSelected.setVisibility(View.INVISIBLE);
			}
		});
		ESelected.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
				if (v.isShown()) {
					v.setVisibility(View.INVISIBLE);
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("E")) {
							pair.remove(i);
						}
					}
				}
				if (pair.size() < 3) {
					submit.setVisibility(View.INVISIBLE);
				}
			}
		});
		ASelected.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
				if (v.isShown()) {
					v.setVisibility(View.INVISIBLE);
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("A")) {
							pair.remove(i);
						}
					}
				}
				if (pair.size() < 3) {
					submit.setVisibility(View.INVISIBLE);
				}
			}
		});
		DSelected.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
				if (v.isShown()) {
					v.setVisibility(View.INVISIBLE);
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("D")) {
							pair.remove(i);
						}
					}
				}
				if (pair.size() < 3) {
					submit.setVisibility(View.INVISIBLE);
				}
			}
		});
		GSelected.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
				if (v.isShown()) {
					v.setVisibility(View.INVISIBLE);
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("G")) {
							pair.remove(i);
						}
					}
				}
				if (pair.size() < 3) {
					submit.setVisibility(View.INVISIBLE);
				}
			}
		});
		BSelected.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
				if (v.isShown()) {
					v.setVisibility(View.INVISIBLE);
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("B")) {
							pair.remove(i);
						}
					}
				}
				if (pair.size() < 3) {
					submit.setVisibility(View.INVISIBLE);
				}
			}
		});
		eSelected.setOnClickListener(new View.OnClickListener() {
			@Override
	        public void onClick(View v) {
				if (v.isShown()) {
					v.setVisibility(View.INVISIBLE);
					for (int i=0; i<pair.size(); i++) {
						if (pair.get(i).contains("e")) {
							pair.remove(i);
						}
					}
				}
				if (pair.size() < 3) {
					submit.setVisibility(View.INVISIBLE);
				}
			}
		});
	}

	@Override
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

	// Actions taken for button press
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.view:
			Intent songList = new Intent(ReverseFind.this, SongList.class);
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
	
	public Note getRoot(ArrayList<String> list) {
		String[] strings = new String[]{"E", "A", "D", "G", "B", "e"};
		Note root = Note.from(0);
		for (int i=0; i<strings.length; i++) {
			for (int j=0; j<list.size(); j++) {
				if (list.get(j).contains(strings[i])) {
					return Note.fromString(list.get(j));
				}
			}
		}
		System.out.println(root+" root");
		return root;
	}
	
	public Chord build(ArrayList<String> list) {
		Chord c = new Chord();
		ArrayList<Note> notes = new ArrayList<Note>();
		
		for (int i=0; i<list.size(); i++) {
			if (list.get(i).equals("E8") || list.get(i).equals("A3") ||
				list.get(i).equals("A15") || list.get(i).equals("D10") ||
				list.get(i).equals("G5") || list.get(i).equals("B1") ||
				list.get(i).equals("B13") || list.get(i).equals("e8")) {
				
				notes.add(Note.C);
			}
			if (list.get(i).equals("E9") || list.get(i).equals("A4") || 
					list.get(i).equals("D11") ||
					list.get(i).equals("G6") || list.get(i).equals("B2") ||
					list.get(i).equals("B14") || list.get(i).equals("e9")) {
					
					notes.add(Note.CSHARP);
			}
			if (list.get(i).equals("E10") || list.get(i).equals("A5") ||
					list.get(i).equals("D0") || list.get(i).equals("D12") ||
					list.get(i).equals("G7") || list.get(i).equals("B3") ||
					list.get(i).equals("B15") || list.get(i).equals("e10")) {
					
					notes.add(Note.D);
			}
			if (list.get(i).equals("E11") || list.get(i).equals("A6") ||
					list.get(i).equals("D1") || list.get(i).equals("D13") ||
					list.get(i).equals("G8") || list.get(i).equals("B4") ||
					list.get(i).equals("e11")) {
					
					notes.add(Note.DSHARP);
			}
			if (list.get(i).equals("E12") || list.get(i).equals("A7") ||
					list.get(i).equals("D2") || list.get(i).equals("D14") ||
					list.get(i).equals("G9") || list.get(i).equals("B5") ||
					list.get(i).equals("e12") || list.get(i).equals("E0") ||
					list.get(i).equals("e0")) {
					
					notes.add(Note.E);
			}
			if (list.get(i).equals("E13") || list.get(i).equals("A8") ||
					list.get(i).equals("D3") || list.get(i).equals("D15") ||
					list.get(i).equals("G10") || list.get(i).equals("B6") ||
					list.get(i).equals("e13") || list.get(i).equals("E1") ||
					list.get(i).equals("e1")) {
					
					notes.add(Note.F);
			}
			if (list.get(i).equals("E14") || list.get(i).equals("A9") ||
					list.get(i).equals("D4") ||
					list.get(i).equals("G11") || list.get(i).equals("B7") ||
					list.get(i).equals("e14") || list.get(i).equals("E2") ||
					list.get(i).equals("e2")) {
					
					notes.add(Note.FSHARP);
			}
			if (list.get(i).equals("E15") || list.get(i).equals("A10") ||
					list.get(i).equals("D5") || list.get(i).equals("G0") ||
					list.get(i).equals("G12") || list.get(i).equals("B8") ||
					list.get(i).equals("e15") || list.get(i).equals("E3") ||
					list.get(i).equals("e3")) {
					
					notes.add(Note.G);
			}
			if (list.get(i).equals("A11") ||
					list.get(i).equals("D6") || list.get(i).equals("G1") ||
					list.get(i).equals("G13") || list.get(i).equals("B9") ||
					list.get(i).equals("E4") ||
					list.get(i).equals("e4")) {
					
					notes.add(Note.GSHARP);
			}
			if (list.get(i).equals("A12") || list.get(i).equals("A0") ||
					list.get(i).equals("D7") || list.get(i).equals("G2") ||
					list.get(i).equals("G14") || list.get(i).equals("B10") ||
					list.get(i).equals("E5") ||
					list.get(i).equals("e5")) {
					
					notes.add(Note.A);
			}
			if (list.get(i).equals("A13") || list.get(i).equals("A1") ||
					list.get(i).equals("D8") || list.get(i).equals("G3") ||
					list.get(i).equals("G15") || list.get(i).equals("B11") ||
					list.get(i).equals("E6") ||
					list.get(i).equals("e6")) {
					
					notes.add(Note.ASHARP);
			}
			if (list.get(i).equals("A14") || list.get(i).equals("B0") ||
					list.get(i).equals("D9") || list.get(i).equals("G4") ||
					list.get(i).equals("G16") || list.get(i).equals("B12") ||
					list.get(i).equals("E7") || list.get(i).equals("A2") ||
					list.get(i).equals("e7")) {
					
					notes.add(Note.B);
			}
		}
		
		System.out.println(notes.size()+ "size");
		
		if (notes.size() == 3) {
			return new Chord(notes.get(0), notes.get(1), notes.get(2));
		}
		else if (notes.size() == 4) {
			return new Chord(notes.get(0), notes.get(1), notes.get(2), notes.get(3));
		}
		else if (notes.size() == 5) {
			return new Chord(notes.get(0), notes.get(1), notes.get(2), notes.get(3),
					notes.get(4));
		}
		else if (notes.size() == 6) {
			return new Chord(notes.get(0), notes.get(1), notes.get(2), notes.get(3),
					notes.get(4), notes.get(5));
		}
		else return c;
	}
	
	public void getFret(float y) {
		if (y >= 0 && y <= 70) {
			fret = 0;
			selected = FRET0;
		}
		if (y >= 97 && y <= 466) {
			fret = 1;
			selected = FRET1;
		}
		if (y >= 539 && y <= 900) {
			fret = 2;
			selected = FRET2;
		}
		if (y >= 973 && y <= 1308) { 
			fret = 3;
			selected = FRET3;
		}
		if (y >= 1373 && y <= 1674) {
			fret = 4;
			selected = FRET4;
		}
		if (y >= 1738 && y <= 2007) {
			fret = 5;
			selected = FRET5;
		}
		if (y >= 2072 && y <= 2312) { 
			fret = 6;
			selected = FRET6;
		}
		if (y >= 2366 && y <= 2600) {
			fret = 7;
			selected = FRET7;
		}
		if (y >= 2650 && y <= 2850) { 
			fret = 8;
			selected = FRET8;
		}
		if (y >= 2920 && y <= 3049) {
			fret = 9;
			selected = FRET9;
		}
		if (y >= 3129 && y <= 3238) {
			fret = 10;
			selected = FRET10;
		}
		if (y >= 3314 && y <= 3411) { 
			fret = 11;
			selected = FRET11;
		}
		if (y >= 3492 && y <= 3584) { 
			fret = 12;
			selected = FRET12;
		}
		if (y >= 3650 && y <= 3736) { 
			fret = 13;
			selected = FRET13;
		}
		if (y >= 3805 && y <= 3866) { 
			fret = 14;
			selected = FRET14;
		}
		if (y >= 3932 && y <= 3990) { 
			fret = 15;
			selected = FRET15;
		}
	}
	
	private class SocketASYNC extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			
			ChordsClient.doit(params[0], params[1]);
			
			return null;
		}
	}
	
	private class FindWithIntervalASYNC extends AsyncTask<String, Void, Variety> {

		@Override
		protected Variety doInBackground(String... params) {
		
			System.out.println("inside async");
			
			Variety chord = new Variety();
			
			try {
				System.out.println("inside try");
				Gson gson = new Gson();
				url = INTERVAL_URL+params[0];
				System.out.println("opening connection");
				HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
				System.out.println("setting get");
				c.setRequestMethod("GET");
				
				System.out.println("getting response code");
				int responseCode = c.getResponseCode();
				System.out.println("Response Code : " + responseCode);
				
				if (responseCode == 200) {	
					BufferedReader in = new BufferedReader(
							new InputStreamReader(c.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
					
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();
					System.out.println(response.toString());
					
					chord = gson.fromJson(response.toString(), Variety.class);
					System.out.println(chord.toString());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return chord;
		}
	}
	
	public int pxToDp(int px) {
	    DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
	    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	    return dp;
	}
	
	public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.ydpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
	
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
	
//	public class SongAsync extends AsyncTask<SongLite, Void, Integer> {
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
	
	public void save() {
		if (datasource.getActiveUser() == null) {
  		   Toast.makeText(getBaseContext(), "Please log in to save songs!", Toast.LENGTH_SHORT).show();
  	   	} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(ReverseFind.this);
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
}
