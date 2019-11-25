package com.qyz.malls.restaurants.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;
import com.qyz.malls.qzyCustom.QzyTextView;

public class RestaurantResultSecondaryHolder extends RecyclerView.ViewHolder {

    public ImageView restImage,fav;
    public TextView rating,restName,cusineName,time,price;
    public RelativeLayout restResultLay;
    public QzyTextView offerText;

    public RestaurantResultSecondaryHolder(@NonNull View itemView) {
        super(itemView);
        restImage = itemView.findViewById(R.id.restImage);
        fav = itemView.findViewById(R.id.fav);
        rating = itemView.findViewById(R.id.rating);
        restName = itemView.findViewById(R.id.restName);
        cusineName = itemView.findViewById(R.id.cusineName);
        time = itemView.findViewById(R.id.time);
        price = itemView.findViewById(R.id.price);
        restResultLay = itemView.findViewById(R.id.restResultLay);
        offerText = itemView.findViewById(R.id.offer_text);
    }
}
