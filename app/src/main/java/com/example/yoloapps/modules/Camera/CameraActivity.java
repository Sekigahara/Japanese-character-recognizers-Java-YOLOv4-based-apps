package com.example.yoloapps.modules.Camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.View;

import com.example.yoloapps.base.BaseFragmentHolderActivity;

public class CameraActivity extends BaseFragmentHolderActivity {
    CameraFragment cameraFragment;

    protected void initializeFragment(){
        initializeView();

        btBack.setVisibility(View.VISIBLE);
        ivIcon.setVisibility(View.GONE);

        tvToolbarTitle.setText("Result Photo");

        Uri image = Uri.parse(getIntent().getStringExtra("uri"));

        cameraFragment = new CameraFragment(image);
        cameraFragment.setBack(btBack);
        setCurrentFragment(cameraFragment, false);
    }
}
