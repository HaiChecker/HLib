package com.haichecker.lib.widget.viewtoast;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.haichecker.lib.utils.DensityUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-24 11:39
 */

public class StateArc extends View {
    private Paint mPaint;
    private RectF mRectF;
    private Handler handler;

    public StateArc(Context context) {
        super(context);
    }

    public StateArc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StateArc(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(15);
        mPaint.setStyle(Paint.Style.STROKE);

        mRectF = new RectF();
        mRectF.left = 5;
        mRectF.top = 5;
        mRectF.right = getMeasuredWidth() - 5;
        mRectF.bottom = getMeasuredHeight() - 5;

        handler = new Handler();
//        Observable.interval(500, TimeUnit.MICROSECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Long>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        request(100);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        invalidate();
//                        request(100);
//                    }
//                });
    }


    private int start = 0;
    private int end = 0;
    private int curr = 0;
    private boolean isOk = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 50);
//        curr += 5;
        if (curr >= 360) {
            curr = 5;
        }
        if (isOk) {
            if (end >= 320) {
                end += 5;
                curr += 10;
                isOk = true;
            } else {
                end += 10;
                curr += 5;
                isOk = false;
            }
            if (end >= 360) {
                end = start;
            }
        } else {
            if (start >= 320) {
                end += 5;
                curr += 10;
                isOk = true;
            } else {
                end += 10;
                curr += 5;
                isOk = false;
            }
            if (start >= 360) {
                curr = end;
            }

        }


        start = curr;


        mRectF.left = 5;
        mRectF.top = 5;
        mRectF.right = 200 - 5;
        mRectF.bottom = 200 - 5;
        canvas.drawArc(mRectF, start, end, false, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        // 在wrap_content的情况下默认长度为200dp
        int minSize = DensityUtil.dip_px(200, getContext().getResources().getDisplayMetrics().density);
        // wrap_content的specMode是AT_MOST模式，这种情况下宽/高等同于specSize
        // 查表得这种情况下specSize等同于parentSize，也就是父容器当前剩余的大小
        // 在wrap_content的情况下如果特殊处理，效果等同martch_parent
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, minSize);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, minSize);
        }
    }
}
