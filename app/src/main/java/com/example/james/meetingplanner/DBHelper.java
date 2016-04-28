package com.example.james.meetingplanner;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "meetings_planner";
    public static final int DB_VERSION = 1;
    public static final String mTable = "meetings";
    private static final String CREATE_TABLE_MEETINGS = "CREATE TABLE IF NOT EXISTS " + mTable + " (friend VARCHAR,location VARCHAR,date DATE, time TIME, activity VARCHAR, duration INT);";

    public static final String COL1 = "friend";
    public static final String COL2 = "location";
    public static final String COL3 = "time";
    public static final String COL4 = "date";
    public static final String COL5 = "activity";
    public static final String COL9 = "duration";

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
        /*
        String f =m.getFriend();
        String l = m.getLocation();
        String t = m.getTime();
        String date = m.getDate();
        String act = m.getActivity();
        String dur = m.getDuration();
        */
        db.insert("meetings", null, values);
        db.close();
    }
}