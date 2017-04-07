package com.haichecker.lib.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.google.common.base.Preconditions;
import com.haichecker.lib.setting.SettingInstance;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * @param <T> DataBinding对象，需要与layout对应
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements View.OnTouchListener {

    /**
     * 标题
     */
    @NotNull
    private String title;

    /**
     * 详情
     */
    private String subTitle;
    /**
     * databinding 对象，采用泛型继承 ViewDataBinding
     */
    public T databinding;
    /**
     * 监听对象
     */
    private SharedPreferences sharedPreferences;
    //--------------------------------------右滑关闭--------------------------------------------------
    /**
     * 记录右滑关闭，当前时间
     */
    private long currtime;

    /**
     * 右滑关闭功能，记录当前x/y轴
     */
    private float x, y;
    /**
     * 右滑关闭功能，用于记录是否移动
     */
    private boolean isMove = false;

    /**
     * 是否开启右滑关闭功能
     */
    private boolean isOpenRightClose = true;
    /**
     * Y轴的最大差值
     */
    private final int MAX_Y = 100;

    /**
     * 延迟时间
     */
    private final int DE_TIME = 500;
    //--------------------------------------右滑关闭--------------------------------------------------


    //--------------------------------------空布局----------------------------------------------------

    /**
     * 空布局View(默认id为android.R.id.empty)
     */
    private View emptyView;

    /**
     * 空布局Id
     */
    private int emptyId = android.R.id.empty;

    /**
     * 标题
     */
    private ActionBar actionBar;

    private ViewGroup contentView;

    private View decorView;

    private SystemBarTintManager tintManager;

//    @TargetApi(19)
//    protected void setTranslucentStatus(boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //用于处理横屏切换导致
        if (savedInstanceState != null) {
            savedInstanceState = Preconditions.checkNotNull(savedInstanceState);
        } else {
            //设置布局
            databinding = DataBindingUtil.setContentView(this, getLayoutRes());
            //获取最顶层View
            decorView = getWindow().getDecorView();
            //获取ActionBar
            actionBar = getSupportActionBar();
            //设置顶层View的Touch
            decorView.setOnTouchListener(this);
            //获取内容View
            contentView = (ViewGroup) decorView.findViewById(android.R.id.content);
            //判断是否开启
            if (isOpenSharePreferenceChangeListener()) {
                sharedPreferences = SettingInstance.getInstance(this).getSharedPreferences();
            }

            super.onCreate(null);
        }

        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);

        // 自定义颜色
//        tintManager.setTintColor(Color.parseColor("#218be9"));
        actionBar = getSupportActionBar();

        ((BaseApplication) getApplication()).getAllActivity().add(this);
    }


    protected void setTintColor(int color) {
        // TODO 这儿有问题需要解决，白色问题
        // 4.4及以上版本开启
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }

        if (tintManager != null) {
            tintManager.setTintColor(color);
        }
    }

    @Override
    protected void onStart() {
        if (isOpenSharePreferenceChangeListener()) {
            sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (isOpenSharePreferenceChangeListener()) {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        }
        super.onDestroy();
        ((BaseApplication) getApplication()).getAllActivity().remove(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        if (options == null) {
            //TODO:添加跳转动画，待续
            super.startActivity(intent, null);
        } else {
            super.startActivity(intent, options);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (isOpenRightClose) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                currtime = System.currentTimeMillis();
                x = event.getX();
                y = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                isMove = !(event.getY() - y > MAX_Y || event.getY() - y < -MAX_Y);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (isMove) {
                    long tempTime = System.currentTimeMillis();
                    if (x < event.getX() && tempTime - currtime < DE_TIME) {
                        finish();
                        isMove = false;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
    }

    ////////////////////////////////自定义函数////////////////////////////////

    /**
     * <p>是否开启监听(默认关闭)</p>
     *
     * @return 返回是否打开  true-开启 flase-关闭
     */
    public boolean isOpenSharePreferenceChangeListener() {
        return false;
    }

    /**
     * <p>收到监听的消息变化</p>
     *
     * @param key               变化的Key值
     * @param sharedPreferences 对象，可以直接用这个对象取出value
     */
    public void onSharedPreferenceChangeds(String key, SharedPreferences sharedPreferences) {

    }

    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            onSharedPreferenceChangeds(key, sharedPreferences);
        }
    };

    ////////////////////////////////自定义抽象////////////////////////////////

    /**
     * <p>用于获取layout 资源Id</p>
     *
     * @return 返回资源ID 用于设置布局   -需要和DataBinding对应Layout
     */
    @LayoutRes
    public abstract int getLayoutRes();

    ////////////////////////////////get-set函数////////////////////////////////

    @Override
    public void setTitle(@Nullable CharSequence title) {
        Preconditions.checkNotNull(title);
        this.title = title.toString();
        actionBar.setTitle(title);
    }

    @Override
    public void setTitle(@StringRes int titleId) {
        this.title = getString(titleId);
        actionBar.setTitle(titleId);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
        actionBar.setTitle(title);
    }

    /**
     * 设置子标题
     *
     * @param subTitle 子标题
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        actionBar.setSubtitle(subTitle);
    }

    /**
     * <p>设置是否开启右滑关闭（默认开启）</p>
     *
     * @param isOpenRightClose 是否开启右滑关闭     true-开启  flase-没有开启
     */
    public void setOpenRightClose(boolean isOpenRightClose) {
        this.isOpenRightClose = isOpenRightClose;
    }

    /**
     * <p>设置状态布局</p>
     *
     * @param emptyView 通过View设置空布局
     */
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        setEmptyViewId(emptyView);
    }

    /**
     * 设置状态布局Id
     *
     * @param emptyView 状态布局
     */
    private void setEmptyViewId(View emptyView) {
        if (emptyView.getId() == 0) {
            emptyView.setId(emptyId);
        } else {
            emptyId = emptyView.getId();
        }
    }

    /**
     * <p>设置状态布局</p>
     *
     * @param layoutId <h1>设置资源Id状态布局</h1>
     */
    public void setEmptyView(@LayoutRes int layoutId) {
        View tempEmptyView = LayoutInflater.from(this).inflate(layoutId, null);
        setEmptyView(tempEmptyView);
    }

    /**
     * 获取状态布局
     *
     * @return 返回当前布局
     */
    public View getEmptyView() {
        return this.emptyView;
    }

    /**
     * <p>开启状态布局</p>
     */
    public void openEmptyView() {
        contentView.addView(emptyView, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        for (int i = 0; i < contentView.getChildCount(); i++) {
            View v = contentView.getChildAt(i);
            if (!v.equals(emptyView)) {
                v.setVisibility(View.GONE);
            }
        }
        //把布局移动到顶部
        emptyView.bringToFront();
    }

    /**
     * <p>关闭状态布局</p>
     */
    public void dismissEmptyView() {
        contentView.removeView(emptyView);
        for (int i = 0; i < contentView.getChildCount(); i++) {
            View v = contentView.getChildAt(i);
            v.setVisibility(View.VISIBLE);
        }
    }

    /**
     * <p>判断是否显示状态布局中...</p>
     *
     * @return 返回状态布局是否现实  true-显示  false-没有现实
     */
    public boolean isShowingEmptyView() {
        View v = contentView.findViewById(emptyId);
        return (v != null && v.getVisibility() == View.VISIBLE);
    }


}
