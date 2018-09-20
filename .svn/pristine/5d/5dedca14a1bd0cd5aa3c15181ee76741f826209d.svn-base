package com.zhiziyun.dmptest.bot.mode.acount;

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
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.acount.request.BRechargeDetailResult;
import com.zhiziyun.dmptest.bot.mode.acount.request.BRechargeResult;
import com.zhiziyun.dmptest.bot.mode.originality.friend.FriendMoneyDetailActivity;
import com.zhiziyun.dmptest.bot.mode.originality.uploadmaterial.BCreateDetailResult;
import com.zhiziyun.dmptest.bot.network.subscriber.PureSubscriber;
import com.zhiziyun.dmptest.bot.ui.activity.BaseActivity;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AccountTypeActivity extends BaseActivity implements View.OnClickListener {
    private String beginTime;
    private String endTime;
    private TextView tv_beginTime, tv_endTime, tv_shop;
    private SharedPreferences share;
    private int mPage = 1;
    private int mRecharge;
    private RecyclerView mAccountRv;
    private List<BRechargeDetailResult.ResponseBean.DataBean> mDataBeen;
    private List<BRechargeDetailResult.ResponseBean.DataBean> mTotal;
    private AccountDetailAdapter mAccountDetailAdapter;
    private SmartRefreshLayout mSmartRefreshLayout;
    private int mNumTotal;
    private MyDialog dialog;
    private LinearLayout mLine_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        mSmartRefreshLayout = findViewById(R.id.account_refreshLayout);
        mTotal = new ArrayList<>();
        smartRefreshListener();
        initview();
    }

    public void initview() {
        tv_beginTime = findViewById(R.id.tv_beginTime);
        tv_endTime = findViewById(R.id.tv_endTime);
        tv_shop = findViewById(R.id.tv_shop);
        mLine_page = findViewById(R.id.line_page);
        mAccountRv = findViewById(R.id.account_rv);
        findViewById(R.id.iv_date).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        beginTime = getDateMon();
        endTime = gettodayDate();
        tv_beginTime.setText(beginTime);
        tv_endTime.setText(endTime);
        loaData();
    }

    public void intiRecycleView() {
        mAccountDetailAdapter = new AccountDetailAdapter(this, mTotal);
        mAccountRv.setLayoutManager(new LinearLayoutManager(this));
        mAccountRv.setAdapter(mAccountDetailAdapter);
    }

    public void loaData() {
        try {
            new AcountTypeCase(1, URLEncoder.encode(Token.gettoken(), "utf-8")).execute(new PureSubscriber<BRechargeResult>() {
                @Override
                public void onFailure(Throwable throwable) {
                }

                @Override
                public void onSuccess(BRechargeResult data) {
                    recharge(2, 1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recharge(int settleType, int mPage) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startDate", beginTime);
            jsonObject.put("endDate", endTime);
            jsonObject.put("settleType", 2);
            jsonObject.put("accountid", share.getString("accountid", ""));
            jsonObject.put("page", mPage);
            jsonObject.put("row", 10);
            dialog = MyDialog.showDialog(AccountTypeActivity.this);
            dialog.SHOW();
            new RechargeCase(1, URLEncoder.encode(Token.gettoken(), "utf-8"), jsonObject.toString()).execute(new PureSubscriber<BRechargeDetailResult>() {
                @Override
                public void onFailure(Throwable throwable) {
                    dialog.dismiss();
                    mLine_page.setVisibility(View.VISIBLE);
                    mSmartRefreshLayout.setVisibility(View.GONE);
                }

                @Override
                public void onSuccess(BRechargeDetailResult data) {
                    dialog.dismiss();
                    mDataBeen = new ArrayList<BRechargeDetailResult.ResponseBean.DataBean>();
                    mDataBeen = data.getResponse().getData();
                    mNumTotal = data.getResponse().getTotal();
                    mTotal.addAll(mDataBeen);
                    if (mNumTotal == 0) {
                        mLine_page.setVisibility(View.VISIBLE);
                        mSmartRefreshLayout.setVisibility(View.GONE);
                    } else {
                        mSmartRefreshLayout.setVisibility(View.VISIBLE);
                        mLine_page.setVisibility(View.GONE);
                        if (mAccountDetailAdapter == null) {
                            intiRecycleView();
                        }
                        mAccountDetailAdapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getDateMon() {
        //过去一月
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_date:
                try {
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
                            beginTime = date(textString.substring(0, index));
                            endTime = date(textString.substring(index + 1, textString.length()));
                            tv_beginTime.setText(beginTime);
                            tv_endTime.setText(endTime);
                            mTotal.clear();
                            recharge(2, 1);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    public String date(String date) {
        int index1 = date.indexOf("-");
        int index2 = index1 + date.substring(date.indexOf("-") + 1).indexOf("-") + 1;
        String year = date.substring(0, index1);
        String month = date.substring(index1 + 1, index2).length() == 1 ? "0" + date.substring(index1 + 1, index2) : date.substring(index1 + 1, index2);
        String day = date.substring(index2 + 1).length() == 1 ? "0" + date.substring(index2 + 1) : date.substring(index2 + 1);
        return year + "-" + month + "-" + day;
    }

    private void smartRefreshListener() {
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                if ((mNumTotal - (mPage - 1) * 10) > 0) {
                    recharge(2, mPage);
                    refreshlayout.finishLoadmore();
                    ToastUtils.showShort(AccountTypeActivity.this, mPage + "/" + ((mNumTotal / 10) + 1));
                } else {
                    refreshlayout.finishLoadmore();
                    ToastUtils.showShort(AccountTypeActivity.this, "最后一页了");
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mTotal.clear();
                recharge(2, mPage);
                refreshlayout.finishRefresh();

            }
        });
    }
}
