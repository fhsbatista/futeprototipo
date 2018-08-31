package com.devandroid.fbatista.futeprototipo.activities;

import android.app.Activity;
import com.devandroid.fbatista.futeprototipo.dao.User;
import com.facebook.Profile;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.config.ConfigFirebase;
import com.devandroid.fbatista.futeprototipo.helper.KeyboardHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {


    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText mEmail, mPassword;
    private Button mLogin;
    private LoginButton mLoginFacebookButton;
    private TextView mSignUp;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    private CallbackManager mCallbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_senha);
        mLogin = findViewById(R.id.bt_login);
        mSignUp = findViewById(R.id.tv_signup);
        mProgressBar = findViewById(R.id.pb_login);
        mLoginFacebookButton = findViewById(R.id.bt_facebook_login);


        mAuth = ConfigFirebase.getAuth();

        //Set the facebook auth stuffs
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AuthCredential credential = FacebookAuthProvider.getCredential
                        (loginResult.getAccessToken().getToken());


                signInCredential(credential);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

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
                    showProgressBar();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        hideProgressBar();
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

    private void signInCredential(final AuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String idUsuario = mAuth.getCurrentUser().getUid();
                            Profile profile = Profile.getCurrentProfile();
                            User user = new User(idUsuario, profile.getName(), firebaseUser.getEmail());
                            user.create();
                            startActivity(new Intent(LoginActivity.this, SelectChallengeActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Somenthing happened", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mEmail.setVisibility(View.GONE);
        mPassword.setVisibility(View.GONE);
        mLogin.setVisibility(View.GONE);
        mSignUp.setVisibility(View.GONE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
        mEmail.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.VISIBLE);
        mLogin.setVisibility(View.VISIBLE);
        mSignUp.setVisibility(View.VISIBLE);
    }


}
