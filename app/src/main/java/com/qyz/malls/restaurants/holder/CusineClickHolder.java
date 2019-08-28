package com.qyz.malls.restaurants.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;

public class CusineClickHolder extends RecyclerView.ViewHolder{

    public CardView cardView;
    public TextView textView;


    public CusineClickHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cusineCardView);
        textView = itemView.findViewById(R.id.cuisineTextView);
    }
}
