package edu.ucdenver.benjaminyee.todolistapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager {
    private SQLiteDatabase db;

    public DataManager(Context context) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public Cursor selectAll() {
        Cursor cursor = null;
        String query = "select * from task order by date";
        try {
            cursor = db.rawQuery(query, null);
        } catch (Exception e) {
            Log.i("info", "in DataManager selectAll method");
            Log.i("info", e.getMessage());
        }
        Log.i("info", "loaded data" + cursor.getCount());
        return cursor;
    }
    public void insert(String date, String title, String time, String description, String status){
        String query = "insert into task" +
                "(date, title, time, description, status) values " +
                "( '"+ date +"', '" + title + "', '" + time + "', '" + description + "', '" + status + "')";
        try {
            db.execSQL(query);
        }catch (SQLException e){
            Log.i("info", "In DataManager insert method");
            Log.i("info", e.getMessage());
        }
        Log.i("info", "Added new task " + title);
    }

    public void Delete(String title) {
        try {
            db.execSQL("DElETE From task Where title =" + "'" + title + "'");
        } catch (Exception e) {
            Log.i("info", "Delete method from dataManager delete Method");
            Log.i("info", e.getMessage());
        }
    }

    private class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper(Context context) {
            super(context, "task_list", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String newTable = "create table task ("
                    + "_id integer primary key autoincrement not null, "
                    + "date text not null,"
                    + "title text not null, "
                    + "time text, "
                    + "description text, "
                    + "status text)";


            try {
                db.execSQL(newTable);
            } catch (SQLException e) {
                Log.i("info", "In MySQLiteOpenHelper class onCreate method");
                Log.i("info", e.getMessage());
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            //No code needed
        }
    }
}
