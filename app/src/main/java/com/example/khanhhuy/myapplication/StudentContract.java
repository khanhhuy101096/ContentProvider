package com.example.khanhhuy.myapplication;

import android.provider.BaseColumns;

/**
 * Created by yeu_thuong on 10/16/2017.
 */

public class StudentContract {
    public StudentContract(){
    }

    public static class StudentEntry implements BaseColumns {
        public static final String TABLE_NAME="students";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_GRADE="grade";;
    }
}
