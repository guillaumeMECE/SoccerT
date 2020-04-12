package com.ece.soccert.database.model;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.sql.Blob;


public class User {

    public static final String TABLE_NAME = "user";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_PHOTO = "photo";

    private int id;
    private String name;
    private String surname;
    private byte[] photo;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_SURNAME + " TEXT,"
                    + COLUMN_PHOTO + " IMAGE"
                    + ")";

   /* public User(int anInt, String string, String cursorString, byte[] blob) {
    }*/


    public User(int id, String name, String surname, byte[] photo) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.photo = photo;
    }

    //GET
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public byte[] getPhoto() {
        return photo;
    }


    //SET
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }




}

