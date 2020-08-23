package com.example.drs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class NotificationActivity extends Activity {

    private final String CHANNEL_ID="jay.aj.drs.DISPLAY_NOTIFICATION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar calendar=Calendar.getInstance();
              //  calendar.add(Calendar.SECOND,10);
                calendar.set(Calendar.HOUR_OF_DAY,12);
               calendar.set(Calendar.MINUTE,23);
              calendar.set(Calendar.SECOND,00);
                Intent intent=new Intent(this,AlarmReceiver.class);
                PendingIntent padingintent=PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(alarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),padingintent);




    }
}
