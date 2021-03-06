package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

import java.util.ArrayList;
import java.util.HashMap;

import ch.ielse.view.SwitchView;

/**
 * Created by Administrator on 2017/11/24.
 * 发短信adapter
 */

public class AddSmsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;
    private AddSmsAdapter.OnCheck onCheck;

    public AddSmsAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setOnCheck(AddSmsAdapter.OnCheck onCheck) {
        this.onCheck = onCheck;
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
            convertView = inflater.inflate(R.layout.listview_add_sms, null);
            viewHold.text1 = convertView.findViewById(R.id.tv_name);
            viewHold.text2 = convertView.findViewById(R.id.tv_state);
            viewHold.text3 = convertView.findViewById(R.id.tv_date);
            viewHold.text4 = convertView.findViewById(R.id.tv_num);
            viewHold.text5 = convertView.findViewById(R.id.tv_cost);
            viewHold.swich = convertView.findViewById(R.id.swich);
            viewHold.image = convertView.findViewById(R.id.image);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.text1.setText(list.get(position).get("content1").toString());
        viewHold.text2.setText(list.get(position).get("content2").toString());//发送状态，待发送和已发送
        viewHold.text4.setText(list.get(position).get("content4").toString());
        viewHold.text5.setText(list.get(position).get("content5").toString());
        try {
            if (TextUtils.isEmpty((CharSequence) list.get(position).get("content3"))) {
                viewHold.text3.setText("--");
            } else {
                viewHold.text3.setText(list.get(position).get("content3").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((Boolean) list.get(position).get("issend")) {
            viewHold.swich.setOpened(false);
            viewHold.swich.setVisibility(View.VISIBLE);
        } else {
            //已发送状态没有点击发送
            viewHold.swich.setOpened(true);
            viewHold.swich.setClickable(false);
        }

        viewHold.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //参数：短信活动编号、位置
                onCheck.setInfo(list.get(position).get("activityId").toString(), position);
            }
        });

        return convertView;
    }

    public static class ViewHold {
        private TextView text1, text2, text3, text4, text5;
        private SwitchView swich;
        private ImageView image;
    }

    public interface OnCheck {
        public void setInfo(String id, int position);
    }
}
