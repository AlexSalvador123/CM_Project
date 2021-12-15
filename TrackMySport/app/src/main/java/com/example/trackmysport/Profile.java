package com.example.trackmysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Map;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static FirebaseDatabase firebasedb;
    private static DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //get data from database
        String email = mAuth.getCurrentUser().getEmail().toString();
        final String[] accountType = new String[1];
        final String[] phoneNumber = new String[1];
        final String[] name = new String[1];

        firebasedb = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
        dbref = firebasedb.getReference("Users").child(mAuth.getCurrentUser().getUid());
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,Object> user = (Map<String, Object>) snapshot.getValue();
                accountType[0] = (String) user.get("accountType");
                phoneNumber[0] = (String)user.get("phoneNumber");
                name[0] = (String) user.get("name");
                ((TextView) findViewById(R.id.nameProfile)).setText(name[0]);
                ((TextView) findViewById(R.id.textViewEmailProfile)).setText(
                        mAuth.getCurrentUser().getEmail());
                ((EditText)findViewById(R.id.editTextPhone2)).setText(phoneNumber[0]);
                if(accountType[0].equals("Coach")){
                    ((RadioButton) findViewById(R.id.radioButton3)).toggle();
                }
                else{
                    ((RadioButton) findViewById(R.id.radioButton4)).toggle();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setContentView(R.layout.activity_profile);
    }

    public void logout(View view){
        mAuth.signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}