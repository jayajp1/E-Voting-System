package com.example.drs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.drs.Model.Voter;
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

public class Forgotpassword extends Activity {

    Button btnfpsubmit,btnfpreset,btngenerate,btnback;
    EditText username,mobile,otp,cpass,pass;
    FirebaseAuth mAuth;
    AwesomeValidation awesomeValidation;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        mAuth = FirebaseAuth.getInstance();
        pass=findViewById(R.id.fppass);
        cpass=findViewById(R.id.fpcpass);
        otp=findViewById(R.id.fpotp);
        btnback=findViewById(R.id.btnback);
        username=findViewById(R.id.fpusername); validate();
        mobile=findViewById(R.id.fpmobileno);
        btnfpsubmit=findViewById(R.id.btnfpsubmit);
        btnfpreset=findViewById(R.id.btnfpreset);
        btngenerate=findViewById(R.id.btnfpgenerate);
            btnback.setOnClickListener(new View.OnClickListener() {
                     @Override
                 public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),loginpage.class);
                    startActivity(i);
                 }
            });
        btngenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNum = mobile.getText().toString();
                if (mobileNum.isEmpty()){
                    mobile.setError("Please enter Mobile number");
                    mobile.requestFocus();
                    return;
                }else {
                    getVerificationCode();
                }
            }
        });
        btnfpsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeText = otp.getText().toString();
                if (codeText.isEmpty()){
                    otp.setError("Please enter verification Code");
                    otp.requestFocus();
                    return;
                }else {

                    Log.d("AAA","else");

                    if(awesomeValidation.validate()) {
                        codeVerification();
                        Retrofit r = RetrofitInst.getRetrofit();
                        VoterResource voterResource = r.create(VoterResource.class);
                        Call<Boolean> c = voterResource.updatevoter(username.getText().toString(), pass.getText().toString(), mobile.getText().toString());

                        c.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Boolean result = response.body();
                                if (result == true) {
                                    Toast.makeText(Forgotpassword.this, "Password Update Successfully...", Toast.LENGTH_SHORT).show();
                                    if (awesomeValidation.validate()) {
                                        Intent ii = new Intent(getApplicationContext(), loginpage.class);
                                        startActivity(ii);
                                    } else {
                                        Toast.makeText(Forgotpassword.this, "Please Enter  Details..", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Forgotpassword.this, "Incorrect Username or Mobile number  ", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(Forgotpassword.this, "Connection Error..", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }






                    /* */
                }
            }
        });

        btnfpreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setText("");
                mobile.setText("");
            }
        });
    }
    public void getVerificationCode (){
        String phoneNumber = mobile.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
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

        String code = otp.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);

    }
    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //  startActivity(new Intent(loginpage.this,loginpage .class));
                        } else
                            {
                           // Toast.makeText(Forgotpassword.this, "Login Failed", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(Forgotpassword.this, "You entered a wrong code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void validate() {

        pass=findViewById(R.id.fppass);
        cpass=findViewById(R.id.fpcpass);
        username=findViewById(R.id.fpusername);
        mobile=findViewById(R.id.fpmobileno);

        //String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*d)(?=.*[@$!%*?&])[A-Za-zd@$!%*?&]{6,10}";
        //String regexPassword = "(?=.[a-z])(?=.[A-Z])(?=.[\\d])(?=.[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{6,10}?";
        awesomeValidation.addValidation(Forgotpassword.this,R.id.fppass, regexPassword, R.string.upass);
        awesomeValidation.addValidation(Forgotpassword.this, R.id.fpcpass,R.id.uspass, R.string.ucopass);
        awesomeValidation.addValidation(Forgotpassword.this, R.id.fpmobileno, "^\\+(?:[0-9]?){6,14}[0-9]$", R.string.umobile);
        awesomeValidation.addValidation(Forgotpassword.this, R.id.fpusername, "^[vt]{2}[_]{1}[a-z,A-Z]{4,15}$", R.string.uvname);
    }
}