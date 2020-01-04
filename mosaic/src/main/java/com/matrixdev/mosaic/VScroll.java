package com.matrixdev.mosaic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class VScroll extends ScrollView {

    private int verticalScrollOffset;
    OnTouchListener onTouchListener;
    HorizontalScrollView hs;


    public VScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VScroll(Context context) {
        super(context);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
////        return onTouchListener.onTouch(null,ev);
//        return false;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        onTouchListener.onTouch(null,event);
        Log.d("----vtouch",""+event.getAction());
        hs.dispatchTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        super.onInterceptTouchEvent(event);
        hs.onInterceptTouchEvent(event);
        return true;
    }

    @Override
    protected int computeVerticalScrollOffset() {
        verticalScrollOffset = super.computeVerticalScrollOffset();
        return verticalScrollOffset;
    }

    public int getVerticalScrollOffset() {
        return verticalScrollOffset;
    }

    public void setVerticalScrollOffset(int verticalScrollOffset) {
        this.verticalScrollOffset = verticalScrollOffset;
    }

    public OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public HorizontalScrollView getHs() {
        return hs;
    }

    public void setHs(HorizontalScrollView hs) {
        this.hs = hs;
    }
}