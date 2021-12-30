package com.example.trackmysport;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Draw extends View {
    private ArrayList<Paint> paintsList = new ArrayList<Paint>();
    private ArrayList<Path> pathsList = new ArrayList<Path>();
    private Bitmap bitmap = null;

    public Draw(Context context) {
        super(context);
        initPaint();
    }

    public Draw(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public Draw(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap != null){
            canvas.drawBitmap(bitmap,0,0,null);
        }
        for(int i = 0; i<this.paintsList.size(); i++){
            canvas.drawPath(this.pathsList.get(i), this.paintsList.get(i));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.pathsList.get(this.pathsList.size()-1).moveTo(eventX, eventY);// updates the path initial point
                return true;
            case MotionEvent.ACTION_MOVE:
                this.pathsList.get(this.pathsList.size()-1).lineTo(eventX, eventY);// makes a line to the point each time this event is fired
                break;
            case MotionEvent.ACTION_UP:// when you lift your finger
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
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        this.paintsList.add(paint);
        this.pathsList.add(new Path());
    }

}