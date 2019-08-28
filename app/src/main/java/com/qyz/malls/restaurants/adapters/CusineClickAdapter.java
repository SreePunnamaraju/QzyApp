package com.qyz.malls.restaurants.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.HomeActivity;
import com.qyz.malls.R;
import com.qyz.malls.restaurants.holder.CusineClickHolder;
import com.qyz.malls.restaurants.models.CuisineFilterModel;

import java.util.ArrayList;

public class CusineClickAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    HomeActivity homeActivity;
    ArrayList<CuisineFilterModel> cusineFilterList;

    public CusineClickAdapter(HomeActivity homeActivity, ArrayList<CuisineFilterModel> cusineFilterList) {
        this.homeActivity = homeActivity;
        this.cusineFilterList = cusineFilterList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(homeActivity).inflate(R.layout.cuisine_layout,parent,false);
        CusineClickHolder cusineClickAdapter = new CusineClickHolder(view);
        return cusineClickAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof CusineClickHolder) {
            CusineClickHolder cusineClickHolder = (CusineClickHolder) holder;
            CuisineFilterModel model = cusineFilterList.get(position);
            cusineClickHolder.textView.setText(model.getName());
            cusineClickHolder.cardView.setCardBackgroundColor(model.getColour());
        }


    }

    @Override
    public int getItemCount() {
        return cusineFilterList.size();
    }
}
