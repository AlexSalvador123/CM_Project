package com.example.trackmysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    System.out.println(item.getItemId());
                    switch(item.getItemId()){
                        case R.id.player:
                            Intent i1 = new Intent(MainPage.this, TrainingSession.class);
                            startActivity(i1);
                            return true;
                        case R.id.teams:
                            Intent i2 = new Intent(MainPage.this, Team.class);
                            startActivity(i2);
                            return true;
                        case R.id.teach:
                            Intent i3 = new Intent(MainPage.this, DrawActivities.class);
                            startActivity(i3);
                            return true;
                        case R.id.agenda:
                            Intent i4 = new Intent(MainPage.this, Agenda.class);
                            startActivity(i4);
                            return true;
                        case R.id.profile:
                            Intent i5 = new Intent(MainPage.this, Profile.class);
                            startActivity(i5);
                            return true;

                    }
                return false;
                }
            };

    public void Profile(View view){
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
    }

    public void Team(View view){
        Intent i = new Intent(this, Team.class);
        startActivity(i);
    }

    public void Videos(View view){
        Intent i = new Intent(this, Videos.class);
        startActivity(i);
    }

    public void TrainingSession(View view){
        Intent i = new Intent(this, TrainingSession.class);
        startActivity(i);
    }

    public void Whiteboard(View view){
        Intent i = new Intent(this, Whiteboard.class);
        startActivity(i);
    }

    public void Agenda(View view){
        Intent i = new Intent(this, Agenda.class);
        startActivity(i);
    }

}