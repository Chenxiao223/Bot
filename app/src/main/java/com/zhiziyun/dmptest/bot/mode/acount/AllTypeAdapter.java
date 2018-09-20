package com.zhiziyun.dmptest.bot.mode.acount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.ItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zhiziyun on 2018/9/17.
 */

public class AllTypeAdapter extends RecyclerView.Adapter<AllTypeAdapter.AllTypeHolder> {
    private ArrayList<HashMap<String, Object>> mList;
    private Context mContext;
    private ItemClickListener mOnItemClickListener;

    public AllTypeAdapter(Context mContext, ArrayList<HashMap<String, Object>> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public AllTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.all_financial_type, null);
        AllTypeHolder allTypeHolder = new AllTypeHolder(view);
        return allTypeHolder;
    }

    @Override
    public void onBindViewHolder(AllTypeHolder holder, int position) {
        holder.mTv_all_financial.setText(mList.get(position).get("typename").toString());
        holder.mCheck.setChecked((Boolean) mList.get(position).get("boolean"));
        holder.mAll_financial_ll.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class AllTypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTv_all_financial;
        private final LinearLayout mAll_financial_ll;
        private final CheckBox mCheck;

        public AllTypeHolder(View itemView) {
            super(itemView);
            mTv_all_financial = itemView.findViewById(R.id.tv_all_financial);
            mAll_financial_ll = itemView.findViewById(R.id.all_financial_ll);
            mCheck = itemView.findViewById(R.id.cb);
            mAll_financial_ll.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.OnItemClick(v, (Integer) mAll_financial_ll.getTag());
            }
        }
    }
}
