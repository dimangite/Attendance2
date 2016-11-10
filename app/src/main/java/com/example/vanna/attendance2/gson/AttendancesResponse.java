package com.example.vanna.attendance2.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Attendance2
 * Created by leapkh on 27/10/16.
 */

public class AttendancesResponse {

    public int responseCode;
    public String responseMessage;

    @SerializedName("payload")
    public List<AttendanceResponse> attendances;

    public class AttendanceResponse{
        public String date;
        public String time;
        public int status;
    }

}
