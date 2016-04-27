package com.example.james.meetingplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View v) {
        Intent intent=null;
        switch(v.getId())
        {
            case R.id.arrange:
                intent = new Intent(this, ArrangeMeeting.class);

            case R.id.viewCurrent:
                //intent = new Intent(this, ViewFutureMeetings.class);

            case R.id.viewPast:
                //intent = new Intent(this, ArrangeMeeting.class);

            case R.id.favs:
                //intent = new Intent(this, ArrangeMeeting.class);

            case R.id.exit:
                //intent = new Intent(this, ArrangeMeeting.class);

            default:
                Toast.makeText(this, "options not working", Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
    }
}