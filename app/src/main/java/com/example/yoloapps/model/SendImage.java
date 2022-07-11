package com.example.yoloapps.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class SendImage implements Serializable {
    @SerializedName("image")
    @Expose
    public String main_image;
}
