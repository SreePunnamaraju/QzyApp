package com.qyz.malls.restaurants.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;
import com.qyz.malls.restaurants.CartListener;
import com.qyz.malls.restaurants.RestaurantDetailActivity;
import com.qyz.malls.restaurants.holder.MenuPrimaryHolder;
import com.qyz.malls.restaurants.models.MenuModel;

import java.util.ArrayList;

public class MenuPrimaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    RestaurantDetailActivity mActivity;
    ArrayList<MenuModel> menuModels;
    CartListener listener;

    public MenuPrimaryAdapter(RestaurantDetailActivity restaurantDetailActivity, ArrayList<MenuModel> menuModels) {
        this.mActivity=restaurantDetailActivity;
        this.menuModels=menuModels;
    }
    public MenuPrimaryAdapter(RestaurantDetailActivity restaurantDetailActivity, ArrayList<MenuModel> menuModels, CartListener cartListener) {
        this.mActivity=restaurantDetailActivity;
        this.menuModels=menuModels;
        this.listener = cartListener;
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
            for (int i=0;i<menuModel.getMenuItemList().size();i++){
                menuModel.getMenuItemList().get(i).setRestid(position+"");
            }
            MenuSecondaryAdapter secondaryAdapter = new MenuSecondaryAdapter(mActivity,menuModel.getMenuItemList(),listener);

            primaryHolder.categoryRecylerView.setAdapter(secondaryAdapter);
            primaryHolder.categoryRecylerView.setLayoutManager(layoutManager);
            primaryHolder.categoryMinimizeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(primaryHolder.categoryRecylerView.getVisibility() == View.VISIBLE){
                        primaryHolder.categoryRecylerView.setVisibility(View.GONE);
                    }
                    else{
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
