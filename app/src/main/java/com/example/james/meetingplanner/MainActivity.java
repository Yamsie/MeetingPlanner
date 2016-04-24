package com.example.james.meetingplanner;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
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
                EditText en = (EditText) findViewById(R.id.editCon);
                EditText el = (EditText) findViewById(R.id.editLoc);
                EditText ea = (EditText) findViewById(R.id.editAct);
                if(dateFragment != null && timeFragment != null && isValid(en) && isValid(el) && isValid(ea)) {
                    TextView ed = (TextView) findViewById(R.id.editDate);
                    TextView et = (TextView) findViewById(R.id.editTime);
                    if(compareWithCurrentDate(ed.getText().toString(), et.getText().toString())) {
                        Toast.makeText(this, "Date parsed and returned!", Toast.LENGTH_SHORT).show();

                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            Date inDate = dateFormat.parse(ed.getText().toString() + " " + et.getText().toString());
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(inDate);

                            Intent calintent = new Intent(Intent.ACTION_EDIT);
                            calintent.setType("vnd.android.cursor.item/event");
                            calintent.putExtra("beginTime", cal.getTimeInMillis());
                            calintent.putExtra("allDay", false);
                            calintent.putExtra("duration", "PT.5H");
                            calintent.putExtra("title", "Meeting with " + en.getText().toString() + "!");
                            startActivity(calintent);
                        } catch (ParseException e) {
                            Toast.makeText(this, "Unexpected error", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Select a later time!", Toast.LENGTH_SHORT).show();
                    }

                } else if (dateFragment == null && timeFragment == null) {
                    Toast.makeText(this, "Select a time and date!", Toast.LENGTH_SHORT).show();
                } else if (!isValid(en) || !isValid(el) || !isValid(ea)) {
                    Toast.makeText(this, "Please fill in all fields with alphanumerical characters only!", Toast.LENGTH_SHORT).show();
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
        }

        return returnValue;
    }

    private boolean isValid(EditText etText) {
        boolean returnValue = false;
        String regex = "[A-Za-z0-9]+";
        if(etText.getText().toString().matches(regex) && etText.getText().toString().trim().length() > 0) {
            returnValue = true;
        }
        return returnValue;
    }
}


