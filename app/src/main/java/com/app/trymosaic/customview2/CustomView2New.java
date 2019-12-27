package com.app.trymosaic.customview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;

import com.app.trymosaic.R;
import com.app.trymosaic.databinding.CompanyHolderBinding;
import com.matrixdev.mosaic.HScroll;

public class CustomView2New extends RelativeLayout {
    ImageView imageView;
    Context context;
    CompanyHolderBinding binding;
    private final int halfOffset = 25;
    private final int fullOffset = 50;
    int count1 = 6;

    int count = 0;

    public CustomView2New(Context context) {
        super(context);
        this.context = context;
    }

    public CustomView2New(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Log.d("----ONCONS", "ssdd");
        Log.d("----ONCONS", "" + getHeight() + " " + getWidth() + " " + getMeasuredHeight() + " " + getMeasuredWidth());
    }

    private void init() {
        ((HScroll) getParent()).getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int arr[] = new int[2];
                getLocationOnScreen(arr);
                Log.d("-----", "" + getTransitionName() + " " + arr[0]);
                Log.d("-----", "" + arr[1]);
            }
        });

//        ((ViewGroup)((ViewGroup)getParent()).getParent()).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Log.d("-----GETX",""+getX());
//                Log.d("-----GETY",""+getY());
//            }
//        });

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        if(getParent()!=null){
//
//            Log.d("-----ATW","sdf");
//            init();
//        }else{
//            Log.d("-----ATW","errg");
//
//        }
    }

    public CustomView2New(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public CustomView2New(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        Log.d("----ONFINISH", "ssdd");
        Log.d("----ONFINISH", "" + getHeight() + " " + getWidth() + " " + getMeasuredHeight() + " " + getMeasuredWidth());
//        init();

        super.onFinishInflate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("----ONMES", "" + getHeight() + " " + getWidth() + " " + getMeasuredHeight() + " " + getMeasuredWidth());
        if (count <= count1) {
            addViewAt(0, 0);
            count++;
        }
        for (int level = 1; level < 5; level++) {
            if (count <= count1) {
                addToHexagon(level);
            } else {
                break;
            }
        }

//        addViewAt(0,100);
//        addViewAt(100,0);
//        addViewAt(100,100);
//        addViewAt(0,-100);
//        addViewAt(-100,0);
//        addViewAt(-100,-100);
//        addViewAt(100,-100);
//        addViewAt(-100,100);
//
//        addViewAt(100,50);
//        addViewAt(50,100);
//        addViewAt(100,-50);
//        addViewAt(-50,100);
//        addViewAt(50,-100);
//        addViewAt(-50,-100);
//        addViewAt(-100,50);
//        addViewAt(-100,-50);

    }

    private void addToHexagon(int level) {
        if(count<=count1) {
            addViewAt(-fullOffset * level, 0);
            count++;
        }
        int start = -fullOffset * level;
        for (int i = 1; i <= level; i++) {
            if(count<=count1) {
                addViewAt(start + (halfOffset * i), -(fullOffset * i));
                count++;
            }
            if(count<=count1) {
                addViewAt(-(start + (halfOffset * i)), -(fullOffset * i));
                count++;
            }
            if(count<=count1) {
                addViewAt(start + (halfOffset * i), (fullOffset * i));
                count++;
            }
            if(count<=count1) {
                addViewAt(-(start + (halfOffset * i)), (fullOffset * i));
                count++;
            }
            if(count>count1){
                break;
            }
        }
        if(count<=count1) {
            addViewAt(fullOffset * level, 0);
            count++;
        }
        if (level > 1) {
            int topStart = start + (halfOffset * level);
            int topEnd = -(start + (halfOffset * level));
            while (topStart < topEnd) {
                topStart += 50;
                if(count>count1) {
                    addViewAt(topStart, -(fullOffset * level));
                    count++;
                }
                if(count>count1) {
                    addViewAt(topStart, (fullOffset * level));
                    count++;
                }
                if(count>count1){
                    break;
                }
            }
        }
    }

    private void addViewAt(int offSetX, int offSetY) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DataBindingUtil.inflate(inflater, R.layout.company_holder, null, false);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int centerX = getMeasuredWidth() / 2 - dpToPx(50) / 2;
        int centerY = getMeasuredHeight() / 2 - dpToPx(50) / 2;
        params.leftMargin = centerX + dpToPx(offSetX) + getMargin(offSetX);
        params.topMargin = centerY + dpToPx(offSetY) + getMargin(offSetY);
        addView(binding.getRoot(), params);

        if (offSetX == 0 && offSetY == 0) {
            binding.ciholder.setWindowListener();
        }

    }

    private int getMargin(int offSet) {
        if (offSet % 50 == 0) {
            if (offSet > 0) {
                return 10 * (Math.abs(offSet) / 50);
            } else if (offSet == 0) {
                return 0;
            } else {
                return -10 * (Math.abs(offSet) / 50);
            }
        } else {
            if (offSet > 0) {
                return 5 * (Math.abs(offSet) / 25);
            } else if (offSet == 0) {
                return 0;
            } else {
                return -5 * (Math.abs(offSet) / 25);
            }
        }
    }

    private int dpToPx(float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return pixels;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);//        imageView.setBackgroundColor(Color.BLUE);

        Log.d("----WIDTH", "" + getHeight() + " " + getWidth() + " " + getMeasuredHeight() + " " + getMeasuredWidth() + " " + changed);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight(), new Paint());
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, new Paint());

    }
}
