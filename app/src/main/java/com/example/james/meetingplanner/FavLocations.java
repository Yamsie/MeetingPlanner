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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavLocations extends AppCompatActivity {

    private DBHelper dbhelper;
    private SQLiteDatabase db;
    String pattern = "[0-9a-zA-Z ]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);
        dbhelper = DBHelper.getInstance(this);
        db = dbhelper.getWritableDatabase();

        final ListView listView = (ListView) findViewById(R.id.listContent);

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

                final int itemPosition     = position;
                final String  itemValue    = (String) listView.getItemAtPosition(position);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                dbhelper.delLocations(itemValue, FavLocations.this);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(FavLocations.this);
                builder.setMessage("Would you like to remove " + itemValue + "?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add:

                EditText afa = (EditText) findViewById(R.id.addNew);
                String input = afa.getText().toString();
                boolean present = dbhelper.searchLocations(input, this);
                if(present) {
                    if (input.matches(pattern)) {
                        dbhelper.addLocations(input, this);
                        Intent thisintent = getIntent();
                        finish();
                        startActivity(thisintent);
                    } else {
                        Toast.makeText(this, "Invalid input, alphanumeric characters and spaces only.", Toast.LENGTH_LONG).show();
                        Intent failintent = getIntent();
                        finish();
                        startActivity(failintent);
                    }
                }
                else{
                    Toast.makeText(this, "Already present in favourites.", Toast.LENGTH_LONG).show();
                    Intent failintent = getIntent();
                    finish();
                    startActivity(failintent);
                }

                break;
        }
    }
}
