package com.qyz.malls.restaurants.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;
import com.qyz.malls.restaurants.interfaces.FilterListener;
import com.qyz.malls.restaurants.models.CuisineFilterModel;

public class CusineFilterHolder extends RecyclerView.ViewHolder implements FilterListener {
    public RecyclerView recyclerView;
    public RelativeLayout filterIcon,searchbox;
    public TextView searchbar;

    public CusineFilterHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.cusineRecyler);
        filterIcon = itemView.findViewById(R.id.filterIcon);
        searchbar= itemView.findViewById(R.id.search_bar);
        searchbox= itemView.findViewById(R.id.searchbox);
    }

    @Override
    public void onCuisineClick(CuisineFilterModel model) {
        searchbar.setText(model.getName());
    }
}
