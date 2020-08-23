package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class Signup3 extends AppCompatActivity {
    EditText fname,mobile,uname,password,cpassword,email,acard,adcard;
    Button submit,reset;
    AwesomeValidation awesomeValidation;
    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup3);
        validate();
        submit=findViewById(R.id.bsubmt);
        reset=findViewById(R.id.breset);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(awesomeValidation.validate()) {
                    Toast.makeText(getApplicationContext(), "Registraion Is Done...", Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(getApplicationContext(), loginpage.class);
                    startActivity(ii);
                }
                else
                    Toast.makeText(getApplicationContext(), "Please fill registration form", Toast.LENGTH_LONG).show();

            }

        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname.setText("");
                mobile.setText("");
                uname.setText("");
                password.setText("");
                cpassword.setText("");
                email.setText("");
                acard.setText("");
                adcard.setText("");

            }
        });

    }

    private void validate() {
        fname=findViewById(R.id.ufname);
        mobile=findViewById(R.id.umobileno);
        uname=findViewById(R.id.usname);
        password=findViewById(R.id.uspass);
        cpassword=findViewById(R.id.uscpass);
        email=findViewById(R.id.uemail);
        acard=findViewById(R.id.uadhar);
        adcard=findViewById(R.id.admincard);

        //String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(Signup3.this, R.id.ufname, "[a-zA-Z{5,20]+", R.string.uname);
        awesomeValidation.addValidation(Signup3.this, R.id.umobileno, "^\\+(?:[0-9]?){6,14}[0-9]$", R.string.umobile);
        awesomeValidation.addValidation(Signup3.this, R.id.usname, "[a-zA-Z0-9{5,20]+", R.string.uname);
        awesomeValidation.addValidation(Signup3.this, R.id.uspass, "[a-zA-Z0-9]{7,20}+", R.string.upass);
        awesomeValidation.addValidation(Signup3.this, R.id.uscpass,R.id.uspass, R.string.ucopass);
        awesomeValidation.addValidation(Signup3.this, R.id.uemail, Patterns.EMAIL_ADDRESS, R.string.uem);
        awesomeValidation.addValidation(Signup3.this, R.id.uadhar,"[0-9]{12}", R.string.uaad);
        awesomeValidation.addValidation(Signup3.this, R.id.admincard,"[0-9]{10}", R.string.uele);
    }
}
