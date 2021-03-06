package com.reversechord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

import com.google.gson.Gson;

public class SongAsync extends AsyncTask<ServerSong, Void, Integer> {
	
	private final String SONG_URL = "http://107.170.115.92:8080/SongService/resources/song";
	
	@Override
	protected Integer doInBackground(ServerSong... params) {

		String url = SONG_URL;
		System.out.println(url+" url");
		Gson gson = new Gson();
		int code = 0;
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			String json = gson.toJson(params[0], ServerSong.class);
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
	 
				BufferedReader in = new BufferedReader(
												new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		 
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			
				//print result
				System.out.println(response.toString());
			}
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return code;
	}
}