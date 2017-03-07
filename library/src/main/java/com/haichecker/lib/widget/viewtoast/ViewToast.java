package com.haichecker.lib.widget.viewtoast;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haichecker.lib.R;


/**
 * Created by root on 16-11-3.
 */

public class ViewToast<T extends ViewGroup> implements DialogInterface {

    private static ViewToast toast;

    private FrameLayout mView = null;

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

    /**
     * 父Ｖｉｅｗ
     */
    private T parent;

    /**
     * 当前的Ｘ和Ｙ轴
     */
    private int x, y;

    /**
     * 是否显示在父Ｖｉｅｗ上
     */
    private boolean isParent = false;

    /**
     * 是否自动隐藏
     */
    private boolean isAutoHide = true;
    /**
     * 显示时长
     */
    public static final int LONG = 0x1;

    private boolean isShowing = false;

    public Context getContext() {
        return mContext;
    }

    public ViewToast(Context mContext) {
        this.mContext = mContext;
        init();
    }

    public static ViewToast create(Context mContext) {
        toast = new ViewToast(mContext);
        return toast;
    }

    /**
     * 创建
     *
     * @param mContext
     * @param style
     * @return
     */
    public static ViewToast create(Context mContext, Style style) {
        ViewToast toast = create(mContext);
        toast.setStyle(style);
        return toast;
    }

    /**
     * 创建
     *
     * @param mContext
     * @param style
     * @param text
     * @return
     */
    public static ViewToast create(Context mContext, Style style, String text) {
        ViewToast toast = create(mContext, style);
        toast.setText(text);
        return toast;
    }

    /**
     * 创建
     *
     * @param mContext
     * @param style
     * @param text     文本
     * @return
     */
    public static ViewToast create(Context mContext, Style style, int text) {
        ViewToast toast = create(mContext, style);
        toast.setText(text);
        return toast;
    }

    public static <V extends ViewGroup> ViewToast createDefalut(Context mContext, V view) {
        return ViewToast.create(mContext, Style.STYLE_PROGRESS_CIR)
                .setParent(true)
                .setParent(view)
                .setGravity(null)
                .setColor(Color.BLACK)
                .setTextColor(Color.WHITE);
    }

    public static <V extends ViewGroup> ViewToast createDefalut(Context mContext) {
        return ViewToast.create(mContext, Style.STYLE_TEXT)
                .setParent(false)
                .setGravity(null)
                .setColor(Color.BLACK)
                .setTextColor(Color.WHITE).hideProgress();

    }

    /**
     * 设置是否自动关闭
     */
    public void setLong() {

    }

    float h = 0;
    ObjectAnimator objectAnimator;

    /**
     * 显示
     */
    public void show() {
        if (isShowing)
            return;
        isShowing = true;
        mView.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        switch (style) {
            case STYLE_PROGRESS_BAR:
                mProgressBarH.setVisibility(View.VISIBLE);
                mProgressBarO.setVisibility(View.GONE);
                break;
            case STYLE_PROGRESS_CIR:
                mProgressBarH.setVisibility(View.GONE);
                mProgressBarO.setVisibility(View.VISIBLE);
                break;
            case STYLE_TEXT:
                mProgressBarH.setVisibility(View.GONE);
                mProgressBarO.setVisibility(View.GONE);
                break;
        }
        if (isParent) {
            if (parent instanceof LinearLayout) {
                parent.addView(mView);
            } else if (parent instanceof RelativeLayout) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                parent.addView(mView, lp);
            } else if (parent instanceof FrameLayout) {
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.gravity = Gravity.CENTER;
                parent.addView(mView, lp);
            } else {
                throw new ClassCastException(String.format("%s Layout is Error", parent.getClass().getSimpleName()));
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
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ObjectAnimator.ofArgb(mView, "backgroundColor", 0x00000000, 0x80000000).setDuration(300).start();
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator.ofFloat(mView, "alpha", 0f, 1f).setDuration(300).start();
        }
//        ObjectAnimator.ofFloat(mTextView, "alpha", 0.7f, 1).setDuration(100).start();
//        mView.findViewById(R.id.root_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                float mLayerWidth = (float) mView.findViewById(R.id.root_layout).getWidth();
//                float mTextWidth = (float) mView.findViewById(R.id.root_layout).getHeight();
//                if (!objectAnimator.isRunning()){
//                    Log.d("Log", "mLayerWidthw =  " + mLayerWidth + "h = " + mTextWidth);
//                }
//                if (mLayerWidth != 0 && mTextWidth != 0 && mTextWidth != h && mView.getVisibility() == View.VISIBLE && (!objectAnimator.isRunning())) {
//                    objectAnimator = ObjectAnimator.ofInt(new LayoutViewHelper(mView.findViewById(R.id.root_layout)), "height", (int) h, (int) mTextWidth);
//                    objectAnimator.setDuration(300).start();
//                    h = mTextWidth;
//                }
//                if (mView.getVisibility() == View.GONE)
//                    mView.setVisibility(View.VISIBLE);
//            }
//        });
    }

    ObjectAnimator objectAnimatorDis;

    /**
     * 关闭
     */
    public void dissmiss() {
        if (!isShowing)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (objectAnimatorDis != null && objectAnimatorDis.isRunning())
                return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            objectAnimatorDis = ObjectAnimator.ofFloat(mView, "alpha", 1.0f, 0f).setDuration(300);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            objectAnimatorDis.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mView.setVisibility(View.GONE);
                    if (isParent) {
                        parent.removeView(mView);
                    } else {
                        try {
                            ContentFrameLayout frameLayout = (ContentFrameLayout) ((AppCompatActivity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
                            frameLayout.removeView(mView);
                        } catch (Exception e) {
                        }
                    }
                    isShowing = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            objectAnimatorDis.start();
        }
    }


    public void dissmiss(int d) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dissmiss();
            }
        }, d);
    }


    public void dissmiss(int num, final OnDismissListener dismissListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dissmiss();
                dismissListener.onDismiss(ViewToast.this);
            }
        }, num);
    }


    private WindowManager.LayoutParams getWindowManagerParams() {
        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        ;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.windowAnimations = android.R.style.Animation_Toast;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        layoutParams.alpha = 1.0f;
        layoutParams.gravity = Gravity.CENTER;
        return layoutParams;
    }


    /**
     * 设置位置，必须在setParent 之后调用
     *
     * @param gravity
     * @return
     */
    public ViewToast setGravity(Gravity gravity) {
        if (isParent) {
//            x = parent.getWidth() / 2 - (mView.getWidth() / 2);
//            y = parent.getHeight() / 2 - (mView.getHeight() / 2);
            x = 200;
            y = 200;
        } else {
            x = mContext.getResources().getDisplayMetrics().widthPixels / 2 - (mView.getWidth() / 2);
            y = mContext.getResources().getDisplayMetrics().heightPixels / 2 - (mView.getHeight() / 2);
        }
        return this;
    }

    /**
     * 初始化
     */
    private void init() {

        final LayoutInflater layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = (FrameLayout) layoutInflater.inflate(R.layout.toast, null);

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

    /**
     * 设置进度总大小
     *
     * @param max
     */
    public ViewToast<T> setProgressMax(int max) {
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
     * 设置当前大小
     *
     * @param progress
     */
    public ViewToast<T> setProgress(int progress) {
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
     * 设置背景颜色
     *
     * @param color
     */
    public ViewToast setColor(int color) {
        this.color = color;
//        mView.setBackgroundTintList(new ColorStateList());
//        mView.seti
        return this;
    }

    /**
     * 设在文本颜色
     *
     * @param color
     */
    public ViewToast setTextColor(int color) {
        textColor = color;
        if (mTextView != null) {
            mTextView.setTextColor(color);
        }
        return this;
    }

    /**
     * 设置是否显示为全局或则局部
     *
     * @param parent True 局部
     */
    public ViewToast<T> setParent(boolean parent) {
        isParent = parent;
        return this;
    }

    /**
     * 设置父Ｖｉｅｗ
     *
     * @param parent
     */
    public ViewToast<T> setParent(ViewGroup parent) {
        this.parent = (T) parent;
        return this;
    }

    /**
     * 设置类别
     *
     * @param style
     */
    public ViewToast<T> setStyle(Style style) {
        this.style = style;
        return this;
    }

    /**
     * 设置文本
     */
    public ViewToast<T> setText(String text) {
        this.text = text;
        if (mTextView != null)
            mTextView.setText(text);
        return this;
    }

    /**
     * 设置文本
     */
    public ViewToast<T> setText(int resId) {
        this.text = mContext.getString(resId);
        if (mTextView != null)
            mTextView.setText(text);
        return this;
    }

    public void setAlpha(float f) {
        if (mView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mView.setAlpha(f);
            }
        }
    }

    public float getAlpha() {
        if (mView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return mView.getAlpha();
            }
        }
        return 0f;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public ViewToast<T> hideProgress() {
        if (mProgressBarH != null)
            mProgressBarH.setVisibility(View.GONE);

        if (mProgressBarO != null)
            mProgressBarO.setVisibility(View.GONE);
        return this;
    }

    public ViewToast<T> showProgress() {
        if (mProgressBarH != null)
            mProgressBarH.setVisibility(View.VISIBLE);

        if (mProgressBarO != null)
            mProgressBarO.setVisibility(View.VISIBLE);
        return this;
    }

    public ViewToast<T> hideText() {
        if (mTextView != null) {
            mTextView.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    public void cancel() {
        dismiss();
    }

    @Override
    public void dismiss() {
        dissmiss();
    }


    public class LayoutViewHelper {
        public View view;

        public LayoutViewHelper(View view) {
            this.view = view;
        }


        public void setHeight(int h) {
            Log.d("Log", h + "");
            ViewGroup.LayoutParams v = view.getLayoutParams();
            v.height = h;
            view.setLayoutParams(v);
        }

        public int getHeight() {
            return view.getLayoutParams().height;
        }

    }

}
