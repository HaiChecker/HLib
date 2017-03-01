package com.haichecker.lib.widget.settinglist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.common.base.Preconditions;
import com.haichecker.lib.R;
import com.haichecker.lib.widget.settinglist.item.BaseItem;
import com.haichecker.lib.widget.settinglist.item.BaseLine;
import com.haichecker.lib.widget.settinglist.item.DefalutTextItem;
import com.haichecker.lib.widget.settinglist.item.ItemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-5 18:48
 */

public class SettingList extends RelativeLayout {

    private LinearLayout root = null;

    private ScrollView scrollView;

    private HorizontalScrollView horizontalScrollView;

    public SettingList(Context context) {
        super(context);
    }

    public SettingList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SettingList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public <I extends ItemLayout> void addItemLayout(I layout) {
        root.addView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public <I extends ItemLayout> void addItemLayout(I layout, ViewGroup.LayoutParams layoutParams) {
        root.addView(layout, layoutParams);
    }

    /**
     * init this View
     */
    private void init() {

        if (scrollView == null) {
            scrollView = new ScrollView(getContext());
            addView(scrollView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        if (root == null) {
            root = new LinearLayout(getContext());
            root.setOrientation(LinearLayout.VERTICAL);
            Preconditions.checkNotNull(scrollView);
            scrollView.addView(root, new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        }

        Preconditions.checkNotNull(root);

        ItemLayout item = new ItemLayout(getContext()) {
            @Override
            public <B extends BaseItem> B getView(ViewGroup group, B itemView, int p) {
                return itemView;
            }

            @Override
            public void setHeadView(LinearLayout rootHeadView) {
                rootHeadView.setPadding(0, 0, 0, 0);
            }
        };

        item.setOrientation(LinearLayout.HORIZONTAL);
        View view = new View(getContext());
        item.addView(view, new ViewGroup.LayoutParams(280, 280));

        ItemLayout layout = new ItemLayout(getContext()) {
            @Override
            public <B extends BaseItem> B getView(ViewGroup group, B itemView, int p) {
                return itemView;
            }

            @Override
            public void setHeadView(LinearLayout rootHeadView) {
                rootHeadView.setPadding(0, 0, 0, 0);
            }
        };
        for (int i = 0; i < 2; i++) {
            final int finalI = i;
            DefalutTextItem defalutTextItem = new DefalutTextItem(getContext()) {
                @Override
                public void getView(LinearLayout rootView, BaseLine.LineOp lineOp) {
                    lineOp.lineColor = new BaseLine.LineColor(R.color.test_b0bob, R.color.test_b0bob, 0, 0);
                    lineOp.layoutParams = new BaseLine.LineLayoutParams(finalI == 0 ? ItemUtils.getTopLayoutParams() : null, finalI == 4 ? ItemUtils.getBottomLayoutParams() : ItemUtils.getBottomLayoutParams(25), null, null);
                    lineOp.lineRect = new BaseLine.LineRect(finalI == 0, true, false, false);
                }
            };
            defalutTextItem.setTitle("Item " + i);
            defalutTextItem.getTitleText().setTextColor(ContextCompat.getColor(getContext(), R.color.test_b0bob));
            layout.addItem(defalutTextItem);
        }
        item.addView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        root.addView(item, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int d = 0; d < 3; d++) {
            final int finalD = d;
            ItemLayout itemLayout = new ItemLayout(getContext()) {
                @Override
                public <B extends BaseItem> B getView(ViewGroup group, B itemView, int p) {
                    return itemView;
                }

                @Override
                public void setHeadView(LinearLayout rootHeadView) {
                }
            };
            itemLayout.setOrientation(LinearLayout.VERTICAL);

            for (int i = 0; i < 5; i++) {
                final int finalI = i;
                if (i % 2 == 0) {
                    DefalutTextItem defalutTextItem = new DefalutTextItem(getContext()) {
                        @Override
                        public void getView(LinearLayout rootView, BaseLine.LineOp lineOp) {
                            lineOp.lineColor = new BaseLine.LineColor(R.color.test_b0bob, R.color.test_b0bob, 0, 0);
                            lineOp.layoutParams = new BaseLine.LineLayoutParams(finalI == 0 ? ItemUtils.getTopLayoutParams() : null, finalI == 4 ? ItemUtils.getBottomLayoutParams() : ItemUtils.getBottomLayoutParams(25), null, null);
                            lineOp.lineRect = new BaseLine.LineRect(finalI == 0, true, false, false);
                        }
                    };
                    defalutTextItem.setTitle("Item " + i);
                    defalutTextItem.getTitleText().setTextColor(ContextCompat.getColor(getContext(), R.color.test_b0bob));
                    itemLayout.addItem(defalutTextItem);
                } else {
                    EditItem defalutTextItem = new EditItem(getContext()) {
                        @Override
                        public void getView(LinearLayout rootView, BaseLine.LineOp lineOp) {
                            lineOp.lineColor = new BaseLine.LineColor(R.color.test_b0bob, R.color.test_b0bob, 0, 0);
                            lineOp.layoutParams = new BaseLine.LineLayoutParams(finalI == 0 ? ItemUtils.getTopLayoutParams() : null, finalI == 4 ? ItemUtils.getBottomLayoutParams() : ItemUtils.getBottomLayoutParams(25), null, null);
                            lineOp.lineRect = new BaseLine.LineRect(finalI == 0, true, false, false);
                        }
                    };
                    defalutTextItem.setTitle("Item " + i);
                    defalutTextItem.getTitleText().setTextColor(ContextCompat.getColor(getContext(), R.color.test_b0bob));
                    defalutTextItem.getEditText().setHint("请输入XXX");
                    itemLayout.addItem(defalutTextItem);
                }
            }
            root.addView(itemLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
}
