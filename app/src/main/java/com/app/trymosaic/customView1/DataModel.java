package com.app.trymosaic.customView1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.matrixdev.mosaic.BitmapContainer;
import com.matrixdev.mosaic.MosaicView;
import com.matrixdev.mosaic.UrlContainer;

public class DataModel implements BitmapContainer, UrlContainer {
    String name;
    String url;
    Bitmap bitmap;

    public DataModel(String name, String url) {
        this.name = name;
        this.url = url;

        bitmap = textAsBitmap(name, 40*3,Color.BLACK);
    }

    public void load(Activity activity, final Runnable onComplete){
        Glide.with(activity)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        bitmap = resource;
                        onComplete.run();
                    }
                });
    }


    public Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawColor(Color.WHITE);
        canvas.drawText(text, 0, baseline, paint);
        return image;
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

    @Override
    public String toUrl() {
        return url;
    }
}
