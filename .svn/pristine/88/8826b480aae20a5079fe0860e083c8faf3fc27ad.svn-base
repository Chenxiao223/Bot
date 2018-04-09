package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/12.
 */

public class SpinnerArrayAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<String> list;

    public SpinnerArrayAdapter(Context context, ArrayList<String> list) {
        super(context, android.R.layout.simple_spinner_item, list);
        mContext = context;
        this.list = list;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //修改Spinner展开后的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(list.get(position));
        tv.setTextSize(14f);
        tv.setTextColor(Color.parseColor("#247ab7"));
        tv.setGravity(android.view.Gravity.CENTER);   //设置居中

        return convertView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 修改Spinner选择后结果的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(list.get(position));
        tv.setTextSize(14f);
        tv.setTextColor(Color.parseColor("#247ab7"));
        tv.setGravity(android.view.Gravity.CENTER);   //设置居中
        return convertView;
    }
}
