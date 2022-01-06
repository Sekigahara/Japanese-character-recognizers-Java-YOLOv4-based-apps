package com.example.yoloapps.modules.helper;

import com.example.yoloapps.model.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @Headers({"Accept: application/json"})
    @POST("detect")
    Call<Response> imageClassification(
            @Body Response response
    );
}