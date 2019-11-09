package com.qyz.malls.restaurants.models;

import java.util.HashMap;

public class CheckoutCart {

    //    public String mallId;
    public String restId;

    public String orderId;
    int count;
    HashMap<MenuItemModel,Integer> cartItems;
    public RestaurantListModel restaurantListModel;

//    public String getMallId() {
//        return mallId;
//    }

//    public void setMallId(String mallId) {
//        this.mallId = mallId;
//    }

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
        return cartItems;
    }

    public void setCart(HashMap<MenuItemModel, Integer> cart) {
        this.cartItems = cart;
    }

    public RestaurantListModel getRestaurantListModel() {
        return restaurantListModel;
    }

    public void setRestaurantListModel(RestaurantListModel restaurantListModel) {
        this.restaurantListModel = restaurantListModel;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    public CheckoutCart(){
//        mallId ="-1";
        restId ="-1";
        count =0;
        cartItems = new HashMap<MenuItemModel, Integer>();
        restaurantListModel = null;
    }
}
