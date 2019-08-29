package com.qyz.malls;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.qyz.malls.restaurants.adapters.RestaurantResultPageAdapter;
import com.qyz.malls.restaurants.models.CuisineFilterModel;
import com.qyz.malls.restaurants.models.RestaurantBannerModel;
import com.qyz.malls.restaurants.models.RestaurantListModel;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.*;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView homePageMainRecyler;
    ArrayList<RestaurantListModel> restList;
    ArrayList<RestaurantBannerModel> banner;
    ArrayList<CuisineFilterModel> cusinefilter;

    /*

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        homePageMainRecyler = findViewById(R.id.homerecyler);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setHomePage();
    }

    public void setHomePage() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, VERTICAL, false);
        homePageMainRecyler.setLayoutManager(layoutManager);
        RestaurantResultPageAdapter restaurantResultPageAdapter = new RestaurantResultPageAdapter(this, banner, cusinefilter, restList);
        homePageMainRecyler.setAdapter(restaurantResultPageAdapter);
        setBanner();
        setCuisine();
        setRestaurantResult();
    }

    public void setRestaurantResult() {
        try {
            restList = new ArrayList<RestaurantListModel>();
            String filename= "dummy_rest_list";
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(filename)));
            String line = reader.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
            String fileAsString = sb.toString();
            JSONObject restraunt = new JSONObject(fileAsString);
            Gson gson = new Gson();
            JSONArray list = restraunt.getJSONArray("restaurants");
            for(int i=0;i<list.length();i++){
                JSONObject object = list.getJSONObject(i);
                RestaurantListModel model = gson.fromJson(object.toString(),RestaurantListModel.class);
                restList.add(model);
            }
            RestaurantResultPageAdapter restaurantResultPageAdapter = new RestaurantResultPageAdapter(this, banner, cusinefilter, restList);
            homePageMainRecyler.setAdapter(restaurantResultPageAdapter);
        } catch (IOException e) {
            System.out.println("sree exp "+e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("sree exp "+e.getMessage());
            e.printStackTrace();
        }
    }

    public void setCuisine() {
    }

    public void setBanner() {
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
