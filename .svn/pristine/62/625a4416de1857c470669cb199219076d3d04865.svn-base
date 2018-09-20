package com.zhiziyun.dmptest.bot.mode.acount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.AddSmsAdapter;
import com.zhiziyun.dmptest.bot.mode.acount.request.BFinancialResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhiziyun on 2018/9/17.
 */

public class FinancialAdapter extends RecyclerView.Adapter<FinancialAdapter.FinancialHolder> {
    private Context mContext;
    private List<BFinancialResult.ResponseBean.DataBean> mList;
    private String mNumber;
    private String mTime;
    private ArrayList<HashMap<String, Object>> mHashMaps;

    public FinancialAdapter(Context mContext, List<BFinancialResult.ResponseBean.DataBean> mList, String mTime, ArrayList<HashMap<String, Object>> mHashMaps) {
        this.mContext = mContext;
        this.mList = mList;
        this.mTime = mTime;
        this.mHashMaps = mHashMaps;
    }

    @Override
    public FinancialHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_financial, null);
        FinancialHolder financialHolder = new FinancialHolder(view);
        return financialHolder;
    }

    @Override
    public void onBindViewHolder(FinancialHolder holder, final int position) {
        holder.mTitleTv.setText(mList.get(position).getSettleType());
        if (mList.get(position).getSettleType().equals("充值")) {
            holder.mTvMoney.setText("+" + mList.get(position).getFee());
            holder.mTvMoney.setTextColor(mContext.getResources().getColor(R.color.trade_money));
        } else {
            holder.mTvMoney.setText("-" + mList.get(position).getFee());
            holder.mTvMoney.setTextColor(mContext.getResources().getColor(R.color.red_homepage));
        }
        holder.mRlfinancial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FinancialDetailActivity.class);
                for (int i = 0; i < mHashMaps.size(); i++) {
                    if (mList.get(position).getSettleType().equals(mHashMaps.get(i).get("typename"))) {
                        mNumber = String.valueOf(mHashMaps.get(i).get("typenumber").toString());
                    }
                }
                intent.putExtra("number", mNumber);
                intent.putExtra("type", mList.get(position).getSettleType());
                intent.putExtra("mTime", mTime);
                ((Activity) mContext).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FinancialHolder extends RecyclerView.ViewHolder {
        private final TextView mTitleTv, mTvMoney;
        private final RelativeLayout mRlfinancial;

        public FinancialHolder(View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.tv_title);
            mTvMoney = itemView.findViewById(R.id.tv_money);
            mRlfinancial = itemView.findViewById(R.id.rl_financial);
        }
    }
}
