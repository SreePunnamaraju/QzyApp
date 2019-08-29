package com.qyz.malls.restaurants.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qyz.malls.R;

public class CusineClickHolder extends RecyclerView.ViewHolder{

    public TextView textView;
    public LinearLayout linearLayout;


    public CusineClickHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.cuisineTextView);
        linearLayout = itemView.findViewById(R.id.linearview);
    }
}
