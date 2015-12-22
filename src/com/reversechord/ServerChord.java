package com.reversechord;


public class ServerChord {
	public int chordId;
	public ServerNote note;
	public Variety variety;
	
	public ServerChord () {}
	
	public ServerChord(ServerNote n, Variety v) {
		chordId = 0;
		note = n;
		variety = v;
	}
	
	public ServerChord(int id, ServerNote n, Variety v) {
		chordId = id;
		note = n;
		variety = v;
	}
	
	public int getChordId() {
		return chordId;
	}
	
	public void setChordId(int id) {
		chordId = id;
	}
	
	public ServerNote getNote() {
		return note;
	}
	
	public void setNote(ServerNote n) {
		note = n;
	}
	
	public Variety getVariety() {
		return variety;
	}
	
	public void setVariety(Variety v) {
		variety = v;
	}
}