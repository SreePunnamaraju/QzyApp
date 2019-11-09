package com.qyz.malls.restaurants.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuItemModel implements Serializable, Parcelable {

    @SerializedName("partition")
    @Expose
    private String restid;

    @SerializedName("sort")
    @Expose
    private String itemid;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("item_name")
    @Expose
    private String name;

    @SerializedName("vegeterian")
    @Expose
    private String vegeterian;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("in_stock")
    @Expose
    private String inStock;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private String image;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int count = 0;

    protected MenuItemModel(Parcel in) {
        vegeterian = in.readString();
        name = in.readString();
        category = in.readString();
        restid = in.readString();
        itemid = in.readString();
        price = in.readString();
        inStock = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vegeterian);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(restid);
        dest.writeString(itemid);
        dest.writeString(price);
        dest.writeString(inStock);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuItemModel> CREATOR = new Creator<MenuItemModel>() {
        @Override
        public MenuItemModel createFromParcel(Parcel in) {
            return new MenuItemModel(in);
        }

        @Override
        public MenuItemModel[] newArray(int size) {
            return new MenuItemModel[size];
        }
    };

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRestid() {
        return restid;
    }

    public void setRestid(String restid) {
        this.restid = restid;
    }

    public String getVegeterian() {
        return vegeterian;
    }

    public void setVegeterian(String vegeterian) {
        this.vegeterian = vegeterian;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

