package com.bloodbuddy.lifeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    private TextView dont_have_account , forgot_password;
    private Button login_button;
    private TextInputEditText email, password;
    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener loginState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();
        loginState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    if(user.getEmail().equals("admin@admin.com")){
                        Intent intent = new Intent(LoginPage.this , AdminLogin.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(LoginPage.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };

        dont_have_account = findViewById(R.id.dont_have_account);
        login_button = findViewById(R.id.login_button);
        email = findViewById(R.id.email);
        password =findViewById(R.id.password);
        forgot_password = findViewById(R.id.forgot_password);

        loading = new ProgressDialog(this);

        dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this , RegistrationPage.class);
                startActivity(intent);
                finish();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String entered_email = email.getText().toString().trim();
                final String entered_passwrord = password.getText().toString().trim();

                if(TextUtils.isEmpty(entered_email)){
                    email.setError("Please enter your registered email id!");
                    return;
                }

                if(TextUtils.isEmpty(entered_passwrord)){
                    password.setError("Please enter password to login!");
                    return;
                }

                else{

                    if(entered_email.equals("admin@admin.com")){
                        loading.setMessage("Please wait while we log you in!");
                        loading.setCanceledOnTouchOutside(false);
                        loading.show();
                        mAuth.signInWithEmailAndPassword(entered_email , entered_passwrord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginPage.this, "Logged in successfully as admin", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginPage.this, AdminLogin.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    String error = task.getException().toString();
                                    Toast.makeText(LoginPage.this, error.substring(error.indexOf(":") + 1, error.length()), Toast.LENGTH_LONG).show();
                                }
                                loading.dismiss();

                            }
                        });

                    }


                    else {
                        loading.setMessage("Please wait while we log you in!");
                        loading.setCanceledOnTouchOutside(false);
                        loading.show();

                        mAuth.signInWithEmailAndPassword(entered_email, entered_passwrord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginPage.this, "Logged in successfully", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    String error = task.getException().toString();
                                    Toast.makeText(LoginPage.this, error.substring(error.indexOf(":") + 1, error.length()), Toast.LENGTH_LONG).show();
                                }
                                loading.dismiss();
                            }
                        });

                    }

                }

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(loginState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(loginState);
    }
}