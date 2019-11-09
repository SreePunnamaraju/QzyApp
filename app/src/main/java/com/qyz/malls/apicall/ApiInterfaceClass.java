package com.qyz.malls.apicall;

import org.json.JSONObject;

import java.util.LinkedHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiInterfaceClass {

    @GET("restaurants")
    Call<JSONObject> getRestaurants(@QueryMap LinkedHashMap<String, String> params);

    @GET("items")
    Call<JSONObject> getItems(@QueryMap LinkedHashMap<String, String> params);

    @POST("users")
    Call<JSONObject> postUser(@Body RequestBody body);

    @POST("sequence")
    Call<JSONObject> getSequenceNumber(@QueryMap LinkedHashMap<String, String> params);

}
