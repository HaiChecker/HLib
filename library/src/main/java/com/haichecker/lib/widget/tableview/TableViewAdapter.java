package com.haichecker.lib.widget.tableview;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.haichecker.lib.widget.BaseHeaderAdapter;
import com.haichecker.lib.widget.tableview.item.BaseCell;
import com.haichecker.lib.widget.tableview.item.CellViewStyle;

/**
 * createTime 2017/6/23 10:14
 * <p>
 * devUser 石文平
 * <p>
 * classDetail
 */
public abstract class TableViewAdapter<T extends BaseCell> extends BaseHeaderAdapter<CellViewHolder<T>> {

    private TableView tableView;

    public TableViewAdapter(TableView tableView) {
        super();
        this.tableView = tableView;
    }

    @Override
    public void onBindViewHolder(CellViewHolder<T> holder, final int contentIndex, final int headerIndex, final boolean isHeader, int countPosition) {
        holder.getBaseCell().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectItem(headerIndex, contentIndex, isHeader, (T) view);
            }
        });
        if (isHeader) {
            holder.getBaseCell().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight(tableView, headerIndex)));
        } else {
            holder.getBaseCell().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightCell(tableView, headerIndex, contentIndex)));
        }
        //设置Cell
        cell(holder.getBaseCell(), contentIndex, headerIndex, isHeader);
        //设置线条
        holder.getBaseCell().setLineView(lineView(tableView, headerIndex, contentIndex));
    }


    public abstract void cell(T cell, int countIndex, int headerIndex, boolean isHeader);

    @Override
    public CellViewHolder<T> onBaseCreateViewHolder(ViewGroup parent, int viewType) {
        CellViewHolder<T> cellViewHolder = null;
        if (viewType == HEADER) {
            cellViewHolder = new CellViewHolder<>(onCreateHeader(tableView, viewType));
        } else {
            cellViewHolder = new CellViewHolder<>(onCreateCell(tableView, viewType));
        }
        return cellViewHolder;
    }

    /**
     * 创建内容Cell
     *
     * @param tableView TableView
     *                  //     * @param headerIndex  头部索引
     *                  //     * @param contentIndex 内容索引
     * @return 返回Cell
     */
    public abstract T onCreateCell(TableView tableView, int viewType);

    /**
     * 创建自定义的Header
     *
     * @param tableView tableView
     * @param viewType  头部索引
     * @return 返回自定义的Header View
     */
    public T onCreateHeader(TableView tableView, int viewType) {
        return (T) new BaseCell(tableView.getContext(), CellViewStyle.CELL_VIEW_STYLE_DEFAULT);
    }

    /**
     * 返回每一行的高度
     *
     * @param tableView    tableview
     * @param headerIndex  头部索引
     * @param contentIndex 内容索引
     * @return 返回Cell的高度
     */
    public int heightCell(TableView tableView, int headerIndex, int contentIndex) {
        return 100;
    }

    /**
     * 设置线条View
     *
     * @param tableView    tab
     * @param headerIndex  头部索引
     * @param contentIndex 内容索引
     * @return 返回线条
     */
    public View lineView(TableView tableView, int headerIndex, int contentIndex) {
        View view = new View(tableView.getContext());
        RelativeLayout.LayoutParams linep = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        linep.leftMargin = 10;
        view.setLayoutParams(linep);
        view.setBackgroundColor(Color.parseColor("#b0b0b0"));
        return view;
    }

    /**
     * 选中事件
     *
     * @param headerIndex  头部索引
     * @param contentIndex 内容索引
     * @param isHeader     是否为Header
     * @param cell         Item对象
     */
    public void selectItem(int headerIndex, int contentIndex, boolean isHeader, T cell) {

    }

    /**
     * 每个header的高度
     *
     * @param tableView   tableView
     * @param headerIndex 头部索引
     * @return 返回header的高度
     */
    public int headerHeight(TableView tableView, int headerIndex) {
        return 50;
    }
}
