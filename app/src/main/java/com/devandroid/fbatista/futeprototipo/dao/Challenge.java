package com.devandroid.fbatista.futeprototipo.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class Challenge implements Serializable {

    private String title;
    private int level;
    private String description;

    public Challenge(String title, int level) {
        this.title = title;
        this.level = level;
    }

    public Challenge(String title, int level, String description){
        this(title, level);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
