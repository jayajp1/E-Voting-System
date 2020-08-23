package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.drs.Model.Admin;
import com.example.drs.resources.AdminResource;
import com.example.drs.resources.VoterResource;
import com.example.drs.utils.RetrofitInst;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Signup2 extends Activity {
    EditText fname,mobile,uname,password,cpassword,email,acard,adcard;
    Button submitt,reset;
    AwesomeValidation awesomeValidation;
    Admin admin=new Admin();
    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup2);

        validate();
        submitt=findViewById(R.id.bsubmiit);
        reset=findViewById(R.id.breset);

        submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(awesomeValidation.validate()) {
                    admin.setAdhar(adcard.getText().toString());
                    admin.setEmailId(email.getText().toString());
                    admin.setElectionCard(acard.getText().toString());
                    admin.setAdminId(Integer.parseInt(acard.getText().toString()));
                    admin.setFullName(fname.getText().toString());
                    admin.setMobileNo(mobile.getText().toString());
                    admin.setPassword(password.getText().toString());
                    admin.setUserName(uname.getText().toString());


                    Retrofit r = RetrofitInst.getRetrofit();
                    AdminResource adminResource = r.create(AdminResource.class);
                    Call<Integer> c = adminResource.persist(admin);

                    c.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(response.body()==-1)
                            {
                                Toast.makeText(Signup2.this, "Username is already taken...", Toast.LENGTH_LONG).show();
                                uname.setError("Username is already taken...");
                            }
                            if(response.body()==1)
                            {
                                Toast.makeText(getApplicationContext(), "Registraion Is Done...", Toast.LENGTH_LONG).show();
                                Intent ii = new Intent(getApplicationContext(), loginpage.class);
                                startActivity(ii);
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Connection Error...", Toast.LENGTH_LONG).show();
                        }
                    });


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

        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{6,10}$";
        awesomeValidation.addValidation(Signup2.this, R.id.ufname, "[a-zA-Z][a-zA-Z ]*", R.string.ufname);
        awesomeValidation.addValidation(Signup2.this, R.id.umobileno, "^\\+(?:[0-9]?){6,14}[0-9]$", R.string.umobile);
        awesomeValidation.addValidation(Signup2.this, R.id.usname, "^[ad]{2}[_]{1}[a-z,A-Z]{4,15}$", R.string.uname);
        awesomeValidation.addValidation(Signup2.this, R.id.uspass, regexPassword, R.string.upass);
        awesomeValidation.addValidation(Signup2.this, R.id.uscpass,R.id.uspass, R.string.ucopass);
        awesomeValidation.addValidation(Signup2.this, R.id.uemail, Patterns.EMAIL_ADDRESS, R.string.uem);
        awesomeValidation.addValidation(Signup2.this, R.id.uadhar,"[0-9]{10}", R.string.uaad);
        awesomeValidation.addValidation(Signup2.this, R.id.admincard,"[0-9]{10}", R.string.uele);
    }

}