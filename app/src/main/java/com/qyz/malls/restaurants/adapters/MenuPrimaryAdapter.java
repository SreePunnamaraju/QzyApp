package com.qyz.malls.restaurants.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;
import com.qyz.malls.restaurants.interfaces.CartListener;
import com.qyz.malls.restaurants.activity.RestaurantDetailActivity;
import com.qyz.malls.restaurants.holder.MenuPrimaryHolder;
import com.qyz.malls.restaurants.models.MenuModel;

import java.util.ArrayList;

public class MenuPrimaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    RestaurantDetailActivity mActivity;
    ArrayList<MenuModel> menuModels;
    CartListener listener;
    int pos;

    public MenuPrimaryAdapter(RestaurantDetailActivity restaurantDetailActivity, ArrayList<MenuModel> menuModels) {
        this.mActivity=restaurantDetailActivity;
        this.menuModels=menuModels;
    }
    public MenuPrimaryAdapter(RestaurantDetailActivity restaurantDetailActivity, ArrayList<MenuModel> menuModels, CartListener cartListener) {
        this.mActivity=restaurantDetailActivity;
        this.menuModels=menuModels;
        this.listener = cartListener;
    }

    public MenuPrimaryAdapter(RestaurantDetailActivity restaurantDetailActivity, ArrayList<MenuModel> menuModels, CartListener cartListener,int pos) {
        this.mActivity=restaurantDetailActivity;
        this.menuModels=menuModels;
        this.listener = cartListener;
        this.pos = pos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.menu_main_scroll,parent,false);
        MenuPrimaryHolder holder = new MenuPrimaryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MenuModel menuModel = menuModels.get(position);
        if(holder instanceof MenuPrimaryHolder){
            final MenuPrimaryHolder primaryHolder = (MenuPrimaryHolder) holder;
            primaryHolder.categoryName.setText(menuModel.getName());
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false);
            MenuSecondaryAdapter secondaryAdapter = new MenuSecondaryAdapter(mActivity,menuModel.getMenuItemList(),listener,pos);

            primaryHolder.categoryRecylerView.setAdapter(secondaryAdapter);
            primaryHolder.categoryRecylerView.setLayoutManager(layoutManager);
            primaryHolder.categoryMinimizeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(primaryHolder.categoryRecylerView.getVisibility() == View.VISIBLE){
                        primaryHolder.categoryMinimizeIcon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_down_icon));
                        primaryHolder.categoryRecylerView.setVisibility(View.GONE);
                    }
                    else{
                        primaryHolder.categoryMinimizeIcon.setImageDrawable(mActivity.getDrawable(R.drawable.ic_up_icon));
                        primaryHolder.categoryRecylerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return menuModels.size();
    }
}
