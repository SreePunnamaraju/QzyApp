package com.qyz.malls.restaurants;

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

    public HashMap<String, Integer> getCart() {
        return cart;
    }

    public void setCart(HashMap<String, Integer> cart) {
        this.cart = cart;
    }

    String mallId;
    String restId;
    int count;
    HashMap<String,Integer> cart;

    public CheckoutCart(){
        mallId ="-1";
        restId ="-1";
        count =0;
        cart = new HashMap<String, Integer>();
    }
}
