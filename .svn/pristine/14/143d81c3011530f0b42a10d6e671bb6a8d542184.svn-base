package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.zhiziyun.dmptest.bot.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class ChooseCityAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String, Object>> list;
    private LayoutInflater layoutInflater;
    private TextView tv;
    private CheckBox cb;
    private OnClick onClick;
    private int pos;

    public ChooseCityAdapter(Context context, List<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;//list中checkbox状态为false
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface OnClick {
        public void setClick(View v, Boolean b, int position);
    }

    public void setClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_tanzhen, null);

            ViewCache viewCache = new ViewCache();
            tv = convertView.findViewById(R.id.tv_tanzhen);
            cb = convertView.findViewById(R.id.cb);

            viewCache.tv = tv;
            viewCache.cb = cb;
            convertView.setTag(viewCache);
        } else {
            ViewCache viewCache = (ViewCache) convertView.getTag();
            tv = viewCache.tv;
            cb = viewCache.cb;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.setClick(v, cb.isChecked(), position);
                }
            });
        }

        try {
            tv.setText(list.get(position).get("name") + "");
            cb.setChecked((Boolean) list.get(position).get("boolean"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pos == position) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.zu_choose_right_item_bg));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.zu_choose_left_item_bg));
        }
        return convertView;
    }

    public class ViewCache {
        TextView tv;
        public CheckBox cb;
    }

    public void setSelectedPosition(int pos) {
        this.pos = pos;
    }
}
