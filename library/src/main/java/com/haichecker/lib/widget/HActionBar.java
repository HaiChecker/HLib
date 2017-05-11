package com.haichecker.lib.widget;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.haichecker.lib.R;


/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-4-11 11:16
 */

public class HActionBar extends RelativeLayout {

    private RelativeLayout contentView;
    private RelativeLayout leftView;
    private RelativeLayout rightView;

    private String title;
    private String backText;
    private boolean isBackDissmis = true;
    private boolean isShowMore = false;
    private boolean isShowTitle = true;
    private boolean isShowBack = true;
    private Drawable backIcon;
    private Drawable moreDrawable;
    private int backIconGravity;
    private int backTextColor, titleTextColor;
    private float backIconDrawablePadding;

    public HActionBar(Context context) {
        super(context);
        init(context);
    }

    public HActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init(context);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HActionBar);
        title = array.getString(R.styleable.HActionBar_title);
        backText = array.getString(R.styleable.HActionBar_back_text);
        isBackDissmis = array.getBoolean(R.styleable.HActionBar_back_is_dissmis, true);
        isShowBack = array.getBoolean(R.styleable.HActionBar_isShowBack, true);
        isShowMore = array.getBoolean(R.styleable.HActionBar_isShowMore, false);
        moreDrawable = array.getDrawable(R.styleable.HActionBar_more_icon);
        isShowTitle = array.getBoolean(R.styleable.HActionBar_isShowTitle, true);
        backIcon = array.getDrawable(R.styleable.HActionBar_back_icon);
        backIconGravity = array.getInt(R.styleable.HActionBar_back_icon_gravity, 1);
        backIconDrawablePadding = array.getDimension(R.styleable.HActionBar_back_icon_drawablePadding, 8f);
        titleTextColor = array.getColor(R.styleable.HActionBar_title_text_color, ContextCompat.getColor(getContext(), R.color.white));
        backTextColor = array.getColor(R.styleable.HActionBar_back_text_color, ContextCompat.getColor(getContext(), R.color.white));
        array.recycle();
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        getTitleView().setTextColor(this.titleTextColor);
    }

    public void setBackTextColor(int backTextColor) {
        this.backTextColor = backTextColor;
        getBackView().setTextColor(this.backTextColor);
    }

    public void setBackIconDrawablePadding(float backIconDrawablePadding) {
        this.backIconDrawablePadding = backIconDrawablePadding;
        getBackView().setCompoundDrawablePadding((int) this.backIconDrawablePadding);

    }

    public void setBackIcon(Drawable backIcona) {
        this.backIcon = backIcona;
        getBackView().setCompoundDrawablesWithIntrinsicBounds(
                backIconGravity == 1 ? backIcon : null,
                backIconGravity == 3 ? backIcon : null,
                backIconGravity == 2 ? backIcon : null,
                backIconGravity == 4 ? backIcon : null
        );
    }

    public void setBackIconGravity(int backIconGravity) {
        this.backIconGravity = backIconGravity;
        getBackView().setCompoundDrawablesWithIntrinsicBounds(
                backIconGravity == 1 ? backIcon : null,
                backIconGravity == 3 ? backIcon : null,
                backIconGravity == 2 ? backIcon : null,
                backIconGravity == 4 ? backIcon : null
        );
    }

    public TextView getBackView() {
        return (TextView) Preconditions.checkNotNull(leftView).findViewById(R.id.back);
    }

    private TextView getTitleView() {
        return (TextView) Preconditions.checkNotNull(contentView).findViewById(R.id.title);
    }

    @SuppressLint("PrivateResource")
    private void init(final Context context) {
        View actionBarView = LayoutInflater.from(getContext()).inflate(R.layout.action_bar_layout, null, false);
        contentView = (RelativeLayout) actionBarView.findViewById(R.id.contentView);
        leftView = (RelativeLayout) actionBarView.findViewById(R.id.leftView);
        rightView = (RelativeLayout) actionBarView.findViewById(R.id.rightView);
        if (isBackDissmis)
            getBackView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Activity) context).finish();
                }
            });
        addView(actionBarView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(android.support.design.R.dimen.abc_action_bar_default_height_material)));
        setTitle(title);
        setBackText(backText);
        if (backIcon == null) {
            backIcon = ContextCompat.getDrawable(getContext(), R.drawable.btn_back);
        }
        getBackView().setCompoundDrawablesWithIntrinsicBounds(
                backIconGravity == 1 ? backIcon : null,
                backIconGravity == 3 ? backIcon : null,
                backIconGravity == 2 ? backIcon : null,
                backIconGravity == 4 ? backIcon : null
        );
        getBackView().setCompoundDrawablePadding((int) backIconDrawablePadding);
        getMore().setVisibility(isShowMore ? VISIBLE : GONE);
        getMore().setCompoundDrawablesWithIntrinsicBounds(moreDrawable, null, null, null);
        getTitleView().setTextColor(titleTextColor);
        getBackView().setTextColor(backTextColor);
        getTitleView().setVisibility(isShowTitle ? VISIBLE : GONE);
        getBackView().setVisibility(isShowBack ? VISIBLE : GONE);
    }

    public HActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View customTitleView = findViewById(R.id.custom_title_layout);
        if (customTitleView != null) {
            removeView(customTitleView);
            contentView.addView(customTitleView);
        }
        View moreLayout = findViewById(R.id.custom_more_layout);
        if (moreLayout != null) {
            removeView(moreLayout);
            rightView.addView(moreLayout);
        }
    }

    public void setTitle(String title) {
        if (contentView != null) {
            getTitleView().setText(title);
        }
    }


    public void setBackText(String backStr) {
        getBackView().setText(backStr);
    }

    public TextView getMore() {
        return (TextView) Preconditions.checkNotNull(rightView).findViewById(R.id.more);
    }

    public ViewGroup getContentView() {
        return contentView;
    }

    public ViewGroup getLeftView() {
        return leftView;
    }

    public ViewGroup getRightView() {
        return rightView;
    }

}
