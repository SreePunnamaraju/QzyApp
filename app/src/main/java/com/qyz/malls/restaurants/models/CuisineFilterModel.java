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

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    @SerializedName("cusid")
    @Expose
    private int cusId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("colour")
    @Expose
    private int colour;

    protected CuisineFilterModel(Parcel in) {
        name = in.readString();
        colour = in.readInt();
        cusId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(colour);
        dest.writeInt(cusId);
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
