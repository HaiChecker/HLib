package com.haichecker.lib.widget.selectlist;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.haichecker.lib.R;
import com.haichecker.lib.utils.DensityUtil;
import com.haichecker.lib.widget.selectlist.adapter.BaseSelectAdapter;
import com.haichecker.lib.widget.selectlist.been.BaseBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-16 16:28
 */

public class SelectList<A extends BaseSelectAdapter> {
    private LinearLayout rootView;
    private Context mContext;
    private ViewPager pager;
    private TabLayout tabLayout;
    private ArrayList<FrameLayout> dataViews;
    private ArrayList<A> dataViewAdapters;
    private ArrayList<String> titles;
    private ViewPagerAdapter adapter;
    private PopupWindow window;
    private View root;
    private OnItemClickListener itemListener;
    private OnItemViewListener onItemViewClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private FrameLayout backgundView;
    private Object obj;
    private int heightPixels;

    /**
     * 内容item单击事件
     *
     * @param onItemViewClockListener
     */
    public void setOnItemViewClickListener(OnItemViewListener onItemViewClockListener) {
        this.onItemViewClickListener = onItemViewClockListener;
    }

    public OnItemViewListener getOnItemViewClickListener() {
        return onItemViewClickListener;
    }

    /**
     * 单击事件
     *
     * @param c
     */
    public void setOnItemClickListener(OnItemClickListener c) {
        itemListener = c;
    }

    /**
     * 长按事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 获取顶部
     *
     * @return TabLayout
     */
    public TabLayout getTabLayout() {
        return tabLayout;
    }

    /**
     * 获取选项总数
     *
     * @return 选项总数
     */
    public int getGroupCount() {
        return dataViews.size();
    }

    /**
     * 初始化函数
     *
     * @param mContext 上下文
     * @param root     最大父View
     */
    public SelectList(Context mContext, View root) {
        this.mContext = mContext;
        heightPixels = mContext.getResources().getDisplayMetrics().heightPixels;
        this.root = root;
        dataViews = new ArrayList<>();
        titles = new ArrayList<>();
        dataViewAdapters = new ArrayList<>();
        backgundView = new FrameLayout(getContext());
        backgundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        backgundView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        backgundView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.test_back_bantou));
        rootView = new LinearLayout(getContext());
        rootView.setVisibility(View.GONE);
        FrameLayout.LayoutParams l = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, heightPixels / 2);
        l.gravity = Gravity.BOTTOM;
        rootView.setLayoutParams(l);
        tabLayout = new TabLayout(getContext());
        adapter = new ViewPagerAdapter();

        View line = new View(getContext());
        line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        tabLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip_px(40, mContext.getResources().getDisplayMetrics().density)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tabLayout.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.test_b00000), ContextCompat.getColor(getContext(), R.color.colorPrimary));
        rootView.addView(tabLayout);
        rootView.addView(line);
        pager = new ViewPager(getContext());
        pager.setAdapter(adapter);
        pager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.addView(pager);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.test_back));
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        backgundView.addView(rootView);
        window = new PopupWindow(backgundView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(heightPixels);
        window.setWidth(mContext.getResources().getDisplayMetrics().widthPixels);
//        window.setAnimationStyle(R.style.mypopwindow_anim_style);
    }

    /**
     * 获取root
     *
     * @return root
     */
    public ViewGroup getRoot() {
        return backgundView;
    }

    /**
     * 获取当前选项的Index
     *
     * @return index
     */
    public int getCurrentGroupIndex() {
        return pager == null ? -1 : pager.getCurrentItem();
    }

    /**
     * 设置标题颜色
     *
     * @param color 颜色
     */
    public void setTitleSelectedTextColor(@ColorInt int color) {
        if (tabLayout != null) {
            tabLayout.setSelectedTabIndicatorColor(color);
        }
    }

    /**
     * 显示
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void show() {
        window.showAtLocation(root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofArgb(backgundView, "backgroundColor", 0x00000000, 0x55000000);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        animatorSet.play(objectAnimator);

        ObjectAnimator rootViewAnim = ObjectAnimator.ofFloat(rootView, "Y", heightPixels, heightPixels / 2 - DensityUtil.dip_px(21, mContext.getResources().getDisplayMetrics().density));
        animatorSet.play(rootViewAnim);
        animatorSet.setDuration(300);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                rootView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    /**
     * 显示
     *
     * @param obj 可用于多个不同种类的Adapter做判断
     */
    public void show(Object obj) {
        this.obj = obj;
        show();
    }

    /**
     * 关闭
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void dismiss() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofArgb(backgundView, "backgroundColor", 0x55000000, 0x00000000);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        animatorSet.play(objectAnimator);


        ObjectAnimator rootViewAnim = ObjectAnimator.ofFloat(rootView, "Y", heightPixels / 2 - DensityUtil.dip_px(21, mContext.getResources().getDisplayMetrics().density), heightPixels);
        animatorSet.play(rootViewAnim);
        animatorSet.setDuration(500);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                rootView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rootView.setVisibility(View.GONE);
                window.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                rootView.setVisibility(View.GONE);
                window.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    /**
     * 当前状态
     */
    public enum State {
        LOADING, SUCCESS, ERROR
    }

    /**
     * 修改状态，如果数据是网络请求的，建议启用状态
     *
     * @param groupIndex index
     * @param s          状态 来自{@link State}
     */
    public void changeState(int groupIndex, State s) {
        dataViews.get(groupIndex).findViewById(android.R.id.progress).setVisibility(s == State.LOADING ? View.VISIBLE : View.GONE);
        dataViews.get(groupIndex).findViewById(android.R.id.list).setVisibility(s == State.SUCCESS ? View.VISIBLE : View.GONE);
        dataViews.get(groupIndex).findViewById(android.R.id.text1).setVisibility(s == State.ERROR ? View.VISIBLE : View.GONE);
    }

    /**
     * <p>修改错误文字</p>
     *
     * @param groupIndex 项Id
     * @param str        文字
     */
    public void changeErrMsg(int groupIndex, String str) {
        ((TextView) dataViews.get(groupIndex).findViewById(android.R.id.text1)).setText(str);
    }

    /**
     * 设置
     *
     * @param title
     * @param data
     * @param groupId
     * @param isSetCurrentItem
     * @param <T>
     */
    public <T extends BaseBeen> void set(String title, ArrayList<T> data, int groupId, boolean isSetCurrentItem) {
        changeTitle(groupId, title);
        change(data, groupId);
        adapter.notifyDataSetChanged();
        if (isSetCurrentItem) {
            pager.setCurrentItem(groupId);
        }
    }

    /**
     * 删除Index后面的选项卡，包括当前
     *
     * @param groupId Index
     */
    public void del(int groupId) {

        int count = dataViews.size() - groupId;

        pager.setCurrentItem(groupId - 1);
        for (int i = 0; i < count; i++) {
            dataViews.remove(groupId);
            dataViewAdapters.get(groupId).clear();
            dataViewAdapters.remove(groupId);
            titles.remove(groupId);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 清空
     */
    private void clear() {
        if (dataViewAdapters != null) {
            for (A dataViewAdapter : dataViewAdapters) {
                dataViewAdapter.clear();
            }
            dataViewAdapters.clear();
        }

        if (dataViews != null) {
            dataViews.clear();
        }
        if (titles != null) {
            titles.clear();
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 删掉所有选项，重新加载
     *
     * @param title   标题
     * @param adapter Adapter
     */
    public void reAdd(String title, A adapter) {
        clear();
        add(title, adapter, null);
    }

    /**
     * 追加一个选项
     *
     * @param title       标题
     * @param listAdapter Adapter
     */
    public void add(String title, A listAdapter) {
        add(title, listAdapter, null);
    }

    /**
     * 追加一个选项
     *
     * @param title       标题
     * @param listAdapter Adapter
     * @param listview    自定义的ListView
     * @param <L>         基层至 {@link ListView}
     */
    public <L extends ListView> void add(String title, A listAdapter, L listview) {
        Preconditions.checkNotNull(title);
        titles.add(title);

        if (listview == null) {
            listview = (L) new ListView(getContext());
        }

        final ListView listView = listview;

        listView.setTag(dataViews.size());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemListener.onItem(Integer.parseInt(listView.getTag().toString()), i, listView, obj);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(Integer.parseInt(listView.getTag().toString()), i, listView, view, obj);
                }
                return true;
            }
        });
        listView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setId(android.R.id.list);
        listAdapter.setGroupIndex(dataViews.size());
        listAdapter.setList(this);
        listAdapter.setListView(listView);
        listView.setAdapter(listAdapter);
        dataViewAdapters.add(listAdapter);
        ProgressBar pb = new ProgressBar(getContext());
        pb.setId(android.R.id.progress);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        pb.setLayoutParams(lp);
        pb.setVisibility(View.GONE);
        FrameLayout rootLayout = new FrameLayout(getContext());
        rootLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView errorMsg = new TextView(getContext());
        errorMsg.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        errorMsg.setGravity(Gravity.CENTER);
        errorMsg.setVisibility(View.GONE);
        errorMsg.setId(android.R.id.text1);
        rootLayout.addView(listView);
        rootLayout.addView(pb);
        rootLayout.addView(errorMsg);
        dataViews.add(rootLayout);
        adapter.notifyDataSetChanged();
        pager.setCurrentItem(adapter.getCount() - 1);
    }

    public Context getContext() {
        return mContext;
    }

    public interface OnItemClickListener {
        public void onItem(int groupIndex, int pIndex, ListView list, Object obj);
    }

    public interface OnItemLongClickListener {
        public void onLongClick(int groupIndex, int pIndex, ListView l, View view, Object obj);
    }

    public interface OnItemViewListener {
        public void onClick(int groupIndex, int pIndex, View view, Object obj);

        public void onLongClick(int groupIndex, int pIndex, View view, Object obj);
    }

    /**
     * 修改标题
     *
     * @param groupIndex 　index
     * @param title      标题内容
     */
    public void changeTitle(int groupIndex, String title) {
        titles.set(groupIndex, title);
    }

    /**
     * 刷新当前ViewPager
     */
    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public <T extends BaseBeen> void change(List<T> data, int groupIndex) {
        if (dataViewAdapters.size() < groupIndex + 1)
            throw new IndexOutOfBoundsException("adapter list outOfBounds");
        getAdapter(groupIndex).update(data);
    }

    /**
     * 获取所有adapter
     *
     * @return 返回所有的adapter
     */
    public ArrayList<A> getAdapters() {
        return dataViewAdapters;
    }


    /**
     * 不被建议使用，建议使用 {@link SelectList#getAdapters()}
     *
     * @param groupIndex 当前的下标
     * @return 返回当前Adapter
     */
    @Deprecated
    public A getAdapter(int groupIndex) {
        if (dataViewAdapters.size() == 0)
            return null;
        return dataViewAdapters.get(groupIndex);
    }


    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return dataViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(dataViews.get(position));
            return dataViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            if (dataViews.size() == 0)
//                return;
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}


