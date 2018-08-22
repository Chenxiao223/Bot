package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.originality.friend.TradeDetail;

import java.util.List;

/**
 * Created by zhiziyun on 2018/7/31.
 */

public class TradeDetailAdapter extends RecyclerView.Adapter<TradeDetailAdapter.TradeHolder> {
    private Context mContext;
    private List<TradeDetail> mList;

    public TradeDetailAdapter(Context mContext, List<TradeDetail> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public TradeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.trade_detail_item, parent, false);
        TradeHolder holder = new TradeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TradeHolder holder, int position) {
        holder.mTitleTv.setText(mList.get(position).getSettleType());
        holder.mTvDate.setText(mList.get(position).getSettleDate());
        if (mList.get(position).getSettleType().equals("消耗")) {
            holder.mTvMoney.setText("-"+mList.get(position).getFee());
            holder.mTvMoney.setTextColor(mContext.getResources().getColor(R.color.red_homepage));
        } else {
            holder.mTvMoney.setText("+"+mList.get(position).getFee());
            holder.mTvMoney.setTextColor(mContext.getResources().getColor(R.color.trade_money));
        }


    }

    public class TradeHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTv, mTvDate, mTvMoney;

        public TradeHolder(View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.tv_title);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mTvMoney = itemView.findViewById(R.id.tv_money);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
