package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
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
import com.zhiziyun.dmptest.bot.adapter.CallRecordsCAdapter;
import com.zhiziyun.dmptest.bot.entity.CallRecordC;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/17.
 * 通话记录子集
 */

public class CallRecordsCActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_call;
    private SharedPreferences share;
    private int pageNum = 1;
    private CallRecordC callRecordC;
    private HashMap<String, String> hm_Children;
    private ArrayList<HashMap<String, String>> list_Children = new ArrayList<>();
    private CallRecordsCAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;
    private LinearLayout line_page;
    private String date = "";
    private MyDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_records_c);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        try {
            if (hm_Children == null) {//根据这个值来判断是第一次进来还是第二次进来
                //加载动画
                dialog = MyDialog.showDialog(this);
                dialog.show();
                date = getIntent().getStringExtra("date");
                getDataChildren(pageNum, date);
                //第二个参数为空就是查所有
            } else {//第二次
                dialog.show();
                clearAllData();
                getDataChildren(pageNum, date);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        adapter = new CallRecordsCAdapter(CallRecordsCActivity.this, list_Children);
        lv_call.setAdapter(adapter);
        findViewById(R.id.iv_back).setOnClickListener(this);

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getDataChildren(pageNum, date);
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
                    if ((callRecordC.getTotal() - (pageNum - 1) * 10) > 0) {
                        getDataChildren(pageNum, date);
                    } else {
                        ToastUtils.showShort(CallRecordsCActivity.this, "最后一页了");
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
            hm_Children.clear();
            list_Children.clear();
            pageNum = 1;
            callRecordC = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataChildren(final int pageNum, final String data) {
        //通话记录（某天详细值）
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("day", data);
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
                            .url(BaseUrl.BaseWang + "dial/listPhoneLine.action")
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
                                callRecordC = gson.fromJson(response.body().string(), CallRecordC.class);
                                Message message = new Message();
                                message.what = 1;
                                message.obj = callRecordC;
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
                        CallRecordC cr = (CallRecordC) msg.obj;
                        if (cr.getRows().size() == 0) {
                            ToastUtils.showShort(CallRecordsCActivity.this, "无数据");
                            line_page.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            smartRefreshLayout.finishRefresh(0);//停止刷新
                            smartRefreshLayout.finishLoadmore(0);//停止加载
                        } else {
                            for (int i = 0; i < cr.getRows().size(); i++) {
                                hm_Children = new HashMap<>();
                                String time = cr.getRows().get(i).getAnswerTime();
                                hm_Children.put("content1", time.substring(time.indexOf(" ")));
                                if (TextUtils.isEmpty(cr.getRows().get(i).getCalltime())) {
                                    hm_Children.put("content2", "时长: 0秒");
                                } else {
                                    hm_Children.put("content2", "时长:" + cr.getRows().get(i).getCalltime());
                                }
                                hm_Children.put("content3", "电话:*******" + cr.getRows().get(i).getStrPhoneB());
                                hm_Children.put("content4", "费用:" + cr.getRows().get(i).getDeductFee() + "元");
                                list_Children.add(hm_Children);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                toFinish();
                break;
            case R.id.line_page:
                try {
                    clearAllData();
                    getDataChildren(pageNum, date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        toFinish();
        finish();
    }

    //清空内存
    private void toFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    lv_call.setAdapter(null);
                    callRecordC = null;
                    hm_Children.clear();
                    list_Children.clear();
                    line_page = null;
                    date = "";
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
