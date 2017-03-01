package com.haichecker.lib.widget.tools;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-5 14:52
 */

public abstract class HTextColorList {

    /**
     * save colors
     */
    private ArrayList<Integer> colors = new ArrayList<>();
    /**
     * save states
     */
    private ArrayList<int[]> states = new ArrayList<>();

    /**
     * set colors to {@link HTextColorList} addColor(int[] key,int color)
     */
    protected abstract void setColorList();

    protected synchronized HTextColorList addColor(int[] key, @ColorRes int color) {
        states.add(key);
        colors.add(color);
        return this;
    }


    /**
     * create ColorStateList Object
     *
     * @return ColorStateList to setColorList
     */
    public ColorStateList create() {

        setColorList();

        Log.d("wwww", "create");

        int[] color = new int[colors.size()];
        for (int i = 0; i < colors.size(); i++) {
            color[i] = colors.get(i);
        }

        int[][] states = new int[this.states.size()][];

        for (int i = 0; i < this.states.size(); i++) {
            states[i] = this.states.get(i);
            Log.d("wwww", "states size = " + this.states.get(i).length);
        }

        return new ColorStateList(states, color);
    }
}
