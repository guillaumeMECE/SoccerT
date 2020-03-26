package com.ece.soccert.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.ece.soccert.database.model.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "results_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Result.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Result.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertResult(String[] teams,Integer[] scores) {
        long[] resultTeams = insertTeams(teams);
        Log.d("DB", "insertResult/insertTeams: "+ Arrays.toString(resultTeams));
        long[] resultScores = insertScores(scores);
        Log.d("DB", "insertResult/insertScores: "+ Arrays.toString(resultScores));
    }

    public long[] insertTeams(String[] teams) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Result.COLUMN_TEAM1, teams[0]);

        // insert row
        long id = db.insert(Result.TABLE_NAME, null, values);

        ContentValues values2 = new ContentValues();
        values2.put(Result.COLUMN_TEAM1, teams[1]);

        // insert row
        long id2 = db.insert(Result.TABLE_NAME, null, values2);

        // close db connection
        db.close();

        // return newly inserted row id
        return new long[]{id, id2};
    }

    public long[] insertScores(Integer[] scores) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Result.COLUMN_SCORET1, scores[0]);

        // insert row
        long id = db.insert(Result.TABLE_NAME, null, values);

        ContentValues values2 = new ContentValues();
        values2.put(Result.COLUMN_SCORET2, scores[1]);

        // insert row
        long id2 = db.insert(Result.TABLE_NAME, null, values2);

        // close db connection
        db.close();

        // return newly inserted row id
        return new long[]{id, id2};
    }

    public Result getResult(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Result.TABLE_NAME,
                new String[]{Result.COLUMN_ID, Result.COLUMN_TEAM1,Result.COLUMN_SCORET1,Result.COLUMN_TEAM2,Result.COLUMN_SCORET2, Result.COLUMN_TIMESTAMP},
                Result.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Result result = new Result(
                cursor.getInt(cursor.getColumnIndex(Result.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Result.COLUMN_TEAM1)),
                cursor.getInt(cursor.getColumnIndex(Result.COLUMN_SCORET1)),
                cursor.getString(cursor.getColumnIndex(Result.COLUMN_TEAM2)),
                cursor.getInt(cursor.getColumnIndex(Result.COLUMN_SCORET2)),
                cursor.getString(cursor.getColumnIndex(Result.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return result;
    }

    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Result.TABLE_NAME + " ORDER BY " +
                Result.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                result.setId(cursor.getInt(cursor.getColumnIndex(Result.COLUMN_ID)));
                result.setTeams(new String[]{cursor.getString(cursor.getColumnIndex(Result.COLUMN_TEAM1)), cursor.getString(cursor.getColumnIndex(Result.COLUMN_TEAM2))});
                result.setScores(new Integer[]{cursor.getInt(cursor.getColumnIndex(Result.COLUMN_SCORET1)), cursor.getInt(cursor.getColumnIndex(Result.COLUMN_SCORET2))});
                result.setTimestamp(cursor.getString(cursor.getColumnIndex(Result.COLUMN_TIMESTAMP)));

                results.add(result);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return results;
    }

    public int getResultsCount() {
        String countQuery = "SELECT  * FROM " + Result.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    /*public int updateResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Result.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }*/

    public void deleteNote(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Result.TABLE_NAME, Result.COLUMN_ID + " = ?",
                new String[]{String.valueOf(result.getId())});
        db.close();
    }
}
