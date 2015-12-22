package com.reversechord;

public class Song {
	
	private int id;
	private String name;
	
	
	public Song() {}
	
	public Song(String nm) {
		name = nm;
	}
	
	public Song(String nm, int num) {
		name = nm;
		id = num;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String nm) {
		name = nm;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int num) {
		id = num;
	}
}
