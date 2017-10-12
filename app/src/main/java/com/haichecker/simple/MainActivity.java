package com.haichecker.simple;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haichecker.lib.app.BaseActivity;
import com.haichecker.lib.widget.BaseHeaderAdapter;
import com.haichecker.lib.widget.ViewScroll;
import com.haichecker.lib.widget.dialog.hdialog.HDialog;
import com.haichecker.lib.widget.dialog.hdialog.HDialogAdapter;
import com.haichecker.lib.widget.dialog.hdialog.OnHDialogListener;
import com.haichecker.lib.widget.selectlist.SelectList;
import com.haichecker.lib.widget.selectlist.adapter.BaseSelectAdapter;
import com.haichecker.lib.widget.selectlist.been.BaseBeen;
import com.haichecker.lib.widget.viewtoast.DToast;
import com.haichecker.lib.widget.viewtoast.DetalutToast;
import com.haichecker.lib.widget.viewtoast.Style;
import com.haichecker.lib.widget.viewtoast.Toasts;
import com.haichecker.lib.widget.viewtoast.ViewToast;
import com.haichecker.simple.databinding.ActivityMainBinding;
import com.haichecker.simple.v.activity.TableViewTestActivity;
import com.haichecker.simple.test.selectlist_test.SelectListTestActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatPreferenceActivity {
    private Toasts detalutToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detalutToast = Toasts.create(this, new DToast(this));
        addPreferencesFromResource(R.xml.main);


        findPreference("select_list").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SelectListTestActivity.start(MainActivity.this);
                return false;
            }
        });
//        findPreference("select_list").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                TableViewTestActivity.startTableViewTest(MainActivity.this);
//                return false;
//            }
//        });
//
//        detalutToast.setStyle(Style.STYLE_PROGRESS_CIR);
//        detalutToast.show(1000, null);

//        R.layout.activity_main

//        BaseHeaderAdapter headerAdapter = new BaseHeaderAdapter() {
//            @Override
//            public int headerCount() {
//                return 0;
//            }
//
//            @Override
//            public int count(int hIndex) {
//                return 0;
//            }
//
//
//            @Override
//            public void onBindViewHolder(RecyclerView.ViewHolder holder, int contentIndex, int headerIndex, boolean isHeader, int countPosition) {
//
//            }
//        };
//
//
//        selectAdapterSelectList.add("请选择", new BaseSelectAdapter<BaseBeen>(this, R.layout.select_list_item) {
//            @SuppressLint("ViewHolder")
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                return inflater.inflate(layout, viewGroup, false);
//            }
//        });


    }


}
