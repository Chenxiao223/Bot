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
 * 通话记录汇总的adapter
 */

public class CallRecordsGAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;

    public CallRecordsGAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
            viewHold = new CallRecordsGAdapter.ViewHold();
            convertView = inflater.inflate(R.layout.listview_crg, null);
            viewHold.tv_date = convertView.findViewById(R.id.tv_date);
            viewHold.tv_fee = convertView.findViewById(R.id.tv_fee);
            convertView.setTag(viewHold);
        } else {
            viewHold = (CallRecordsGAdapter.ViewHold) convertView.getTag();
        }
        viewHold.tv_date.setText(list.get(position).get("content1"));
        viewHold.tv_fee.setText(list.get(position).get("content2"));
        return convertView;
    }

    public static class ViewHold {
        private TextView tv_date, tv_fee;
    }
}
