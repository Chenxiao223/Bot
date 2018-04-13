package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 * 跟进记录adapter
 */

public class CommunicationRecordAdapter extends BaseAdapter {
    private Context context;
    public static final int ITEM_A = 0;
    public static final int ITEM_B = 1;
    private LayoutInflater inflater;
    private List<HashMap<String, String>> list;

    public CommunicationRecordAdapter(Context context, List<HashMap<String, String>> list) {
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
    public int getItemViewType(int position) {
        return Integer.parseInt(list.get(position).get("content3"));
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            if (type == ITEM_A) {
                convertView = inflater.inflate(R.layout.listview_record_a, null);
            } else {
                convertView = inflater.inflate(R.layout.listview_record_b, null);
            }
            viewHold.text1 = convertView.findViewById(R.id.tv_record);
            viewHold.text2 = convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.text1.setText(list.get(position).get("content1"));
        viewHold.text2.setText(list.get(position).get("content2"));
        return convertView;
    }

    public static class ViewHold {
        private TextView text1, text2;
    }
}
