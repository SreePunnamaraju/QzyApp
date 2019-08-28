package com.qyz.malls.restaurants.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CuisineFilterModel implements Serializable, Parcelable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("colour")
    @Expose
    private String colour;

    protected CuisineFilterModel(Parcel in) {
        name = in.readString();
        colour = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(colour);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CuisineFilterModel> CREATOR = new Creator<CuisineFilterModel>() {
        @Override
        public CuisineFilterModel createFromParcel(Parcel in) {
            return new CuisineFilterModel(in);
        }

        @Override
        public CuisineFilterModel[] newArray(int size) {
            return new CuisineFilterModel[size];
        }
    };
}
