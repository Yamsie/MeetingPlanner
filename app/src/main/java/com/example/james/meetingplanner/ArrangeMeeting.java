package com.example.james.meetingplanner;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ArrangeMeeting extends AppCompatActivity {

    private final int PICK_CONTACT = 1;
    private final int START_CAL = 2;
    private DialogFragment dateFragment;
    private DialogFragment timeFragment;
    private DBHelper dbhelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrange_meeting);
        dbhelper = new DBHelper(this);
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
        } else if(reqCode == START_CAL) {
            Toast.makeText(this, "Reached Start_cal", Toast.LENGTH_LONG).show();
            if(RESULT_OK == resultCode) {
                Toast.makeText(this, "Pass", Toast.LENGTH_LONG).show();
                setResult(resultCode);
            }
        } else {
            Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
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

            case R.id.buttonDate:
                FragmentManager fmDate = this.getFragmentManager();
                dateFragment = new DatePickerFragment(this);
                dateFragment.show(fmDate, "datePicker");
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
                        Toast.makeText(this, "Date parsed and returned!", Toast.LENGTH_SHORT).show();

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

                        //db = dbhelper.getReadableDatabase();
                        //String insertQuery = "INSERT INTO meetings VALUES ('name', 'location', 'time', 'date', 'activity', 'duration');";
                        //db.execSQL(insertQuery);
                        //db.insert("meetings", null, values);

                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            Date inDate = dateFormat.parse(ed.getText().toString() + " " + et.getText().toString());
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(inDate);

                            Intent calintent = new Intent(Intent.ACTION_EDIT);
                            calintent.setType("vnd.android.cursor.item/event");
                            calintent.putExtra("beginTime", cal.getTimeInMillis());
                            calintent.putExtra("allDay", false);
                            calintent.putExtra("endTime", cal.getTimeInMillis() + (Integer.parseInt(edu.getText().toString()) * 60 * 60 * 1000));
                            calintent.putExtra("title", activity + " with " + en.getText().toString() + "!");
                            calintent.putExtra("eventLocation", location);
                            calintent.putExtra("description", "You're meeting with " + name + " at " + time + " on " + date + " for " + activity + ".");
                            calintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            calintent.putExtra("EXIT", true);

                            startActivityForResult(calintent, START_CAL);
                            //Toast.makeText(this, "Calendar has been exited!", Toast.LENGTH_SHORT).show();
                            //finish();
                            } catch (ParseException e) {
                            Toast.makeText(this, "Unexpected error", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Select a later time!", Toast.LENGTH_SHORT).show();
                    }

                } else if (dateFragment == null && timeFragment == null) {
                    Toast.makeText(this, "Select a time and date!", Toast.LENGTH_SHORT).show();
                } else if (!isValid(edu)) {
                    Toast.makeText(this, "Enter a duration less than !", Toast.LENGTH_SHORT).show();
                } else if (!isValidNum(edu)){
                    Toast.makeText(this, "Enter a duration!", Toast.LENGTH_SHORT).show();
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


