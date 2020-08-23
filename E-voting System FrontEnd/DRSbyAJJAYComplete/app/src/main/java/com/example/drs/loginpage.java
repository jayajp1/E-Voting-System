package com.example.drs;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import com.example.drs.Model.Admin;
import com.example.drs.Model.Voter;
import com.example.drs.resources.AdminResource;
import com.example.drs.resources.UserResource;
import com.example.drs.resources.VoterResource;
import com.example.drs.utils.RetrofitInst;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class loginpage extends Activity {
    Button login,register,btngenerate,btnverify,forgotpass;
    EditText uname,upassword,umobiile,uotp;
    AwesomeValidation awesomeValidation;
    FirebaseAuth mAuth;
    String codeSent;
    private AppCompatCheckBox checkbox;
    String UserCity,firstname,MobileNo,vdone;

    int AdharCard;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginpage);

        checkbox=findViewById(R.id.showtext);
        mAuth = FirebaseAuth.getInstance();
        btngenerate=findViewById(R.id.btngenerate);
        uname=findViewById(R.id.username);
        upassword=findViewById(R.id.upassword);
        umobiile=findViewById(R.id.umobileno);
        uotp=findViewById(R.id.uotp);

        forgotpass=findViewById(R.id.forgotpass);
        login=findViewById(R.id.blogin);
        register=findViewById(R.id.bregister);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    upassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    // hide password
                    upassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(),Forgotpassword.class);
                startActivity(ii);
                uname.setText("");
                upassword.setText("");
                umobiile.setText("");
                uotp.setText("");
            }
        });

        btngenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNum = umobiile.getText().toString();
                Log.d("VERIFICATION",mobileNum);
                if (mobileNum.isEmpty()){
                    umobiile.setError("Please enter Mobile number");
                    umobiile.requestFocus();
                    return;
                }else {
                    getVerificationCode();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                if(awesomeValidation.validate())
                {
                    String codeText = uotp.getText().toString();
                    if (codeText.isEmpty()){
                        uotp.setError("Please enter verification Code");
                        uotp.requestFocus();
                        return;
                    }else {
                        Retrofit r = RetrofitInst.getRetrofit();
                        if (uname.getText().toString().contains("vt_")) {
                            VoterResource voterResource = r.create(VoterResource.class);
                            Call<Voter> c = voterResource.AuthenticateVoter(uname.getText().toString(), upassword.getText().toString());

                            c.enqueue(new Callback<Voter>() {
                                @Override
                                public void onResponse(Call<Voter> call, Response<Voter> response) {
                                    Voter afterCall = response.body();
                                    if (afterCall != null) {
                                        String Password = "" + upassword.getText().toString().hashCode();
                                        //Log.d("sss", "onFailure: "+(uname.getText().toString()+(afterCall.getUserName())+ (upassword.getText().toString())+(afterCall.getPassword())));
                                        if ((uname.getText().toString().equals(afterCall.getUserName())) && (Password.equals(afterCall.getPassword()))) {
                                            UserCity = afterCall.getCity();
                                            AdharCard = afterCall.getAdharcard();
                                            firstname = afterCall.getFirstName();
                                            MobileNo = afterCall.getMobileNo();
                                            vdone = afterCall.getVdone();
                                            codeVerification();

                                            //Toast.makeText(loginpage.this, "Success", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(loginpage.this, "Invalid data", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Toast.makeText(loginpage.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Voter> call, Throwable t) {
                                    Toast.makeText(loginpage.this, "Please Enter valid data..", Toast.LENGTH_SHORT).show();
                                    //Log.d("sss", "onFailure: "+t);
                                }
                            });


                        } else if (uname.getText().toString().contains("ad_")) {
                            AdminResource adminResource = r.create(AdminResource.class);
                            Call<Admin> c = adminResource.AuthenticateAdmin(uname.getText().toString(), upassword.getText().toString());

                            c.enqueue(new Callback<Admin>() {
                                @Override
                                public void onResponse(Call<Admin> call, Response<Admin> response) {
                                    Admin afterCall = response.body();
                                    if (afterCall != null) {
                                        //Log.d("sss", "onFailure: "+(uname.getText().toString()+(afterCall.getUserName())+ (upassword.getText().toString())+(afterCall.getPassword())));
                                        if ((uname.getText().toString().equals(afterCall.getUserName())) && (upassword.getText().toString().equals(afterCall.getPassword()))) {
                                            AdharCard = Integer.parseInt(afterCall.getAdhar());
                                            firstname = afterCall.getFullName();
                                            codeVerification();
                                            //Toast.makeText(loginpage.this, "Success", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(loginpage.this, "Invalid data", Toast.LENGTH_SHORT).show();

                                        }
                                    } else {
                                        Toast.makeText(loginpage.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Admin> call, Throwable t) {
                                    Toast.makeText(loginpage.this, "Please Enter valid data..", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(loginpage.this, "Please Enter Correct Details", Toast.LENGTH_SHORT).show();
                        }

                    }
                }}
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(),RegistrationChoice.class);
                startActivity(ii);
                uname.setText("");
                upassword.setText("");
                umobiile.setText("");
                uotp.setText("");
            }
        });


    }
    public void getVerificationCode (){
        String phoneNumber = umobiile.getText().toString().trim();
        Log.d("VERIFICATION",phoneNumber+"hh");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                100,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential)
        {


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException) {

                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {

            }

            // ...
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            codeSent = verificationId;
        }
    };

    public void codeVerification (){
        String code = uotp.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);

    }
    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(loginpage.this, "Logiin Successfully...", Toast.LENGTH_SHORT).show();
                            if (awesomeValidation.validate()){
                                GlobalUser globalUser = (GlobalUser) getApplicationContext();
                                globalUser.setUsername(uname.getText().toString().trim());
                                if(uname.getText().toString().contains("vt_")) {
                                    globalUser.setCity(UserCity);
                                    globalUser.setMobileNo(MobileNo);
                                    globalUser.setVoteDone(vdone);
                                }
                                globalUser.setAdharcard(AdharCard);
                                globalUser.setFirstname(firstname);

                                if(uname.getText().toString().contains("vt_")) {
                                    Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(ii);
                                    finish();
                                }
                                else
                                {
                                    Intent ii = new Intent(getApplicationContext(), AdminTaskActivity.class);
                                    startActivity(ii);
                                    finish();
                                }
                            }
                            else
                            {
                                Toast.makeText(loginpage.this, "Please Enter login Detail..", Toast.LENGTH_SHORT).show();
                            }
                            //  startActivity(new Intent(loginpage.this,loginpage .class));

                        } else {
                            Toast.makeText(loginpage.this, "Login Failed", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(loginpage.this, "You entered a wrong code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void validate() {

        uname = findViewById(R.id.username);
        upassword = findViewById(R.id.upassword);
        umobiile = findViewById(R.id.umobileno);
        uotp = findViewById(R.id.uotp);
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{6,10}$";
        //String regexPassword = "(?=.[a-z])(?=.[A-Z])(?=.[\\d])(?=.[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(loginpage.this, R.id.username, "^[ad/vt]{2}[_]{1}[a-z,A-Z]{4,15}$", R.string.uname);
        awesomeValidation.addValidation(loginpage.this, R.id.umobileno, "^\\+(?:[0-9]?){6,14}[0-9]$", R.string.umobile);
        awesomeValidation.addValidation(loginpage.this, R.id.upassword, regexPassword, R.string.upass);

    }

}