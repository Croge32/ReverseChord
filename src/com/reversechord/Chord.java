package com.reversechord;

import java.util.ArrayList;

public class Chord {

	private Note first;
	private Note second;
	private Note third;
	private Note fourth;
	private Note fifth;
	private Note sixth;
	
	
	
	public Chord() {}
	
	public Chord(Note first, Note second, Note third, Note fourth, Note fifth, Note sixth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
		this.sixth = sixth;
	}
	
	public Chord(Note first, Note second, Note third, Note fourth, Note fifth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
	}
	
	public Chord(Note first, Note second, Note third, Note fourth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
	}
	
	public Chord(Note first, Note second, Note third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public ArrayList<Note> getNotes() {
		ArrayList<Note> notes = new ArrayList<Note>();
		notes.add(first);
		notes.add(second);
		notes.add(third);
		if (fourth != null) {
			notes.add(fourth);
		}
		if (fifth != null) {
			notes.add(fifth);
		}
		if (sixth != null) {
			notes.add(sixth);
		}
		return notes;
	}
	
	public Note getFirst() {
		return first;
	}
	
	public void setFirst(Note f) {
		first = f;
	}
	
	public Note getSecond() {
		return second;
	}
	
	public void setSecond(Note s) {
		second = s;
	}
	
	public Note getThird() {
		return third;
	}
	
	public void setThird(Note t) {
		third = t;
	}
	
	public Note getFourth() {
		return fourth;
	}
	
	public void setFourth(Note f) {
		fourth = f;
	}
	
	public Note getFifth() {
		return fifth;
	}
	
	public void setFifth(Note f) {
		fifth = f;
	}
	
	public Note getSixth() {
		return sixth;
	}
	
	public void setSixth(Note s) {
		sixth = s;
	}
	
	public static String identify(String c) {
		String name = " variation not found";
		
		if (c.equals("1 3 5 ") || c.equals("1 3 ")) {
			name = " Major";
		}
		if (c.equals("1 3 5 7 ") || c.equals("1 3 7 ")) {
			name = " Major 7th";
		}
		if (c.equals("1 2 3 7 ") || c.equals("1 2 3 5 7 ")) {
			name = " Major 9th";
		}
		if (c.equals("1 3 4 5 ") || c.equals("1 3 4 ")) {
			name = " Major added 4th";
		}
		if (c.equals("1 b3 5 ") || c.equals("1 b3 ")) {
			name = " Minor";
		}
		if (c.equals("1 b3 5 6 ") || c.equals("1 b3 6 ")) {
			name = " Minor 6th";
		}
		if (c.equals("1 b3 4 5 ")) {
			name = " Minor added 4th";
		}
		if (c.equals("1 b3 5 7 ") || c.equals("1 b3 7 ")) {
			name = " Minor/Major 7th";
		}
		if (c.equals("1 b3 5 b7 ") || c.equals("1 b3 b7 ")) {
			name = " Minor 7th";
		}
		if (c.equals("1 2 b3 5 b7 ") || c.equals("1 2 b3 b7 ")) {
			name = " Minor 9th";
		}
		if (c.equals("1 b3 b5 b7 ")) {
			name = " Half-Dimished";
		}
		if (c.equals("1 3 5 b7 ") || c.equals("1 3 b7 ")) {
			name = " Dominant 7th";
		}
		if (c.equals("1 2 3 5 b7 ") || c.equals("1 2 3 b7 ")) {
			name = " Dominant 9th";
		}
		if (c.equals("1 3 5 6 ") || c.equals("1 3 6 ")) {
			name = " Sixth";
		}
		if (c.equals("1 2 5 ") || c.equals("1 2 ")) {
			name = " Suspended 2nd";
		}
		if (c.equals("1 4 5 ") || c.equals("1 4 ")) {
			name = " Suspended 4th";
		}
		if (c.equals("1 4 5 b7 ") || c.equals("1 4 b7 ")) {
			name = " 7th Suspended 4th";
		}
		if (c.equals("1 b3 b5 ")) {
			name = " Diminished";
		}
		if (c.equals("1 b3 b5 6 ")) {
			name = " Diminished 7th";
		}
		if (c.equals("1 3 #5 ")) {
			name = " Augmented";
		}
		if (c.equals("1 3 #5 b7 ")) {
			name = " Augmented 7th";
		}
		if (c.equals("1 3 b5 b7 ")) {
			name = " Seven flat 5";
		}
		if (c.equals("1 5 ")) {
			name = " Fifth (power chord)";
		}
		if (c.equals("1 b5 ") || c.equals("1 3 b5 ")) {
			name = " Flat fifth";
		}
		if (c.equals("1 ")) {
			name = " Octave";
		}
		
		return name;
	}
	
	public static String abbr(String c) {
		String name = "";
		
		if (c.equals(" Major")) {
			name = "maj";
		}
		if (c.equals(" Major 7th")) {
			name = "maj7";
		}
		if (c.equals(" Major 9th")) {
			name = "maj9";
		}
		if (c.equals(" Major added 4th")) {
			name = "majadd4";
		}
		if (c.equals(" Minor")) {
			name = "m";
		}
		if (c.equals(" Minor 6th")) {
			name = "m6";
		}
		if (c.equals(" Minor added 4th")) {
			name = "madd4";
		}
		if (c.equals(" Minor/Major 7th")) {
			name = "m/maj7";
		}
		if (c.equals(" Minor 7th")) {
			name = "m7";
		}
		if (c.equals(" Minor 9th")) {
			name = "m9";
		}
		if (c.equals(" Half-Diminished")) {
			name = "m7b5";
		}
		if (c.equals(" Dominant 7th")) {
			name = "7";
		}
		if (c.equals(" Dominant 9th")) {
			name = "9";
		}
		if (c.equals(" Sixth")) {
			name = "6";
		}
		if (c.equals(" Suspended 2nd")) {
			name = "sus2";
		}
		if (c.equals(" Suspended 4th")) {
			name = "sus4";
		}
		if (c.equals(" 7th Suspended 4th")) {
			name = "7sus4";
		}
		if (c.equals(" Diminished")) {
			name = "dim";
		}
		if (c.equals(" Diminished 7th")) {
			name = "dim7";
		}
		if (c.equals(" Augmented")) {
			name = "aug";
		}
		if (c.equals(" Augmented 7th")) {
			name = "7#5";
		}
		if (c.equals(" Seven flat 5")) {
			name = "7b5";
		}
		if (c.equals(" Fifth (power chord)")) {
			name = "5";
		}
		if (c.equals(" Flat fifth")) {
			name = "-5";
		}
		if (c.equals(" Octave")) {
			name = "";
		}
		
		return name;
	}
	
	public static String getScale(String name) {
		if (name.equals("maj") || name.equals("maj7") || name.equals("maj9")|| name.equals("6") ||
			name.equals("majadd4")) {
			return "Ionian";
		}
		else if (name.equals("7") || name.equals("9")) {
			return "Mixolydian";
		}
		else if (name.equals("sus4") || name.equals("sus2") || name.equals("7sus4")) {
			return "Usually mixolydian";
		}
		else if (name.equals("m") || name.equals("m7") ||
				 name.equals("m6") || name.equals("m9") || name.equals("madd4")) {
			return "Dorian or aeolian";
		}
		else if (name.equals("dim") || name.equals("dim7")) {
			return "Tone/Half-tone";
		}
		else if (name.equals("m7b5")) {
			return "Locrian";
		}
		else if (name.equals("aug") || name.equals("7#5")) {
			return "Whole tone";
		}
		else if (name.equals("m/maj7")) {
			return "Harmonic minor";
		}
		else {
			return "None";
		}
	}
	
	@Override
	public String toString() {
		if (sixth == null && fifth == null && fourth == null){
			return first+" "+second+" "+third;			
		} else if (sixth == null && fifth == null){
			return first+" "+second+" "+third+" "+fourth;			
		} else if (sixth == null) {
			return first+" "+second+" "+third+" "+fourth+" "+fifth;
		} else {
			return first+" "+second+" "+third+" "+fourth+" "+fifth+" "+sixth;
		}
	}
}
