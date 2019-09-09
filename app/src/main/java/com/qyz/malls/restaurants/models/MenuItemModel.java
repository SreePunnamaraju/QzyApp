package com.qyz.malls.restaurants.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuItemModel implements Serializable, Parcelable {

    @SerializedName("itemid")
    @Expose
    private String itemid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("restid")
    @Expose
    private String restid;

    @SerializedName("mallid")
    @Expose
    private String mallid;

    @SerializedName("cuisines")
    @Expose
    private String cuisines;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int count = 0;

    protected MenuItemModel(Parcel in) {
        itemid = in.readString();
        name = in.readString();
        price = in.readString();
        restid = in.readString();
        mallid = in.readString();
        cuisines = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemid);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(restid);
        dest.writeString(mallid);
        dest.writeString(cuisines);
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

    public String getMallid() {
        return mallid;
    }

    public void setMallid(String mallid) {
        this.mallid = mallid;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }
}

