package com.haichecker.lib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.widget.Button;

import com.haichecker.lib.R;


/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-4 18:36
 */

public class HButton extends Button {

    /**
     * 按下状态的图片、颜色
     */
    private Drawable touch_down_drawable;
    /**
     * 弹起状态的图片、颜色
     */
    private Drawable touch_up_drawable;
    /**
     * 按下状态的文字颜色
     */
    private int touch_down_text;
    /**
     * 弹起状态的文字颜色
     */
    private int touch_up_text;

    /**
     * selected the color
     */
    private int selected_text_color;

    public HButton(Context context) {
        super(context);
    }

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param attrs   参数
     */
    public HButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HButton);
        //获取按下状态的文字颜色
        touch_down_text = typedArray.getColor(R.styleable.HButton_touch_down_text, getCurrentTextColor());
        //获取弹起状态的文字颜色
        touch_up_text = typedArray.getColor(R.styleable.HButton_touch_up_text, getCurrentTextColor());
        //get selected text color
        selected_text_color = typedArray.getColor(R.styleable.HButton_selected_text_color, getCurrentTextColor());
        //获取按下状态的背景
        touch_down_drawable = typedArray.getDrawable(R.styleable.HButton_touch_down_drawble);
        //获取弹起状态的背景
        touch_up_drawable = typedArray.getDrawable(R.styleable.HButton_touch_up_drawble);
        //释放对象
        typedArray.recycle();


        //Init Text_Color
        setTextColorList();

    }

    public HButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置选中的文字颜色
     *
     * @param selected_text_color color Res
     */
    public void setSelected_text_color(int selected_text_color) {
        this.selected_text_color = selected_text_color;
        setTextColorList();
    }

    /**
     * 设置按下状态的背景
     *
     * @param touch_down_drawable 资源
     */
    public void setTouch_down_drawable(Drawable touch_down_drawable) {
        this.touch_down_drawable = touch_down_drawable;
    }

    /**
     * 设置弹起状态的背景
     *
     * @param touch_up_drawable 资源
     */
    public void setTouch_up_drawable(Drawable touch_up_drawable) {
        this.touch_up_drawable = touch_up_drawable;
    }

    /**
     * 设置按下状态的文字颜色
     *
     * @param touch_down_text 颜色
     */
    public void setTouch_down_text(@ColorRes int touch_down_text) {
        this.touch_down_text = touch_down_text;
        setTextColorList();
    }

    /**
     * 设置弹起状态的文字颜色
     *
     * @param touch_up_text 颜色
     */
    public void setTouch_up_text(@ColorRes int touch_up_text) {
        this.touch_up_text = touch_up_text;
        setTextColorList();
    }


    private int[] getColors() {
        return new int[]{touch_down_text, touch_up_text, selected_text_color};
    }

    /**
     * 这是
     */
    private void setTextColorList() {
        int[] color = getColors();
        int[][] state = new int[3][];
        state[0] = new int[]{android.R.attr.state_pressed};
        state[1] = new int[]{};
        state[2] = new int[]{android.R.attr.state_selected};
        ColorStateList list = new ColorStateList(state, color);
        setTextColor(list);
    }
}
