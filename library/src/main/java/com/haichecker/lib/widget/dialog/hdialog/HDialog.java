package com.haichecker.lib.widget.dialog.hdialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haichecker.lib.R;
import com.haichecker.lib.utils.DensityUtil;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-4-21 09:42
 */
public class HDialog extends AppCompatDialog {
    private HDialogAdapter adapter;

    private OnHDialogListener onHDialogListener;

    public void setOnHDialogListener(OnHDialogListener onHDialogListener) {
        this.onHDialogListener = onHDialogListener;
    }

    // Activity Content
    private Context mContext;
    //RootView
    private RelativeLayout rootView;
    //ContentView
    private LinearLayout rootContentView;
    //取消按钮
    private Button cancel;
    //消息
    private TextView messageText;
    //消息
    private String message;
    //取消文字
    private String cancelText;
    //确定按钮文字
    private String okText;
    //取消文字颜色
    private int cancelTextColor = Color.parseColor("#ff0000");
    //消息文字颜色
    private int messageColor = Color.parseColor("#999999");
    //Line颜色
    private int lineColor = Color.parseColor("#bfbfbf");
    //确认颜色
    private int okColor = Color.parseColor("#999999");
    //Line
    private View view;
    //确定按钮
    private Button ok;
    //内容
    private LinearLayout contentView;


    private Rect outRect1;

    public void setAdapter(HDialogAdapter adapter) {
        this.adapter = adapter;
    }

    public String getMessage() {
        return message;
    }

    public Button getOk() {
        return ok;
    }

    public String getOkText() {
        return okText;
    }

    public void setOkColor(int okColor) {
        this.okColor = okColor;
        if (ok != null) {
            ok.setTextColor(okColor);
        }
    }

    public void setOkText(String okText) {
        this.okText = okText;
        if (ok != null) {
            ok.setText(okText);
        }
    }

    public TextView getMessageText() {
        return messageText;
    }

    /**
     * 设置消息
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
        if (messageText != null) {
            messageText.setText(message);
        }
    }

    /**
     * 设置取消文字按钮颜色
     *
     * @param cancelTextColor
     */
    public void setCancelTextColor(int cancelTextColor) {
        this.cancelTextColor = cancelTextColor;
        if (cancel != null) {
            cancel.setTextColor(cancelTextColor);
        }
    }

    /**
     * 设置取消按钮文字
     *
     * @param cancelText
     */
    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
        if (cancel != null) {
            cancel.setText(cancelText);
        }
    }

    public HDialog(Context context) {
        super(context);
        mContext = context;
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        rootView = new RelativeLayout(mContext);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.setGravity(Gravity.BOTTOM);
        rootContentView = new LinearLayout(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //设置局下
//        layoutParams.gravity = Gravity.BOTTOM;
        //设置
        rootContentView.setLayoutParams(layoutParams);
        //设置垂直
        rootContentView.setOrientation(LinearLayout.VERTICAL);
        rootView.addView(rootContentView);
        rootView.setBackgroundColor(Color.parseColor("#00000000"));
        super.setContentView(rootView);
        if (getWindow() != null) {
            getWindow().getDecorView().setPadding(0, 0, 0, 0);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        //初始化消息
        messageText = new TextView(mContext);
        messageText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip_px(60, getContext().getResources().getDisplayMetrics().density)));
        messageText.setGravity(Gravity.CENTER);
        messageText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.test_back));
        messageText.setTextColor(messageColor);
        messageText.setText(message);
        //初始化取消按钮
        cancel = new Button(mContext);
        cancel.setText(cancelText);
        cancel.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.test_back));
        cancel.setTextColor(cancelTextColor);
        cancel.setLayoutParams(getDefalut());

        //初始化确认按钮
        ok = new Button(mContext);
        ok.setText(okText);
        ok.setTextColor(okColor);
        ok.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.test_back));
        ok.setLayoutParams(getDefalut());

        //初始化线条颜色
        view = new View(mContext);
        view.setId(R.id.hdialog_line);
        view.setBackgroundColor(lineColor);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 7));

        contentView = new LinearLayout(mContext);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 设置消息文字颜色
     *
     * @param messageColor
     */
    public void setMessageColor(int messageColor) {
        this.messageColor = messageColor;
        if (messageText != null) {
            messageText.setTextColor(messageColor);
        }
    }

    public LinearLayout.LayoutParams getDefalut() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip_px(45, getContext().getResources().getDisplayMetrics().density));
    }

    @Override
    public void setOnDismissListener(@Nullable final OnDismissListener listener) {
        super.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (listener != null) {
                    listener.onDismiss(dialog);
                }
            }
        });
    }

    @Override
    public void dismiss() {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        rootContentView.measure(width, height);
        rootView.measure(width, height);
        float endY = outRect1.height() - rootContentView.getMeasuredHeight();
        float currY = outRect1.height();
        Log.d("curr", "curr=" + currY + ",end=" + endY);
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(rootContentView, "Y", endY, currY);
        yAnimator.setDuration(300);
        yAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                HDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        yAnimator.start();
    }

    @Override
    public void show() {
        rootContentView.removeAllViews();
        contentView.removeAllViews();

        if (!TextUtils.isEmpty(message))
            rootContentView.addView(messageText);

        //初始化线条颜色
        View topLine = new View(mContext);
        topLine.setBackgroundColor(lineColor);
        topLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        rootContentView.addView(topLine);

        if (contentView != null) {
            if (adapter != null && adapter.itemCount() > 0) {
                contentView.removeAllViews();
                for (int i = 0; i < adapter.itemCount(); i++) {
                    Button button = new Button(mContext);
                    button.setLayoutParams(getDefalut());
                    button.setTag(i);
                    if (onHDialogListener != null) {
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onHDialogListener.itemClick(v, Integer.parseInt(v.getTag().toString()));
                            }
                        });
                        button.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                onHDialogListener.itenLongClick(v, Integer.parseInt(v.getTag().toString()));
                                return false;
                            }
                        });
                    }
                    adapter.itemOnView(button, i);
                    contentView.addView(button);
                    //初始化线条颜色
                    View line = new View(mContext);
                    line.setBackgroundColor(lineColor);
                    line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                    contentView.addView(line);
                }
            }
            rootContentView.addView(contentView);
        }

        if (ok != null && !TextUtils.isEmpty(okText)) {
            if (adapter != null) {
                adapter.button(ok, 2);
            }
            if (onHDialogListener != null) {
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onHDialogListener.okClick();
                    }
                });
            }
            rootContentView.addView(ok);
        }

        if (view != null) {
            rootContentView.addView(view);
        }

        if (cancel != null) {
            if (adapter != null) {
                adapter.button(cancel, 1);
            }
            if (onHDialogListener != null) {
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onHDialogListener.cancelClick();
                    }
                });
            }
            rootContentView.addView(cancel);
        }
        super.show();

        if (getWindow() != null) {
            //应用区域
            outRect1 = new Rect();
            ((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = getContext().getResources().getDisplayMetrics().widthPixels; //设置宽度
            lp.height = outRect1.height(); //设置高度
            getWindow().setAttributes(lp);
        }
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        rootContentView.measure(width, height);
        rootView.measure(width, height);
        float endY = outRect1.height() - rootContentView.getMeasuredHeight();

        rootContentView.setY(outRect1.height());
        float currY = outRect1.height();
        Log.d("curr", "curr=" + currY + ",end=" + endY);
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(rootContentView, "Y", currY, endY);
        yAnimator.setDuration(300);
        yAnimator.start();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        rootContentView.addView(view, params);
    }

    /**
     * 不能使用此函数 建议使用 {@link HDialog#setContentView(View)} {@link HDialog#setContentView(View, ViewGroup.LayoutParams)}
     *
     * @param layoutResID 资源
     */
    @Deprecated
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
    }

    @Override
    public void setContentView(View view) {
        rootContentView.addView(view);
    }

    /**
     * 获取内容View
     *
     * @return contentView
     */
    private LinearLayout getContentView() {
        return contentView;
    }

    /**
     * 获取背景View
     *
     * @return rootView
     */
    protected RelativeLayout getRootView() {
        return rootView;
    }
}
