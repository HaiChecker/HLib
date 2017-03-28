package com.haichecker.simple;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.haichecker.lib.app.BaseActivity;
import com.haichecker.lib.widget.viewtoast.ViewToast;
import com.haichecker.simple.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<ActivityMainBinding> {
    ViewToast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = ViewToast.createDefalut(this, (ViewGroup) databinding.getRoot());
        databinding.viewToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.setText("测试")
                        .showProgress()
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (toast.isShowing()) {
            toast.hide();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }
}
