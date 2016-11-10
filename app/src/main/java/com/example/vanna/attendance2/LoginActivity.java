package com.example.vanna.attendance2;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.vanna.attendance2.history.Attendance;
import com.example.vanna.attendance2.history.AttenddanceHistoryActivity;
import com.example.vanna.attendance2.sqlite.DBConnector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView facebookButton = (TextView) findViewById(R.id.facebookButton);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/icomoon.ttf");
        facebookButton.setTypeface(typeface);
        facebookButton.setText("\ue901");

        facebookButton.setOnClickListener(this);

        /*
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Insert attendance
                Date date = new Date();
                String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
                String timeStr = new SimpleDateFormat("HH:mm:ss").format(date);
                Attendance.Status status = Attendance.Status.Present;
                Attendance attendance = new Attendance(dateStr, timeStr, status);
                DBConnector.getInstance(LoginActivity.this).insertAttendance(attendance);

                Intent intent = new Intent(getApplicationContext(), AttenddanceHistoryActivity.class);
                startActivity(intent);
            }
        });
        */

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.facebookButton){
            // Insert attendance
            Date date = new Date();
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
            String timeStr = new SimpleDateFormat("HH:mm:ss").format(date);
            Attendance.Status status = Attendance.Status.Present;
            Attendance attendance = new Attendance(dateStr, timeStr, status);
            DBConnector.getInstance(LoginActivity.this).insertAttendance(attendance);

            Intent intent = new Intent(getApplicationContext(), AttenddanceHistoryActivity.class);
            startActivity(intent);
        }
    }


}
