package com.example.james.meetingplanner;

import android.app.AlertDialog;
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

                DBHelper dbhelper;
                SQLiteDatabase db;
                ListView listView3 = (ListView) findViewById(R.id.list_view);

                final int itemPosition = position;
                final String itemValue = (String) listView3.getItemAtPosition(position);

                dbhelper = DBHelper.getInstance(ViewFutureMeetings.this);
                db = dbhelper.getWritableDatabase();

                Cursor c = db.rawQuery("SELECT * FROM meetings WHERE friend = '" + itemValue + "';", null);
                int num = c.getCount();
                final String[] list = new String[num];
                int count = 0;
                if (c.moveToFirst()) {
                    do {
                        String data = "You are meeting ";
                        data += c.getString(c.getColumnIndex(DBHelper.COL1)); //name
                        data += " at/in " + c.getString(c.getColumnIndex(DBHelper.COL2)); //location
                        data += " on " + c.getString(c.getColumnIndex(DBHelper.COL4)); //date
                        data += " at " + c.getString(c.getColumnIndex(DBHelper.COL3)); //time
                        data += " for " + c.getString(c.getColumnIndex(DBHelper.COL9)); //duration
                        data += " hour(s) doing" + c.getString(c.getColumnIndex(DBHelper.COL5)) + "."; //activity
                        list[count] = data;
                        count++;
                    }
                    while (c.moveToNext());
                }
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ViewFutureMeetings.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
                listView3.setAdapter(adapter3);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent (ViewFutureMeetings.this, Menu.class);
                        startActivity(intent);
                    }
                };
                String message = "";
                for (int i = 0; i < list.length; i++) {
                    message += list[i] + "\n";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewFutureMeetings.this);
                builder.setMessage(message).setPositiveButton("Ok", dialogClickListener);
            }
        });
    }
}