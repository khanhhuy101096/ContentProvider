package com.example.khanhhuy.myapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by yeu_thuong on 10/16/2017.
 */

public class StudentsProvider extends ContentProvider {
    private static final String PROVIDER_NAME = "com.example.khanhhuy.myapplication.StudentsProvider";
    private static final String URL = "content://" + PROVIDER_NAME + "/students";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String GRADE = "grade";

    private SQLiteDatabase db;
    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    private static final int STUDENTS = 1;
    private static final int STUDENT_ID = 2;

    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(StudentContract.StudentEntry.TABLE_NAME);
            switch (uriMatcher.match(uri)) {
                case STUDENTS:
                    queryBuilder.setProjectionMap(STUDENTS_PROJECTION_MAP);
                    break;
                case STUDENT_ID:
                    queryBuilder.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                    break;
                default:
            }
            if (sortOrder == null || sortOrder == ""){
                sortOrder = NAME;
            }
            Cursor c = queryBuilder.query(db,	projection,	selection,
                    selectionArgs,null, null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case STUDENTS:
                return "vnd.android.cursor.dir/vnd.example.students";
            case STUDENT_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(	StudentContract.StudentEntry.TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case STUDENTS:
                count = db.delete(StudentContract.StudentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case STUDENT_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( StudentContract.StudentEntry.TABLE_NAME, _ID +  " = " + id +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            int count = 0;
            switch (uriMatcher.match(uri)) {
                case STUDENTS:
                    count = db.update(StudentContract.StudentEntry.TABLE_NAME, values, selection, selectionArgs);
                    break;
                case STUDENT_ID:
                    count = db.update(StudentContract.StudentEntry.TABLE_NAME, values,
                            _ID + " = " + uri.getPathSegments().get(1) +
                                    (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri );
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
    }
}
