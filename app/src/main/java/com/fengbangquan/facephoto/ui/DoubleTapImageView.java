package com.fengbangquan.facephoto.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Feng Bangquan on 17-12-28
 */
public class DoubleTapImageView extends ImageView  {
    long startTime;
    long endTime;
    public DoubleTapImageView(Context context) {
        super(context);
    }

    public DoubleTapImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DoubleTapImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DoubleTapImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
