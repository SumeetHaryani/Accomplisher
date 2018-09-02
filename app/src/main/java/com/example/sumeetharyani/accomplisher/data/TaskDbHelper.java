package com.example.sumeetharyani.accomplisher.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TaskDbHelper extends SQLiteOpenHelper {
    public static String TAG = TaskDbHelper.class.getName();
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "accomplisher.db";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TASK_TABLE = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COLUMN_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION + " TEXT, " +
                TaskContract.TaskEntry.COLUMN_TASK_CATEGORY + " INTEGER NOT NULL DEFAULT  0, " +
                TaskContract.TaskEntry.COLUMN_TASK_DATE + " INTEGER NOT NULL, " +
                TaskContract.TaskEntry.COLUMN_TASK_PRIORITY + " INTEGER NOT NULL DEFAULT 1, " +
                TaskContract.TaskEntry.COLUMN_TASK_COLOR + " INTEGER DEFAULT 0, " +
                TaskContract.TaskEntry.COLUMN_TASK_STATUS + " INTEGER);";
        Log.d(TAG, SQL_CREATE_TASK_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
