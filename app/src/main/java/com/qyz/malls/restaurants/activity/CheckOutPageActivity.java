package com.qyz.malls.restaurants.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.qyz.malls.AppExecutors;
import com.qyz.malls.R;
import com.qyz.malls.UserDetails;
import com.qyz.malls.apicall.ApiCallBackInterface;
import com.qyz.malls.apicall.ApiInstanceClass;
import com.qyz.malls.db.LocalDatabase;
import com.qyz.malls.restaurants.adapters.CartMenuAdapter;
import com.qyz.malls.restaurants.interfaces.TotalCostListner;
import com.qyz.malls.restaurants.models.CheckoutCart;
import com.qyz.malls.restaurants.models.ItemLinesModel;
import com.qyz.malls.restaurants.models.MenuItemModel;
import com.qyz.malls.restaurants.models.OrderModel;
import com.qyz.malls.restaurants.models.RestaurantListModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.Executor;

public class CheckOutPageActivity extends AppCompatActivity implements TotalCostListner, ApiCallBackInterface {

    private static final String TAG = "Qzy/CheckOutPagActivity";

    public CheckoutCart checkoutCart;
    ImageView restImage,noItemsInCart,backButton;
    TextView rating,restName,mallName,finalPrice,priceToPay,taxPrice;
    RecyclerView itemListRecycler;
    RelativeLayout coupon,itemsInCart,proceedPayment;
    RestaurantListModel restaurantListModel;
    CheckOutPageActivity mActivity;
    int tax  =20;
    int dist =50;
    public final static String HASHNULL = "#null";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: instance created");
        setContentView(R.layout.checkout_page);
        mActivity = this;
        noItemsInCart = findViewById(R.id.noItemsInCart);
        itemsInCart = findViewById(R.id.itemsInCart);
        restImage = findViewById(R.id.restImage);
        rating = findViewById(R.id.rating);
        restName = findViewById(R.id.rest_name);
        mallName = findViewById(R.id.mall_name);
        finalPrice = findViewById(R.id.final_price);
        priceToPay = findViewById(R.id.final_price_total);
        taxPrice = findViewById(R.id.final_price_charges);
        itemListRecycler = findViewById(R.id.menu_cart_list);
        coupon = findViewById(R.id.coupon);
        checkoutCart = UserDetails.cart;
        proceedPayment=findViewById(R.id.proceedToPay);
        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        System.out.println("sree user detail "+checkoutCart);
        if(checkoutCart == null || checkoutCart.getCart()==null || checkoutCart.getCart().size()==0){
            itemsInCart.setVisibility(View.GONE);
            proceedPayment.setVisibility(View.GONE);
            noItemsInCart.setVisibility(View.VISIBLE);
        }else{
            restaurantListModel = checkoutCart.getRestaurantListModel();
            noItemsInCart.setVisibility(View.GONE);
            itemsInCart.setVisibility(View.VISIBLE);
            updateList();
            setRecyler();
            proceedPayment.setVisibility(View.VISIBLE);
            proceedPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateOrderId();
                    postOrder();
                }
            });
        }

    }

    private void generateOrderId() {
        if(checkoutCart.getOrderId() == null){
            LinkedHashMap<String, String> queryMap = new LinkedHashMap<>();
            queryMap.put("sequenceType", "order");
            ApiInstanceClass.getInstance().submitPostRequest(ApiInstanceClass.getBaseInterface(), null, this, "get_sequence_number", queryMap);
        }
    }
    private void postOrder() {
        final OrderModel order = new OrderModel();
        Executor diskIO = new AppExecutors().getDiskIO();
        diskIO.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String phoneNo=LocalDatabase.getInstance(mActivity).userDao().getPhoneNumber();
                    System.out.println("running in userDbDetails:"+phoneNo);
                    order.setPartition(phoneNo);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mActivity,R.string.no_ph_no,Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        order.setSort(checkoutCart.getOrderId());
        order.setDate_time(GregorianCalendar.getInstance().getTime().toString());
        order.setGsi_partition(checkoutCart.getRestaurantListModel().getRestid());
        order.setRating(HASHNULL);
        order.setReview(HASHNULL);
        order.setRestaurant_name(checkoutCart.getRestaurantListModel().getName());
        order.setPayment_id(HASHNULL);
        order.setPayment_method(HASHNULL);
        order.setStatus("Confirmed");

        Set<MenuItemModel> items = checkoutCart.getCart().keySet();
        ArrayList<ItemLinesModel> itemLines = new ArrayList<>();
        int totalPrice = 0;
        for(MenuItemModel item:items){
            int quantity = checkoutCart.getCart().get(item);
            System.out.println("item name and quantity:"+item.getPrice()+" "+quantity);
            int itemTotal = (quantity)*(Integer.parseInt(item.getPrice()));
            totalPrice += itemTotal;
            System.out.println(itemTotal);
            ItemLinesModel itemLine = new ItemLinesModel();
            itemLine.setItem_id(item.getItemid());
            itemLine.setPrice(item.getPrice());
            itemLine.setItem_name(item.getName());
            itemLine.setQuantity(checkoutCart.getCart().get(item).toString());
            itemLine.setLine_total(Integer.toString(itemTotal));
            itemLines.add(itemLine);
        }
        order.setItem_lines(itemLines);
        order.setTransaction_total(Integer.toString(totalPrice));

        order.setExtra_charges(null);
        Gson gson = new Gson();
        System.out.println(gson.toJson(order));


    }

    private void setRecyler() {
        ArrayList<MenuItemModel> models = new ArrayList<>();
        models.addAll(checkoutCart.getCart().keySet());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
       if(itemListRecycler.getLayoutManager() == null) {
           itemListRecycler.setLayoutManager(layoutManager);
       }
       CartMenuAdapter menuAdapter = new CartMenuAdapter(this,models);
       if(itemListRecycler.getAdapter() == null){
           itemListRecycler.setAdapter(menuAdapter);
       }else{
           itemListRecycler.getAdapter().notifyDataSetChanged();
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
        if(price == 0){
            itemsInCart.setVisibility(View.GONE);
            noItemsInCart.setVisibility(View.VISIBLE);
            proceedPayment.setVisibility(View.GONE);
        }else {
            finalPrice.setText(getResources().getString(R.string.rs) + price);
            taxPrice.setText(getResources().getString(R.string.rs) + 20);
            int tot = price + 20;
            priceToPay.setText(getResources().getString(R.string.rs) + tot);
        }
    }

    @Override
    public void onBackPressed() {
        UserDetails.cart = checkoutCart;
        super.onBackPressed();
    }

    @Override
    public void onResponseSuccess(JSONObject json, String requestTag) throws JSONException {
        if(requestTag == "get_sequence_number"){
            Log.d(TAG, "onResponseSuccess: setting sequence number:"+json.getString("sequence_number"));
            checkoutCart.setOrderId(json.getString("sequence_number"));
        }
    }

    @Override
    public void onResponseFailure(String requestTag) {

    }
}
