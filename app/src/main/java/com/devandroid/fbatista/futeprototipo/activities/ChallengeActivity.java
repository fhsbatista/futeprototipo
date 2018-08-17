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
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.devandroid.fbatista.futeprototipo.dao.Challenge;
import com.devandroid.fbatista.futeprototipo.dao.ParticipationChallenge;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    private String idUser;
    private String idChallenge;
    private String videoName;

    private ParticipationChallenge participation;
    private FirebaseStorage storage;
    private StorageReference reference;
    private StorageReference userRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);


        //Retrieve data from previous activity
        Bundle bundle = getIntent().getExtras();
        Challenge challenge = (Challenge) bundle.getSerializable(SelectChallengeActivity.KEY_CHALLENGE);

        //Set the authentication information
        mAuth = ConfigFirebase.getAuth();

        //Set layout elements
        mTextViewTitle = findViewById(R.id.tv_title);
        mTextViewDescription = findViewById(R.id.tv_description);
        mTextViewLevel = findViewById(R.id.tv_level);
        mButtonRecord = findViewById(R.id.bt_gravar);
        mVideoViewVideo = findViewById(R.id.vv_video);



        mTextViewTitle.setText(challenge.getTitle());
        mTextViewDescription.setText(challenge.getDescription());
        mTextViewLevel.setText(String.valueOf(challenge.getLevel()));

        //Set the strings for database
        idChallenge = challenge.getIdChallenge();
        idUser = mAuth.getCurrentUser().getUid();
        videoName = "video" + idChallenge;

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
            userRef = reference.child(idUser);;
            UploadTask uploadTask = userRef.child(idChallenge).child(videoName).putFile(videoUri);



            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Setting object model of the participation
                    //Get the url of the video which has been uploaded
                    String url = taskSnapshot.getDownloadUrl().toString();
                            participation = new ParticipationChallenge
                            (idUser, idChallenge, url);
                    participation.saveParticipation();
                }
            });


        }
    }
}
