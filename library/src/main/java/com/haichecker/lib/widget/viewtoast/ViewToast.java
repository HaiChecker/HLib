package com.haichecker.lib.widget.viewtoast;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
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

import static android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID;
import static com.haichecker.lib.widget.viewtoast.Style.STYLE_PROGRESS_BAR;
import static com.haichecker.lib.widget.viewtoast.Style.STYLE_PROGRESS_CIR;


/**
 * <h1>标准提示类，个人封装</h1>
 * </br>
 * 实现Dialog的{@link DialogInterface} 接口
 * </br>
 * 并重写了{@link DialogInterface#dismiss()} 和 {@link DialogInterface#cancel()}
 * </br>
 * <p>
 * <p>详情了解请访问<a href="http://www.shiwenping.com/">我的个人网站</a></p>
 *
 * @param <T> 它的父控件，必须继承{@link ViewGroup}
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
    //显示动画
    ObjectAnimator objectAnimator;
    //关闭动画
    ObjectAnimator objectAnimatorDis;
    //颜色渐变和alpha动画
    ObjectAnimator alphaAnimator;
    ObjectAnimator colorAnimator;
    //当前状态
    private State currState = State.NONE;

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
        return ViewToast.create(mContext, STYLE_PROGRESS_CIR)
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

    private void changeStyle() {
        mProgressBarH.setVisibility(style == STYLE_PROGRESS_BAR ? View.VISIBLE : View.GONE);
        mProgressBarO.setVisibility(style == STYLE_PROGRESS_CIR ? View.VISIBLE : View.GONE);
    }

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
        //修改状态
        changeStyle();
        if (isParent) {
            if (parent instanceof LinearLayout) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //TODO 需要添加参数
                parent.addView(mView, lp);
            } else if (parent instanceof RelativeLayout) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                parent.addView(mView, lp);
            } else if (parent instanceof FrameLayout) {
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.gravity = Gravity.CENTER;
                parent.addView(mView, lp);
            } else if (parent instanceof ConstraintLayout) {
                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.bottomToBottom = PARENT_ID;
                lp.topToTop = PARENT_ID;
                lp.leftToLeft = PARENT_ID;
                lp.rightToRight = PARENT_ID;
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
    }

    /**
     * 无延迟关闭
     */
    public void hide() {
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
                if (isParent) {
                    parent.removeView(mView);
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

    /**
     * 关闭这个Toast，可以延迟关闭
     *
     * @param delay 延迟时间，单位（毫秒）
     */
    public void hide(long delay) {
        hide(delay, null);
    }

    /**
     * 可延迟关闭，可以有回调
     *
     * @param delay           延迟时间，单位(毫秒)
     * @param dismissListener 回调事件
     */
    public void hide(long delay, final OnDismissListener dismissListener) {
        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                hide();
                if (dismissListener != null) {
                    dismissListener.onDismiss(ViewToast.toast);
                }
            }
        }, delay);
    }

    /**
     * 关闭
     * <p>  不被建议函数 </p>
     * {@link ViewToast#hide()}
     */
    @Deprecated
    public void dissmiss() {
        AnimatorSet set = new AnimatorSet();
        if (!isShowing)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (objectAnimatorDis != null && objectAnimatorDis.isRunning())
                return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            objectAnimatorDis = ObjectAnimator.ofFloat(mView, "alpha", 1.0f, 0f);
            colorAnimator = ObjectAnimator.ofInt(mView, "backgroundColor", 0x36000000, 0x00000000);
            colorAnimator.setEvaluator(new ArgbEvaluator());
            //动画组合
            set.play(colorAnimator);
            set.play(objectAnimatorDis);
            set.setDuration(300);
            set.start();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            set.addListener(new Animator.AnimatorListener() {
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

    /**
     * {@link ViewToast#hide(long)}
     *
     * @param d
     */
    @Deprecated
    public void dissmiss(int d) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dissmiss();
            }
        }, d);
    }

    /**
     * <h1>不被建议</h1>
     * 关闭，延迟多少毫秒，关闭可以收到回调
     *
     * @param num
     * @param dismissListener
     */
    @Deprecated
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
     * 修改状态
     *
     * @param state 状态
     * @param text  文字
     * @return 当前对象
     */
    private ViewToast<T> changeState(State state, String text) {
        this.currState = state;
        //设置文字
        setText(text);
        //修改状态

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
        if (mProgressBarH != null && style == STYLE_PROGRESS_BAR)
            mProgressBarH.setVisibility(View.VISIBLE);

        if (mProgressBarO != null && style == STYLE_PROGRESS_CIR)
            mProgressBarO.setVisibility(View.VISIBLE);
        return this;
    }

    public ViewToast<T> hideText() {
        if (mTextView != null) {
            mTextView.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * {@link ViewToast#hide()}
     */
    @Override
    @Deprecated
    public void cancel() {
        dismiss();
    }

    /**
     * {@link ViewToast#hide()}
     */
    @Override
    @Deprecated
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
