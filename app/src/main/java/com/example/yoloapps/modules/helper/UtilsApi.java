package com.example.yoloapps.modules.helper;

public class UtilsApi {
    //public static final String BASE_URL_API="http://192.168.18.4:5000/api/";
    public static final String BASE_URL_API="http://13.213.63.216:80/api/";
    //public static final String BASE_URL_API="http://9f1b-182-253-116-239.ngrok.io";

    public static ApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }
}