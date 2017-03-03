package com.haichecker.lib.app.actionBar;

import android.support.v7.app.AlertDialog;

import com.haichecker.lib.R;
import com.haichecker.lib.databinding.NavigationDefaultBinding;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-2 14:37
 */

public class DefNavigation extends AbsNavigation<NavigationDefaultBinding, AbsNavigation.Builder.NavigationParams> {

    public DefNavigation(Builder.NavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.navigation_default;
    }


    public static class Builder extends AbsNavigation.Builder {
        private NavigationParams params;


        @Override
        public AbsNavigation create() {
            return null;
        }
    }

}
