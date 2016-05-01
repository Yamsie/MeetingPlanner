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
    //This activity displays all saved locations and allows for the inclusion of more favourite locations
    //and the deletion of selected locations

    private DBHelper dbhelper;
    private SQLiteDatabase db;
    String pattern = "[0-9a-zA-Z ]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //This method displays all the locations in the locations table, using an array adapter and a listview
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
                //If a location is clicked, a dialog box appears asking if you want to delete this item

                final int itemPosition     = position;
                final String  itemValue    = (String) listView.getItemAtPosition(position);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //If you press yes on the dialog box, the item is deleted
                                //and disappears from the screen
                                dbhelper.delLocations(itemValue, FavLocations.this);
                                Intent yesintent = getIntent();
                                finish();
                                startActivity(yesintent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //If no is pressed, the item is not deleted and the screen remains the same
                                Intent nointent = getIntent();
                                finish();
                                startActivity(nointent);
                                break;
                        }
                    }
                };
                //This code segment creates the dialog box to display the display the message
                AlertDialog.Builder builder = new AlertDialog.Builder(FavLocations.this);
                builder.setMessage("Would you like to remove " + itemValue + "?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add:
                //If the add button is clicked, the input is taken from the editbox and searched for in the locations table.
                //If already present in the table a toast message is stating this appears
                //Otherwise, the input is validated and added to table if valid
                //If the input is invalid a toast message stating this appears
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
