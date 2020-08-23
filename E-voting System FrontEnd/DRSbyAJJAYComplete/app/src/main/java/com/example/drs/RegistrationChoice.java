package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class RegistrationChoice extends Activity {


    Button bradmin,brvoter;
    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration_choice);

        bradmin=findViewById(R.id.bradmin);
        brvoter=findViewById(R.id.brvoter);


brvoter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent ii=new Intent(getApplicationContext(),Signup1.class);
        startActivity(ii);
    }
});


bradmin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent ii=new Intent(getApplicationContext(),Signup2.class);
        startActivity(ii);
    }
});
    }
}
