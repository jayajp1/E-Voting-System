package com.example.drs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class carditems extends AppCompatActivity {

    RecyclerView newslist;
    Button btvote;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carditems);

        final Executor executor= Executors.newSingleThreadExecutor();

        final BiometricPrompt biometricPrompt= new BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Authentication")
                .setDescription("This is require for Security..")
                .setNegativeButton("Cancel",executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).build();

        final carditems carditemss=this;
            btvote=findViewById(R.id.btnvotte);
            btvote.setOnClickListener(new View.OnClickListener() {
                 @Override
                   public void onClick(View v) {
                          biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                                // super.onAuthenticationSucceeded(result);

                carditemss.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(carditems.this,"Authentication",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
});

    }
}
