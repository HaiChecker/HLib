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
import android.widget.Scroller;

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
    private int start = 0;
    private int type = 0;
    private int sweepAngle = 90;
    private Scroller mScroller;
    private int ok_1_startX;
    private int ok_1_startY;
    private int ok_1_endX;
    private int ok_1_endY;

    public void setType(int type) {
        this.type = type;
        ok_1_startX = 16 * 2;
        ok_1_startY = (int) (mRectF.bottom / 2);
        mScroller.startScroll(ok_1_startX, ok_1_startY, (int) mRectF.right - 10, (int) mRectF.bottom, 500);
    }

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
        mScroller = new Scroller(getContext());
        mRectF = new RectF();
        handler = new Handler();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ok_1_endX = mScroller.getCurrX();
            ok_1_endY = mScroller.getCurrY();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.left = 10;
        mRectF.top = 10;
        mRectF.right = w - 10;
        mRectF.bottom = h - 10;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#bfbfbf"));
        canvas.drawArc(mRectF, 0, 360, false, mPaint);
        mPaint.setColor(Color.parseColor("#fbfbfb"));
        canvas.drawArc(mRectF, start, sweepAngle, false, mPaint);
        if (type == 1) {
            mPaint.setColor(Color.parseColor("#000000"));
            canvas.drawLine(ok_1_startX, ok_1_startY, ok_1_endX, ok_1_endY, mPaint);
        }
        handler.postDelayed(invalidateRunnable, 30);
    }

    private Runnable invalidateRunnable = new Runnable() {
        @Override
        public void run() {
            if (type == 0) {
                start += 10;
                if (start >= 360)
                    start = 0;
            } else if (type == 1) {
                if (start == 50) {
                    sweepAngle += 5;
                }
            }
            invalidate();
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        // 在wrap_content的情况下默认长度为200dp
        int minSize = DensityUtil.dip_px(100, getContext().getResources().getDisplayMetrics().density);
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
