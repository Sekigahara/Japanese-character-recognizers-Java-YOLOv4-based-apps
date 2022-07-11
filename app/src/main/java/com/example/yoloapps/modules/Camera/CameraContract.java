package com.example.yoloapps.modules.Camera;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.yoloapps.base.BasePresenter;
import com.example.yoloapps.base.BaseView;
import com.example.yoloapps.model.Response;
import com.example.yoloapps.model.SubResponse;

import java.io.File;
import java.util.ArrayList;

public interface CameraContract {
    interface View extends BaseView<Presenter> {
        void detectionCallback(Response response, boolean status);
    }

    interface Presenter extends BasePresenter{
        void getDetection(String takenImage);
        Bitmap uriToBitmap(Uri uriPath);
        String encodeToBase64(Bitmap image);
        Bitmap byteToBitmap(byte[] data);
        ArrayList<SubResponse> recycleData(String[] image_arr, String[] predicted_arr);
        Bitmap rotationCheck(Uri selectedImage);

        String uriToFilename(Uri uri);
        File bmpToFile(Bitmap bmp, String filename);
    }
}
