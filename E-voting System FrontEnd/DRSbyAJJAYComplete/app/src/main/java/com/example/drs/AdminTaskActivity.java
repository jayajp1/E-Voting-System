package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class AdminTaskActivity extends Activity {
    Button ad_btn_insert_candidate,ad_btnannounce_result,ad_btn_anaylis,btnadlogout,ad_btn_votestatus;
    TextView tvadname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_task);
        ad_btn_insert_candidate=findViewById(R.id.ad_btn_insert_candidate);
       // ad_btnannounce_result=findViewById(R.id.ad_btnannounce_result);
        ad_btn_anaylis=findViewById(R.id.ad_btn_anaylis);
        tvadname=findViewById(R.id.adname);
        btnadlogout=findViewById(R.id.btnAdminlogout);
        ad_btn_votestatus=findViewById(R.id.ad_btn_votestaus);
        GlobalUser globalUser = (GlobalUser)getApplicationContext();
        String firstname = globalUser.getFirstname();
        tvadname.setText("Welcome "+firstname);


        ad_btn_votestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), AdminSideBeforeVote.class);
                startActivity(i);
            }
        });
        ad_btn_insert_candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Insertcandidatedetails.class);
                startActivity(i);
            }
        });

        ad_btn_anaylis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ChartChoice.class);
                startActivity(i);
            }
        });
        btnadlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(),loginpage.class);
                startActivity(ii);
            }
        });
    }
}


