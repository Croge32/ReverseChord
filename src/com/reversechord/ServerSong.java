package com.reversechord;

import java.util.List;

public class ServerSong {
	
	private int songId;
	private int userId;
	private String songName;
	private List<ChordsInSong> chordsInSong;
	
	
	public ServerSong() {}
	
	public ServerSong(int id, String name) {
		userId = id;
		songName = name;
	}
	
	public ServerSong(int id, String name, List<ChordsInSong> chords) {
		userId = id;
		songName = name;
		chordsInSong = chords;
	}
	
	public String getSongName() {
		return songName;
	}
	
	public void setSongName(String nm) {
		songName = nm;
	}
	
	public int getSongId() {
		return songId;
	}
	
	public void setSongId(int id) {
		songId = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int id) {
		userId = id;
	}
	
	public List<ChordsInSong> getchordsInSong() {
		return chordsInSong;
	}
	
	public void setChordsInSong(List<ChordsInSong> list) {
		chordsInSong = list;
	}
}
