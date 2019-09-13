package com.qyz.malls.restaurants.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;
import com.qyz.malls.restaurants.activity.CheckOutPageActivity;
import com.qyz.malls.restaurants.holder.CartItemHolder;
import com.qyz.malls.restaurants.models.CheckoutCart;
import com.qyz.malls.restaurants.models.MenuItemModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CartMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<MenuItemModel> menuItemModels;
    CheckOutPageActivity mActivity;
    HashMap<String,MenuItemModel> map;
    int totalPrice=0;
    CheckoutCart checkoutCart;

    public CartMenuAdapter(CheckOutPageActivity checkOutPageActivity, ArrayList<MenuItemModel> models) {
        this.mActivity =checkOutPageActivity;
        this.menuItemModels = models;
        checkoutCart = mActivity.checkoutCart;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.restaunt_checkout_row,parent,false);
        CartItemHolder holder = new CartItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        final MenuItemModel menuModel = menuItemModels.get(position);
        final CartItemHolder holder = (CartItemHolder) holder1;
        holder.itemName.setText(menuModel.getName());
        if (menuModel.getCount() == 0 ){
            holder.itemCountNonZero.setVisibility(View.GONE);
            holder.itemCountZero.setVisibility(View.VISIBLE);
        }
        else{
            holder.itemCountZero.setVisibility(View.GONE);
            holder.itemCountNonZero.setVisibility(View.VISIBLE);
            holder.itemVal.setText(menuModel.getCount()+"");
        }
        String str = menuModel.getPrice();
        str = str.replaceAll("[^\\d]", "").trim();
        final int price = Integer.valueOf(str);
        System.out.println("sree price "+str + " "+price);
        setItemPriceVal(holder,price,menuModel.getCount(),0);
        holder.decreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menuModel.count>1) {
                    menuModel.setCount(menuModel.getCount()-1);
                    setItemPriceVal(holder,price,menuModel.getCount(),-1);
                }
                else{
                    menuItemModels.remove(menuModel);
                    CartMenuAdapter.this.notifyDataSetChanged();
                }
                updateCart(menuModel,0);
            }
        });
        holder.increaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuModel.setCount(menuModel.getCount()+1);
                holder.itemVal.setText(menuModel.getCount()+"");
                setItemPriceVal(holder,price,menuModel.getCount(),1);
                updateCart(menuModel,1);
            }
        });
    }

    private void updateCart(MenuItemModel model,int val) {
        int count = model.getCount();
        HashMap<MenuItemModel,Integer> shopCart = mActivity.checkoutCart.getCart();
        if(count == 0){
            shopCart.remove(model);
        }
        else{
            shopCart.put(model,count);
        }
        if(val == 0) {
            mActivity.checkoutCart.setCount(mActivity.checkoutCart.getCount() - 1);
        }else{
            mActivity.checkoutCart.setCount(mActivity.checkoutCart.getCount() + 1);
        }
        mActivity.checkoutCart.setCart(shopCart);
    }

    private void setItemPriceVal(CartItemHolder holder, int price, int count,int change) {
        int totpri = price*count;
        holder.itemPrice.setText(mActivity.getResources().getString(R.string.rs)+totpri+"");
        holder.itemVal.setText(count+"");
        HashMap<MenuItemModel,Integer> shopCart = mActivity.checkoutCart.getCart();
        if(change == 0){
            totalPrice+=totpri;
        }else if(change == -1){
            totalPrice -=price;
        }else if(change == 1){
            totalPrice+=price;
        }
        System.out.println("sree total price "+totalPrice);
        mActivity.updateTotalPrice(totalPrice);
    }


    @Override
    public int getItemCount() {
        return menuItemModels.size();
    }

}
