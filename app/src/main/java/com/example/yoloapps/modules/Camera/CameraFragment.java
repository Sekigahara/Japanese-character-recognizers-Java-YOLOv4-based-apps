package com.example.yoloapps.modules.Camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yoloapps.R;
import com.example.yoloapps.base.BaseFragment;
import com.example.yoloapps.model.Response;
import com.example.yoloapps.model.SubResponse;
import com.example.yoloapps.modules.MainMenu.MainMenuActivity;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CameraFragment extends BaseFragment<CameraActivity, CameraContract.Presenter> implements CameraContract.View{
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerview;
    ImageView ivPhoto;
    TextView textView;
    Uri imagePath;

    public CameraFragment(Uri imagePath){
        this.imagePath = imagePath;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.activity_camera, container,false);
        mPresenter = new CameraPresenter(this, activity);
        mPresenter.start();

        Toast.makeText(activity, imagePath.toString(), Toast.LENGTH_SHORT).show();
        detect(imagePath);

        mRecyclerview = fragmentView.findViewById(R.id.rvCamera);
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerview.setLayoutManager(mLayoutManager);

        // Set Main Image
        textView = fragmentView.findViewById(R.id.textView);
        ivPhoto = fragmentView.findViewById(R.id.ivMainPhoto);

        textView.setText("Result : ");

        return fragmentView;
    }

    private void detect(Uri uriPath){
        Bitmap tempImg = mPresenter.rotationCheck(uriPath);
        String encodedImg = mPresenter.encodeToBase64(tempImg);

        mPresenter.getDetection(encodedImg);
    }

    public void detectionCallback(Response response, boolean status){
        if(response != null && status != false){
            Bitmap image = mPresenter.byteToBitmap(response.main_image.getBytes(StandardCharsets.UTF_8));

            if(image == null)
                Toast.makeText(activity, "Image is Null", Toast.LENGTH_LONG).show();
            else
                ivPhoto.setImageBitmap(image);

            if(response.predicted != null){
                final ArrayList<SubResponse> subResponses = mPresenter.recycleData(response.cropped_image, response.predicted);

                mAdapter = new RecyclerViewCamera(subResponses);
                mRecyclerview.setAdapter(mAdapter);
            }
        }else{
            Toast.makeText(activity, "No Characters Detected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, MainMenuActivity.class);
            startActivity(intent);
            activity.finish();
        }
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
