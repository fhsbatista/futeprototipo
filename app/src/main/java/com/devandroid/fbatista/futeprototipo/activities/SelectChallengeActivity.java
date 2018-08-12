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
import com.devandroid.fbatista.futeprototipo.dao.Challenge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SelectChallengeActivity extends AppCompatActivity {

    public static final String KEY_CHALLENGE = "challenge";
    private static final String TAG = SelectChallengeActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private SelectChallengeAdapter adapter;
    private ArrayList<Challenge> challenges;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


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
        challenges = new ArrayList<Challenge>();
        challenges.add(new Challenge("1 Embaixadinha", 1, "Fazer uma embaixadinha"));
        challenges.add(new Challenge("3 Embaixadinha", 1, "Fazer 3 embaixadinhas, usandos pes diferentes"));
        challenges.add(new Challenge("10 Embaixadinha", 2, "Fazer 10 embaixadinhas, usando pes diferentes"));
        challenges.add(new Challenge("5 Embaixadinhas Sentado", 4, "5 embaixadinhas sentado no chao, com pes diferentes"));
        challenges.add(new Challenge("10 Embaixadinhas Sentado", 4, "10 embaixadinhas sentado no chao, com pes diferentes"));

        //Set the Adapter
        adapter = new SelectChallengeAdapter(challenges);
        mRecyclerView.setAdapter(adapter);

        //Set the click handler
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this, mRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener(){

                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(SelectChallengeActivity.this, "teste", Toast.LENGTH_SHORT).show();
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

    public void buttonLogoutClick(View view){
        mAuth.signOut();
        finish();
    }






}
