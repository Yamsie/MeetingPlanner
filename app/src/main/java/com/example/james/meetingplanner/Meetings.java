package com.example.james.meetingplanner;

public class Meetings {

    //This is the class for the Meetings object
    String tableName = "meetings";
    String friend;
    String location;
    String time;
    String date;
    String activity;
    String duration;
    String extra;

    public Meetings() {
    }

    public Meetings(String fr, String l, String t, String d, String a, String dur) {
        this.friend = fr;
        this.location = l;
        this.time = t;
        this.date = d;
        this.activity = a;
        this.duration = dur;
        this.extra= "";
    }

    public Meetings(String ex){
        this.extra = ex;
    }

    //getter and setter methods

    public void setFriend(String f){
        this.friend = f;
    }

    public void setLocation(String l){
        this.location = l;
    }

    public void setTime(String t){
        this.time = t;
    }

    public void setDate(String d){
        this.date = d;
    }

    public void setActivity(String a){
        this.activity = a;
    }

    public void setDuration(String dur){
        this.duration = dur;
    }

    public String getFriend(){
        return friend;
    }

    public String getLocation(){
        return location;
    }

    public String getTime(){
        return time;
    }

    public String getDate(){
        return date;
    }

    public String getActivity(){
        return activity;
    }

    public String getDuration(){ return duration; }
}
