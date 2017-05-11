package com.haichecker.lib.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-4-18 16:51
 */

public class ViewScroll<T, V extends View> extends FrameLayout {

    private List<T> data;

    private int index = 0;

    private OnCallBack<T, V> callBack;

    //多久滚动
    private long delay = 1000;
    //滚动时间
    private long animationDelay = 1000;


    private int height;

    private int v1Top, v2Top;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        v1Top = 0;
        v2Top = v1Top + h;
        height = h;
        requestLayout();
    }

    public void setAnimationDelay(long animationDelay) {
        this.animationDelay = animationDelay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    //用于自动更新
    private Handler handler;

//    private Subscription subscription;

    public void start() {
        isStop = false;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animation();
            }
        }, 0);
    }

    boolean isStop = false;

    public void stop() {
        isStop = true;
    }

    public void animation() {
        if (isStop)
            return;
        isStop = false;
        ObjectAnimator animator = ObjectAnimator.ofInt(new ViewHelper(), "Y", 0, -height);
        animator.setDuration(animationDelay);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (index >= getData().size())
                    index = 0;
                if (v1IsTop) {
                    callBack.onLoad(v1, getData().get(index), index);
                } else {
                    callBack.onLoad(v2, getData().get(index), index);
                }
                index++;
                v1IsTop = !v1IsTop;
            }

            @Override
            public void onAnimationEnd(Animator animator) {


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation();
                    }
                }, delay);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    public void setCallBack(OnCallBack<T, V> callBack) {
        this.callBack = callBack;
    }

    private void getView() {
        removeAllViews();
        v2 = callBack.getView();
        v1 = callBack.getView();
        v1.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        v2.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(v1);
        addView(v2);
        v1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(v1, index == 0 ? 0 : index - 1);
            }
        });
        v2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(v2, index == 0 ? 0 : index - 1);
            }
        });
//        callBack.onLoad(v1, getData().get(index), index);

//        callBack.onLoad(v2, getData().get(index + 1), index + 1);

        requestLayout();
    }

    public List<T> getData() {
        return data;
    }

    private class ViewHelper {

        public void setY(int y) {
            if (v1IsTop) {
                v1Top = y;
            } else {
                v2Top = y;
            }
            requestLayout();
        }

        public int getY() {
            return v1IsTop ? v1Top : v2Top;
        }

    }

    public void setNewData(List<T> data) {
        stop();
        this.data = data;
        getView();
        start();
    }

    public ViewScroll(Context context) {
        super(context);
        handler = new Handler();
    }

    public ViewScroll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        handler = new Handler();
    }

    public ViewScroll(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private V v1, v2;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (callBack == null) {
            TextView t1 = new TextView(getContext());
            t1.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            t1.setText("Item 1");
            addView(t1);
            TextView t2 = new TextView(getContext());
            t1.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            t1.setText("Item 2");
            addView(t2);
        }
    }

    //判断第一个View是否在顶部
    public boolean v1IsTop = true;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (v1 != null && v2 != null) {
            int currBottom;
            if (v1IsTop) {
                //当前Y轴
                currBottom = height + v1Top;
                v1.layout(l, v1Top, r, currBottom);
                int v2B = height + currBottom;
                v2Top = currBottom;
                v2.layout(l, v2Top, r, v2B);
            } else {
                //当前Y轴
                //当前Y轴
                currBottom = height + v2Top;
                v2.layout(l, v2Top, r, currBottom);
                int v1B = height + currBottom;
                v1Top = currBottom;
                v1.layout(l, v1Top, r, v1B);
            }
        }
    }

    public static interface OnCallBack<T, V extends View> {

        void onLoad(V view, T t, int index);

        V getView();

        void onClick(V v, int p);

    }

}
