package com.qyz.malls.restaurants.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;
import com.qyz.malls.UserDetails;
import com.qyz.malls.restaurants.models.CheckoutCart;

public class CheckOutPageActivity extends AppCompatActivity {


    CheckoutCart checkoutCart;
    ImageView imageView,noItemsInCart;
    TextView rating,restName,mallName,finalPrice;
    RecyclerView itemList;
    RelativeLayout coupon,itemsInCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page);
        noItemsInCart = findViewById(R.id.noItemsInCart);
        itemsInCart = findViewById(R.id.itemsInCart);
        imageView = findViewById(R.id.restImage);
        rating = findViewById(R.id.rating);
        restName = findViewById(R.id.rest_name);
        mallName = findViewById(R.id.mall_name);
        finalPrice = findViewById(R.id.final_price);
        itemList = findViewById(R.id.menu_cart_list);
        coupon = findViewById(R.id.coupon);
        checkoutCart = UserDetails.cart;
        System.out.println("sree user detail "+checkoutCart);
        if(checkoutCart == null || checkoutCart.getCart()==null || checkoutCart.getCart().size()==0){
            itemsInCart.setVisibility(View.GONE);
            noItemsInCart.setVisibility(View.VISIBLE);
        }else{
            noItemsInCart.setVisibility(View.GONE);
            itemsInCart.setVisibility(View.VISIBLE);
            updateList();
        }
    }

    private void updateList() {

    }
}
