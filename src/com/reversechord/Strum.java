package com.reversechord;

import java.io.Serializable;

import android.os.Parcelable;

public class Strum implements Serializable {
	public enum Direction {
		up(1), down(2);
		
		private final int num;
		
		Direction(int val) {
			num = val;
		}
	}
	public enum Duration {
		eighth(1),fourth(2),half(3),whole(4);
		
		private final int num;
		
		Duration(int val) {
			num = val;
		}
	}
	
	public int chordInSongId;
	public Direction direction;
	public Duration duration;
	public int count;
}
