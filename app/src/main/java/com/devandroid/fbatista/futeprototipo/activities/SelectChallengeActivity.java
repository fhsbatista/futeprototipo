package com.devandroid.fbatista.futeprototipo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.widget.AdapterView;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.RecyclerItemClickListener;
import com.devandroid.fbatista.futeprototipo.adapters.SelectChallengeAdapter;
import com.devandroid.fbatista.futeprototipo.dao.Challenge;

import java.util.ArrayList;
import java.util.List;

public class SelectChallengeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SelectChallengeAdapter adapter;
    private List<Challenge> challenges;
    private RecyclerItemClickListener.OnItemClickListener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_challenge);

        //Initialize the recycler view
        mRecyclerView = findViewById(R.id.rv);

        //Set the layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        //Set the list of challenges
        challenges = new ArrayList<>();
        challenges.add(new Challenge("1 Embaixadinha", 1, "Fazer uma embaixadinha"));
        challenges.add(new Challenge("3 Embaixadinha", 1, "Fazer 3 embaixadinhas, usandos pes diferentes"));
        challenges.add(new Challenge("10 Embaixadinha", 2, "Fazer 10 embaixadinhas, usando pes diferentes"));
        challenges.add(new Challenge("5 Embaixadinhas Sentado", 4, "5 embaixadinhas sentado no chao, com pes diferentes"));
        challenges.add(new Challenge("10 Embaixadinhas Sentado", 4, "10 embaixadinhas sentado no chao, com pes diferentes"));

        //Set the Adapter
        adapter = new SelectChallengeAdapter(challenges);
        mRecyclerView.setAdapter(adapter);

        //Set the click handler
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, ) {
        });
    }






}
