package com.example.trackmysport;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransparentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransparentFragment extends Fragment{

    private ArrayList<Paint> paintsList;
    private ArrayList<Path> pathsList;
    private Bitmap bitmap = null;
    private PaintCanvas paint;

    public TransparentFragment() {
        // Required empty public constructor
    }

    public static TransparentFragment newInstance(String param1, String param2) {
        TransparentFragment fragment = new TransparentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paint = new PaintCanvas(getContext(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_transparent, container, false);
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_transparent, null);
        ConstraintLayout constraintLayout = (ConstraintLayout) root.findViewById(R.id.frameLayout2);
        constraintLayout.addView(paint);
        return root;
    }


}