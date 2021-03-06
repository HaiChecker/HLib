package com.haichecker.lib.widget;

import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-8 11:19
 * <p>
 * 仿ｉＯＳ＿UITableView
 */

public abstract class BaseHeaderAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            update();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    };

    /**
     * 注册数据源改变回调
     */
    public void reg() {
        registerAdapterDataObserver(adapterDataObserver);
    }

    /**
     * 卸载
     */
    public void unreg() {
        unregisterAdapterDataObserver(adapterDataObserver);
    }

    public BaseHeaderAdapter() {
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        update();
        reg();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unreg();
    }

    //空View
    private View emptyView;
    private ArrayList<ArrayList<Integer>> headerList = new ArrayList<>();
    private SparseIntArray sparseIntArray = new SparseIntArray();
    //用于记录所有Item的总数，包括Header
    private int count = 0;

    public static final int HEADER = 2;
    public static final int CONTENT = 1;
    private static final int EMPTY = 3;

    /**
     * 设置空数据布局
     *
     * @param emptyView 空数据View
     */
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        update().notifyDataSetChanged();
    }

    /**
     * 通过资源设置
     *
     * @param emptyLayout 空布局的资源
     * @param viewGroup   他的父布局
     */
    public void setEmptyView(int emptyLayout, ViewGroup viewGroup) {
        this.emptyView = LayoutInflater.from(viewGroup.getContext()).inflate(emptyLayout, viewGroup, false);
        update().notifyDataSetChanged();
    }


    /**
     * 更新Header，如数据改变必须调用此函数
     *
     * @return 返回当前对象用于后续操作
     */
    public BaseHeaderAdapter<VH> update() {
        count = 0;
        sparseIntArray.clear();
        int hi = 0;
        headerList.clear();
        for (int i = 0; i < headerCount(); i++) {
            sparseIntArray.put(hi, i);
            hi++;
            ArrayList<Integer> countList = new ArrayList<>();

            for (int i1 = 0; i1 < count(i); i1++) {
                sparseIntArray.put(hi, i);
                hi++;
                countList.add(i1);
            }
            count += countList.size();
            headerList.add(countList);
        }
        count += headerCount();
        return this;
    }


    /**
     * 获取头部总数
     *
     * @return 头部总数
     */
    public abstract int headerCount();

    /**
     * 获取某个头部的内容个数
     *
     * @param hIndex 头部
     * @return 返回当前头部的个数
     */
    public abstract int count(int hIndex);


    /**
     * 绑定事件
     *
     * @param holder        返回的ViewHolder
     * @param contentIndex  某个Header下的内容索引
     * @param headerIndex   Header的索引
     * @param isHeader      当前位置是否为头部
     * @param countPosition 总共的索引，可以根据他来实现多Item类型
     */
    public abstract void onBindViewHolder(VH holder, int contentIndex, int headerIndex, boolean isHeader, int countPosition);


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY) {
            return (VH) new EmptyViewHodler(emptyView);
        }
        return onBaseCreateViewHolder(parent, viewType);
    }

    /**
     * 由于使用了空布局，需要实现此函数，效果和{@link BaseHeaderAdapter#onCreateViewHolder(ViewGroup, int)}效果一样
     *
     * @param parent   父布局
     * @param viewType viewType
     * @return 返回
     */
    public abstract VH onBaseCreateViewHolder(ViewGroup parent, int viewType);


    @Override
    public int getItemCount() {
        if (isEmpty())
            return 1;
        return count;
    }

    public boolean isEmpty() {
        return count == 0 && emptyView != null;
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty())
            return EMPTY;
        if (isHeader(position))
            return HEADER;
        return CONTENT;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (isEmpty()) {
            //设置空布局为MATCH_PARENT
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return;
        }
        onBindViewHolder(holder, getHeaderContentIndex(position), getHeaderIndex(position), isHeader(position), position);
    }

    /**
     * 判断当前下标是否在头部
     *
     * @param p 当前坐标
     * @return 返回是否为header
     */
    public boolean isHeader(int p) {
        int countP = 0;

        if (p == 0)
            return true;

        for (int i = 1; i < headerCount(); i++) {
            countP += headerList.get(i - 1).size() + 1;
            if (p == countP)
                return true;
        }
        return false;
    }


    /**
     * 获取当前下标的头部索引
     *
     * @param p 当前坐标
     * @return 返回当前下表的头部
     */
    public int getHeaderIndex(int p) {
        return sparseIntArray.get(p);
    }


    /**
     * 通过头部，获取里面的内容下标
     *
     * @param p 当前坐标
     * @return 通过头部，获取里面的内容下标
     */
    private int getHeaderContentIndex(int p) {
        int countP = 0;
        for (int i = 0; i < headerList.size(); i++) {
            for (int j = 0; j < headerList.get(i).size(); j++) {
                countP++;
                if (countP == p - i) {
                    return j;
                }
            }
        }
        return -1;
    }



    /**
     * 空数据ViewHodler
     */
    private class EmptyViewHodler extends RecyclerView.ViewHolder {

        /**
         * 空数据构造函数
         *
         * @param itemView 空数据View
         */
        EmptyViewHodler(View itemView) {
            super(itemView);
        }
    }

}
