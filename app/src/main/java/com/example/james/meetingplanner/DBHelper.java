package com.example.james.meetingplanner;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {
    // Define the SQLite database name
    private static final String DATABASE_NAME = "FriendlyReminder.db";
    // Define the SQLite database version
    private static final int DATABASE_VERSION = 1;
    // Define the SQLite Tables names to create
    public static final String TABLE_FRIEND = "Friend";
    public static final String TABLE_LOCATION = "Location";
    public static final String TABLE_MEETING = "Meeting";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All table creation in here
        db.execSQL("CREATE TABLE IF NOT EXISTS friends(name VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS locations(place VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS meetings(name VARCHAR,place VARCHAR,date DATE, when TIME);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //SHOULDN'T BE NEEDED FOR THE PROJECT - TO BE IMPLEMENTED LATER
        database.execSQL("DROP TABLE IF EXISTS ");
    }

}