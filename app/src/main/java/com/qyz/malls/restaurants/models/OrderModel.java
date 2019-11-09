package com.qyz.malls.restaurants.models;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderModel implements Serializable{
    private String partition;
    private String sort;
    private String gsi_partition;
    private String transaction_total;
    private String date_time;
    private String rating;
    private String review;
    private String restaurant_name;
    private String payment_method;
    private String payment_id;
    private String status;
    private ArrayList<ItemLinesModel> item_lines;
    private ArrayList<ExtraChargesModel> extra_charges;

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getGsi_partition() {
        return gsi_partition;
    }

    public void setGsi_partition(String gsi_partition) {
        this.gsi_partition = gsi_partition;
    }

    public String getTransaction_total() {
        return transaction_total;
    }

    public void setTransaction_total(String transaction_total) {
        this.transaction_total = transaction_total;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }
    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ItemLinesModel> getItem_lines() {
        return item_lines;
    }

    public void setItem_lines(ArrayList<ItemLinesModel> item_lines) {
        this.item_lines = item_lines;
    }

    public ArrayList<ExtraChargesModel> getExtra_charges() {
        return extra_charges;
    }

    public void setExtra_charges(ArrayList<ExtraChargesModel> extra_charges) {
        this.extra_charges = extra_charges;
    }
}
