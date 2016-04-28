package com.example.james.meetingplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    //Button arrange, viewCurrent, viewAll, favs, exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.arrange:
                Intent arrintent = new Intent(this, ArrangeMeeting.class);
                startActivity(arrintent);
                break;

            case R.id.viewCurrent:
                Intent currintent = new Intent(this, Add_Delete_From_DB.class);
                startActivity(currintent);
                break;

            case R.id.viewPast:
                Intent pastintent = new Intent(this, ArrangeMeeting.class);
                startActivity(pastintent);
                break;

            case R.id.favs:
                Intent favintent = new Intent(this, Favourites.class);
                startActivity(favintent);
                break;
        }
    }
}
