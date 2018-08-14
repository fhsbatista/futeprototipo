package com.devandroid.fbatista.futeprototipo.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button mButtonStart;
    private EditText mEmail;
    private EditText mSenha;


    private FirebaseAuth auth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonStart = findViewById(R.id.bt_entrar);
        mEmail = findViewById(R.id.et_email);
        mSenha = findViewById(R.id.et_senha);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

    }

    @Override
    protected void onStart() {
        super.onStart();
        validadeLoggedUser();
    }

    public void buttonStartClick(View view) {

        auth.signInWithEmailAndPassword(
                mEmail.getText().toString(), mSenha.getText().toString()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    startActivity(new Intent(MainActivity.this, SelectChallengeActivity.class));

                } else {
                    Toast.makeText(MainActivity.this, "Algo deu errado", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void validadeLoggedUser() {
        auth = ConfigFirebase.getAuth();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, SelectChallengeActivity.class));
        }
    }


}
