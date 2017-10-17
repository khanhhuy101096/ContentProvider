package com.example.khanhhuy.myapplication;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddName(View view) {
        ContentValues values = new ContentValues();
        values.put(StudentContract.StudentEntry.COLUMN_NAME,
                ((EditText)findViewById(R.id.editTextName)).getText().toString());

        values.put(StudentContract.StudentEntry.COLUMN_GRADE,
                ((EditText)findViewById(R.id.editTextGrade)).getText().toString());

        Uri uri = ContentUris.withAppendedId(
                StudentsProvider.CONTENT_URI, 1);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }
}
