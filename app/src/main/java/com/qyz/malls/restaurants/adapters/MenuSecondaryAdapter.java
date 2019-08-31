package com.qyz.malls.restaurants.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;
import com.qyz.malls.restaurants.RestaurantDetailActivity;
import com.qyz.malls.restaurants.holder.MenuSecondaryHolder;
import com.qyz.malls.restaurants.models.MenuItemModel;

import java.util.ArrayList;

class MenuSecondaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    RestaurantDetailActivity mActivity;
    ArrayList<MenuItemModel> menuItemList;


    public MenuSecondaryAdapter(RestaurantDetailActivity mActivity, ArrayList<MenuItemModel> menuItemList) {
        this.mActivity=mActivity;
        this.menuItemList=menuItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.menu_item_lay,parent,false);
        MenuSecondaryHolder secondaryHolder = new MenuSecondaryHolder(view);
        return secondaryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder menuHolder, int position) {
        MenuItemModel menuModel = menuItemList.get(position);
        if(menuHolder instanceof MenuSecondaryHolder){
            MenuSecondaryHolder holder =(MenuSecondaryHolder) menuHolder;
            holder.itemName.setText(menuModel.getName());
            holder.itemPrice.setText(menuModel.getPrice());
            int count = position % 3;
            if (count == 0 ){
                holder.itemCountNonZero.setVisibility(View.GONE);
                holder.itemCountZero.setVisibility(View.VISIBLE);
            }
            else{
                holder.itemCountZero.setVisibility(View.GONE);
                holder.itemCountNonZero.setVisibility(View.VISIBLE);
                holder.itemVal.setText(String.valueOf(count));
            }
        }

    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }
}
