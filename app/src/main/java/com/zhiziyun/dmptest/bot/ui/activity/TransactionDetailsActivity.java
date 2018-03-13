package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.TransactionDetailsAdapter;
import com.zhiziyun.dmptest.bot.entity.TransactionDetails;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/2.
 * 交易明细页
 */

public class TransactionDetailsActivity extends BaseActivity implements View.OnClickListener {
    private String beginTime;
    private String endTime;
    private SharedPreferences share;
    private ListView xlistview;
    private TransactionDetails td;
    private HashMap<String, String> hm_td;
    private ArrayList<HashMap<String, String>> list_td = new ArrayList<>();
    private TransactionDetailsAdapter adapter;
    private int pageNum = 1;
    private SmartRefreshLayout smartRefreshLayout;
    private MyDialog dialog;
    private LinearLayout line_page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        initView();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        line_page = this.findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        TextView tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setOnClickListener(this);
        ImageView tv_back = (ImageView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        beginTime = gettodayDate();
        endTime = beginTime;

        xlistview = (ListView) findViewById(R.id.xlistview);
        adapter = new TransactionDetailsAdapter(this, list_td);
        xlistview.setAdapter(adapter);

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    hm_td.clear();
                    clearAllData();
                    getData(pageNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if ((td.getResponse().getTotal() - (pageNum - 1) * 10) > 0) {
                    getData(pageNum);
                } else {
                    smartRefreshLayout.finishLoadmore(0);
                    ToastUtils.showShort(TransactionDetailsActivity.this, "最后一页了");
                }
            }
        });
        //加载动画
        dialog = MyDialog.showDialog(TransactionDetailsActivity.this);
        dialog.SHOW();
        getData(1);
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
                        beginTime = date(textString.substring(0, index));
                        endTime = date(textString.substring(index + 1, textString.length()));

                        pageNum = 1;
                        list_td.clear();
                        dialog.SHOW();
                        getData(pageNum);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
            case R.id.tv_back:
                toFinish();
                finish();
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        clearAllData();
                        getData(pageNum);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    @Override
    public void onBackPressed() {
        toFinish();
        finish();
    }

    public void getData(final int page) {
        //结算账户消费详情
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("accountid", share.getString("accountid", ""));
                    json.put("startDate", beginTime);
                    json.put("endDate", endTime);
                    json.put("page", page);
                    json.put("row", 10);
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "advertiserApp/accountDetail")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                Gson gson = new Gson();
                                td = gson.fromJson(response.body().string(), TransactionDetails.class);
                                handler.sendEmptyMessage(1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        if (td.getResponse().getData() != null) {
                            if (td.getResponse().getData().size() == 0) {//如果没数据
                                ToastUtils.showShort(TransactionDetailsActivity.this, "无数据");
                                line_page.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                smartRefreshLayout.finishRefresh(0);//停止刷新
                                smartRefreshLayout.finishLoadmore(0);//停止加载
                            } else {
                                for (int i = 0; i < td.getResponse().getData().size(); i++) {
                                    hm_td = new HashMap<>();
                                    hm_td.put("content1", td.getResponse().getData().get(i).getSettleType());
                                    hm_td.put("content2", td.getResponse().getData().get(i).getSettleDate());
                                    hm_td.put("content3", td.getResponse().getData().get(i).getFee());
                                    list_td.add(hm_td);
                                }
                                pageNum++;
                                adapter.notifyDataSetChanged();
                                line_page.setVisibility(View.GONE);
                            }
                            dialog.dismiss();
                            smartRefreshLayout.finishRefresh(0);//停止刷新
                            smartRefreshLayout.finishLoadmore(0);//停止加载
                        }
                    } catch (Exception e) {
                        ToastUtils.showShort(TransactionDetailsActivity.this, td.getErrormsg());
                        dialog.dismiss();
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    //清空所有数据
    public void clearAllData() {
        pageNum = 1;
        list_td.clear();
    }

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    //清空内存
    private void toFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    beginTime = null;
                    endTime = null;
                    share = null;
                    td = null;
                    hm_td.clear();
                    list_td.clear();
                    adapter = null;
                    xlistview.setAdapter(null);
                    smartRefreshLayout = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
