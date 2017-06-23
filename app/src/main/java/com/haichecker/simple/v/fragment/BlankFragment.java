package com.haichecker.simple.v.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haichecker.lib.app.BaseFragment;
import com.haichecker.lib.widget.tableview.CellViewHolder;
import com.haichecker.lib.widget.tableview.TableView;
import com.haichecker.lib.widget.tableview.TableViewAdapter;
import com.haichecker.lib.widget.tableview.item.BaseCell;
import com.haichecker.lib.widget.tableview.item.CellViewStyle;
import com.haichecker.simple.R;
import com.haichecker.simple.databinding.FragmentBlankBinding;

public class BlankFragment extends BaseFragment<FragmentBlankBinding> {

    public BlankFragment() {
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding.tab.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.tab.setAdapter(new TableViewAdapter<BaseCell>(fragmentBinding.tab) {
            @Override
            public void cell(BaseCell cell, int countIndex, int headerIndex, boolean isHeader) {
                if (!isHeader) {
                    cell.getTextLabel().setText("" + (countIndex + 1));
                    cell.setBackgroundColor(Color.parseColor("#ffffff"));
                } else {
                    cell.getTextLabel().setText("" + (headerIndex + 1));
                    cell.setBackgroundColor(Color.parseColor("#b0b0b0"));
                }
            }

            @Override
            public int headerCount() {
                return 3;
            }

            @Override
            public int count(int hIndex) {
                switch (hIndex) {
                    case 0:
                        return 4;
                    case 1:
                        return 2;
                    case 2:
                        return 7;
                }
                return 5;
            }


            @Override
            public BaseCell onCreateCell(TableView tableView, int viewType) {
                return new BaseCell(getContext(), CellViewStyle.CELL_VIEW_STYLE_DEFAULT);
            }

            @Override
            public int headerHeight(TableView tableView, int headerIndex) {
                return 20;
            }

            @Override
            public int heightCell(TableView tableView, int headerIndex, int contentIndex) {
                return 100;
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_blank;
    }

}
