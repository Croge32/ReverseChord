package com.reversechord;

public class ChordLite {
	public int chordInSongId;
	public int songId;
	public String note;
	public String abbr;
	public int order;
	
	public ChordLite() {}
	
	public ChordLite(int id, String n, String a) {
		songId = id;
		note = n;
		abbr = a;
	}
	
	public void setAbbr(String ab) {
		abbr = ab;
	}
	
	public String getAbbr() {
		return abbr;
	}
	
	public void setNote(String n) {
		note = n;
	}
	
	public String getNote() {
		return abbr;
	}
	
	public void setSongId(int id) {
		songId = id;
	}
	
	public int getSongId() {
		return songId;
	}
}
