package com.haichecker.lib.widget.selectlist.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haichecker.lib.R;
import com.haichecker.lib.widget.selectlist.SelectListValueInterface;
import com.haichecker.lib.widget.selectlist.been.BaseBeen;

import java.util.List;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-18 20:04
 */

public class DefalutSelectAdapter<T extends SelectListValueInterface> extends BaseSelectAdapter<T> {


    public DefalutSelectAdapter(Context mContext, List<T> data) {
        super(mContext, data, R.layout.select_list_item);
    }

    public DefalutSelectAdapter(Context mContext) {
        super(mContext, R.layout.select_list_item);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        H h = null;
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
            h = new H();
            h.text1 = (TextView) view.findViewById(android.R.id.text1);
            view.setTag(h);
        } else {
            h = (H) view.getTag();
        }
        SelectListValueInterface selectListValueInterface = data.get(i);
        h.text1.setText(selectListValueInterface.getContentText() + ":" + i);
        return view;
    }

    public class H {
        TextView text1;
    }
}
