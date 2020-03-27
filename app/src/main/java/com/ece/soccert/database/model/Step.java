package com.ece.soccert.database.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Step {
    public static final String TABLE_NAME = "step";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IDRESULT = "idresult";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TEAM = "team";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private int idresult;
    private String name;
    private int team;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_IDRESULT + " INTEGER,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_TEAM + " INTEGER,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Step() {
    }

    public Step(int id, int idresult, String name, int team, String timestamp) {
        this.id = id;
        this.idresult = idresult;
        this.name = name;
        this.team = team;
        this.timestamp=timestamp;
    }

    public int getId() {
        return id;
    }

    public int getIdresult() {
        return idresult;
    }

    public int getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setIdresult(int idresult) {
        this.idresult = idresult;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
