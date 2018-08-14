package com.devandroid.fbatista.futeprototipo.dao;

import java.io.Serializable;

public class ParticipationChallenge implements Serializable {

    public static final String STATUS_WAITING_APPROVEMENT = "Aguardando Aprovaçao";
    public static final String STATUS_REJECTED = "Participaçao nao Aprovada";
    public static final String STATUS_APPROVED = "Participaçao Aprovada";

    private String idUser;
    private String idChallenge;
    private String videoPath;
    private String status;

    public ParticipationChallenge() {
    }

    public ParticipationChallenge(String idUser, String idChallenge, String status, String videoPath) {
        this.idUser = idUser;
        this.idChallenge = idChallenge;
        this.status = status;
        this.videoPath = videoPath;
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
