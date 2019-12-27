package com.app.trymosaic.customView1;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


public class Draw extends View {
    Paint paint = new Paint();

    public Draw(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Compute the height required to render the view
        // Assume Width will always be MATCH_PARENT.
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 3000 + 50; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(Canvas canvas) {

        paint.setColor(Color.GREEN);
        canvas.drawRect(30, 30, 90, 200, paint);
        paint.setColor(Color.BLUE);

        canvas.drawLine(100, 20, 100, 1900, paint);

        paint.setColor(Color.GREEN);
        canvas.drawRect(200, 2000, 400, 3000, paint);
    }
}