package com.example.james.meetingplanner;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context,DBContract.DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All table creation in here
        db.execSQL("CREATE TABLE IF NOT EXISTS friends(name VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS locations(place VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS meetings(name VARCHAR,place VARCHAR,date DATE, when TIME);");
        db.execSQL("CREATE TABLE IF NOT EXISTS activity(to-do VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //SHOULDN'T BE NEEDED FOR THE PROJECT - TO BE IMPLEMENTED LATER
        database.execSQL("DROP TABLE IF EXISTS ");
    }

}