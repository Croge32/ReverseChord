package com.reversechord;

public class Abbr {
	private int abbreviationId;
	private String abbreviation;
	
	public Abbr() {}
	
	public Abbr(String a) {
		abbreviationId = 0;
		abbreviation = a;
	}
	
	public int getAbbreviationId() {
		return abbreviationId;
	}
	
	public void setAbbreviationId(int id) {
		abbreviationId = id;
	}
	
	public String getName() {
		return abbreviation;
	}
	
	public void setName(String a) {
		abbreviation = a;
	}
}