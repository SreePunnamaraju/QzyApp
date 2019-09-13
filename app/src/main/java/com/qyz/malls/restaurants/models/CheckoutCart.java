package com.qyz.malls.restaurants.models;

import java.util.HashMap;

public class CheckoutCart {

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HashMap<MenuItemModel, Integer> getCart() {
        return cart;
    }

    public void setCart(HashMap<MenuItemModel, Integer> cart) {
        this.cart = cart;
    }


    public String mallId;
    public String restId;
    int count;
    HashMap<MenuItemModel,Integer> cart;

    public RestaurantListModel getRestaurantListModel() {
        return restaurantListModel;
    }

    public void setRestaurantListModel(RestaurantListModel restaurantListModel) {
        this.restaurantListModel = restaurantListModel;
    }

    public RestaurantListModel restaurantListModel;

    public CheckoutCart(){
        mallId ="-1";
        restId ="-1";
        count =0;
        cart = new HashMap<MenuItemModel, Integer>();
        restaurantListModel = null;
    }
}
