package com.matrixdev.mosaic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class VScroll extends ScrollView {

    private int verticalScrollOffset;
    private boolean isBlocked;
    OnTouchListener onTouchListener;


    public VScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VScroll(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        return onTouchListener.onTouch(null,ev);
        return isBlocked;
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

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }
}