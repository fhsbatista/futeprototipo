package com.devandroid.fbatista.futeprototipo.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.dao.Challenge;

import java.io.Serializable;

public class ChallengeActivity extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CAPTURE = 1;

    private TextView mTextViewTitle;
    private TextView mTextViewDescription;
    private TextView mTextViewLevel;
    private Button mButtonRecord;
    private VideoView mVideoViewVideo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);


        //Retrieve data from previous activity
        Bundle bundle = getIntent().getExtras();
        Challenge challenge = (Challenge) bundle.getSerializable(SelectChallengeActivity.KEY_CHALLENGE);

        //Set layout elements
        mTextViewTitle = findViewById(R.id.tv_title);
        mTextViewDescription = findViewById(R.id.tv_description);
        mTextViewLevel = findViewById(R.id.tv_level);
        mButtonRecord = findViewById(R.id.bt_gravar);
        mVideoViewVideo = findViewById(R.id.vv_video);



        mTextViewTitle.setText(challenge.getTitle());
        mTextViewDescription.setText(challenge.getDescription());
        mTextViewLevel.setText(String.valueOf(challenge.getLevel()));

    }

    public void buttonRecordClick(View view){

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }


    }

    


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK){
            Uri videoUri = data.getData();
            mVideoViewVideo.setVideoURI(videoUri);
            mVideoViewVideo.start();
        }
    }
}
