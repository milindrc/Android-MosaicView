package com.app.trymosaic.customview2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.app.trymosaic.R;
import com.app.trymosaic.customView1.HScroll;
import com.app.trymosaic.databinding.CompanyHolderBinding;

public class CompanyImageHolder extends RelativeLayout {

    Context context;
    ImageView imageView;

    public CompanyImageHolder(Context context) {
        super(context);
        this.context=context;
//        setImageView(context);
    }

    private void setImageView(Context context) {
        imageView=new ImageView(context);
        LayoutParams params = new LayoutParams(dpToPx(50), dpToPx(50));

        imageView.setImageResource(R.drawable.usability_testing_prototype);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        addView(imageView,params);
    }

    private int dpToPx(float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return pixels;
    }

    public void setWindowListener(){

        init();

    }

    private void init() {
        ((HScroll)((CustomView2New)getParent()).getParent()).getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int arr[] = new int[2];
                getLocationOnScreen(arr);
                if(arr[0]%2==0){
                    int perc = arr[0] / 1000 *100;
                    int side = (dpToPx(50) * perc) /100;
                    ViewGroup.LayoutParams params = getLayoutParams();
                    imageView.setLayoutParams(params);
                }else{
                    imageView.setImageResource(R.color.colorAccent);
                }
                Log.d("-----",""+getTransitionName()+" "+arr[0]);
                Log.d("-----",""+arr[1]);

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

    public CompanyImageHolder(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        setImageView(context);
    }

    public CompanyImageHolder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public CompanyImageHolder(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
    }
}
