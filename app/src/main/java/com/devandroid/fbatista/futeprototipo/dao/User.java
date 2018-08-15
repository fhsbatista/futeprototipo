package com.devandroid.fbatista.futeprototipo.dao;

import com.devandroid.fbatista.futeprototipo.Keys;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class User implements Serializable{

    private String idUser;
    private String name;
    private String email;

    public User(){}


    public User(String idUser, String name, String email){
        this.idUser = idUser;
        this.name = name;
        this.email = email;
    }

    public void create(){
        DatabaseReference dbRef = ConfigFirebase.getFirebaseDatabase();
        dbRef.child(Keys.KEY_USERS).child(this.idUser).setValue(this);
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
