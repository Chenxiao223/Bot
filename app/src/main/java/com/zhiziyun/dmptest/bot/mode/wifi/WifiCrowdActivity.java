package com.zhiziyun.dmptest.bot.mode.wifi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.originality.uploadmaterial.MaterialAdapter;
import com.zhiziyun.dmptest.bot.mode.wifi.store.BStoreResult;
import com.zhiziyun.dmptest.bot.mode.wifi.store.BWifiResult;
import com.zhiziyun.dmptest.bot.mode.wifi.store.StoreCase;
import com.zhiziyun.dmptest.bot.mode.wifi.store.Wificase;
import com.zhiziyun.dmptest.bot.network.subscriber.PureSubscriber;
import com.zhiziyun.dmptest.bot.ui.activity.BaseActivity;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.zhiziyun.dmptest.bot.ui.activity.BaseActivity.getStatusBarHeight;


public class WifiCrowdActivity extends BaseActivity implements View.OnClickListener, StoreAdapter.ItemClickListener {
    private RecyclerView mChooseCrowdRv;
    private SharedPreferences share;
    private int page = 1, rows = 10, mNumTotal;
    private List<BWifiResult.RowsBean> mBStoreResults;
    private List<BWifiResult.RowsBean> mTotal;
    private WifiAdapter mWifiAdapter;
    private Button mSureSubmitBt;
    private int mPosition = -1;
    private SmartRefreshLayout mSmartRefreshLayout;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_crowd);
        dialog = MyDialog.showDialog(this);
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        mChooseCrowdRv = findViewById(R.id.rv_choose_crowd);
        mSureSubmitBt = findViewById(R.id.complete_bt);
        mSmartRefreshLayout = findViewById(R.id.friend_refreshLayout);
        mSureSubmitBt.setOnClickListener(this);
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        mTotal = new ArrayList<>();
        loadData(page);
        smartRefreshListener();
    }

    private void smartRefreshListener() {
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                refreshlayout.finishLoadmore();
                if ((mNumTotal - (page - 1) * 10) > 0) {
                    loadData(page);
                    ToastUtils.showShort(WifiCrowdActivity.this, page + "/" + ((mNumTotal / 10) + 1));
                } else {
                    refreshlayout.finishLoadmore();
                    ToastUtils.showShort(WifiCrowdActivity.this, "最后一页了");
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mTotal.clear();
                loadData(page);
                refreshlayout.finishRefresh();
            }
        });
    }

    public void loadData(int page) {
        try {
            dialog.show();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("siteId", share.getString("siteid", ""));
            jsonObject.put("page", page);
            jsonObject.put("rows", rows);
            jsonObject.put("sort", "updateTime");
            jsonObject.put("order", "desc");
            new Wificase(1, URLEncoder.encode(Token.gettoken(), "utf-8"), jsonObject.toString()).execute(new PureSubscriber<BWifiResult>() {
                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(BWifiResult data) {
                    dialog.dismiss();
                    mNumTotal = data.getTotal();
                    mBStoreResults = new ArrayList<>();
                    mBStoreResults = data.getRows();
                    mTotal.addAll(mBStoreResults);
                    for (int i = 0; i < mTotal.size(); i++) {
                        mTotal.get(i).setChecked(false);
                    }
                    if (mWifiAdapter == null) {
                        initRecycleView();
                    }
                    mWifiAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initRecycleView() {
        mWifiAdapter = new WifiAdapter(mTotal, this);
//        mStoreAdapter.setItemClickDataListener(this);
        mWifiAdapter.setItemClickListener(this);
        mChooseCrowdRv.setLayoutManager(new LinearLayoutManager(this));
        mChooseCrowdRv.setAdapter(mWifiAdapter);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < mTotal.size(); i++) {
            if (mTotal.get(i).isChecked()) {
                Intent intent = new Intent();
                intent.putExtra("id", mTotal.get(i).getId());
                setResult(13, intent);
                finish();
            }
        }
    }

    @Override
    public void OnItemClick(View v, int position) {
        switch (v.getId()) {
            case R.id.rl_check:
                if (mPosition == position) {
                    mTotal.get(mPosition).setChecked(false);
                    mWifiAdapter.notifyItemChanged(position);
                    mPosition = -1;
                } else if (mPosition != position && mPosition != -1) {
                    //先取消上个item的勾选状态
                    mTotal.get(mPosition).setChecked(false);
                    mWifiAdapter.notifyItemChanged(mPosition);
                    //设置新Item的勾选状态
                    mTotal.get(position).setChecked(true);
                    mPosition = position;
                    mWifiAdapter.notifyItemChanged(position);
                } else if (mPosition == -1) {
                    mPosition = position;
                    mTotal.get(position).setChecked(true);
                    mWifiAdapter.notifyItemChanged(mPosition);
                }
                break;
        }
    }
}
