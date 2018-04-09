package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/24.
 */

public class VisitorsselfAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;

    public VisitorsselfAdapter(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
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
        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.listview_visitorsself, null);
            viewHold.text1 = convertView.findViewById(R.id.tv_time);
            viewHold.text2 = convertView.findViewById(R.id.tv_brands);
            viewHold.text3 = convertView.findViewById(R.id.tv_Model);
            viewHold.text4 = convertView.findViewById(R.id.tv_position);
            viewHold.text5 = convertView.findViewById(R.id.tv_mac);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        try {
            viewHold.text1.setText(list.get(position).get("content1"));
            if (TextUtils.isEmpty(list.get(position).get("content2"))) {
                viewHold.text2.setText("未知");
            } else {
                viewHold.text2.setText(list.get(position).get("content2"));
            }
            if (TextUtils.isEmpty(list.get(position).get("content3"))) {
                viewHold.text3.setText("未知");
            } else {
                viewHold.text3.setText(list.get(position).get("content3"));
            }
            viewHold.text4.setText(list.get(position).get("content4"));
            viewHold.text5.setText(list.get(position).get("mac"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public static class ViewHold {
        private TextView text1, text2, text3, text4, text5;
    }

}
