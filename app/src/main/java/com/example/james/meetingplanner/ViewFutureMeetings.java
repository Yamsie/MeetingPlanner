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

        Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings WHERE date > date('now')", null);
        //only checks the date, not the time if the meeting is today
        int num = c.getCount();
        ArrayList<String> ms = new ArrayList<>();
        Toast.makeText(this, num + " row(s) in meetings table TESTING PURPOSES", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Click a name to view meeting details!", Toast.LENGTH_LONG).show();
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
                ms.add(meet);*/
                ms.add(name);
            }
            while (c.moveToNext());
        }
            //putting friend values from the array into an ArrayList
            String[] v = new String[ms.size()];
            for(int i = 0; i < v.length; i++) {
                v[i] = ms.get(i);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, v);
            listView1.setAdapter(adapter);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //put meeting shite in this method maybe create a frag to display meetings shite
                //I have no idea how to do this I'm afraid
                // code is in fav activities for dialog box with overide method
                /*
                final String [] mDetails = new String[5];
                //copied from above
                ListView listView1 = (ListView) findViewById(R.id.list_view);

                DBHelper dbhelper = DBHelper.getInstance(ViewFutureMeetings.this);
                SQLiteDatabase db = dbhelper.getWritableDatabase();

                Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings where friend =", null);
                int num = c.getCount();
                ArrayList<String> ms = new ArrayList<>();
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
                //putting meeting values from the array into an ArrayList
                String[] v = new String[ms.size()];
                for(int i = 0; i < v.length; i++) {
                    v[i] = ms.get(i);
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, v);
                listView1.setAdapter(adapter1);

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dbhelper.delActivities(itemValue, Favourites.this);
                        Intent yesintent = getIntent();
                        finish();
                        startActivity(yesintent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent nointent = getIntent();
                        finish();
                        startActivity(nointent);
                        break;
                }*/
            }
    });
        db.close();
        c.close();
    }
}