package com.bloodbuddy.lifeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.StringTokenizer;

public class RegistrationPage extends AppCompatActivity {

    private TextView already_registered;
    private TextInputEditText full_name , password , email , phone_number;
    private Button signup_button;
    private Spinner blood_group;
    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        already_registered = findViewById(R.id.already_registered);
        full_name = findViewById(R.id.full_name);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        signup_button = findViewById(R.id.signup_button);
        blood_group = findViewById(R.id.blood_group);
        loading = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        already_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationPage.this , LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String entered_email = email.getText().toString().trim();
                final String entered_password = password.getText().toString().trim();
                final String entered_fullname = full_name.getText().toString().trim();
                final String entered_phonenumber = phone_number.getText().toString().trim();
                final String entered_bloodgroup = blood_group.getSelectedItem().toString();

                if(TextUtils.isEmpty(entered_email)) {
                    email.setError("Email cannot be empty!");
                    return;
                }

                if(entered_bloodgroup.equals("Select your blood group")) {
                    Toast.makeText(RegistrationPage.this , "Blood group cannot be empty" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(entered_fullname)) {
                    full_name.setError("Name cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(entered_password)) {
                    password.setError("Password cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(entered_phonenumber)) {
                    phone_number.setError("Phone number cannot be empty!");
                    return;
                }

                else{
                    loading.setMessage("Wait for a moment while we Sign you up");
                    loading.setCanceledOnTouchOutside(false);
                    loading.show();

                    mAuth.createUserWithEmailAndPassword(entered_email , entered_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                String error = task.getException().toString();
                                Toast.makeText(RegistrationPage.this , "Error! "+ error , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }

            }
        });

    }
}