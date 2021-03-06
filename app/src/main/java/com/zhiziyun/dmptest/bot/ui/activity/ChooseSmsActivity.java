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
import com.zhiziyun.dmptest.bot.adapter.ChooseSmsAdapter;
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
 * Created by Administrator on 2018/1/26.
 * 选择短信页面
 */

public class ChooseSmsActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences share;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView lv_sms;
    private ChooseSmsAdapter adapter;
    private HashMap<String, String> hash_sms;
    private ArrayList<HashMap<String, String>> list_sms = new ArrayList<>();
    private ChooseSms sms;
    private int pageNum = 1;
    private final int FLAG = 1298;
    private MyDialog dialog;
    private LinearLayout line_page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sms);
        initView();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        try {
            if (hash_sms == null) {//根据这个值来判断是第一次进来还是第二次进来
                //加载动画
                dialog = MyDialog.showDialog(this);
                dialog.show();
                getSms(pageNum);//第二个参数为空就是查所有
            } else {//第二次
                dialog.show();
                clearAllData();
                getSms(pageNum);
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

        share = this.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_add_sms).setOnClickListener(this);

        lv_sms = (ListView) findViewById(R.id.lv_sms);
        adapter = new ChooseSmsAdapter(ChooseSmsActivity.this, list_sms);
        lv_sms.setAdapter(adapter);
        lv_sms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("smsName", list_sms.get(position).get("content1"));
                intent.putExtra("smsId", list_sms.get(position).get("smsId"));
                setResult(FLAG, intent);
                finish();
            }
        });

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getSms(pageNum);
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
                    if ((sms.getTotal() - (pageNum - 1) * 10) > 0) {
                        getSms(pageNum);
                    } else {
                        ToastUtils.showShort(ChooseSmsActivity.this, "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void clearAllData() {
        try {
            pageNum = 1;
            list_sms.clear();
            hash_sms.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                toFinish();
                break;
            case R.id.tv_add_sms:
                isCreateSms();
                break;
            case R.id.line_page:
                if (ClickUtils.isFastClick()) {
                    try {
                        clearAllData();
                        getSms(pageNum);
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
                            .url(BaseUrl.BaseZhang + "api/advertiserApp/canCreateSms")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                Gson gson = new Gson();
                                IsCreateSms isCreateSms = gson.fromJson(response.body().string(), IsCreateSms.class);
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

    public void getSms(final int page) {
        //短信查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("page", page);
                    jsonObject.put("rows", 10);
                    jsonObject.put("type", getIntent().getIntExtra("type", 0));//信息类型，0短信，1闪信
                    jsonObject.put("sort", "updateTime");
                    jsonObject.put("order", "desc");//排序方式,倒序
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
                            .url(BaseUrl.BaseTest + "sms/list")
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
                    try {
                        if (sms.getRows().size() == 0) {
                            line_page.setVisibility(View.VISIBLE);
                            ToastUtils.showShort(ChooseSmsActivity.this, "无数据");
                            smartRefreshLayout.finishLoadmore(0);//停止刷新
                            dialog.dismiss();
                        } else {
                            for (int i = 0; i < sms.getRows().size(); i++) {
                                hash_sms = new HashMap<>();
                                hash_sms.put("content1", sms.getRows().get(i).getName());
                                hash_sms.put("content2", sms.getRows().get(i).getContent());
                                hash_sms.put("content3", sms.getRows().get(i).getAdVerifyStatus());
                                hash_sms.put("content4", sms.getRows().get(i).getUpdateTime());
                                hash_sms.put("smsId", sms.getRows().get(i).getEntityId());
                                list_sms.add(hash_sms);
                            }
                            pageNum++;
                            line_page.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    line_page.setVisibility(View.VISIBLE);
                    ToastUtils.showShort(ChooseSmsActivity.this, "无数据");
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    dialog.dismiss();
                    break;
                case 3:
                    final SelfDialog selfDialog = new SelfDialog(ChooseSmsActivity.this);
                    selfDialog.setTitle("提示");
                    selfDialog.setMessage("发短信需要信息安全责任书照片，请上传");
                    selfDialog.setYesOnclickListener("知道了", new SelfDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            startActivity(new Intent(ChooseSmsActivity.this, AddQualificationActivity.class));
                            selfDialog.dismiss();
                        }
                    });
                    selfDialog.show();
                    selfDialog.setCancelable(false);//禁止点击回退键
                    break;
                case 4:
                    startActivity(new Intent(ChooseSmsActivity.this, EditSmsActivity.class));
                    break;
            }
        }
    };

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
                    share = null;
                    lv_sms.setAdapter(null);
                    hash_sms.clear();
                    list_sms.clear();
                    sms = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
