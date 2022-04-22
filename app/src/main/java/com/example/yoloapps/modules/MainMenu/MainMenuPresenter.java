package com.example.yoloapps.modules.MainMenu;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.Calendar;

public class MainMenuPresenter implements MainMenuContract.Presenter{
    private final MainMenuContract.View view;
    private final Context context;

    public MainMenuPresenter(MainMenuContract.View view , Context context){
        this.view = view;
        this.context = context;
    }

    public void start(){

    }

    public void toCamera(PackageManager packageManager){
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            File dir = new File(Environment.getExternalStorageDirectory(), context.getApplicationContext().getPackageName() + ".provider");
            //File dir = new File(context.getFilesDir(), context.getApplicationContext().getPackageName() + ".provider");
            if (!dir.exists())
                dir.mkdirs();

            Calendar calendar = Calendar.getInstance();

            File file =  new File(dir, "IMG_" + calendar.getTimeInMillis());
            Uri uriFilePath = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

            view.redirectPageCamera(uriFilePath);
        }
    }

}
