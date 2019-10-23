package com.qyz.malls;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qyz.malls.restaurants.activity.RestaurantHomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    public static final String CHANNEL_ID = "my_channel_01";
    public static final String CHANNEL_NAME = "Simplified Coding Notification";
    public static final String CHANNEL_DESCRIPTION = "www.simplifiedcoding.net";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        mAuth= FirebaseAuth.getInstance();
        final Handler handler = new Handler();
        checkIfLoggedIn();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel();
            mChannel.setDescription(CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        /*
         * Displaying a notification locally
         */
        MyNotificationManager.getInstance(this).displayNotification("Greetings", "Hello how are you?");
//        startService(new Intent(this,NotificationService.class));
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("sree in this listner xxy");
            this.startForegroundService(new Intent(this, NotificationService.class));
        } else {
            System.out.println("sree in this listner xxy12");
            this.startService(new Intent(this, NotificationService.class));

        }*/
       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIfLoggedIn();
            }
        }, 2000);*/
    }

    private void checkIfLoggedIn() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            LoginActivity.sendPostCred(mAuth,this);
            Intent intent = new Intent(this, RestaurantHomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
