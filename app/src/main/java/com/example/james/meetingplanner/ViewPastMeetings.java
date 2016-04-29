package com.example.james.meetingplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android .widget.Toast;
import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewPastMeetings extends AppCompatActivity {
    private DBHelper dbhelper;
    private SQLiteDatabase db;
    private ArrayList <String> info;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.view_past_meetings);
        dbhelper = DBHelper.getInstance(this);
        db = dbhelper.getWritableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM meetings", null); //get dates in the past
        int num = cur.getCount();
        Toast.makeText(this, num + " past meetings", Toast.LENGTH_LONG).show();
        while (cur.moveToNext()) {
            data = "";
            data = cur.getString(cur.getColumnIndex(DBHelper.COL1));
            data += ", ";
            data += cur.getString(cur.getColumnIndex(DBHelper.COL4));
            info.add(data);
        }
        cur.close();
        db.close();
    }
}