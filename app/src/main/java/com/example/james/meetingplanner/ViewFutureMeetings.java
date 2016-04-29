package com.example.james.meetingplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android .widget.Toast;
import android.content.Context;

import java.util.ArrayList;

public class ViewFutureMeetings extends AppCompatActivity {
    private DBHelper dbhelper;
    private SQLiteDatabase db;
    private ArrayList <String> friends;
    private ListView lol;


    String name, loc, time, date, act, dur, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_future_meetings);
        dbhelper = DBHelper.getInstance(this);
        db = dbhelper.getWritableDatabase();
        lol = (ListView) findViewById(R.id.list_person);

        Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings", null);
        int num = c.getCount();
        Toast.makeText(this, num + " row in meetings table", Toast.LENGTH_LONG).show();
        if(c.moveToFirst()) {
            while (c.moveToNext()) {
                data = "";
                data = c.getString(c.getColumnIndex(DBHelper.COL1));
                data += ", ";
                data += c.getString(c.getColumnIndex(DBHelper.COL4));
                friends.add(data);
                }
            }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
            this,
                android.R.layout.simple_list_item_1,friends);
        lol.setAdapter(arrayAdapter);


        c.close();
        db.close();
    }
}
