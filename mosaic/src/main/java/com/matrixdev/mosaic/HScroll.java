package com.matrixdev.mosaic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class HScroll extends HorizontalScrollView {


    OnTouchListener onTouchListener;
    ScrollView scrollView;

    public HScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HScroll(Context context) {
        super(context);
    }

//    @Override
////    public boolean onTouchEvent(MotionEvent ev) {
//////        return onTouchListener.onTouch(null,ev);
////        return false;
////    }

//    @Override public boolean onTouchEvent(MotionEvent event)
//    {
//        boolean ret = super.onTouchEvent(event);
//        ret = ret | scrollView.onTouchEvent(event);
//        return ret;
//    }
//
//    @Override public boolean onInterceptTouchEvent(MotionEvent event)
//    {
//        boolean ret = super.onInterceptTouchEvent(event);
//        ret = ret | scrollView.onInterceptTouchEvent(event);
//        return ret;
//    }


    public OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }
}
