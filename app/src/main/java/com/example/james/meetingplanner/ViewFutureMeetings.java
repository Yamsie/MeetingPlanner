package com.example.james.meetingplanner;

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
        String data = "";
        ListView listView1 = (ListView) findViewById(R.id.list_view);

        DBHelper dbhelper = DBHelper.getInstance(this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT DISTINCT friend FROM meetings", null);
        int num = c.getCount();
        ArrayList<String> ms = new ArrayList<>();
        Toast.makeText(this, num + " row(s) in meetings table", Toast.LENGTH_LONG).show();
        int count = 0;
        String n = "";
        if (c.moveToFirst())
        {
            do
            {
                //Meetings meet = new Meetings();
                n = c.getString(c.getColumnIndex(DBHelper.COL1));
                /*meet.setLocation(c.getString(c.getColumnIndex(DBHelper.COL2)));
                meet.setTime(c.getString(c.getColumnIndex(DBHelper.COL3)));
                meet.setDate(c.getString(c.getColumnIndex(DBHelper.COL4)));
                meet.setActivity(c.getString(c.getColumnIndex(DBHelper.COL5)));
                meet.setDuration(c.getString(c.getColumnIndex(DBHelper.COL9)));
                ms.add(meet);*/
                ms.add(n);
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
                    //put meeting shite in this method maybe create a frag to display meeting details???
                }
            });
        db.close();
        c.close();
    }
}