package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class FAQs extends Activity {

    VideoView videoView;
    Button play;
    MediaController mdec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        play=findViewById(R.id.play);
        videoView = findViewById(R.id.videoView);
        mdec=new MediaController(this);

    }

    public void onplay(View view) {
        videoView.setVisibility(View.VISIBLE);
        String path="android.resource://com.example.drs/"+R.raw.vote;
        Uri uri=Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mdec);
        mdec.setAnchorView(videoView);
        videoView.start();
    }
}
