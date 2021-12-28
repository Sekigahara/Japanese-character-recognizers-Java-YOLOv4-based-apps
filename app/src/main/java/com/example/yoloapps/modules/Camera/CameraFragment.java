package com.example.yoloapps.modules.Camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yoloapps.R;
import com.example.yoloapps.base.BaseFragment;
import com.example.yoloapps.model.Response;
import com.example.yoloapps.modules.MainActivity;
import com.example.yoloapps.modules.MainMenu.MainMenuActivity;
import com.example.yoloapps.modules.Camera.CameraContract;
import com.example.yoloapps.modules.Camera.CameraPresenter;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        // Set Main Image
        ivPhoto = fragmentView.findViewById(R.id.ivMainPhoto);

        detect(imagePath);

        return fragmentView;
    }

    public void detect(Uri uriPath){
        Bitmap tempImg = uriToBitmap(imagePath);
        String encodedImg = encodeToBase64(tempImg);

        mPresenter.getDetection(encodedImg);
    }

    private String encodeToBase64(Bitmap image){
        ByteArrayOutputStream byteData = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0 , byteData);
        byte[] b = byteData.toByteArray();
        String encodedImg = Base64.encodeToString(b, Base64.DEFAULT);

        return encodedImg;
    }

    public void detectionCallback(Response response, boolean status){
        if(response != null && status != false){
            Bitmap image = byteToBitmap(response.main_image.getBytes(StandardCharsets.UTF_8));
            if(image == null)
                Toast.makeText(activity, "Image is Null", Toast.LENGTH_LONG).show();
            else
                ivPhoto.setImageBitmap(image);
        }else{
            Toast.makeText(activity, "No Characters Detected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, MainMenuActivity.class);
            startActivity(intent);
            activity.finish();
        }
    }

    private Bitmap byteToBitmap(byte[] data){
        byte[] decodedString = Base64.decode(data, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, data.length);
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

    private byte[] bitmapToByte(Bitmap bitmapData){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmapData.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }
}
