package com.reversechord;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RCOpenHelper extends SQLiteOpenHelper{
	
	Context context;
	private static final int DATABASE_VERSION = 41;
    private static final String DATABASE_NAME = "reverse.db";   
    public static final String CHORD_TABLE_NAME = "chords";
    public static final String SONG_TABLE_NAME = "songs";
    public static final String CHORD_IN_SONG_TABLE_NAME = "chords_in_songs";
    public static final String NOTE_TABLE_NAME = "notes";
    public static final String USER_TABLE_NAME = "users";
    public static final String VARIETY_TABLE_NAME = "varieties";
    public static final String ABBREVIATION_TABLE_NAME = "abbreviations";
    public static final String SCALE_TABLE_NAME = "scale";
    public static final String INTERVAL_TABLE_NAME = "intervals";
    public static final String STRUM_TABLE_NAME = "strum";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SONG_ID = "song_id";
    public static final String COLUMN_NOTE_ID = "note_id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_VARIETY_ID = "variety_id";
    public static final String COLUMN_VARIETY = "variety";
    public static final String COLUMN_SERVER_ID = "server_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_CHORD_ID = "chord_id";
    public static final String COLUMN_SCALE_ID = "scale_id";
    public static final String COLUMN_INTERVAL_ID = "interval_id";
    public static final String COLUMN_ABBR_ID = "abbr_id";
    public static final String COLUMN_CHORD_IN_SONG_ID = "chord_in_song_id";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ABBR = "abbreviation";
    public static final String COLUMN_INTERVAL = "interval";
    public static final String COLUMN_SCALE = "scale";
    public static final String COLUMN_DEGREES = "degrees";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_EMAIL= "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DIRECTION = "direction";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_COUNT = "count";
    
    public static final String CREATE_NOTE_TABLE = "CREATE TABLE "+NOTE_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SERVER_ID+" INTEGER NOT NULL, "+
    		COLUMN_NAME+" TEXT NOT NULL)";
    
    /*
    public static final String CREATE_CHORD_TABLE = "CREATE TABLE "+CHORD_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+" TEXT NOT NULL, "+
            COLUMN_ABBR+" TEXT NOT NULL, "+COLUMN_INTERVAL+" TEXT NOT NULL, "+
    		COLUMN_SCALE+" TEXT NOT NULL, "+COLUMN_SONG_ID+" INTEGER)";
    */
    		
    ///*
    public static final String CREATE_CHORD_TABLE = "CREATE TABLE "+CHORD_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SERVER_ID+" INTEGER NOT NULL, "+
            COLUMN_NOTE_ID+" INTEGER NOT NULL, "+COLUMN_VARIETY_ID+" INTEGER NOT NULL, "+
            "FOREIGN KEY ("+COLUMN_NOTE_ID+")" +
            " REFERENCES " + NOTE_TABLE_NAME+"("+COLUMN_SERVER_ID+") " +
            "ON DELETE CASCADE," +
            "FOREIGN KEY ("+COLUMN_VARIETY_ID+")" +
            " REFERENCES " + VARIETY_TABLE_NAME+"("+COLUMN_SERVER_ID+") " +
            "ON DELETE CASCADE)";
    //*/
    
    /*
    public static final String CREATE_SONG_TABLE = "CREATE TABLE "+SONG_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+" TEXT NOT NULL)";
    */
    
    ///*
    public static final String CREATE_SONG_TABLE = "CREATE TABLE "+SONG_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SERVER_ID+" INTEGER NOT NULL, "+
            COLUMN_USER_ID+" INTEGER NOT NULL, "+COLUMN_NAME+" TEXT NOT NULL, "+
            "FOREIGN KEY ("+COLUMN_USER_ID+")" +
            " REFERENCES " + USER_TABLE_NAME+"("+COLUMN_SERVER_ID+") " +
            "ON DELETE CASCADE)";
    //*/
    
    public static final String CREATE_CHORDS_IN_SONGS_TABLE = "CREATE TABLE "+CHORD_IN_SONG_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SERVER_ID+" INTEGER NOT NULL, "+
            COLUMN_SONG_ID+" INTEGER NOT NULL, "+COLUMN_VARIETY+" TEXT NOT NULL, "+
            COLUMN_NOTE+" TEXT NOT NULL, "+COLUMN_ORDER_ID+" INTEGER NOT NULL, "+
            "FOREIGN KEY ("+COLUMN_SONG_ID+")" +
            " REFERENCES " + SONG_TABLE_NAME+"("+COLUMN_SERVER_ID+") " +
            "ON DELETE CASCADE)";
    
    public static final String CREATE_ABBREVIATION_TABLE = "CREATE TABLE "+ABBREVIATION_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SERVER_ID+" INTEGER NOT NULL, "+
            COLUMN_ABBR+" TEXT_USER_ID)";
    
    public static final String CREATE_SCALE_TABLE = "CREATE TABLE "+SCALE_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SERVER_ID+" INTEGER NOT NULL, "+
    		COLUMN_NAME+" TEXT NOT NULL)";
    
    public static final String CREATE_INTERVAL_TABLE = "CREATE TABLE "+INTERVAL_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SERVER_ID+" INTEGER NOT NULL, "+
    		COLUMN_DEGREES+" TEXT NOT NULL)";
    
    public static final String CREATE_VARIETY_TABLE = "CREATE TABLE "+VARIETY_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SERVER_ID+" INTEGER NOT NULL, "+
            COLUMN_TYPE+" TEXT NOT NULL, "+COLUMN_ABBR_ID+" INTEGER NOT NULL, "+
            COLUMN_SCALE_ID+" INTEGER NOT NULL, "+COLUMN_INTERVAL_ID+" INTEGER NOT NULL, "+
            "FOREIGN KEY ("+COLUMN_ABBR_ID+")" +
            " REFERENCES " + ABBREVIATION_TABLE_NAME+"("+COLUMN_SERVER_ID+") " +
            "ON DELETE CASCADE," +
            "FOREIGN KEY ("+COLUMN_SCALE_ID+")" +
            " REFERENCES " + SCALE_TABLE_NAME+"("+COLUMN_SERVER_ID+") " +
            "ON DELETE CASCADE," +
		    "FOREIGN KEY ("+COLUMN_INTERVAL_ID+")" +
		    " REFERENCES " + INTERVAL_TABLE_NAME+"("+COLUMN_SERVER_ID+") " +
		    "ON DELETE CASCADE)";
    
    public static final String CREATE_USER_TABLE = "CREATE TABLE "+USER_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SERVER_ID+" INTEGER NOT NULL, "+
    		COLUMN_EMAIL+" TEXT NOT NULL, "+COLUMN_PASSWORD+" TEXT NOT NULL, "+COLUMN_STATUS+" INTEGER)";
    
    public static final String CREATE_STRUM_TABLE = "CREATE TABLE "+STRUM_TABLE_NAME+" ( "+
    		COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_CHORD_IN_SONG_ID+" INTEGER NOT NULL, "+
    		COLUMN_DURATION+" TEXT NOT NULL, "+COLUMN_DIRECTION+" TEXT, "+COLUMN_COUNT+" INTEGER NOT NULL)";
    		
    		
    public RCOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(CREATE_CHORD_TABLE);
    	db.execSQL(CREATE_SONG_TABLE);
    	db.execSQL(CREATE_ABBREVIATION_TABLE);
    	db.execSQL(CREATE_VARIETY_TABLE);
    	db.execSQL(CREATE_SCALE_TABLE);
    	db.execSQL(CREATE_INTERVAL_TABLE);
    	db.execSQL(CREATE_USER_TABLE);
    	db.execSQL(CREATE_CHORDS_IN_SONGS_TABLE);
    	db.execSQL(CREATE_STRUM_TABLE);
    	createNotes(db);
        System.out.println("ALL TABLES CREATED");
    }
    
    public void onCreateSongsAndChords(SQLiteDatabase db) {
    	db.execSQL(CREATE_CHORDS_IN_SONGS_TABLE);
    	db.execSQL(CREATE_SONG_TABLE);
        System.out.println("SONGS AND NOTES CREATED");
    }
    
    public void createNotes(SQLiteDatabase db) {
    	db.execSQL(CREATE_NOTE_TABLE);
    	System.out.println("NOTES CREATED");
    }
    
    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+CHORD_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+SONG_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+NOTE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+ABBREVIATION_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+VARIETY_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+SCALE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+INTERVAL_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+CHORD_IN_SONG_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+STRUM_TABLE_NAME);
		System.out.println("UPDATING ALL TABLES");
		onCreate(db);
	}
    
    public void reset(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS "+CHORD_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+SONG_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+NOTE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+ABBREVIATION_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+VARIETY_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+SCALE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+INTERVAL_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+CHORD_IN_SONG_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+STRUM_TABLE_NAME);
		System.out.println("UPDATING ALL TABLES");
		onCreate(db);
	}
    
    public void clearSongsAndChords(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS "+CHORD_IN_SONG_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+SONG_TABLE_NAME);
		System.out.println("UPDATING ALL TABLES");
		onCreateSongsAndChords(db);
	}
    
    public void insertNote(String n, SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(RCOpenHelper.COLUMN_NAME, n);
		db.insert(RCOpenHelper.NOTE_TABLE_NAME, null, values);
	}        
}