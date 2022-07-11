package com.example.yoloapps.modules.Camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.widget.Toast;

import com.example.yoloapps.model.Response;
import com.example.yoloapps.model.SendImage;
import com.example.yoloapps.model.SubResponse;
import com.example.yoloapps.modules.MainMenu.MainMenuContract;
import com.example.yoloapps.modules.helper.ApiService;
import com.example.yoloapps.modules.helper.UtilsApi;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CameraPresenter implements CameraContract.Presenter{
    private final CameraContract.View view;
    private final Context context;
    ApiService mApiService;

    public CameraPresenter(CameraContract.View view , Context context){
        this.view = view;
        this.context = context;
    }

    public void start(){

    }

    public void getDetection(final String takenImage){
        SendImage packetToAPI = new SendImage();
        packetToAPI.main_image = takenImage;

        mApiService = UtilsApi.getAPIService();
        Call<Response> call = mApiService.imageClassification(packetToAPI);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                boolean status = false;
                Response response_data = null;
                if(response.isSuccessful()){
                    Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();

                    if(response.body() != null){
                        Toast.makeText(context, "Response succesfully retrieved", Toast.LENGTH_LONG).show();
                        response_data = response.body();
                        status = true;
                    }
                }else{
                    Toast.makeText(context, "Image Not Succesfully Retrieved", Toast.LENGTH_LONG).show();
                }

                view.detectionCallback(response_data, status);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("Range")
    public String uriToFilename(Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public File bmpToFile(Bitmap bmp, String filename){
        //create a file to write bitmap data
        File dir = new File(Environment.getExternalStorageDirectory(), context.getApplicationContext().getPackageName() + ".provider");
        if (!dir.exists())
            dir.mkdirs();

        File f = new File(dir, filename);
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bmp.compress(Bitmap.CompressFormat.PNG, 0, os);
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    public Bitmap uriToBitmap(Uri uriPath){
        Bitmap img = null;
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uriPath, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

            img = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        }catch (IOException e){
            e.printStackTrace();
        }

        return Bitmap.createScaledBitmap(img, 732, 732, true);
    }

    public Bitmap byteToBitmap(byte[] data){
        byte[] decodedString = Base64.decode(data, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public String encodeToBase64(Bitmap image){
        ByteArrayOutputStream byteData = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0 , byteData);
        byte[] b = byteData.toByteArray();
        String encodedImg = Base64.encodeToString(b, Base64.DEFAULT);

        return encodedImg;
    }

    public ArrayList<SubResponse> recycleData(String[] image_arr, String[] predicted_arr){
        ArrayList<SubResponse> subResponseArray = new ArrayList<>();

        for(int i = 0; i < image_arr.length;i++){
            SubResponse subResponse = new SubResponse();
            subResponse.label = predicted_arr[i];
            Bitmap image = byteToBitmap(image_arr[i].getBytes(StandardCharsets.UTF_8));
            subResponse.image = image = Bitmap.createScaledBitmap(image, 476, 476, true);

            subResponseArray.add(subResponse);
        }

        return subResponseArray;
    }

    public Bitmap rotationCheck(Uri selectedImage){
        RotationHandler rotationHandler = new RotationHandler();

        Bitmap image = null;
        try{
            image = rotationHandler.handleSamplingAndRotation(context, selectedImage);
        }catch (IOException e){

        }

        return image;
    }

}
