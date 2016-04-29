package com.example.james.meetingplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "meetings_planner";
    public static final int DB_VERSION = 1;
    public static final String mTable = "meetings";
    public static final String aTable = "activities";
    public static final String lTable = "locations";

    private static final String CREATE_TABLE_MEETINGS = "CREATE TABLE IF NOT EXISTS " + mTable + " (friend VARCHAR,location VARCHAR,date DATE, time TIME, activity VARCHAR,duration INT);";
    private static final String CREATE_TABLE_ACTIVITIES = "CREATE TABLE IF NOT EXISTS " + aTable + " (activity VARCHAR);";
    private static final String CREATE_TABLE_LOCATIONS = "CREATE TABLE IF NOT EXISTS " + lTable + " (location VARCHAR);";

    public static final String COL1 = "friend";
    public static final String COL2 = "location";
    public static final String COL3 = "time";
    public static final String COL4 = "date";
    public static final String COL5 = "activity";
    public static final String COL9 = "duration";
    public static final String location = "location";
    public static final String activity = "activity";

    //singleton class
    private static DBHelper instance;
    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DBHelper(Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEETINGS);
        db.execSQL(CREATE_TABLE_ACTIVITIES);
        db.execSQL(CREATE_TABLE_LOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //SHOULDN'T BE NEEDED FOR THE PROJECT - TO BE IMPLEMENTED LATER
        database.execSQL("DROP TABLE IF EXISTS ");
    }

    public void addMeetings(Meetings m, Context c){
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, m.getFriend());
        values.put(COL2, m.getLocation());
        values.put(COL3, m.getTime());
        values.put(COL4, m.getDate());
        values.put(COL5, m.getActivity());
        values.put(COL9, m.getDuration());

        db.insert("meetings", null, values);
        db.close();
    }

    public void addActivities(String act, Context c) {
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(activity, act);

        db.insert("activities", null, values);
        db.close();
    }

    public void addLocations(String loc, Context c) {
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(location, loc);

        db.insert("locations", null, values);
        db.close();
    }

    public boolean delActivities(String act, Context c) {
        boolean returnVal;
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();

        returnVal = db.delete("activities", "activity = " + act, null) > 0;
        db.close();
        return returnVal;
    }

    public boolean delLocations(String loc, Context c) {
        boolean returnVal;
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();

        returnVal = db.delete("locations", "location = " + loc, null) > 0;
        db.close();
        return returnVal;
    }

    // BOTH TO BE USED FOR LISTING WHAT CURRENTLY EXISTS IN TABLES
    public ArrayList<String> viewActivities(Context c) {
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM activities;", null);
        int count = cur.getCount();
        ArrayList<String> data = new ArrayList<String>(); //cur.getColumnNames();
        int counter = 0;
        if (cur.moveToFirst())
        {
            do
            {
                data.add(cur.getString(0));
            }
            while (cur.moveToNext());
        }
        if (cur != null && !cur.isClosed())
        {
            cur.close();
        }
        db.close();
        cur.close();
        return data;
    }

    public void viewLocations(Context c) {
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM locations;", null);
        db.close();
        cur.close();
    }


    protected boolean searchActivities(String act) {
    boolean returnVal;
    SQLiteDatabase db = instance.getReadableDatabase();
    String query = "Select * from activities where activity = '" + act + "';";
    Cursor c = db.rawQuery(query, null);
    if(c == null){
        returnVal = false;
    } else {
        returnVal = true;
    }
    c.close();
    return returnVal;
    }

    private boolean searchLocations(String loc) {
    boolean returnVal;
    SQLiteDatabase db = instance.getReadableDatabase();
    String query = "Select * from locations where location = '" + loc + "';";
    Cursor c = db.rawQuery(query, null);
    if(c == null){
        returnVal = false;
    } else {
        returnVal = true;
    }
    c.close();
    return returnVal;
    }
}