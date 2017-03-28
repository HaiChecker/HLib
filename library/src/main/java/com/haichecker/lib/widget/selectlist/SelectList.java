package com.haichecker.lib.widget.selectlist;

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
    private OnItemViewListener onItemViewClockListener;
    private OnItemLongClickListener onItemLongClickListener;
    private FrameLayout backgundView;
    private Object obj;

    public OnItemViewListener getOnItemViewClockListener() {
        return onItemViewClockListener;
    }

    public void setOnItemViewClockListener(OnItemViewListener onItemViewClockListener) {
        this.onItemViewClockListener = onItemViewClockListener;
    }

    public void setOnItemClickListener(OnItemClickListener c) {
        itemListener = c;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public int getGroupCount() {
        return dataViews.size();
    }

    public SelectList(Context mContext, View root) {
        this.mContext = mContext;
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
        FrameLayout.LayoutParams l = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mContext.getResources().getDisplayMetrics().heightPixels / 2);
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
        window.setHeight(mContext.getResources().getDisplayMetrics().heightPixels);
        window.setWidth(mContext.getResources().getDisplayMetrics().widthPixels);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
    }

    public ViewGroup getRoot() {
        return backgundView;
    }

    public int getCurrentGroupIndex() {
        return pager == null ? -1 : pager.getCurrentItem();
    }

    public void setTitleSelectedTextColor(@ColorInt int color) {
        if (tabLayout != null) {
            tabLayout.setSelectedTabIndicatorColor(color);
        }
    }

    public void show() {
        window.showAtLocation(root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void show(Object obj) {
        this.obj = obj;
        window.showAtLocation(root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void dismiss() {
        window.dismiss();
    }

    public enum State {
        LOADING, SUCCESS, ERROR
    }

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

    public <T extends BaseBeen> void set(String title, ArrayList<T> data, int groupId, boolean isSetCurrentItem) {
        changeTitle(groupId, title);
        change(data, groupId);
        adapter.notifyDataSetChanged();
        if (isSetCurrentItem) {
            pager.setCurrentItem(groupId);
        }
    }

    public void del(int groupId) {

        int count = dataViews.size() - groupId;

        Log.d(this.getClass().getSimpleName(), "循环次数：" + count);
        pager.setCurrentItem(groupId - 1);
        for (int i = 0; i < count; i++) {
            dataViews.remove(groupId);
            dataViewAdapters.get(groupId).clear();
            dataViewAdapters.remove(groupId);
            titles.remove(groupId);
        }
//        for (int i = groupId; i < dataViewAdapters.size() - groupId; i++) {
//            dataViewAdapters.remove(groupId);
//        }
//
//        for (int i = groupId; i < titles.size() - groupId; i++) {
//            titles.remove(groupId);
//        }
        adapter.notifyDataSetChanged();
    }

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

    public void reAdd(String title, A adapter) {
        clear();
//        del(0);
//        for (String s : data) {
//            Log.d(s,s);
//        }
        add(title, adapter, null);
    }

    public void add(String title, A listAdapter) {
        add(title, listAdapter, null);
    }
    //@373399
//    public void add(String title, ArrayList<String> data) {
//        add(title, data, null);
//    }

    //@373399
    public <L extends ListView> void add(String title, A listAdapter, L a) {
        Preconditions.checkNotNull(title);
        titles.add(title);

        if (a == null) {
            a = (L) new ListView(getContext());
        }

        final ListView listView = a;

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

    public void changeTitle(int groupIndex, String title) {
        titles.set(groupIndex, title);
    }

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

    public A getAdapter(int groupIndex) {
        return dataViewAdapters.get(groupIndex);
    }

//    private class DataViewAdapter extends BaseAdapter {
//        public void clear() {
//            data = null;
//            notifyDataSetChanged();
//        }
//
//        private ArrayList<String> data;
//
//        public void update(ArrayList<String> data) {
//            this.data = data;
//            notifyDataSetChanged();
//        }
//
//        public DataViewAdapter(ArrayList<String> data) {
//            this.data = data;
//        }
//
//        @Override
//        public int getCount() {
//            return data == null ? 0 : data.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return data.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            Hodler hodler = null;
//            if (view == null) {
//                TextView textView = new TextView(getContext());
//                FrameLayout.LayoutParams l = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//                l.leftMargin = 10;
//                textView.setLayoutParams(l);
//                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.test_back_333));
//                textView.setTextSize(24);
//                textView.setGravity(Gravity.CENTER_VERTICAL);
//                FrameLayout frameLayout = new FrameLayout(getContext());
//                frameLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
//                frameLayout.addView(textView);
//                hodler = new Hodler();
//                hodler.textView = textView;
//                hodler.frameLayout = frameLayout;
//                view = frameLayout;
//                view.setTag(hodler);
//            } else {
//                hodler = (Hodler) view.getTag();
//            }
//            hodler.textView.setText(data.get(i));
//            Log.d(data.get(i), data.get(i));
//            return view;
//        }
//
//        private class Hodler {
//            TextView textView;
//            FrameLayout frameLayout;
//        }
//    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return dataViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            if (dataViews.size() == 0) {
//                ((ViewPager) container).removeAllViews();
//            } else {
//                ((ViewPager) container).removeView(dataViews.get(position % dataViews.size()));
//            }
//        }

        @Override
        public int getItemPosition(Object object) {
//            return null != dataViews && dataViews.size() == 0 ? POSITION_NONE : super.getItemPosition(object);
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(dataViews.get(position % dataViews.size()));
            return dataViews.get(position % dataViews.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (dataViews.size() == 0)
                return;
            if (position == 0)
                container.removeView(dataViews.get(position));
            else
                container.removeView(dataViews.get(position % dataViews.size()));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position % dataViews.size());
        }
    }
}


