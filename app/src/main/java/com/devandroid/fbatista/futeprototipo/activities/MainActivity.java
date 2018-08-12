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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button mButtonStart;
    private EditText mEmail;
    private EditText mSenha;


    private FirebaseAuth auth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonStart = findViewById(R.id.bt_entrar);
        mEmail = findViewById(R.id.et_email);
        mSenha = findViewById(R.id.et_senha);












    }

    public void buttonStartClick(View view){

        auth.signInWithEmailAndPassword(
                mEmail.getText().toString(), mSenha.getText().toString()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
