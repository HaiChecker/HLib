package com.haichecker.lib.widget;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-8 11:19
 */

public abstract class BaseHeaderAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


    private ArrayList<ArrayList<Integer>> headerList = new ArrayList<>();
    private HashMap<Integer, Integer> headerIndexHash = new HashMap<>();
    private int count = 0;

    public static final int HEADER = 2;
    public static final int CONTENT = 1;


    public BaseHeaderAdapter<VH> update() {
        count = 0;
        headerIndexHash.clear();
        int hi = 0;
        headerList.clear();
        for (int i = 0; i < headerCount(); i++) {
            headerIndexHash.put(hi, i);
            hi++;
            ArrayList<Integer> countList = new ArrayList<>();

            for (int i1 = 0; i1 < count(i); i1++) {
                headerIndexHash.put(hi, i);
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
    public int getItemCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position))
            return HEADER;
        return CONTENT;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
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
     * @return
     */
    public int getHeaderIndex(int p) {
        return headerIndexHash.get(p);
    }


    /**
     * 通过头部，获取里面的内容下标
     *
     * @param p
     * @return
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
}
