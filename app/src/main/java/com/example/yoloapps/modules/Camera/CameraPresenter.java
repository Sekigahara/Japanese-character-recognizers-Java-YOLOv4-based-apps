package com.example.yoloapps.modules.Camera;

import android.content.Context;
import android.widget.Toast;

import com.example.yoloapps.model.Response;
import com.example.yoloapps.modules.MainMenu.MainMenuContract;
import com.example.yoloapps.modules.helper.ApiService;
import com.example.yoloapps.modules.helper.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;

public class CameraPresenter implements CameraContract.Presenter{
    private final CameraContract.View view;
    private final Context context;
    ApiService mApiService;

    public CameraPresenter(CameraContract.View view , Context context){
        this.view = view;
        this.context = context;
    }

    public void start(){

    }

    public void getDetection(final String takenImage){
        mApiService = UtilsApi.getAPIService();
        Call<Response> call = mApiService.imageClassification(takenImage);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                boolean status = false;
                Response response_data = null;
                if(response.isSuccessful()){
                    Toast.makeText(context, "Image Succesfully Retrieved", Toast.LENGTH_LONG).show();
                    response_data.main_image = response.body().main_image;
                    status = true;
                }else{
                    Toast.makeText(context, "Image Not Succesfully Retrieved", Toast.LENGTH_LONG).show();
                }

                view.detectionCallback(response_data, status);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
