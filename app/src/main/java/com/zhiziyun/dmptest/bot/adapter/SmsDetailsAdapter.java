package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/24.
 * 短信详情的adapter
 */

public class SmsDetailsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;

    public SmsDetailsAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
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
            convertView = inflater.inflate(R.layout.listview_sms_details, null);
            viewHold.text1 = convertView.findViewById(R.id.tv_phone);
            viewHold.text2 = convertView.findViewById(R.id.tv_date);
            viewHold.text3 = convertView.findViewById(R.id.tv_state);
            viewHold.img = convertView.findViewById(R.id.image);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        try {
            viewHold.text1.setText(list.get(position).get("content1").toString());
            viewHold.text2.setText((CharSequence) list.get(position).get("content2"));
            if ((boolean) list.get(position).get("content3")) {
                viewHold.text3.setText("发送成功");
                viewHold.text3.setTextColor(Color.parseColor("#247ab7"));
                viewHold.img.setVisibility(View.INVISIBLE);
            } else {
                viewHold.text3.setText("发送失败");
                viewHold.text3.setTextColor(Color.parseColor("#ff664e"));
                viewHold.img.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public static class ViewHold {
        private TextView text1, text2, text3;
        private ImageView img;
    }
}
