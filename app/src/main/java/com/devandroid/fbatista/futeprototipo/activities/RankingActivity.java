package com.devandroid.fbatista.futeprototipo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.devandroid.fbatista.futeprototipo.Keys;
import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.adapters.RankingAdapter;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.devandroid.fbatista.futeprototipo.dao.RankPosition;
import com.devandroid.fbatista.futeprototipo.dao.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private DatabaseReference mReference;

    private RecyclerView mRecyclerView;
    private RankingAdapter mAdapter;
    private List<RankPosition> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //Initialize the list with the rank positions
        mList = new ArrayList<>();


        //Initialize the recycler view
        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //Set the adapter for the recycler view
        mAdapter = new RankingAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);



        //Config layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);


        //Populate de List of rank positions
        //For that the code below will request the ranking in the firebase
        mReference = ConfigFirebase.getFirebaseDatabase();

        //Below is the code that request the data from firebase
        mReference.child(Keys.KEY_USERS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> userList = new ArrayList<>();
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            User user = data.getValue(User.class);
                            userList.add(user);
                        }

                        Collections.sort(userList);
                        int position = 0;
                        for(User user : userList){
                            position++;
                            RankPosition rankPosition = new RankPosition(position, user.getName(), user.getScore());
                            mList.add(rankPosition);
                            mAdapter.notifyDataSetChanged();

                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


















    }
}
