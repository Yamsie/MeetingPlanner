package com.example.james.meetingplanner;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private final int PICK_CONTACT = 1;
    private DialogFragment dateFragment;
    private DialogFragment timeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode == PICK_CONTACT) {
            if(resultCode == AppCompatActivity.RESULT_OK) {
                Uri contactData = data.getData();
                Cursor c = getContentResolver().query(contactData, null, null, null, null);

                if(c.moveToFirst()) {
                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    EditText et = (EditText)findViewById(R.id.editCon);
                    et.setText(name);
                }
            }
        }
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonCon:
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            break;

            case R.id.buttonTime:
                FragmentManager fmTime = this.getFragmentManager();
                timeFragment = new TimePickerFragment(this);
                timeFragment.show(fmTime, "timePicker");
            break;

            case R.id.buttonDate:
                FragmentManager fmDate = this.getFragmentManager();
                dateFragment = new DatePickerFragment(this);
                dateFragment.show(fmDate, "datePicker");
            break;

            case R.id.buttonSubmit:
                if(dateFragment != null && timeFragment != null) {
                    TextView ed = (TextView) findViewById(R.id.editDate);
                    TextView et = (TextView) findViewById(R.id.editTime);
                    if(compareWithCurrentDate(ed.getText().toString(), et.getText().toString())) {
                        Toast.makeText(this, "Date parsed and returned!", Toast.LENGTH_SHORT).show();
                    }

                }
                else Toast.makeText(this, "Select a time and date!", Toast.LENGTH_SHORT).show();

            break;

        }
    }

    private boolean compareWithCurrentDate(String date, String time)  {
        boolean returnValue = false;
        String fullDate = date + " " + time;
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            cal.setTime(dateFormat.parse(fullDate));
            dateFormat.format(today.getTime());
        } catch(ParseException e) {
            Toast.makeText(this, "Date unable to parse!", Toast.LENGTH_SHORT).show();
        }

        if(cal.compareTo(today) > 0) {
            returnValue = true;
        } else {
            Toast.makeText(this,  "Pick a later time!", Toast.LENGTH_SHORT).show();
        }

        return returnValue;
    }
}


