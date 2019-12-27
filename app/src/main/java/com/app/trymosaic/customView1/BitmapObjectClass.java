package com.app.trymosaic.customView1;

import android.graphics.Bitmap;

public class BitmapObjectClass<T> implements ItemChooseInterface{

    private Bitmap bitmap=null;
    private int left;
    private int top;
    private int right;
    private int bottom;

    private Object genericObject;

    public void setGenericObject(Object object) {
        this.genericObject = object;
    }

    public Object getGenericObject() {
        return genericObject;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    @Override
    public void itemChooseCallBack(Object genericObject) {
        this.genericObject=genericObject;
    }
}
