package com.example.yoloapps.modules.MainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.yoloapps.R;
import com.example.yoloapps.base.BaseFragment;
import com.example.yoloapps.modules.Camera.CameraActivity;

public class MainMenuFragment extends BaseFragment<MainMenuActivity, MainMenuContract.Presenter> implements MainMenuContract.View{
    Button btPhoto;

    public MainMenuFragment(){

    }

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.activity_main, container,false);
        mPresenter = new MainMenuPresenter(this, activity);
        mPresenter.start();

        btPhoto = fragmentView.findViewById(R.id.bt_photo);
        btPhoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPresenter.toCamera();
            }
        });

        return fragmentView;
    }

    public void setPresenter(MainMenuContract.Presenter presenter){
        mPresenter = presenter;
    }

    public void redirectPageCamera(){
        Intent intent = new Intent(activity, CameraActivity.class);
        startActivity(intent);
        activity.finish();
    }
}
