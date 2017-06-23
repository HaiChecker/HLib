package com.haichecker.lib.widget.tableview.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haichecker.lib.R;

/**
 * createTime 2017/6/22 11:24
 * <p>
 * devUser 石文平
 * <p>
 * classDetail  基类Cell
 */
public class BaseCell extends RelativeLayout {
    //xian
    private View lineView;
    //View
    private RelativeLayout contentView;
    //左边图片
    private ImageView imageView;
    //中间文字
    private TextView textLabel;
    //详情文字
    private TextView detailTextLabel;
    //右边的辅助View
    private RelativeLayout rightView;
    //用于上下结构的详情布局
    private LinearLayout contentSubText;
    //初始化默认
    private CellViewStyle cellViewStyle = CellViewStyle.CELL_VIEW_STYLE_DEFAULT;
    //不显示任何图表
    private CellViewAccessoryType cellViewAccessoryType = CellViewAccessoryType.CELL_VIEW_ACCESSORY_TYPE_ACCESSORYDISCLOSUREINDICATOR;
    //箭头图标
    private Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_other_hsfh);

    private String text = "text Label";

    private String detailText = "detail Text";

    public void setCellViewAccessoryType(CellViewAccessoryType cellViewAccessoryType) {
        this.cellViewAccessoryType = cellViewAccessoryType;
        rightView.removeAllViews();
        switch (cellViewAccessoryType) {
            case CELL_VIEW_ACCESSORY_TYPE_ACCESSORYNONE:
                break;
            case CELL_VIEW_ACCESSORY_TYPE_ACCESSORYCHECKMARK:
                break;
            case CELL_VIEW_ACCESSORY_TYPE_ACCESSORYDETAILBUTTON:
                break;
            case CELL_VIEW_ACCESSORY_TYPE_ACCESSORYDISCLOSUREINDICATOR:
                ImageView imageView = new ImageView(getContext());
                imageView.setImageDrawable(drawable);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                rightView.addView(imageView);
                break;
            case CELL_VIEW_ACCESSORY_TYPE_ACCESSORYDETAILDISCLOSUREBUTTON:
                break;
        }
    }

    public View getLineView() {
        return lineView;
    }

    /**
     * 设置线条
     *
     * @param lineView
     */
    public void setLineView(View lineView) {
        if (this.lineView != null)
            removeView(this.lineView);
        this.lineView = lineView;
        if (lineView == null) {
            return;
        }
        lineView.setId(R.id.line_view);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) lineView.getLayoutParams();
        if (layoutParams == null)
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lineView.setLayoutParams(layoutParams);

        addView(lineView);

        LayoutParams contentLa = (LayoutParams) contentView.getLayoutParams();
        contentLa.addRule(RelativeLayout.ABOVE, lineView.getId());
        contentView.setLayoutParams(contentLa);


    }

    /**
     * 获取详情TextView
     *
     * @return TextView
     */
    public TextView getDetailTextLabel() {
        if (detailTextLabel == null) {
            detailTextLabel = new TextView(getContext());
            detailTextLabel.setTextSize(12);
            detailTextLabel.setTextColor(Color.parseColor("#bfbfbf"));
            detailTextLabel.setText(detailText);
            int wight = ViewGroup.LayoutParams.WRAP_CONTENT;
            ViewGroup.LayoutParams detailLayoutParams = null;
            switch (cellViewStyle) {
                case CELL_VIEW_STYLE_VALUE1:
                    detailLayoutParams = new RelativeLayout.LayoutParams(wight, ViewGroup.LayoutParams.WRAP_CONTENT);
                    wight = ViewGroup.LayoutParams.MATCH_PARENT;
                    detailTextLabel.setGravity(Gravity.END);
                    ((RelativeLayout.LayoutParams) detailLayoutParams).addRule(RelativeLayout.CENTER_VERTICAL);
                    ((RelativeLayout.LayoutParams) detailLayoutParams).addRule(RelativeLayout.RIGHT_OF, R.id.text_label);
                    ((RelativeLayout.LayoutParams) detailLayoutParams).addRule(RelativeLayout.LEFT_OF, R.id.right_text_view);
                    break;
                case CELL_VIEW_STYLE_VALUE2:
                    detailLayoutParams = new RelativeLayout.LayoutParams(wight, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ((RelativeLayout.LayoutParams) detailLayoutParams).addRule(RelativeLayout.CENTER_VERTICAL);
                    ((RelativeLayout.LayoutParams) detailLayoutParams).addRule(RelativeLayout.RIGHT_OF, R.id.text_label);
                    ((RelativeLayout.LayoutParams) detailLayoutParams).addRule(RelativeLayout.LEFT_OF, R.id.right_text_view);
                    ((LayoutParams) detailLayoutParams).leftMargin = 10;
                    wight = ViewGroup.LayoutParams.WRAP_CONTENT;
                    detailTextLabel.setGravity(Gravity.START);
                    break;
                case CELL_VIEW_STYLE_DEFAULT:
                    detailLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, wight);
                    detailTextLabel.setVisibility(GONE);
                    break;
                case CELL_VIEW_STYLE_SUBTITLE:
                    detailLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ((LinearLayout.LayoutParams) detailLayoutParams).topMargin = 2;
                    break;
            }
            detailLayoutParams.width = wight;
            detailTextLabel.setLayoutParams(detailLayoutParams);
        }
        return detailTextLabel;
    }

    /**
     * 获取详情默认
     *
     * @return 详情
     */
    public LinearLayout getContentSubText() {
        if (contentSubText == null) {
            contentSubText = new LinearLayout(getContext());
            RelativeLayout.LayoutParams contentLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            contentLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            contentLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.image);
            contentLayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.right_text_view);
            contentLayoutParams.leftMargin = 20;
            contentSubText.setLayoutParams(contentLayoutParams);
            contentSubText.setOrientation(LinearLayout.VERTICAL);
        }
        return contentSubText;
    }

    /**
     * 获取最左边图片对象，用于设置图片
     *
     * @return 返回ImageView对象
     */
    public ImageView getImageView() {
        if (imageView == null) {
            imageView = new ImageView(getContext());
            RelativeLayout.LayoutParams imgLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imgLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            imgLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            imgLayoutParams.leftMargin = 20;
            imageView.setId(R.id.image);
            imageView.setLayoutParams(imgLayoutParams);
        }
        return imageView;
    }

    /**
     * 获取左边文字信息
     *
     * @return TextView
     */
    public TextView getTextLabel() {
        if (textLabel == null) {
            textLabel = new TextView(getContext());
            textLabel.setText(text);
            textLabel.setTextSize(14);
            textLabel.setTextColor(Color.parseColor("#000000"));
            textLabel.setId(R.id.text_label);
            ViewGroup.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            switch (cellViewStyle) {
                case CELL_VIEW_STYLE_SUBTITLE:
                    textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ((LinearLayout.LayoutParams) textLayoutParams).bottomMargin = 2;
                    break;
                case CELL_VIEW_STYLE_DEFAULT:
                    ((RelativeLayout.LayoutParams) textLayoutParams).addRule(RelativeLayout.RIGHT_OF, R.id.image);
                    ((RelativeLayout.LayoutParams) textLayoutParams).addRule(RelativeLayout.LEFT_OF, R.id.right_text_view);
                    ((RelativeLayout.LayoutParams) textLayoutParams).addRule(RelativeLayout.CENTER_VERTICAL);
                    ((RelativeLayout.LayoutParams) textLayoutParams).leftMargin = 20;
                    break;
                case CELL_VIEW_STYLE_VALUE2:
                case CELL_VIEW_STYLE_VALUE1:
                    ((RelativeLayout.LayoutParams) textLayoutParams).leftMargin = 20;
                    ((RelativeLayout.LayoutParams) textLayoutParams).addRule(RelativeLayout.RIGHT_OF, R.id.image);
                    ((RelativeLayout.LayoutParams) textLayoutParams).addRule(RelativeLayout.CENTER_VERTICAL);
                    break;
            }

            textLabel.setLayoutParams(textLayoutParams);
        }
        return textLabel;
    }


    /**
     * 设置样式
     *
     * @param cellViewStyle 样式 {@link CellViewStyle}
     */
    public void setCellViewStyle(CellViewStyle cellViewStyle) {
        this.cellViewStyle = cellViewStyle;
    }

    public BaseCell(Context context, CellViewStyle style) {
        super(context);
        this.cellViewStyle = style;
        init();
    }

    public BaseCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseCell);
        int ce = typedArray.getIndex(R.styleable.BaseCell_cell_style);
        switch (ce) {
            case 1:
                cellViewStyle = CellViewStyle.CELL_VIEW_STYLE_DEFAULT;
                break;
            case 2:
                cellViewStyle = CellViewStyle.CELL_VIEW_STYLE_VALUE1;
                break;
            case 3:
                cellViewStyle = CellViewStyle.CELL_VIEW_STYLE_VALUE2;
                break;
            case 4:
                cellViewStyle = CellViewStyle.CELL_VIEW_STYLE_SUBTITLE;
                break;
        }
        Log.d("BaseCell", ce + "");
        text = typedArray.getString(R.styleable.BaseCell_text);
        detailText = typedArray.getString(R.styleable.BaseCell_detail_text);
        typedArray.recycle();
    }

    public BaseCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    /**
     * 获取辅助View
     *
     * @return ViewGroup
     */
    public ViewGroup getRightView() {
        if (rightView == null) {
            rightView = new RelativeLayout(getContext());
            rightView.setId(R.id.right_text_view);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.rightMargin = 10;
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rightView.setLayoutParams(layoutParams);
        }
        return rightView;
    }

    /**
     * 初始化
     */
    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
        if (contentView == null) {
            contentView = new RelativeLayout(getContext());
            contentView.setId(R.id.content_view);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(ALIGN_PARENT_TOP);
            if (lineView != null) {
                layoutParams.addRule(RelativeLayout.ABOVE, R.id.line_view);
            }
            contentView.setLayoutParams(layoutParams);
        }
        contentView.addView(getImageView());
        contentView.addView(getRightView());
        setCellViewAccessoryType(cellViewAccessoryType);
        if (cellViewStyle == CellViewStyle.CELL_VIEW_STYLE_SUBTITLE) {
            LinearLayout linearLayout = getContentSubText();
            linearLayout.addView(getTextLabel());
            linearLayout.addView(getDetailTextLabel());
            contentView.addView(linearLayout);
        } else {
            contentView.addView(getTextLabel());
            contentView.addView(getDetailTextLabel());
        }
        if (lineView != null)
            addView(lineView);
        addView(contentView);
    }


}
