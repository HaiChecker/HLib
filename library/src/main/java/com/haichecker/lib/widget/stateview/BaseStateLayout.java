package com.haichecker.lib.widget.stateview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-17 12:09
 */

public class BaseStateLayout extends LinearLayout {
    protected State state = State.STATE_EDIT;
    protected OnChangeListener listener;

    public void setOnChangeListener(OnChangeListener listener) {
        this.listener = listener;
    }

    public BaseStateLayout(Context context) {
        super(context);
    }

    public BaseStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void changeState(State state) {
        this.state = state;
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof BaseStateLayout) {
                BaseStateLayout layout = (BaseStateLayout) v;
                layout.changeState(state);
            }
        }
        if (listener != null)
            listener.changeState(state);
    }

    public enum State {
        STATE_NONE, STATE_EDIT
    }

    public interface OnChangeListener {
        void changeState(State state);
    }
}
