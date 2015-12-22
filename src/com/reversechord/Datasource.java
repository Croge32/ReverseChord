package com.reversechord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

public class Datasource {
	
	SQLiteOpenHelper db;
	SQLiteDatabase qdb;
	
//	public static final String[] chordColumns = {
//		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_NAME,
//		RCOpenHelper.COLUMN_ABBR, RCOpenHelper.COLUMN_INTERVAL,
//		RCOpenHelper.COLUMN_SCALE, RCOpenHelper.COLUMN_SONG_ID
//	};
	
	public static final String[] chordColumns = {
		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_SERVER_ID,
		RCOpenHelper.COLUMN_NOTE_ID, RCOpenHelper.COLUMN_VARIETY_ID,
	};
	
//	public static final String[] songColumns = {
//		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_NAME
//	};
	
	public static final String[] songColumns = {
		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_SERVER_ID,
		RCOpenHelper.COLUMN_USER_ID, RCOpenHelper.COLUMN_NAME
	};
	
	public static final String[] noteColumns = {
		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_NAME
	};
	
	public static final String[] userColumns = {
		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_SERVER_ID, 
		RCOpenHelper.COLUMN_EMAIL, RCOpenHelper.COLUMN_PASSWORD,
		RCOpenHelper.COLUMN_STATUS
	};
	
	public static final String[] varietyColumns = {
		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_SERVER_ID, 
		RCOpenHelper.COLUMN_TYPE, RCOpenHelper.COLUMN_ABBR_ID,
		RCOpenHelper.COLUMN_SCALE_ID, RCOpenHelper.COLUMN_INTERVAL_ID
	};
	
	public static final String[] abbrColumns = {
		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_SERVER_ID,
		RCOpenHelper.COLUMN_ABBR
	};
	
	public static final String[] scaleColumns = {
		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_SERVER_ID,
		RCOpenHelper.COLUMN_NAME
	};
	
	public static final String[] intervalColumns = {
		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_SERVER_ID,
		RCOpenHelper.COLUMN_DEGREES
	};
	
	public static final String[] chordInSongColumns = {
		RCOpenHelper.COLUMN_ID, RCOpenHelper.COLUMN_SERVER_ID, 
		RCOpenHelper.COLUMN_SONG_ID, RCOpenHelper.COLUMN_VARIETY,
		RCOpenHelper.COLUMN_NOTE, RCOpenHelper.COLUMN_ORDER_ID
	};
	
	//Datasource object needed to use methods in other classes
	public Datasource(Context context){
		db = new RCOpenHelper(context);
	}
		
	//Needed for cursor/database operation that alters DB.
	public void openWrite(){		
		System.out.println("DB OPENED WRITABLE");
		qdb = db.getWritableDatabase();
	}
		
	//Needed for cursor/database operation that reads from DB.
	public void openRead(){		
		System.out.println("DB OPENED READABLE");
		qdb = db.getReadableDatabase();
	}
		
	//closes database after read/write
	public void close(){
		System.out.println("DB CLOSED");
		db.close();
	}
	
	public void insertChord(DBChord c) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_NAME, c.getName());
		values.put(RCOpenHelper.COLUMN_ABBR, c.getAbbr());
		values.put(RCOpenHelper.COLUMN_INTERVAL, c.getInterval());
		values.put(RCOpenHelper.COLUMN_SCALE, c.getScale());
		values.put(RCOpenHelper.COLUMN_SONG_ID, c.getSongId());
		qdb.insert(RCOpenHelper.CHORD_TABLE_NAME, null, values);
	}
	
	public void insertChordLite(ChordLite c) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_NAME, "");
		values.put(RCOpenHelper.COLUMN_ABBR, c.getAbbr());
		values.put(RCOpenHelper.COLUMN_INTERVAL, "");
		values.put(RCOpenHelper.COLUMN_SCALE, "");
		values.put(RCOpenHelper.COLUMN_SONG_ID, c.getSongId());
		qdb.insert(RCOpenHelper.CHORD_TABLE_NAME, null, values);
	}
	
	public void insertServerChord(ServerChord c) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_SERVER_ID, c.getChordId());
		values.put(RCOpenHelper.COLUMN_NOTE_ID, c.getNote().getNoteId());
		values.put(RCOpenHelper.COLUMN_VARIETY_ID, c.getVariety().getVarietyId());
		qdb.insert(RCOpenHelper.CHORD_TABLE_NAME, null, values);
	}
	
	public void insertServerNote(ServerNote n) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_SERVER_ID, n.getNoteId());
		values.put(RCOpenHelper.COLUMN_NAME, n.getName());
		qdb.insert(RCOpenHelper.NOTE_TABLE_NAME, null, values);
	}
	
	public void insertChordsInSong(ChordsInSong c) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_SERVER_ID, c.getChordInSongId());
		values.put(RCOpenHelper.COLUMN_SONG_ID, c.getSongId());
		values.put(RCOpenHelper.COLUMN_VARIETY, c.getVariety());
		values.put(RCOpenHelper.COLUMN_NOTE, c.getNote());
		values.put(RCOpenHelper.COLUMN_ORDER_ID, c.getOrder());
		qdb.insert(RCOpenHelper.CHORD_IN_SONG_TABLE_NAME, null, values);
	}
	
//	public void insertSong(Song s) {
//		ContentValues values = new ContentValues();
//		if (s.getId() != 0) {
//			values.put(RCOpenHelper.COLUMN_ID, s.getId());
//		}
//		values.put(RCOpenHelper.COLUMN_NAME, s.getName());
//		qdb.insert(RCOpenHelper.SONG_TABLE_NAME, null, values);
//	}
	
	public void insertServerSong(ServerSong s) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_SERVER_ID, s.getSongId());
		values.put(RCOpenHelper.COLUMN_USER_ID, s.getUserId());
		values.put(RCOpenHelper.COLUMN_NAME, s.getSongName());
		qdb.insert(RCOpenHelper.SONG_TABLE_NAME, null, values);
	}
	
	public void insertUser(User u) {
		openWrite();
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_SERVER_ID, u.getUserId());
		values.put(RCOpenHelper.COLUMN_EMAIL, u.getEmail());
		values.put(RCOpenHelper.COLUMN_PASSWORD, u.getPassword());
		qdb.insert(RCOpenHelper.USER_TABLE_NAME, null, values);
		close();
	}
	
	public void insertVariety(Variety v) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_SERVER_ID, v.getVarietyId());
		values.put(RCOpenHelper.COLUMN_TYPE, v.getType());
		values.put(RCOpenHelper.COLUMN_INTERVAL_ID, v.getInterval().getIntervalId());
		values.put(RCOpenHelper.COLUMN_SCALE_ID, v.getScale().getScaleId());
		values.put(RCOpenHelper.COLUMN_ABBR_ID, v.getAbbreviation().getAbbreviationId());
		qdb.insert(RCOpenHelper.VARIETY_TABLE_NAME, null, values);
	}
	
	public void insertAbbr(Abbr a) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_SERVER_ID, a.getAbbreviationId());
		values.put(RCOpenHelper.COLUMN_ABBR, a.getName());
		qdb.insert(RCOpenHelper.ABBREVIATION_TABLE_NAME, null, values);
	}
	
	public void insertScale(Sca s) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_SERVER_ID, s.getScaleId());
		values.put(RCOpenHelper.COLUMN_NAME, s.getName());
		qdb.insert(RCOpenHelper.SCALE_TABLE_NAME, null, values);
	}
	
	public void insertInterval(Interval i) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_SERVER_ID, i.getIntervalId());
		values.put(RCOpenHelper.COLUMN_DEGREES, i.getDegrees());
		qdb.insert(RCOpenHelper.INTERVAL_TABLE_NAME, null, values);
	}
	
	public int getUserCount() {
		openRead();
		int num = 0;
		Cursor c = qdb.query(true, RCOpenHelper.USER_TABLE_NAME, userColumns, null, null, null, null, null, null);
		while (c.moveToNext()) {
			num = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_ID));
		}
		c.close();
		close();
		return num;
	}
	
	public boolean userExists(User u) {
		openRead();
		boolean exists = false;
		Cursor c = qdb.query(true, RCOpenHelper.USER_TABLE_NAME, userColumns,
				RCOpenHelper.COLUMN_SERVER_ID+" = "+u.getUserId(), null, null, null, null, null);
		if (c.getCount() > 0) {
			exists = true;
		}
		c.close();
		close();
		return exists;
	}
	
	public User getActiveUser() {
		openRead();
		User u = new User();
		Cursor c = qdb.query(true, RCOpenHelper.USER_TABLE_NAME, userColumns,
				RCOpenHelper.COLUMN_STATUS+" = "+1, null, null, null, null, null);
		if (c.getCount() == 0) {
			return null;
		}
		while (c.moveToNext()) {
			u.setUserId(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_SERVER_ID)));
			u.setEmail(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_EMAIL)));
			u.setPassword(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_PASSWORD)));
			u.setStatus(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_STATUS)));
		}
		c.close();
		close();
		return u;
	}
	
	public void logIn(User u) {
		ContentValues values = new ContentValues();
		openWrite();
		int status = 0;
		Cursor c = qdb.query(true, RCOpenHelper.USER_TABLE_NAME, userColumns, RCOpenHelper.COLUMN_SERVER_ID+" = "+u.getUserId(),
				null, null, null, null, null);
		System.out.println(c.getCount()+" login cursor count");
		while (c.moveToNext()) {
			status = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_STATUS));
			if (status == 1) {
				System.out.println("Logged in");
			} else {
				System.out.println("Not logged in");
				values.put(RCOpenHelper.COLUMN_STATUS, 1);
				qdb.update(RCOpenHelper.USER_TABLE_NAME, values, RCOpenHelper.COLUMN_SERVER_ID+" = "+u.getUserId(), null);
				u.setStatus(1);
			}
		}
		c.close();
		close();
	}
	
	public int getStatus(User u) {
		openRead();
		int status = 0;
		Cursor c = qdb.query(true, RCOpenHelper.USER_TABLE_NAME, userColumns, RCOpenHelper.COLUMN_SERVER_ID+" = "+u.getUserId(),
				null, null, null, null, null);
		while (c.moveToNext()) {
			status = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_STATUS));
		}
		c.close();
		close();
		return status;
	}
	
	public void logOut(User u) {
		ContentValues values = new ContentValues();
		openWrite();
		int status = 0;
		Cursor c = qdb.query(true, RCOpenHelper.USER_TABLE_NAME, userColumns, null, null, null, null, null, null);
		while (c.moveToNext()) {
			status = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_STATUS));
			if (status == 0) {
				System.out.println("Logged out");
			} else {
				values.put(RCOpenHelper.COLUMN_STATUS, 0);
				qdb.update(RCOpenHelper.USER_TABLE_NAME, values,
						null, null);
				u.setStatus(0);
			}
		}
		c.close();
		close();
	}
	
	public void getAllUsersLocal() {
		openRead();
		User u = new User();
		Cursor c = qdb.query(true, RCOpenHelper.USER_TABLE_NAME, userColumns, null, null, null, null, null, null);
		while (c.moveToNext()) {
			u.setUserId(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_SERVER_ID)));
			u.setEmail(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_EMAIL)));
			u.setPassword(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_PASSWORD)));
			u.setStatus(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_STATUS)));
			System.out.println(u.toString());
		}
		c.close();
		close();
	}
	
	public int getSongNumber() {
		openRead();
		int num = 0;
		Cursor c = qdb.query(true, RCOpenHelper.SONG_TABLE_NAME, songColumns, null, null, null, null, null, null);
		while (c.moveToNext()) {
			num = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_ID));
		}
		c.close();
		close();
		return num+1;
	}
	
	public int getSongId(String name) {
		openRead();
		int id = 0;
		Cursor c = qdb.query(true, RCOpenHelper.SONG_TABLE_NAME, songColumns,
				RCOpenHelper.COLUMN_NAME+" = '"+name+"'", null, null, null, null, null);
		while (c.moveToNext()) {
			id = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_SERVER_ID));
		}
		c.close();
		close();
		return id;
	}
	
	public int getChordInSongId(ChordsInSong c, int id) {
		openRead();
		int chordInSongId = 0;
		Cursor cc = qdb.query(true, RCOpenHelper.CHORD_IN_SONG_TABLE_NAME, chordInSongColumns,
				RCOpenHelper.COLUMN_ORDER_ID+" = "+c.getOrder()+" AND "+RCOpenHelper.COLUMN_SONG_ID+" = "+id,
		null, null, null, null, null);
		while (cc.moveToNext()) {
			chordInSongId = cc.getInt(cc.getColumnIndex(RCOpenHelper.COLUMN_SERVER_ID));
		}
		cc.close();
		close();
		return chordInSongId;
	}
	
	public String[] songDisplay() {
		int songCount = 0;
		String[] songs;
		ArrayList<String> songNames = new ArrayList<String>();
		String entry = "";
		Cursor c = qdb.query(true, RCOpenHelper.SONG_TABLE_NAME, songColumns,
				null, null, null, null, null, null);
		songCount = c.getCount();
		songs = new String[songCount];
		while (c.moveToNext()) {
			songNames.add(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_NAME)));
		}
		c.moveToFirst();
		
		for (int i=1; i<=songCount; i++) {			
			c = qdb.query(true, RCOpenHelper.CHORD_TABLE_NAME, chordColumns,
					RCOpenHelper.COLUMN_SONG_ID+" = "+i, null, null, null, null, null);
			entry = songNames.get(i-1)+": ";
			while(c.moveToNext()) {
				if (c.getPosition() == c.getCount()-1) {
					entry = entry + c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_ABBR));
					songs[i-1] = entry;
				} else {
					entry = entry + c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_ABBR))+" | ";
					songs[i-1] = entry;
				}
			}
			c.moveToFirst();
			entry = "";
		}
		return songs;
	}
	
//	public ArrayList<String> liteDisplay() {
//		ArrayList<String> songs = new ArrayList<String>();
//		StringBuffer chords = new StringBuffer();
//		int index = 0;
//		Cursor c = qdb.query(true, RCOpenHelper.SONG_TABLE_NAME, songColumns,
//				null, null, null, null, null, null);
//		while (c.moveToNext()) {
//			chords.append(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_NAME))+": ");
//			index = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_ID));
//			Cursor c2 = qdb.query(true, RCOpenHelper.CHORD_TABLE_NAME, chordColumns,
//					RCOpenHelper.COLUMN_SONG_ID+" = "+index, null, null, null, null, null);
//			while(c2.moveToNext()) {
//				if (c2.getPosition() == c2.getCount()-1) {
//					chords.append(c2.getString(c2.getColumnIndex(RCOpenHelper.COLUMN_ABBR)));
//				} else {
//					chords.append(c2.getString(c2.getColumnIndex(RCOpenHelper.COLUMN_ABBR))+" | ");
//				}
//			}
//			//System.out.println(chords.toString()+" buffer for song row");
//			songs.add(chords.toString());
//			chords.delete(0, chords.length());
//		}
//		c.close();
//		return songs;
//	}
	
	public ArrayList<String> serverSongDisplay() {
		ArrayList<String> songs = new ArrayList<String>();
		StringBuffer chords = new StringBuffer();
		Cursor c = qdb.query(true, RCOpenHelper.SONG_TABLE_NAME, songColumns,
				null, null, null, null, null, null);
		while (c.moveToNext()) {
			songs.add(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_NAME)));
		}
		c.close();
		return songs;
	}
	
	public void removeDeletedSong(int id) {
		openWrite();
		qdb.delete(RCOpenHelper.SONG_TABLE_NAME, RCOpenHelper.COLUMN_SERVER_ID+" = "+id, null);
		close();
	}
	
	public void removeDeletedChord(int id) {
		openWrite();
		qdb.delete(RCOpenHelper.CHORD_IN_SONG_TABLE_NAME, RCOpenHelper.COLUMN_SERVER_ID+" = "+id, null);
		close();
	}
	
	public String getAbbrFromVariety(String variety) {
		openRead();
		String abbr = "";
		int abbrId = 0;
		Cursor c = qdb.query(true, RCOpenHelper.VARIETY_TABLE_NAME, Datasource.varietyColumns,
				RCOpenHelper.COLUMN_TYPE+" = '"+variety+"'", null, null, null, null, null);
		while (c.moveToNext()) {
			abbrId = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_ABBR_ID));
		}
		c.moveToFirst();
		c = qdb.query(true, RCOpenHelper.ABBREVIATION_TABLE_NAME, Datasource.abbrColumns,
				RCOpenHelper.COLUMN_SERVER_ID+" = "+abbrId, null, null, null, null, null);
		while (c.moveToNext()) {
			abbr = c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_ABBR));
		}
		close();
		return abbr;
	}
	
	public ArrayList<String> notesToArray() {
		ArrayList<String> notes = new ArrayList<String>();
		Cursor c = qdb.query(true, RCOpenHelper.NOTE_TABLE_NAME,
				Datasource.noteColumns, null, null, null, null, null, null);
		
		System.out.println(c.getCount()+" c column count");
		
		while(c.moveToNext()){
			final String noteText = c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_NAME));	
			notes.add(noteText);
		}
		return notes;
	}
	
//	public ArrayList<String> songToArray(int id) {
//		ArrayList<String> songs = new ArrayList<String>();
//		Cursor c = qdb.query(true, RCOpenHelper.CHORD_TABLE_NAME,
//				Datasource.chordColumns, RCOpenHelper.COLUMN_SONG_ID+" = "+id, null, null, null, null, null);
//		
//		System.out.println(c.getCount()+" c column count");
//		
//		while(c.moveToNext()){
//			final String songText = c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_ABBR));	
//			songs.add(songText);
//		}
//		return songs;
//	}
	
	public ArrayList<String> songToArray(int id) {
		String variety = "";
		String note = "";
		int order = 0;
		ArrayList<String> songs = new ArrayList<String>();
		Cursor c = qdb.query(true, RCOpenHelper.CHORD_IN_SONG_TABLE_NAME, chordInSongColumns, 
				RCOpenHelper.COLUMN_SONG_ID+" = "+id, null, null, null, null, null);
		
		System.out.println(c.getCount()+" c column count");
		//String[] chordsArray = new String[c.getCount()];
		
		while(c.moveToNext()) {
			order = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_ORDER_ID));
			System.out.println(order+" order in datasource songtoarray at index "+c.getPosition());
			note = c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_NOTE));
			variety = c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_VARIETY));
			//if (order == -1 || order > c.getCount()) {
			//	chordsArray[c.getPosition()] = note+" "+variety;
			//} else {				
			//	chordsArray[(order - 1)] =  note+" "+variety;	
			//}
			//System.out.println("passed index "+c.getPosition());
			//songs.add((order-1), note+" "+variety);
			songs.add(note+" "+variety);
		}
		//for (int i=0; i<chordsArray.length; i++) {
		//	songs.add(chordsArray[i]);
		//}
		return songs;
	}
	
	public ArrayList<ChordsInSong> songToChordArray(int id) {
		int serverId = 0;
		int order = 0;
		String variety = "";
		String note = "";
		ArrayList<ChordsInSong> chords = new ArrayList<ChordsInSong>();
		Cursor c = qdb.query(true, RCOpenHelper.CHORD_IN_SONG_TABLE_NAME, chordInSongColumns, 
				RCOpenHelper.COLUMN_SONG_ID+" = "+id, null, null, null, null, null);		
		System.out.println(c.getCount()+" c column count");
		
		//ChordsInSong[] chordsArray = new ChordsInSong[c.getCount()];
		
		while(c.moveToNext()){
			ChordsInSong chord = new ChordsInSong();
			chord.setChordInSongId(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_SERVER_ID)));
			chord.setNote(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_NOTE)));
			chord.setVariety(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_VARIETY)));
			order = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_ORDER_ID));
			System.out.println(order+" order in datasource songtochordarray at index "+c.getPosition());
			chord.setSongId(id);
//			if (order == -1 || order > c.getCount()) {
//				chord.setOrder(c.getPosition()+1);
//				chordsArray[c.getPosition()] =  chord;		
//			} else {
//				chord.setOrder(order);
//				chordsArray[(order - 1)] =  chord;
//			}
			chords.add(chord);
		}
//		for (int i=0; i<chordsArray.length; i++) {
//			chords.add(chordsArray[i]);
//		}
		return chords;
	}
	
	public ArrayList<ChordLite> songLiteArray(int id) {
		openRead();
		ArrayList<ChordLite> songs = new ArrayList<ChordLite>();
		Cursor c = qdb.query(true, RCOpenHelper.CHORD_TABLE_NAME,
				Datasource.chordColumns, RCOpenHelper.COLUMN_SONG_ID+" = "+id, null, null, null, null, null);
		
		System.out.println(c.getCount()+" c column count");
		
		while(c.moveToNext()){
			ChordLite chord = new ChordLite();
			String preAbbr = c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_ABBR));
			String preNote = c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_NAME));
			String[] split = preAbbr.split("([A-G]#?)");
			String[] split2 = preNote.split("\\s");
			chord.abbr = split[1];
			chord.note = split2[0];
			songs.add(chord);
		}
		
		for (int i=0; i<songs.size(); i++) {
			System.out.println("index "+i);
		}
		close();
		return songs;
	}
	
//	public void songRetrieverPopulate(SongRetriever s) {
//		openWrite();
//		Song song = new Song();
//		ChordLite chord = new ChordLite();
//		
//		for (int i=0; i<s.songLite.size(); i++) {
//			song.setName(s.songLite.get(i).songName);
//			song.setId(s.songLite.get(i).songId);
//			insertSong(song);
//			System.out.println("inserted song");
//			if (s.songLite.get(i).chords != null) {
//				for (int j=0; j<s.songLite.get(i).chords.size(); j++) {
//					chord.setAbbr(s.songLite.get(i).chords.get(j).note+s.songLite.get(i).chords.get(j).abbr);
//					chord.setSongId(s.songLite.get(i).chords.get(j).songId);
//					insertChordLite(chord);
//					System.out.println("inserted chord");
//				}
//			}
//		}
//		getSongs();
//		getChords();
//		close();
//	}
	
	public void serverSongRetrieverPopulate(ServerSongRetriever s) {
		openWrite();
		ServerSong song = new ServerSong();
		
		for (int i=0; i<s.song.size(); i++) {
			song.setSongName(s.song.get(i).getSongName());
			song.setSongId(s.song.get(i).getSongId());
			song.setUserId(s.song.get(i).getUserId());
			song.setChordsInSong(s.song.get(i).getchordsInSong());
			insertServerSong(song);
			System.out.println("inserted song");
			if (s.song.get(i).getchordsInSong() != null) {
				for (int j=0; j<s.song.get(i).getchordsInSong().size(); j++) {
					

					insertChordsInSong(s.song.get(i).getchordsInSong().get(j));
				}				
			}
		}
		getSongs();
		getChords();
		getChordsInSongs();
		close();
	}
	
	public void replaceChordInSong(ChordsInSong chord) {
		openWrite();
		qdb.delete(RCOpenHelper.CHORD_IN_SONG_TABLE_NAME,
				RCOpenHelper.COLUMN_SERVER_ID+" = "+chord.getChordInSongId(), null);
		insertChordsInSong(chord);
		close();
	}
	
	public void getSongs() {
		System.out.print("songs start");
		openRead();
		Cursor c = qdb.query(true, RCOpenHelper.SONG_TABLE_NAME, songColumns,
				null, null, null, null, null, null, null);
		while(c.moveToNext()) {
			System.out.print(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_SERVER_ID))+" ");
			System.out.print(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_USER_ID))+" ");
			System.out.println(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_NAME)));
		}
		
		c.close();
		close();
		System.out.print("songs end");
	}
	
	public void getChords() {
		System.out.print("chords start");
		openRead();
		Cursor c = qdb.query(true, RCOpenHelper.CHORD_TABLE_NAME, chordColumns,
				null, null, null, null, null, null, null);
		while(c.moveToNext()) {
			System.out.print(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_SERVER_ID))+" ");
			System.out.print(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_NOTE_ID))+" ");
			System.out.println(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_VARIETY_ID)));
		}
		
		c.close();
		close();
		System.out.print("chords end");
	}
	
	public void getChordsInSongs() {
		System.out.print("chords in songs start");
		openRead();
		Cursor c = qdb.query(true, RCOpenHelper.CHORD_IN_SONG_TABLE_NAME, chordInSongColumns,
				null, null, null, null, null, null, null);
		while(c.moveToNext()) {
			System.out.print(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_SERVER_ID))+" ");
			System.out.print(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_SONG_ID))+" ");
			System.out.print(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_VARIETY))+" ");
			System.out.print(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_NOTE))+" ");
			System.out.println(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_ORDER_ID)));
		}
		
		c.close();
		close();
		System.out.print("chords in songs end");
	}
	
	public void decrementOrderAfter(ChordsInSong chord) {
		openRead();
		Cursor c = qdb.query(true, RCOpenHelper.CHORD_IN_SONG_TABLE_NAME, chordInSongColumns,
				RCOpenHelper.COLUMN_ORDER_ID+" > "+chord.getOrder()+" AND "+ RCOpenHelper.COLUMN_SONG_ID+" = "+chord.getSongId(),
				null, null, null, null, null, null);
		System.out.println(c.getCount()+" c count decrement");
		while (c.moveToNext()) {
			ContentValues values = new ContentValues();
			int currentOrder = c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_ORDER_ID));
			values.put(RCOpenHelper.COLUMN_ORDER_ID, currentOrder-1);
			qdb.update(RCOpenHelper.CHORD_IN_SONG_TABLE_NAME, values, null, null);
		}
		c.close();
		getChordsInSongs();
		close();
	}
	
	public ArrayList<String> varietyArray() {
		ArrayList<String> vars = new ArrayList<String>();
		Variety v = new Variety();
		openRead();
		Cursor c = qdb.query(true, RCOpenHelper.VARIETY_TABLE_NAME, new String[] {RCOpenHelper.COLUMN_TYPE, RCOpenHelper.COLUMN_ABBR_ID},
				null, null, null, null, null, null, null);
		System.out.println(c.getCount()+" cursor count variety");
		while(c.moveToNext()) {
			v.setType(c.getString(c.getColumnIndex(RCOpenHelper.COLUMN_TYPE)));
			v.setAbbreviationId(c.getInt(c.getColumnIndex(RCOpenHelper.COLUMN_ABBR_ID)));
			
			Cursor c2 = qdb.query(true, RCOpenHelper.ABBREVIATION_TABLE_NAME, abbrColumns,
					RCOpenHelper.COLUMN_SERVER_ID+" = "+v.getAbbreviationId(), null, null, null, null, null, null);
			while(c2.moveToNext()) {
				v.setAbbreviationName(c2.getString(c2.getColumnIndex(RCOpenHelper.COLUMN_ABBR)));
			}
			c2.close();
			
			vars.add(v.getType()+" ("+v.getAbbreviationName()+")");
		}
		c.close();
		close();
		return vars;
	}
}
