package com.qyz.malls.restaurants.holder;


import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;

public class BannerHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;

    public BannerHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.banner);

    }
}
