package com.example.james.meetingplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android .widget.Toast;
import android.content.Context;


public class ViewFutureMeetings extends AppCompatActivity {
    private DBHelper dbhelper;
    private SQLiteDatabase db;
    String name, loc, time, date, act, dur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_future_meetings);
        Toast.makeText(this, "got this far", Toast.LENGTH_LONG).show();
        dbhelper = DBHelper.getInstance(this);
        db = DBHelper.getInstance(this).getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM meetings", null);
        if (c.moveToFirst()) {
            Toast.makeText(this, "cursor worked", Toast.LENGTH_LONG).show();
            name = c.getString(c.getColumnIndex(DBHelper.COL1));
            loc = c.getString(c.getColumnIndex(DBHelper.COL2));
            time = c.getString(c.getColumnIndex(DBHelper.COL3));
            date = c.getString(c.getColumnIndex(DBHelper.COL4));
            act = c.getString(c.getColumnIndex(DBHelper.COL5));
            dur = c.getString(c.getColumnIndex(DBHelper.COL9));
            c.close();
        }

        TextView tdf = (TextView) findViewById(R.id.textDisplayFriend);
        tdf.setText(name);
        Toast.makeText(this, "xxxxxxxxxxxx", Toast.LENGTH_LONG).show();
    }
}
