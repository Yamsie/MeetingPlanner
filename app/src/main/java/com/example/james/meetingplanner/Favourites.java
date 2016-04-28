package com.example.james.meetingplanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Favourites extends AppCompatActivity {

    private DBHelper dbhelper;
    private SQLiteDatabase db;
    String pattern = "[0-9a-zA-Z ]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        dbhelper = DBHelper.getInstance(this);
        db = dbhelper.getWritableDatabase();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.viewFavActs:
                /*
                Present list with all of users favourite activities saved to DB.
                 */
                final ListView listView = (ListView) findViewById(R.id.listAct);
                String[] values = dbhelper.viewActivities(this);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        int itemPosition     = position;

                        String  itemValue    = (String) listView.getItemAtPosition(position);

                        Toast.makeText(getApplicationContext(),
                                "Position: " + itemPosition + "  ListItem: " + itemValue, Toast.LENGTH_LONG)
                                .show();
                    }
                });
                break;

            case R.id.editFavActs:
                /*
                Take user input from right hand side.
                - Verify it. Check for one alphanumeric characters
                  only AND spaces (or [0-9a-zA-Z ]+)
                - Add it to database. Check if it exists in table.
                 */
                EditText afa = (EditText) findViewById(R.id.addAct);
                String input = afa.getText().toString();
                if(input.matches(pattern)) {
                    dbhelper.addActivities(input, this);
                }
                else{
                    Toast.makeText(this, "Error, invalid input",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.delFavActs:
                /*
                Take user input from right hand side.
                - Verify it. Check for one alphanumeric characters
                  only AND spaces (or [0-9a-zA-Z ]+)
                - Delete it from database. Check if it exists in table.
                 */
                break;

            case R.id.viewFavLocs:
                /*
                Present list with all of users favourite locations saved to DB.
                 */
                break;

            case R.id.editFavLocs:
                /*
                Take user input from right hand side.
                - Verify it. Check for one alphanumeric characters
                  only AND spaces (or [0-9a-zA-Z ]+)
                - Add it to database. Check if it exists in table.
                 */
                break;

            case R.id.delFavLocs:
                /*
                Take user input from right hand side.
                - Verify it. Check for one alphanumeric characters
                  only AND spaces (or [0-9a-zA-Z ]+). Check if it exists in table.
                - Delete it from database.
                 */
                break;


        }
    }
}
