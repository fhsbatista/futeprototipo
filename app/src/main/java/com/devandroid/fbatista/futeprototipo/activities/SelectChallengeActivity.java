package com.devandroid.fbatista.futeprototipo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
//import android.util.Config;
import android.view.View;
import android.widget.AdapterView;

import com.devandroid.fbatista.futeprototipo.Keys;
import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.RecyclerItemClickListener;
import com.devandroid.fbatista.futeprototipo.adapters.SelectChallengeAdapter;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.devandroid.fbatista.futeprototipo.dao.ParticipationChallenge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectChallengeActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_CHALLENGE = "challenge";
    private static final String TAG = SelectChallengeActivity.class.getSimpleName();

    private HashMap<String, ParticipationChallenge> participations = new HashMap<>();

    private RecyclerView mRecyclerView;
    private SelectChallengeAdapter adapter;
    private ArrayList<ParticipationChallenge> challenges;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference dbRefUser;
    private DatabaseReference dbRefChallenges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_challenge);

        //Este e um teste

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

        String idUser = ConfigFirebase.getAuth().getCurrentUser().getUid();

        dbRefChallenges = ConfigFirebase.getFirebaseDatabase().child(Keys.KEY_USERS).child(idUser).child(Keys.KEY_USER_PARTICIPATION);

        dbRefChallenges.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                challenges.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    ParticipationChallenge part = data.getValue(ParticipationChallenge.class);
                    challenges.add(part);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Set the Adapter
        adapter= new SelectChallengeAdapter(challenges);
        mRecyclerView.setAdapter(adapter);

        //Set the click handler
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this, mRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getApplicationContext(), ChallengeActivity.class);
                                intent.putExtra(KEY_EXTRA_CHALLENGE, challenges.get(position));
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

    public void buttonLogoutClick(View view) {
        mAuth.signOut();
        finish();
    }


}





