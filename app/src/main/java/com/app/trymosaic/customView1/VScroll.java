package com.app.trymosaic.customView1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class VScroll extends ScrollView {

    private int verticalScrollOffset;

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
        return false;
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
}