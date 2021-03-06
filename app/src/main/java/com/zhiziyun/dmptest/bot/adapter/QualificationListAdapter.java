package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
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
 * 资质adapter
 */

public class QualificationListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;

    public QualificationListAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
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
            convertView = inflater.inflate(R.layout.listview_qualification, null);
            viewHold.text = convertView.findViewById(R.id.text);
            viewHold.image = convertView.findViewById(R.id.image);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        if (list.get(position).get("content1").toString().equals("法人代表身份证照片")) {
            if (list.get(position).get("state").toString().equals("审核失败")) {
                String str = list.get(position).get("content1").toString() + "<font color='#FF0000'>  (可以点击图片进行修改)</font>";
                viewHold.text.setText(str);
                viewHold.text.setText(Html.fromHtml(str));//让部分字变色
            } else {
                viewHold.text.setText(list.get(position).get("content1").toString());
            }
        } else {
            viewHold.text.setText(list.get(position).get("content1").toString());
        }
        viewHold.image.setImageBitmap((Bitmap) list.get(position).get("content2"));
        return convertView;
    }

    public static class ViewHold {
        private TextView text;
        private ImageView image;
    }
}
