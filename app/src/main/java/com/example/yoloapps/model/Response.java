package com.example.yoloapps.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class Response implements Serializable {
    @SerializedName("status")
    @Expose
    public Boolean status;

    @SerializedName("Main_Image")
    @Expose
    public String main_image;

    @SerializedName("Cropped_Image")
    @Expose
    public String[] cropped_image;

    @SerializedName("Predicted")
    @Expose
    public String[] predicted;
}
