package com.example.james.meetingplanner;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.arrange:
                MediaPlayer mp = MediaPlayer.create(this, R.raw.Ding);
                mp.start();
                Intent arrintent = new Intent(this, ArrangeMeeting.class);
                startActivity(arrintent);
                break;

            case R.id.viewCurrent:
                Intent currintent = new Intent(this, ViewFutureMeetings.class);
                startActivity(currintent);
                break;

            case R.id.viewPast:
                Intent pastintent = new Intent(this, ViewPastMeetings.class);
                startActivity(pastintent);
                break;

            case R.id.favs:
                Intent favintent = new Intent(this, SelectFavourites.class);
                startActivity(favintent);
                break;

        }
    }
}
