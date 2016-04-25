package com.example.james.meetingplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Add_Delete_From_DB extends AppCompatActivity {

    Button Place, Meeting, Friend;
    private DBHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__delete__from__db);


        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.buttonPlace:
                        Toast.makeText(getBaseContext(), "You Clicked Place Button!", Toast.LENGTH_SHORT).show();
                        break;
                    //toasts are just to make sure the buttons work, db table will be displayed
                    case R.id.buttonFriend:
                        Toast.makeText(getBaseContext(), "You Clicked Friend Button!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.buttonMeeting:
                        Toast.makeText(getBaseContext(), "You Clicked Meeting Button!", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        findViewById(R.id.buttonPlace).setOnClickListener(handler);
        findViewById(R.id.buttonMeeting).setOnClickListener(handler);
        findViewById(R.id.buttonFriend).setOnClickListener(handler);
    }

}
