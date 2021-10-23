package com.bloodbuddy.lifeshare;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.tv.TvContract;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class BloodRequest extends AppCompatActivity {

    private TextInputEditText request_name, request_email, hospital_area, hospital_name, request_phone_number, hospital_city, hospital_pin;
    private EditText file_name;
    private Button submit_button;
    private Spinner request_blood_group, request_blood_amount, patient_condition;
    private int request_no;
    private DatabaseReference databaseRef ;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);

        SharedPreferences pref = getSharedPreferences("com.bloodbuddy.lifeshare.requests" ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        request_no = pref.getInt("request_no" , 0);

        request_blood_group = findViewById(R.id.request_blood_group);
        request_blood_amount = findViewById(R.id.request_blood_amount);
        patient_condition = findViewById(R.id.patient_condition);
        request_name = findViewById(R.id.request_name);
        request_email = findViewById(R.id.request_email);
        request_phone_number = findViewById(R.id.request_phone_number);
        hospital_name = findViewById(R.id.hospital_name);
        hospital_area = findViewById(R.id.hospital_area);
        hospital_city = findViewById(R.id.hospital_city);
        hospital_pin = findViewById(R.id.hospital_pin);
        file_name = findViewById(R.id.file_name);
        submit_button = findViewById(R.id.submit_button);

        mAuth = FirebaseAuth.getInstance();

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String entered_fullname = request_name.getText().toString().trim();
                final String entered_phonenumber = request_phone_number.getText().toString().trim();
                final String entered_bloodgroup = request_blood_group.getSelectedItem().toString().trim();
                final String entered_amount = request_blood_amount.getSelectedItem().toString().trim();
                final String entered_hospital_name = hospital_name.getText().toString().trim();
                final String entered_hospital_city = hospital_city.getText().toString().trim();
                final String entered_hospital_area = hospital_area.getText().toString().trim();
                final String entered_hospital_pin = hospital_pin.getText().toString().trim();
                final String entered_patients_condition = patient_condition.getSelectedItem().toString().trim();
                final String entered_email = request_email.getText().toString().trim();

                String userId = mAuth.getCurrentUser().getUid();

                if (TextUtils.isEmpty(entered_fullname)) {
                    request_name.setError("Name cannot be empty!");
                    return;
                }

                if (TextUtils.isEmpty(entered_phonenumber)) {
                    request_phone_number.setError("Phone number cannot be empty!");
                    return;
                }

                if (TextUtils.isEmpty(entered_email)) {
                    request_email.setError("Email cannot be empty!");
                    return;
                }

                if (entered_bloodgroup.equals("Select your blood group")) {
                    Toast.makeText(BloodRequest.this, "Blood group cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if (entered_amount.equals("Select units")) {
                    Toast.makeText(BloodRequest.this, "Enter a valid amount", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(entered_hospital_name)) {
                    hospital_name.setError("Hospital name cannot be empty!");
                    return;
                }

                if (TextUtils.isEmpty(entered_hospital_area)) {
                    hospital_area.setError("Hospital area cannot be empty!");
                    return;
                }

                if (TextUtils.isEmpty(entered_hospital_city)) {
                    hospital_city.setError("Hospital city be empty!");
                    return;
                }

                if (TextUtils.isEmpty(entered_hospital_pin)) {
                    hospital_pin.setError("Pin code cannot be empty!");
                    return;
                }

                if (patient_condition.equals("Select patient condition")) {
                    Toast.makeText(BloodRequest.this, "Enter patient's condition", Toast.LENGTH_LONG).show();
                    return;
                }

                else {
                    request_no  = request_no + 1;
                    editor.putInt("request_no" ,request_no);
                    editor.apply();
                    databaseRef = FirebaseDatabase.getInstance().getReference().child("raised_requests").child(Integer.toString(request_no));
                    HashMap userData = new HashMap();
                    userData.put("uid" , userId);
                    userData.put("name", entered_fullname);
                    userData.put("email", entered_email);
                    userData.put("phone_number", entered_phonenumber);
                    userData.put("amount", entered_amount);
                    userData.put("blood_group", entered_bloodgroup);
                    userData.put("hospital_name", entered_hospital_name);
                    userData.put("hospital_area", entered_hospital_area);
                    userData.put("hospital_city", entered_hospital_city);
                    userData.put("hospital_pin", entered_hospital_pin);
                    userData.put("patient_condition", entered_patients_condition);
                    userData.put("request_no",Integer.toString(request_no));

                    databaseRef.updateChildren(userData).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(BloodRequest.this, "Request raised successfully" + request_no , Toast.LENGTH_LONG).show();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(BloodRequest.this, error.substring(error.indexOf(":") + 1, error.length()), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                    Intent intent = new Intent(BloodRequest.this, HomePage.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
}