package com.devandroid.fbatista.futeprototipo.dao;

import com.devandroid.fbatista.futeprototipo.Keys;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class ParticipationChallenge extends Challenge implements Serializable {

    public static final String STATUS_WAITING_APPROVEMENT = "STATUS_WAITING_APPROVEMENT";
    public static final String STATUS_REJECTED = "STATUS_REJECTED";
    public static final String STATUS_APPROVED = "STATUS_APPROVED";
    public static final String STATUS_NOT_STARTED = "STATUS_NOT_STARTED";

    //This property is only used when it's needed to get the name of the user
    //It is only used in the ApprovementChallengeAdapter and Approvement Activity so far
    private String nomeUser;

    private String idUser;
    private String idChallenge;
    private String videoPath;
    private String status;
    private String description;

    public ParticipationChallenge() {
    }

    public ParticipationChallenge(String idUser, String idChallenge, String videoPath) {
        this.idUser = idUser;
        this.idChallenge = idChallenge;
        this.status = STATUS_NOT_STARTED;
        this.videoPath = videoPath;

    }



    public void saveParticipation(){
        this.status = "STATUS_WAITING_APPROVEMENT";
        DatabaseReference dbRef = ConfigFirebase.getFirebaseDatabase().child(Keys.KEY_USERS)
                .child(idUser).child(Keys.KEY_USER_PARTICIPATION).child(idChallenge);
        dbRef.setValue(this);

    }

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(String idChallenge) {
        this.idChallenge = idChallenge;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
