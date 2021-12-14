package com.example.trackmysport;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Toast toast;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    public void createAccount(View view){
        EditText emailAddress = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        EditText name = (EditText)findViewById(R.id.editTextTextPersonName);
        EditText password1 = (EditText)findViewById(R.id.editTextTextPassword2);
        EditText password2 = (EditText)findViewById(R.id.editTextTextPassword3);
        EditText phoneNumber = (EditText)findViewById(R.id.editTextPhone);

        String email = emailAddress.getText().toString();
        String password = password1.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password)
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

    public void updateUI(FirebaseUser account){
        if(account != null){
            toast = Toast.makeText(this,"You signed up successfully",Toast.LENGTH_LONG);
            startActivity(new Intent(this,MainPage.class));

        }else {
            toast = Toast.makeText(this,"Error",Toast.LENGTH_LONG);
        }

        toast.show();
    }}
