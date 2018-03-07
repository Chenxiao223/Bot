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

public class ChooseSmsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;
    private TextView text1, text2, text3, text4;

    public ChooseSmsAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_choose_sms, null);

            ViewHold viewHold = new ViewHold();
            text1 = convertView.findViewById(R.id.tv_title);
            text2 = convertView.findViewById(R.id.tv_content);
            text3 = convertView.findViewById(R.id.tv_state);
            text4 = convertView.findViewById(R.id.tv_date);

            viewHold.text1 = text1;
            viewHold.text2 = text2;
            viewHold.text3 = text3;
            viewHold.text4 = text4;
            convertView.setTag(viewHold);
        } else {
            ViewHold viewHold = (ViewHold) convertView.getTag();
            text1 = viewHold.text1;
            text2 = viewHold.text2;
            text3 = viewHold.text3;
            text4 = viewHold.text4;
        }
        text1.setText((String) list.get(position).get("content1"));
        text2.setText((String) list.get(position).get("content2"));
        text4.setText((String) list.get(position).get("content4"));
        String state = list.get(position).get("content3");
        if (state.equals("未提交")) {
            text3.setText("预审中");
        } else {
            text3.setText(state);
        }
        return convertView;
    }

    public static class ViewHold {
        private TextView text1, text2, text3, text4;
    }
}
