package com.haichecker.simple;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.haichecker.lib.utils.DensityUtil;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-4-1 10:53
 */

public class HuaView extends ViewGroup {
    private View titleView;
    private View typeView;
    private View contentView;
    private int cotentMinHeight = DensityUtil.dip_px(50, getResources().getDisplayMetrics().density);

    private int titleView_height;
    private int titleView_top = -1;
    int title_bottom;
    private int type_height = 0;
    private float downY = 0;
    private int contentT = getResources().getDisplayMetrics().heightPixels / 2;
    private int contentTopCount = contentT;

    private int typeT = contentT, typeB;
    private ViewDragHelper mDragger;

    private boolean isType = false;

    public void setTitle_bottom(int title_bottom) {
        this.title_bottom = title_bottom;
    }

    public int getTitle_bottom() {
        return title_bottom;
    }

    public HuaView(Context context) {
        super(context);
    }

    private void smoothToTop() {
        if (mDragger.smoothSlideViewTo(contentView, getPaddingLeft(), titleView_height + type_height + 1)) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            Log.d("动画啊错误", "动画错误" + titleView_height + "+" + type_height);
        }
    }

    private void smoothTo() {
        if (mDragger.smoothSlideViewTo(titleView, getPaddingLeft(), 0) && mDragger.smoothSlideViewTo(typeView, getPaddingLeft(), titleView_height)) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            Log.d("动画啊错误", "动画错误");
        }
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void show() {
        smoothToTop();
        smoothTo();
    }

    public void hide() {
        if (mDragger.smoothSlideViewTo(contentView, getPaddingLeft(), getMeasuredHeight() / 2)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public HuaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child.equals(contentView);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return 0;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild.getTop() <= getResources().getDisplayMetrics().heightPixels / 3) {
                    show();
                } else {
                    hide();
                }
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                contentTopCount += dy;

                if (contentTopCount < 0)
                    contentTopCount = 0;

                if (contentTopCount <= type_height + titleView_height) {
                    top = type_height + titleView_height;
                    if (titleView_top == -1) {
                        titleView_top = -titleView_height;
                    }
                    if (getTitle_bottom() > titleView_height || titleView_top > 0) {
                        if (titleView_top > 0) {
                            titleView_top = 0;
                        }
                        if (isTopMove(dy)) {
                            setTitle_bottom(titleView_height);
                        } else {
                            setTitle_bottom(titleView_height + (~dy));
                            if ((titleView_top + (~dy) <= 0)) {
                                titleView_top += (~dy);
                            }
                        }
                    } else {
                        if (getTitle_bottom() < 0) {
                            setTitle_bottom(0);
                            titleView_top = -titleView_height;
                        } else {
                            setTitle_bottom(getTitle_bottom() + (~dy));
                            if ((titleView_top + (~dy) <= 0)) {
                                titleView_top += (~dy);
                            }
                        }
                    }
                    if (contentTopCount <= type_height) {
                        top = type_height + titleView_height;
                        if (isTopMove(dy)) {
                            if (typeT >= titleView_height && !isType) {
                                typeT += dy;
                                typeB = typeT + type_height;
                            } else {
                                typeT = titleView_height;
                                typeB = top;
                                isType = true;
                            }
                        } else {
                            isType = false;
                            typeT += dy;
                            typeB = typeT + type_height;
                        }
                    }

                } else {
                    typeT = top;
                }

//                if (top <= type_height + titleView_height) {
//                    top = (type_height + titleView_height);
//                    if (titleView.getBottom() >= titleView_height) {
//                        title_b = titleView_height;
//                        if (typeT <= titleView.getBottom()) {
//                            typeT = titleView.getBottom();
//                        } else {
//                            typeT += dy;
//                            isType = true;
//                        }
//                    } else {
//                        th_layout += ~dy;
//                        title_b += ~dy;
//                    }
//                } else {
//                    if (typeT != contentView.getTop() && isType) {
//                        typeT += dy;
//                        top = (type_height + titleView_height);
//                    } else {
//                        typeT = top;
//                        isType = false;
//                    }
//                }
//
                contentT = top;

                requestLayout();
            }
        });

    }

    public boolean isTopMove(float y) {
        return y < 0;
    }

    public HuaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean is, int l, int t, int r, int b) {
        titleView.layout(l, titleView_top, r, title_bottom);
        contentView.layout(l, contentT, r, b);
        typeView.layout(l, typeT, r, typeB);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeigth = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeigth);
        // TODO Auto-generated method stub
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            int widthSpec = 0;
            int heightSpec = 0;
            LayoutParams params = v.getLayoutParams();
            if (params.width > 0) {
                widthSpec = MeasureSpec.makeMeasureSpec(params.width, MeasureSpec.EXACTLY);
            } else if (params.width == -1) {
                widthSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY);
            } else if (params.width == -2) {
                widthSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.AT_MOST);
            }

            if (params.height > 0) {
                heightSpec = MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
            } else if (params.height == -1) {
                heightSpec = MeasureSpec.makeMeasureSpec(measureHeigth, MeasureSpec.EXACTLY);
            } else if (params.height == -2) {
                heightSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.AT_MOST);
            }
            v.measure(widthSpec, heightSpec);

        }
//        titleView.measure(maxWidth, maxHeight);
        final int h = titleView.getMeasuredHeight();
        titleView_height = h;
        type_height = typeView.getMeasuredHeight();
//        contentView.measure(maxWidth, maxHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleView = getChildAt(0);
        typeView = getChildAt(1);
        contentView = getChildAt(2);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }
}
