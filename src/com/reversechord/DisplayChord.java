package com.reversechord;

import java.util.List;

public class DisplayChord {

	public List<ServerChord> chord;
	
	public DisplayChord() {}
	
	public String toString() {
		return chord.get(0).getChordId()+"";
	}
}
