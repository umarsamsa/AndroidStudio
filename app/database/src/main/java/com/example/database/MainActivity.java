package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this);

        dbHelper.deleteAllData();

        dbHelper.insertData("Umar", 25);
        dbHelper.insertData("Alikhan", 24);

        getData();

        dbHelper.updateData(2, "Caburnee", 26);

        getData();

        dbHelper.deleteData(2);

        getData();

        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", "Umar");
        editor.putInt("age", 26);
        editor.putBoolean("is_student", true);
        editor.commit();

        String name = prefs.getString("name", "");
        int age = prefs.getInt("age", 0);
        boolean isStudent = prefs.getBoolean("is_student", false);
        Log.d("MainActivity", name);
        Log.d("MainActivity", String.valueOf(age));
        Log.d("MainActivity", String.valueOf(isStudent));

        editor.remove("name");
        editor.apply();

        name = prefs.getString("name", "");
        age = prefs.getInt("age", 0);
        isStudent = prefs.getBoolean("is_student", false);
        Log.d("MainActivity", name);
        Log.d("MainActivity", String.valueOf(age));
        Log.d("MainActivity", String.valueOf(isStudent));

        editor.clear();
        editor.commit();

        name = prefs.getString("name", "");
        age = prefs.getInt("age", 0);
        isStudent = prefs.getBoolean("is_student", false);
        Log.d("MainActivity", name);
        Log.d("MainActivity", String.valueOf(age));
        Log.d("MainActivity", String.valueOf(isStudent));
    }

    @SuppressLint("Range")
    private void getData() {
        Cursor cursor = dbHelper.getData();

        Log.d("MainActivity", "========= START =========");
        if (cursor.getCount() > 0) {
            // Loop through the cursor to retrieve the data
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));

                Log.d("MainActivity", "Record retrieved with ID: " + id + ", name: " + name + ", age: " + age);
            }
        } else {
            Log.d("MainActivity", "No records found.");
        }

        Log.d("MainActivity", "========= END =========");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database helper
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}