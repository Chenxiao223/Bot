package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
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
 */

public class VisitorsselfAdapter extends BaseAdapter{
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = null;
        if (viewHold == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.listview_visitorsself, null);
            viewHold.text1 = convertView.findViewById(R.id.tv_time);
            viewHold.text2 = convertView.findViewById(R.id.tv_brands);
            viewHold.text3 = convertView.findViewById(R.id.tv_Model);
            viewHold.text4 = convertView.findViewById(R.id.tv_position);
            viewHold.img = convertView.findViewById(R.id.iv_head);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.text1.setText(list.get(position).get("content1"));
        viewHold.text2.setText(list.get(position).get("content2"));
        viewHold.text3.setText(list.get(position).get("content3"));
        viewHold.text4.setText(list.get(position).get("content4"));
        String gender=list.get(position).get("content5");
        if (gender.equals("男")){
            viewHold.img.setImageResource(R.drawable.man);
        }else if (gender.equals("女")){
            viewHold.img.setImageResource(R.drawable.woman);
        }else {
            viewHold.img.setImageResource(R.drawable.unknown);
        }
        return convertView;
    }

    public static class ViewHold{
        private TextView text1, text2, text3, text4;
        private ImageView img;
    }
}
