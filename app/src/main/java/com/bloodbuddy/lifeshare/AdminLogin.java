package com.bloodbuddy.lifeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bloodbuddy.lifeshare.Model.RaisedRequests;
import com.bloodbuddy.lifeshare.adapter.AdminAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminLogin extends AppCompatActivity {

    private DatabaseReference user_ref;
    private Toolbar toolbar;
    private RecyclerView recycler_view;
    private ProgressBar progress;
    private List<RaisedRequests> raisedRequestsList;
    private AdminAdapter adminAdapter;
    private ExtendedFloatingActionButton log_out_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        log_out_button = findViewById(R.id.log_out_button);
        progress = findViewById(R.id.progress);
        recycler_view = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LifeShare Admin Page");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recycler_view.setLayoutManager(layoutManager);

        raisedRequestsList = new ArrayList<>();
        adminAdapter = new AdminAdapter(AdminLogin.this , raisedRequestsList);

        recycler_view.setAdapter(adminAdapter);

        user_ref = FirebaseDatabase.getInstance().getReference().child("raised_requests");
        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                raisedRequestsList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RaisedRequests raisedRequests = dataSnapshot.getValue(RaisedRequests.class);
                    raisedRequestsList.add(raisedRequests);
                }
                adminAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);

                if(raisedRequestsList.isEmpty()){
                    Toast.makeText(AdminLogin.this , "No requests yet!" , Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminLogin.this , LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}