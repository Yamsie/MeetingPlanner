package com.example.james.meetingplanner;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ArrangeMeeting extends AppCompatActivity {

    // Global declarations. Used for starting activities for result.
    private final int PICK_CONTACT = 1;
    private final int START_CAL = 2;
    private final int PICK_ACTIVITY = 3;
    private final int PICK_LOCATION = 4;
    private DialogFragment dateFragment;
    private DialogFragment timeFragment;
    private DBHelper dbhelper;
    private SQLiteDatabase db;

    // Starts when app is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrange_meeting);
        dbhelper = DBHelper.getInstance(this); // Sets instance of DBHelper
    }


    // Called when an intent is started. The reqCode is compared and it checked in if else statements.
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
        } else if(reqCode == START_CAL) {
            if(RESULT_OK == resultCode) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        } else if(reqCode == PICK_ACTIVITY) {
            if(RESULT_OK == resultCode) {
                EditText ea = (EditText) findViewById(R.id.editAct);
                ea.setText(data.getStringExtra("act"));
            }
        } else if(reqCode == PICK_LOCATION) {
            if(RESULT_OK == resultCode) {
                EditText el = (EditText) findViewById(R.id.editLoc);
                el.setText(data.getStringExtra("loc"));
            }
        } else {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonCon:
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
                break;

            case R.id.buttonTime:
                FragmentManager fmTime = this.getFragmentManager();
                timeFragment = new TimePickerFragment(this);
                timeFragment.show(fmTime, "timePicker");
                break;

            case R.id.buttonDur:
                EditText edr = (EditText) findViewById(R.id.editDur);
                edr.requestFocus();
                break;

            case R.id.buttonDate:
                FragmentManager fmDate = this.getFragmentManager();
                dateFragment = new DatePickerFragment(this);
                dateFragment.show(fmDate, "datePicker");
                break;

            case R.id.buttonLoc:
                Intent loc = new Intent(this, SelLocation.class);
                startActivityForResult(loc, PICK_LOCATION);
                break;

            case R.id.buttonAct:
                Intent act = new Intent(this, SelActivity.class);
                startActivityForResult(act, PICK_ACTIVITY);
                break;

            case R.id.buttonSubmit:
                EditText en = (EditText) findViewById(R.id.editCon);
                EditText el = (EditText) findViewById(R.id.editLoc);
                EditText ea = (EditText) findViewById(R.id.editAct);
                EditText edu = (EditText) findViewById(R.id.editDur);
                if (dateFragment != null && timeFragment != null && isValid(en) && isValid(el) && isValid(ea) && isValidNum(edu)) {
                    TextView ed = (TextView) findViewById(R.id.editDate);
                    TextView et = (TextView) findViewById(R.id.editTime);

                    if (compareWithCurrentDate(ed.getText().toString(), et.getText().toString())) {

                        String name  = en.getText() . toString() . trim();
                        String location = el.getText() . toString() . trim();
                        String time = et.getText(). toString() . trim();
                        String date = ed.getText(). toString() . trim();
                        String activity = ea.getText(). toString() . trim();
                        String duration = edu.getText(). toString() . trim();

                        ContentValues values = new ContentValues();
                        values.put("friend", name);
                        values.put("location", location);
                        values.put("time", time);
                        values.put("date", date);
                        values.put("activity", activity);
                        values.put("duration", duration);

                        Meetings m1 = new Meetings(name, location, time, date, activity, duration);
                        dbhelper.addMeetings(m1, this);
                        Toast.makeText(this, "Meeting saved!", Toast.LENGTH_SHORT).show();

                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            Date inDate = dateFormat.parse(ed.getText().toString() + " " + et.getText().toString());
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(inDate);

                            final Intent calintent = new Intent(Intent.ACTION_EDIT);
                            calintent.setType("vnd.android.cursor.item/event");
                            calintent.putExtra("beginTime", cal.getTimeInMillis());
                            calintent.putExtra("allDay", false);
                            calintent.putExtra("endTime", cal.getTimeInMillis() + (Integer.parseInt(edu.getText().toString()) * 60 * 60 * 1000));
                            calintent.putExtra("title", activity + " with " + en.getText().toString() + "!");
                            calintent.putExtra("eventLocation", location);
                            calintent.putExtra("description", "You're meeting with " + name + " at " + time + " on " + date + " for " + activity + ".");
                            calintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            calintent.putExtra("EXIT", true);

                            // Dialog box for when everything is written to database
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            startActivityForResult(calintent, START_CAL);
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Intent intent = getIntent();
                                            finish();
                                            startActivity(intent);
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage("Meeting saved!\nWould you like to save it to Google Calendar?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).setCancelable(false).show();
                        } catch (ParseException e) {
                            Toast.makeText(this, "Unexpected error", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Select a later time or date!", Toast.LENGTH_SHORT).show();
                    }

                } else if (dateFragment == null && timeFragment == null) {
                    Toast.makeText(this, "Select a time and date!", Toast.LENGTH_SHORT).show();
                } else if (!isValid(edu)) {
                    Toast.makeText(this, "Enter a duration!", Toast.LENGTH_SHORT).show();
                } else if (!isValidNum(edu)){
                    Toast.makeText(this, "Enter a duration between  hours!", Toast.LENGTH_SHORT).show();
                } else if (!isValid(en) || !isValid(el) || !isValid(ea) || !isValid(edu)) {
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
        String regex = "[A-Za-z0-9 ]+";
        if(etText.getText().toString().matches(regex) && etText.getText().toString().trim().length() > 0) {
            returnValue = true;
        }
        return returnValue;
    }

    private boolean isValidNum(EditText etText) {
        boolean returnValue = false;
        if(!etText.getText().toString().equals("") && Integer.parseInt(etText.getText().toString()) <= 12 && Integer.parseInt(etText.getText().toString()) > 0) {
            returnValue = true;
        }
        return returnValue;
    }
}


