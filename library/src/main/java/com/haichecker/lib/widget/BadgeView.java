package com.haichecker.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-6 13:30
 */

public class BadgeView extends ViewGroup {

    private View view;
    private RelativeLayout dotRoot;
    private TextView dotNumber;
    private int w, h;


    public BadgeView(Context context) {
        super(context);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dotRoot = new RelativeLayout(getContext());
        dotNumber = new TextView(getContext());
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
//        if (getChildCount() != 2)
//            throw new IllegalArgumentException("There is only one　child");

        view = getChildAt(0);
        w = view.getMeasuredWidth();
        h = view.getMeasuredHeight();
    }
}
