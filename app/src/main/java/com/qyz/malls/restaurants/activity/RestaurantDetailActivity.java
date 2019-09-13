package com.qyz.malls.restaurants.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.qyz.malls.UserDetails;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.adapters.MenuPrimaryAdapter;
import com.qyz.malls.restaurants.interfaces.CartListener;
import com.qyz.malls.restaurants.models.CheckoutCart;
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
    public CheckoutCart cart;
    TextView  cartCount;
    TextView textCart;
    String[] shoppingCart;
    RelativeLayout footerCart;
    int pos;
    MenuPrimaryAdapter menuPrimaryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
       setContentView(R.layout.activity_restaurant_detail);
        //if(getIntent()!=null && getIntent().hasExtra(RestaurantHomeActivity.MODEL))
       restaurantListModel =(RestaurantListModel) getIntent().getSerializableExtra(RestaurantHomeActivity.MODEL);
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
        textCart = findViewById(R.id.textCart);
        footerCart = findViewById(R.id.footer_cart);
        pos = getIntent().getIntExtra("pos",0);
        System.out.println("sree in this "+restaurantListModel.getName());
        setDetailPage();
        setMenu();
        if(UserDetails.cart == null) {
            cart = new CheckoutCart();
        }
        else{
            cart = UserDetails.cart;
        }
        System.out.println("sree rest id 1234 "+ cart.getRestId()+" "+restaurantListModel.getRestid());
        updateMainCart(cart.getCount());
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
        MenuPrimaryAdapter menuPrimaryAdapter = new MenuPrimaryAdapter(this,menuModels,this,pos);
        restDetailRecyler.setLayoutManager(linearLayoutManager);
        restDetailRecyler.setAdapter(menuPrimaryAdapter);
    }


    public void addItemToCart(final MenuItemModel model) {
        System.out.println("sree bool "+ cart.getMallId().equals("-1"));
        if(cart.getMallId().equals("-1") ){
            System.out.println("sree id in this "+cart.getMallId());
            cart.setMallId(model.getMallid());
            System.out.println("sree id in this 1"+cart.getMallId()+" "+cart.getRestId());
            cart.setRestId(model.getRestid());
            System.out.println("sree id in this 2"+cart.getRestId());
        }
        System.out.println("sree id "+cart.getRestId()+" "+model.getRestid() );
        if(cart.getMallId().equals(model.getMallid()) && cart.getRestId().equals(model.getRestid())){
            System.out.println("sree id in this if");
            addItem(model);
        }
        else{
            System.out.println("sree id in this else");
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("You already have items in your cart.Do you want to discard them?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            cart= new CheckoutCart();
                            System.out.println("sree cart "+cart.getRestId()+" "+cart.getMallId()+" "+cart.getCount());
                            addItem(model);
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }


    public void addItem(MenuItemModel model){
        if(cart.getMallId().equals("-1") ){
            cart.setMallId(model.getMallid());
            cart.setRestId(model.getRestid());
        }
        final HashMap<String,Integer> shopCart = cart.getCart();
        if(shopCart.containsKey(model.getItemid())){
            int c = shopCart.get(model.getItemid())+1;
            shopCart.put(model.getItemid(),c);
        }
        else{
            shopCart.put(model.getItemid(),1);
        }
        cart.setCount(cart.getCount()+1);
        cart.setCart(shopCart);
        updateMainCart(cart.getCount());
    }

    @Override
    public void updateMainCart(int count){
        if(cart.getRestId().equals(restaurantListModel.getRestid())) {
            if (count == 0) {
                cartCount.setVisibility(View.GONE);
                footerCart.setVisibility(View.GONE);
            } else {
                if (count == 1) {
                    textCart.setText(cart.getCount() + " Item Added to cart");
                } else {
                    textCart.setText(cart.getCount() + " Items Added to cart");
                }
                cartCount.setText(count + "");
                cartCount.setVisibility(View.VISIBLE);
                footerCart.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        UserDetails.cart = cart;
      //  setCart();
        super.onBackPressed();
    }
    private void setCart() {
        StringBuilder set = new StringBuilder();
        if (cart != null && cart.getCart()!=null) {
            for (String s : cart.getCart().keySet()) {
                    set.append(s).append(UserDetails.CART_SEPARATOR);
            }
        }
        UserDetails.setStrPref(this, UserDetails.CART, set.toString());
        System.out.println("sree cart set x"+ UserDetails.getStrPref(this, UserDetails.CART).toString());

    }
}
