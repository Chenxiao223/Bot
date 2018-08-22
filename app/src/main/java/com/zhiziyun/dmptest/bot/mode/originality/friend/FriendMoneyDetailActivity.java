package com.zhiziyun.dmptest.bot.mode.originality.friend;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.TradeDetailAdapter;
import com.zhiziyun.dmptest.bot.mode.originality.TradeDetailCase;
import com.zhiziyun.dmptest.bot.network.subscriber.PureSubscriber;
import com.zhiziyun.dmptest.bot.ui.activity.BaseActivity;
import com.zhiziyun.dmptest.bot.ui.activity.TransactionDetailsActivity;
import com.zhiziyun.dmptest.bot.util.DateUtil;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FriendMoneyDetailActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences share;
    private String mBeginTime, mEndTime;
    private int mPage = 1, mRow = 10;
    private RecyclerView mFriendRv;
    private List<TradeDetail> mTradeDetails;
    private List<TradeDetail> mTotal;
    private TradeDetailAdapter mTradeDetailAdapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private MyDialog dialog;
    private LinearLayout mLinePage;
    private int mNumTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_money_detail);
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        mTotal = new ArrayList<>();
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        mFriendRv = findViewById(R.id.friend_rv);
        mSmartRefreshLayout = findViewById(R.id.friend_refreshLayout);
        mLinePage = findViewById(R.id.line_page);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_date).setOnClickListener(this);
        mBeginTime = DateUtil.getDateMon();
        mEndTime = DateUtil.gettodayDate();
        smartRefreshListener();
        getData(mPage);
        dialog = MyDialog.showDialog(FriendMoneyDetailActivity.this);
        dialog.show();
    }

    public void getData(int Page) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tencentId", share.getString("tencentid", ""));
//            jsonObject.put("accountid", share.getString("accountid", ""));
            jsonObject.put("startDate", mBeginTime);
            jsonObject.put("endDate", mEndTime);
            jsonObject.put("page", Page);
            jsonObject.put("row", mRow);
            new TradeDetailCase(1, URLEncoder.encode(Token.gettoken(), "utf-8"), jsonObject.toString()).execute(new PureSubscriber<BTradeDetailResult>() {
                @Override
                public void onFailure(Throwable throwable) {
                    dialog.dismiss();
                    Toast.makeText(FriendMoneyDetailActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(BTradeDetailResult data) {
                    dialog.dismiss();
                    mNumTotal = data.getResponse().getTotal();
                    mTradeDetails = new ArrayList<TradeDetail>();
                    mTradeDetails = data.getResponse().getData();
                    mTotal.addAll(mTradeDetails);
                    if (mTradeDetailAdapter == null) {
                        initRecycleView();
                    }
                    mTradeDetailAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initRecycleView() {
        mTradeDetailAdapter = new TradeDetailAdapter(this, mTotal);
        mFriendRv.setLayoutManager(new LinearLayoutManager(this));
        mFriendRv.setAdapter(mTradeDetailAdapter);
    }

    private void smartRefreshListener() {
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                refreshlayout.finishLoadmore();
                if ((mNumTotal - (mPage - 1) * 10) > 0) {
                    getData(mPage);
                    ToastUtils.showShort(FriendMoneyDetailActivity.this, mPage + "/" + ((mNumTotal / 10) + 1));
                } else {
                    refreshlayout.finishLoadmore();
                    ToastUtils.showShort(FriendMoneyDetailActivity.this, "最后一页了");
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mTotal.clear();
                getData(mPage);
                refreshlayout.finishRefresh();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String textString = String.format("%d-%d-%d %d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        int index = textString.indexOf(" ");
                        mBeginTime = DateUtil.date(textString.substring(0, index));
                        mEndTime = DateUtil.date(textString.substring(index + 1, textString.length()));
                        mPage = 1;
                        mTotal.clear();
                        getData(mPage);
                        dialog = MyDialog.showDialog(FriendMoneyDetailActivity.this);
                        dialog.show();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
