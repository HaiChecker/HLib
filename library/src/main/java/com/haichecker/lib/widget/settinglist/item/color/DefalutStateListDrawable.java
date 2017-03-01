package com.haichecker.lib.widget.settinglist.item.color;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.google.common.base.Preconditions;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-5 19:30
 */

public class DefalutStateListDrawable extends StateListDrawable {



    /**
     * @param mContext           Context
     * @param pressedDrawableId  pressed
     * @param selectedDrawableId selected
     * @param focusedDrawableId  focused
     * @return {@link DefalutStateListDrawable}
     */
    public static DefalutStateListDrawable create(Context mContext, @DrawableRes int pressedDrawableId, @DrawableRes int selectedDrawableId, @DrawableRes int focusedDrawableId, @DrawableRes int defalutDrawableId) {
        DefalutStateListDrawable stateListDrawable = new DefalutStateListDrawable();
        Preconditions.checkNotNull(mContext);
        int pressed = android.R.attr.state_pressed;
        int window_focused = android.R.attr.state_window_focused;
        int focused = android.R.attr.state_focused;
        int selected = android.R.attr.state_selected;
        if (pressedDrawableId != -1) {
            /**
             * 设置按下状态
             */
            stateListDrawable.addState(new int[]{pressed, window_focused}, ContextCompat.getDrawable(mContext, pressedDrawableId));
        }
        if (selectedDrawableId != -1) {
            /**
             * 设置选中
             */
            stateListDrawable.addState(new int[]{selected}, ContextCompat.getDrawable(mContext, selectedDrawableId));
        }

        if (focusedDrawableId != -1) {
            /**
             *  设置有焦点状态
             */
            stateListDrawable.addState(new int[]{focused}, ContextCompat.getDrawable(mContext, focusedDrawableId));
        }

        if (defalutDrawableId != -1) {
            /**
             * 设置默认状态
             */
            stateListDrawable.addState(new int[]{}, ContextCompat.getDrawable(mContext, focusedDrawableId));
        }

        return stateListDrawable;
    }

    /**
     *
     * @param mContext
     * @param pressedDrawableId
     * @param defalutDrawableId
     * @return
     */
    public static DefalutStateListDrawable create(Context mContext, @DrawableRes int pressedDrawableId, @DrawableRes int defalutDrawableId) {
        return create(mContext, pressedDrawableId, -1, -1, defalutDrawableId);
    }
}
