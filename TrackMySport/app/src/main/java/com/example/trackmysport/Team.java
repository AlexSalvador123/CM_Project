package com.example.trackmysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Team extends AppCompatActivity {

    FloatingActionButton fragmentCreate;
    Button teamCreated;
    boolean frag= true;
    private static FirebaseDatabase fd;
    private static DatabaseReference dr;
    TextView dateText ;
    public static String teamName = "a";
    private FirebaseAuth mAuth;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_team);
        setTitle("Teams");
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        bottomNavigationView.setSelectedItemId(R.id.teams);

        replaceFragment(new manageTeamsFragment());
        fragmentCreate = findViewById(R.id.floatingActionButton);
        /*fragmentCreate.setOnClickListener(new View.OnClickListener() {
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
        );*/

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.player:
                            Intent i1 = new Intent(Team.this, TrainingSession.class);
                            startActivity(i1);
                            return true;
                        case R.id.teams:

                            return true;
                        case R.id.teach:
                            Intent i3 = new Intent(Team.this, DrawActivities.class);
                            startActivity(i3);
                            return true;
                        case R.id.agenda:
                            Intent i4 = new Intent(Team.this, Agenda.class);
                            startActivity(i4);
                            return true;
                        case R.id.profile:
                            Intent i5 = new Intent(Team.this, MainPage.class);
                            startActivity(i5);
                            return true;

                    }
                    return false;
                }
            };

    public void onClickFloating(View view) {
          if (frag==true) {
              replaceFragment(new CreateTeamFragment());
              frag=false;
          }else{
              replaceFragment(new manageTeamsFragment());
              frag=true;
          }
    }
    public void onClickEvent(View view) {

        replaceFragment(new Events());
    }
    public void onClickElements(View view) {

        replaceFragment(new Elements());
    }

    public void BackTeam(View view) {
        replaceFragment(new manageTeamsFragment());
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
        DatabaseReference dbref = fd.getReference("Users").child(mAuth.getCurrentUser().getUid());
        String [] username = new String[1];
        EditText editText = (EditText) findViewById(R.id.nameTeam);
        String name = editText.getText().toString();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("-----");
                HashMap s = (HashMap) snapshot.getValue();
                System.out.println(s.get("name"));

                username[0] = (String) s.get("name");
                dr.child("Teams").child(name).child("teamname").setValue(name);
                dr.child("Users").child(mAuth.getCurrentUser().getUid()).child("Teams").child(name).child("name").setValue(name);
                dr.child("Teams").child(name).child("members").child(mAuth.getCurrentUser().getUid()).child("id").setValue(mAuth.getCurrentUser().getUid());
                dr.child("Teams").child(name).child("members").child(mAuth.getCurrentUser().getUid()).child("name").setValue(username[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        replaceFragment(new manageTeamsFragment());
    }
    public void eliminateTeam(View view){
        fd = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
        dr = fd.getReference();
        TextView editText = (TextView) findViewById(R.id.titleTeamName);
        String name = editText.getText().toString();
        dr.child("Teams").child(name).removeValue();
        dr.child("Users").child(mAuth.getCurrentUser().getUid()).child("Teams").child(name).removeValue();
        replaceFragment(new manageTeamsFragment());
    }

    /*public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Team.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String date = "month/day/year: " + month + "/" + dayOfMonth + "/" + year;
                        dateText.setText(date);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void pickDate(View view){
        System.out.println("entrei");
        showDatePickerDialog();
    }*/



    public void createEvent(View v){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final View popV = getLayoutInflater().inflate(R.layout.eventpop,null);
        final String[] date = new String[1];
        popV.findViewById(R.id.pickDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Team.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                dateText = popV.findViewById(R.id.showDatePicked);
                                int correctmonth = month+1;
                                String date1 = correctmonth + "/" + dayOfMonth + "/" + year;
                                date[0] = date1;
                                dateText.setText(date[0]);
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        builder.setTitle("Event")
                .setView(popV)
                .setNegativeButton("cancel", null)
                .setPositiveButton("done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText edit1 = (EditText) popV.findViewById(R.id.editText);
                        Editable editable1 = edit1.getText();
                        String title = editable1.toString();

                        EditText edit2 = (EditText) popV.findViewById(R.id.editTextPostalAddress);
                        Editable editable2 = edit2.getText();
                        String address = editable2.toString();
                        fd = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
                        dr = fd.getReference();
                        String key = dr.push().getKey();
                        //TextView editText = (TextView) findViewById(R.id.titleTeamName);
                        //String name = editText.getText().toString();
                        String name = Team.teamName;
                        dr.child("Teams").child(name).child("events").child(key).child("date").setValue(date[0]);
                        dr.child("Teams").child(name).child("events").child(key).child("title").setValue(title);
                        dr.child("Teams").child(name).child("events").child(key).child("address").setValue(address);

                    }
                });
        builder.show();
    }


    public void addElement(View v) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final View popE = getLayoutInflater().inflate(R.layout.addelementpop, null);

        builder.setTitle("Event")
                .setView(popE)
                .setNegativeButton("cancel", null)
                .setPositiveButton("done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edit = (EditText) popE.findViewById(R.id.editTextEmailAddress4);
                        Editable editable = edit.getText();
                        String email = editable.toString();

                        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
                        DatabaseReference databaseReference = mDatabase.getReference();
                        //TextView editText = (TextView) findViewById(R.id.titleTeamName);
                        //String name = editText.getText().toString();
                        String name = Team.teamName;
                        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Boolean finish = false;
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    UserData data = ds.getValue(UserData.class);

                                    if (email.equals(data.getEmail()) && !finish){
                                        databaseReference.child("Users").child(ds.getKey()).child("Teams").child(name).child("name").setValue(name);
                                        databaseReference.child("Teams").child(name).child("members").child(ds.getKey()).child("id").setValue(ds.getKey());
                                        databaseReference.child("Teams").child(name).child("members").child(ds.getKey()).child("name").setValue(data.getName());
                                        finish = true;
                                    }

                                }
                                if (!finish){
                                    System.out.println("ola bom dia");
                                        Toast.makeText(Team.this,"There's no users with this email address",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                });
        builder.show();
    }

};