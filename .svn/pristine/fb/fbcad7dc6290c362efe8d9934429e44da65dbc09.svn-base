package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/24.
 * 发短信adapter
 */

public class WifiADAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;

    public WifiADAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
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
            convertView = inflater.inflate(R.layout.listview_wifi_ad, null);
            viewHold.text1 = convertView.findViewById(R.id.tv_name);
            viewHold.text2 = convertView.findViewById(R.id.tv_number);
            viewHold.text3 = convertView.findViewById(R.id.tv_progress);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.text1.setText(list.get(position).get("content1").toString());
        viewHold.text2.setText(list.get(position).get("content2").toString());//发送状态，待发送和已发送
        viewHold.text3.setText(list.get(position).get("content3").toString());

        //让listview交替变色
        if (position % 2 == 0) {
            //偶数
            convertView.setBackgroundColor(Color.parseColor("#f4f5f7"));
        } else {
            //奇数
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        return convertView;
    }

    public static class ViewHold {
        private TextView text1, text2, text3;
    }

}
