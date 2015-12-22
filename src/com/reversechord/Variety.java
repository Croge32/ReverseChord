package com.reversechord;

public class Variety {

	private int varietyId;
	private String type;
	private Interval interval;
	private Sca scale;
	private Abbr abbreviation;
	
	private int intervalId;
	private int scaleId;
	private int abbreviationId;
	
	private String intervalName;
	private String scaleName;
	private String abbreviationName;
	
	public Variety() {}
	
	public Variety(String t, Interval i, Sca s, Abbr a) {
		varietyId = 0;
		type = t;
		interval = i;
		scale = s;
		abbreviation = a;
	}
	
	public Variety(int id, String t, Interval i, Sca s, Abbr a) {
		varietyId = id;
		type = t;
		interval = i;
		scale = s;
		abbreviation = a;
	}
	
	public int getVarietyId() {
		return varietyId;
	}
	
	public void setVarietyId(int id) {
		varietyId = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String t) {
		type = t;
	}
	
	public int getIntervalId() {
		return intervalId;
	}
	
	public void setIntervalId(int id) {
		intervalId = id;
	}
	
	public Interval getInterval() {
		return interval;
	}
	
	public void setInterval(Interval in) {
		interval = in;
	}
	
	public String getIntervalName() {
		return intervalName;
	}
	
	public void setIntervalName(String n) {
		intervalName = n;
	}
	
	public int getScaleId() {
		return scaleId;
	}
	
	public void setScaleId(int id) {
		scaleId = id;
	}
	
	public Sca getScale() {
		return scale;
	}
	
	public void setScale(Sca s) {
		scale = s;
	}
	
	public String getScaleName() {
		return scaleName;
	}
	
	public void setScaleName(String n) {
		scaleName = n;
	}
	
	public int getAbbreviationId() {
		return abbreviationId;
	}
	
	public void setAbbreviationId(int id) {
		abbreviationId = id;
	}
	
	public Abbr getAbbreviation() {
		return abbreviation;
	}
	
	public void setAbbreviation(Abbr a) {
		abbreviation = a;
	}
	
	public String getAbbreviationName() {
		return abbreviationName;
	}
	
	public void setAbbreviationName(String n) {
		abbreviationName = n;
	}
	
	public String toString() {
		return type + " " + interval.getDegrees() + " " + scale.getName() + " " + abbreviation.getName();
	}
}
