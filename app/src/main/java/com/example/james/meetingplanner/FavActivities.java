package com.example.james.meetingplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FavActivities extends AppCompatActivity {

    private DBHelper dbhelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_activities);
        dbhelper = DBHelper.getInstance(this);
        db = dbhelper.getWritableDatabase();

        final ListView listView = (ListView) findViewById(R.id.listAct);

        ArrayList<String> data = dbhelper.viewActivities(this);

        String[] values = new String[data.size()];
        for (int i = 0; i < values.length; i++)
            values[i] = data.get(i);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
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
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(FavActivities.this);
                builder.setMessage("Would you like to remove " + itemValue + "?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

}
