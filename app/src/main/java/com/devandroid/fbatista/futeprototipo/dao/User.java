package com.devandroid.fbatista.futeprototipo.dao;

import android.graphics.Bitmap;

import com.devandroid.fbatista.futeprototipo.Keys;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{

    private String idUser;
    private String name;
    private String email;
    private Long score;

    public User(){}


    public User(String idUser, String name, String email){
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.score = 0L;
    }

    public void create(){
        final DatabaseReference dbRefUser = ConfigFirebase.getFirebaseDatabase()
            .child(Keys.KEY_USERS).child(this.idUser);
        dbRefUser.setValue(this);


        final List<ParticipationChallenge> partList = new ArrayList<>();
        final List<Achievement> achList = new ArrayList<>();

        final DatabaseReference dbRefChallenges = ConfigFirebase.getFirebaseDatabase().child(Keys.KEY_CHALLENGES);
        final DatabaseReference dbRefAchievements = ConfigFirebase.getFirebaseDatabase().child(Keys.KEY_ACHIEVEMENTS);
        dbRefChallenges.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    ParticipationChallenge participationChallenge = data.getValue(ParticipationChallenge.class);
                    participationChallenge.setStatus(ParticipationChallenge.STATUS_NOT_STARTED);
                    partList.add(participationChallenge);
                }

                for(ParticipationChallenge part : partList){
                    dbRefUser.child(Keys.KEY_USER_PARTICIPATION).child(part.getIdChallenge())
                            .setValue(part);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbRefAchievements.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Achievement achievement = data.getValue(Achievement.class);
                    achList.add(achievement);
                }

                for(Achievement ach : achList){
                    dbRefUser.child(Keys.KEY_ACHIEVEMENTS).child(ach.getId()).setValue(ach);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
