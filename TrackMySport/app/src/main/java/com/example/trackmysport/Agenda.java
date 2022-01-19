package com.example.trackmysport;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Agenda extends AppCompatActivity{
    private FirebaseAuth mAuth;
    RecyclerView recyclerView1,recyclerView2,recyclerView3,recyclerView4;
    AdapterEvent adapter1,adapter2,adapter3,adapter4;
    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        mAuth = FirebaseAuth.getInstance();
        final ArrayList<ArrayList<String>> listOfEvents1 = new ArrayList<ArrayList<String>>();
        final ArrayList<ArrayList<String>> listOfEvents2 = new ArrayList<ArrayList<String>>();
        final ArrayList<ArrayList<String>> listOfEvents3 = new ArrayList<ArrayList<String>>();
        final ArrayList<ArrayList<String>> listOfEvents4 = new ArrayList<ArrayList<String>>();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference databaseReference = mDatabase.getReference();

        recyclerView1  = findViewById(R.id.RVdia1);
        recyclerView2  = findViewById(R.id.RVdia2);
        recyclerView3  = findViewById(R.id.RVdia3);
        recyclerView4  = findViewById(R.id.RVdia4);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        adapter1 = new AdapterEvent(this, listOfEvents1);
        recyclerView1.setAdapter(adapter1);
        adapter2 = new AdapterEvent(this, listOfEvents2);
        recyclerView2.setAdapter(adapter2);
        adapter3 = new AdapterEvent(this, listOfEvents3);
        recyclerView3.setAdapter(adapter3);
        adapter4 = new AdapterEvent(this, listOfEvents4);
        recyclerView4.setAdapter(adapter4);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        bottomNavigationView.setSelectedItemId(R.id.agenda);


        databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Teams").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    HashMap s = (HashMap) ds.getValue();
                    databaseReference.child("Teams").child(s.get("name").toString()).child("events").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            for (DataSnapshot ds2 : snapshot2.getChildren()) {
                                HashMap e = (HashMap) ds2.getValue();
                                //System.out.println(e.get("date"));
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                Date date = null;
                                try {
                                    date = sdf.parse(e.get("date").toString());

                                } catch (ParseException parseException) {
                                    parseException.printStackTrace();
                                }

                                Calendar verifyDate = Calendar.getInstance();
                                verifyDate.setTime(date);

                                Calendar nowPlus4 = Calendar.getInstance();
                                nowPlus4.add(Calendar.DAY_OF_MONTH, 4);
                                Calendar nowPlus3 = Calendar.getInstance();
                                nowPlus3.add(Calendar.DAY_OF_MONTH, 3);
                                Calendar nowPlus2 = Calendar.getInstance();
                                nowPlus2.add(Calendar.DAY_OF_MONTH, 2);
                                Calendar nowPlus1 = Calendar.getInstance();
                                nowPlus1.add(Calendar.DAY_OF_MONTH, 1);
                                Calendar now = Calendar.getInstance();
                                //System.out.println("verifyDate->"+sdf.format(verifyDate.getTime()));
                                //System.out.println("nowPlus4->"+sdf.format(nowPlus1.getTime()));
                                if (verifyDate.before(nowPlus1)) {
                                    ArrayList<String> EventList = new ArrayList<String>();
                                    EventList.add(sdf.format(verifyDate.getTime()));
                                    EventList.add(e.get("title").toString());
                                    EventList.add(ds.getKey());
                                    //System.out.println("------------->"+EventList.toString());
                                    listOfEvents1.add(EventList);
                                }else if(verifyDate.before(nowPlus2)){
                                    ArrayList<String> EventList = new ArrayList<String>();
                                    EventList.add(sdf.format(verifyDate.getTime()));
                                    EventList.add(e.get("title").toString());
                                    EventList.add(ds.getKey());
                                    //System.out.println("------------->"+EventList.toString());
                                    listOfEvents2.add(EventList);
                                }else if(verifyDate.before(nowPlus3)){
                                    ArrayList<String> EventList = new ArrayList<String>();
                                    EventList.add(sdf.format(verifyDate.getTime()));
                                    EventList.add(e.get("title").toString());
                                    EventList.add(ds.getKey());
                                    //System.out.println("------------->"+EventList.toString());
                                    listOfEvents3.add(EventList);
                                }else if(verifyDate.before(nowPlus4)){
                                    ArrayList<String> EventList = new ArrayList<String>();
                                    EventList.add(sdf.format(verifyDate.getTime()));
                                    EventList.add(e.get("title").toString());
                                    EventList.add(ds.getKey());
                                    //System.out.println("------------->"+EventList.toString());
                                    listOfEvents4.add(EventList);
                                }
                                /*System.out.println("list1->"+listOfEvents1.toString());
                                System.out.println("list2->"+listOfEvents2.toString());
                                System.out.println("list3->"+listOfEvents3.toString());
                                System.out.println("list4->"+listOfEvents4.toString());*/
                            }
                            adapter1.notifyDataSetChanged();
                            adapter2.notifyDataSetChanged();
                            adapter3.notifyDataSetChanged();
                            adapter4.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    System.out.println(item.getItemId());
                    switch(item.getItemId()){
                        case R.id.player:
                            Intent i1 = new Intent(Agenda.this, TrainingSession.class);
                            startActivity(i1);
                            return true;
                        case R.id.teams:
                            Intent i2 = new Intent(Agenda.this, Team.class);
                            startActivity(i2);
                            return true;
                        case R.id.teach:
                            Intent i3 = new Intent(Agenda.this, DrawActivities.class);
                            startActivity(i3);
                            return true;
                        case R.id.agenda:
                            return true;
                        case R.id.profile:
                            Intent i5 = new Intent(Agenda.this, Profile.class);
                            startActivity(i5);
                            return true;

                    }
                    return false;
                }
            };
}