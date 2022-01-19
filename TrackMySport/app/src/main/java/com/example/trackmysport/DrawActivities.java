package com.example.trackmysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DrawActivities extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_activities);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        bottomNavigationView.setSelectedItemId(R.id.teach);
    }

    public void GoToVideos(View view){
        Intent i = new Intent(DrawActivities.this, Videos.class);
        startActivity(i);
    }

    public void GoToWhiteboard(View view){
        Intent i = new Intent(DrawActivities.this, Whiteboard.class);
        startActivity(i);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.player:
                            Intent i1 = new Intent(DrawActivities.this, TrainingSession.class);
                            startActivity(i1);
                            return true;
                        case R.id.teams:
                            Intent i2 = new Intent(DrawActivities.this, Team.class);
                            startActivity(i2);
                            return true;
                        case R.id.teach:

                            return true;
                        case R.id.agenda:
                            Intent i4 = new Intent(DrawActivities.this, Agenda.class);
                            startActivity(i4);
                            return true;
                        case R.id.profile:
                            Intent i5 = new Intent(DrawActivities.this, MainPage.class);
                            startActivity(i5);
                            return true;
                    }
                    return false;
                }
            };
}