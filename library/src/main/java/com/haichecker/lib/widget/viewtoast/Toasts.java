package com.haichecker.lib.widget.viewtoast;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.common.base.Preconditions;
import com.haichecker.lib.R;

import static android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID;

/**
 * createTime 2017/7/19 8:12
 * <p>
 * devUser 石文平
 * <p>
 * classDetail  新的Toast 代替{@link ViewToast}
 */
public class Toasts implements DialogInterface {

    /**
     * Toast的各种回调，方便自定义
     */
    private ToastCallBack toastCallBack;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 依附在此View上
     */
    private ViewGroup rootView;

    /**
     * Toast根View
     */
    private View mView;

    /**
     * 当前状态
     */
    private Style style;

    //是否显示中
    private boolean isShowing = false;
    //背景是否可以点击
    private boolean isBackgound = false;

    //关闭动画
    ObjectAnimator objectAnimatorDis;
    //颜色渐变和alpha动画
    ObjectAnimator alphaAnimator;
    ObjectAnimator colorAnimator;

    public Context getContext() {
        return mContext;
    }

    public ViewGroup getRootView() {
        return rootView;
    }

    /**
     * 依附在{@link android.view.Window}上
     *
     * @param mContext 上下文
     * @return 返回  {@link Toasts}对象
     */


    public static Toasts create(Context mContext, ToastCallBack t) {
        return new Toasts(mContext, t);
    }

    /**
     * 依附在选择的View上
     *
     * @param rootView 依附在此View中
     * @return 返回  {@link Toasts}对象
     */
    public static Toasts create(ViewGroup rootView, ToastCallBack t) {
        return new Toasts(rootView, t);
    }

    /**
     * 通过Context创建，它将依附在{@link android.view.Window}上
     *
     * @param mContext      上下文
     * @param toastCallBack 各种事件的回调，方便自定义
     */
    private Toasts(Context mContext, ToastCallBack toastCallBack) {
        this.mContext = mContext;
        this.toastCallBack = Preconditions.checkNotNull(toastCallBack);
        init();
    }

    /**
     * 通过ViewGroup创建，它将依附在 {@link ViewGroup} 上
     *
     * @param rootView      依附的控件
     * @param toastCallBack 各种事件的回调，方便自定义
     */
    private Toasts(ViewGroup rootView, ToastCallBack toastCallBack) {
        this.rootView = Preconditions.checkNotNull(rootView);
        this.toastCallBack = Preconditions.checkNotNull(toastCallBack);
        this.mContext = this.rootView.getContext();
        init();
    }

    private void init() {
        toastCallBack.setToasts(this);
        final LayoutInflater layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = (FrameLayout) layoutInflater.inflate(R.layout.toast, null);
        FrameLayout frameLayout = (FrameLayout) mView.findViewById(R.id.root_layout);
        View tc = toastCallBack.getView();
        frameLayout.addView(tc);
        toastCallBack.setCurrView(tc);
        toastCallBack.onCreate(tc);
    }


    /**
     * 显示
     */
    public Toasts show(long time, OnShowListener listener) {
        pShow(time, listener);
        toastCallBack.onShow(rootView, rootView != null, time, listener);
        return this;
    }

    private void pShow(long time, final OnShowListener listener) {
        if (isShowing)
            return;
        if (rootView != null) {
            if (rootView instanceof LinearLayout) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //TODO 需要添加参数
                rootView.addView(mView, lp);
            } else if (rootView instanceof RelativeLayout) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                rootView.addView(mView, lp);
            } else if (rootView instanceof FrameLayout) {
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.gravity = Gravity.CENTER;
                rootView.addView(mView, lp);
            } else if (rootView instanceof ConstraintLayout) {
                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.bottomToBottom = PARENT_ID;
                lp.topToTop = PARENT_ID;
                lp.leftToLeft = PARENT_ID;
                lp.rightToRight = PARENT_ID;
                rootView.addView(mView, lp);
            } else {
                throw new ClassCastException(String.format("%s Layout is Error", rootView.getClass().getSimpleName()));
            }
            mView.bringToFront();
        } else {
            try {
                ContentFrameLayout frameLayout = (ContentFrameLayout) ((AppCompatActivity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.gravity = Gravity.CENTER;
                frameLayout.addView(mView, lp);
            } catch (Exception e) {
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mView.setAlpha(0f);
        }
        mView.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mView, "alpha", 0f, 1f);
            colorAnimator = ObjectAnimator.ofInt(mView, "backgroundColor", 0x00000000, 0x36000000);
            colorAnimator.setEvaluator(new ArgbEvaluator());
            //动画组合
            AnimatorSet set = new AnimatorSet();
            set.play(colorAnimator);
            set.play(alphaAnimator);
            set.setDuration(300);
            set.start();
        }
        isShowing = true;
        if (listener != null) {
            listener.onShow(Toasts.this);
        }
        if (time > 0)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hide(0,null);
                }
            }, time);
        }
    }


    /**
     * 隐藏关闭
     */
    public Toasts hide(long time, OnDismissListener dismissListener) {
        pHide(time, dismissListener);
        toastCallBack.onHide(time, dismissListener);
        return this;
    }

    private void pHide(long time, final OnDismissListener dismissListener) {
       if (time > 0)
       {
           new Handler()
                   .postDelayed(new Runnable() {
                       @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                       @Override
                       public void run() {
                           h(dismissListener);
                       }
                   }, time);
       }else {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
               h(dismissListener);
           }
       }
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void h(final OnDismissListener dismissListener){
        //动画组合对象
        AnimatorSet set = new AnimatorSet();
        //判断是否显示，没有显示则返回
        if (!isShowing)
            return;
        //动画对象
        alphaAnimator = ObjectAnimator.ofFloat(mView, "alpha", 1.0f, 0f);
        colorAnimator = ObjectAnimator.ofInt(mView, "backgroundColor", 0x36000000, 0x00000000);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        set.play(alphaAnimator);
        set.play(colorAnimator);
        set.setDuration(500);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //首先设置它的显示
                mView.setVisibility(View.GONE);
                //如果选择的是加载到父控件上，直接删除
                if (rootView != null) {
                    rootView.removeView(mView);
                } else {
                    try {
                        ContentFrameLayout frameLayout = (ContentFrameLayout) ((AppCompatActivity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
                        frameLayout.removeView(mView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isShowing = false;
                if (dismissListener != null) {
                    dismissListener.onDismiss(Toasts.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();
    }
    public void setStyle(Style style) {
        this.style = style;
        toastCallBack.styleChange(style);
    }

    @Override
    public void cancel() {
        hide(0, null);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void dismiss() {
        //动画组合对象
        AnimatorSet set = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            set = new AnimatorSet();
        }
        //判断是否显示，没有显示则返回
        if (!isShowing)
            return;
        //动画对象
        alphaAnimator = ObjectAnimator.ofFloat(mView, "alpha", 1.0f, 0f);
        colorAnimator = ObjectAnimator.ofInt(mView, "backgroundColor", 0x36000000, 0x00000000);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        set.play(alphaAnimator);
        set.play(colorAnimator);
        set.setDuration(500);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //首先设置它的显示
                mView.setVisibility(View.GONE);
                //如果选择的是加载到父控件上，直接删除
                if (rootView != null) {
                    rootView.removeView(mView);
                } else {
                    try {
                        ContentFrameLayout frameLayout = (ContentFrameLayout) ((AppCompatActivity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
                        frameLayout.removeView(mView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isShowing = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();
    }
}
