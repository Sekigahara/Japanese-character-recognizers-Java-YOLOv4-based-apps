package com.example.yoloapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Response implements Serializable {
    @SerializedName("Main_Image")
    @Expose
    public String main_image;

    //@SerializedName("Cropped_Image")
    //@Expose
    //public byte[][] cropped_image;

    //@SerializedName("Predicted")
    //@Expose
    //public String[] predicted;
}
