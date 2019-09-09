package com.qyz.malls.restaurants;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.qyz.malls.HomeActivity;
import com.qyz.malls.Preference;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.adapters.MenuPrimaryAdapter;
import com.qyz.malls.restaurants.models.MenuItemModel;
import com.qyz.malls.restaurants.models.MenuModel;
import com.qyz.malls.restaurants.models.RestaurantListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantDetailActivity extends AppCompatActivity implements CartListener {

    ArrayList<MenuModel> menuModels = new ArrayList<>();
    RestaurantListModel restaurantListModel;
    RecyclerView restDetailRecyler;
    ImageView backIcon,restImage,favIcon;
    TextView rating,restName,cusineName,time,price;
    RelativeLayout fav;
    CheckoutCart cart;
    TextView  cartCount;
    String[] shoppingCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
       setContentView(R.layout.activity_restaurant_detail);
        //if(getIntent()!=null && getIntent().hasExtra(HomeActivity.MODEL))
       restaurantListModel =(RestaurantListModel) getIntent().getSerializableExtra(HomeActivity.MODEL);
        restDetailRecyler = findViewById(R.id.restDetailRecyler);
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        restImage = findViewById(R.id.restImage);
        fav = findViewById(R.id.fav);
        rating = findViewById(R.id.rating);
        restName = findViewById(R.id.restName);
        cusineName = findViewById(R.id.cusineName);
        time = findViewById(R.id.time);
        price = findViewById(R.id.price);
        favIcon =findViewById(R.id.favIcon);
        cartCount = findViewById(R.id.cart_count);
       System.out.println("sree in this "+restaurantListModel.getName());
        setDetailPage();
        setMenu();
        if(Preference.cart == null) {
            cart = new CheckoutCart();
        }
        else{
            cart =Preference.cart;
        }
    }
    public void setDetailPage() {
        Glide.with(this)
                .load(restaurantListModel.getImageUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(restImage);
        cusineName.setText(restaurantListModel.getCusine());
        price.setText(restaurantListModel.getPrice());
        restName.setText(restaurantListModel.getName());
        rating.setText(restaurantListModel.getRating());
        time.setText(restaurantListModel.getTime());
        if(restaurantListModel.getFav()==0){
            favIcon.setImageDrawable(getDrawable(R.drawable.ic_heart_grey));
        }
        else{
            favIcon.setImageDrawable(getDrawable(R.drawable.ic_heart_red));
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(restaurantListModel.getFav()==0){
                    favIcon.setImageDrawable(getDrawable(R.drawable.ic_heart_red));
                    restaurantListModel.setFav(1);
                }
                else{
                    favIcon.setImageDrawable(getDrawable(R.drawable.ic_heart_grey));
                    restaurantListModel.setFav(0);
                }
            }
        });

    }

    public void setMenu() {
        getMenuData();
        setMenuData();
    }
    private void getMenuData() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("dummy_items_list")));
        String line = reader.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        String fileAsString = sb.toString();
        Gson gson = new Gson();
        JSONArray jsonArray;
        JSONObject jsonObject = new JSONObject(fileAsString);
        jsonArray= jsonObject.getJSONArray("menu");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject menuJson = jsonArray.getJSONObject(i);
            MenuModel menuModel = new MenuModel();
            ArrayList<MenuItemModel> menuItemModels = new ArrayList<>();
            menuModel.setName(menuJson.getString("name"));
            JSONArray items = menuJson.getJSONArray("items");
            for(int j=0;j<items.length();j++){
                MenuItemModel model = gson.fromJson(items.getJSONObject(j).toString(), MenuItemModel.class);
                menuItemModels.add(model);
            }
            menuModel.setMenuItemList(menuItemModels);
            menuModels.add(menuModel);
        }

        } catch (IOException | JSONException e) {
            System.out.println("sree detail exception "+e.getMessage());
            e.printStackTrace();
        }
    }

    private void setMenuData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        MenuPrimaryAdapter menuPrimaryAdapter = new MenuPrimaryAdapter(this,menuModels,this);
        restDetailRecyler.setLayoutManager(linearLayoutManager);
        restDetailRecyler.setAdapter(menuPrimaryAdapter);
    }

    @Override
    public void removeItemFromCart(MenuItemModel model) {
        if(cart.mallId.equals("-1") ){
            cart.mallId = model.getMallid();
            cart.restId = model.getRestid();
        }
        HashMap<String,Integer> shopCart = cart.getCart();
        if(cart.mallId.equals(model.getMallid())&& cart.restId.equals(model.getRestid())){
           int count = shopCart.get(model.getItemid())-1;
           if(count == 0){
               shopCart.remove(model.getItemid());
           }
           else{
               shopCart.put(model.getItemid(),count);
           }
        }
        cart.setCount(cart.getCount()-1);
        cart.setCart(shopCart);
        updateMainCart(cart.getCount());
    }

    @Override
    public void addItemToCart(MenuItemModel model) {
        if(cart.getMallId().equals("-1") ){
            cart.setMallId(model.getMallid());
            cart.setMallId(model.getRestid());
        }
        HashMap<String,Integer> shopCart = cart.getCart();
        System.out.println("sree id "+cart.getRestId()+" "+model.getRestid() );
        if(cart.getMallId().equals(model.getMallid()) && cart.getRestId().equals(model.getRestid())){
            if(shopCart.containsKey(model.getItemid())){
                int c = shopCart.get(model.getItemid())+1;
                shopCart.put(model.getItemid(),c);
            }
            else{
                shopCart.put(model.getItemid(),1);
            }
        }
        cart.setCount(cart.getCount()+1);
        cart.setCart(shopCart);
        updateMainCart(cart.getCount());
    }

    public void updateMainCart(int count){
        if(count == 0){
            cartCount.setVisibility(View.GONE);
        } else{
            cartCount.setText(count+"");
            cartCount.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        Preference.cart = cart;
      //  setCart();
        super.onBackPressed();
    }
    private void setCart() {
        StringBuilder set = new StringBuilder();
        if (cart != null && cart.getCart()!=null) {
            for (String s : cart.getCart().keySet()) {
                    set.append(s).append(Preference.CART_SEPARATOR);
            }
        }
        Preference.setStrPref(this, Preference.CART, set.toString());
        System.out.println("sree cart set x"+ Preference.getStrPref(this,Preference.CART).toString());

    }
}
