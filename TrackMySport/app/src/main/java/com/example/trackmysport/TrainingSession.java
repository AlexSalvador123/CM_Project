package com.example.trackmysport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrainingSession extends AppCompatActivity {

    FloatingActionButton fragmentCreate;
    boolean frag= true;
    private static FirebaseDatabase firebasedb;
    private static DatabaseReference dbref;

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
        firebasedb = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
        dbref = firebasedb.getReference();

        //plan name
        EditText name_session = (EditText) findViewById(R.id.name_session);
        String name_session_string = name_session.getText().toString();
        dbref.child("Training Plans").child(name_session_string).child("plan_name").setValue(name_session_string);

        //difficulty
        EditText difficulty_session = (EditText) findViewById(R.id.session_difficulty);
        String difficulty_session_string = difficulty_session.getText().toString();
        dbref.child("Training Plans").child(name_session_string).child("plan_difficulty").setValue(difficulty_session_string);

        //exercise1 name
        EditText exercise1_session = (EditText) findViewById(R.id.line1_1);
        String exercise1_session_string = exercise1_session.getText().toString();
        dbref.child("Training Plans").child(name_session_string).child("plan_exercise1").setValue(exercise1_session_string);

        //exercise1 time
        EditText time1_session = (EditText) findViewById(R.id.line1_2);
        String time1_session_string = time1_session.getText().toString();
        dbref.child("Training Plans").child(name_session_string).child("plan_time1").setValue(time1_session_string);

        //exercise1 repetitions
        EditText reps1_session = (EditText) findViewById(R.id.line1_3);
        String reps1_session_string = reps1_session.getText().toString();
        dbref.child("Training Plans").child(name_session_string).child("plan_reps1").setValue(reps1_session_string);

        //exercise2 name
        EditText exercise2_session = (EditText) findViewById(R.id.line2_1);
        String exercise2_session_string = exercise2_session.getText().toString();
        dbref.child("Training Plans").child(name_session_string).child("plan_exercise2").setValue(exercise2_session_string);

        //exercise2 time
        EditText time2_session = (EditText) findViewById(R.id.line2_2);
        String time2_session_string = time2_session.getText().toString();
        dbref.child("Training Plans").child(name_session_string).child("plan_time2").setValue(time2_session_string);

        //exercise2 repetitions
        EditText reps2_session = (EditText) findViewById(R.id.line2_3);
        String reps2_session_string = exercise2_session.getText().toString();
        dbref.child("Training Plans").child(name_session_string).child("plan_reps2").setValue(reps2_session_string);


        replace_sessions_fragment(new ManageTrainingSessionsFragment());
    }


}