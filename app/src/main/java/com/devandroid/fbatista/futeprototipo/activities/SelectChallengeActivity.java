package com.devandroid.fbatista.futeprototipo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.RecyclerItemClickListener;
import com.devandroid.fbatista.futeprototipo.adapters.SelectChallengeAdapter;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.devandroid.fbatista.futeprototipo.dao.Challenge;
import com.devandroid.fbatista.futeprototipo.dao.ChallengeForShowing;
import com.devandroid.fbatista.futeprototipo.dao.ParticipationChallenge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectChallengeActivity extends AppCompatActivity {

    public static final String KEY_CHALLENGE = "challenge";
    private static final String TAG = SelectChallengeActivity.class.getSimpleName();
    private static final String KEY_CHALLENGES = "challenges";
    private static final String KEY_PARTICIPATIONS = "participations";

    private RecyclerView mRecyclerView;
    private SelectChallengeAdapter adapter;
    private ArrayList<ChallengeForShowing> challenges;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference dbRefUser;
    private DatabaseReference dbRefChallenges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_challenge);

        //Get the current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        //Initialize the recycler view
        mRecyclerView = findViewById(R.id.rv);

        //Set the layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        //Set the list of challenges
        challenges = new ArrayList<>();

        dbRefChallenges = FirebaseDatabase.getInstance().getReference().child("challenges");

        dbRefChallenges.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Download the participations that the user has participated in order to fill the status field in the recycler view
                HashMap<String, ParticipationChallenge> participations = downloadParticipation();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ChallengeForShowing challenge = data.getValue(ChallengeForShowing.class);
                    challenge.setIdChallenge(data.getKey());

                    //The method below will verify wheter the user has participate of the challenge
                    //If so, the status in the recycler view will be customized
                    if (verifyUserParticipation(data, participations)) {
                        String status = participations.get(data.getKey().toString()).getStatus();
                        challenge.setStatus(status);
                    }
                    challenges.add(challenge);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Set the Adapter
        adapter = new SelectChallengeAdapter(challenges);
        mRecyclerView.setAdapter(adapter);

        //Set the click handler
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this, mRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getApplicationContext(), ChallengeActivity.class);
                                intent.putExtra(KEY_CHALLENGE, challenges.get(position));
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }) {
                });
    }

    private boolean verifyUserParticipation(DataSnapshot data, HashMap<String, ParticipationChallenge> participations) {

        ParticipationChallenge participation = participations.get(data.getKey());
        if (participation != null)
            return true;

        return false;

    }

    private HashMap<String, ParticipationChallenge> downloadParticipation() {

        final HashMap<String, ParticipationChallenge> participations = new HashMap<>();

        DatabaseReference dbRef = ConfigFirebase.getFirebaseDatabase().child("fernando").child(KEY_PARTICIPATIONS);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    participations.put(data.getKey(), data.getValue(ParticipationChallenge.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return participations;

    }

    public void buttonLogoutClick(View view) {
        mAuth.signOut();
        finish();
    }


}





