package com.example.trackmysport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TrainingSession extends AppCompatActivity {

    FloatingActionButton fragmentCreate;
    boolean frag= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_session);

        fragmentCreate = findViewById(R.id.floatingActionButton_sessions);

        replace_sessions_fragment(new ManageTrainingSessionsFragment());
        fragmentCreate.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (frag==true) {
                      replace_sessions_fragment(new CreateTrainingSessionFragment());
                      frag=false;
                  }else{
                      replace_sessions_fragment(new ManageTrainingSessionsFragment());
                      frag=true;
                  }
              }
            }
        );
    }

    public void replace_sessions_fragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_sessions, fragment);
        fragmentTransaction.commit();
    }

    public void create_training_session(View view){
        frag=true;
        replace_sessions_fragment(new ManageTrainingSessionsFragment());
    }

}