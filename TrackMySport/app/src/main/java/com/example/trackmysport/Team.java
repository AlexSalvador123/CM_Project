package com.example.trackmysport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Team extends AppCompatActivity {

    FloatingActionButton fragmentCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        fragmentCreate = findViewById(R.id.floatingActionButton);
        replaceFragment(new manageTeamsFragment());
        fragmentCreate.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  replaceFragment(new CreateTeamFragment());

              }
          }
        );
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}