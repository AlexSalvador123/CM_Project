package com.example.trackmysport;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainingSession extends AppCompatActivity {

    FloatingActionButton fragmentCreate;
    public static int frag= 1;
    private static FirebaseDatabase firebasedb;
    private static DatabaseReference dbref;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    public static String current_plan = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_session);

        fragmentCreate = findViewById(R.id.floatingActionButton_sessions);

        replace_sessions_fragment(new ManageTrainingSessionsFragment());
        fragmentCreate.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (frag==1) {
                      replace_sessions_fragment(new CreateTrainingSessionFragment());
                      fragmentCreate.setImageDrawable(getResources().getDrawable(R.drawable.baseline_arrow_back_20));
                      frag=0;
                  }else if (frag==0){
                      replace_sessions_fragment(new ManageTrainingSessionsFragment());
                      fragmentCreate.setImageDrawable(getResources().getDrawable(R.drawable.plus_foreground));
                      frag=1;
                  }/*else{
                      replace_sessions_fragment(new ManageTrainingSessionsFragment());
                      frag=1;
                      fragmentCreate.setImageDrawable(getResources().getDrawable(R.drawable.plus_foreground));
                  }*/
              }
            }

        );

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        bottomNavigationView.setSelectedItemId(R.id.player);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    System.out.println(item.getItemId());
                    switch(item.getItemId()){
                        case R.id.player:
                            return true;
                        case R.id.teams:
                            Intent i2 = new Intent(TrainingSession.this, Team.class);
                            startActivity(i2);
                            return true;
                        case R.id.teach:
                            Intent i3 = new Intent(TrainingSession.this, DrawActivities.class);
                            startActivity(i3);
                            return true;
                        case R.id.agenda:
                            Intent i4 = new Intent(TrainingSession.this, Agenda.class);
                            startActivity(i4);
                            return true;
                        case R.id.profile:
                            Intent i5 = new Intent(TrainingSession.this, MainPage.class);
                            startActivity(i5);
                            return true;

                    }
                    return false;
                }
            };


    public void replace_sessions_fragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_sessions, fragment);
        fragmentTransaction.commit();
    }

    public void create_training_session(View view){
        frag=1;
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
        String reps2_session_string = reps2_session.getText().toString();
        dbref.child("Training Plans").child(name_session_string).child("plan_reps2").setValue(reps2_session_string);

        //associated team
        dbref.child("Training Plans").child(name_session_string).child("Team").setValue("*");


        replace_sessions_fragment(new ManageTrainingSessionsFragment());
        fragmentCreate.setImageDrawable(getResources().getDrawable(R.drawable.plus_foreground));
    }
    public void show_teams_list(View v) {

        mAuth = FirebaseAuth.getInstance();
        //get data from database
        String email = mAuth.getCurrentUser().getUid().toString();

        FirebaseDatabase dataBaseFire  = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference dataBaseFireReference = dataBaseFire .getReference();
        Task task_ = dataBaseFireReference.child("Users").child(email).child("Teams").get();
        task_.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                try {
                    DataSnapshot dataSnapshot = (DataSnapshot) task_.getResult();

                    AlertDialog.Builder builder = new AlertDialog.Builder(TrainingSession.this);
                    builder.setTitle("Which Team do you want to assign this plan to?");
                    ArrayList<String> items = new ArrayList<String>();
                    HashMap<String,String> drawings = new HashMap<String, String>();
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        items.add(ds.getKey());
                        //drawings.put(ds.getKey(), (String)ds.getValue());
                    }
                    String[] itemsArray = items.toArray(new String[0]);
                    System.out.println("items: " + items);
                    builder.setItems(itemsArray, new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int canvas_index) {
                            // ao clicar numa equipa, associá-la na base de dados a um plano de treino

                            System.out.println("Estamos no plano " + current_plan);
                            System.out.println("Foi escolhida a equipa " + itemsArray[canvas_index]);
                            dataBaseFireReference.child("Training Plans").child(current_plan).child("Team").setValue(itemsArray[canvas_index]);


                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

    }

}