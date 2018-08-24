package com.devandroid.fbatista.futeprototipo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
//import android.util.Config;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    private Toolbar mToolbar;
    private ProgressBar mProgressBar;

    private HashMap<String, ParticipationChallenge> participations = new HashMap<>();

    private RecyclerView mRecyclerView;
    private SelectChallengeAdapter adapter;
    private ArrayList<ParticipationChallenge> challenges;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference dbRefUser;
    private DatabaseReference dbRefChallenges;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout_menu :
                mAuth.signOut();
                startActivity(new Intent(SelectChallengeActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.ranking_menu :
                startActivity(new Intent(SelectChallengeActivity.this, RankingActivity.class));
                break;

            case R.id.approvement_menu :
                startActivity(new Intent(SelectChallengeActivity.this, ApprovementListActivity.class));
                break;


        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_challenge);



        //Get the current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //Initialize the toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Initialize the recycler view
        mRecyclerView = findViewById(R.id.rv);

        //Initialize the progress bar
        mProgressBar = findViewById(R.id.pb_challenges);
        showProgressBar();

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
                    part.setIdUser(mAuth.getCurrentUser().getUid());
                    challenges.add(part);
                    adapter.notifyDataSetChanged();
                }

                hideProgressBar();
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

                                String challengeStatus = challenges.get(position).getStatus();
                                switch (challengeStatus){

                                    case ParticipationChallenge.STATUS_APPROVED :
                                        Toast.makeText(SelectChallengeActivity.this, "Voce ja foi aprovado neste desafio", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ParticipationChallenge.STATUS_WAITING_APPROVEMENT :
                                        Toast.makeText(SelectChallengeActivity.this, "Voce ja esta sendo avaliado para este desafio, aguarde o resultado", Toast.LENGTH_SHORT).show();
                                        break;
                                    default :
                                        Intent intent = new Intent(getApplicationContext(), ChallengeActivity.class);
                                        intent.putExtra(KEY_EXTRA_CHALLENGE, challenges.get(position));
                                        startActivity(intent);
                                        break;



                                }

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


    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void buttonLogoutClick(View view) {
        mAuth.signOut();
        finish();
    }


}





