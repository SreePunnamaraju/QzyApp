package com.qyz.malls.restaurants.models;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuModel implements Serializable {
    String name;

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
}
