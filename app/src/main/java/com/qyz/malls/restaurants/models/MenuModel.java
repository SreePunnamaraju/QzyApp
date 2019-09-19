package com.qyz.malls.restaurants.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuModel implements Serializable, Parcelable {
    String name;

    protected MenuModel(Parcel in) {
        name = in.readString();
        menuItemList = in.createTypedArrayList(MenuItemModel.CREATOR);
    }

    public static final Creator<MenuModel> CREATOR = new Creator<MenuModel>() {
        @Override
        public MenuModel createFromParcel(Parcel in) {
            return new MenuModel(in);
        }

        @Override
        public MenuModel[] newArray(int size) {
            return new MenuModel[size];
        }
    };

    public MenuModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MenuItemModel> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(ArrayList<MenuItemModel> menuItemList) {
        this.menuItemList = menuItemList;
    }

    ArrayList<MenuItemModel> menuItemList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(menuItemList);
    }
}
