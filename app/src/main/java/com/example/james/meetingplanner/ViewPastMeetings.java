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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewPastMeetings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_past_meetings);
        ListView listView2 = (ListView) findViewById(R.id.list_view2);

        long currTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String ct = sdf.format(currTime);

        DBHelper dbhelper = DBHelper.getInstance(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        //Cursor c = db.rawQuery("SELECT * FROM meetings WHERE date < date('now') AND time > '" + ct + "';", null);
        Cursor c = db.rawQuery("SELECT * FROM meetings WHERE date < date('now');", null);
        //only checks the date, not the time if the meeting is today
        int num = c.getCount();
        String [] ms = new String[num];
        Toast.makeText(this, num + " row(s) in meetings table TESTING PURPOSES", Toast.LENGTH_SHORT).show();
        int count= 0;
        if (c.moveToFirst()) {
            do {
                String data = "";
                data = c.getString(c.getColumnIndex(DBHelper.COL1)) + ", "; //friend
                data += c.getString(c.getColumnIndex(DBHelper.COL2)) + ", "; //location
                data += c.getString(c.getColumnIndex(DBHelper.COL5)) + ", "; //activity
                data += c.getString(c.getColumnIndex(DBHelper.COL3)) + ", "; //time
                data += c.getString(c.getColumnIndex(DBHelper.COL4)) + ", "; //date
                data += c.getString(c.getColumnIndex(DBHelper.COL9));       //duration
                ms[count] = data;
                count++;
            }
            while (c.moveToNext());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, ms);
        listView2.setAdapter(adapter2);

    }
}