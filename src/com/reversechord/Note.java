package com.reversechord;

import java.util.HashMap;
import java.util.Map;

public enum Note {
	A(0), ASHARP(1), B(2), C(3), CSHARP(4), D(5), DSHARP(6), E(7), F(8), FSHARP(9), G(10), GSHARP(11);

	private final int num;
	
	private Note(int val) {
		num = val;
	}
	
    private static final Map<Integer, Note> _map = new HashMap<Integer, Note>();
    static
    {
        for (Note note: Note.values())
            _map.put(note.num, note);
    }
 
    public static Note from(int value)
    {
        return _map.get(value);
    }
    
    public static Note fromString(String str) {
    	if (str.equals("E8") || str.equals("A3") ||
				str.equals("A15") || str.equals("D10") ||
				str.equals("G5") || str.equals("B1") ||
				str.equals("B13") || str.equals("e8")) {
				
				return Note.C;
			}
			if (str.equals("E9") || str.equals("A4") || 
					str.equals("D11") ||
					str.equals("G6") || str.equals("B2") ||
					str.equals("B14") || str.equals("e9")) {
					
				return Note.CSHARP;
			}
			if (str.equals("E10") || str.equals("A5") ||
					str.equals("D0") || str.equals("D12") ||
					str.equals("G7") || str.equals("B3") ||
					str.equals("B15") || str.equals("e10")) {
					
				return Note.D;
			}
			if (str.equals("E11") || str.equals("A6") ||
					str.equals("D1") || str.equals("D13") ||
					str.equals("G8") || str.equals("B4") ||
					str.equals("e11")) {
					
				return Note.DSHARP;
			}
			if (str.equals("E12") || str.equals("A7") ||
					str.equals("D2") || str.equals("D14") ||
					str.equals("G9") || str.equals("B5") ||
					str.equals("e12") || str.equals("E0") ||
					str.equals("e0")) {
					
				return Note.E;
			}
			if (str.equals("E13") || str.equals("A8") ||
					str.equals("D3") || str.equals("D15") ||
					str.equals("G10") || str.equals("B6") ||
					str.equals("e13") || str.equals("E1") ||
					str.equals("e1")) {
					
				return Note.F;
			}
			if (str.equals("E14") || str.equals("A9") ||
					str.equals("D4") ||
					str.equals("G11") || str.equals("B7") ||
					str.equals("e14") || str.equals("E2") ||
					str.equals("e2")) {
					
				return Note.FSHARP;
			}
			if (str.equals("E15") || str.equals("A10") ||
					str.equals("D5") || str.equals("G0") ||
					str.equals("G12") || str.equals("B8") ||
					str.equals("e15") || str.equals("E3") ||
					str.equals("e3")) {
					
				return Note.G;
			}
			if (str.equals("A11") ||
					str.equals("D6") || str.equals("G1") ||
					str.equals("G13") || str.equals("B9") ||
					str.equals("E4") ||
					str.equals("e4")) {
					
				return Note.GSHARP;
			}
			if (str.equals("A12") || str.equals("A0") ||
					str.equals("D7") || str.equals("G2") ||
					str.equals("G14") || str.equals("B10") ||
					str.equals("E5") ||
					str.equals("e5")) {
					
				return Note.A;
			}
			if (str.equals("A13") || str.equals("A1") ||
					str.equals("D8") || str.equals("G3") ||
					str.equals("G15") || str.equals("B11") ||
					str.equals("E6") ||
					str.equals("e6")) {
					
				return Note.ASHARP;
			}
			if (str.equals("A14") || str.equals("B0") ||
					str.equals("D9") || str.equals("G4") ||
					str.equals("G16") || str.equals("B12") ||
					str.equals("E7") || str.equals("A2") ||
					str.equals("e7")) {
					
				return Note.B;
			}
			else return Note.C;
    }
    
    public static Note flatten(Note n) {
    	int flatDegree = n.ordinal()-1;
    	if (flatDegree == -1) {
    		flatDegree = 11;
    	}
    	return Note.from(flatDegree);
    }
    
    public static Note sharpen(Note n) {
    	int sharpDegree = n.ordinal()+1;
    	if (sharpDegree == 12) {
    		sharpDegree = 0;
    	}
    	return Note.from(sharpDegree);
    }
    
    @Override
    public String toString() {
    	if (this.equals(Note.A)) {
    		return "A";
    	}
    	if (this.equals(Note.ASHARP)) {
    		return "A#";
    	}
    	if (this.equals(Note.B)) {
    		return "B";
    	}
    	if (this.equals(Note.C)) {
    		return "C";
    	}
    	if (this.equals(Note.CSHARP)) {
    		return "C#";
    	}
    	if (this.equals(Note.D)) {
    		return "D";
    	}
    	if (this.equals(Note.DSHARP)) {
    		return "D#";
    	}
    	if (this.equals(Note.E)) {
    		return "E";
    	}
    	if (this.equals(Note.F)) {
    		return "F";
    	}
    	if (this.equals(Note.FSHARP)) {
    		return "F#";
    	}
    	if (this.equals(Note.G)) {
    		return "G";
    	}
    	if (this.equals(Note.GSHARP)) {
    		return "G#";
    	}
    	else{
    		return "";
    	}
    }
}

	
