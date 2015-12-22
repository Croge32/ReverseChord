package com.reversechord;

public class ServerNote {
	public int noteId;
	public String name;
	
	public ServerNote(String n) {
		name = n;
	}
	
	int getNoteId() {
		return noteId;
	}
	
	void setNoteId(int id) {
		noteId = id;
	}
	
	String getName() {
		return name;
	}
	
	void setName(String n) {
		name = n;
	}
}
