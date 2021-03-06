package com.zhiziyun.dmptest.bot.mode.acount;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.mode.acount.request.BFinancialResult;
import com.zhiziyun.dmptest.bot.network.subscriber.PureSubscriber;
import com.zhiziyun.dmptest.bot.ui.activity.BaseActivity;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
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


public class FinancialActivity extends BaseActivity implements View.OnClickListener {
    private String beginTime;
    private String endTime;
    public TextView tv_beginTime, tv_endTime, tv_shop;
    private MyDialog dialog;
    private SharedPreferences share;
    private RecyclerView mFinancialRv;
    private FinancialAdapter mFinancialAdapter;
    private List<BFinancialResult.ResponseBean.DataBean> mBFinancialResult;
    private List<BFinancialResult.ResponseBean.DataBean> mTotal;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private LinearLayout mLine_page;
    private int mNumTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        mTotal = new ArrayList<>();
        initview();
    }

    public void initview() {
        tv_beginTime = findViewById(R.id.tv_beginTime);
        tv_endTime = findViewById(R.id.tv_endTime);
        tv_shop = findViewById(R.id.tv_shop);
        mLine_page = findViewById(R.id.line_page);
        mFinancialRv = findViewById(R.id.financial_rv);
        findViewById(R.id.iv_date).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        beginTime = getDateMon();
        endTime = gettodayDate();
        tv_beginTime.setText(beginTime);
        tv_endTime.setText(endTime);
        loadData();
        getAllData();
    }

    public void loadData() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startDate", beginTime);
            jsonObject.put("endDate", endTime);
            jsonObject.put("accountid", share.getString("accountid", ""));
            dialog = MyDialog.showDialog(FinancialActivity.this);
            dialog.SHOW();
            new FinancialCase(1, URLEncoder.encode(Token.gettoken(), "utf-8"), jsonObject.toString()).execute(new PureSubscriber<BFinancialResult>() {
                @Override
                public void onFailure(Throwable throwable) {
                    dialog.dismiss();
                    mFinancialRv.setVisibility(View.GONE);
                    mLine_page.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuccess(BFinancialResult data) {
                    dialog.dismiss();
                    mBFinancialResult = new ArrayList<>();
                    mBFinancialResult = data.getResponse().getData();
                    mNumTotal = data.getResponse().getTotal();
                    mTotal.addAll(mBFinancialResult);
                    if (mNumTotal == 0) {
                        mFinancialRv.setVisibility(View.GONE);
                        mLine_page.setVisibility(View.VISIBLE);
                    } else {
                        mFinancialRv.setVisibility(View.VISIBLE);
                        mLine_page.setVisibility(View.GONE);
                        initRecycleView();
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
                            loadData();
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

    public void initRecycleView() {
        mFinancialAdapter = new FinancialAdapter(this, mTotal, beginTime, list);
        mFinancialRv.setLayoutManager(new LinearLayoutManager(this));
        mFinancialRv.setAdapter(mFinancialAdapter);
        mFinancialAdapter.notifyDataSetChanged();
        mFinancialAdapter = null;
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
                            hashMap.put("boolean", false);
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
}
