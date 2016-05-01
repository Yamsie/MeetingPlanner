package com.example.james.meetingplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //declarations of all the final variables necessary for the database schema
    public static final String DB_NAME = "meetings_planner";
    public static final int DB_VERSION = 1;
    public static final String mTable = "meetings";
    public static final String aTable = "activities";
    public static final String lTable = "locations";

    private static final String CREATE_TABLE_MEETINGS = "CREATE TABLE IF NOT EXISTS " + mTable + " (friend VARCHAR,location VARCHAR,date DATE, time TIME, activity VARCHAR,duration INT);";
    private static final String CREATE_TABLE_ACTIVITIES = "CREATE TABLE IF NOT EXISTS " + aTable + " (activity VARCHAR);";
    private static final String CREATE_TABLE_LOCATIONS = "CREATE TABLE IF NOT EXISTS " + lTable + " (location VARCHAR);";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS";

    public static final String COL1 = "friend";
    public static final String COL2 = "location";
    public static final String COL3 = "time";
    public static final String COL4 = "date";
    public static final String COL5 = "activity";
    public static final String COL9 = "duration";
    public static final String location = "location";
    public static final String activity = "activity";

    private static DBHelper instance;

    public static DBHelper getInstance(Context context) {
        //singleton class for dbhelper, this method is called instead of creating new instances of the DBHelper
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creates the database tables when the app is run for the first time
        db.execSQL(CREATE_TABLE_MEETINGS);
        db.execSQL(CREATE_TABLE_ACTIVITIES);
        db.execSQL(CREATE_TABLE_LOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //upgrades database - not used in this project but added in for completeness sake
        database.execSQL(DELETE_TABLE + mTable);
        database.execSQL(DELETE_TABLE + aTable);
        database.execSQL(DELETE_TABLE + lTable);
        onCreate(database);
    }

    public void addMeetings(Meetings m, Context c){
        //receives a Meetings object and adds it to the meetings table
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
        //Receives a String representing an activity and adds it to the activities table
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(activity, act);

        db.insert("activities", null, values);
        db.close();
    }

    public void addLocations(String loc, Context c) {
        //Receives a String representing an locations and adds it to the locations table
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(location, loc);

        db.insert("locations", null, values);
        db.close();
    }

    public boolean delActivities(String act, Context c) {
        //Deletes an activity from the activities table
        boolean returnVal;
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();

        returnVal = db.delete("activities", "activity = '" + act + "';", null) > 0;
        db.close();
        return returnVal;
    }

    public boolean delLocations(String loc, Context c) {
        //deletes a location from the locations table
        boolean returnVal;
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();

        returnVal = db.delete("locations", "location = '" + loc + "';", null) > 0;
        db.close();
        return returnVal;
    }

    public ArrayList<String> viewActivities(Context c) {
        //Displays what is currently in the activities table
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM activities;", null);
        int count = cur.getCount();
        ArrayList<String> data = new ArrayList<String>();
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

    public ArrayList<String> viewLocations(Context c) {
        //Displays what is currently in the locations table
        SQLiteDatabase db = DBHelper.getInstance(c).getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM locations;", null);
        int count = cur.getCount();
        ArrayList<String> data = new ArrayList<String>();
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


    protected boolean searchActivities(String act, Context c) {
        //To check if an activity is already present in the activities table when entering a new activity
        boolean returnVal = true;
        ArrayList<String> list = viewActivities(c);

        for(int i = 0; i < list.size(); i++) {
            if (act.toLowerCase().compareTo(list.get(i).toLowerCase()) == 0) {
                returnVal = false;
                i = list.size();
            }
        }
        return returnVal;
    }

    protected boolean searchLocations(String loc, Context c) {
        //To check if a location is already present in the locations table when entering a new location
        boolean returnVal = true;
        ArrayList<String> list = viewLocations(c);

        for (int i = 0; i < list.size(); i++) {
            if (loc.toLowerCase().compareTo(list.get(i).toLowerCase()) == 0) {
                returnVal = false;
                i = list.size();
            }
        }
        return returnVal;
    }
}