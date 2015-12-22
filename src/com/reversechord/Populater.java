package com.reversechord;

import java.util.List;

public class Populater {

	private List<Variety> variety;
	
	public Populater() {}
	
	public List<Variety> getVariety() {
		return variety;
	}
	
	public void setType(List<Variety> l) {
		variety = l;
	}
	
	public static class Variety {
		private int varietyId;
		private String type;
		private Abbr abbreviation;
		
		private Variety() {}
		
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
		
		public Abbr getAbbreviation() {
			return abbreviation;
		}
		
		public void setAbbreviation(Abbr a) {
			abbreviation = a;
		}
	}
	
	public String toString() {
		return variety.get(0).getType();
	}
	
	public int getSize() {
		return variety.size();
	}
}