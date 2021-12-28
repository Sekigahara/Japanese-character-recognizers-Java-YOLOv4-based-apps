package com.example.yoloapps.modules.helper;

public class UtilsApi {
    public static final String BASE_URL_API="http://192.168.18.4:5000/api/";

    public static ApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }
}
