package com.haichecker.simple;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.haichecker.lib.widget.viewtoast.ViewToast;
import com.haichecker.simple.databinding.ActivityMainBinding;
import com.haichecker.simple.v.activity.TableViewTestActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding> {
    ViewToast toast;
    List<String> data = new ArrayList<>();
    SelectList<BaseSelectAdapter<BaseBeen>> selectAdapterSelectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databinding.tableViewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableViewTestActivity.startTableViewTest(getContext());
            }
        });
//        databinding.recycler.setLayoutManager(new LinearLayoutManager(this));
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
//
//            @Override
//            public RecyclerView.ViewHolder onBaseCreateViewHolder(ViewGroup parent, int viewType) {
//                return new RecyclerView.ViewHolder(new Button(getContext())) {
//                };
//            }
//
//        };
//        headerAdapter.setEmptyView(R.layout.fragment_blank, databinding.recycler);
//        databinding.recycler.setAdapter(headerAdapter);
//
//
//        selectAdapterSelectList = new SelectList<>(this, databinding.getRoot());
//
//        selectAdapterSelectList.add("请选择", new BaseSelectAdapter<BaseBeen>(this, R.layout.select_list_item) {
//            @SuppressLint("ViewHolder")
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                return inflater.inflate(layout, viewGroup, false);
//            }
//        });
//
//
//        toast = ViewToast.createDefalut(this, (ViewGroup) databinding.getRoot());
//        databinding.viewToast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toast.setText("测试")
//                        .showProgress()
//                        .show();
//            }
//        });
//        for (int i = 0; i < 10; i++) {
//            data.add("测试数据：" + i);
//        }
//        final HDialog hDialog = new HDialog(this);
//        hDialog.setCancelText("取消");
//        hDialog.setOnHDialogListener(new OnHDialogListener() {
//            @Override
//            public void itemClick(View view, int index) {
//                hDialog.dismiss();
//            }
//
//            @Override
//            public void itenLongClick(View view, int index) {
//
//            }
//
//            @Override
//            public void okClick() {
//
//            }
//
//            @Override
//            public void cancelClick() {
//
//            }
//        });
//        hDialog.setAdapter(new HDialogAdapter() {
//            @Override
//            public void itemOnView(Button view, int index) {
//                view.setText("测试数据:" + index);
//                view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.test_back));
//                view.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.test_back_333));
//            }
//
//            @Override
//            public void button(Button b, int type) {
//
//            }
//
//            @Override
//            public int itemCount() {
//                return 4;
//            }
//        });
//        databinding.stateArc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                databinding.stateArc.setType(1);
////                selectAdapterSelectList.show();
//                hDialog.show();
//            }
//        });
//        databinding.scrollView.setCallBack(new ViewScroll.OnCallBack<String, Button>() {
//            @Override
//            public void onLoad(Button view, String s, int index) {
//                view.setText("aaaaaaa" + s + "");
//            }
//
//            @Override
//            public Button getView() {
//                Button textView = new Button(MainActivity.this);
//                textView.setGravity(Gravity.CENTER_VERTICAL);
//                textView.setMaxLines(1);
//                textView.setLines(1);
//                return textView;
//            }
//
//            @Override
//            public void onClick(Button textView, int p) {
//                Log.d("ViewScroll", "Index=" + databinding.scrollView.getData().get(p));
//            }
//        });
//        databinding.scrollView.setNewData(data);

//        if (databinding.lodingView.isShowMessageing()) {
//            ((Button) databinding.lodingView.getMessageView()).setText("哈哈");
//        }
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
