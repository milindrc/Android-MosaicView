package com.app.trymosaic.customView1;

import android.graphics.Bitmap;

import com.matrixdev.mosaic.BitmapContainer;

public class DataModel implements BitmapContainer {
    String name;
    String url;
    Bitmap bitmap;

    public DataModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public Bitmap toBitmap() {
        return bitmap;
    }
}
