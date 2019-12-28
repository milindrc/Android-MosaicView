package com.matrixdev.mosaic;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

public class MosaicView extends View {

    int perc, side;
    Context context;
    private CountDownTimer countDownTimer;
    private HScroll hscroll;
    private VScroll vScroll;
    private int maxScrollX;
    private int horzLim;

    private final int halfOffset = 40;
    private final int fullOffset = 80;
    private int leftDistance;
    private int rightDistance;
    private int midX;
    private int currentPosX;
    private int maxScrollY;
    private int verLim;
    private int midY;
    private int topDistance;
    private int bottomDistance;
    private int currentPosY;

    ArrayList<BitmapObjectClass> bitmapObjectClasses = new ArrayList<>();
    int totalCompanies = -1;

    int count = 1;
    TypedArray draw;
    int random = 0;
    private float touchX;
    private float touchY;
    private Handler timer;
    private ItemChooseInterface onItemChooseListener;

    public MosaicView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MosaicView);
//        draw = context.getResources().obtainTypedArray(R.array.random_imgs);
//
//        bitmapObjectClasses.clear();
//        for (int i = 0; i < draw.length(); i++) {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), draw.getResourceId(i, -1));
//            random = new Random().nextInt(70);
//            Log.d("-----RANDOM", "" + random);
//            bitmap = addRoundCorners(bitmap, random);
//            BitmapObjectClass bitmapObjectClass = new BitmapObjectClass();
//
//            bitmapObjectClass.setBitmap(bitmap);
//            bitmapObjectClasses.add(bitmapObjectClass);
//        }
//
        attributes.recycle();
//        totalCompanies = draw.length();
//        draw.recycle();
    }

    public MosaicView(Context context, Drawable mCustomImage) {
        super(context);

    }

    public void drawRectCenter(Canvas canvas, BitmapObjectClass bitmapObjectClass, int x, int y, int height, int width) {

        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;

        int percX;

        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(255);
        int pivotX = currentPosX + (centerX - x);
        if (pivotX > midX) { //left
            int dist = pivotX - midX;
            if (dist > leftDistance) {
                percX = 100;
            } else {
                percX = (dist * 100) / leftDistance;
            }
            int h = (height * percX) / 100;
            height = height - ((h * percX) / 100);
            int w = (width * percX) / 100;
            width = width - ((w * percX) / 100);
            alphaPaint.setAlpha(alphaPaint.getAlpha() - Math.abs((150 * percX) / 100));
        } else {  //right
            int dist = midX - pivotX;
            if (dist > rightDistance) {
                percX = 100;
            } else {
                percX = (dist * 100) / rightDistance;
            }
            int h = (height * percX) / 100;
            height = height - ((h * percX) / 100);
            int w = (width * percX) / 100;
            width = width - ((w * percX) / 100);
            alphaPaint.setAlpha(alphaPaint.getAlpha() - Math.abs((150 * percX) / 100));
        }

        int pivotY = currentPosY + (centerY - y);
        int percY;
        if (pivotY > midY) { //left
            int dist = pivotY - midX;
            if (dist > topDistance) {
                percY = 100;
            } else {
                percY = (dist * 100) / topDistance;
            }
            int h = (height * percY) / 100;
            height = height - ((h * percY) / 100);
            int w = (width * percY) / 100;
            width = width - ((w * percY) / 100);
            alphaPaint.setAlpha(alphaPaint.getAlpha() - Math.abs((150 * percY) / 100));
        } else {  //right
            int dist = midY - pivotY;
            if (dist > bottomDistance) {
                percY = 100;
            } else {
                percY = (dist * 100) / bottomDistance;
            }
            int h = (height * percY) / 100;
            height = height - ((h * percY) / 100);
            int w = (width * percY) / 100;
            width = width - ((w * percY) / 100);
            alphaPaint.setAlpha(alphaPaint.getAlpha() - Math.abs((150 * percY) / 100));
        }

        RectF rect = new RectF();
        rect.set(x - width / 2, y - height / 2, x + width / 2, y + height / 2);

        //check if touchX and touchY is inside rect
        //shrink
        if (touchX > 0 && touchY > 0) {
            if (rect.bottom >= touchY && rect.top <= touchY && rect.left <= touchX && rect.right >= touchX) {
                rect.set(rect.left + dpToPx(10), rect.top + dpToPx(10), rect.right - dpToPx(10), rect.bottom - dpToPx(10));
            }
        }

        alphaPaint.setAntiAlias(true);
        canvas.drawBitmap(bitmapObjectClass.getBitmap(), null, rect, alphaPaint);

        setBitmapClassCoordinates(bitmapObjectClass, x - width / 2, y - height / 2, x + width / 2, y + height / 2);
        this.count++;

    }

    private void setBitmapClassCoordinates(BitmapObjectClass bitmapObjectClass, int left, int top, int right, int bottom) {
        bitmapObjectClass.setLeft(left);
        bitmapObjectClass.setTop(top);
        bitmapObjectClass.setRight(right);
        bitmapObjectClass.setBottom(bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("-----Down", "down");
            touchX = motionEvent.getX();
            touchY = motionEvent.getY();

            timer = new Handler();
            timer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //todo use touchx and touchy

                    for (int i = 0; i < bitmapObjectClasses.size(); i++) {
                        if (bitmapObjectClasses.get(i).getLeft() <= touchX &&
                                bitmapObjectClasses.get(i).getRight() >= touchX &&
                                bitmapObjectClasses.get(i).getTop() <= touchY &&
                                bitmapObjectClasses.get(i).getBottom() >= touchY ) {

                            BitmapObjectClass data = bitmapObjectClasses.get(i);
                            if(onItemChooseListener!=null)
                                onItemChooseListener.itemChoose(data.getGenericObject());
                        }
                    }

                }
            }, 300);
        }
        return super.onTouchEvent(motionEvent);

    }


    private void init() {

        hscroll = ((HScroll) ((LinearLayout) getParent()).getParent());
        vScroll = (VScroll) ((HScroll) ((LinearLayout) getParent()).getParent()).getParent();

        hscroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                maxScrollX = getWidth() - hscroll.getMeasuredWidth();
                hscroll.scrollTo(maxScrollX / 2, 0);
                horzLim = hscroll.getMeasuredWidth() / 2 + 500;

                maxScrollY = getHeight() - vScroll.getMeasuredHeight();
                vScroll.scrollTo(0, maxScrollY / 2);
                verLim = vScroll.getMeasuredHeight() / 2;
            }
        });
        hscroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (timer != null)
                    timer.removeCallbacksAndMessages(null);

                int arr[] = new int[2];
                getLocationInWindow(arr);

                midX = maxScrollX / 2;
                leftDistance = Math.abs(midX + horzLim) - midX;
                rightDistance = midX - Math.abs(midX - horzLim);
                currentPosX = hscroll.getScrollX();

                midY = maxScrollY / 2;
                topDistance = Math.abs(midY + verLim) - midY + 500;
                bottomDistance = midY - Math.abs(midY - verLim) + 500;
                currentPosY = vScroll.getScrollY();

                perc = (Math.abs(arr[0]) * 100) / maxScrollX;
                side = 50 + ((50) * perc) / 100;

                if (countDownTimer == null) {
                    countDownTimer = new CountDownTimer(10, 10) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            invalidate();
                            countDownTimer = null;

                        }

                    }.start();
                }
            }
        });

//        touchHandler.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                for (int i = 0; i < bitmapObjectClasses.size(); i++) {
//                    if (bitmapObjectClasses.get(i).getLeft() <= motionEvent.getX()+hscroll.getScrollX() &&
//                            bitmapObjectClasses.get(i).getRight() >= motionEvent.getX()+hscroll.getScrollX() &&
//                            bitmapObjectClasses.get(i).getTop() <= motionEvent.getY()+vScroll.getScrollY() &&
//                            bitmapObjectClasses.get(i).getBottom() >= motionEvent.getY()+vScroll.getScrollY()) {
//                        Log.d("Suuceess", "" + motionEvent.getAction());
//                        switch (motionEvent.getAction()) {
//                            case (MotionEvent.ACTION_DOWN):
//                                hscroll.setBlocked(true);
//                                vScroll.setBlocked(true);
//                                touchX = motionEvent.getX()+hscroll.getScrollX();
//                                touchY = motionEvent.getY()+vScroll.getScrollY();
//                                invalidate();
//                                Log.d("------", "Action was DOWN");
//                        }
//                    }
//                }
////                switch (motionEvent.getAction()) {
////                    case (MotionEvent.ACTION_MOVE):
////                        if(touchX!=0) {
////
////                        }else{
//////                    motionEvent.setLocation(motionEvent.getX()+hscroll.getScrollX(),motionEvent.getY()+vScroll.getScrollY());
////                        }
////                        break;
////                    case (MotionEvent.ACTION_UP):
////                        touchX = 0;
////                        touchY = 0;
////                        Log.d("-----ACTION_UP", "dssf");
////                        invalidate();
////                    case MotionEvent.ACTION_CANCEL:
////                        touchX = 0;
////                        touchY = 0;
////                        Log.d("-----ACTION_CAncel", "cancel");
//////                invalidate();
////                }
////        if(touchX!=0)
//
//                Log.d("-----",""+motionEvent);
////        singleTapGesture.onTouchEvent(motionEvent);
////            motionEvent.setLocation(2654,2667);
//                if(touchX!=0) {
//                    motionEvent.setLocation(motionEvent.getX()+hscroll.getScrollX(),motionEvent.getY()+vScroll.getScrollY());
//                    return true;
//                }else {
////            hscroll.setBlocked(false);
////            vScroll.setBlocked(false);
//                    return false;
//                }
//            }
//        });


    }


    private Bitmap addRoundCorners(Bitmap bmp, int padding_y) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight() + padding_y, bmp.getConfig());

        RectF rect = new RectF();
        rect.set(0, padding_y, bmp.getWidth(), bmp.getHeight() + padding_y);

        Path path = new Path();
        path.addRoundRect(rect, dpToPx(20), dpToPx(20), Path.Direction.CW);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.clipPath(path);
        canvas.drawColor(
                Color.TRANSPARENT,
                PorterDuff.Mode.CLEAR);

        canvas.drawBitmap(bmp, null, rect, paint);
        return bmpWithBorder;
    }

    private int dpToPx(float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return pixels;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() != null) {
            init();
        } else {

        }
    }


    protected void onDraw(Canvas canvas) {

        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;

        count = 1;

        if (count <= totalCompanies) {
            drawRectCenter(canvas, bitmapObjectClasses.get(count - 1), centerX, centerY, dpToPx(fullOffset), dpToPx(fullOffset));
        }

        for (int level = 1; level < 5; level++) {
            if (count <= totalCompanies) {
                addToHexagon(level, canvas);
            } else {
                break;
            }
        }

        super.onDraw(canvas);

    }

    private void addToHexagon(int level, Canvas canvas) {
        if (count <= totalCompanies) {
            addViewAt(-fullOffset * level, 0, canvas);
        }
        int start = -fullOffset * level;
        for (int i = 1; i <= level; i++) {
            if (count <= totalCompanies) {
                addViewAt(start + (halfOffset * i), -(fullOffset * i), canvas);
            }
            if (count <= totalCompanies) {
                addViewAt(-(start + (halfOffset * i)), -(fullOffset * i), canvas);
            }
            if (count <= totalCompanies) {
                addViewAt(start + (halfOffset * i), (fullOffset * i), canvas);
            }
            if (count <= totalCompanies) {
                addViewAt(-(start + (halfOffset * i)), (fullOffset * i), canvas);
            }
            if (count > totalCompanies) {
                break;
            }
        }
        if (count <= totalCompanies) {
            addViewAt(fullOffset * level, 0, canvas);
        }
        if (level > 1) {
            int topStart = start + (halfOffset * level);
            int topEnd = -(start + (halfOffset * level));
            while (topStart < topEnd - fullOffset) {
                topStart += fullOffset;
                if (count <= totalCompanies) {
                    addViewAt(topStart, -(fullOffset * level), canvas);
                }
                if (count <= totalCompanies) {
                    addViewAt(topStart, (fullOffset * level), canvas);
                }
                if (count > totalCompanies) {
                    break;
                }
            }
        }
    }

    private void addViewAt(int offSetX, int offSetY, Canvas canvas) {
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;
        int leftMargin = centerX + dpToPx(offSetX) + getMargin(offSetX);
        int topMargin = centerY + dpToPx(offSetY) + getMargin(offSetY);
        drawRectCenter(canvas, bitmapObjectClasses.get(count - 1), leftMargin, topMargin, dpToPx(fullOffset), dpToPx(fullOffset));

    }

    private int getMargin(int offSet) {
        if (offSet % 50 == 0) {
            if (offSet > 0) {
                return 30 * (Math.abs(offSet) / fullOffset);
            } else if (offSet == 0) {
                return 0;
            } else {
                return -30 * (Math.abs(offSet) / fullOffset);
            }
        } else {
            if (offSet > 0) {
                return 15 * (Math.abs(offSet) / halfOffset);
            } else if (offSet == 0) {
                return 0;
            } else {
                return -15 * (Math.abs(offSet) / halfOffset);
            }
        }
    }


    class TapGesture extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("-----TAP", "single tapppp");
            return super.onSingleTapConfirmed(e);
        }
    }

    public <T extends BitmapContainer>void prepare(ItemChooseInterface<T> onItemChooseListener,ArrayList<T> objects){
        this.onItemChooseListener = onItemChooseListener;
        bitmapObjectClasses.clear();
        for (int i = 0; i < objects.size(); i++) {
            random = new Random().nextInt(70);
            Log.d("-----RANDOM", "" + random);
            Bitmap bitmap = addRoundCorners(objects.get(i).toBitmap(), random);
            BitmapObjectClass bitmapObjectClass = new BitmapObjectClass();

            bitmapObjectClass.setBitmap(bitmap);
            bitmapObjectClass.setGenericObject(objects.get(i));
            bitmapObjectClasses.add(bitmapObjectClass);
        }
        totalCompanies = draw.length();
        invalidate();
    }
}