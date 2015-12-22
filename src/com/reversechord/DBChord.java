package com.reversechord;

public class DBChord {

	private String name;
	private String abbr;
	private String interval;
	private String scale;
	private int songId;
	
	public DBChord() {}
	
	public DBChord (String nm, String ab, String inter, String sc, int id) {
		name = nm;
		abbr = ab;
		interval = inter;
		scale = sc;
		songId = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String nm) {
		name = nm;
	}
	
	public String getAbbr() {
		return abbr;
	}
	
	public void setAbbr(String ab) {
		abbr = ab;
	}
	
	public String getInterval() {
		return interval;
	}
	
	public void setInterval(String inter) {
		interval = inter;
	}
	
	public String getScale() {
		return scale;
	}
	
	public void setScale(String sc) {
		scale = sc;
	}
	
	public int getSongId() {
		return songId;
	}
	
	public void setSongId(int id) {
		songId = id;
	}
	
	public String toString() {
		return name+","+abbr+","+interval+","+scale+","+songId;
	}
}
