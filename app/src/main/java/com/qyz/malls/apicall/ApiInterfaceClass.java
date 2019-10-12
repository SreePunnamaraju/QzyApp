package com.qyz.malls.apicall;

import org.json.JSONObject;

import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterfaceClass {

    @GET("restaurants")
    Call<JSONObject> getRestaurants(@QueryMap LinkedHashMap<String, String> params);
}
