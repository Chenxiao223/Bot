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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.CrowdList;
import com.zhiziyun.dmptest.bot.entity.CrowdSms;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/29.
 * 选择人群,短信专用
 */

public class CrowdSmsActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout smartRefreshLayout;
    private ListView lv_crowd;
    private SharedPreferences share;
    private List<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private List<String> listStr = new ArrayList<String>();
    private MyDialog dialog;
    private CrowdList crowdList;
    private final int Flag_corwd = 1702;
    private int page = 1;
    private int flag = 0;
    private LinearLayout line_page;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd_sms);
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
        intent = getIntent();
        try {
            if (list.isEmpty()) {//根据这个值来判断是第一次进来还是第二次进来
                //加载动画
                dialog = MyDialog.showDialog(this);
                dialog.show();
                requestCrowd(1);//第二个参数为空就是查所有
            } else {//第二次
                try {
                    dialog.show();
                    list.clear();
                    listStr.clear();
                    page = 1;
                    requestCrowd(page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_add_crowd).setOnClickListener(this);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        findViewById(R.id.tv_back).setOnClickListener(this);
        lv_crowd = (ListView) findViewById(R.id.lv_crowd);

        flag = getIntent().getIntExtra("flag", 0);

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    list.clear();
                    listStr.clear();
                    page = 1;
                    requestCrowd(page);
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
                    if ((crowdList.getTotal() - (page - 1) * 10) > 0) {
                        requestCrowd(page);
                    } else {
                        ToastUtils.showShort(CrowdSmsActivity.this, "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void requestCrowd(final int page) {
        switch (intent.getIntExtra("flag", 0)) {
            case 1://到店人群
                getCrowd(page);
                break;
            case 2://wifi人群
                getWifiCrowd(page);
                break;
            case 3://点击人群
                getClickCrowd(page);
                break;
        }
    }

    public void getCrowd(final int page) {
        //到店人群列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("page", page);
                    json.put("rows", 10);
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
                            .url(BaseUrl.BaseTest + "deviceSegmentInfo/segmentList")
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
                                crowdList = gson.fromJson(response.body().string(), CrowdList.class);
                                if (crowdList != null) {
                                    handler.sendEmptyMessage(1);
                                } else {
                                    handler.sendEmptyMessage(2);//无数据
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
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

    public void getWifiCrowd(final int page) {
        //wifi人群查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("page", page);
                    json.put("rows", 10);
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
                            .url(BaseUrl.BaseTest + "wifiSegmentInfo/list")
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
                                crowdList = gson.fromJson(response.body().string(), CrowdList.class);

                                if (crowdList != null) {
                                    handler.sendEmptyMessage(1);
                                } else {
                                    handler.sendEmptyMessage(2);//无数据
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
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

    public void getClickCrowd(final int page) {
        //点击人群查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("page", page);
                    json.put("rows", 10);
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
                            .url(BaseUrl.BaseTest + "adClickSegmentInfo/list")
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
                                crowdList = gson.fromJson(response.body().string(), CrowdList.class);

                                if (crowdList != null) {
                                    handler.sendEmptyMessage(1);
                                } else {
                                    handler.sendEmptyMessage(2);//无数据
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
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
                        if (crowdList.getRows().size() == 0) {
                            line_page.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            smartRefreshLayout.finishLoadmore(0);//停止刷新
                            ToastUtils.showShort(CrowdSmsActivity.this, "无数据");
                        } else {
                            for (int i = 0; i < crowdList.getRows().size(); i++) {
                                list.add(crowdList.getRows().get(i).getName());
                                listStr.add(String.valueOf(crowdList.getRows().get(i).getId()));
                            }
                            page++;
                            line_page.setVisibility(View.GONE);
                        }
                        adapter = new ArrayAdapter<String>(CrowdSmsActivity.this, android.R.layout.simple_list_item_1, list);
                        lv_crowd.setAdapter(adapter);
                        dialog.dismiss();
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        lv_crowd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent it = new Intent();
                                it.putExtra("crowdname", list.get(position));
                                it.putExtra("crowd", listStr.get(position));
                                switch (intent.getIntExtra("flag", 0)) {
                                    case 1://到店人群
                                        it.putExtra("segmentType", "PROBE");
                                        setResult(Flag_corwd, it);
                                        break;
                                    case 2://wifi人群
                                        it.putExtra("segmentType", "WIFI");
                                        setResult(Flag_corwd, it);
                                        break;
                                    case 3://点击人群
                                        it.putExtra("segmentType", "ADCLICK");
                                        setResult(Flag_corwd, it);
                                        break;
                                }
                                finish();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    line_page.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    ToastUtils.showShort(CrowdSmsActivity.this, "无数据");
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                toFinish();
                finish();
                break;
            case R.id.tv_add_crowd:
                if (intent.getIntExtra("flag", 0) == 1) {
                    startActivity(new Intent(CrowdSmsActivity.this, AddCorwdActivity.class));
                } else if (intent.getIntExtra("flag", 0) == 2) {
                    startActivity(new Intent(CrowdSmsActivity.this, AddWifiCorwdActivity.class));
                } else if (intent.getIntExtra("flag", 0) == 3) {
                    startActivity(new Intent(CrowdSmsActivity.this, AddAdvertisingCrowd.class));
                }
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        list.clear();
                        listStr.clear();
                        page = 1;
                        requestCrowd(page);
                    }
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
                    lv_crowd.setAdapter(null);
                    list.clear();
                    listStr.clear();
                    adapter = null;
                    crowdList = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
