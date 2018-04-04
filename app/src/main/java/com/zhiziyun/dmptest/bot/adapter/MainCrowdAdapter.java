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
 * Created by Administrator on 2018/4/4.
 * 底部导航人群适配器
 */

public class MainCrowdAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;
    private OnCall onCall;
    private OnEdit onEdit;

    public MainCrowdAdapter(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public interface OnCall {
        public void setInfo(String nm, int position);
    }

    public interface OnEdit {
        public void setInfo(String nm, int position);
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
            convertView = inflater.inflate(R.layout.listview_main_crowd, null);
            viewHold.tv_title = convertView.findViewById(R.id.tv_title);
            viewHold.tv_state = convertView.findViewById(R.id.tv_state);
            viewHold.tv_state = convertView.findViewById(R.id.tv_state);
            viewHold.tv_customer = convertView.findViewById(R.id.tv_customer);
            viewHold.tv_head = convertView.findViewById(R.id.tv_head);
            viewHold.tv_note = convertView.findViewById(R.id.tv_note);
            viewHold.iv_edit = convertView.findViewById(R.id.iv_edit);
            viewHold.iv_call = convertView.findViewById(R.id.iv_call);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_title.setText(list.get(position).get("content1"));
        viewHold.tv_state.setText(list.get(position).get("content2"));
        viewHold.tv_customer.setText(list.get(position).get("content3"));
        viewHold.tv_head.setText(list.get(position).get("content4"));
        viewHold.tv_note.setText(list.get(position).get("content5"));
        //点击电话
        viewHold.iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall.setInfo(list.get(position).get("content1"), position);
            }
        });
        //点击编辑
        viewHold.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit.setInfo(list.get(position).get("content1"), position);
            }
        });
        return convertView;
    }

    public static class ViewHold {
        private TextView tv_title, tv_state, tv_customer, tv_head, tv_note;
        private ImageView iv_edit, iv_call;
    }

    public void setOnCall(OnCall onCall) {
        this.onCall = onCall;
    }

    public void setOnEdit(OnEdit onEdit) {
        this.onEdit = onEdit;
    }
}