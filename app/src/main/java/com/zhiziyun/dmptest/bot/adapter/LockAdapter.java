package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiziyun.dmptest.bot.R;

import java.util.ArrayList;

/**
 * Created by zhiziyun on 2018/9/6.
 */

public class LockAdapter extends RecyclerView.Adapter<LockAdapter.LockHolder> {
    private Context mContext;
    private ArrayList<String> mData;

    public LockAdapter(Context mContext, ArrayList<String> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public LockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_lock, null);
        LockHolder lockHolder = new LockHolder(view);
        return lockHolder;
    }

    @Override
    public void onBindViewHolder(LockHolder holder, int position) {

    }


    public class LockHolder extends RecyclerView.ViewHolder {
        public LockHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
