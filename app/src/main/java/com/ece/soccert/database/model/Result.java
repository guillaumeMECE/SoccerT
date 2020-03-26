package com.ece.soccert.database.model;

public class Result {
    public static final String TABLE_NAME = "results";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEAM1 = "team1";
    public static final String COLUMN_SCORET1 = "scoret1";
    public static final String COLUMN_TEAM2 = "team2";
    public static final String COLUMN_SCORET2 = "scoret2";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String[] teams;
    private Integer[] scores;
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

    public Result(int id, String[] teams,Integer[] scores, String timestamp) {
        this.id = id;
        this.teams = teams;
        this.scores = scores;
    }
    public Result(int id, String team1,Integer score1,String team2,Integer score2, String timestamp) {
        this.id = id;
        this.teams = new String[]{team1, team2};
        this.scores = new Integer[]{score1, score2};
    }

    public int getId() {
        return id;
    }

    public Integer[] getScores() {
        return scores;
    }

    public String[] getTeams() {
        return teams;
    }

    public void setScores(Integer[] scores) {
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
}
