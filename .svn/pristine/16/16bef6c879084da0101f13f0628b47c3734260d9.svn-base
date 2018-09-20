package com.zhiziyun.dmptest.bot.mode.acount;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.ItemClickListener;
import com.zhiziyun.dmptest.bot.mode.acount.request.BRechargeDetailResult;
import com.zhiziyun.dmptest.bot.network.subscriber.PureSubscriber;
import com.zhiziyun.dmptest.bot.ui.activity.BaseActivity;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FinancialDetailActivity extends BaseActivity implements View.OnClickListener, ItemClickListener {
    private String beginTime;
    private String endTime;
    public TextView tv_beginTime, tv_endTime, tv_shop;
    private AccountDetailAdapter mAccountDetailAdapter;
    private List<BRechargeDetailResult.ResponseBean.DataBean> mDataBeen;
    private List<BRechargeDetailResult.ResponseBean.DataBean> mTotal;
    private int mNumTotal;
    private MyDialog dialog;
    private SharedPreferences share;
    private RecyclerView mFinancialDetail;
    private int mSetType;
    private int mPage = 1;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private String mNameType, mTime;
    private RecyclerView recyclerView;
    private PopupWindow mPopWindow;
    private AllTypeAdapter mAllTypeAdapter;
    private int width;
    private LinearLayout mLine_shop, mLine_page;
    private RelativeLayout mRelativeLayout;
    private SmartRefreshLayout mSmartRefreshLayout;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_detail);
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        mSetType = Integer.valueOf(getIntent().getStringExtra("number"));
        mNameType = getIntent().getStringExtra("type");
        beginTime = getIntent().getStringExtra("mTime");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        width = metrics.widthPixels;
        initview();
    }

    public void initview() {
        mTotal = new ArrayList<>();
        mSmartRefreshLayout = findViewById(R.id.account_refreshLayout);
        tv_beginTime = findViewById(R.id.tv_beginTime);
        tv_endTime = findViewById(R.id.tv_endTime);
        tv_shop = findViewById(R.id.tv_shop);
        mLine_page = findViewById(R.id.line_page);
        tv_shop.setText(mNameType);
        mFinancialDetail = findViewById(R.id.financial_ddetail_rv);
        findViewById(R.id.iv_date).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        mRelativeLayout = findViewById(R.id.relativeLayout);
        mLine_shop = findViewById(R.id.line_shop);
        mLine_shop.setOnClickListener(this);
//        beginTime = getDateMon();
        endTime = gettodayDate();
        tv_beginTime.setText(beginTime);
        tv_endTime.setText(endTime);
        loaData(mSetType, mPage);
        smartRefreshListener();
        getAllData();
    }

    public void loaData(int settleType, int mPage) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startDate", beginTime);
            jsonObject.put("endDate", endTime);
            jsonObject.put("settleType", settleType);
            jsonObject.put("accountid", share.getString("accountid", ""));
            jsonObject.put("page", mPage);
            jsonObject.put("row", 10);
            dialog = MyDialog.showDialog(FinancialDetailActivity.this);
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
                    if (mNumTotal == 0) {
                        mLine_page.setVisibility(View.VISIBLE);
                        mSmartRefreshLayout.setVisibility(View.GONE);
                    } else {
                        mSmartRefreshLayout.setVisibility(View.VISIBLE);
                        mLine_page.setVisibility(View.GONE);
                        mTotal.addAll(mDataBeen);
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
                            loaData(mSetType, 1);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.line_shop:
                initPopwindow();
                mPopWindow.showAsDropDown(mRelativeLayout, 0, 0);
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

    public void intiRecycleView() {
        mAccountDetailAdapter = new AccountDetailAdapter(this, mTotal);
        mFinancialDetail.setLayoutManager(new LinearLayoutManager(this));
        mFinancialDetail.setAdapter(mAccountDetailAdapter);
    }

    public void getAllData() {
        try {
            new AllTypeCase(1, URLEncoder.encode(Token.gettoken(), "utf-8")).execute(new PureSubscriber<String>() {
                @Override
                public void onFailure(Throwable throwable) {
                }

                @Override
                public void onSuccess(String data) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(data);
                        HashMap<String, String> maps = JSON.parseObject(json.get("response").toString(), HashMap.class);
                        for (Object map : maps.entrySet()) {
                            String key = ((Map.Entry) map).getKey().toString();
                            String value = ((Map.Entry) map).getValue().toString();
                            HashMap hashMap = new HashMap<>();
                            hashMap.put("typename", value);
                            hashMap.put("typenumber", key);
                            if (value.equals(mNameType)) {
                                hashMap.put("boolean", true);
                            } else {
                                hashMap.put("boolean", false);
                            }
                            list.add(hashMap);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //   初始化popWindow
    public void initPopwindow() {
        //让背景变暗
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.7f;
        this.getWindow().setAttributes(lp);
        View contentView = LayoutInflater.from(FinancialDetailActivity.this).inflate(R.layout.popu_recycle_view, null);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.popurecycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(FinancialDetailActivity.this));
        // 是否具有固定大小
        mAllTypeAdapter = new AllTypeAdapter(this, list);
        mAllTypeAdapter.setItemClickListener(this);
        recyclerView.setAdapter(mAllTypeAdapter);
        mPopWindow = new PopupWindow(contentView);
        float scale = getResources().getDisplayMetrics().density;
//        判断popWindow的大小，高度，宽度及颜色
//        int x = historyUserInfo.size() < 4 ? historyUserInfo.size() : 4;
//        mPopWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.alpha_all)));
        mPopWindow.setHeight((int) (6 * 50 * scale + 0.5f));
        mPopWindow.setWidth(width);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        mPopWindow.setAnimationStyle(R.style.popwin_anim);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void OnItemClick(View v, int position) {
        switch (v.getId()) {
            case R.id.all_financial_ll:
                mSetType = Integer.valueOf(list.get(position).get("typenumber").toString());
//                将数据全部置为未选中状态点击的设为选中状态
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).put("boolean", false);
                }
                list.get(position).put("boolean", true);
                mPopWindow.dismiss();
                mPage = 1;
//                清空数据防止数据叠加
                mTotal.clear();
                tv_shop.setText(list.get(position).get("typename").toString());
                loaData(mSetType, mPage);
                break;
        }
    }

    private void smartRefreshListener() {
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                if ((mNumTotal - (mPage - 1) * 10) > 0) {
                    loaData(mSetType, mPage);
                    refreshlayout.finishLoadmore();
                    ToastUtils.showShort(FinancialDetailActivity.this, mPage + "/" + ((mNumTotal / 10) + 1));
                } else {
                    refreshlayout.finishLoadmore();
                    ToastUtils.showShort(FinancialDetailActivity.this, "最后一页了");
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mTotal.clear();
                loaData(mSetType, mPage);
                refreshlayout.finishRefresh();
            }
        });
    }
}
