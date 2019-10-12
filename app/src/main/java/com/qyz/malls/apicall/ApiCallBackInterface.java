package com.qyz.malls.apicall;

import org.json.JSONException;
import org.json.JSONObject;

public interface ApiCallBackInterface {

    void onResponseSuccess(JSONObject json, String requestTag) throws JSONException;

    void onResponseFailure(String requestTag);
}
