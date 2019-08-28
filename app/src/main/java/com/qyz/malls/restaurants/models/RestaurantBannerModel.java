package com.qyz.malls.restaurants.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantBannerModel implements Serializable, Parcelable {

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

    @SerializedName("restid")
    @Expose
    private String restid;

    @SerializedName("mallid")
    @Expose
    private String mallid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("offer")
    @Expose
    private String offer;

    protected RestaurantBannerModel(Parcel in) {
        restid = in.readString();
        mallid = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        offer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restid);
        dest.writeString(mallid);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(offer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RestaurantBannerModel> CREATOR = new Creator<RestaurantBannerModel>() {
        @Override
        public RestaurantBannerModel createFromParcel(Parcel in) {
            return new RestaurantBannerModel(in);
        }

        @Override
        public RestaurantBannerModel[] newArray(int size) {
            return new RestaurantBannerModel[size];
        }
    };
}
