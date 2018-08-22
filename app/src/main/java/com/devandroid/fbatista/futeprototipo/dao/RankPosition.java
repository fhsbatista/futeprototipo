package com.devandroid.fbatista.futeprototipo.dao;

public class RankPosition {

    private int mRank;
    private String mName;
    private Long mScore;

    public RankPosition(int rank, String name, Long score) {
        mRank = rank;
        mName = name;
        mScore = score;
    }

    public int getRank() {
        return mRank;
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getScore() {
        return mScore;
    }

    public void setScore(Long score) {
        mScore = score;
    }
}
