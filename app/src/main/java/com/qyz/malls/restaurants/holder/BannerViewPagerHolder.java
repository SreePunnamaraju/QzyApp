package com.qyz.malls.restaurants.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.qyz.malls.R;

public class BannerViewPagerHolder extends RecyclerView.ViewHolder {

    public ViewPager viewPager;

    public BannerViewPagerHolder(@NonNull View itemView) {
        super(itemView);
        viewPager = itemView.findViewById(R.id.banner_view_pager);
    }
}
