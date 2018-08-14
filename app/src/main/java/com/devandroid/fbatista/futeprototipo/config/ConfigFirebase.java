package com.devandroid.fbatista.futeprototipo.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {

    private static FirebaseAuth mAuth;
    private static DatabaseReference mRef;

    public static FirebaseAuth getAuth(){

        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public static DatabaseReference getFirebaseDatabase(){

        mRef = FirebaseDatabase.getInstance().getReference();
        return mRef;

    }







}
