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

    private String idUser;
    private String idChallenge;
    private String videoPath;
    private String status;

    public ParticipationChallenge() {
    }

    public ParticipationChallenge(String idUser, String idChallenge, String videoPath) {
        this.idUser = idUser;
        this.idChallenge = idChallenge;
        this.status = "STATUS_WAITING_APPROVEMENT";
        this.videoPath = videoPath;
    }

    public void saveParticipation(){

        DatabaseReference dbRef = ConfigFirebase.getFirebaseDatabase().child(Keys.KEY_USERS)
                .child(idUser).child(Keys.KEY_USER_PARTICIPATION).child(idChallenge);
        dbRef.setValue(this);

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
