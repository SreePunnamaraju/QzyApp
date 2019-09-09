package com.qyz.malls.restaurants;

import com.qyz.malls.restaurants.models.MenuItemModel;

public interface CartListener {
    void removeItemFromCart(MenuItemModel position);

    void addItemToCart(MenuItemModel position);
}
