package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChartChoice extends AppCompatActivity {

    Button barchart,piechart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_choice);
    barchart=findViewById(R.id.adbarchart);
    piechart=findViewById(R.id.adpiechart);


    barchart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(getApplicationContext(),adbarchart.class);
            startActivity(i);
        }
    });

    piechart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(getApplicationContext(),adpiechart.class);
            startActivity(i);
        }
    });
    }
}
