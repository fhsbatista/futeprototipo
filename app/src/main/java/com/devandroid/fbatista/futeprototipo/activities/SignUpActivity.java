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
import com.devandroid.fbatista.futeprototipo.dao.User;
import com.devandroid.fbatista.futeprototipo.helper.KeyboardHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignUpActivity extends AppCompatActivity {

    private EditText mName, mEmail, mPassword;
    private Button mSignUp;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mName = findViewById(R.id.et_name);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mSignUp = findViewById(R.id.bt_signup);

        mAuth = ConfigFirebase.getAuth();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardHelper.hideKeyBoard(SignUpActivity.this);
                final String name = mName.getText().toString();
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Campos digitados incorretamente", Toast.LENGTH_SHORT).show();
                    KeyboardHelper.showKeyboard(SignUpActivity.this);
                } else{

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        String errorMessage = "";
                                        try{
                                            throw task.getException();
                                        }catch(FirebaseAuthWeakPasswordException e){
                                            errorMessage = "Digite uma senha mais forte";
                                        }catch (FirebaseAuthInvalidCredentialsException e){
                                            errorMessage = "Digite um e-mail valido";
                                        }catch(FirebaseAuthUserCollisionException e){
                                            errorMessage = "Ja existe um cadastro com e-mail informado";
                                        }catch (Exception e){
                                            errorMessage = e.getMessage();
                                            e.printStackTrace();
                                        }
                                        KeyboardHelper.showKeyboard(SignUpActivity.this);
                                        Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                    } else{
                                        Toast.makeText(SignUpActivity.this, "Voce foi cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                                        String idUser = mAuth.getCurrentUser().getUid();
                                        User user = new User(idUser, name, email);
                                        user.create();
                                        startActivity(new Intent(SignUpActivity.this, SelectChallengeActivity.class));
                                        finish();

                                    }
                                }
                            });
                }
            }
        });
    }





}
