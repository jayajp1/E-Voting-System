package com.example.drs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.drs.Model.Voter;
import com.example.drs.resources.UserResource;
import com.example.drs.resources.VoterResource;
import com.example.drs.utils.RetrofitInst;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScanningActivity extends AppCompatActivity {

    private ZXingScannerView scannerView;
    Button btnscan;
    AwesomeValidation awesomeValidation;
    private int CAMREA_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        if(permissionAlreadyGranted()) {
            setContentView(R.layout.activity_scanning);
            scannerView = new ZXingScannerView(getApplicationContext());
            scannerView.setResultHandler(new ZxingScannerResultHandler());
            setContentView(scannerView);
            scannerView.startCamera();
        }
        else
        {
            requestPermission();
        }
    }

    private boolean permissionAlreadyGranted() {

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        Log.d("Resukt", "permissionAlreadyGranted: " + result);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMREA_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Permission granted successfully", Toast.LENGTH_SHORT).show();
            scannerView = new ZXingScannerView(getApplicationContext());
            scannerView.setResultHandler(new ZxingScannerResultHandler());
            setContentView(scannerView);
            scannerView.startCamera();
        } else {
            Toast.makeText(getApplicationContext().getApplicationContext(), "Permission is denied!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("FromScanning","true");
            startActivity(intent);
        }
    }

    @Override
    public  void onPause()
    {
        super.onPause();
        if(scannerView != null) {
            scannerView.stopCamera();
        }
    }
    class ZxingScannerResultHandler implements  ZXingScannerView.ResultHandler
    {
        @Override
        public void handleResult(Result result)
        {
            String resultcode=result.getText();
            final Integer adhar=new Integer(resultcode);


            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ScanningActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
            final EditText mUsername = (EditText) mView.findViewById(R.id.etUsername);
            final EditText mPassword = (EditText) mView.findViewById(R.id.etPasswordd);
            Button Login = (Button) mView.findViewById(R.id.qrbtnLogin);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!mUsername.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){
                        validate();
                        if (awesomeValidation.validate()){
                            Retrofit r=RetrofitInst.getRetrofit();
                            VoterResource voterResource = r.create(VoterResource.class);
                            Call<Voter> c=voterResource.AuthenticateVoterQR(mUsername.getText().toString().trim(),mPassword.getText().toString().trim(),adhar);
                            c.enqueue(new Callback<Voter>() {
                                @Override
                                public void onResponse(Call<Voter> call, Response<Voter> response) {
                                    Voter afterCall=response.body();
                                    if(response.body()!=null)
                                    {
                                        GlobalUser globalUser = (GlobalUser) getApplicationContext();
                                        globalUser.setUsername(mUsername.getText().toString().trim());
                                        if(mUsername.getText().toString().contains("vt_")) {
                                            globalUser.setCity(afterCall.getCity());
                                            globalUser.setMobileNo(afterCall.getMobileNo());
                                            globalUser.setVoteDone(afterCall.getVdone());
                                        }
                                        globalUser.setAdharcard(afterCall.getAdharcard());
                                        globalUser.setFirstname(afterCall.getFirstName());

                                        Intent ii=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(ii);
                                        dialog.dismiss();
                                    }}

                                @Override
                                public void onFailure(Call<Voter> call, Throwable t) {
                                    Toast.makeText(ScanningActivity.this,
                                            "Connection Error.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(ScanningActivity.this,
                                    "Please Enter details",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ScanningActivity.this,
                               "Please Enter details",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //Retrofit r = RetrofitInst.getRetrofit();
           /* Toast.makeText(getApplicationContext(),"Vote Done....",Toast.LENGTH_LONG).show();
            Intent ii=new Intent(getApplicationContext(),MainActivity.class);
            ii.putExtra("",ScannerFragment.class);
            startActivity(ii);
            setContentView(R.layout.fragment_scanner);*/
          //  scannerView.stopCamera();
        }
    }
    private void validate() {
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{6,10}$";
        //String regexPassword = "(?=.[a-z])(?=.[A-Z])(?=.[\\d])(?=.[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(ScanningActivity.this, R.id.etUsername, "^[vt]{2}[_]{1}[a-z,A-Z]{4,15}$", R.string.uvname);
        awesomeValidation.addValidation(ScanningActivity.this, R.id.etPasswordd, regexPassword, R.string.upass);
    }
}
