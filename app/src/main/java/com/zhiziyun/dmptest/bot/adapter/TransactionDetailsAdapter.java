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
import com.zhiziyun.dmptest.bot.util.MyDialog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/24.
 * 交易明细的adapter
 */

public class TransactionDetailsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;
    private MyDialog dialog;
    public TransactionDetailsAdapter(Context context, ArrayList<HashMap<String, String>> list) {
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
            convertView = inflater.inflate(R.layout.listview_transaction_details, null);
            viewHold.text1 = convertView.findViewById(R.id.tv_title);
            viewHold.text2 = convertView.findViewById(R.id.tv_date);
            viewHold.text3 = convertView.findViewById(R.id.tv_money);
            viewHold.img = convertView.findViewById(R.id.image);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.text1.setText(list.get(position).get("content1"));
        viewHold.text2.setText(list.get(position).get("content2"));
        if (list.get(position).get("content1").indexOf("结算") != -1) {
            viewHold.text3.setTextColor(Color.parseColor("#ff664e"));
            viewHold.text3.setText("-" + list.get(position).get("content3"));//说明包含结算，颜色为红色
        } else {
            viewHold.text3.setTextColor(Color.parseColor("#2a7ccf"));
            viewHold.text3.setText("+" + list.get(position).get("content3"));
        }
        return convertView;
    }

    public static class ViewHold {
        private TextView text1, text2, text3;
        private ImageView img;
    }
}
