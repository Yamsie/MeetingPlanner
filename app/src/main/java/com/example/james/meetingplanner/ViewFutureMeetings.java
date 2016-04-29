package com.example.james.meetingplanner;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android .widget.Toast;
import android.content.Context;
import java.util.ArrayList;

public class ViewFutureMeetings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_future_meetings);
        String data = "";
        ListView listView = (ListView) findViewById(R.id.list_view);

        DBHelper dbhelper = DBHelper.getInstance(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings", null);
        int num = c.getCount();
        String[] ms = new String[num];
        Toast.makeText(this, num + " row in meetings table", Toast.LENGTH_LONG).show();
        int count = 0;
        if (c.moveToFirst()) {
            while (c.moveToNext()) {
                data = "";
                data = c.getString(c.getColumnIndex(DBHelper.COL1));
                //meet.setLocation(c.getString(c.getColumnIndex(DBHelper.COL2)));
                //meet.setTime(c.getString(c.getColumnIndex(DBHelper.COL3)));
                //meet.setDate(c.getString(c.getColumnIndex(DBHelper.COL4)));
                //meet.setActivity(c.getString(c.getColumnIndex(DBHelper.COL5)));
                //meet.setDuration(c.getString(c.getColumnIndex(DBHelper.COL9)));
                ms[count] = data;
                count++;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, ms);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });
        db.close();
        c.close();
    }
}