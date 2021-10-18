package com.bloodbuddy.lifeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //private Button log_out_button;
    private DrawerLayout navigation_drawer;
    private Toolbar toolbar;
    private NavigationView navigation_view;
    private TextView user_name;
    private DatabaseReference user_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LifeShare");

        navigation_drawer = findViewById(R.id.navigation_drawer);
        navigation_view = findViewById(R.id.navigation_view);

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

//       log_out_button = findViewById(R.id.log_out_button);
//
//        log_out_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(homePage.this , LoginPage.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_profile:
                Intent intent = new Intent(HomePage.this , Profile.class);
                startActivity(intent);
        }
        navigation_drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}