package com.devandroid.fbatista.futeprototipo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devandroid.fbatista.futeprototipo.R;

public class MainActivity extends AppCompatActivity {

    private Button mButtonStart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonStart = findViewById(R.id.bt_start);





    }

    public void buttonStartClick(View view){
        startActivity(new Intent(this, SelectChallengeActivity.class));
    }


}
