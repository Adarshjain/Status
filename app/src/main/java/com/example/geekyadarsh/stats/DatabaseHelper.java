package com.example.geekyadarsh.stats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Geeky Adarsh on 23/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
        private static final String DATABASE_NAME = "notesDB.db";
        private static final String TABLE_NAME = "Notes";
        private static final String UID = "_id";
        private static final String TIME = "time";
        private static final String STATUS = "status";
        private static final int DATABASE_VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE Notes (_id INTEGER PRIMARY KEY AUTOINCREMENT,time text,status text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Notes;");
            onCreate(db);
        }

        public long insert(String time, String status){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TIME,time);
            contentValues.put(STATUS, status);
            return db.insert(TABLE_NAME,null,contentValues);
        }

        public Cursor getData(){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res;
            res = db.rawQuery("SELECT * FROM Notes;",null);
            return res;
        }

        public void dropDB(){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS Notes");
            this.onCreate(db);
        }
}
