package com.qyz.malls.restaurants.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qyz.malls.HomeActivity;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.models.RestaurantBannerModel;

import java.util.ArrayList;

class BannerAdapter extends PagerAdapter {

    HomeActivity homeActivity;
    ArrayList<RestaurantBannerModel> bannerList;
    ViewPager viewPager;

    public BannerAdapter(HomeActivity homeActivity, ArrayList<RestaurantBannerModel> bannerList,ViewPager viewPager) {
        this.homeActivity=homeActivity;
        this.bannerList=bannerList;
        this.viewPager=viewPager;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        View viewItem = LayoutInflater.from(homeActivity).inflate(R.layout.banner, container, false);
        ImageView imageView = viewItem.findViewById(R.id.banner);
        TextView bannerName = viewItem.findViewById(R.id.bannerName);
        TextView bannerOffer = viewItem.findViewById(R.id.bannerOffer);
        Glide.with(homeActivity.getBaseContext())
                .load(bannerList.get(position).getImageUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        bannerName.setText(bannerList.get(position).getName());
        bannerOffer.setText(bannerList.get(position).getOffer());

        viewPager.addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}
