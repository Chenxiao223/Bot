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
 * 探针列表的adapter
 */

public class TanzhenListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;

    public TanzhenListAdapter(Context context,ArrayList<HashMap<String, String>> list) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_tanzhenlist, null);
            holder.text1 = convertView.findViewById(R.id.tv_name);
            holder.text2 = convertView.findViewById(R.id.tv_mac);
            holder.text3 = convertView.findViewById(R.id.tv_area);
            holder.text4 = convertView.findViewById(R.id.tv_online);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text1.setText(list.get(position).get("content1"));
        holder.text2.setText(list.get(position).get("content2"));
        holder.text3.setText(list.get(position).get("content3"));
        holder.text4.setText(list.get(position).get("content4"));
        return convertView;
    }

    public class ViewHolder {
        private TextView text1, text2, text3, text4;
    }
}
