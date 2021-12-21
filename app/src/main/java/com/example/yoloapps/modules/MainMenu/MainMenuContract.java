package com.example.yoloapps.modules.MainMenu;

import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.yoloapps.base.BasePresenter;
import com.example.yoloapps.base.BaseView;

import java.io.File;

public interface MainMenuContract {
    interface View extends BaseView<Presenter> {
        void redirectPageCamera(Uri uri);
        //void redirectPageCamera(File file);
    }

    interface Presenter extends BasePresenter{
        void toCamera(PackageManager packageManager);
    }
}
