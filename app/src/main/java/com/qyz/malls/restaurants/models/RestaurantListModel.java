package com.qyz.malls.restaurants.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantListModel implements Parcelable, Serializable {

    @SerializedName("fav")
    @Expose
    private int fav=0;

    @SerializedName("sort")
    @Expose
    private String restid;

    @SerializedName("partition")
    @Expose
    private String mallid;

    @SerializedName("restaurant_name")
    @Expose
    private String name;

    @SerializedName("cuisine")
    @Expose
    private String cusine;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("avg_time")
    @Expose
    private String time;

    @SerializedName("picture")
    @Expose
    private String imageUrl;

    @SerializedName("offer")
    @Expose
    private String offer;

    @SerializedName("cost_for_two")
    @Expose
    private String price;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCusine() {
        return cusine;
    }

    public void setCusine(String cusine) {
        this.cusine = cusine;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private RestaurantListModel(Parcel in) {
        fav = in.readInt();
        restid = in.readString();
        mallid = in.readString();
        name = in.readString();
        cusine = in.readString();
        rating = in.readString();
        time = in.readString();
        imageUrl = in.readString();
        offer = in.readString();
        price = in.readString();
    }

    public static final Creator<RestaurantListModel> CREATOR = new Creator<RestaurantListModel>() {
        @Override
        public RestaurantListModel createFromParcel(Parcel in) {
            return new RestaurantListModel(in);
        }

        @Override
        public RestaurantListModel[] newArray(int size) {
            return new RestaurantListModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(fav);
        parcel.writeString(restid);
        parcel.writeString(mallid);
        parcel.writeString(name);
        parcel.writeString(cusine);
        parcel.writeString(rating);
        parcel.writeString(time);
        parcel.writeString(imageUrl);
        parcel.writeString(offer);
        parcel.writeString(price);
    }
}
