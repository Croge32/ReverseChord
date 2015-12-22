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
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SongList extends Activity {
	
	private Datasource datasource;
	private ListView songs;
	private ArrayList<String> songArray;
	private String selected;
	private ArrayAdapter<String> songAdapter;
	public final static String BASE_URL = "http://107.170.115.92:8080/SongService/resources/";
	public final static String DELETE_SONG = "song/";
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.song_list);
		datasource = new Datasource(this);
		
		songs = (ListView) findViewById(R.id.songs);
		songArray = new ArrayList<String>();
		songs.setFastScrollEnabled(true);
	
		
		/*
		datasource.openRead();
		String[] array = datasource.songDisplay();
		for (int i=0; i<array.length; i++){
			songArray.add(array[i]);
			songAdapter.notifyDataSetChanged();
		}
		datasource.close();
		 */
		
//		datasource.openRead();
//		songArray = datasource.liteDisplay();
//		for (int i=0; i<songArray.size(); i++) {
//			System.out.println(songArray.get(i)+" song array row");
//		}
//		datasource.close();
		
		datasource.openRead();
		songArray = datasource.serverSongDisplay();
		for (int i=0; i<songArray.size(); i++) {
			System.out.println(songArray.get(i)+" song array row");
		}
		datasource.close();
		
		songAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songArray);
		songs.setAdapter(songAdapter);
		
		songs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				System.out.println("long click");
				selected = songs.getItemAtPosition(position).toString();
				AlertDialog.Builder b = new AlertDialog.Builder(SongList.this);
				b.setTitle("Edit Song");
				b.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int songId = datasource.getSongId(selected);
						DeleteSongAsync delete = new DeleteSongAsync();
						delete.execute(songId);
						try {
							if (delete.get() == 1) {
								datasource.openRead();
								songArray = datasource.serverSongDisplay();
								datasource.close();
								songAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, songArray);
								songs.setAdapter(songAdapter);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				AlertDialog alert = b.create();
				alert.show();
				return false;
			}
		});
		
		songs.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) 
			{	
				selected = songs.getItemAtPosition(position).toString();	
				datasource.openRead();
				int songId = datasource.getSongId(selected);
				datasource.close();
				Intent editIntent = new Intent(SongList.this, EditSong.class);
				editIntent.putExtra("song", selected);
				editIntent.putExtra("songId", songId);
				startActivity(editIntent);	
			}
		});
	}
	
	public class DeleteSongAsync extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {
			
			String url = BASE_URL+DELETE_SONG+params[0];
			
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
					
					datasource.removeDeletedSong(params[0]);
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
				AlertDialog.Builder builder = new AlertDialog.Builder(SongList.this);
				builder.setTitle("Save Song");
				builder.setMessage("Name your song!");
				final EditText name = new EditText(builder.getContext());
				
				builder.setView(name);
	
				builder.setPositiveButton("Save Song", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                   
	                	ServerSong s = new ServerSong(datasource.getActiveUser().getUserId(), name.getText().toString());
	                	
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
}
