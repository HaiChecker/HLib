package com.haichecker.simple.v.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.haichecker.lib.app.BaseActivity;
import com.haichecker.lib.widget.tableview.TableView;
import com.haichecker.lib.widget.tableview.TableViewAdapter;
import com.haichecker.lib.widget.tableview.item.BaseCell;
import com.haichecker.lib.widget.tableview.item.CellViewStyle;
import com.haichecker.simple.R;
import com.haichecker.simple.databinding.ActivityTableViewTestBinding;

public class TableViewTestActivity extends BaseActivity<ActivityTableViewTestBinding> {

    public static void startTableViewTest(Context mContext) {
        mContext.startActivity(new Intent(mContext, TableViewTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TableViewAdapter<BaseCell> cellTableViewAdapter = new TableViewAdapter<BaseCell>(databinding.tab) {
            @Override
            public void cell(BaseCell cell, int countIndex, int headerIndex, boolean isHeader) {
                if (isHeader) {
                    cell.removeAllViews();
                } else {
                    cell.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }

            @Override
            public BaseCell onCreateCell(TableView tableView, int viewType) {
                return new BaseCell(getContext(), CellViewStyle.CELL_VIEW_STYLE_DEFAULT);
            }

            @Override
            public BaseCell onCreateHeader(TableView tableView, int viewType) {
                return new BaseCell(getContext(), CellViewStyle.CELL_VIEW_STYLE_DEFAULT);
            }


            @Override
            public View lineView(TableView tableView, int headerIndex, int contentIndex) {
                if (contentIndex == -1) {
                    return null;
                } else {
                    return super.lineView(tableView, headerIndex, contentIndex);
                }
            }

            @Override
            public int heightCell(TableView tableView, int headerIndex, int contentIndex) {
                return 120;
            }

            @Override
            public void selectItem(int headerIndex, int contentIndex, boolean isHeader, BaseCell cell) {
                super.selectItem(headerIndex, contentIndex, isHeader, cell);
                cell.getTextLabel().setText("点击：" + contentIndex);
            }

            @Override
            public int headerHeight(TableView tableView, int headerIndex) {
                return 20;
            }

            @Override
            public int headerCount() {
                return 4;
            }

            @Override
            public int count(int hIndex) {
                switch (hIndex) {
                    case 0:
                        return 4;
                    case 1:
                        return 2;
                    case 2:
                        return 5;
                    case 3:
                        return 3;
                }
                return 5;
            }

        };
        databinding.tab.setAdapter(cellTableViewAdapter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_table_view_test;
    }
}
