package com.example.trackmysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

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
    public void onClickFloating(View view) {
          if (frag==true) {
              replaceFragment(new CreateTeamFragment());
              frag=false;
          }else{
              replaceFragment(new manageTeamsFragment());
              frag=true;
          }
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
    public void eliminateTeam(View view){
        fd = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
        dr = fd.getReference();
        TextView editText = (TextView) findViewById(R.id.titleTeamName);
        String name = editText.getText().toString();
        dr.child("Teams").child(name).removeValue();
        replaceFragment(new manageTeamsFragment());
    }

    public void createEvent(View v){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        final View popV = getLayoutInflater().inflate(R.layout.eventpop,null);
        builder.setTitle("Event")
                .setView(popV)
                .setNegativeButton("cancel", null)
                .setPositiveButton("done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edit = (EditText) popV.findViewById(R.id.editTextDate);
                        Editable editable = edit.getText();
                        String date = editable.toString();
                        EditText edit1 = (EditText) popV.findViewById(R.id.editText);
                        Editable editable1 = edit1.getText();
                        String title = editable1.toString();
                        EditText edit2 = (EditText) popV.findViewById(R.id.editTextPostalAddress);
                        Editable editable2 = edit2.getText();
                        String address = editable2.toString();
                        fd = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
                        dr = fd.getReference();
                        String key = dr.push().getKey();
                        TextView editText = (TextView) findViewById(R.id.titleTeamName);
                        String name = editText.getText().toString();
                        dr.child("Teams").child(name).child("events").child(key).child("date").setValue(date);
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
                        TextView editText = (TextView) findViewById(R.id.titleTeamName);
                        String name = editText.getText().toString();
                        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Boolean finish = false;
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    UserData data = ds.getValue(UserData.class);

                                    if (email.equals(data.getEmail()) && !finish){
                                        databaseReference.child("Users").child(ds.getKey()).child("Teams").child("name").setValue(name);
                                        databaseReference.child("Teams").child(name).child("members").child("id").setValue(ds.getKey());
                                        finish = true;
                                    }

                                }
                                System.out.println(finish);
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