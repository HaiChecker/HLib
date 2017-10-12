package com.haichecker.simple.test.selectlist_test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.haichecker.lib.widget.selectlist.SelectList;
import com.haichecker.lib.widget.selectlist.SelectListValueInterface;
import com.haichecker.lib.widget.selectlist.adapter.DefalutSelectAdapter;
import com.haichecker.simple.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class SelectListTestActivity extends AppCompatActivity {
    public class Been implements SelectListValueInterface {

        @Override
        public String getContentText() {
            return "Test";
        }
    }

    private SelectList<DefalutSelectAdapter<Been>> selectList;

    public static void start(Context context) {
        Intent starter = new Intent(context, SelectListTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list_test);
        final List<Been> beens = new ArrayList<>();
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());
        beens.add(new Been());


        final List<Been> beens1 = new ArrayList<>();
        beens1.add(new Been());
        beens1.add(new Been());
        beens1.add(new Been());

        final List<Been> beens2 = new ArrayList<>();
        beens2.add(new Been());
        beens2.add(new Been());
        beens2.add(new Been());
        selectList = new SelectList<>(this, findViewById(R.id.root));
        selectList.getTabLayout().setTabMode(MODE_SCROLLABLE);
        selectList.setOnItemClickListener(new SelectList.OnItemClickListener() {
            @Override
            public void onItem(int groupIndex, int pIndex, ListView list, Object obj) {
                switch (groupIndex) {
                    case 0:
                        selectList.changeTitle(groupIndex, beens.get(pIndex).getContentText());
                        selectList.del(1);
                        selectList.add("请选择", new DefalutSelectAdapter<Been>(list.getContext(), beens1));
                        break;
                    case 1:
                        selectList.changeTitle(groupIndex, beens1.get(pIndex).getContentText());
                        selectList.del(2);
                        selectList.add("请选择", new DefalutSelectAdapter<Been>(list.getContext(), beens2));
                        break;
                    case 2:
                        selectList.changeTitle(groupIndex, beens2.get(pIndex).getContentText());
                        break;
                }
            }
        });
        findViewById(R.id.text)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectList.add("请选择", new DefalutSelectAdapter<Been>(view.getContext(), beens));
                        selectList.show();
                    }
                });

    }
}
