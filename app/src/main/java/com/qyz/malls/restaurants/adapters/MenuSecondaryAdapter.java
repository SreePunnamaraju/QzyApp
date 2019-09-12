package com.qyz.malls.restaurants.adapters;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.HomeActivity;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.CartListener;
import com.qyz.malls.restaurants.CheckoutCart;
import com.qyz.malls.restaurants.RestaurantDetailActivity;
import com.qyz.malls.restaurants.holder.MenuSecondaryHolder;
import com.qyz.malls.restaurants.models.MenuItemModel;

import java.util.ArrayList;
import java.util.HashMap;

class MenuSecondaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RestaurantDetailActivity mActivity;
    ArrayList<MenuItemModel> menuItemList;
    CartListener callBack;
    int pos;

    public MenuSecondaryAdapter(RestaurantDetailActivity mActivity, ArrayList<MenuItemModel> menuItemList) {
        this.mActivity=mActivity;
        this.menuItemList=menuItemList;
    }

    public MenuSecondaryAdapter(RestaurantDetailActivity mActivity, ArrayList<MenuItemModel> menuItemList,CartListener listener) {
        this.mActivity=mActivity;
        this.menuItemList=menuItemList;
        this.callBack = listener;
    }

    public MenuSecondaryAdapter(RestaurantDetailActivity mActivity, ArrayList<MenuItemModel> menuItemList,CartListener listener,int pos) {
        this.mActivity=mActivity;
        this.menuItemList=menuItemList;
        this.callBack = listener;
        this.pos =pos;
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
            menuModel.setRestid(String.valueOf(pos+1));
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
                 /*       if(menuModel.getCount() ==1){
                            holder.itemCountNonZero.setVisibility(View.GONE);
                            holder.itemCountZero.setVisibility(View.VISIBLE);
                        }*/
                        menuModel.setCount(menuModel.getCount()-1);
                     //   updateCount(holder,menuModel,holder);
                        removeItemFromCart(menuModel,holder);
                    }
                }
            });
            holder.increaseCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuModel.setCount(menuModel.getCount()+1);
                  //  updateCount(holder,menuModel,holder);
                    addItemToCart(menuModel,holder);
                }
            });
            holder.itemCountZero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  holder.itemCountZero.setVisibility(View.GONE);
                   // holder.itemCountNonZero.setVisibility(View.VISIBLE);
                    menuModel.setCount(menuModel.getCount()+1);
                  //  updateCount(holder,menuModel,holder);
                    addItemToCart(menuModel,holder);
                }
            });
        }

    }

    private void removeItemFromCart(MenuItemModel model,MenuSecondaryHolder menuSecondaryHolder) {
        if(mActivity.cart.mallId.equals("-1") ){
            mActivity.cart.mallId = model.getMallid();
            mActivity.cart.restId = model.getRestid();
        }
        HashMap<String,Integer> shopCart = mActivity.cart.getCart();
        if(mActivity.cart.mallId.equals(model.getMallid())&& mActivity.cart.restId.equals(model.getRestid())){
            int count = shopCart.get(model.getItemid())-1;
            if(count == 0){
                shopCart.remove(model.getItemid());
            }
            else{
                shopCart.put(model.getItemid(),count);
            }
        }
        mActivity.cart.setCount(mActivity.cart.getCount()-1);
        mActivity.cart.setCart(shopCart);
        callBack.updateMainCart(mActivity.cart.getCount());
        updateCount(menuSecondaryHolder,model);
    }

    public void addItemToCart(final MenuItemModel model, final MenuSecondaryHolder holder) {
        System.out.println("sree bool "+ mActivity.cart.getMallId().equals("-1"));
        if(mActivity.cart.getMallId().equals("-1") ){
            System.out.println("sree id in this "+mActivity.cart.getMallId());
            mActivity.cart.setMallId(model.getMallid());
            System.out.println("sree id in this 1"+mActivity.cart.getMallId()+" "+mActivity.cart.getRestId());
            mActivity.cart.setRestId(model.getRestid());
            System.out.println("sree id in this 2"+mActivity.cart.getRestId());
        }
        System.out.println("sree id "+mActivity.cart.getRestId()+" "+model.getRestid() );
        if(mActivity.cart.getMallId().equals(model.getMallid()) && mActivity.cart.getRestId().equals(model.getRestid())){
            System.out.println("sree id in this if");
            addItem(model,holder);
        }
        else{
            System.out.println("sree id in this else");
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mActivity);
            builder1.setMessage("You already have items in your cart.Do you want to discard them?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mActivity.cart= new CheckoutCart();
                            System.out.println("sree cart "+mActivity.cart.getRestId()+" "+mActivity.cart.getMallId()+" "+mActivity.cart.getCount());
                            addItem(model,holder);
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    public void addItem(MenuItemModel model,MenuSecondaryHolder holder){
        if(mActivity.cart.getMallId().equals("-1") ){
            mActivity.cart.setMallId(model.getMallid());
            mActivity.cart.setRestId(model.getRestid());
        }
        final HashMap<String,Integer> shopCart = mActivity.cart.getCart();
        if(shopCart.containsKey(model.getItemid())){
            int c = shopCart.get(model.getItemid())+1;
            shopCart.put(model.getItemid(),c);
        }
        else{
            shopCart.put(model.getItemid(),1);
        }
        mActivity.cart.setCount(mActivity.cart.getCount()+1);
        mActivity.cart.setCart(shopCart);
        callBack.updateMainCart(mActivity.cart.getCount());
        updateCount(holder,model);
    }

    private void updateCount(MenuSecondaryHolder holder,MenuItemModel menuModel) {
        if(menuModel.getCount()==0){
            holder.itemCountNonZero.setVisibility(View.GONE);
            holder.itemCountZero.setVisibility(View.VISIBLE);
        }
        else{
            holder.itemCountZero.setVisibility(View.GONE);
            holder.itemCountNonZero.setVisibility(View.VISIBLE);
            holder.itemVal.setText(String.valueOf(menuModel.getCount()));
        }
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

}
