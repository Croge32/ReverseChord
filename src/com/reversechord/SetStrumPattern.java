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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetStrumPattern extends Activity {
	
	private float x1,x2;
	private ArrayList<Strum> strums = new ArrayList<Strum>();
	private int midpoint;
	private int count = 0;
	private int increment = 0;
	private int chordInSongId = 0;
	private View save;
	private final CharSequence[] items = {"Whole", "Half", "Quarter", "Eighth"};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_strum);
		
		Intent intent = getIntent();
		chordInSongId = intent.getIntExtra("chordInSongId", 0);
		
		save = (View) findViewById(R.id.save);
		
		save.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
				for (int i=count; i>0; i--) {
					final int index = increment;
					System.out.println("for loop looping");
					AlertDialog.Builder b = new AlertDialog.Builder(SetStrumPattern.this);
					b.setTitle("Set Strum Pattern For Strum "+i);
					b.setItems(items, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.out.println(items[which]+" clicked");
							Strum strum = new Strum();
							switch(which) {
							case 0:
								System.out.println(increment+" increment");
								strum = strums.get(increment);
								strum.duration = Strum.Duration.whole;
								strum.count = increment+1;
								strum.chordInSongId = chordInSongId;
								System.out.println("whole");
								strums.set(increment, strum);
								if (increment <= count) {
									increment++;
								}
								if (increment == count) {
									Intent intent = new Intent();
									setResult(RESULT_OK, intent);
									intent.putExtra("strums", strums);
									for (int i=0; i<strums.size(); i++) {
										System.out.println(strums.get(i).duration+" duration");
										System.out.println(strums.get(i).direction+" direction");
										System.out.println(strums.get(i).chordInSongId+" chordinsongid");
										System.out.println(strums.get(i).count+" count");
									}
									StrumAsync async = new StrumAsync();
									async.execute(strums);
									try {
										if (async.get() == 1) {
											System.out.println("success");
										}
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ExecutionException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									finish();
								}
								break;
							case 1:
								System.out.println(increment+" increment");
								strum = strums.get(increment);
								strum.duration = Strum.Duration.half;
								strum.count = increment+1;
								strum.chordInSongId = chordInSongId;
								System.out.println("half");
								strums.set(increment, strum);
								if (increment <= count) {
									increment++;
								}
								if (increment == count) {
									Intent intent = new Intent();
									setResult(RESULT_OK, intent);
									intent.putExtra("strums", strums);
									for (int i=0; i<strums.size(); i++) {
										System.out.println(strums.get(i).duration+" duration");
										System.out.println(strums.get(i).direction+" direction");
										System.out.println(strums.get(i).chordInSongId+" chordinsongid");
										System.out.println(strums.get(i).count+" count");
									}
									StrumAsync async = new StrumAsync();
									async.execute(strums);
									try {
										if (async.get() == 1) {
											System.out.println("success");
										}
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ExecutionException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									finish();
								}
								break;
							case 2:
								System.out.println(increment+" increment");
								strum = strums.get(increment);
								strum.duration = Strum.Duration.fourth;
								strum.count = increment+1;
								strum.chordInSongId = chordInSongId;
								System.out.println("quarter");
								strums.set(increment, strum);
								if (increment <= count) {
									increment++;
								}
								if (increment == count) {
									Intent intent = new Intent();
									setResult(RESULT_OK, intent);
									intent.putExtra("strums", strums);
									for (int i=0; i<strums.size(); i++) {
										System.out.println(strums.get(i).duration+" duration");
										System.out.println(strums.get(i).direction+" direction");
										System.out.println(strums.get(i).chordInSongId+" chordinsongid");
										System.out.println(strums.get(i).count+" count");
									}
									StrumAsync async = new StrumAsync();
									async.execute(strums);
									try {
										if (async.get() == 1) {
											System.out.println("success");
										}
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ExecutionException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									finish();
								}
								break;
							case 3:
								System.out.println(increment+" increment");
								strum = strums.get(increment);
								strum.duration = Strum.Duration.eighth;
								strum.count = increment+1;
								strum.chordInSongId = chordInSongId;
								System.out.println("eighth");
								strums.set(increment, strum);
								if (increment <= count) {
									increment++;
								}
								if (increment == count) {
									Intent intent = new Intent();
									setResult(RESULT_OK, intent);
									intent.putExtra("strums", strums);
									for (int i=0; i<strums.size(); i++) {
										System.out.println(strums.get(i).duration+" duration");
										System.out.println(strums.get(i).direction+" direction");
										System.out.println(strums.get(i).chordInSongId+" chordinsongid");
										System.out.println(strums.get(i).count+" count");
									}
									StrumAsync async = new StrumAsync();
									async.execute(strums);
									try {
										if (async.get() == 1) {
											System.out.println("success");
										}
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ExecutionException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									finish();
								}
								break;
							}
						}
					});
					b.show();
				}
				//finish();
			}
		});
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		midpoint = width/2;
		
		System.out.println(width+" screen width");
		System.out.println(height+" screen height");
		
	}
	
	@Override
	 public boolean onTouchEvent(MotionEvent event)
	 {     
	     switch(event.getAction())
	     {
	       case MotionEvent.ACTION_DOWN:
	           x1 = event.getX();                         
	       break;         
	       case MotionEvent.ACTION_UP:
	           x2 = event.getX();
	           if (x1 > midpoint && x2 < midpoint) {
	        	   Toast.makeText(this, "swipe up", Toast.LENGTH_SHORT).show();
	        	   Strum strum = new Strum();
	        	   strum.direction = Strum.Direction.up;
	        	   strums.add(strum);
	        	   count++;
	           } else if (x2 > midpoint && x1 < midpoint) {
	        	   Toast.makeText(this, "swipe down", Toast.LENGTH_SHORT).show();
	        	   Strum strum = new Strum();
	        	   strum.direction = Strum.Direction.down;
	        	   strums.add(strum);
	        	   count++;
	           }
	           else {
	        	   Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show ();
	           }                          
	       break;   
	     }           
	     return super.onTouchEvent(event);       
	 }
	
	public class StrumAsync extends AsyncTask<ArrayList<Strum>, Void, Integer> {

		@Override
		protected Integer doInBackground(ArrayList<Strum>... params) {
			
			String url = "http://107.170.115.92:8080/SongService/resources/strum";
			Gson gson = new Gson();
			int code = 0;
			
			for (int i=0; i<params[0].size(); i++) {
			
				try {
					URL obj = new URL(url);
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					
					Strum s = params[0].get(i);
					
					String json = gson.toJson(s, Strum.class);
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
	
						System.out.println(response.toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return code;
		}
	}
	
}
