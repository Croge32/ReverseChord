package com.reversechord;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import android.os.AsyncTask;

public class SyncAsync extends AsyncTask<String, Void, Integer> {
	
	public final static String BASE_URL = "http://107.170.115.92:8080/SongService/resources/";
	public final static String VARIETY = "variety/all";
	public final static String VARANDABBR = "variety/name";

	@Override
	protected Integer doInBackground(String... params) {
		
		String url = BASE_URL+VARIETY;
		System.out.println(url+" url from get");
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
				
				System.out.println(response.toString()+" json");
			
			}
	 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return code;
	}

}
