package com.example.trackmysport;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            GoToMainPage();
        }
    }
    @Override
    public void onBackPressed() {
        //do nothing
    }
    public void GoToMainPage (){
        Intent i = new Intent(this, MainPage.class);
        startActivity(i);
        finish();
    };
    public void GoToMainAgenda (){
        Intent i = new Intent(this, Agenda.class);
        startActivity(i);
        finish();
    };

    public void GoToRegisterPage (View view){
        Intent i = new Intent(this, Register.class);
        startActivity(i);
        //finish();
    };

    public void login(View view){
        EditText email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText password = (EditText) findViewById(R.id.editTextTextPassword);
        try {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                updateUI(null);
                            }
                        }
                    });
        }
        catch (Exception e){

        }

    }
    public void updateUI(FirebaseUser account){
        if(account != null){
            final boolean[] isCoach = {false};
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference databaseReference = mDatabase.getReference();
            databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //System.out.println(snapshot.getValue().getClass());
                    HashMap e = (HashMap) snapshot.getValue();

                    if (e.get("accountType").equals("Coach")){
                        GoToMainPage();
                    }else{
                        GoToMainAgenda();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
            toast = Toast.makeText(this,"Username or Password are incorrect",Toast.LENGTH_LONG);
            toast.show();
        }


    }
}