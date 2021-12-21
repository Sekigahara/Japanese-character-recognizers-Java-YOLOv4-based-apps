package com.example.yoloapps.modules.Camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.yoloapps.R;
import com.example.yoloapps.base.BaseFragment;
import com.example.yoloapps.modules.MainActivity;
import com.example.yoloapps.modules.MainMenu.MainMenuActivity;
import com.example.yoloapps.modules.Camera.CameraContract;
import com.example.yoloapps.modules.Camera.CameraPresenter;

import java.io.FileDescriptor;
import java.io.IOException;

public class CameraFragment extends BaseFragment<CameraActivity, CameraContract.Presenter> implements CameraContract.View{
    ImageView ivPhoto;
    Uri imagePath;

    public CameraFragment(Uri imagePath){
        this.imagePath = imagePath;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.activity_camera, container,false);
        mPresenter = new CameraPresenter(this, activity);
        mPresenter.start();

        ivPhoto = fragmentView.findViewById(R.id.ivMainPhoto);
        Bitmap image = uriToBitmap(imagePath);
        ivPhoto.setImageBitmap(image);

        return fragmentView;
    }

    private Bitmap uriToBitmap(Uri uriPath){
        Bitmap img = null;
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(uriPath, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

            img = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        }catch (IOException e){
            e.printStackTrace();
        }

        return img;
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
