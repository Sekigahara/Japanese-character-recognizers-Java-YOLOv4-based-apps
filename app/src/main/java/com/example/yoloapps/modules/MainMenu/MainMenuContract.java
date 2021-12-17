package com.example.yoloapps.modules.MainMenu;

import com.example.yoloapps.base.BasePresenter;
import com.example.yoloapps.base.BaseView;

public interface MainMenuContract {
    interface View extends BaseView<Presenter> {
        void redirectPageCamera();
    }

    interface Presenter extends BasePresenter{
        void toCamera();
    }
}
