package com.devandroid.fbatista.futeprototipo.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.devandroid.fbatista.futeprototipo.helper.KeyboardHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {


    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView mSignUp;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_senha);
        mLogin = findViewById(R.id.bt_login);
        mSignUp = findViewById(R.id.tv_signup);

        mAuth = ConfigFirebase.getAuth();

        //The user will be logged out if he is already logged in, this is temporaly
        mAuth.signOut();

        //Object to manipulate the user's keyboard
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KeyboardHelper.hideKeyBoard(LoginActivity.this);
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Campos nao preenchidos", Toast.LENGTH_SHORT).show();

                    //Show the keyboard for the user
                    KeyboardHelper.showKeyboard(LoginActivity.this);
                } else {

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        String mensagemErro = "";
                                        try{
                                            throw task.getException();
                                        }catch(FirebaseAuthInvalidUserException e){
                                            mensagemErro = "Usuario nao existente";
                                        }catch (FirebaseAuthInvalidCredentialsException e){
                                            mensagemErro = "Senha Incorreta";
                                        } catch (Exception e){
                                            mensagemErro = e.getMessage();
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(LoginActivity.this, mensagemErro, Toast.LENGTH_SHORT).show();

                                        //Show the keyboard for the user
                                        KeyboardHelper.showKeyboard(LoginActivity.this);

                                    } else{
                                        if (mAuth.getCurrentUser() != null) {
                                            startActivity(new Intent(LoginActivity.this, SelectChallengeActivity.class));
                                        }
                                    }
                                }
                            });
                }


            }
        });
    }




}
