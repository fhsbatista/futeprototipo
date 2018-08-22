package com.devandroid.fbatista.futeprototipo.helper;

import com.devandroid.fbatista.futeprototipo.Keys;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScoreHelper {


    private static FirebaseAuth mAuth;
    private static DatabaseReference mRef;

    //Variable which represents the current user's score
    private static Long userScore;



    public static final String SCORE_FIRST_SENT_CHALLENGE = "first_sent_challenge";

    public static void updateScore(final String key){

        mAuth = ConfigFirebase.getAuth();
        String idUser = mAuth.getCurrentUser().getUid();

        mRef = ConfigFirebase.getFirebaseDatabase().child(Keys.KEY_USERS)
                .child(idUser).child(Keys.KEY_SCORE);

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userScore = dataSnapshot.getValue(Long.class);
                getScoreToUpdate(key);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public static void getScoreToUpdate(String key){

        switch (key){

            case SCORE_FIRST_SENT_CHALLENGE :
                scoreFirstSentChallenge();
                break;


        }




    }

    private static void scoreFirstSentChallenge() {

        //Score that will be increased for the user
        Long scoreToIncrease = 10L;

        //Variable which represents the new score of the user
        Long updatedScore = userScore + scoreToIncrease;

        //Update the score of the user in the database
        mRef.setValue(updatedScore);

    }


}
