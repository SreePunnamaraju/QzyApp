package com.qyz.malls.restaurants.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.SystemClock;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.qyz.malls.UserDetails;
import com.qyz.malls.R;
import com.qyz.malls.apicall.ApiCallBackInterface;
import com.qyz.malls.apicall.ApiCallResponse;
import com.qyz.malls.apicall.ApiInstanceClass;
import com.qyz.malls.restaurants.models.CheckoutCart;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static androidx.recyclerview.widget.RecyclerView.*;

public class RestaurantHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ApiCallBackInterface {

    RecyclerView homePageMainRecyler;
    ArrayList<RestaurantListModel> restList = new ArrayList<>();
    ArrayList<RestaurantBannerModel> banner = new ArrayList<>();
    ArrayList<CuisineFilterModel> cusinefilter = new ArrayList<>();
    ImageView menuicon;
    public static final String MODEL = "MODEL";
    public static final String RESTAURANTID = "RESTAURANTID";
    RestaurantResultPageAdapter restaurantResultPageAdapter;
    public  CheckoutCart checkoutCart;
    TextView cartCount;
    RelativeLayout shoppingCart;
    long mLastClickTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        homePageMainRecyler = findViewById(R.id.homerecyler);
        menuicon = findViewById(R.id.menuicon);
        cartCount = findViewById(R.id.cart_count);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setHomePage();
        updateCart();
        menuicon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(navigationView);
            }
        });
        shoppingCart = findViewById(R.id.shopping_cart);
        shoppingCart.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                   return;
               }
               mLastClickTime = SystemClock.elapsedRealtime();
                launchCart();
           }
       });
    }

    private void launchCart() {
        Intent intent = new Intent(this,CheckOutPageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCart();
    }

    private void updateCart() {
        int count =0;
        Gson gson = new Gson();
       /*if(UserDetails.getStrPref(this,UserDetails.CART)!=null){
           String [] cartSet = UserDetails.getStrPref(this,UserDetails.CART).split(UserDetails.CART_SEPARATOR);
           System.out.println("sree cart set "+ UserDetails.getStrPref(this,UserDetails.CART).toString());
           for(String str:cartSet){
               if(str.trim().length()>0){
                   MenuItemModel itemModel = gson.fromJson(str, MenuItemModel.class);
                   checkoutCart.setMallId(itemModel.getMallid());
                   checkoutCart.setRestId(itemModel.getRestid());
                   HashMap<String,Integer> map = new HashMap<String, Integer>();
                   map.put(itemModel.getItemid(),itemModel.getCount());
                   count+=itemModel.getCount();
                   checkoutCart.setCart(map);
               }
           }
           checkoutCart.setCount(count);
       }*/
       System.out.println("sree pref cart "+ UserDetails.cart);
       if(UserDetails.cart!=null){
           checkoutCart = UserDetails.cart;
       }
       else {
           checkoutCart = new CheckoutCart();
       }
        updateCartCount();
    }
    public void updateCartCount(){
        System.out.println("sree count "+checkoutCart.getCount()+checkoutCart.getCart().size());
        if(checkoutCart!=null && checkoutCart.getCount()>0){
            cartCount.setText(checkoutCart.getCount()+"");
            cartCount.setVisibility(View.VISIBLE);
        }
        else{
            cartCount.setVisibility(View.GONE);
        }
    }

    public void setHomePage() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, VERTICAL, false);
        homePageMainRecyler.setLayoutManager(layoutManager);
        setRestaurantResult();
        setBanner();
        setCuisine();
    }

    public void setRestaurantResult() {
      //   populateList("dummy_rest_list", "rest");
        LinkedHashMap<String, String> apiCallMap = new LinkedHashMap<>();
        apiCallMap.put("mall","SCCP_HYD");
        ApiInstanceClass.getInstance().submitGetRequest(ApiInstanceClass.getBaseInterface(),apiCallMap,this,"restaurant_list");
    }

    public void setCuisine() {
        populateList("Cusine_payload", "cus");
        System.out.println("sree cusine data "+cusinefilter.size());
        if(homePageMainRecyler.getAdapter() == null){
            restaurantResultPageAdapter = new RestaurantResultPageAdapter(this, banner, cusinefilter, restList);
            homePageMainRecyler.setAdapter(restaurantResultPageAdapter);
        }else{
            ((RestaurantResultPageAdapter)homePageMainRecyler.getAdapter()).setCusineData(cusinefilter); ;
            homePageMainRecyler.getAdapter().notifyDataSetChanged();
        }
    }

    public void setBanner() {
         populateList("Banner_payload", "banner");
         System.out.println("sree banner data "+banner.size());
        if(homePageMainRecyler.getAdapter() == null){
            restaurantResultPageAdapter = new RestaurantResultPageAdapter(this, banner, cusinefilter, restList);
            homePageMainRecyler.setAdapter(restaurantResultPageAdapter);
        }else{
            ((RestaurantResultPageAdapter)homePageMainRecyler.getAdapter()).setBannerData(banner) ;
            homePageMainRecyler.getAdapter().notifyDataSetChanged();
        }

    }

   private void populateList(String filename, String key) {
      try
      {
         BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)));
         String line = reader.readLine();
         StringBuilder sb = new StringBuilder();
         while (line != null)
         {
            sb.append(line);
            line = reader.readLine();
         }

         String fileAsString = sb.toString();
         JSONObject jsonObject = new JSONObject(fileAsString);
         Gson gson = new Gson();
         JSONArray jsonArray;

         if (key.equals("rest"))
         {
             jsonArray = jsonObject.getJSONArray("restaurants");
            for (int i = 0; i < jsonArray.length(); i++)
            {
               JSONObject object = jsonArray.getJSONObject(i);
               RestaurantListModel model = gson.fromJson(object.toString(), RestaurantListModel.class);
               restList.add(model);
            }
         }
         else if (key.equals("banner"))
         {
             System.out.println("sree in cus banner");
             jsonArray = jsonObject.getJSONArray("banners");
            for (int i = 0; i < jsonArray.length(); i++)
            {
               JSONObject object = jsonArray.getJSONObject(i);
               RestaurantBannerModel model = gson.fromJson(object.toString(), RestaurantBannerModel.class);
               banner.add(model);
            }
         }
         else if (key.equals("cus"))
         {
             jsonArray = jsonObject.getJSONArray("cuisines");
             for (int i = 0; i < jsonArray.length(); i++)
             {
                 JSONObject object = jsonArray.getJSONObject(i);
                 CuisineFilterModel model = gson.fromJson(object.toString(), CuisineFilterModel.class);
                 cusinefilter.add(model);
             }
         }
      }
      catch (IOException | JSONException e )
      {
          System.out.println("sree in this error "+ e.getMessage() + e.toString());
         e.printStackTrace();
      }
   }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            if(restaurantResultPageAdapter !=null && !restaurantResultPageAdapter.searchText.equalsIgnoreCase(getString(R.string.search))){
                restaurantResultPageAdapter.filterResult(null);
            }
            else {
                super.onBackPressed();
            }
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

        if (id == R.id.nav_orders) {
            // Handle the camera action
        } else if (id == R.id.edit_profile) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_faq) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResponseSuccess(JSONObject json, String requestTag) {

        System.out.println("sree json " + json.toString() + " tag " + requestTag);
        Gson gson = new Gson();
        JSONArray jsonArray;
        if (requestTag.equals("restaurant_list")) {
            try {
                jsonArray = json.getJSONArray("restaurants");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    RestaurantListModel model = gson.fromJson(object.toString(), RestaurantListModel.class);
                    model.setCusines(object.getJSONArray("cusines"));
                    restList.add(model);
                    System.out.println("sree rest " + model.toString());
                }
                if(homePageMainRecyler.getAdapter() == null){
                    restaurantResultPageAdapter = new RestaurantResultPageAdapter(this, banner, cusinefilter, restList);
                    homePageMainRecyler.setAdapter(restaurantResultPageAdapter);
                }else{
                    ((RestaurantResultPageAdapter)homePageMainRecyler.getAdapter()).setRestaurantData(restList); ;
                  homePageMainRecyler.getAdapter().notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponseFailure(String requestTag) {

    }
}
