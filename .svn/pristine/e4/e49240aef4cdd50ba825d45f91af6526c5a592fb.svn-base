package com.zhiziyun.dmptest.bot.adapter;

import android.graphics.Bitmap;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhiziyun.dmptest.bot.R;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/7/30.
 */

public class SubmitFriendAdapter extends BaseAdapter {
    public static final int ITEM_A = 0;
    public static final int ITEM_B = 1;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;
    private Content content;
    private Pictures pictures;

    public SubmitFriendAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public interface Pictures {
        public void setPicture(View v, int position);
    }

    public void setPicture(Pictures pictures) {
        this.pictures = pictures;
    }

    public interface Content {
        public void setText(String text, int position);
    }

    public void setText(Content content) {
        this.content = content;
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
        return (int) list.get(position).get("state");
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        ViewHolder holder = null;
        ViewHolder2 holder2 = null;
        if (convertView == null) {
            switch (type) {
                case ITEM_A:
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.listview_submitfriend_item_text, null);
                    holder.et_title = convertView.findViewById(R.id.et_title);
                    convertView.setTag(holder);
                    break;
                case ITEM_B:
                    holder2 = new ViewHolder2();
                    convertView = inflater.inflate(R.layout.listview_submitfriend_item_image, null);
                    holder2.iv_picture = convertView.findViewById(R.id.iv_picture);
                    convertView.setTag(holder2);
                    break;
            }

        } else {

        }

        switch (type) {
            case ITEM_A:
                holder = (ViewHolder) convertView.getTag();
                int maxnum = Integer.parseInt(list.get(position).get("textMaxNum").toString());
                holder.et_title.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxnum)}); //最大输入长度
                try {
                    if (!TextUtils.isEmpty(list.get(position).get("title").toString())) {
                        holder.et_title.setText(list.get(position).get("title").toString());
                    }
                    if (list.get(position).get("edit").equals("edit")) {
                        //不能编辑
                        holder.et_title.setFocusable(false);
                        holder.et_title.setFocusableInTouchMode(false);
                        holder.et_title.setClickable(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ITEM_B:
                holder2 = (ViewHolder2) convertView.getTag();
                if (list.get(position).get("bitmap") != null) {
                    holder2.iv_picture.setImageBitmap((Bitmap) list.get(position).get("bitmap"));
                }
                break;
            default:
                break;
        }

        if (holder != null) {
            holder.et_title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    content.setText(s.toString(), position);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        if (holder2 != null) {
            holder2.iv_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pictures.setPicture(v, position);
                }
            });
        }

        return convertView;
    }

    public class ViewHolder {
        private TextView et_title;
    }

    public class ViewHolder2 {
        private ImageView iv_picture;
    }

}
