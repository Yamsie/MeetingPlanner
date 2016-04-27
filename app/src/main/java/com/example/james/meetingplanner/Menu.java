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

    Button arrange, viewCurrent, viewAll, favs, exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId())
        {
            case R.id.arrange:
                intent = new Intent(this, ArrangeMeeting.class);

            case R.id.viewCurrent:
                intent = new Intent(this, Add_Delete_From_DB.class);

            case R.id.viewPast:
                intent = new Intent(this, ArrangeMeeting.class);

            case R.id.favs:
                intent = new Intent(this, ArrangeMeeting.class);

            case R.id.exit:
                intent = new Intent(this, ArrangeMeeting.class);
        }
        startActivity(intent);
    }
}
