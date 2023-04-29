package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "my_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_AGE + " INTEGER);";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertData(String name, int age) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        long rowId = db.insert(TABLE_NAME, null, values);
        if (rowId != -1) {
            Log.d("MainActivity", "New record inserted with row ID: " + rowId);
        } else {
            Log.d("MainActivity", "Failed to insert new record.");
        }
        db.close();
        return rowId;
    }

    public int updateData(long id, String name, int age) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        int count = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return count;
    }

    public int deleteData(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        int count = db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
        return count;
    }

    public Cursor getData() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { COLUMN_ID, COLUMN_NAME, COLUMN_AGE };
        String sortOrder = COLUMN_NAME + " ASC";
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, sortOrder);
        return cursor;
    }

    public void deleteAllData() {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.close();
    }
}