package com.devandroid.fbatista.futeprototipo.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class ApprovementActivity extends AppCompatActivity {

    private static final String TAG = ApprovementActivity.class.getSimpleName();
    private VideoView mVideoView;
    private TextView mChallengeName, mUserName;
    private Button mReprove, mApprove;

    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvement);

        mVideoView = findViewById(R.id.vv_video);
        mChallengeName = findViewById(R.id.tv_challenge_name);
        mUserName = findViewById(R.id.tv_user_name);
        mReprove = findViewById(R.id.bt_reprove);
        mApprove = findViewById(R.id.bt_approve);

        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("userName");
        String challengeName = bundle.getString("challengeName");
        String idChallenge = bundle.getString("idChallenge");
        String videoName = bundle.getString("videoName");
        String idUser = ConfigFirebase.getAuth().getCurrentUser().getUid();

        //Set MediaController for the video view
        MediaController mediaController = new MediaController(this);
        mVideoView.setMediaController(mediaController);

        final long ONE_HUNDRED_MEGABYTE = 1024 * 1024 * 1024;

        mStorage= FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference().child(idUser).child(idChallenge).child(videoName);
        Log.d(TAG, mStorageReference.getPath());
        //DOWNLOAD DE VIDEO
        mStorageReference.getBytes(ONE_HUNDRED_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        File video = convertBytesToFile(bytes);
                        mVideoView.setVideoPath(video.getPath());
                        mVideoView.start();



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });






    }

    private File convertBytesToFile(byte[] bytearray) {
        try {

            File outputFile = File.createTempFile("file", "mp4", getCacheDir());
            outputFile.deleteOnExit();
            FileOutputStream fileoutputstream = new FileOutputStream(outputFile);
            fileoutputstream.write(bytearray);
            fileoutputstream.close();
            return outputFile;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
