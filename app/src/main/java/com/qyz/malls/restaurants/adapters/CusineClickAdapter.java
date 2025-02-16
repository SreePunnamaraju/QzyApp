package com.qyz.malls.restaurants.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.restaurants.activity.RestaurantHomeActivity;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.holder.CusineClickHolder;
import com.qyz.malls.restaurants.interfaces.FilterListener;
import com.qyz.malls.restaurants.models.CuisineFilterModel;

import java.util.ArrayList;

public class CusineClickAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RestaurantHomeActivity restaurantHomeActivity;
    ArrayList<CuisineFilterModel> cusineFilterList;
    FilterListener mCallBack;
    RestaurantFilter restaurantFilter;

    public CusineClickAdapter(RestaurantHomeActivity restaurantHomeActivity, ArrayList<CuisineFilterModel> cusineFilterList, FilterListener filterListener, RestaurantFilter restaurantFilter) {
        System.out.println("sree cusine 1 " +cusineFilterList.size());
        this.restaurantHomeActivity = restaurantHomeActivity;
        this.cusineFilterList = cusineFilterList;
        this.mCallBack= filterListener;
        this.restaurantFilter = restaurantFilter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(restaurantHomeActivity).inflate(R.layout.cuisine_layout,parent,false);
        CusineClickHolder cusineClickAdapter = new CusineClickHolder(view);
        return cusineClickAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        System.out.println("sree cusine 2");
        if(holder instanceof CusineClickHolder) {
            CusineClickHolder cusineClickHolder = (CusineClickHolder) holder;
            final CuisineFilterModel model = cusineFilterList.get(position);
            cusineClickHolder.textView.setText(model.getName().toUpperCase());
            cusineClickHolder.linearLayout.setBackgroundResource(R.drawable.round_bg_cuisine);
            GradientDrawable drawable = (GradientDrawable) cusineClickHolder.linearLayout.getBackground();
            drawable.setColor(Color.parseColor(model.getColour()));
            cusineClickHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    restaurantFilter.filterResult(model);
                    mCallBack.onCuisineClick(model);
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return cusineFilterList.size();
    }
}
