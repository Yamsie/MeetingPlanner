package com.example.james.meetingplanner;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android .widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewPastMeetings extends AppCompatActivity {
    //This method displays all the past meetings, by comparing the dates and times of meetings in the database
    //with the current time and date

    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_past_meetings);
        ListView listView2 = (ListView) findViewById(R.id.list_view2);

        long currTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd");
        String ct = sdf.format(currTime);
        String cd = sdf2.format(currTime);

        DBHelper dbhelper = DBHelper.getInstance(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        //Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings WHERE date > date('now');", null);
        //Cursor c = db.rawQuery("SELECT * FROM meetings WHERE date <= date('now')", null);
        Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings WHERE (date < '"+cd+"') OR (date = '" + cd + "' AND time < '" + ct + "');", null);
        //Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings WHERE (date < date('now')) OR ((date = ('now')) AND (time < '" + ct + "'));", null);
        int num = c.getCount();
        ArrayList<String> ms = new ArrayList<>();
        String name = "";
        if ((c.moveToFirst()) && (num > 0)) {
            Toast.makeText(this, "Click a name to view meeting details!", Toast.LENGTH_SHORT).show();
            do {
                name = c.getString(c.getColumnIndex(DBHelper.COL1));
                ms.add(name);
            }while (c.moveToNext());
        }
        else{
            Toast.makeText(this, "No past meetings.", Toast.LENGTH_SHORT).show();
        }
        //putting friend values from the ArrayList into an array
        String[] v = new String[ms.size()];
        for (int i = 0; i < v.length; i++) {
            v[i] = ms.get(i);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, v);
        listView2.setAdapter(adapter2);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //method for viewing meeting details
                counter++;
                if (counter == 2) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                DBHelper dbhelper;
                SQLiteDatabase db;
                ListView listView4 = (ListView) findViewById(R.id.list_view2);
                final String itemValue = (String) listView4.getItemAtPosition(position);

                dbhelper = DBHelper.getInstance(ViewPastMeetings.this);
                db = dbhelper.getWritableDatabase();

                Cursor c = db.rawQuery("SELECT * FROM meetings WHERE friend = '" + itemValue + "';", null);
                int num = c.getCount();
                final String[] list = new String[num];
                int count = 0;
                if (c.moveToFirst()) {
                    do {
                        String data1 = "You met ";
                        data1 += c.getString(c.getColumnIndex(DBHelper.COL1)); //name
                        data1 += " at/in " + c.getString(c.getColumnIndex(DBHelper.COL2)); //location
                        data1 += " on " + c.getString(c.getColumnIndex(DBHelper.COL4)); //date
                        data1 += " at " + c.getString(c.getColumnIndex(DBHelper.COL3)); //time
                        data1 += " for " + c.getString(c.getColumnIndex(DBHelper.COL9)); //duration
                        data1 += " hour(s) doing " + c.getString(c.getColumnIndex(DBHelper.COL5)) + "."; //activity
                        list[count] = data1;
                        count++;
                    }
                    while (c.moveToNext());
                }
                c.close();
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ViewPastMeetings.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
                listView4.setAdapter(adapter3);
                String message = "";
                for (int i = 0; i < list.length; i++) {
                    message += list[i] + "\n";
                }
                //displays meeting details
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewPastMeetings.this);
                builder1.setMessage(message);
            }
        });
        db.close();
        c.close();


    }
}