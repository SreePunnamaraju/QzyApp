package com.qyz.malls.restaurants.adapters;

import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qyz.malls.restaurants.activity.RestaurantHomeActivity;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.activity.RestaurantDetailActivity;
import com.qyz.malls.restaurants.holder.RestaurantResultSecondaryHolder;
import com.qyz.malls.restaurants.models.RestaurantListModel;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RestaurantHomeActivity restaurantHomeActivity;
    ArrayList<RestaurantListModel> restList;
    long mLastClickTime=0;

    public RestaurantResultAdapter(RestaurantHomeActivity restaurantHomeActivity, ArrayList<RestaurantListModel> restList) {
        System.out.println("sree in this rest "+ restList.size());
        this.restaurantHomeActivity = restaurantHomeActivity;
        this.restList = restList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == -1) {
            System.out.println("sree in this rest 1");
            View view = LayoutInflater.from(restaurantHomeActivity).inflate(R.layout.rest_list_lay, parent, false);
            return new RestaurantResultSecondaryHolder(view);
        }else{
            View view = LayoutInflater.from(restaurantHomeActivity).inflate(R.layout.layout_empty, parent, false);
            return new ItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof RestaurantResultSecondaryHolder){
            System.out.println("sree in this rest 2");
            final RestaurantListModel model = restList.get(position);
            final RestaurantResultSecondaryHolder secondaryHolder = (RestaurantResultSecondaryHolder) holder;
            System.out.println("sree url "+ model.getImageUrl());
            System.out.println("sree model "+ model.getRestid());
            if(!restaurantHomeActivity.isFinishing()) {
                Glide.with(restaurantHomeActivity)
                        .load(model.getImageUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(secondaryHolder.restImage);
            }
            secondaryHolder.cusineName.setText(model.getCusine());
            String price =restaurantHomeActivity.getString(R.string.rs)+model.getPrice()+" "+restaurantHomeActivity.getString(R.string.fortwo);
          //  secondaryHolder.price.setText(restaurantHomeActivity.getString(R.string.rs)+model.getPrice()+restaurantHomeActivity.getString(R.string.fortwo));
            secondaryHolder.price.setText(price);
            secondaryHolder.restName.setText(model.getName());
            secondaryHolder.rating.setText(model.getRating());
            secondaryHolder.time.setText(model.getTime() +" "+ restaurantHomeActivity.getString(R.string.min));
            if(model.getOffer() != null && !model.getOffer().equalsIgnoreCase("NA")){
                secondaryHolder.offerText.setText(model.getOffer());
            }else{
                secondaryHolder.offerText.setVisibility(View.GONE);
            }
            if(model.getFav()==0){
                secondaryHolder.fav.setImageDrawable(restaurantHomeActivity.getDrawable(R.drawable.ic_heart_grey));
            }
            else{
                secondaryHolder.fav.setImageDrawable(restaurantHomeActivity.getDrawable(R.drawable.ic_heart_red));
            }

            secondaryHolder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(model.getFav()==0){
                        secondaryHolder.fav.setImageDrawable(restaurantHomeActivity.getDrawable(R.drawable.ic_heart_red));
                        model.setFav(1);
                    }
                    else{
                        secondaryHolder.fav.setImageDrawable(restaurantHomeActivity.getDrawable(R.drawable.ic_heart_grey));
                        model.setFav(0);
                    }
                }
            });
            secondaryHolder.restResultLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    launchRestDetailsPage(position,model.getRestid());
                }
            });
        }

    }

    private void launchRestDetailsPage(int position,String restId) {
        Intent intent = new Intent(restaurantHomeActivity, RestaurantDetailActivity.class);
        intent.putExtra(RestaurantHomeActivity.MODEL, (Serializable) restList.get(position));
        intent.putExtra("pos",position);
        intent.putExtra("frm","HOMEACTIVITY");
        intent.putExtra(RestaurantHomeActivity.RESTAURANTID,restId);
        restaurantHomeActivity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return restList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position<restList.size()){
            return -1;
        }else{
            return -2;
        }
      }

    private class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(View view) {
            super(view);
        }
    }
}
