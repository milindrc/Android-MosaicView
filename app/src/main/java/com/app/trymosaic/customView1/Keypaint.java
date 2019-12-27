package com.app.trymosaic.customView1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.app.trymosaic.R;

public class Keypaint extends View {
    Paint p;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p=new Paint();
        Bitmap b= BitmapFactory.decodeResource(getResources(), R.drawable.usability_testing_prototype);
        p.setColor(Color.RED);
        canvas.drawBitmap(b, 50, 50, p);
    }

    public Keypaint(Context context) {
        super(context);
    }
}