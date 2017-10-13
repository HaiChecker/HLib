package com.haichecker.lib.widget.selectlist.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.common.base.Preconditions;
import com.haichecker.lib.widget.selectlist.SelectList;
import com.haichecker.lib.widget.selectlist.SelectListValueInterface;
import com.haichecker.lib.widget.selectlist.been.BaseBeen;

import java.util.List;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-18 19:54
 */

public abstract class BaseSelectAdapter<T> extends BaseAdapter {
    private Context mContext;
    protected List<? extends SelectListValueInterface> data;
    protected LayoutInflater inflater;
    protected ListView listView;

    protected int groupIndex;

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }


    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @LayoutRes
    protected int layout;

    public BaseSelectAdapter(Context mContext, List<? extends SelectListValueInterface> data, @LayoutRes int layout) {
        this.mContext = mContext;
        this.data = data;
        this.layout = layout;
        inflater = LayoutInflater.from(mContext);
    }

    public BaseSelectAdapter(Context mContext, @LayoutRes int layout) {
        this.mContext = mContext;
        this.layout = layout;
        inflater = LayoutInflater.from(mContext);
    }

    public List<? extends SelectListValueInterface> getData() {
        return data;
    }

    public void clear() {
        data = null;
        notifyDataSetChanged();
    }

    public void update(List<? extends SelectListValueInterface> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
