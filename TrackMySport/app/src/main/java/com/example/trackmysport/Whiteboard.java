package com.example.trackmysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.R.drawable;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;

public class Whiteboard extends AppCompatActivity {
    private static final String TAG = "TAG";
    private int fragment = 0;
    private PaintCanvas paintCanvas;
    private String background = "white";
    private FloatingActionButton undo;
    private FloatingActionButton delete;
    private FloatingActionButton save;
    private FloatingActionButton openPreferences;
    private BottomNavigationView bottomNavigationView;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiteboard);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        bottomNavigationView.setSelectedItemId(R.id.teach);
        setTitle("Whiteboard");
        ConstraintLayout whiteboard = findViewById(R.id.whiteboard);
        paintCanvas = new PaintCanvas(Whiteboard.this, null);
        whiteboard.addView(paintCanvas);
        preferences = new Preferences();
        undo = findViewById(R.id.undo);
        delete = findViewById(R.id.delete);
        openPreferences = findViewById(R.id.openPreferences);
    }

    public void openPreferences(View view){
        if(fragment == 0){
            setTitle("Preferences");
            undo.hide();
            delete.hide();
            openPreferences.setImageDrawable(getResources().getDrawable(R.drawable.baseline_arrow_back_20));
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.whiteboard, preferences);
            transaction.addToBackStack(TAG);
            transaction.commit();
            fragment = 1;
        }
        else{
            undo.show();
            delete.show();
            openPreferences.setImageDrawable(getResources().getDrawable(R.drawable.baseline_settings_20));
            setTitle("Whiteboard");
            getSupportFragmentManager().popBackStack();
            fragment = 0;
            ConstraintLayout whiteboard = findViewById(R.id.whiteboard);
            switch (background){
                case "white":
                    whiteboard.setBackgroundColor(Color.WHITE);
                    System.out.println("1");
                    break;
                case "football":
                    whiteboard.setBackgroundResource(R.drawable.football);
                    System.out.println("2");
                    break;
                case "handball":
                    whiteboard.setBackgroundResource(R.drawable.handball);
                    System.out.println("3");
                    break;
                case "basketball":
                    whiteboard.setBackgroundResource(R.drawable.basketball);
                    System.out.println("4");
                    break;
            }
            whiteboard.invalidate();
        }

    }

    public void changeBackground(View view){
        background = view.getResources().getResourceName(view.getId()).replace("com.example.trackmysport:id/","");
    }

    public void undo(View view){
        paintCanvas.undo();
    }

    public void erase(View view){
        paintCanvas.erase();
    }

    public void changeColor(View view){
        String id = view.getResources().getResourceName(view.getId());
        int color = Color.parseColor(id.replace("com.example.trackmysport:id/b","#"));
        paintCanvas.changeStrokeColor(color);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.player:
                            Intent i1 = new Intent(Whiteboard.this, TrainingSession.class);
                            startActivity(i1);
                            return true;
                        case R.id.teams:
                            Intent i2 = new Intent(Whiteboard.this, Team.class);
                            startActivity(i2);
                            return true;
                        case R.id.teach:

                            return true;
                        case R.id.agenda:
                            Intent i4 = new Intent(Whiteboard.this, Agenda.class);
                            startActivity(i4);
                            return true;
                        case R.id.profile:
                            Intent i5 = new Intent(Whiteboard.this, MainPage.class);
                            startActivity(i5);
                            return true;
                    }
                    return false;
                }
            };
}