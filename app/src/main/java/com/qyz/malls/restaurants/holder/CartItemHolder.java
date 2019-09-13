package com.qyz.malls.restaurants.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;

public class CartItemHolder extends RecyclerView.ViewHolder{
    public TextView itemName,itemPrice,itemVal;
    public RelativeLayout itemCountZero;
    public LinearLayout itemCountNonZero;
    public RelativeLayout decreaseCount,increaseCount;

    public CartItemHolder(@NonNull View itemView) {
        super(itemView);
        itemName =itemView.findViewById(R.id.item_name);
        itemPrice =itemView.findViewById(R.id.price_total);
        itemVal =itemView.findViewById(R.id.itemVal);
        itemCountZero =itemView.findViewById(R.id.itemCountZero);
        itemCountNonZero =itemView.findViewById(R.id.itemCountNonZero);
        decreaseCount = itemView.findViewById(R.id.decrease);
        increaseCount = itemView.findViewById(R.id.increase);
    }

}
