package com.example.trackmysport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Team extends AppCompatActivity {

    FloatingActionButton fragmentCreate;
    Button teamCreated;
    boolean frag= true;
    private static FirebaseDatabase fd;
    private static DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_team);
        fragmentCreate = findViewById(R.id.floatingActionButton);

        replaceFragment(new manageTeamsFragment());
        fragmentCreate.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (frag==true) {
                      replaceFragment(new CreateTeamFragment());
                      frag=false;
                  }else{
                      replaceFragment(new manageTeamsFragment());
                      frag=true;
                  }
              }
          }
        );
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        //transaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        //transaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void createTeam(View view){

        frag=true;
        fd = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
        dr = fd.getReference();
        EditText editText = (EditText) findViewById(R.id.nameTeam);
        String name = editText.getText().toString();
        dr.child("Teams").child(name).child("teamname").setValue(name);
        replaceFragment(new manageTeamsFragment());
    }


}