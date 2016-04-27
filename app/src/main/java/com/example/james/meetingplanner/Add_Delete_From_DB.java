package com.example.james.meetingplanner;

import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

//view databases and add or delete friends/locations/activities - just as a something extra!
public class Add_Delete_From_DB extends AppCompatActivity {

    Button Place, Meeting, Friend, Activity;
    private DBHelper dbhelper;
    private SQLiteDatabase DB_NAME;
    String table;
    Intent i = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add__delete__from__db);
        dbhelper = new DBHelper(this);
        //DB_NAME = dbhelper.getReadableDatabase();


        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {
                //toasts are just to make sure the buttons work, db table will be displayed
                switch (v.getId()) {

                    case R.id.buttonPlace:
                        Toast.makeText(getBaseContext(), "You Clicked Place Button!", Toast.LENGTH_SHORT).show();
                        table = DBContract.EntryToTableLocations.TABLE;
                        showLocationsTable();

                        break;

                    case R.id.buttonFriend:
                        Toast.makeText(getBaseContext(), "You Clicked Friend Button!", Toast.LENGTH_SHORT).show();
                        table = DBContract.EntryToTableFriends.TABLE;
                        showFriendsTable();
                        break;

                    case R.id.buttonMeeting:
                        Toast.makeText(getBaseContext(), "You Clicked Meeting Button!", Toast.LENGTH_SHORT).show();
                        table = DBContract.EntryToTableMeetings.TABLE;
                        showMeetingsTable(dbhelper);
                        break;

                    case R.id.buttonActivity:
                        Toast.makeText(getBaseContext(), "You Clicked Activity Button!", Toast.LENGTH_SHORT).show();
                        table = DBContract.EntryToTableActivities.TABLE;
                        showActivitiesTable();
                        break;
                }

            }
        };
        findViewById(R.id.buttonPlace).setOnClickListener(handler);
        findViewById(R.id.buttonMeeting).setOnClickListener(handler);
        findViewById(R.id.buttonFriend).setOnClickListener(handler);
        findViewById(R.id.buttonActivity).setOnClickListener(handler);
    }

    public void showMeetingsTable(DBHelper dbhelper){
        /*String showFriend;
        String showLoc;
        Cursor c= dbhelper.DB_NAME.rawQuery("SELECT * FROM meetings", null);
        if(c.moveToFirst())
        {
            showFriend = c.getString(1);
            showLoc = c.getString(2);
        }*/
    }

    public void showActivitiesTable(){

    }

    public void showFriendsTable(){

    }

    public void showLocationsTable(){

    }

}
