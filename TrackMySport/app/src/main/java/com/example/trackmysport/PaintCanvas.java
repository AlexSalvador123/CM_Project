package com.example.trackmysport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class PaintCanvas extends View implements View.OnTouchListener{

    private ArrayList<Paint> paintsList;
    private ArrayList<Path> pathsList;
    private boolean first_draw = true;
    private Bitmap bitmap = null;
    private int backGroundColor = Color.TRANSPARENT;
    private GestureDetector mGestureDetector;

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintsList = new ArrayList<Paint>();
        pathsList = new ArrayList<Path>();
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        initPaint();

    }

    public PaintCanvas(Context context, AttributeSet attrs, GestureDetector mGestureDetector) {
        super(context, attrs);
        this.mGestureDetector = mGestureDetector;
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(bitmap != null){
            canvas.drawBitmap(bitmap,0,0,null);
            this.performClick();
        }
        for(int i = 0; i<this.paintsList.size(); i++){
            canvas.drawPath(this.pathsList.get(i), this.paintsList.get(i));
        }
    }

    @Override
    public boolean performClick(){
        return super.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false; // let the event go to the rest of the listeners
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initPaint();
                this.pathsList.get(this.pathsList.size()-1).moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                this.pathsList.get(this.pathsList.size()-1).lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }


    private void initPaint(){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        this.paintsList.add(paint);
        this.pathsList.add(new Path());
    }

    public void changeStrokeColor(){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

    }

    public void undo(){
        try{
            this.paintsList.remove(this.paintsList.size()-1);
            this.pathsList.remove(this.pathsList.size()-1);
            invalidate();
        } catch (Exception e) {
            invalidate();
        }
    }

    public void erase(){
        this.pathsList.clear();
        this.paintsList.clear();
        invalidate();
    }
}