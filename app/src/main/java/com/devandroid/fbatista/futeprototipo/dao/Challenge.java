package com.devandroid.fbatista.futeprototipo.dao;

public class Challenge {

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
