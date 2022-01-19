package com.example.trackmysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.Map;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static FirebaseDatabase firebasedb;
    private static DatabaseReference dbref;
    private final String[] accountType = new String[1];
    private final String[] phoneNumber = new String[1];
    private final String[] name = new String[1];
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //get data from database
        String email = mAuth.getCurrentUser().getEmail().toString();

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
                ((TextView)findViewById(R.id.editPhoneNumber)).setText(phoneNumber[0]);
                ((TextView)findViewById(R.id.account)).setText(accountType[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Intent i = new Intent(Profile.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        setContentView(R.layout.activity_profile);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }


    public void logout(View view){
        mAuth.signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void changePassword(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Profile.this);
        alertDialog.setTitle("Change Password");
        final EditText oldPass = new EditText(Profile.this);
        final EditText newPass = new EditText(Profile.this);
        final EditText confirmPass = new EditText(Profile.this);

        oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        oldPass.setHintTextColor(Color.GRAY);
        newPass.setHintTextColor(Color.GRAY);
        confirmPass.setHintTextColor(Color.GRAY);

        oldPass.setHint("Old Password");
        newPass.setHint("New Password");
        confirmPass.setHint("Confirm Password");
        LinearLayout ll=new LinearLayout(Profile.this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(oldPass);

        ll.addView(newPass);
        ll.addView(confirmPass);
        alertDialog.setView(ll);
        alertDialog.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(mAuth.getCurrentUser().getEmail().toString(),
                                        oldPass.getText().toString());

                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(newPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                        "Password updated",Toast.LENGTH_SHORT);
                                                        toast.show();
                                                    } else {
                                                        Toast toast = Toast.makeText(getApplicationContext(),
                                                    "Error password not updated",Toast.LENGTH_SHORT);
                                                        toast.show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast toast = Toast.makeText(getApplicationContext(),
                                                    "Error auth failed",Toast.LENGTH_SHORT);
                                            toast.show();
                                            System.out.println(task.getResult());
                                        }
                                    }
                                });

                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void changeAccountType(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Profile.this);
        alertDialog.setTitle("Change Account Type");

        LinearLayout ll=new LinearLayout(Profile.this);
        ll.setOrientation(LinearLayout.VERTICAL);

        RadioGroup rg = new RadioGroup(Profile.this);
        RadioButton coach = new RadioButton(Profile.this);
        coach.setText("Coach");
        RadioButton athlete = new RadioButton(Profile.this);
        athlete.setText("Athlete");

        rg.addView(coach);
        rg.addView(athlete);
        if (accountType[0].equals("Coach")){
            coach.toggle();
        }
        else{
            athlete.toggle();
        }
        ll.addView(rg);

        alertDialog.setView(ll);

        alertDialog.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String newAccountType = accountType[0];
                        if(coach.isChecked()){
                            newAccountType = "Coach";
                        }
                        else{
                            newAccountType = "Athlete";
                        }
                        if(!newAccountType.equals(accountType[0])){
                            firebasedb = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
                            dbref = firebasedb.getReference();
                            DatabaseReference userDB = dbref.child("Users").child(mAuth.getUid());
                            userDB.child("accountType").setValue(newAccountType);
                        }
                        dialog.cancel();
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void changePhoneNumber(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Profile.this);
        alertDialog.setTitle("Change Phone Number");

        LinearLayout ll=new LinearLayout(Profile.this);
        ll.setOrientation(LinearLayout.VERTICAL);

        EditText phoneNumber = new EditText(Profile.this);
        phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

        ll.addView(phoneNumber);

        alertDialog.setView(ll);

        alertDialog.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        firebasedb = FirebaseDatabase.getInstance("https://trackmysport-ff56d-default-rtdb.europe-west1.firebasedatabase.app/");
                        dbref = firebasedb.getReference();
                        DatabaseReference userDB = dbref.child("Users").child(mAuth.getUid());
                        userDB.child("phoneNumber").setValue(phoneNumber.getText().toString());
                        dialog.cancel();
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.player:
                            Intent i1 = new Intent(Profile.this, TrainingSession.class);
                            startActivity(i1);
                            return true;
                        case R.id.teams:
                            Intent i2 = new Intent(Profile.this, Team.class);
                            startActivity(i2);
                            return true;
                        case R.id.teach:
                            Intent i3 = new Intent(Profile.this, DrawActivities.class);
                            startActivity(i3);
                            return true;
                        case R.id.agenda:
                            Intent i4 = new Intent(Profile.this, Agenda.class);
                            startActivity(i4);
                            return true;
                        case R.id.profile:
                            return true;
                    }
                    return false;
                }
            };
}