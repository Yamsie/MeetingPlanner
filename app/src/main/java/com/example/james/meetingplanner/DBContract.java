package com.example.james.meetingplanner;

import android.provider.BaseColumns;

public class DBContract {
    public static final String DB_NAME = "meetings_planner";
    public static final int DB_VERSION = 1;

    private DBContract() {} //private constructor so it cannot be called accidentally

    public static abstract class EntryToTableFriends implements BaseColumns {
        public static final String TABLE = "friends";
        public static final String COL7 = "name";
    }

    public static abstract class EntryToTableLocations implements BaseColumns {
        public static final String TABLE = "locations";
        public static final String COL6 = "places";
    }

    public static abstract class EntryToTableMeetings implements BaseColumns {
        public static final String TABLE = "meetings";
        public static final String COL1 = "friend";
        public static final String COL2 = "location";
        public static final String COL3 = "time";
        public static final String COL4 = "date";
        public static final String COL5 = "activity";
        public static final String COL9 = "duration";
    }

    public static abstract class EntryToTableActivities implements BaseColumns{
        public static final String TABLE = "activity";
        public static final String COL8 = "to-do";
    }
}