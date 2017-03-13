package com.haichecker.lib.widget.stateview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.haichecker.lib.R;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-17 18:38
 */

public class MoreItemLayout extends DefalutItemLayout {

    private String moreStr;

    private ImageView img;

    private LinearLayout rootView;

    private TextView message;

    private OnClickListener listener;


    public interface OnClickListener {
        void onClick(MoreItemLayout v, State state);
    }

    public void setListener(final OnClickListener listener) {
        this.listener = listener;
        getRootLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(MoreItemLayout.this, state);
            }
        });
    }

    public MoreItemLayout(Context context) {
        super(context);
        init();
    }

    public MoreItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MoreItemLayout);
        moreStr = array.getString(R.styleable.MoreItemLayout_more_text);
        array.recycle();
        init();
    }

    private void init() {
        rootView = new LinearLayout(getContext());
        rootView.setOrientation(HORIZONTAL);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.rightMargin = 30;
        rootView.setLayoutParams(layoutParams);
        rootView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        message = new TextView(getContext());
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        l.rightMargin = 30;
        message.setLayoutParams(l);
        message.setTextColor(ContextCompat.getColor(getContext(), R.color.test_back_333));
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, 26);
        message.setText(moreStr);
        message.setGravity(Gravity.CENTER_VERTICAL);
        rootView.addView(message);

        img = new ImageView(getContext());
        img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        img.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_other_hsfh));
        rootView.addView(img);
        getRootLayout().addView(rootView);
        switch (state) {
            case STATE_EDIT:
                rootView.setVisibility(VISIBLE);
                findViewById(android.R.id.content).setVisibility(GONE);
                break;
            case STATE_NONE:
                rootView.setVisibility(GONE);
                findViewById(android.R.id.content).setVisibility(VISIBLE);
                break;
        }
        if (listener != null) {
            getRootLayout().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    listener.onClick(MoreItemLayout.this, state);
                }
            });
        }
    }

    public void setMoreStr(String str) {
        moreStr = str;
        if (message != null)
            message.setText(str);
    }

    public String getMoreStr() {
        return Preconditions.checkNotNull(moreStr);
    }

    @Override
    public void changeState(State state) {
        super.changeState(state);
        switch (state) {
            case STATE_EDIT:
                rootView.setVisibility(VISIBLE);
                findViewById(android.R.id.content).setVisibility(GONE);
                break;
            case STATE_NONE:
                rootView.setVisibility(GONE);
                findViewById(android.R.id.content).setVisibility(VISIBLE);
                break;
        }
    }

}
