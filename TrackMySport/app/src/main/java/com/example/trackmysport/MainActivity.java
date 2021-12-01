package com.example.trackmysport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GoToMainPage (View view){
        Intent i = new Intent(this, MainPage.class);
        startActivity(i);
        finish();
    };

    public void GoToRegisterPage (View view){
        Intent i = new Intent(this, Register.class);
        startActivity(i);
        finish();
    };
}