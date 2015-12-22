package com.reversechord;

public class Sca {
	private int scaleId;
	private String name;
	
	public Sca() {}
	
	public Sca(String n) {
		scaleId = 0;
		name = n;
	}
	
	public int getScaleId() {
		return scaleId;
	}
	
	public void setScaleId(int id) {
		scaleId = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
}