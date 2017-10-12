package com.haichecker.lib.widget.tableview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.haichecker.lib.widget.tableview.item.BaseCell;

/**
 * createTime 2017/6/23 10:15
 * <p>
 * devUser 石文平
 * <p>
 * classDetail  ViewHolder
 */
public class CellViewHolder<T extends BaseCell> extends RecyclerView.ViewHolder {
    private T baseCell;

    public CellViewHolder(T baseCell) {
        super(baseCell);
        this.baseCell = baseCell;
    }

    public T getBaseCell() {
        return baseCell;
    }
}
