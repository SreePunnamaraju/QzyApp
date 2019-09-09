package com.qyz.malls.restaurants.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.HomeActivity;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.CartListener;
import com.qyz.malls.restaurants.RestaurantDetailActivity;
import com.qyz.malls.restaurants.holder.MenuSecondaryHolder;
import com.qyz.malls.restaurants.models.MenuItemModel;

import java.util.ArrayList;

class MenuSecondaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RestaurantDetailActivity mActivity;
    ArrayList<MenuItemModel> menuItemList;
    CartListener callBack;


    public MenuSecondaryAdapter(RestaurantDetailActivity mActivity, ArrayList<MenuItemModel> menuItemList) {
        this.mActivity=mActivity;
        this.menuItemList=menuItemList;
    }

    public MenuSecondaryAdapter(RestaurantDetailActivity mActivity, ArrayList<MenuItemModel> menuItemList,CartListener listener) {
        this.mActivity=mActivity;
        this.menuItemList=menuItemList;
        this.callBack = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.menu_item_lay,parent,false);
        MenuSecondaryHolder secondaryHolder = new MenuSecondaryHolder(view);
        return secondaryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder menuHolder, final int position) {
        final MenuItemModel menuModel = menuItemList.get(position);
        if(menuHolder instanceof MenuSecondaryHolder){
            final MenuSecondaryHolder holder =(MenuSecondaryHolder) menuHolder;
            holder.itemName.setText(menuModel.getName());
            holder.itemPrice.setText(menuModel.getPrice());
            if (menuModel.getCount() == 0 ){
                holder.itemCountNonZero.setVisibility(View.GONE);
                holder.itemCountZero.setVisibility(View.VISIBLE);
            }
            else{
                holder.itemCountZero.setVisibility(View.GONE);
                holder.itemCountNonZero.setVisibility(View.VISIBLE);
                updateCount(holder,menuModel);
            }
            holder.decreaseCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(menuModel.count>0) {
                        if(menuModel.getCount() ==1){
                            holder.itemCountNonZero.setVisibility(View.GONE);
                            holder.itemCountZero.setVisibility(View.VISIBLE);
                        }
                        menuModel.setCount(menuModel.getCount()-1);
                        updateCount(holder,menuModel);
                        callBack.removeItemFromCart(menuModel);
                    }
                }
            });
            holder.increaseCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuModel.setCount(menuModel.getCount()+1);
                    updateCount(holder,menuModel);
                    callBack.addItemToCart(menuModel);
                }
            });
            holder.itemCountZero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.itemCountZero.setVisibility(View.GONE);
                    holder.itemCountNonZero.setVisibility(View.VISIBLE);
                    menuModel.setCount(menuModel.getCount()+1);
                    updateCount(holder,menuModel);
                    callBack.addItemToCart(menuModel);
                }
            });
        }

    }

    private void updateCount(MenuSecondaryHolder holder,MenuItemModel menuModel) {
        holder.itemVal.setText(String.valueOf(menuModel.getCount()));
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

}
