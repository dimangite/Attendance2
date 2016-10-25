package com.example.vanna.attendance2.sqlite;

/**
 * Attendance2
 * Created by leapkh on 25/10/16.
 */

public class DBSchema {

    public static final String DB_NAEM = "ckcc.sqlite";
    public static final int DB_VERSION = 1;

    public static String getTablesCreationSql() {
        String sql = "create table " + DBSchema.AttendanceTable.TABLE_NAME + "(" +
                DBSchema.AttendanceTable.FIELD_ID + " integer primary key autoincrement, " +
                DBSchema.AttendanceTable.FIELD_DATE + " text, " +
                DBSchema.AttendanceTable.FIELD_TIME + " text, " +
                DBSchema.AttendanceTable.FIELD_STATUS + " integer)";
        return sql;
    }

    public interface AttendanceTable {
        String TABLE_NAME = "tblAttendance";
        String FIELD_ID = "_id";
        String FIELD_DATE = "_date";
        String FIELD_TIME = "_time";
        String FIELD_STATUS = "_status";
    }

}
