package com.example.yoloapps.modules.MainMenu;

import android.view.View;

import com.example.yoloapps.base.BaseFragmentHolderActivity;

public class MainMenuActivity extends BaseFragmentHolderActivity {
    MainMenuFragment mainMenuFragment;

    protected void initializeFragment(){
        initializeView();

        btBack.setVisibility(View.GONE);
        ivIcon.setVisibility(View.GONE);
        tvToolbarTitle.setVisibility(View.VISIBLE);

        tvToolbarTitle.setText("Character Recognition with Yolov4");

        mainMenuFragment = new MainMenuFragment();
        setCurrentFragment(mainMenuFragment, false);
    }
}
