package com.haichecker.simple;

import android.os.Bundle;

import com.haichecker.lib.app.BaseActivity;
import com.haichecker.simple.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }
}
