package com.qyz.malls.restaurants.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import com.qyz.malls.R;
import com.qyz.malls.restaurants.activity.RestaurantDetailActivity;
import com.qyz.malls.restaurants.models.MenuItemModel;
import com.qyz.malls.restaurants.models.MenuModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MenuItemSearchFragment extends Fragment{

    View rootView;
    FrameLayout searchContainer;
    AppCompatMultiAutoCompleteTextView searchBar;
    ArrayList<MenuModel> models;
    ArrayList<MenuItemModel> menuItemModels;
    private Timer textCheckTimer = new Timer();
    private RestaurantDetailActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.search_layout,container,false);
        searchBar = rootView.findViewById(R.id.search_edit_text_location);
        searchContainer = rootView.findViewById(R.id.container_search);
        mActivity =(RestaurantDetailActivity) getActivity();
        if(getArguments()!=null && getArguments().containsKey("list") && getArguments().getParcelableArrayList("list")!=null) {
            models = getArguments().getParcelableArrayList("list");
            for(int i=0;i<models.size();i++){
                System.out.println("sree check 2"+models.get(i).getMenuItemList().size());
            }
        }
        searchBar.setTypeface(Typeface.SANS_SERIF);
        searchBar.setCursorVisible(true);
        searchBar.setFocusable(true);
        searchBar.setFocusableInTouchMode(true);
        searchBar.requestFocus();
        searchBar.requestFocusFromTouch();
        searchBar.addTextChangedListener(textChangeWatcher);
        searchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("sree click fragment");
                if(searchBar.getText().toString().trim().length() ==0){
                    hideKeyboard();
                    mActivity.onBackPressed();
                }
            }
        });
        showKeyboard();
        setList();
        return rootView;
    }

    private void setList() {
        menuItemModels = new ArrayList<>();
        for(int i=0;i<models.size();i++){
            menuItemModels.addAll(models.get(i).getMenuItemList());
        }
    }

    private void showKeyboard() {
        System.out.println("sree keyboard activity "+mActivity);
        if (mActivity != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }
    }
    private void hideKeyboard() {
        System.out.println("sree keyboard activity "+mActivity);
        if (mActivity != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchBar.getWindowToken(),0);
        }
    }

    private TextWatcher textChangeWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                final String seq = s.toString();
                final String countString = count + "";
                if (searchBar.getText().toString().trim().length() > 0) {
                    if (searchBar.getText().toString().trim().length() > 1 /*&& (before < 2 || gooautosuggestfire)*/) {
                        textCheckTimer.cancel();
                        textCheckTimer.purge();
                        textCheckTimer = new Timer();
                        textCheckTimer.schedule(new TimerTask() {
                            public void run() {
                                mActivity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        menuSearch(seq);
                                    }
                                });
                            }
                        }, 200);
                    }
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    };

    private void menuSearch(String seq) {
        System.out.println("sree seq "+seq);


    }

}
