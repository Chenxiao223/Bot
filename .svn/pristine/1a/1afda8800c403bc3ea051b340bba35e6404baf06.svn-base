package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

import java.util.List;

/**
 * 子类别 适配器
 *
 * @author ansen
 * @create time 2018-05-30
 */
public class ChildrenCustomerSourceAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> list;
    private int pos;

    public ChildrenCustomerSourceAdapter(Context context, List<String> list) {
        mContext = context;
        this.list = list;
    }

    public void setDatas(List<String> list) {
        this.list = list;
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_category_item, null);
            holder.tvChildrenCategoryName = (TextView) convertView.findViewById(R.id.tv_children_category_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvChildrenCategoryName.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder {
        private TextView tvChildrenCategoryName;
    }

    public void setSelectedPosition(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }
}
