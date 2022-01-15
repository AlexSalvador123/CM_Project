package com.example.trackmysport;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Agenda extends AppCompatActivity {
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        mAuth = FirebaseAuth.getInstance();
        final ArrayList<ArrayList<String>> listOfEvents = new ArrayList<ArrayList<String>>();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference databaseReference = mDatabase.getReference();


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

                                Calendar nowPlus5 = Calendar.getInstance();
                                nowPlus5.add(Calendar.DAY_OF_MONTH, 5);
                                String newDate = sdf.format(nowPlus5.getTime());
                                if (verifyDate.before(nowPlus5)){
                                    ArrayList<String> EventList = new ArrayList<String>();
                                    EventList.add(sdf.format(verifyDate.getTime()));
                                    EventList.add(e.get("title").toString());
                                    EventList.add(ds.getKey());
                                    //System.out.println(EventList.toString());
                                    listOfEvents.add(EventList);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                System.out.println(listOfEvents.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}