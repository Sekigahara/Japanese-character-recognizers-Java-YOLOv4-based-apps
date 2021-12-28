package com.example.yoloapps.modules.MainMenu;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yoloapps.R;
import com.example.yoloapps.base.BaseFragment;
import com.example.yoloapps.modules.Camera.CameraActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Calendar;

public class MainMenuFragment extends BaseFragment<MainMenuActivity, MainMenuContract.Presenter> implements MainMenuContract.View{
    Button btPhoto;
    private static final int CAMERA_PIC_REQUEST = 1888;
    private Uri uriFilePath;

    public MainMenuFragment(){

    }

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.activity_main, container,false);
        mPresenter = new MainMenuPresenter(this, activity);
        mPresenter.start();

        btPhoto = fragmentView.findViewById(R.id.bt_photo);
        PackageManager packageManager = getActivity().getPackageManager();
        btPhoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPresenter.toCamera(packageManager);
            }
        });

        return fragmentView;
    }

    public void setPresenter(MainMenuContract.Presenter presenter){
        mPresenter = presenter;
    }

    public void redirectPageCamera(Uri uriFilePath){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uriFilePath);
        this.uriFilePath = uriFilePath;
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    public void onSaveInstanceState(Bundle outState){
        if (uriFilePath != null)
            outState.putString("uri_file_path", uriFilePath.toString());
        super.onSaveInstanceState(outState);
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            if (uriFilePath == null & savedInstanceState.getString("uri_file_path") != null)
                uriFilePath = Uri.parse((savedInstanceState.getString("uri_file_path")));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAMERA_PIC_REQUEST){
            activity.finishActivity(requestCode);
            cameraActivityRedirect(uriFilePath.toString());
        }
    }

    private void cameraActivityRedirect(String path){
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra("uri", uriFilePath.toString());
        startActivity(intent);
        activity.finish();
    }
}
