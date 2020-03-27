package com.ece.soccert.database.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {
    public static final String TABLE_NAME = "results";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEAM1 = "team1";
    public static final String COLUMN_SCORET1 = "scoret1";
    public static final String COLUMN_TEAM2 = "team2";
    public static final String COLUMN_SCORET2 = "scoret2";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String[] teams;
    private int[] scores;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TEAM1 + " TEXT,"
                    + COLUMN_SCORET1 + " INTEGER,"
                    + COLUMN_TEAM2 + " TEXT,"
                    + COLUMN_SCORET2 + " INTEGER,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Result() {
    }

    public Result(int id, String[] teams,int[] scores, String timestamp) {
        this.id = id;
        this.teams = teams;
        this.scores = scores;
    }
    public Result(int id, String team1,int score1,String team2,int score2, String timestamp) {
        this.id = id;
        this.teams = new String[]{team1, team2};
        this.scores = new int[]{score1, score2};
        this.timestamp=timestamp;
    }



    public int getId() {
        return id;
    }

    public int[] getScores() {
        return scores;
    }

    public String[] getTeams() {
        return teams;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public void setTeams(String[] teams) {
        this.teams = teams;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Result(Parcel parcel){
        this.id = parcel.readInt();
        this.teams = parcel.createStringArray();
        this.scores = parcel.createIntArray();
        this.timestamp=parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeStringArray(teams);
        dest.writeIntArray(scores);
        dest.writeString(timestamp);
    }
    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {

        @Override
        public Result createFromParcel(Parcel parcel) {
            return new Result(parcel);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[0];
        }
    };
}
