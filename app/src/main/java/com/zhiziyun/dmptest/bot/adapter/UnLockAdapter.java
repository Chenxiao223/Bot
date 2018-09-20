package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.BTestdata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiziyun on 2018/9/6.
 */

public class UnLockAdapter extends RecyclerView.Adapter<UnLockAdapter.UnLockHodler> {
    private Context mContext;
    private ArrayList<BTestdata> mData;
    private List<Integer> mCloseList = new ArrayList<>();

    public UnLockAdapter(Context mContext, ArrayList<BTestdata> mdata) {
        this.mContext = mContext;
        this.mData = mdata;
    }

    @Override
    public UnLockHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_unlock, parent, false);
        UnLockHodler unLockHodler = new UnLockHodler(view);
        return unLockHodler;
    }

    @Override
    public void onBindViewHolder(final UnLockHodler holder, final int position) {
        holder.mPlatformTv.setText(mData.get(position).getString());
        UnlockDetailAdpter unlockDetailAdpter = new UnlockDetailAdpter(mContext, mData.get(position).getDetailbeen());
        holder.mRvUnlockDetail.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mRvUnlockDetail.setAdapter(unlockDetailAdpter);
        holder.mLlUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCloseList.contains(position)) {
                    holder.mRvUnlockDetail.setVisibility(View.VISIBLE);
                    mCloseList.add(mCloseList.size(), position);
                } else {
                    holder.mRvUnlockDetail.setVisibility(View.GONE);
                    for (int i = 0; i < mCloseList.size(); i++) {
                        if (mCloseList.get(i) == position) {
                            mCloseList.remove(i);
                            i--;
                        }
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class UnLockHodler extends RecyclerView.ViewHolder {

        private final TextView mPlatformTv;
        private final RecyclerView mRvUnlockDetail;
        private final LinearLayout mLlUnlock;

        public UnLockHodler(View itemView) {
            super(itemView);
            mPlatformTv = itemView.findViewById(R.id.platform_tv);
            mRvUnlockDetail = itemView.findViewById(R.id.rv_unlock_detail);
            mLlUnlock = itemView.findViewById(R.id.ll_unlock);
        }
    }

    public class UnlockDetailAdpter extends RecyclerView.Adapter<UnlockDetailAdpter.UnlockDetailHodler> {
        private Context mContext;
        private ArrayList<BTestdata.detailbean> mList;

        public UnlockDetailAdpter(Context mContext, ArrayList<BTestdata.detailbean> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public UnlockDetailHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.item_unlock_detail, parent, false);
            UnlockDetailHodler unlockDetailHodler = new UnlockDetailHodler(view);
            return unlockDetailHodler;
        }

        @Override
        public void onBindViewHolder(UnlockDetailHodler holder, int position) {
            holder.mLicense_tv.setText(mList.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class UnlockDetailHodler extends RecyclerView.ViewHolder {

            private final TextView mLicense_tv;

            public UnlockDetailHodler(View itemView) {
                super(itemView);
                mLicense_tv = itemView.findViewById(R.id.license_tv);
            }
        }
    }
}
