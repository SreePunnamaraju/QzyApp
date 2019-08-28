package com.qyz.malls.restaurants.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qyz.malls.HomeActivity;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.holder.BannerHolder;
import com.qyz.malls.restaurants.holder.CusineFilterHolder;
import com.qyz.malls.restaurants.holder.RestaurantResultHolder;
import com.qyz.malls.restaurants.models.CuisineFilterModel;
import com.qyz.malls.restaurants.models.RestaurantBannerModel;
import com.qyz.malls.restaurants.models.RestaurantListModel;

import java.util.ArrayList;

public class RestaurantResultPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int BANNER_TYPE = 0;
    private static final int SEARCHBAR_VIEW = 1;
    private static final int CUSINE_FILTER_TYPE = 2;
    private static final int RESTAURANT_RECYLER = 3;
    private HomeActivity homeActivity;
    private ArrayList<RestaurantBannerModel> bannerList;
    private ArrayList<CuisineFilterModel> cusineFilterList;
    private ArrayList<RestaurantListModel> restList;

    public RestaurantResultPageAdapter(HomeActivity homeActivity, ArrayList<RestaurantBannerModel> banner, ArrayList<CuisineFilterModel> cusinefilter, ArrayList<RestaurantListModel> restList) {
        this.homeActivity = homeActivity;
        this.bannerList = banner;
        this.cusineFilterList= cusinefilter;
        this.restList = restList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == BANNER_TYPE){
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.banner,parent,false);
            BannerHolder holder = new BannerHolder(view);
            return holder;
        }
        else if(viewType == SEARCHBAR_VIEW){

        }
        else if(viewType == CUSINE_FILTER_TYPE){
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.cusine_recylerview,parent,false);
            CusineFilterHolder holder = new CusineFilterHolder(view);
            return holder;
        }
        else{
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.restaurant_result,parent,false);
            RestaurantResultHolder holder = new RestaurantResultHolder(view);
            return holder;
        }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BannerHolder){
            BannerHolder bannerHolder = (BannerHolder) holder;
            Glide.with(homeActivity)
                    .load(bannerList.get(0).getImageUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bannerHolder.imageView);
            //todo click
            //make circular
        }
        if(holder instanceof CusineFilterHolder){
            CusineFilterHolder cusineFilterHolder = (CusineFilterHolder) holder;
            LinearLayoutManager layoutManager = new LinearLayoutManager(homeActivity,LinearLayoutManager.HORIZONTAL,false);
            cusineFilterHolder.recyclerView.setLayoutManager(layoutManager);
            CusineClickAdapter cusineClickAdapter = new CusineClickAdapter(homeActivity,cusineFilterList);
            cusineFilterHolder.recyclerView.setAdapter(cusineClickAdapter);
        }
        if(holder instanceof RestaurantResultHolder){
            RestaurantResultHolder restaurantResultHolder = (RestaurantResultHolder) holder;
            LinearLayoutManager layoutManager = new LinearLayoutManager(homeActivity,RecyclerView.VERTICAL,false);
            restaurantResultHolder.recyclerView.setLayoutManager(layoutManager);
            RestaurantResultAdapter restaurantResultAdapter = new RestaurantResultAdapter(homeActivity,restList);
            restaurantResultHolder.recyclerView.setAdapter(restaurantResultAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return BANNER_TYPE;
        }
        else if(position == 1){
            return SEARCHBAR_VIEW;
        }
        else if(position == 2){
            return CUSINE_FILTER_TYPE;
        }
        else {
            return RESTAURANT_RECYLER;
        }
    }
}
