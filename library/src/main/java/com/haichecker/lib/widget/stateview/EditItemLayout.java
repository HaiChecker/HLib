package com.haichecker.lib.widget.stateview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.common.base.Preconditions;
import com.haichecker.lib.R;

import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-17 17:38
 */

public class EditItemLayout extends DefalutItemLayout {
    private RelativeLayout editLayout;
    private EditText editText;
    private int gravity = 5;
    private int textSize = 26;
    private String edit_text, edit_hide;
//    <attr name="none_text" format="string"/>
//    <attr name="none_hide" format="string"/>
//    <attr name="edit_text" format="string"/>
//    <attr name="edit_hide" format="string"/>

    public EditText getEditText() {
        return editText;
    }

    public String getEdit_text() {
        return edit_text;
    }

    public void setEdit_text(String edit_text) {
        this.edit_text = edit_text;
        if (editText != null) {
            editText.setText(edit_text);
        }
    }

    public EditText getEdit() {
        return Preconditions.checkNotNull(editText);
    }

    public String getEdit_hide() {
        return edit_hide;
    }

    public void setEdit_hide(String edit_hide) {
        this.edit_hide = edit_hide;
        if (editText != null) {
            editText.setHint(edit_hide);
        }
    }

    public EditItemLayout(Context context) {
        super(context);
    }

    public EditItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditItemLayout);
//        setNone_hide(array.getString(R.styleable.EditItemLayout_none_hide));
//        setNone_text(array.getString(R.styleable.EditItemLayout_none_text));
        setEdit_hide(array.getString(R.styleable.EditItemLayout_edit_hide));
        setEdit_text(array.getString(R.styleable.EditItemLayout_edit_text));
        gravity = array.getInteger(R.styleable.EditItemLayout_edit_gravity, 5);
        textSize = array.getInteger(R.styleable.EditItemLayout_edit_text_size, 26);
        array.recycle();
        init();
    }



    private void init() {
        editLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams e = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        e.addRule(ALIGN_PARENT_RIGHT);
        e.addRule(RelativeLayout.CENTER_VERTICAL);
        e.rightMargin = 30;
        e.addRule(RelativeLayout.RIGHT_OF, android.R.id.title);
        editLayout.setLayoutParams(e);

        editText = new EditText(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(null);
        } else {
            editText.setBackgroundDrawable(null);
        }

        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        editText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.test_back_333));
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.test_back_333));
        RelativeLayout.LayoutParams edit = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        edit.addRule(RelativeLayout.CENTER_VERTICAL);
        edit.addRule(ALIGN_PARENT_RIGHT);
        editText.setText(edit_text);
        editText.setHint(edit_hide);
//        editText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        setGravity(gravity);
        editText.setLayoutParams(edit);

        editLayout.addView(editText);
//        addView(editLayout);
        getRootLayout().addView(editLayout);
        if (state == State.STATE_EDIT) {
            editLayout.setVisibility(VISIBLE);
            findViewById(android.R.id.content).setVisibility(GONE);
        } else {
            editLayout.setVisibility(GONE);
            findViewById(android.R.id.content).setVisibility(VISIBLE);
        }

    }


    public void setGravity(int gravity) {
        if (gravity == Gravity.LEFT || gravity == Gravity.START) {
            this.gravity = Gravity.LEFT;
            editText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) getRootLayout().findViewById(android.R.id.content).getLayoutParams();
            lp.addRule(ALIGN_PARENT_LEFT);
            getRootLayout().findViewById(android.R.id.content).setLayoutParams(lp);
        } else if (Gravity.RIGHT == gravity || Gravity.END == gravity) {
            this.gravity = Gravity.RIGHT;
            editText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) getRootLayout().findViewById(android.R.id.content).getLayoutParams();
            lp.addRule(ALIGN_PARENT_RIGHT);
            getRootLayout().findViewById(android.R.id.content).setLayoutParams(lp);
        }
    }

    @Override
    public void changeState(State state) {
        super.changeState(state);
        switch (state) {
            case STATE_NONE:
                editLayout.setVisibility(GONE);
                findViewById(android.R.id.content).setVisibility(VISIBLE);
                break;
            case STATE_EDIT:
                editLayout.setVisibility(VISIBLE);
                findViewById(android.R.id.content).setVisibility(GONE);
                break;
        }
    }
}
