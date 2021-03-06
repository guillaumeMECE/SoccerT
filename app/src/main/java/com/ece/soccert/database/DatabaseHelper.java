package com.ece.soccert.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.ContactsContract;
import android.util.Log;

import com.ece.soccert.database.model.Result;
import com.ece.soccert.database.model.Step;
import com.ece.soccert.database.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "soccert_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Result.CREATE_TABLE);
        db.execSQL(Step.CREATE_TABLE);
        db.execSQL(User.CREATE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Result.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Step.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);


        // Create tables again
        onCreate(db);
    }

    /**
     * Insert a new STEP in SQLITE
     * @param idresult
     * @param name
     * @param team
     * @return
     */
    public long insertStep(int idresult,String name,int team) {
        Log.d("TAG/DB", "insertStep: "+idresult+name+team);
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Step.COLUMN_IDRESULT, idresult);
        values.put(Step.COLUMN_NAME, name);
        values.put(Step.COLUMN_TEAM, team);

        // insert row
        long id = db.insert(Step.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<Step> getStepHistory(int idresult) {
        List<Step> steps = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Step.TABLE_NAME + " WHERE " +
                Step.COLUMN_IDRESULT + "=" + idresult + " ORDER BY " +
                Step.COLUMN_TIMESTAMP + " DESC";

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                // prepare Step object
                Step step = new Step(
                        cursor.getInt(cursor.getColumnIndex(Step.COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndex(Step.COLUMN_IDRESULT)),
                        cursor.getString(cursor.getColumnIndex(Step.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(Step.COLUMN_TEAM)),
                        cursor.getString(cursor.getColumnIndex(Step.COLUMN_TIMESTAMP)));
                steps.add(step);
            }while (cursor.moveToNext());
        }

        // close the db connection
        cursor.close();

        return steps;
    }

    public int[] getStepStrike(int idresult) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + Step.TABLE_NAME + " WHERE (" +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"GOAL\" AND "+
                Step.COLUMN_TEAM+"=0) OR (" +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"LOST STRIKE\" AND " +
                Step.COLUMN_TEAM+"=0)";

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        int yT1 = cursor.getCount();
        cursor.close();

        selectQuery = "SELECT * FROM " + Step.TABLE_NAME + " WHERE (" +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"GOAL\" AND "+
                Step.COLUMN_TEAM+"=1) OR (" +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"LOST STRIKE\" AND " +
                Step.COLUMN_TEAM+"=1)";

        Cursor cursor1 = db.rawQuery(selectQuery, null);

        if (cursor1 != null)
            cursor1.moveToFirst();

        int yT2 = cursor1.getCount();
        cursor1.close();

        return new int[]{yT1, yT2};
    }

    public int[] getStepFault(int idresult) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + Step.TABLE_NAME + " WHERE (" +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"RED CARD\" AND "+
                Step.COLUMN_TEAM+"=0) OR (" +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"YELLOW CARD\" AND " +
                Step.COLUMN_TEAM+"=0) OR (" +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"FAULT\" AND "+
                Step.COLUMN_TEAM+"=0)";

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        int yT1 = cursor.getCount();
        cursor.close();

        selectQuery = "SELECT * FROM " + Step.TABLE_NAME + " WHERE (" +
                        Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                        Step.COLUMN_NAME + "=\"RED CARD\" AND "+
                        Step.COLUMN_TEAM+"=1) OR (" +
                        Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                        Step.COLUMN_NAME + "=\"YELLOW CARD\" AND " +
                        Step.COLUMN_TEAM+"=1) OR (" +
                        Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                        Step.COLUMN_NAME + "=\"FAULT\" AND "+
                        Step.COLUMN_TEAM+"=1)";

        Cursor cursor1 = db.rawQuery(selectQuery, null);

        if (cursor1 != null)
            cursor1.moveToFirst();

        int yT2 = cursor1.getCount();
        cursor1.close();

        return new int[]{yT1, yT2};
    }

    public int[] getStepYellow(int idresult) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + Step.TABLE_NAME + " WHERE " +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"YELLOW CARD\" AND "+Step.COLUMN_TEAM+"=0";

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        int yT1 = cursor.getCount();
        cursor.close();

        selectQuery = "SELECT * FROM " + Step.TABLE_NAME + " WHERE " +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"YELLOW CARD\" AND "+Step.COLUMN_TEAM+"=1";

        Cursor cursor1 = db.rawQuery(selectQuery, null);

        if (cursor1 != null)
            cursor1.moveToFirst();

        int yT2 = cursor1.getCount();
        cursor1.close();

        return new int[]{yT1, yT2};
    }

    public int[] getStepRed(int idresult) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + Step.TABLE_NAME + " WHERE " +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"RED CARD\" AND "+Step.COLUMN_TEAM+"=0";

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        int yT1 = cursor.getCount();
        cursor.close();

        selectQuery = "SELECT * FROM " + Step.TABLE_NAME + " WHERE " +
                Step.COLUMN_IDRESULT + "=" + idresult + " AND " +
                Step.COLUMN_NAME + "=\"RED CARD\" AND "+Step.COLUMN_TEAM+"=1";

        Cursor cursor1 = db.rawQuery(selectQuery, null);

        if (cursor1 != null)
            cursor1.moveToFirst();

        int yT2 = cursor1.getCount();
        cursor1.close();

        return new int[]{yT1, yT2};
    }

    /**
     * RESULT CRUD
     * @param teams
     * @param scores
     * @return
     */

    public long insertResult(String[] teams,int[] scores,double[] pos) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Result.COLUMN_TEAM1, teams[0]);
        values.put(Result.COLUMN_TEAM2, teams[1]);
        values.put(Result.COLUMN_SCORET1, scores[0]);
        values.put(Result.COLUMN_SCORET2, scores[1]);
        values.put(Result.COLUMN_LAT, pos[0]);
        values.put(Result.COLUMN_LONG, pos[1]);

        // insert row
        long id = db.insert(Result.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Result getResult(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Result.TABLE_NAME,
                new String[]{Result.COLUMN_ID, Result.COLUMN_TEAM1,Result.COLUMN_SCORET1,Result.COLUMN_TEAM2,Result.COLUMN_SCORET2, Result.COLUMN_TIMESTAMP,Result.COLUMN_LAT,Result.COLUMN_LONG},
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
                cursor.getString(cursor.getColumnIndex(Result.COLUMN_TIMESTAMP)),
                cursor.getDouble(cursor.getColumnIndex(Result.COLUMN_LAT)),
                cursor.getDouble(cursor.getColumnIndex(Result.COLUMN_LONG)));

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
                    result.setScores(new int[]{cursor.getInt(cursor.getColumnIndex(Result.COLUMN_SCORET1)), cursor.getInt(cursor.getColumnIndex(Result.COLUMN_SCORET2))});
                    result.setTimestamp(cursor.getString(cursor.getColumnIndex(Result.COLUMN_TIMESTAMP)));
                    result.setLatitude(cursor.getDouble(cursor.getColumnIndex(Result.COLUMN_LAT)));
                    result.setLongitude(cursor.getDouble(cursor.getColumnIndex(Result.COLUMN_LONG)));

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

        return count;
    }

    public int updateResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Result.COLUMN_SCORET1, result.getScores()[0]);
        values.put(Result.COLUMN_SCORET2, result.getScores()[1]);

        // updating row
        return db.update(Result.TABLE_NAME, values, Result.COLUMN_ID + " = ?",
                new String[]{String.valueOf(result.getId())});
    }

    public void deleteResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Result.TABLE_NAME, Result.COLUMN_ID + " = ?",
                new String[]{String.valueOf(result.getId())});
        db.close();
    }

    /**
     * USER CRUD
     * @param name
     * @param surname
     * @param photo
     * @return
     */



    public long insertUser(String name, String surname, byte[] photo) {
        // get writable database as we want to write data
       /* SQLiteDatabase db = this.getWritableDatabase();

        String sql = "INSERT INTO  User   VALUES (NULL, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, surname);
        statement.bindBlob(3, photo);

        statement.executeInsert();*/

        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(User.COLUMN_NAME, name);
        values.put(User.COLUMN_SURNAME, surname);
        values.put(User.COLUMN_PHOTO, photo);

        // insert row
        long id = db.insert(User.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;



    }


    public Cursor getUser(String sql) {

        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);

    }


    public void updateUser(String name, String surname, byte[] photo, int id){
        SQLiteDatabase db = getWritableDatabase();

        String sql = "UPDATE USER SET nom = ?, prenom = ?, photo = ? WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, surname);
        statement.bindBlob(3, photo);
        statement.bindDouble(4, (double)id);

        statement.execute();
        db.close();

    }

    public  void deleteUser(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = "DELETE FROM USER WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        db.close();
    }








}
