package com.devandroid.fbatista.futeprototipo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.devandroid.fbatista.futeprototipo.Keys;
import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.RecyclerItemClickListener;
import com.devandroid.fbatista.futeprototipo.adapters.ApprovementChallengeAdapter;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.devandroid.fbatista.futeprototipo.dao.ParticipationChallenge;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApprovementListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ApprovementChallengeAdapter mAdapter;
    private List<ParticipationChallenge> mApprovementChallengeList;

    private DatabaseReference mReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvement_list);


        //Initialize the recycler view
        mRecyclerView = findViewById(R.id.rv_challenges);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //Set the Layout Manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        //Initialize the List of approvements
        mApprovementChallengeList = new ArrayList<>();

        //Initialize the adapter
        mAdapter = new ApprovementChallengeAdapter(mApprovementChallengeList);

        //Set the adapter for the recycler view
        mRecyclerView.setAdapter(mAdapter);

        //Set the database's reference
        mReference = ConfigFirebase.getFirebaseDatabase().child(Keys.KEY_USERS);

        //Fetch request in order to get the challenges participations which are waiting for approvement
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    for(DataSnapshot dataPart : data.child(Keys.KEY_USER_PARTICIPATION).getChildren()){
                        ParticipationChallenge participationChallenge = dataPart.getValue(ParticipationChallenge.class);
                        if(participationChallenge.getStatus().equals(ParticipationChallenge.STATUS_WAITING_APPROVEMENT)){
                            participationChallenge.setNomeUser((String) data.child("name").getValue().toString());
                            mApprovementChallengeList.add(participationChallenge);
                            mAdapter.notifyDataSetChanged();

                        }
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String videoName = "video" + mApprovementChallengeList.get(position).getIdChallenge();
                Intent intent = new Intent(ApprovementListActivity.this, ApprovementActivity.class);
                intent.putExtra("videoName", videoName);
                intent.putExtra("userName", mApprovementChallengeList.get(position).getNomeUser());
                intent.putExtra("idChallenge", mApprovementChallengeList.get(position).getIdChallenge());
                intent.putExtra("challengeName", mApprovementChallengeList.get(position).getDescription());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }
        ));




    }
}
