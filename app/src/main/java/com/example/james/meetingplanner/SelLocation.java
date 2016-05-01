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

public class SelLocation extends AppCompatActivity {
    //This activity allows you to select a favourite location when arranging a meeting

    private DBHelper dbhelper;
    private SQLiteDatabase db;
    String pattern = "[0-9a-zA-Z ]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel);

        dbhelper = DBHelper.getInstance(this);
        db = dbhelper.getWritableDatabase();

        final ListView listView = (ListView) findViewById(R.id.listContent);

        //This method call lists out all the locations in the locations table
        ArrayList<String> data = dbhelper.viewLocations(this);

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
                //If an activity is pressed, it is added to the arrange meeting activity in the location section

                final int itemPosition = position;
                final String itemValue = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("loc", itemValue);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
