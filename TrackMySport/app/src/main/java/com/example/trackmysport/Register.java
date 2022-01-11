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
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Toast toast;
    private static FirebaseDatabase firebasedb;
    private static DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.accountType);
        final String accountType = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).
                                    getText().toString();


        String email = emailAddress.getText().toString();
        String password = password1.getText().toString();
        String cPassword = password2.getText().toString();
        if(validate(email, password,cPassword)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                firebasedb = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
                                dbref = firebasedb.getReference();
                                DatabaseReference userDB = dbref.child("Users").child(user.getUid());
                                userDB.child("name").setValue(name.getText().toString());
                                userDB.child("phoneNumber").setValue(phoneNumber.getText().toString());
                                userDB.child("accountType").setValue(accountType);
                                userDB.child("email").setValue(email);

                            } else {
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    public void updateUI(FirebaseUser account){
        if(account != null){
            toast = Toast.makeText(this,"You signed up successfully",Toast.LENGTH_LONG);
            startActivity(new Intent(this,MainPage.class));

        }else {
            toast = Toast.makeText(this,"Error",Toast.LENGTH_LONG);
        }

        toast.show();
    }

    private boolean validate(String email, String password1, String password2) {
        boolean temp=true;
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        if(!password1.equals(password2)){
            Toast.makeText(this,"Password Not matching",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        return temp;
    }
}
