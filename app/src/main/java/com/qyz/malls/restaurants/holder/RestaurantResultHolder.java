package com.qyz.malls.restaurants.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;

public class RestaurantResultHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public RecyclerView recyclerView;

    public RestaurantResultHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.viewName);
        recyclerView = itemView.findViewById(R.id.restResultRecyler);
    }
}
