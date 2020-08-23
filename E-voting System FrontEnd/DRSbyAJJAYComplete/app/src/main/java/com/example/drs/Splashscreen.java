package com.example.drs;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splashscreen extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        // METHOD 1

        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(2*1000);

                    // After 5 seconds redirect to another intent
                    Intent ii=new Intent(getBaseContext(),loginpage.class);
                    startActivity(ii);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
