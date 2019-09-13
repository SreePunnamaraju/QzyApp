package com.qyz.malls.restaurants.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qyz.malls.R;
import com.qyz.malls.UserDetails;
import com.qyz.malls.restaurants.adapters.CartMenuAdapter;
import com.qyz.malls.restaurants.interfaces.TotalCostListner;
import com.qyz.malls.restaurants.models.CheckoutCart;
import com.qyz.malls.restaurants.models.MenuItemModel;
import com.qyz.malls.restaurants.models.RestaurantListModel;

import java.util.ArrayList;

public class CheckOutPageActivity extends AppCompatActivity implements TotalCostListner {


    public CheckoutCart checkoutCart;
    ImageView restImage,noItemsInCart;
    TextView rating,restName,mallName,finalPrice;
    RecyclerView itemListRecyler;
    RelativeLayout coupon,itemsInCart;
    RestaurantListModel restaurantListModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page);
        noItemsInCart = findViewById(R.id.noItemsInCart);
        itemsInCart = findViewById(R.id.itemsInCart);
        restImage = findViewById(R.id.restImage);
        rating = findViewById(R.id.rating);
        restName = findViewById(R.id.rest_name);
        mallName = findViewById(R.id.mall_name);
        finalPrice = findViewById(R.id.final_price);
        itemListRecyler = findViewById(R.id.menu_cart_list);
        coupon = findViewById(R.id.coupon);
        checkoutCart = UserDetails.cart;
        System.out.println("sree user detail "+checkoutCart);
        if(checkoutCart == null || checkoutCart.getCart()==null || checkoutCart.getCart().size()==0){
            itemsInCart.setVisibility(View.GONE);
            noItemsInCart.setVisibility(View.VISIBLE);
        }else{
            restaurantListModel = checkoutCart.getRestaurantListModel();
            noItemsInCart.setVisibility(View.GONE);
            itemsInCart.setVisibility(View.VISIBLE);
            updateList();
            setRecyler();
        }
    }

    private void setRecyler() {
        ArrayList<MenuItemModel> models = new ArrayList<>();
        models.addAll(checkoutCart.getCart().keySet());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
       if(itemListRecyler.getLayoutManager() == null) {
           itemListRecyler.setLayoutManager(layoutManager);
       }
       CartMenuAdapter menuAdapter = new CartMenuAdapter(this,models);
       if(itemListRecyler.getAdapter() == null){
           itemListRecyler.setAdapter(menuAdapter);
       }else{
           itemListRecyler.getAdapter().notifyDataSetChanged();
       }

    }

    private void updateList() {
        Glide.with(this)
                .load(restaurantListModel.getImageUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(restImage);
        restName.setText(restaurantListModel.getName());
        rating.setText(restaurantListModel.getRating());
    }

    @Override
    public void updateTotalPrice(int price) {
        finalPrice.setText(getResources().getString(R.string.rs)+price);
    }

    @Override
    public void onBackPressed() {
        UserDetails.cart = checkoutCart;
        super.onBackPressed();
    }
}
