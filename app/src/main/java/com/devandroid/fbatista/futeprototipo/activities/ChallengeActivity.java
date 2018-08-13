package com.devandroid.fbatista.futeprototipo.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import com.devandroid.fbatista.futeprototipo.dao.ParticipationChallenge;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;

public class ChallengeActivity extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CAPTURE = 1;

    private TextView mTextViewTitle;
    private TextView mTextViewDescription;
    private TextView mTextViewLevel;
    private Button mButtonRecord;
    private VideoView mVideoViewVideo;
    private static final String idUser = "fernando";
    private static final String idChallenge = "challenge1";
    private static final String videoName = "video_challenge1";

    private ParticipationChallenge participation;
    private FirebaseStorage storage;
    private StorageReference reference;
    private StorageReference userRef;
    private FirebaseDatabase db;
    private DatabaseReference ref;


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

            //Set variables for Storage use
            storage = FirebaseStorage.getInstance();
            reference = storage.getReference();
            userRef = reference.child(idUser).child(idChallenge).child(videoName);
            UploadTask uploadTask = userRef.putFile(videoUri);

            //Setting object model of the participation
            participation = new ParticipationChallenge
                    (idUser, idChallenge, ParticipationChallenge.STATUS_WAITING_APPROVEMENT, videoName);


            //Set variables for Database use
            db = FirebaseDatabase.getInstance();
            ref = db.getReference()
                    .child(idUser).child("participations").child(idChallenge);



            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ChallengeActivity.this, "An error has occurred when uploading", Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Toast.makeText(ChallengeActivity.this, "The video has been uploaded successfully", Toast.LENGTH_SHORT).show();
                    //If the video has been uploaded succesfully, it will be registered as a participation waiting for approvement
                    ref.setValue(participation);

                }
            });

        }
    }
}
