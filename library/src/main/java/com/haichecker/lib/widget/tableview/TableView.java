package com.haichecker.lib.widget.tableview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * createTime 2017/6/22 11:24
 * <p>
 * devUser 石文平
 * <p>
 * classDetail  仿iOS UITableView
 */
public class TableView extends RecyclerView {

    public TableView(Context context) {
        super(context);
        init();
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
