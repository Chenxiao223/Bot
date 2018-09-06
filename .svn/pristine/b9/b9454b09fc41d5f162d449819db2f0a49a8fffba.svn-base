package com.zhiziyun.dmptest.bot.mode.wifi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.wifi.store.BStoreResult;
import com.zhiziyun.dmptest.bot.mode.wifi.store.BWifiResult;

import java.util.List;

/**
 * Created by zhiziyun on 2018/8/30.
 */

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.WifiHolder> {
    private List<BWifiResult.RowsBean> mRowsBeen;
    private Context mContext;
    private StoreAdapter.ItemClickDataListener<List<BStoreResult.RowsBean>> mItemClickDataListener;
    private StoreAdapter.ItemClickListener itemClickListener;


    public WifiAdapter(List<BWifiResult.RowsBean> mRowsBeen, Context mContext) {
        this.mRowsBeen = mRowsBeen;
        this.mContext = mContext;
    }

    public void setItemClickListener(StoreAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemClickDataListener(StoreAdapter.ItemClickDataListener<List<BStoreResult.RowsBean>> itemClickDataListener) {
        mItemClickDataListener = itemClickDataListener;
    }

    @Override
    public WifiAdapter.WifiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_choose_store, null);
        WifiHolder wifiHolder = new WifiHolder(view);
        return wifiHolder;
    }

    @Override
    public void onBindViewHolder(WifiHolder holder, int position) {
        holder.mStoreType.setText(mRowsBeen.get(position).getName());
        holder.itemView.setTag(R.string.app_name, position);
        holder.mChooseCheck.setChecked(mRowsBeen.get(position).isChecked());
    }


    class WifiHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mStoreType;
        private final CheckBox mChooseCheck;
        private final RelativeLayout mRlCheck;

        public WifiHolder(View itemView) {
            super(itemView);
            mStoreType = itemView.findViewById(R.id.tv_store);
            mChooseCheck = itemView.findViewById(R.id.choose_check);
            mRlCheck = itemView.findViewById(R.id.rl_check);
            mRlCheck.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.OnItemClick(v, (Integer) itemView.getTag(R.string.app_name));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mRowsBeen.size();
    }

    public interface ItemClickDataListener<T> {
        void OnItemClick(View v, List<BStoreResult.RowsBean> data);
    }

    public interface ItemClickListener {
        void OnItemClick(View v, int position);
    }
}

