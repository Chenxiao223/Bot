package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
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
 * 发短信adapter
 */

public class SendPlanAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;

    public SendPlanAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.listview_send_plan, null);
            viewHold.text1 = convertView.findViewById(R.id.tv_name);
            viewHold.text2 = convertView.findViewById(R.id.tv_state);
            viewHold.text3 = convertView.findViewById(R.id.tv_date);
            viewHold.text4 = convertView.findViewById(R.id.tv_num);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.text1.setText(list.get(position).get("content1"));
        viewHold.text2.setText(list.get(position).get("content2"));
        viewHold.text3.setText(list.get(position).get("content3"));
        viewHold.text4.setText(list.get(position).get("content4"));
        return convertView;
    }

    public static class ViewHold{
        private TextView text1, text2, text3, text4;
    }
}
