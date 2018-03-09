package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.SmsListAdapter;
import com.zhiziyun.dmptest.bot.entity.ChooseSms;
import com.zhiziyun.dmptest.bot.entity.IsCreateSms;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.SelfDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
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
 * Created by Administrator on 2018/1/29.
 * 短信列表
 */

public class SmsListActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_sms;
    private SmsListAdapter adapter;
    private HashMap<String, String> hm_sms;
    private ArrayList<HashMap<String, String>> list_sms = new ArrayList<>();
    private SharedPreferences share;
    private ChooseSms sms;
    private int pageNum = 1;
    private SmartRefreshLayout smartRefreshLayout;
    private MyDialog dialog;
    private final int FLAG = 4287;
    private LinearLayout line_page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smslist);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = this.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_addsms).setOnClickListener(this);
        lv_sms = (ListView) findViewById(R.id.lv_sms);
        adapter = new SmsListAdapter(this, list_sms);
        lv_sms.setAdapter(adapter);
        lv_sms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(SmsListActivity.this, EditSmsActivity.class);
                it.putExtra("flag", FLAG);
                it.putExtra("title", list_sms.get(position).get("content1"));
                it.putExtra("smsId", list_sms.get(position).get("smsId") + "");
                it.putExtra("content", list_sms.get(position).get("content3"));
                it.putExtra("state", list_sms.get(position).get("content4"));
                startActivity(it);
            }
        });

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    smsQuery(pageNum);
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
                    if ((sms.getResponse().getTotal() - (pageNum - 1) * 10) > 0) {
                        smsQuery(pageNum);
                    } else {
                        ToastUtils.showShort(SmsListActivity.this, "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //以便刷新页面
    @Override
    protected void onResume() {
        super.onResume();
        if (hm_sms == null) {//第一次进来
            //加载动画
            dialog = MyDialog.showDialog(this);
            dialog.show();
            smsQuery(1);//第二个参数为空就是查所有
        } else {//第二次进来
            dialog.show();
            clearAllData();
            smsQuery(pageNum);
        }
    }

    public void clearAllData() {
        try {
            pageNum = 1;
            list_sms.clear();
            hm_sms.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void smsQuery(final int page) {
        //短信查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("page", page);
                    jsonObject.put("row", 10);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "smsApp/getSms")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                Gson gson = new Gson();
                                sms = gson.fromJson(response.body().string(), ChooseSms.class);
                                if (sms != null) {
                                    handler.sendEmptyMessage(1);//通知刷新适配器
                                } else {
                                    handler.sendEmptyMessage(2);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
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
                    if (sms.getResponse().getData().size() == 0) {
                        line_page.setVisibility(View.VISIBLE);
                        ToastUtils.showShort(SmsListActivity.this, "无数据");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        dialog.dismiss();
                    } else {
                        for (int i = 0; i < sms.getResponse().getData().size(); i++) {
                            hm_sms = new HashMap<>();
                            hm_sms.put("content1", sms.getResponse().getData().get(i).getSmsName());
                            hm_sms.put("content2", sms.getResponse().getData().get(i).getUpdateDate());
                            hm_sms.put("content3", sms.getResponse().getData().get(i).getSmsContent());
                            hm_sms.put("content4", sms.getResponse().getData().get(i).getSmsStatus());
                            hm_sms.put("smsId", sms.getResponse().getData().get(i).getSmsId());
                            list_sms.add(hm_sms);
                        }
                        pageNum++;
                        line_page.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    dialog.dismiss();
                    break;
                case 2:
                    line_page.setVisibility(View.VISIBLE);
                    ToastUtils.showShort(SmsListActivity.this, "无数据");
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    dialog.dismiss();
                    break;
                case 3:
                    final SelfDialog selfDialog = new SelfDialog(SmsListActivity.this);
                    selfDialog.setTitle("提示");
                    selfDialog.setMessage("发短信需要法人代表身份证照片，请上传");
                    selfDialog.setYesOnclickListener("知道了", new SelfDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            startActivity(new Intent(SmsListActivity.this, AddQualificationActivity.class));
                            selfDialog.dismiss();
                        }
                    });
                    selfDialog.show();
                    selfDialog.setCancelable(false);//禁止点击回退键
                    break;
                case 4:
                    startActivity(new Intent(SmsListActivity.this, EditSmsActivity.class));
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.iv_addsms:
                isCreateSms();
                break;
            case R.id.line_page:
                if (ClickUtils.isFastClick()) {
                    try {
                        clearAllData();
                        smsQuery(pageNum);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void isCreateSms() {
        //是否可以创建短信
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));

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
                            .url(BaseUrl.BaseZhang + "advertiserApp/canCreateSms")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String str = response.body().string();
                                Log.i("infos", str);
                                Gson gson = new Gson();
                                IsCreateSms isCreateSms = gson.fromJson(str, IsCreateSms.class);
                                if (!isCreateSms.getResponse().isCanBeCreated()) {//判断是否可以创建短信
                                    handler.sendEmptyMessage(3);
                                } else {
                                    handler.sendEmptyMessage(4);
                                }
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
                    lv_sms.setAdapter(null);
                    adapter = null;
                    hm_sms.clear();
                    list_sms.clear();
                    share = null;
                    sms = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
