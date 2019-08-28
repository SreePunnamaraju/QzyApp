package com.qyz.malls.restaurants;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.HomeActivity;
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

        }
        else if(viewType == SEARCHBAR_VIEW){

        }
        else if(viewType == CUSINE_FILTER_TYPE){

        }
        else{

        }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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
        else if(position ==2){
            return CUSINE_FILTER_TYPE;
        }
        else {
            return RESTAURANT_RECYLER;
        }
    }
}
