package com.matrixdev.mosaic;

import android.graphics.Bitmap;

public class BitmapObjectClass<T>{

    private Bitmap bitmap=null;
    private int left;
    private int top;
    private int right;
    private int bottom;

    private T genericObject;

    public void setGenericObject(T object) {
        this.genericObject = object;
    }

    public T getGenericObject() {
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

}
