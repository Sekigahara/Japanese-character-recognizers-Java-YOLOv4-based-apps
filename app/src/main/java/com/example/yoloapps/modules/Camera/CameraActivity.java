package com.example.yoloapps.modules.Camera;

import android.view.View;

import com.example.yoloapps.base.BaseFragmentHolderActivity;

public class CameraActivity extends BaseFragmentHolderActivity {
    CameraFragment cameraFragment;

    protected void initializeFragment(){
        initializeView();

        btBack.setVisibility(View.VISIBLE);
        ivIcon.setVisibility(View.GONE);

        tvToolbarTitle.setText("Take Photo");

        cameraFragment = new CameraFragment();
        cameraFragment.setBack(btBack);
        setCurrentFragment(cameraFragment, false);
    }
}
