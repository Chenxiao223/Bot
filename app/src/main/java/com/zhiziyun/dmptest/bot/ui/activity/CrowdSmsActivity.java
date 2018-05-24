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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
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
    private CrowdSms crowd;
    private final int FLAG = 1702;
    private int page = 1;
    private int flag = 0;
    private LinearLayout line_page;

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
        smartRefreshLayout.setEnableLoadmore(false);//屏蔽掉上拉加载的效果
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

    }

    public void requestCrowd(final int page) {
        //移动人群查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("activityType", 2);
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
                            .url(BaseUrl.BaseZhang + "customMobileGroupApp/getCustomMobileGroups")
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
                                crowd = gson.fromJson(response.body().string(), CrowdSms.class);
                                if (crowd != null) {
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
                        if (crowd.getResponse().getData().size() == 0) {
                            line_page.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            smartRefreshLayout.finishLoadmore(0);//停止刷新
                            ToastUtils.showShort(CrowdSmsActivity.this, "无数据");
                        } else {
                            for (int i = 0; i < crowd.getResponse().getData().size(); i++) {
                                list.add(crowd.getResponse().getData().get(i).getCustomMobileGroupName());
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
                                Intent intent = new Intent();
                                intent.putExtra("crowdname", crowd.getResponse().getData().get(position).getCustomMobileGroupName());
                                intent.putExtra("crowd", crowd.getResponse().getData().get(position).getCustomMobileGroupId());
                                setResult(FLAG, intent);
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
                startActivity(new Intent(CrowdSmsActivity.this, AddCorwdActivity.class));
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
                    crowd = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
