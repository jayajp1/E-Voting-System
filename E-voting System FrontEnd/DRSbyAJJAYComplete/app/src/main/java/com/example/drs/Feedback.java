package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class Feedback extends Activity {

    Button btnfdsubmit,btnfdreset,back;
    EditText fdemail,fdsuugesstion;
    TextView startcoynt;
    AwesomeValidation awesomeValidation;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        btnfdreset=findViewById(R.id.btfdreset);
        btnfdsubmit=findViewById(R.id.btnfdsubmit);
        ratingBar=findViewById(R.id.ratingstar);
        fdsuugesstion=findViewById(R.id.fdsugeestion);
        startcoynt=findViewById(R.id.ratingcount);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        validate();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==1 || rating==1.5){
                    startcoynt.setText( "Bad");
                }
                else if(rating==2 || rating==2.5 ){
                    startcoynt.setText(" Nice");
                }
                else if(rating==3 ||rating==3.5 ){
                    startcoynt.setText(" Good");
                }
                else if(rating==4){
                    startcoynt.setText(" Very Good");
                }
                else {
                startcoynt.setText("Excellent");}
            }
        });
        btnfdreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fdsuugesstion.setText("");

            }
        });
        btnfdsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext(),"Thank you for feedback..",Toast.LENGTH_LONG).show();
                    Intent ii=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(ii);
                    fdsuugesstion.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Detail..", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void validate() {

        fdsuugesstion=findViewById(R.id.fdsugeestion);

        //String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        //awesomeValidation.addValidation(Feedback.this, R.id.fdemail, "[a-zA-Z0-9{5,20]+", R.string.uemail);
        awesomeValidation.addValidation(Feedback.this, R.id.fdsugeestion, "[a-zA-z0-9]{2,100}+", R.string.usugges);
    }
}
