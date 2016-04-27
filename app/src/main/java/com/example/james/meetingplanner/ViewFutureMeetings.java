package com.example.james.meetingplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android .widget.Toast;
import android.content.Context;


public class ViewFutureMeetings extends AppCompatActivity {
    private DBHelper dbhelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_future_meetings);
        Toast.makeText(this, "got this far", Toast.LENGTH_LONG).show();
        dbhelper = new DBHelper(this);
        db = dbhelper.getReadableDatabase();

        /*String query = "SELECT * FROM meetings";
        Cursor c = db.rawQuery("query", null);
        if (c.moveToFirst()) {
            Toast.makeText(this, "cursor worked", Toast.LENGTH_LONG).show();
        }*/
    }
}
