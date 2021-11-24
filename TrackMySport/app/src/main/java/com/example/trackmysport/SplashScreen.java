package com.example.trackmysport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class SplashScreen extends AppCompatActivity {
    Handler h = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
            // if you are redirecting from a fragment then use getActivity() as the context.
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
        };
    };

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // The Runnable will be executed after the given delay time
        h.postDelayed(r, 3000);
    };

    public void GoToScreen (View view){
        h.removeCallbacks(r);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    };
};