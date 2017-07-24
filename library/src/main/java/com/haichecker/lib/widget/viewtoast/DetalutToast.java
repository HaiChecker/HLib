package com.haichecker.lib.widget.viewtoast;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haichecker.lib.R;

import static android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID;
import static com.haichecker.lib.widget.viewtoast.Style.STYLE_PROGRESS_BAR;
import static com.haichecker.lib.widget.viewtoast.Style.STYLE_PROGRESS_CIR;

/**
 * createTime 2017/7/19 19:00
 * <p>
 * devUser 石文平
 * <p>
 * classDetail
 */
public class DetalutToast extends ToastCallBack {
    private Toasts toasts;

    /**
     * 显示的进度条
     */
    private ProgressBar mProgressBarH, mProgressBarO;
    /**
     * 显示的当前进度
     */
    private int progress;
    /**
     * 显示的总进度
     */
    private int max;
    /**
     * 文本对象
     */
    private TextView mTextView;
    /**
     * 显示类型
     */
    private Style style;
    /**
     * 文本内容
     */
    private String text;
    /**
     * 文本颜色
     */
    private int textColor = Color.WHITE;
    /**
     * 背景颜色
     */
    private int color = 0x000000;
    /**
     * 上下文
     */
    private Context mContext;

    private ObjectAnimator colorAnimator;

    public DetalutToast(ViewGroup root) {
        toasts = Toasts.create(root, this);
    }





    /**
     * 设置进度总大小
     *
     * @param max
     */
    public DetalutToast setProgressMax(int max) {
        this.max = max;
        if (mProgressBarO != null) {
            mProgressBarO.setMax(max);
        }

        if (mProgressBarH != null) {
            mProgressBarH.setMax(max);
        }
        return this;
    }

    /**
     * 显示
     *
     * @param delay 如果等于0则不关闭
     */
    public void show(long delay) {
        pShow();
        hide(delay);
    }

    private void pShow() {
        if (toasts.getRootView() != null) {
            if (toasts.getRootView() instanceof LinearLayout) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //TODO 需要添加参数
                toasts.getRootView().addView(getCurrView(), lp);
            } else if (toasts.getRootView() instanceof RelativeLayout) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                toasts.getRootView().addView(getCurrView(), lp);
            } else if (toasts.getRootView() instanceof FrameLayout) {
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.gravity = Gravity.CENTER;
                toasts.getRootView().addView(getCurrView(), lp);
            } else if (toasts.getRootView() instanceof ConstraintLayout) {
                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.bottomToBottom = PARENT_ID;
                lp.topToTop = PARENT_ID;
                lp.leftToLeft = PARENT_ID;
                lp.rightToRight = PARENT_ID;
                toasts.getRootView().addView(getCurrView(), lp);
            } else {
                throw new ClassCastException(String.format("%s Layout is Error", toasts.getRootView().getClass().getSimpleName()));
            }
            getCurrView().bringToFront();
        } else {
            try {
                ContentFrameLayout frameLayout = (ContentFrameLayout) ((AppCompatActivity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.gravity = Gravity.CENTER;
                frameLayout.addView(getCurrView(), lp);
            } catch (Exception e) {
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getCurrView().setAlpha(0f);
        }
        getCurrView().setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(getCurrView(), "alpha", 0f, 1f);
            colorAnimator = ObjectAnimator.ofInt(getCurrView(), "backgroundColor", 0x00000000, 0x36000000);
            colorAnimator.setEvaluator(new ArgbEvaluator());
            //动画组合
            AnimatorSet set = new AnimatorSet();
            set.play(colorAnimator);
            set.play(alphaAnimator);
            set.setDuration(300);
            set.start();
        }
    }


    /**
     * 可延迟关闭，可以有回调
     *
     * @param delay           延迟时间，单位(毫秒)
     * @param dismissListener 回调事件
     */
    public void hide(long delay, final DialogInterface.OnDismissListener dismissListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toasts.dismiss();
                if (dismissListener != null) {
                    dismissListener.onDismiss(toasts);
                }
            }
        }, delay);
    }

    /**
     * 可延迟关闭
     *
     * @param delay 延迟时间，单位(毫秒)
     */
    public void hide(long delay) {
        hide(delay, null);
    }

    /**
     * 设置当前大小
     *
     * @param progress
     */
    public DetalutToast setProgress(int progress) {
        this.progress = progress;
        if (mProgressBarH != null) {
            mProgressBarH.setProgress(progress);
        }
        if (mProgressBarO != null) {
            mProgressBarO.setProgress(progress);
        }
        return this;
    }

    /**
     * 设在文本颜色
     *
     * @param color
     */
    public DetalutToast setTextColor(int color) {
        textColor = color;
        if (mTextView != null) {
            mTextView.setTextColor(color);
        }
        return this;
    }


    /**
     * 设置文本
     */
    public DetalutToast setText(String text) {
        this.text = text;
        if (mTextView != null)
            mTextView.setText(text);
        return this;
    }

    /**
     * 设置文本
     */
    public DetalutToast setText(int resId) {
        this.text = mContext.getString(resId);
        if (mTextView != null)
            mTextView.setText(text);
        return this;
    }


    @Override
    void onShow(ViewGroup parent, boolean isShowParent, long time, DialogInterface.OnShowListener onShowListener) {

    }

    @Override
    void onHide(long time, DialogInterface.OnDismissListener onDismissListener) {

    }

    @Override
    void onCreate(View mView) {
        mProgressBarH = (ProgressBar) mView.findViewById(R.id.progressh);
        mProgressBarH.setMax(max);
        mProgressBarH.setProgress(progress);
        mProgressBarH.setVisibility(View.GONE);
        mProgressBarO = (ProgressBar) mView.findViewById(R.id.progressc);
        mProgressBarO.setMax(max);
        mProgressBarO.setProgress(progress);
        mProgressBarO.setVisibility(View.GONE);
        mTextView = (TextView) mView.findViewById(R.id.text);
        mTextView.setText(text);
        mTextView.setTextColor(textColor);
    }

    @Override
    View getView() {
        LayoutInflater layoutInflater = LayoutInflater.from(toasts.getRootView().getContext());
        return layoutInflater.inflate(R.layout.defalut_toast_view, null, false);
    }


    @Override
    void styleChange(Style style) {
        switch (style) {
            case STYLE_PROGRESS_BAR:
            case STYLE_PROGRESS_CIR:
                if (mProgressBarH != null && style == STYLE_PROGRESS_BAR)
                    mProgressBarH.setVisibility(View.VISIBLE);

                if (mProgressBarO != null && style == STYLE_PROGRESS_CIR)
                    mProgressBarO.setVisibility(View.VISIBLE);
                break;
            case STYLE_TEXT:
                if (mProgressBarH != null)
                    mProgressBarH.setVisibility(View.GONE);

                if (mProgressBarO != null)
                    mProgressBarO.setVisibility(View.GONE);
                break;
        }
    }
}
