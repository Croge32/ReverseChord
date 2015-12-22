package com.reversechord;

public class DisplayChordOne {

	public Chord chord;
	
	public DisplayChordOne() {}
	
	public static class Chord {
		public int chordId;
		public Note note;
		public Variety variety;
		
		public static class Note {
			public int noteId;
			public String name;
		}
		
		public static class Variety {
			public int varietyId;
			public String type;
			public Interval interval;
			public Sca scale;
			public Abbr abbreviation;
			
			public static class Interval {
				public int intervalId;
				public String degrees;
				
				public Interval() {}
				
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
			
			public static class Sca {
				public int scaleId;
				public String name;
				
				public Sca() {}
				
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
			
			public static class Abbr {
				public int abbreviationId;
				public String abbreviation;
				
				public Abbr() {}
				
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
		}
	}
	
	public String toString() {
		return chord.chordId+"";
	}
}