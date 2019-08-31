package com.qyz.malls.restaurants.adapters;

import android.content.Intent;
import android.os.Handler;
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
import com.qyz.malls.restaurants.RestaurantSearchActivity;
import com.qyz.malls.restaurants.holder.BannerHolder;
import com.qyz.malls.restaurants.holder.BannerViewPagerHolder;
import com.qyz.malls.restaurants.holder.CusineFilterHolder;
import com.qyz.malls.restaurants.holder.RestaurantResultHolder;
import com.qyz.malls.restaurants.holder.SearchHolder;
import com.qyz.malls.restaurants.interfaces.FilterListener;
import com.qyz.malls.restaurants.models.CuisineFilterModel;
import com.qyz.malls.restaurants.models.RestaurantBannerModel;
import com.qyz.malls.restaurants.models.RestaurantListModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RestaurantResultPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RestaurantFilter{

    private static final int BANNER_TYPE = 0;
    private static final int SEARCHBAR_VIEW = 1;
    private static final int CUSINE_FILTER_TYPE = 2;
    private static final int RESTAURANT_RECYLER = 3;
    private HomeActivity homeActivity;
    private ArrayList<RestaurantBannerModel> bannerList;
    private ArrayList<CuisineFilterModel> cusineFilterList;
    private ArrayList<RestaurantListModel> restList;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 500;
    private final long PERIOD_MS = 3000;
    private RestaurantResultAdapter restaurantResultAdapter;
    private RestaurantResultHolder restaurantResultHolder;
    private CusineFilterHolder cusineFilterHolder;
    public String searchText = "Search";


    public RestaurantResultPageAdapter(HomeActivity homeActivity, ArrayList<RestaurantBannerModel> banner, ArrayList<CuisineFilterModel> cusinefilter, ArrayList<RestaurantListModel> restList) {
        System.out.println("sree in this");
        this.homeActivity = homeActivity;
        this.bannerList = banner;
        this.cusineFilterList= cusinefilter;
        this.restList = restList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        System.out.println("sree in this 1 1 "+viewType);
        if(viewType == BANNER_TYPE){
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.banner_view_pager,parent,false);
            BannerViewPagerHolder holder = new BannerViewPagerHolder(view);
            return holder;
        }
        else if(viewType == SEARCHBAR_VIEW){
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.searchbar,parent,false);
            SearchHolder holder = new SearchHolder(view);
            return holder;
        }
        else if(viewType == CUSINE_FILTER_TYPE){
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.cusine_recylerview,parent,false);
            CusineFilterHolder holder = new CusineFilterHolder(view);
            return holder;
        }
        else{
            System.out.println("sree in this 1");
            View view = LayoutInflater.from(homeActivity).inflate(R.layout.restaurant_result,parent,false);
            RestaurantResultHolder holder = new RestaurantResultHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        System.out.println("sree in this 1 1 1 1");
        if(holder instanceof BannerHolder){
            if(bannerList!=null) {
               BannerHolder bannerHolder = (BannerHolder) holder;
                Glide.with(homeActivity.getBaseContext())
                        .load(bannerList.get(0).getImageUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(bannerHolder.imageView);
                //todo click
            }
            //make circular
        }
        else if(holder instanceof BannerViewPagerHolder){
            final BannerViewPagerHolder viewPagerHolder = (BannerViewPagerHolder) holder;
            BannerAdapter bannerAdapter = new BannerAdapter(homeActivity,bannerList,viewPagerHolder.viewPager);
            viewPagerHolder.viewPager.setAdapter(bannerAdapter);
            final int NUM_PAGES = bannerList.size();
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES-1) {
                        currentPage = 0;
                    }
                    viewPagerHolder.viewPager.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);
        }
        else if(holder instanceof CusineFilterHolder){
            if(cusineFilterList!=null) {
                System.out.println("sree cusine");
                cusineFilterHolder = (CusineFilterHolder) holder;
                LinearLayoutManager layoutManager = new LinearLayoutManager(homeActivity, LinearLayoutManager.HORIZONTAL, false);
                cusineFilterHolder.recyclerView.setLayoutManager(layoutManager);
                CusineClickAdapter cusineClickAdapter = new CusineClickAdapter(homeActivity, cusineFilterList,cusineFilterHolder,this);
                cusineFilterHolder.recyclerView.setAdapter(cusineClickAdapter);
                cusineFilterHolder.searchbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(homeActivity, RestaurantSearchActivity.class);
                        intent.putExtra(HomeActivity.MODEL,restList);
                        homeActivity.startActivity(intent);
                    }
                });
            }
        }
        else if(holder instanceof RestaurantResultHolder){
            System.out.println("sree in this 2");
            if(restList!=null) {
                System.out.println("sree in this 3");
                restaurantResultHolder = (RestaurantResultHolder) holder;
                LinearLayoutManager layoutManager = new LinearLayoutManager(homeActivity, RecyclerView.VERTICAL, false);
                restaurantResultHolder.recyclerView.setLayoutManager(layoutManager);
                restaurantResultAdapter = new RestaurantResultAdapter(homeActivity, restList);
                restaurantResultHolder.recyclerView.setAdapter(restaurantResultAdapter);
            }
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return BANNER_TYPE;
        }
        /*else if(position == 1){
            return SEARCHBAR_VIEW;
        }*/
        else if(position == 1){
            return CUSINE_FILTER_TYPE;
        }
        else {
            return RESTAURANT_RECYLER;
        }
    }

    @Override
    public void filterResult(CuisineFilterModel cuisineFilterModel) {
        ArrayList<RestaurantListModel> filterList = new ArrayList<>();
        if(cuisineFilterModel == null){
            searchText = homeActivity.getString(R.string.search);
            cusineFilterHolder.searchbar.setText(R.string.search);
            restaurantResultAdapter = new RestaurantResultAdapter(homeActivity, restList);
            restaurantResultHolder.recyclerView.setAdapter(restaurantResultAdapter);
        }
        else {
            for (int i = 0; i < restList.size(); i++) {
                RestaurantListModel model = restList.get(i);
                if (model.getCusine().contains(cuisineFilterModel.getName())) {
                    filterList.add(model);
                }
            }
            searchText = cuisineFilterModel.getName();
            cusineFilterHolder.searchbar.setText(cuisineFilterModel.getName());
            restaurantResultAdapter = new RestaurantResultAdapter(homeActivity, filterList);
            restaurantResultHolder.recyclerView.setAdapter(restaurantResultAdapter);
        }
    }

}
