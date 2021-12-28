package com.example.yoloapps.modules.helper;

import com.example.yoloapps.model.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("detect")
    Call<Response> imageClassification(
            @Query("image") String image
    );

}