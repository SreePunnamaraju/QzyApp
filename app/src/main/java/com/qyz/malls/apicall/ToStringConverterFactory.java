package com.qyz.malls.apicall;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ToStringConverterFactory extends Converter.Factory {

    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (JSONObject.class.equals(type)) {
            return new Converter<ResponseBody, JSONObject>() {
                @Override
                public JSONObject convert(ResponseBody value) {
                    JSONObject jsonToReturn = new JSONObject();
                    try {
                        jsonToReturn = new JSONObject(value.string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return jsonToReturn;
                }
            };
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (JSONObject.class.equals(type)) {
            return new Converter<JSONObject, RequestBody>() {
                @Override
                public RequestBody convert(JSONObject value) {
                    return RequestBody.create(MEDIA_TYPE, value.toString());
                }
            };
        }

        return null;
    }
}
