package com.reversechord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ChordFind extends Activity {
	
	private ListView noteList;
	private List<String> noteArray;
	private Datasource datasource;
	private String selected;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chord_find);
		
		noteList = (ListView) findViewById(R.id.noteList);
		noteArray = new ArrayList<String>();
		
		datasource = new Datasource(this);
		datasource.openRead();
		noteArray = datasource.notesToArray();
		datasource.close();


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteArray);
		noteList.setAdapter(adapter);
		
		System.out.println(noteArray.size()+" note array size");
		
		for (int i=0; i<noteArray.size(); i++) {
			System.out.println(noteArray.get(i)+" at index "+i);
		}
		
		noteList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) 
			{	
				selected = noteList.getItemAtPosition(position).toString();				
				Intent varIntent = new Intent(ChordFind.this, ChordVariation.class);
				varIntent.putExtra("note", selected);
				startActivity(varIntent);	
			}
		});
		
	}
}
