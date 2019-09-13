package com.qyz.malls.restaurants.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.qyz.malls.R;

public class RestaurantSearchActivity extends AppCompatActivity {

    private RestaurantSearchActivity mContext;
    private RestaurantSearchActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.out.println("sreeree get intent " + getIntent().toString()+"   "+ getIntent().getExtras().toString());
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mContext = this;
        mActivity = this;
        setContentView(R.layout.activity_restaurant_search);
    }
}
