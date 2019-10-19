package com.qyz.malls;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qyz.malls.restaurants.activity.RestaurantHomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        mAuth= FirebaseAuth.getInstance();
        final Handler handler = new Handler();
        checkIfLoggedIn();
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
