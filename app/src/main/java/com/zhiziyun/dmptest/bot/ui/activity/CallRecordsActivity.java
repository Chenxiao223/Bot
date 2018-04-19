package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CallRecordsGAdapter;
import com.zhiziyun.dmptest.bot.entity.CallRecords;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
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
 * Created by Administrator on 2018/4/8.
 * 通话记录
 */

public class CallRecordsActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_call;
    private SharedPreferences share;
    private int pageNum = 1;
    private CallRecords callRecords;
    private HashMap<String, String> hm_groups;
    private ArrayList<HashMap<String, String>> list_groups = new ArrayList<>();
    private CallRecordsGAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;
    private LinearLayout line_page;
    private String beginTime;
    private String endTime;
    private MyDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_records);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (hm_groups == null) {//根据这个值来判断是第一次进来还是第二次进来
            //加载动画
            dialog = MyDialog.showDialog(this);
            dialog.show();
            getCallRecords(pageNum);
            //第二个参数为空就是查所有
        } else {//第二次
            dialog.show();
            hm_groups.clear();
            clearAllData();
            getCallRecords(pageNum);
        }
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        lv_call = (ListView) findViewById(R.id.lv_call);
        adapter = new CallRecordsGAdapter(CallRecordsActivity.this, list_groups);
        lv_call.setAdapter(adapter);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_date).setOnClickListener(this);
        beginTime = getDateMon();
        endTime = gettodayDate();
        lv_call.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent intent = new Intent(CallRecordsActivity.this, CallRecordsCActivity.class);
                    intent.putExtra("date", callRecords.getRows().get(position).getSettleDate());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getCallRecords(pageNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                try {
                    if ((callRecords.getTotal() - (pageNum - 1) * 10) > 0) {
                        getCallRecords(pageNum);
                    } else {
                        ToastUtils.showShort(CallRecordsActivity.this, "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void clearAllData() {
        try {
            hm_groups.clear();
            list_groups.clear();
            pageNum = 1;
            callRecords = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCallRecords(final int pageNum) {
        //获取通话记录（汇总值）
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("startSettleDate", beginTime);
                    json.put("endSettleDate", endTime);
                    json.put("page", pageNum);
                    json.put("rows", 10);
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "dial/listPhoneLineSum.action")
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
                                callRecords = gson.fromJson(response.body().string(), CallRecords.class);
                                Message message = new Message();
                                message.what = 1;
                                message.obj = callRecords;
                                handler.sendMessage(message);
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
                        CallRecords cr = (CallRecords) msg.obj;
                        if (cr.getRows().size() == 0) {
                            ToastUtils.showShort(CallRecordsActivity.this, "无数据");
                            line_page.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            smartRefreshLayout.finishRefresh(0);//停止刷新
                            smartRefreshLayout.finishLoadmore(0);//停止加载
                        } else {
                            for (int i = 0; i < cr.getRows().size(); i++) {
                                hm_groups = new HashMap<>();
                                hm_groups.put("content1", cr.getRows().get(i).getSettleDate().toString());
                                hm_groups.put("content2", "花费:" + cr.getRows().get(i).getFee() + "元");
                                list_groups.add(hm_groups);
                            }
                            pageNum++;
                            line_page.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            smartRefreshLayout.finishRefresh(0);//停止刷新
                            smartRefreshLayout.finishLoadmore(0);//停止加载
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

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

    public String date(String date) {
        int index1 = date.indexOf("-");
        int index2 = index1 + date.substring(date.indexOf("-") + 1).indexOf("-") + 1;
        String year = date.substring(0, index1);
        String month = date.substring(index1 + 1, index2).length() == 1 ? "0" + date.substring(index1 + 1, index2) : date.substring(index1 + 1, index2);
        String day = date.substring(index2 + 1).length() == 1 ? "0" + date.substring(index2 + 1) : date.substring(index2 + 1);
        return year + "-" + month + "-" + day;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.line_page:
                try {
                    clearAllData();
                    getCallRecords(pageNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
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

                        try {
                            clearAllData();
                            getCallRecords(pageNum);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
        }
    }

}
