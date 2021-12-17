package com.example.yoloapps.modules.Camera;

import android.content.Context;

import com.example.yoloapps.modules.MainMenu.MainMenuContract;

public class CameraPresenter implements CameraContract.Presenter{
    private final CameraContract.View view;
    private final Context context;

    public CameraPresenter(CameraContract.View view , Context context){
        this.view = view;
        this.context = context;
    }

    public void start(){

    }

}
