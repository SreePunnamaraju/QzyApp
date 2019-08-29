package com.qyz.malls.restaurants.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qyz.malls.HomeActivity;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.holder.RestaurantResultSecondaryHolder;
import com.qyz.malls.restaurants.models.RestaurantListModel;

import java.util.ArrayList;

public class RestaurantResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    HomeActivity homeActivity;
    ArrayList<RestaurantListModel> restList;

    public RestaurantResultAdapter(HomeActivity homeActivity, ArrayList<RestaurantListModel> restList) {
        System.out.println("sree in this rest "+ restList.size());
        this.homeActivity = homeActivity;
        this.restList = restList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("sree in this rest 1");
        View view  = LayoutInflater.from(homeActivity).inflate(R.layout.rest_list_lay,parent,false);
        return new RestaurantResultSecondaryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RestaurantResultSecondaryHolder){
            System.out.println("sree in this rest 2");
            RestaurantListModel model = restList.get(position);
            RestaurantResultSecondaryHolder secondaryHolder = (RestaurantResultSecondaryHolder) holder;
            System.out.println("sree url "+ model.getImageUrl());
            if(!homeActivity.isFinishing()) {
                Glide.with(homeActivity)
                        .load(model.getImageUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(secondaryHolder.restImage);
            }
            secondaryHolder.cusineName.setText(model.getCusine());
            secondaryHolder.price.setText(model.getPrice());
            secondaryHolder.restName.setText(model.getName());
            secondaryHolder.rating.setText(model.getRating());
            secondaryHolder.time.setText(model.getTime());
            if(model.getFav()==0){
                secondaryHolder.fav.setImageDrawable(homeActivity.getDrawable(R.drawable.ic_star));
            }
            else{
                secondaryHolder.fav.setImageDrawable(homeActivity.getDrawable(R.drawable.ic_star_red));
            }
        }

    }

    @Override
    public int getItemCount() {
        return restList.size();
    }
}
