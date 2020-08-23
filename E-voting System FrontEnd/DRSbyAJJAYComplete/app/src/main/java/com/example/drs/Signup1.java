package com.example.drs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.drs.Model.Voter;
import com.example.drs.resources.VoterResource;
import com.example.drs.utils.RetrofitInst;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Signup1 extends Activity implements AdapterView.OnItemSelectedListener {

    EditText fname,mobile,bdate,address,uname,password,cpassword,age,email,acard,ecard;
    Spinner s1,s2,s3;
    Button personal,verfication,submitvoter,reset,fingerprint;
    RadioGroup gen;
    RadioButton radioButton;
    AwesomeValidation awesomeValidation;
    Voter voter=new Voter();

    public void SetData()
    {
        voter.setAdharcard(Integer.parseInt(acard.getText().toString()));
        voter.setAddress(address.getText().toString());
        voter.setAge(Integer.parseInt(age.getText().toString()));
        voter.setCity(s3.getSelectedItem().toString());
        voter.setState(s2.getSelectedItem().toString());
        voter.setCountry(s1.getSelectedItem().toString());
        voter.setDob(bdate.getText().toString());
        voter.setElectionCard(ecard.getText().toString());
        voter.setEmailId(email.getText().toString());
        voter.setFirstName(fname.getText().toString());
        voter.setPassword(password.getText().toString());
        voter.setUserName(uname.getText().toString());
        int selectedId = gen.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        voter.setGender(radioButton.getText().toString());
        voter.setMobileNo(mobile.getText().toString());
        voter.setVdone("false");
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup1);

        final Executor executor= Executors.newSingleThreadExecutor();
        final BiometricPrompt biometricPrompt= new BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Authentication")
                .setDescription("This is require for Security..")
                .setNegativeButton("Cancel",executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).build();
        final Signup1 signup1=this;

        validate();
        s1 = (Spinner)findViewById(R.id.ucountry);
        s2 = (Spinner)findViewById(R.id.ustate);
        s3= (Spinner)findViewById(R.id.ucity);
        s2.setOnItemSelectedListener(this);

       // fingerprint=findViewById(R.id.fingerprint);
        fname=findViewById(R.id.ufname);
        mobile=findViewById(R.id.umobileno);
        bdate=findViewById(R.id.udob);
        address=findViewById(R.id.uAddress);
        uname=findViewById(R.id.usname);
        password=findViewById(R.id.uspass);
        cpassword=findViewById(R.id.uscpass);
        age=findViewById(R.id.uage);
        email=findViewById(R.id.uemail);
        acard=findViewById(R.id.uadhar);
        ecard=findViewById(R.id.uelection);
        Log.d("lll","bye");
        personal=findViewById(R.id.bpersonal);
        verfication=findViewById(R.id.bverification);
        submitvoter=findViewById(R.id.brsubvt);
        reset=findViewById(R.id.breset);
        Log.d("lll","hii");

        submitvoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()){
                Log.d("lll", "heyaaa");
                SetData();
                Log.d("lll", "heyy");
                Gson gson = new Gson();
                String voterstring = gson.toJson(voter);
                Log.d("lll", "heyaj");
              //  awesomeValidation.validate();
                Retrofit r = RetrofitInst.getRetrofit();
                VoterResource voterResource = r.create(VoterResource.class);
                Call<Integer> c = voterResource.persist(voter);
                c.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response)
                    {
                        Integer result=response.body();
                        Log.d("hellohii",""+(result==-1)+"\t"+(result.intValue()==-1));

                        if(result==-1)
                        {
                            uname=findViewById(R.id.usname);
                            Toast.makeText(Signup1.this, "Username is already taken...", Toast.LENGTH_LONG).show();
                            uname.setError("Username is already taken...");
                        }
                        else if(result==1)
                        {
                            if (awesomeValidation.validate()){
                                Toast.makeText(Signup1.this, "Registration Complete...", Toast.LENGTH_SHORT).show();
                                Intent ii=new Intent(getApplicationContext(),loginpage.class);
                                startActivity(ii);
                            }
                            else
                            {
                                Toast.makeText(Signup1.this, "Registration failed..", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(result!=1)
                        {
                            Toast.makeText(Signup1.this, "You are Not register user", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(Signup1.this, "Connection Error..", Toast.LENGTH_SHORT).show();
                    }
                });

            }}

        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname.setText("");
                mobile.setText("");
                bdate.setText("");
                address.setText("");
                uname.setText("");
                password.setText("");
                cpassword.setText("");
                age.setText("");
                email.setText("");
                acard.setText("");
                ecard.setText("");
            }
        });

    }
    private void validate() {
        fname=findViewById(R.id.ufname);
        mobile=findViewById(R.id.umobileno);
        bdate=findViewById(R.id.udob);
        address=findViewById(R.id.uAddress);
        s1 = (Spinner)findViewById(R.id.ucountry);
        s2 = (Spinner)findViewById(R.id.ustate);
        s3= (Spinner)findViewById(R.id.ucity);

        uname=findViewById(R.id.usname);
        password=findViewById(R.id.uspass);
        cpassword=findViewById(R.id.uscpass);
        age=findViewById(R.id.uage);
        email=findViewById(R.id.uemail);
        acard=findViewById(R.id.uadhar);
        ecard=findViewById(R.id.uelection);
        gen=(RadioGroup)findViewById(R.id.ugender);
        int id = gen.getCheckedRadioButtonId();

        if(id==-1)
        {
            //Toast.makeText(Signup1.this,"Gender error",Toast.LENGTH_LONG).show();
        }

        if (s1.getSelectedItem().toString().trim().equals("<--Select-->")) {
            Toast.makeText(Signup1.this, "Plaese select something..", Toast.LENGTH_LONG).show();
        }
        //  if (s2.getSelectedItem().toString().trim().equals("Select")) {
        //    Toast.makeText(Signup1.this, "Error", Toast.LENGTH_LONG).show();
        //}
        // if (s3.getSelectedItem().toString().trim().equals("Select")) {
        //   Toast.makeText(Signup1.this, "Error", Toast.LENGTH_LONG).show();
        // }
        //String regexPassword = "(?=.[a-z])(?=.[A-Z])(?=.[\\d])(?=.[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{6,10}$";
        awesomeValidation.addValidation(Signup1.this, R.id.ufname, "[a-zA-Z][a-zA-Z ]*", R.string.ufname);
        awesomeValidation.addValidation(Signup1.this, R.id.umobileno, "^\\+(?:[0-9]?){6,14}[0-9]$", R.string.umobile);
        awesomeValidation.addValidation(Signup1.this, R.id.udob, "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$", R.string.udate);
        awesomeValidation.addValidation(Signup1.this, R.id.uAddress, "[a-zA-Z][a-zA-Z ]*", R.string.uadd);
        awesomeValidation.addValidation(Signup1.this, R.id.usname, "^[vt]{2}[_]{1}[a-z,A-Z]{4,15}$", R.string.uvname);
        awesomeValidation.addValidation(Signup1.this, R.id.uspass, regexPassword, R.string.upass);
        awesomeValidation.addValidation(Signup1.this, R.id.uscpass,R.id.uspass, R.string.ucopass);
        awesomeValidation.addValidation(Signup1.this, R.id.uage, "^([1][8-9]|[2-9][0-9]|)$", R.string.uag);
        awesomeValidation.addValidation(Signup1.this, R.id.uemail, Patterns.EMAIL_ADDRESS, R.string.uem);
        awesomeValidation.addValidation(Signup1.this, R.id.uadhar,"[0-9]{6,10}", R.string.uaad);
        awesomeValidation.addValidation(Signup1.this, R.id.uelection,"[0-9]{6,10}", R.string.uele);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // TODO Auto-generated method stub
        String sp1= String.valueOf(s2.getSelectedItem());
       // Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if(sp1.contentEquals("<--Select-->")) {
            List<String> list = new ArrayList<String>();
            list.add("<--Select-->");
            ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter5.notifyDataSetChanged();
            s3.setAdapter(dataAdapter5);
        }
        if(sp1.contentEquals("Gujarat")) {
            List<String> list = new ArrayList<String>();
            list.add("<--Select-->");
            list.add("Vadodara");
            list.add("Surat");
            list.add("Ahemdabad");
            list.add("Rajkot");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            s3.setAdapter(dataAdapter);
        }
        if(sp1.contentEquals("Maharastra")) {
            List<String> list = new ArrayList<String>();
            list.add("<--Select-->");
            list.add("Mumbai");
            list.add("Pune");
            list.add("Nashik");
            list.add("Shirdi");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            s3.setAdapter(dataAdapter2);
        }

        if(sp1.contentEquals("Madhya Pradesh")) {
            List<String> list = new ArrayList<String>();
            list.add("<--Select-->");
            list.add("Bhopal");
            list.add("Indore");
            list.add("Ujjain");
            list.add("Ratlam");
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter3.notifyDataSetChanged();
            s3.setAdapter(dataAdapter3);
        }

        if(sp1.contentEquals("Rajasthan")) {
            List<String> list = new ArrayList<String>();
            list.add("<--Select-->");
            list.add("Kota");
            list.add("Jaipur");
            list.add("Udaipur");
            list.add("Jaisalmer");
            ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter4.notifyDataSetChanged();
            s3.setAdapter(dataAdapter4);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}