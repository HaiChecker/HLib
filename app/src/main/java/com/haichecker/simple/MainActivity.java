package com.haichecker.simple;

import android.os.Bundle;
import android.util.Log;

import com.haichecker.lib.app.BaseActivity;
import com.haichecker.lib.widget.tools.HTextColorList;
import com.haichecker.simple.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        databinding.Button.setTextColor(new HTextColorList() {
//            @Override
//            protected void setColorList() {
//                addColor(new int[]{android.R.attr.state_pressed}, R.color.colorPrimary);
//                addColor(new int[]{}, R.color.colorAccent);
//                Log.d("wwww", "add");
//            }
//        }.create());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }
}
