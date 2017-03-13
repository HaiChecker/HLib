package com.haichecker.lib.widget.stateview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.haichecker.lib.R;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-17 12:12
 */

public class DefalutItemLayout extends BaseStateLayout {
    private ItemState defalutState = ItemState.NONE;

    private TextView titleView;

    private TextView detailText;

    protected String detailTextStr;

    private String titleStr = "Item";

    private RelativeLayout rootLayout;

    private RelativeLayout detailLayout;

    @ColorInt
    private int titleColor = 0x666666;

    private boolean isDot;

    private View dotView, topLine, bottomLine;

    private int titleMarginLeft = 10, topLineMarginLeft, bottomMarginLeft;

    private boolean isTopLine = false, isBottomLine = false;

    private int titleSize = 26;

    @ColorInt
    private int lineColor = 0xCCCCCC;

    public DefalutItemLayout(Context context) {
        super(context);
        init();
    }

    public DefalutItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DefalutItemLayout);
        titleColor = array.getColor(R.styleable.DefalutItemLayout_title_color, 0xb0b0b0);
        isDot = array.getBoolean(R.styleable.DefalutItemLayout_isDot, false);
        isTopLine = array.getBoolean(R.styleable.DefalutItemLayout_isTopLine, false);
        isBottomLine = array.getBoolean(R.styleable.DefalutItemLayout_isBottomLine, false);
        titleStr = array.getString(R.styleable.DefalutItemLayout_title_text);
        lineColor = array.getColor(R.styleable.DefalutItemLayout_lineColor, ContextCompat.getColor(getContext(), R.color.test_back_ccc));
        titleMarginLeft = array.getInt(R.styleable.DefalutItemLayout_title_margin_left, 10);
        topLineMarginLeft = array.getInt(R.styleable.DefalutItemLayout_top_margin_left, 0);
        bottomMarginLeft = array.getInt(R.styleable.DefalutItemLayout_bottom_margin_left, 0);
        titleSize = array.getInt(R.styleable.DefalutItemLayout_titleSize, 26);
        detailTextStr = array.getString(R.styleable.DefalutItemLayout_detailText);
        array.recycle();
        init();
    }


    public RelativeLayout getRootLayout() {
        return Preconditions.checkNotNull(rootLayout);
    }

    public void setDot(boolean isShow) {
        if (isShow) {
            if (dotView != null) {
                dotView.setVisibility(VISIBLE);
            }
        } else {
            if (dotView != null) {
                dotView.setVisibility(INVISIBLE);
            }
        }

        isDot = isShow;
    }

    public void setTopLine(boolean isShow) {
        isTopLine = isShow;
        if (isTopLine) {
            if (topLine != null && topLine.getParent() != null)
                rootLayout.removeView(topLine);

            topLine = new View(getContext());
            topLine.setBackgroundColor(lineColor);
            RelativeLayout.LayoutParams top = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            top.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            top.leftMargin = topLineMarginLeft;
            topLine.setLayoutParams(top);
            rootLayout.addView(topLine);
            topLine.bringToFront();
        }
    }

    public void setLineColor(@ColorInt int color) {
        lineColor = color;
        if (topLine != null) {
            topLine.setBackgroundColor(lineColor);
        }
        if (bottomLine != null) {
            bottomLine.setBackgroundColor(lineColor);
        }
    }

    public void setTopLineMarginLeft(int l) {
        topLineMarginLeft = l;
        if (topLine != null && topLine.getParent() != null) {
            ((RelativeLayout.LayoutParams) topLine.getLayoutParams()).leftMargin = l;
        }
    }

    public void setBottomLine(boolean isShow) {
        isBottomLine = isShow;
        if (isBottomLine) {
            if (bottomLine != null && bottomLine.getParent() != null)
                rootLayout.removeView(bottomLine);

            bottomLine = new View(getContext());
            bottomLine.setBackgroundColor(lineColor);
            RelativeLayout.LayoutParams bottom = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            bottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            bottom.leftMargin = bottomMarginLeft;
            bottomLine.setLayoutParams(bottom);
            rootLayout.addView(bottomLine);
            bottomLine.bringToFront();
        }
    }

    public void setBottomLineMarginLeft(int l) {
        bottomMarginLeft = l;
        if (bottomLine != null && bottomLine.getParent() != null) {
            ((RelativeLayout.LayoutParams) bottomLine.getLayoutParams()).leftMargin = l;
        }
    }


    private void init() {
        rootLayout = new RelativeLayout(getContext());
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dotView = new View(getContext());
        //设置点
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            dotView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dot_drawable));
        } else {
            dotView.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.dot_drawable));
        }
        dotView.setId(android.R.id.icon);
        RelativeLayout.LayoutParams dotLayoutParams = new RelativeLayout.LayoutParams(10, 10);
        dotLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        dotLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        dotLayoutParams.leftMargin = 8;
        if (!isDot) {
            dotView.setVisibility(INVISIBLE);
        }
        dotView.setLayoutParams(dotLayoutParams);
        rootLayout.addView(dotView);
        //设置标题
        titleView = new TextView(getContext());
        titleView.setId(android.R.id.title);
        RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleLayoutParams.addRule(RelativeLayout.RIGHT_OF, android.R.id.icon);
        titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        titleLayoutParams.leftMargin = titleMarginLeft;
        titleView.setLayoutParams(titleLayoutParams);
        titleView.setGravity(Gravity.CENTER_VERTICAL);
        titleView.setTextColor(titleColor);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        titleView.setText(titleStr);

        rootLayout.addView(titleView);
        if (isTopLine) {
            topLine = new View(getContext());
            topLine.setBackgroundColor(lineColor);
            RelativeLayout.LayoutParams top = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            top.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            top.leftMargin = topLineMarginLeft;
            topLine.setLayoutParams(top);
            rootLayout.addView(topLine);
            topLine.bringToFront();
        }
        if (isBottomLine) {
            bottomLine = new View(getContext());
            bottomLine.setBackgroundColor(lineColor);
            RelativeLayout.LayoutParams bottom = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            bottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            bottom.leftMargin = bottomMarginLeft;
            bottomLine.setLayoutParams(bottom);
            rootLayout.addView(bottomLine);
            bottomLine.bringToFront();
        }

        detailLayout = new RelativeLayout(getContext());
        detailLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        detailLayout.setId(android.R.id.content);
        RelativeLayout.LayoutParams contentLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        contentLayoutParams.rightMargin = 30;
        detailLayout.setLayoutParams(contentLayoutParams);

        detailText = new TextView(getContext());
        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        detailText.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        detailText.setLayoutParams(textLayoutParams);
        detailText.setText(detailTextStr);
        detailText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 26);
        detailText.setTextColor(ContextCompat.getColor(getContext(), R.color.test_back_333));
        detailLayout.addView(detailText);
        rootLayout.addView(detailLayout);
        addView(rootLayout);
    }

    public void setDetailText(String str) {
        if (detailText != null) {
            detailText.setText(str);
        }
        detailTextStr = str;
    }

    public void setTitleMarginLeft(int m) {
        if (titleView != null)
            ((RelativeLayout.LayoutParams) titleView.getLayoutParams()).leftMargin = m;
        titleMarginLeft = m;
    }

    public void setTitle(String str) {
        if (titleView != null)
            titleView.setText(str);
        titleStr = str;
    }

    public void setTitleColor(@ColorInt int color) {
        if (titleView != null)
            titleView.setTextColor(color);
        titleColor = color;
    }

    public enum ItemState {
        EDIT, NONE, MORE
    }

    public String getTitleStr() {
        return titleStr;
    }

    public String getDetailTextStr() {
        return detailTextStr;
    }

    @Override
    public void changeState(State state) {
        super.changeState(state);
    }
}
