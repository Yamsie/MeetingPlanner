package com.example.james.meetingplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
        ListView listView1 = (ListView) findViewById(R.id.list_view);

        DBHelper dbhelper = DBHelper.getInstance(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        //Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings WHERE date > date('now') AND time > time('now')", null);
        Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings WHERE date > date('now');", null);
        //only checks the date, not the time if the meeting is today
        int num = c.getCount();
        ArrayList<String> ms = new ArrayList<>();
        Toast.makeText(this, num + " row(s) in meetings table TESTING PURPOSES", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Click a name to view meeting details!", Toast.LENGTH_SHORT).show();
        int count = 0;
        String name = "";
        if (c.moveToFirst()) {
            do {
                //Meetings meet = new Meetings();
                name = c.getString(c.getColumnIndex(DBHelper.COL1));
                /*meet.setLocation(c.getString(c.getColumnIndex(DBHelper.COL2)));
                meet.setTime(c.getString(c.getColumnIndex(DBHelper.COL3)));
                meet.setDate(c.getString(c.getColumnIndex(DBHelper.COL4)));
                meet.setActivity(c.getString(c.getColumnIndex(DBHelper.COL5)));
                meet.setDuration(c.getString(c.getColumnIndex(DBHelper.COL9)));
                ms.add(meet);*/
                ms.add(name);
            }
            while (c.moveToNext());
        }
        //putting friend values from the array into an ArrayList
        String[] v = new String[ms.size()];
        for (int i = 0; i < v.length; i++) {
            v[i] = ms.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, v);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

         }
     }

        );
    }
}


                   /*
                   DBHelper dbhelper;
                   SQLiteDatabase db;
                   ListView listView3 = (ListView) findViewById(R.id.list_view);
                   //ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, v);
                   //listView3.setAdapter(adapter3);

                   final int itemPosition     = position;
                   final String  itemValue    = (String) listView3.getItemAtPosition(position);

                   dbhelper = DBHelper.getInstance();
                   db = dbhelper.getWritableDatabase();


                   Cursor c = db.rawQuery("SELECT * FROM meetings WHERE friend = " + itemValue + ";", null);
                   //only checks the date, not the time if the meeting is today
                   int num = c.getCount();
                   ArrayList<String> ms = new ArrayList<>();
                   //Toast.makeText(this, num + " row(s) in meetings table TESTING PURPOSES", Toast.LENGTH_LONG).show();
                   int count = 0;
                   String name = "";
                   if (c.moveToFirst())
                   {
                       do
                       {
                           //Meetings meet = new Meetings();
                           name = c.getString(c.getColumnIndex(DBHelper.COL1));
                /*meet.setLocation(c.getString(c.getColumnIndex(DBHelper.COL2)));
                meet.setTime(c.getString(c.getColumnIndex(DBHelper.COL3)));
                meet.setDate(c.getString(c.getColumnIndex(DBHelper.COL4)));
                meet.setActivity(c.getString(c.getColumnIndex(DBHelper.COL5)));
                meet.setDuration(c.getString(c.getColumnIndex(DBHelper.COL9)));
                ms.add(meet);
                           ms.add(name);
                       }
                       while (c.moveToNext());
                   }

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                dbhelper.delActivities(itemValue, FavActivities.this);
                                Intent yesintent = getIntent();
                                finish();
                                startActivity(yesintent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Intent nointent = getIntent();
                                finish();
                                startActivity(nointent);
                                break;
                        }
                    }
                };@Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {



                final int itemPosition     = position;
                final String  itemValue    = (String) listView.getItemAtPosition(position);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                dbhelper.delActivities(itemValue, FavActivities.this);
                                Intent yesintent = getIntent();
                                finish();
                                startActivity(yesintent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Intent nointent = getIntent();
                                finish();
                                startActivity(nointent);
                                break;
                        }
                    }
                }; */


