package com.example.james.meetingplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectFavourites extends AppCompatActivity {
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_favourites);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.selAct:
                Intent actIntent = new Intent(this, FavActivities.class);
                startActivity(actIntent);
                break;

            case R.id.selLoc:
                Intent locIntent = new Intent(this, FavLocations.class);
                startActivity(locIntent);
                break;
        }
    }
}
