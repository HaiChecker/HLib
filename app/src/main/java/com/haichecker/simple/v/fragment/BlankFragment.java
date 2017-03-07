package com.haichecker.simple.v.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haichecker.lib.app.BaseFragment;
import com.haichecker.simple.R;
import com.haichecker.simple.databinding.FragmentBlankBinding;

public class BlankFragment extends BaseFragment<FragmentBlankBinding> {

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_blank;
    }

}
