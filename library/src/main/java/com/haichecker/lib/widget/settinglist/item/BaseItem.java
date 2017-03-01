package com.haichecker.lib.widget.settinglist.item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.common.base.Preconditions;
import com.haichecker.lib.R;

/**
 * default height = 80px
 * default backColor = 0xffffff
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-5 18:48
 */

public abstract class BaseItem {

    private View b, t, r, l;

    /**
     * 背景颜色
     */
    @ColorInt
    private int backColor = 0xffffff;
    /**
     * item高度
     */
    private int height = 140;

    /**
     * 根布局
     */
    private LinearLayout rootView;

    private RelativeLayout view;

    /**
     * dot
     */
    private RelativeLayout dot;

    /**
     * context
     */
    private Context mContext;

    /**
     *
     */
    private int textPanding = 25;


    public BaseItem(Context context) {
        this.mContext = context;
        rootView = new LinearLayout(mContext);
        rootView.setId(R.id.setting_list_item_root);
        rootView.setBackgroundColor(backColor);
        dot = new RelativeLayout(mContext);
        dot.setId(R.id.setting_list_item_dot);
        view = new RelativeLayout(mContext);


        RelativeLayout.LayoutParams dotLayout = new RelativeLayout.LayoutParams(textPanding, ViewGroup.LayoutParams.MATCH_PARENT);
        dotLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        view.addView(dot, dotLayout);
        dot.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams rootLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        rootLayout.addRule(RelativeLayout.RIGHT_OF, R.id.setting_list_item_dot);
//        rootLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        view.addView(rootView, rootLayout);

        init();

        BaseLine.LineOp op = new BaseLine.LineOp();

        getView(rootView, op);

        if (op.lineRect != null && op.layoutParams != null && op.lineColor != null) {
            BaseLine.LineRect rect = op.lineRect;
            BaseLine.LineLayoutParams layoutParams = op.layoutParams;
            BaseLine.LineColor color = op.lineColor;
            if (rect.isB() && layoutParams.getB() != null) {
                b = new View(mContext);
                b.setId(R.id.setting_list_line_b);
                b.setBackgroundColor(ContextCompat.getColor(getContext(), color.getB()));
                view.addView(b, layoutParams.getB());
                b.bringToFront();
            }

            if (rect.isT() && layoutParams.getT() != null) {
                t = new View(mContext);
                t.setId(R.id.setting_list_line_t);
                t.setBackgroundColor(ContextCompat.getColor(getContext(), color.getT()));
                view.addView(t, layoutParams.getT());
                t.bringToFront();
            }

            if (rect.isR() && layoutParams.getR() != null) {
                r = new View(mContext);
                r.setId(R.id.setting_list_line_r);
                r.setBackgroundColor(ContextCompat.getColor(getContext(), color.getR()));
                view.addView(r, layoutParams.getR());
                r.bringToFront();
            }

            if (rect.isL() && layoutParams.getL() != null) {
                l = new View(mContext);
                l.setId(R.id.setting_list_line_l);
                l.setBackgroundColor(ContextCompat.getColor(getContext(), color.getL()));
                view.addView(l, layoutParams.getL());
                l.bringToFront();
            }
        }
    }

    public void setTextPanding(int textPanding) {
        this.textPanding = textPanding;
        if (view.findViewById(R.id.setting_list_item_dot) != null)
            dot.getLayoutParams().width = textPanding;
    }

    /**
     * 统一初始化
     */
    public abstract void init();

    /**
     * View创建成功回调，可以通过这些来设置一些东西
     *
     * @param rootView
     * @param lineOp
     */
    public abstract void getView(LinearLayout rootView, BaseLine.LineOp lineOp);


    /**
     * getView to id
     *
     * @param id view id
     * @return view == null ? null : view
     */
    public View getView(int id) {
        Preconditions.checkNotNull(rootView);
        return rootView.findViewById(id);
    }

    /**
     * getView to tag
     *
     * @param tag view tag
     * @return view == null ? null : view
     */
    public View getView(Object tag) {
        Preconditions.checkNotNull(rootView);
        return rootView.findViewWithTag(tag);
    }

    /**
     * getBackColor
     *
     * @return defalut 0xffffff
     */
    public int getBackColor() {
        return backColor;
    }

    /**
     * set rootView setBackgroundColor
     *
     * @param backColor color
     */
    public void setBackColor(int backColor) {
        this.backColor = backColor;
        if (rootView != null)
            rootView.setBackgroundColor(backColor);
    }

    /**
     * get rootView height
     *
     * @return rootView height
     */
    public int getHeight() {
        return height;
    }

    /**
     * set rootView{@link BaseItem} height
     *
     * @param height height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 设置布局的各种状态
     *
     * @param drawable 状态资源
     */
    protected void setStateListDrawable(StateListDrawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rootView.setBackground(drawable);
        } else {
            rootView.setBackgroundDrawable(drawable);
        }
    }

    /**
     * get context
     *
     * @return context  is null throw new NullPointerException(); {@link NullPointerException}
     */
    public Context getContext() {
        Preconditions.checkNotNull(mContext);
        return mContext;
    }

    /**
     * get rootView
     *
     * @return rootVIew
     */
    public LinearLayout getRootView() {
        return Preconditions.checkNotNull(rootView);
    }

    public RelativeLayout getView() {
        return Preconditions.checkNotNull(view);
    }

    /**
     * setOrientation
     *
     * @param orientation
     */
    public void setOrientation(int orientation) {
        Preconditions.checkNotNull(rootView);
        rootView.setOrientation(orientation);
    }

    /**
     * add line  if line gravity existence is change
     *
     * @param gravity {@link ItemGravity}
     */
    public void addLine(ItemGravity gravity, Drawable drawable) throws Exception {
        Preconditions.checkNotNull(rootView);
        switch (gravity.getValue()) {
            case 3:
                if (l == null || rootView.findViewById(R.id.setting_list_line_l) == null) {
                    l = new View(getContext());
                    rootView.addView(l);
                }
                setDrawable(drawable, l);

                break;
            case 5:
                if (r == null || rootView.findViewById(R.id.setting_list_line_r) == null) {
                    r = new View(getContext());
                }
                setDrawable(drawable, r);
                break;
            case 48:
                if (t == null || rootView.findViewById(R.id.setting_list_line_t) == null) {
                    t = new View(getContext());
                }
                setDrawable(drawable, t);
                break;
            case 80:
                if (b == null || rootView.findViewById(R.id.setting_list_line_b) == null) {
                    b = new View(getContext());
                }
                setDrawable(drawable, b);
                break;
        }
    }

    private void setDrawable(Drawable drawble, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawble);
        } else {
            view.setBackgroundDrawable(drawble);
        }
    }
}



