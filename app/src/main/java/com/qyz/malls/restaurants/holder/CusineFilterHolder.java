package com.qyz.malls.restaurants.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;

public class CusineFilterHolder extends RecyclerView.ViewHolder{
    public RecyclerView recyclerView;

    public CusineFilterHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.cusineRecyler);
    }
}
