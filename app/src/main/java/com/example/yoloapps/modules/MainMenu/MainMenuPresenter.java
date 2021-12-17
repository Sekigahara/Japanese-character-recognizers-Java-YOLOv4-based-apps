package com.example.yoloapps.modules.MainMenu;

import android.content.Context;

public class MainMenuPresenter implements MainMenuContract.Presenter{
    private final MainMenuContract.View view;
    private final Context context;

    public MainMenuPresenter(MainMenuContract.View view , Context context){
        this.view = view;
        this.context = context;
    }

    public void start(){

    }

    public void toCamera(){
        view.redirectPageCamera();
    }

}
