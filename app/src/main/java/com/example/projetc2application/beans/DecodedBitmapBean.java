package com.example.projetc2application.beans;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Eurisko on 11/9/18.
 */
public class DecodedBitmapBean implements Serializable {
    Bitmap bitmap;
    String base64Image = "";

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
