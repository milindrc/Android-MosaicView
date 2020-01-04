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
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Random;

public class MosaicView extends View {

    private int defaultColor;
    int perc, side;
    Context context;
    private CountDownTimer countDownTimer;
    private HScroll hscroll;
    private VScroll vScroll;
    private int maxScrollX;
    private int horzLim;

    private int halfOffset = 60;
    private int fullOffset = 120;
    private int leftDistance = 1;
    private int rightDistance=1;
    private int midX;
    private int currentPosX;
    private int maxScrollY;
    private int verLim;
    private int midY;
    private int topDistance = 1;
    private int bottomDistance =1;
    private int currentPosY;
    private Bitmap placeholder;

    ArrayList<BitmapObjectClass> bitmapObjectClasses = new ArrayList<>();
    int totalCompanies = -1;

    int count = 1;
    ArrayList<Integer> random = new ArrayList();
    private float touchX;
    private float touchY;
    private Handler timer;
    private ItemChooseInterface onItemChooseListener;
    private ViewGroup rootview;

    private boolean isRoundedEdges;
    private boolean isFadeEnabled;
    private boolean isShrinkEnabled;


    public MosaicView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        placeholder = createPlaceholder(dpToPx(100), dpToPx(100), Color.WHITE);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MosaicView);

        isRoundedEdges = attributes.getBoolean(R.styleable.MosaicView_isRoundedEdges, true);
        isFadeEnabled = attributes.getBoolean(R.styleable.MosaicView_isFadeEnabled, true);
        isShrinkEnabled = attributes.getBoolean(R.styleable.MosaicView_isShrinkEnabled, true);
        defaultColor = attributes.getColor(R.styleable.MosaicView_imageBackground, Color.WHITE);
        fullOffset = attributes.getInteger(R.styleable.MosaicView_fullSize, 100);
        halfOffset = attributes.getColor(R.styleable.MosaicView_halfSize, 50);

        attributes.recycle();
    }

    public MosaicView(Context context, Drawable mCustomImage) {
        super(context);
        placeholder = createPlaceholder(dpToPx(100), dpToPx(100), Color.WHITE);

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
            if (isShrinkEnabled) {
                int h = (height * percX) / 100;
                height = height - ((h * percX) / 100);
                int w = (width * percX) / 100;
                width = width - ((w * percX) / 100);
            }
            if (isFadeEnabled) {
                alphaPaint.setAlpha(alphaPaint.getAlpha() - Math.abs((150 * percX) / 100));
            }
        } else {  //right
            int dist = midX - pivotX;
            if (dist > rightDistance) {
                percX = 100;
            } else {
                percX = (dist * 100) / rightDistance;
            }
            if (isShrinkEnabled) {
                int h = (height * percX) / 100;
                height = height - ((h * percX) / 100);
                int w = (width * percX) / 100;
                width = width - ((w * percX) / 100);
            }
            if (isFadeEnabled) {
                alphaPaint.setAlpha(alphaPaint.getAlpha() - Math.abs((150 * percX) / 100));
            }
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
            if (percY > 0) {
                if (isShrinkEnabled) {
                    int h = (height * percY) / 100;
                    height = height - ((h * percY) / 100);
                    int w = (width * percY) / 100;
                    width = width - ((w * percY) / 100);
                }
                if (isFadeEnabled) {
                    alphaPaint.setAlpha(alphaPaint.getAlpha() - Math.abs((150 * percY) / 100));
                }
            }
        } else {  //right
            int dist = midY - pivotY;
            if (dist > bottomDistance) {
                percY = 100;
            } else {
                percY = (dist * 100) / bottomDistance;
            }
            if (percY > 0) {
                if (isShrinkEnabled) {
                    int h = (height * percY) / 100;
                    height = height - ((h * percY) / 100);
                    int w = (width * percY) / 100;
                    width = width - ((w * percY) / 100);
                }
                if (isFadeEnabled) {
                    alphaPaint.setAlpha(alphaPaint.getAlpha() - Math.abs((150 * percY) / 100));
                }
            }
        }
//        if(bitmapObjectClass.getGenericObject().toString().equals("Cadbury"))
//            Log.d("-----%XY",""+percX+","+percY);

        RectF rect = new RectF();
        rect.set(x - width / 2, y - height / 2, x + width / 2, y + height / 2);

        //check if touchX and touchY is inside rect
        //shrink
        if (touchX > 0 && touchY > 0) {
            if (rect.bottom >= touchY && rect.top <= touchY && rect.left <= touchX && rect.right >= touchX) {
                rect.set(rect.left + dpToPx(2), rect.top + dpToPx(2), rect.right - dpToPx(2), rect.bottom - dpToPx(2));
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

    private void handleClick() {
        for (int i = 0; i < bitmapObjectClasses.size(); i++) {
            if (bitmapObjectClasses.get(i).getLeft() <= touchX &&
                    bitmapObjectClasses.get(i).getRight() >= touchX &&
                    bitmapObjectClasses.get(i).getTop() <= touchY &&
                    bitmapObjectClasses.get(i).getBottom() >= touchY) {

                playSoundEffect(android.view.SoundEffectConstants.CLICK);
                BitmapObjectClass data = bitmapObjectClasses.get(i);
                if (onItemChooseListener != null)
                    onItemChooseListener.itemChoose(data.getGenericObject());
            }
        }
    }


    private void init() {

        hscroll = ((HScroll) ((LinearLayout) getParent()).getParent());
        vScroll = (VScroll) ((HScroll) ((LinearLayout) getParent()).getParent()).getParent();

        vScroll.setHs(hscroll);
        vScroll.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float curX, curY;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        touchX = event.getX() + hscroll.getScrollX();
                        touchY = event.getY() + vScroll.getScrollY();
                        invalidate();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curX = event.getX();
                        curY = event.getY();
                        Log.d("-----Touch", "" + touchX + "," + touchY + "|" + (curX + hscroll.getScrollX()));

                        if (curX + hscroll.getScrollX() < touchX - 10 || curX + hscroll.getScrollX() > touchX + 10) {
                            touchX = 0;
                            touchY = 0;
                        }
                        if (curY + vScroll.getScrollY() < touchY - 10 || curY + vScroll.getScrollY() > touchY + 10) {
                            touchX = 0;
                            touchY = 0;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (touchX != 0) {

                            handleClick();
                            touchX = 0;
                            touchY = 0;
                            invalidate();
                        }
                        curX = event.getX();
                        curY = event.getY();
                        break;
                }

                return true;
            }
        });
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

    }

    public static Bitmap createPlaceholder(int width, int height, int color) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(0F, 0F, (float) width, (float) height, paint);
        return bitmap;
    }

    private Bitmap addRoundCorners(Bitmap bmp, int padding_y) {

        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight() + padding_y, bmp.getConfig()==null? Bitmap.Config.ARGB_8888 : bmp.getConfig());

        RectF rect = new RectF();
        rect.set(0, padding_y, bmp.getWidth(), bmp.getHeight() + padding_y);

        Path path = new Path();
        path.addRoundRect(rect, dpToPx(20), dpToPx(20), Path.Direction.CW);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.clipPath(path);

        canvas.drawColor(defaultColor);
        canvas.drawBitmap(bmp, null, rect, paint);
        return bmpWithBorder;
    }

    public Bitmap pad(Bitmap Src, int padding_y) {
        Bitmap outputimage = Bitmap.createBitmap(Src.getWidth(), Src.getHeight() + padding_y, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Canvas can = new Canvas(outputimage);
        can.drawColor(defaultColor); //This represents White color
        can.drawBitmap(Src, 0, padding_y, paint);
        return outputimage;
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
        if (offSet % fullOffset == 0) {
            if (offSet > 0) {
                return 40 * (Math.abs(offSet) / fullOffset);
            } else if (offSet == 0) {
                return 0;
            } else {
                return -40 * (Math.abs(offSet) / fullOffset);
            }
        } else {
            if (offSet > 0) {
                return 20 * (Math.abs(offSet) / halfOffset);
            } else if (offSet == 0) {
                return 0;
            } else {
                return -20 * (Math.abs(offSet) / halfOffset);
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

    public <T extends BitmapContainer> void prepare(ArrayList<T> objects, ItemChooseInterface<T> onItemChooseListener) {
        this.onItemChooseListener = onItemChooseListener;
        bitmapObjectClasses.clear();
        for (int i = 0; i < objects.size(); i++) {
            if (i >= random.size()) {
                random.add(new Random().nextInt(70));
            }
            Bitmap bitmap;
            if (isRoundedEdges) {
                bitmap = addRoundCorners(objects.get(i).toBitmap(), random.get(i));
            } else {
                bitmap = pad(objects.get(i).toBitmap(), random.get(i));
            }
            BitmapObjectClass bitmapObjectClass = new BitmapObjectClass();

            bitmapObjectClass.setBitmap(bitmap);
            bitmapObjectClass.setGenericObject(objects.get(i));
            bitmapObjectClasses.add(bitmapObjectClass);
        }
        totalCompanies = objects.size();
        invalidate();
    }

    public <T extends UrlContainer> void prepareFromUrl(ArrayList<T> objects, ItemChooseInterface<T> onItemChooseListener) {
        this.onItemChooseListener = onItemChooseListener;
        bitmapObjectClasses.clear();
        for (int i = 0; i < objects.size(); i++) {
            if (i >= random.size()) {
                random.add(new Random().nextInt(70));
            }
            final BitmapObjectClass bitmapObjectClass = new BitmapObjectClass();
            final int finalI = i;
            if (isRoundedEdges) {
                bitmapObjectClass.setBitmap(addRoundCorners(placeholder, random.get(i)));
            } else {
                bitmapObjectClass.setBitmap(pad(placeholder, random.get(i)));
            }
            Glide.with(context)
                    .asBitmap()
                    .load(objects.get(i).toUrl())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Log.d("----", "loaded" + finalI);
                            if (isRoundedEdges) {
                                Bitmap rounded = addRoundCorners(resource, random.get(finalI));
                                bitmapObjectClass.setBitmap(rounded);
                            } else {
                                bitmapObjectClass.setBitmap(pad(resource, random.get(finalI)));
                            }
                            invalidate();
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
            bitmapObjectClass.setGenericObject(objects.get(i));
            bitmapObjectClasses.add(bitmapObjectClass);
        }
        totalCompanies = objects.size();
        invalidate();
    }

    public Bitmap getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Bitmap placeholder) {
        this.placeholder = placeholder;
    }
}