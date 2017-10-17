package com.example.khanhhuy.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yeu_thuong on 10/16/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "college";
    private static final int DATA_VERSION = 1;
    private static final String CREATE_DB_TABLE = "CREATE TABLE "
            + StudentContract.StudentEntry.TABLE_NAME
            + " ("
            + StudentContract.StudentEntry._ID
            + " INTEGER PRIMARY KEY,"
            + StudentContract.StudentEntry.COLUMN_NAME
            + " TEXT,"
            + StudentContract.StudentEntry.COLUMN_GRADE
            + " TEXT)";

    private static final String SQL_DELETE_CONTACTS =
            "DROP TABLE IF EXISTS " + StudentContract.StudentEntry.TABLE_NAME;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_CONTACTS);
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
    }
}
