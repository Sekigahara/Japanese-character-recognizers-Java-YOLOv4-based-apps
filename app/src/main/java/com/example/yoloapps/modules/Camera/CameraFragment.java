package com.example.yoloapps.modules.Camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.yoloapps.R;
import com.example.yoloapps.base.BaseFragment;
import com.example.yoloapps.modules.MainActivity;
import com.example.yoloapps.modules.MainMenu.MainMenuActivity;
import com.example.yoloapps.modules.Camera.CameraContract;
import com.example.yoloapps.modules.Camera.CameraPresenter;

public class CameraFragment extends BaseFragment<CameraActivity, CameraContract.Presenter> implements CameraContract.View{
    Button btPhoto;

    public CameraFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.activity_camera, container,false);
        mPresenter = new CameraPresenter(this, activity);
        mPresenter.start();

        return fragmentView;
    }

    public void setBack(ImageButton btBack){
        btBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(activity, MainMenuActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });
    }

    public void setPresenter(CameraContract.Presenter presenter){
        mPresenter = presenter;
    }
}
