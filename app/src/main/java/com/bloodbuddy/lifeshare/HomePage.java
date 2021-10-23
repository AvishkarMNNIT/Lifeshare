package com.bloodbuddy.lifeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bloodbuddy.lifeshare.Model.RaisedRequests;
import com.bloodbuddy.lifeshare.Model.User;
import com.bloodbuddy.lifeshare.adapter.UserAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout navigation_drawer;
    private Toolbar toolbar;
    private NavigationView navigation_view;
    private TextView user_name;
    private DatabaseReference user_ref;
    private ExtendedFloatingActionButton request_blood_button;
    private RecyclerView recycler_view;
    private ProgressBar progress;
    private List<RaisedRequests> raisedRequestsList ;
    private UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LifeShare");

        navigation_drawer = findViewById(R.id.navigation_drawer);
        navigation_view = findViewById(R.id.navigation_view);
        request_blood_button = findViewById(R.id.request_blood_button);


        user_name = navigation_view.getHeaderView(0).findViewById(R.id.user_name);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomePage.this , navigation_drawer , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        navigation_drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigation_view.setNavigationItemSelectedListener(this);
        user_ref = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    user_name.setText("Hello, " +  snapshot.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        request_blood_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this , BloodRequest.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_profile:
                Intent intent = new Intent(HomePage.this , Profile.class);
                startActivity(intent);
                break;

            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(HomePage.this , LoginPage.class);
                startActivity(intent1);
                finish();

            case R.id.requests:
                progress = findViewById(R.id.progress);
                recycler_view = findViewById(R.id.recycler_view);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                recycler_view.setLayoutManager(layoutManager);

                raisedRequestsList = new ArrayList<>();
                userAdapter = new UserAdapter(HomePage.this , raisedRequestsList);

                recycler_view.setAdapter(userAdapter);

                user_ref = FirebaseDatabase.getInstance().getReference().child("accepted_requests");
                user_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        raisedRequestsList.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            RaisedRequests raisedRequests = dataSnapshot.getValue(RaisedRequests.class);
                            raisedRequestsList.add(raisedRequests);
                        }
                        userAdapter.notifyDataSetChanged();
                        progress.setVisibility(View.GONE);

                        if(raisedRequestsList.isEmpty()){
                            Toast.makeText(HomePage.this , "No requests yet!" , Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        }
        navigation_drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}