package com.qyz.malls.apicall;

public class ApiCallResponse {

    private static ApiCallResponse mInstance;

    public ApiCallResponse(){

    }
    public static synchronized ApiCallResponse getInstance() {
        if (mInstance == null) {
            mInstance = new ApiCallResponse();
        }
        return mInstance;
    }

}
