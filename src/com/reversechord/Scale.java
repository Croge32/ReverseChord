package com.reversechord;

import java.util.ArrayList;

public class Scale {
	
	private static Note root;
	private static Note minorSecond;
	private static Note majorSecond;
	private static Note minorThird;
	private static Note majorThird;
	private static Note perfectFourth;
	private static Note diminishedFifth;
	private static Note perfectFifth;
	private static Note minorSixth;
	private static Note majorSixth;
	private static Note minorSeventh;
	private static Note majorSeventh;
	
	public Scale () {}
	
	public Scale (Note r) {
		int degree = r.ordinal();	
		
		root = r;
		degree++;
		if (degree == 12) degree = 0;
		
		minorSecond = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		majorSecond = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		minorThird = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		majorThird = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		perfectFourth = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		diminishedFifth = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		perfectFifth = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		minorSixth = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		majorSixth = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		minorSeventh = Note.from(degree);
		degree++;
		if (degree == 12) degree = 0;
		
		majorSeventh = Note.from(degree);
	}
	
	public static ArrayList<Note> toList(Scale s) {
		ArrayList<Note> scale = new ArrayList<Note>();
		scale.add(s.root);
		scale.add(s.minorSecond);
		scale.add(s.majorSecond);
		scale.add(s.minorThird);
		scale.add(s.majorThird);
		scale.add(s.perfectFourth);
		scale.add(s.diminishedFifth);
		scale.add(s.perfectFifth);
		scale.add(s.minorSixth);
		scale.add(s.majorSixth);
		scale.add(s.minorSeventh);
		scale.add(s.majorSeventh);
		return scale;
	}
	
	public static String intervalConvert(ArrayList<Integer> interval) {
		String newInt = "";
		for (int i=0; i<interval.size(); i++) {
			if(i == (interval.size()-1)) {
				if(interval.get(i) == 1) {
					newInt = newInt +"1";
				}
				if(interval.get(i) == 2) {
					newInt = newInt +"b2";	
				}
				if(interval.get(i) == 3) {
					newInt = newInt +"2";
				}
				if(interval.get(i) == 4) {
					newInt = newInt +"b3";
				}
				if(interval.get(i) == 5) {
					newInt = newInt +"3";
				}
				if(interval.get(i) == 6) {
					newInt = newInt +"4";
				}
				if(interval.get(i) == 7) {
					newInt = newInt +"b5";
				}
				if(interval.get(i) == 8) {
					newInt = newInt +"5";
				}
				if(interval.get(i) == 9) {
					newInt = newInt +"#5";
				}
				if(interval.get(i) == 10) {
					newInt = newInt +"6";
				}
				if(interval.get(i) == 11) {
					newInt = newInt +"b7";
				}
				if(interval.get(i) == 12) {
					newInt = newInt +"7";
				}
			} else {			
				if(interval.get(i) == 1) {
					newInt = newInt +"1-";
				}
				if(interval.get(i) == 2) {
					newInt = newInt +"b2-";	
				}
				if(interval.get(i) == 3) {
					newInt = newInt +"2-";
				}
				if(interval.get(i) == 4) {
					newInt = newInt +"b3-";
				}
				if(interval.get(i) == 5) {
					newInt = newInt +"3-";
				}
				if(interval.get(i) == 6) {
					newInt = newInt +"4-";
				}
				if(interval.get(i) == 7) {
					newInt = newInt +"b5-";
				}
				if(interval.get(i) == 8) {
					newInt = newInt +"5-";
				}
				if(interval.get(i) == 9) {
					newInt = newInt +"#5-";
				}
				if(interval.get(i) == 10) {
					newInt = newInt +"6-";
				}
				if(interval.get(i) == 11) {
					newInt = newInt +"b7-";
				}
				if(interval.get(i) == 12) {
					newInt = newInt +"7-";
				}
			}
		}
		return newInt;
	}
	
	public Note getRoot() {
		return root;
	}
	
	public Note getMinorSecond() {
		return minorSecond;
	}
	
	public Note getMajorSecond() {
		return majorSecond;
	}
	
	public Note getMinorThird() {
		return minorThird;
	}
	
	public Note getMajorThird() {
		return majorThird;
	}
	
	public Note getPerfectFourth() {
		return perfectFourth;
	}
	
	public Note getDiminishedFifth() {
		return diminishedFifth;
	}
	
	public Note getMinorSixth() {
		return minorSixth;
	}
	
	public Note getMajorSixth() {
		return majorSixth;
	}
	
	public Note getMinorSeventh() {
		return minorSeventh;
	}
	
	public Note getMajorSeventh() {
		return majorSeventh;
	}
	
	
	
	@Override
	public String toString() {
		return root+" "+
			   minorSecond+" "+
			   majorSecond+" "+
			   minorThird+" "+
			   majorThird+" "+
			   perfectFourth+" "+
			   diminishedFifth+" "+
			   perfectFifth+" "+
			   minorSixth+" "+
			   majorSixth+" "+
			   minorSeventh+" "+
			   majorSeventh+
			   " scale: "+root;
	}
	
	/*public Scale(Note r) {
	root = r;
	int degree = r.ordinal();
	
	degree += 2;
	if (degree > 11) {degree -= 12;}
	majorSecond = Note.from(degree);
	
	degree += 2;
	if (degree > 11) {degree -= 12;}
	majorThird = Note.from(degree);
	
	degree += 1;
	if (degree > 11) {degree -= 12;}
	perfectFourth = Note.from(degree);
	
	degree += 2;
	if (degree > 11) {degree -= 12;}
	perfectFifth = Note.from(degree);
	
	degree += 2;
	if (degree > 11) {degree -= 12;}
	majorSixth = Note.from(degree);
	
	degree += 2;
	if (degree > 11) {degree -= 12;}
	majorSeventh = Note.from(degree);
}*/
}
