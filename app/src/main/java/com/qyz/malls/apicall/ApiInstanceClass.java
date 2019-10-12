package com.qyz.malls.apicall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstanceClass {

    static ApiInstanceClass mInstance;
    okhttp3.OkHttpClient okHttpClient;
    public static final int CONNECTION_TIMEOUT_MS = 30000;

    public static synchronized ApiInstanceClass getInstance() {
        if (mInstance == null) {
            mInstance = new ApiInstanceClass();
        }
        return mInstance;
    }
    public static ApiInterfaceClass getBaseInterface() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://f27qlq2y9g.execute-api.ap-south-1.amazonaws.com/Test/").addConverterFactory(new ToStringConverterFactory()).client(ApiInstanceClass.getInstance().getOkHttpClient()).build();
//        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://mapi1.justdis.com/").addConverterFactory(new ToStringConverterFactoryReturnString()).client(VectorApp.getInstance().getOkHttpClient()).build();
        return retrofit.create(ApiInterfaceClass.class);
    }

    public  okhttp3.OkHttpClient getOkHttpClient()
    {
        if(okHttpClient != null)
        {
            return okHttpClient;
        }
        okhttp3.OkHttpClient.Builder okClientBuilder = new okhttp3.OkHttpClient.Builder();
        /*HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);*/
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okClientBuilder.connectTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        okClientBuilder.readTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        okClientBuilder.writeTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        okHttpClient = okClientBuilder.build();
        return okHttpClient;
    }

    public void getRestaurantList(ApiInterfaceClass apiInstanceClass, LinkedHashMap<String, String> apiCallMap, final ApiCallBackInterface callBackInterface,final String requestTag) {

        apiInstanceClass.getRestaurants(apiCallMap).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                try {
                    System.out.println("sree json "+ call.request().toString());
                    callBackInterface.onResponseSuccess(response.body(),requestTag);
                } catch (JSONException e) {
                    System.out.println("sree json err"+ response.toString()+" "+response.body()+" "+response.raw());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                callBackInterface.onResponseFailure(requestTag);
            }
        });

    }
}
