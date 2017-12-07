package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.TanzhenListActivity;
import com.zhiziyun.dmptest.bot.util.SlideItemView;
import com.zhiziyun.dmptest.bot.util.SlideListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/24.
 * 探针列表的adapter
 */

public class TanzhenListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;
    private SlideListView listView;

    public TanzhenListAdapter(Context context, SlideListView listView, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
        this.listView = listView;
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
        ViewHolder holder = null;
        if (convertView == null) {
            SlideItemView itemView = new SlideItemView(context);
            itemView.setView(listView, R.layout.listview_tanzhenlist, R.layout.item_menu_tanzhen, 2.0f / 7);
            holder = new ViewHolder(itemView);
            itemView.setTag(holder);
            convertView = itemView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text1.setText(list.get(position).get("content1"));
        holder.text2.setText(list.get(position).get("content2"));
        holder.text3.setText(list.get(position).get("content3"));
        final SlideItemView itemView = (SlideItemView) convertView;
        holder.tv_unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.showContent();
                TanzhenListActivity.tanzhenListActivity.unbindTanzhen(list.get(position).get("id"));
            }
        });
        return convertView;
    }

    public class ViewHolder {
        private TextView text1, text2, text3, tv_unbind;

        public ViewHolder(SlideItemView view) {
            View content = view.getContent();
            text1 = content.findViewById(R.id.tv_name);
            text2 = content.findViewById(R.id.tv_mac);
            text3 = content.findViewById(R.id.tv_area);
            View menu = view.getMenu();
            tv_unbind = menu.findViewById(R.id.tv_unbind);
        }

    }
}
