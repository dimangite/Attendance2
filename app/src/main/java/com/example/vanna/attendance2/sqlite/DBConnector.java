package com.example.vanna.attendance2.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vanna.attendance2.history.Attendance;

public class DBConnector extends SQLiteOpenHelper {

    private static DBConnector INSTANCE;

    public static DBConnector getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DBConnector(context);
        }
        return INSTANCE;
    }

    private DBConnector(Context context) {
        super(context, DBSchema.DB_NAEM, null, DBSchema.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBSchema.getTablesCreationSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO - Alter DB on upgrade
    }

    public boolean insertAttendance(Attendance attendance) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBSchema.AttendanceTable.FIELD_DATE, attendance.getDate());
        row.put(DBSchema.AttendanceTable.FIELD_TIME, attendance.getTime());
        row.put(DBSchema.AttendanceTable.FIELD_STATUS, attendance.getStatus().ordinal());
        long newId = db.insert(DBSchema.AttendanceTable.TABLE_NAME, null, row);
        return (newId != -1);
    }

    public Attendance[] getAttendances() {
        SQLiteDatabase db = getReadableDatabase();
        String[] selectedColumns = {DBSchema.AttendanceTable.FIELD_ID, DBSchema.AttendanceTable.FIELD_DATE, DBSchema.AttendanceTable.FIELD_TIME, DBSchema.AttendanceTable.FIELD_STATUS};
        String sortBy = DBSchema.AttendanceTable.FIELD_ID + " desc";
        String selection = null;
        //String selection = DBSchema.AttendanceTable.FIELD_ID + " < ?";
        String[] selectionArgs = null;
        //String[] selectionArgs = {"3"};
        Cursor cursor = db.query(DBSchema.AttendanceTable.TABLE_NAME, selectedColumns, selection, selectionArgs, null, null, sortBy);
        //Cursor cursor = db.rawQuery("select * from tblAttendance where _id < 3", null);
        Attendance[] attendances = new Attendance[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            // int id = cursor.getInt(0);
            String date = cursor.getString(1);
            String time = cursor.getString(2);
            int statusOrdinal = cursor.getInt(3);
            Attendance.Status status = Attendance.Status.fromOrdinal(statusOrdinal);
            Attendance attendance = new Attendance(date, time, status);
            attendances[i] = attendance;
            i++;
        }
        return attendances;
    }

}


