package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.fragment.CrowdFragment;

import java.util.ArrayList;
import java.util.HashMap;

import ch.ielse.view.SwitchView;

/**
 * Created by Administrator on 2018/4/4.
 * 人群的adapter
 */

public class CrowdAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;

    public CrowdAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
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
            convertView = inflater.inflate(R.layout.listview_crowd, null);
            viewHold.tv_title = convertView.findViewById(R.id.tv_title);
            viewHold.tv_date = convertView.findViewById(R.id.tv_date);
            viewHold.swich = convertView.findViewById(R.id.swich);
            viewHold.swich2 = convertView.findViewById(R.id.swich2);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        try {
            viewHold.tv_title.setText(list.get(position).get("content1").toString());
            viewHold.tv_date.setText(list.get(position).get("content2").toString());
            viewHold.swich.setOpened((Boolean) list.get(position).get("content3"));
            viewHold.swich2.setOpened((Boolean) list.get(position).get("content4"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        final ViewHold finalViewHold = viewHold;
        viewHold.swich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalViewHold.swich.isOpened()) {
                    //如果开关开启，就改变记录开关的状态
                    CrowdFragment.crowdFragment.list_crowd.get(position).put("content3", true);
                } else {
                    //如果开关关闭，就改变记录开关的状态
                    CrowdFragment.crowdFragment.list_crowd.get(position).put("content3", false);
                }
                CrowdFragment.crowdFragment.adapter.notifyDataSetChanged();
            }
        });

        viewHold.swich2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalViewHold.swich2.isOpened()) {
                    CrowdFragment.crowdFragment.list_crowd.get(position).put("content4", true);
                } else {
                    CrowdFragment.crowdFragment.list_crowd.get(position).put("content4", false);
                }
                CrowdFragment.crowdFragment.adapter.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public static class ViewHold {
        private TextView tv_title, tv_date;
        private SwitchView swich, swich2;
    }
}
