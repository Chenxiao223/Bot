package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.graphics.Color;
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
 * 通话记录详情的adapter
 */

public class CallRecordsCAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;

    public CallRecordsCAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
            viewHold = new CallRecordsCAdapter.ViewHold();
            convertView = inflater.inflate(R.layout.listview_crc, null);
            viewHold.tv_answerTime = convertView.findViewById(R.id.tv_answerTime);
            viewHold.tv_calltime = convertView.findViewById(R.id.tv_calltime);
            viewHold.tv_phoneA = convertView.findViewById(R.id.tv_phoneA);
            viewHold.tv_deductDspFee = convertView.findViewById(R.id.tv_deductDspFee);
            convertView.setTag(viewHold);
        } else {
            viewHold = (CallRecordsCAdapter.ViewHold) convertView.getTag();
        }
        viewHold.tv_answerTime.setText(list.get(position).get("content1"));
        viewHold.tv_calltime.setText(list.get(position).get("content2"));
        viewHold.tv_phoneA.setText(list.get(position).get("content3"));
        viewHold.tv_deductDspFee.setText(list.get(position).get("content4"));

        //让listview交替变色
        if (position % 2 == 0) {
            //偶数
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            //奇数
            convertView.setBackgroundColor(Color.parseColor("#e7e9ea"));
        }

        return convertView;
    }

    public static class ViewHold {
        private TextView tv_answerTime, tv_calltime, tv_phoneA, tv_deductDspFee;
    }
}
