package com.example.trackmysport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentContainer;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;


import com.example.trackmysport.databinding.FragmentColorsVideoBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Videos extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGE = 17;
    private MediaController mMediaController;
    private int position = 1;
    colorsVideo colorsVideo;
    private int fragment = 0;
    private static final String TAG = "TAG";
    PaintCanvas paintCanvas;
    ImageView selectedImage;
    VideoView selectedVideo;
    private FloatingActionButton undoPaintingVideo;
    private FloatingActionButton deletePaintingVideo;
    private FloatingActionButton preferencesVideo;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        selectedImage = (ImageView) findViewById(R.id.selectedImage);
        selectedVideo = (VideoView) findViewById(R.id.selectedVideo);
        colorsVideo = new colorsVideo();
        ConstraintLayout videos = findViewById(R.id.video);
        paintCanvas = new PaintCanvas(Videos.this, null);
        videos.addView(paintCanvas);
        undoPaintingVideo = findViewById(R.id.undoPaintingVideo);
        deletePaintingVideo = findViewById(R.id.deletePaintingVideo);
        preferencesVideo = findViewById(R.id.preferencesVideo);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.camera);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePicture(v);
            }
        });

        Button gallery = (Button) findViewById(R.id.openGallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openGallery(v);
            }
        });
    }




    private void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }

    private void openGallery(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("*/*");
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            String d = data.getData().toString();
            Uri uri = data.getData();
            if(uri.toString().contains("video")){
                selectedVideo.bringToFront();
                selectedVideo.setVideoURI(data.getData());
                paintCanvas.bringToFront();
                // Set video link (mp4 format )
                selectedVideo.setVideoURI(uri);
                selectedVideo.start();
            }
            else if(uri.toString().contains("image")){
                selectedImage.bringToFront();
                selectedImage.setImageURI(data.getData());
                paintCanvas.bringToFront();
            }
        }
    }


    public void openPreferencesVideo(View view){
        if(fragment == 0){
            undoPaintingVideo.hide();
            deletePaintingVideo.hide();
            preferencesVideo.setImageDrawable(getResources().getDrawable(R.drawable.baseline_arrow_back_20));
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.video, colorsVideo);
            transaction.addToBackStack(TAG);
            transaction.commit();
            fragment = 1;
        }
        else{
            undoPaintingVideo.show();
            deletePaintingVideo.show();
            preferencesVideo.setImageDrawable(getResources().getDrawable(R.drawable.baseline_settings_20));
            getSupportFragmentManager().popBackStack();
            fragment = 0;
            ConstraintLayout colorsVideo = findViewById(R.id.video);
            colorsVideo.invalidate();
        }

    }

    public void changeColor(View view){
        String id = view.getResources().getResourceName(view.getId());
        int color = Color.parseColor(id.replace("com.example.trackmysport:id/b","#"));
        paintCanvas.changeStrokeColor(color);
    }

    public void undo(View view){
        paintCanvas.undo();
    }

    public void erase(View view){
        paintCanvas.erase();
    }

}

