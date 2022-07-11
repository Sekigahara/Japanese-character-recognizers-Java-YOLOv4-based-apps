package com.example.yoloapps.modules.MainMenu;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.yoloapps.R;
import com.example.yoloapps.base.BaseFragment;
import com.example.yoloapps.modules.Camera.CameraActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class MainMenuFragment extends BaseFragment<MainMenuActivity, MainMenuContract.Presenter> implements MainMenuContract.View{
    Button btPhoto;
    Button btGalery;
    private static final int CAMERA_PIC_REQUEST = 1888;
    private static final int EXTERNAL_STORAGE_WRITE = 1889;
    private static final int EXTERNAL_STORAGE_READ = 1890;
    private static final int PICK_IMAGE = 1232;
    private Uri uriFilePath;

    public MainMenuFragment(){

    }

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.activity_main, container,false);
        mPresenter = new MainMenuPresenter(this, activity);
        mPresenter.start();

        btPhoto = fragmentView.findViewById(R.id.bt_photo);
        btGalery = fragmentView.findViewById(R.id.bt_photo_galery);
        PackageManager packageManager = getActivity().getPackageManager();

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, EXTERNAL_STORAGE_WRITE);
        btPhoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, CAMERA_PIC_REQUEST);
                mPresenter.toCamera(packageManager);
            }
        });

        btGalery.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, EXTERNAL_STORAGE_READ);
                //checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, EXTERNAL_STORAGE_READ);
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
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
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_PIC_REQUEST){
            if(resultCode == RESULT_OK){
                activity.finishActivity(requestCode);
                cameraActivityRedirect(uriFilePath.toString());
            }else if(resultCode == RESULT_CANCELED){
                Intent intent = new Intent(activity, MainMenuActivity.class);
                startActivity(intent);
                activity.finish();
            }
        }else if(requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {

                Bitmap data_bmp = null;
                try {
                    InputStream inputStream = activity.getContentResolver().openInputStream(data.getData());
                    data_bmp = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getContext(), "Error pick image", Toast.LENGTH_SHORT).show();
                }

                File tempDir = Environment.getExternalStorageDirectory();
                tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
                tempDir.mkdir();
                File tempFile = null;
                try {
                    tempFile = File.createTempFile(title, ".png", tempDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                data_bmp.compress(Bitmap.CompressFormat.PNG, 0, bytes);
                byte[] bitmapData = bytes.toByteArray();

                //write the bytes in file
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(tempFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(bitmapData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                activity.finishActivity(requestCode);
                cameraActivityRedirect(Uri.fromFile(tempFile).toString());
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getContext(), "Error pick image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { permission }, requestCode);
        }
        else {
            if(requestCode == CAMERA_PIC_REQUEST){
                Toast.makeText(getContext(), "Camera Permission already granted", Toast.LENGTH_SHORT).show();
            }else if(requestCode == EXTERNAL_STORAGE_WRITE){
                Toast.makeText(getContext(), "Storage Permission already granted", Toast.LENGTH_SHORT).show();
            }else if(requestCode == EXTERNAL_STORAGE_READ){
                Toast.makeText(getContext(), "Storage read Permission already granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PIC_REQUEST) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(getContext(), "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == EXTERNAL_STORAGE_WRITE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cameraActivityRedirect(String path1){
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra("uri", path1.toString());
        startActivity(intent);
        activity.finish();
    }
}
