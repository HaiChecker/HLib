package com.haichecker.lib.widget.settinglist.item;

import android.content.Context;
import android.databinding.tool.util.L;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.common.base.Preconditions;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-6 14:01
 */

public class BaseLine {

    public static final int defalut_width = 0x0000001;
    public static final int defalut_height = 0x0000002;

    @ColorInt
    private int color;

    /**
     * line height
     */
    private int height = defalut_height;

    /**
     * line width
     */
    private int width = defalut_width;


    private int ma;


    private View line;

    /**
     * get line View
     *
     * @return
     */
    public View getLine() {
        return Preconditions.checkNotNull(line);
    }

    private Context mContext;

    public Context getContext() {
        return Preconditions.checkNotNull(mContext);
    }

    public BaseLine(Context mContext) {
        this.mContext = mContext;
        line = new View(mContext);
    }

    public ViewGroup.LayoutParams getLayoutParams() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(height == defalut_height ? 1 : height, width == defalut_width ? ViewGroup.LayoutParams.MATCH_PARENT : width);
        return layoutParams;
    }

    public static class LineRect {
        private boolean t, b, l, r;

        public LineRect(boolean top, boolean b, boolean l, boolean r) {
            this.t = top;
            this.b = b;
            this.r = r;
            this.l = l;
        }

        public boolean isT() {
            return t;
        }

        public boolean isB() {
            return b;
        }

        public boolean isL() {
            return l;
        }

        public boolean isR() {
            return r;
        }
    }

    public static class LineLayoutParams {
        private RelativeLayout.LayoutParams t, b, l, r;

        public LineLayoutParams(RelativeLayout.LayoutParams t, RelativeLayout.LayoutParams b, RelativeLayout.LayoutParams l, RelativeLayout.LayoutParams r) {
            this.t = t;
            this.b = b;
            this.l = l;
            this.r = r;
        }

        public RelativeLayout.LayoutParams getT() {
            return t;
        }

        public RelativeLayout.LayoutParams getB() {
            return b;
        }

        public RelativeLayout.LayoutParams getL() {
            return l;
        }

        public RelativeLayout.LayoutParams getR() {
            return r;
        }
    }

    public static class LineColor {
        @ColorInt
        private int t, b, l, r;

        public LineColor(int t, int b, int l, int r) {
            this.t = t;
            this.b = b;
            this.l = l;
            this.r = r;
        }

        public int getT() {
            return t;
        }

        public int getB() {
            return b;
        }

        public int getL() {
            return l;
        }

        public int getR() {
            return r;
        }
    }

    public static class LineOp {
        public LineColor lineColor;
        public LineLayoutParams layoutParams;
        public LineRect lineRect;
    }
}
