package com.chazuo.czlib.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author LiQiong
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "CZXY_db";
    private static final int version = 1;
    private static DatabaseHelper dbHelper;

    public static DatabaseHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
        }
        return dbHelper;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}