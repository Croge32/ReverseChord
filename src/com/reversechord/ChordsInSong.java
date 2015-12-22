package com.reversechord;

import com.google.gson.annotations.Expose;

public class ChordsInSong {

	private int id;
	@Expose private int chordInSongId;
	@Expose private int songId;
	@Expose private String variety;
	@Expose private String note;
	@Expose private int order;
	
	public ChordsInSong() {}
	
	public ChordsInSong(int s, String v, String n) {
		songId = s;
		variety = v;
		note = n;
		order = -1;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getChordInSongId() {
		return chordInSongId;
	}
	
	public void setChordInSongId(int id) {
		chordInSongId = id;
	}
	
	public int getSongId() {
		return songId;
	}
	
	public void setSongId(int id) {
		songId = id;
	}
	
	public String getVariety() {
		return variety;
	}
	
	public void setVariety(String v) {
		variety = v;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String n) {
		note = n;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int o) {
		order = o;
	}
}
