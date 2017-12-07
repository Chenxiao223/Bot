package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.StoreListActivity;
import com.zhiziyun.dmptest.bot.util.SlideItemView;
import com.zhiziyun.dmptest.bot.util.SlideListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/24.
 * 门店列表的adapter
 */

public class StoreListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> list;
    private SlideListView listView;

    public StoreListAdapter(Context context, SlideListView listView, ArrayList<HashMap<String, String>> list) {
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
            itemView.setView(listView, R.layout.listview_storelist, R.layout.item_menu, 2.0f / 3);//最后一个参数是滑动块的长度
            holder = new ViewHolder(itemView);
            itemView.setTag(holder);
            convertView = itemView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text1.setText(list.get(position).get("content1"));
        holder.text2.setText(list.get(position).get("content2") + "㎡");
        holder.text3.setText(list.get(position).get("content3"));
        final SlideItemView itemView = (SlideItemView) convertView;
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreListActivity.storeListActivity.editeStore(list.get(position).get("content1"),
                        list.get(position).get("content2"),
                        list.get(position).get("lat"),
                        list.get(position).get("lon"),
                        list.get(position).get("id"));
                itemView.showContent();
            }
        });
        holder.tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemView.showContent();
                StoreListActivity.storeListActivity.checkTanzhen(list.get(position).get("id"),
                        Float.parseFloat(list.get(position).get("lat")),
                        Float.parseFloat(list.get(position).get("lon")),
                        list.get(position).get("area"));
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.showContent();
                StoreListActivity.storeListActivity.deleteStore(list.get(position).get("id"));
            }
        });
        return convertView;
    }

    public class ViewHolder {
        private TextView text1, text2, text3, tv_edit, tv_delete, tv_view;

        public ViewHolder(SlideItemView view) {
            View content = view.getContent();
            text1 = content.findViewById(R.id.tv_store);
            text2 = content.findViewById(R.id.tv_area);
            text3 = content.findViewById(R.id.tv_probeCount);
            View menu = view.getMenu();
            tv_edit = menu.findViewById(R.id.tv_edit);
            tv_delete = menu.findViewById(R.id.tv_delete);
            tv_view = menu.findViewById(R.id.tv_view);
        }

    }
}
