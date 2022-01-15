package com.example.trackmysport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

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