package com.reversechord;

import java.util.ArrayList;

public class ChordNick {

	private String E;
	private String A;
	private String D;
	private String G;
	private String B;
	private String e;
	
	public ChordNick () {}
	
	public ChordNick(String E, String A, String D, String G, String B, String e) {
		this.E = E;
		this.A = A;
		this.D = D;
		this.G = G;
		this.B = B;
		this.e = e;
	}
	
	public String getE(){
		return E;
	}
	
	public void setE(String E) {
		this.E = E;
	}
	
	public String getA(){
		return A;
	}
	
	public void setA(String A) {
		this.A = A;
	}
	
	public String getD(){
		return D;
	}
	
	public void setD(String D) {
		this.D = D;
	}
	
	public String getG(){
		return G;
	}
	
	public void setG(String G) {
		this.G = G;
	}
	
	public String getB(){
		return B;
	}
	
	public void setB(String B) {
		this.B = B;
	}
	
	public String gete(){
		return e;
	}
	
	public void sete(String e) {
		this.e = e;
	}
	
	public static ChordNick fromArrayList(ArrayList<String> notes) {
		ChordNick c = new ChordNick();
		int size = notes.size();
		if (size == 6) {
			c.setE(notes.get(0));
			c.setA(notes.get(1));
			c.setD(notes.get(2));
			c.setG(notes.get(3));
			c.setB(notes.get(4));
			c.sete(notes.get(5));
		} else if (size == 5) {
			c.setE(notes.get(0));
			c.setA(notes.get(1));
			c.setD(notes.get(2));
			c.setG(notes.get(3));
			c.setB(notes.get(4));
		} else if (size == 4) {
			c.setE(notes.get(0));
			c.setA(notes.get(1));
			c.setD(notes.get(2));
			c.setG(notes.get(3));
		} else if (size == 3) {
			c.setE(notes.get(0));
			c.setA(notes.get(1));
			c.setD(notes.get(2));
		}
		return c;
	}
	
	public String toString() {
		return E+","+A+","+D+","+G+","+B+","+e;
	}
}
