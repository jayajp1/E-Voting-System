package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Votedone extends AppCompatActivity {

    TextView vtdname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votedone);
        vtdname=findViewById(R.id.vtdunaame);
        GlobalUser globalUser = (GlobalUser) getApplicationContext();
        String firstname = globalUser.getFirstname();
        Log.d("HELLOPLEASE",firstname);
        vtdname.setText("Hey ");
    }
}
