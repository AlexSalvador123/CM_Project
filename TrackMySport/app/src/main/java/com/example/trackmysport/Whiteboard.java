package com.example.trackmysport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class Whiteboard extends AppCompatActivity {
    private static final String TAG = "TAG";
    private int fragment = 0;
    private PaintCanvas paintCanvas;
    private String background = "white";
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiteboard);
        ConstraintLayout whiteboard = findViewById(R.id.whiteboard);
        paintCanvas = new PaintCanvas(Whiteboard.this, null);
        whiteboard.addView(paintCanvas);
        preferences = new Preferences();
    }

    public void openPreferences(View view){
        if(fragment == 0){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.whiteboard, preferences);
            transaction.addToBackStack(TAG);
            transaction.commit();
            fragment = 1;
        }
        else{
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
    };

}