package com.example.vanna.attendance2.history;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vanna on 9/8/16.
 */

public class Attendance {

    private String date;
    private String time;
    private Status status;

    public Attendance(String date, String time, Status status) {
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public static Attendance fromJsonObject(JSONObject jsonObject){
        try {
            String date = jsonObject.getString("date");
            String time = jsonObject.getString("time");
            int status = jsonObject.getInt("status");
            Attendance attendance = new Attendance(date, time, Status.fromOrdinal(status));
            return attendance;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {

        Present, Absent, Permission;

        public static Status fromOrdinal(int ordinal){
            if(ordinal == Present.ordinal()){
                return Present;
            } else if(ordinal == Permission.ordinal()){
                return Permission;
            }else{
                return Absent;
            }
        }

    }

}
