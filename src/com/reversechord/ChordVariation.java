package com.reversechord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ChordVariation extends Activity {

	private ListView variationList;
	private List<String> varArray;
	private Datasource datasource;
	private String selected;
	private String variation;
	private String chordNote;
	private String chord;
	private String chordImgString;
	private final String RETRIEVE_URL = "http://107.170.115.92:8080/SongService/resources/variety/?field=name";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chord_variation);
		Datasource datasource = new Datasource(this);
		
		variationList = (ListView) findViewById(R.id.variationList);
		varArray = new ArrayList<String>();

		Intent intent = getIntent();
		chordNote = intent.getStringExtra("note");
		System.out.println(chordNote+" chord note");
		
		varArray = datasource.varietyArray();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, varArray);
		variationList.setAdapter(adapter);
		
		System.out.println(varArray.size()+" note array size");
		
		for (int i=0; i<varArray.size(); i++) {
			System.out.println(varArray.get(i)+" at index "+i);
		}
		
		variationList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) 
			{	
				selected = variationList.getItemAtPosition(position).toString();
				String[] sel = selected.split("\\(|\\)");
				variation = sel[1];
				Intent varIntent = new Intent(ChordVariation.this, ChordDisplay.class);
				varIntent.putExtra("note", chordNote);
				varIntent.putExtra("variation", variation);
				startActivity(varIntent);
			}
		});
		
	}
	
//	private class RetrieveAsync extends AsyncTask<Void, Void, ArrayList<String>> {
//
//		@Override
//		protected ArrayList<String> doInBackground(Void... params) {
//			
//			ArrayList<String> vars = new ArrayList<String>();
//			
//			String url = RETRIEVE_URL;
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
//					System.out.println(response.toString());
//
//					Populater chord = gson.fromJson(response.toString(), Populater.class);
//					System.out.println(chord.toString());
//					System.out.println(chord.getSize()+" size");
//					
//					for (int i=0; i<chord.getSize(); i++) {
//						String line = chord.getVariety().get(i).getType()+
//								" ("+chord.getVariety().get(i).getAbbreviation().getName()+")";
//						vars.add(line);
//					}
//			 
//					//print result
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			return vars;
//		}
//	}
}
