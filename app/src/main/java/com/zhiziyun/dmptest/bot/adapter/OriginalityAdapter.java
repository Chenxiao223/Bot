package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.zhiziyun.dmptest.bot.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/24.
 * 创意页面适配器
 */

public class OriginalityAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;
    private ImageView imageView;
    private CheckBox cb;
    private int flag = 0;

    public OriginalityAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_originality, null);

            ViewHold viewHold = new ViewHold();
            imageView = convertView.findViewById(R.id.image);
            cb = convertView.findViewById(R.id.cb);
            if (flag != 0) {
                cb.setVisibility(View.GONE);
            }

            viewHold.imageView = imageView;
            viewHold.cb = cb;
            convertView.setTag(viewHold);
        } else {
            ViewHold viewHold = (ViewHold) convertView.getTag();
            imageView = viewHold.imageView;
            cb = viewHold.cb;
        }
        try {
            imageView.setImageBitmap((Bitmap) list.get(position).get("image"));
            cb.setChecked((Boolean) list.get(position).get("boolean"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public class ViewHold {
        private ImageView imageView;
        public CheckBox cb;
    }

    public void hiddenCheckBox(int num) {
        flag = num;
    }
}
