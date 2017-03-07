package com.haichecker.simple;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.haichecker.lib.app.BaseActivity;
import com.haichecker.lib.app.actionBar.DefNavigation;
import com.haichecker.lib.databinding.NavigationDefaultBinding;
import com.haichecker.lib.widget.tools.HTextColorList;
import com.haichecker.simple.databinding.ActivityMainBinding;
import com.haichecker.simple.v.fragment.BlankFragment;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DefNavigation.Builder<NavigationDefaultBinding>
//        databinding.Button.setTextColor(new HTextColorList() {
//            @Override
//            protected void setColorList() {
//                addColor(new int[]{android.R.attr.state_pressed}, R.color.colorPrimary);
//                addColor(new int[]{}, R.color.colorAccent);
//                Log.d("wwww", "add");
//            }
//        }.create());
        databinding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
    }

    class FragmentAdapter extends FragmentPagerAdapter {


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new BlankFragment();
        }

        @Override
        public int getCount() {
            return 5;
        }

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }
}
