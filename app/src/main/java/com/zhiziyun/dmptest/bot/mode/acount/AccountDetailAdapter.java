package com.zhiziyun.dmptest.bot.mode.acount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.acount.request.BRechargeDetailResult;

import java.util.List;

/**
 * Created by zhiziyun on 2018/9/14.
 */

public class AccountDetailAdapter extends RecyclerView.Adapter<AccountDetailAdapter.AccountHolder> {
    private Context mContext;
    private List<BRechargeDetailResult.ResponseBean.DataBean> mList;

    public AccountDetailAdapter(Context mContext, List<BRechargeDetailResult.ResponseBean.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.trade_detail_item, parent, false);
        AccountHolder holder = new AccountHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AccountHolder holder, int position) {
        holder.mTitleTv.setText(mList.get(position).getSettleType());
        holder.mTvDate.setText(mList.get(position).getSettleDate());
        if (mList.get(position).getSettleType().equals("充值")) {
            holder.mTvMoney.setText("+" + mList.get(position).getFee());
            holder.mTvMoney.setTextColor(mContext.getResources().getColor(R.color.trade_money));
        } else {
            holder.mTvMoney.setText("-" + mList.get(position).getFee());
            holder.mTvMoney.setTextColor(mContext.getResources().getColor(R.color.red_homepage));
        }
    }

    public class AccountHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTv, mTvDate, mTvMoney;

        public AccountHolder(View itemView) {
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
