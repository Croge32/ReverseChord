package com.reversechord;

public class Interval {
	private int intervalId;
	private String degrees;
	
	public Interval() {}
	
	public Interval(String d) {
		intervalId = 0;
		degrees = d;
	}
	
	public int getIntervalId() {
		return intervalId;
	}
	
	public void setIntervalId(int id) {
		intervalId = id;
	}
	
	public String getDegrees() {
		return degrees;
	}
	
	public void setDegrees(String d) {
		degrees = d;
	}
}