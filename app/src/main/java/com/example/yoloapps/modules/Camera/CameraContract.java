package com.example.yoloapps.modules.Camera;

import android.net.Uri;

import com.example.yoloapps.base.BasePresenter;
import com.example.yoloapps.base.BaseView;
import com.example.yoloapps.model.Response;

public interface CameraContract {
    interface View extends BaseView<Presenter> {
        void detectionCallback(Response response, boolean status);
        void detect(Uri uri);
    }

    interface Presenter extends BasePresenter{
        void getDetection(String takenImage);
    }
}
