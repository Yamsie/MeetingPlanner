package com.example.james.meetingplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Favourites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.viewFavActs:
                /*
                Present list with all of users favourite activities saved to DB.
                 */
                break;

            case R.id.editFavActs:
                /*
                Take user input from right hand side.
                - Verify it. Check for one alphanumeric characters
                  only AND spaces (or [0-9a-zA-Z ]+)
                - Add it to database.
                 */
                break;

            case R.id.delFavActs:
                /*
                Take user to list of activities.
                - Allow them to select one, and present a pop-up that will ask if
                  they would like to delete it. Have "Yes" and "No" buttons.
                - Delete selection from the database.
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
                - Add it to database.
                 */
                break;

            case R.id.delFavLocs:
                /*
                Take user to list of locations.
                - Allow them to select one, and present a pop-up that will ask if
                they would like to delete it. Have "Yes" and "No" buttons.
                - Delete selection from the database.
                 */
                break;


        }
    }
}
