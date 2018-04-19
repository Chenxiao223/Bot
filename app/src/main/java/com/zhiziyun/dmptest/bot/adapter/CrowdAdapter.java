package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

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
    private OnClick onClick;
    private OnCheck onCheck;

    public CrowdAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setOnClick(CrowdAdapter.OnClick onClick) {
        this.onClick = onClick;
    }

    public void setOnCheck(CrowdAdapter.OnCheck onCheck) {
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
            convertView = inflater.inflate(R.layout.listview_crowd, null);
            viewHold.tv_title = convertView.findViewById(R.id.tv_title);
            viewHold.tv_date = convertView.findViewById(R.id.tv_date);
            viewHold.swich = convertView.findViewById(R.id.swich);
            viewHold.line_details = convertView.findViewById(R.id.line_details);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        try {
            viewHold.tv_title.setText(list.get(position).get("content1").toString());
            viewHold.tv_date.setText(list.get(position).get("content2").toString());
            switch (Integer.parseInt(list.get(position).get("content4").toString())) {
                case 0:
                    viewHold.swich.setOpened(false);
                    viewHold.swich.setVisibility(View.VISIBLE);//如果是0或1，这个状态要重新设置
                    break;
                case 1:
                    viewHold.swich.setOpened(true);
                    viewHold.swich.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    viewHold.swich.setVisibility(View.GONE);
                    break;
                case 3:
                    viewHold.swich.setVisibility(View.GONE);
                    break;
            }
//            if ((int) list.get(position).get("content4") == 0) {//关闭
//                viewHold.swich.setOpened(false);
//            } else if ((int) list.get(position).get("content4") == 1) {//打开
//                viewHold.swich.setOpened(true);
//            } else {//隐藏开关
//                viewHold.swich.setVisibility(View.INVISIBLE);
//            }

            if ((Boolean) list.get(position).get("content3")) {
                viewHold.line_details.setVisibility(View.VISIBLE);
            } else {
                viewHold.line_details.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHold.swich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchView sv = v.findViewById(R.id.swich);
                //参数：开关状态、是否创建了广告任务、到店人群编号、人群名称、位置
                onCheck.setInfo(sv.isOpened(), (Boolean) list.get(position).get("content3"), list.get(position).get("id").toString(), list.get(position).get("content1").toString(), position);
            }
        });

        viewHold.line_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClick.setInfo(list.get(position).get("id").toString(), position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return convertView;
    }

    public static class ViewHold {
        private TextView tv_title, tv_date;
        private SwitchView swich;
        private LinearLayout line_details;
    }

    public interface OnClick {
        public void setInfo(String id, int position);
    }

    public interface OnCheck {
        public void setInfo(boolean b, boolean hasTranForPhone, String id, String name, int position);
    }

}
