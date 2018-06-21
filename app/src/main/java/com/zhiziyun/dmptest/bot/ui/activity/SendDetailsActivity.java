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
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.SmsDetailsAdapter;
import com.zhiziyun.dmptest.bot.entity.SmsDetails;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.SelfDialog;
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
 * Created by Administrator on 2018/5/25.
 * //短信发送详情
 */

public class SendDetailsActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences share;
    private ListView xlistview;
    private SmsDetails sd;
    private HashMap<String, Object> hm_td;
    private ArrayList<HashMap<String, Object>> list_td = new ArrayList<>();
    private SmsDetailsAdapter adapter;
    private int pageNum = 1;
    private SmartRefreshLayout smartRefreshLayout;
    private MyDialog dialog;
    private LinearLayout line_page;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_details);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        intent = getIntent();
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        line_page = this.findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        ImageView tv_back = (ImageView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        xlistview = (ListView) findViewById(R.id.xlistview);
        adapter = new SmsDetailsAdapter(this, list_td);
        xlistview.setAdapter(adapter);
        xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (!(boolean) list_td.get(position).get("content3")) {
                        //点击弹出对话框
                        final SelfDialog selfDialog = new SelfDialog(SendDetailsActivity.this);
                        selfDialog.setTitle("错误提示");
                        selfDialog.setMessage(list_td.get(position).get("content4").toString());
                        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.show();
                    }
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
                if ((sd.getTotal() - (pageNum - 1) * 10) > 0) {
                    getData(pageNum);
                    ToastUtils.showShort(SendDetailsActivity.this, pageNum + "/" + ((sd.getTotal() / 10) + 1));
                } else {
                    smartRefreshLayout.finishLoadmore(0);
                    ToastUtils.showShort(SendDetailsActivity.this, "最后一页了");
                }
            }
        });
        //加载动画
        dialog = MyDialog.showDialog(SendDetailsActivity.this);
        dialog.SHOW();
        getData(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                toFinish();
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

    public void getData(final int page) {
        //短信回执
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("type", intent.getIntExtra("type", 3));
                    json.put("mid", intent.getStringExtra("mid"));
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
                            .url(BaseUrl.BaseTest + "smsOrder/list")
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
                                sd = gson.fromJson(response.body().string(), SmsDetails.class);
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
                        if (sd.getRows() != null) {
                            if (sd.getRows().size() == 0) {//如果没数据
                                ToastUtils.showShort(SendDetailsActivity.this, "无数据");
                                line_page.setVisibility(View.VISIBLE);
                            } else {
                                for (int i = 0; i < sd.getRows().size(); i++) {
                                    hm_td = new HashMap<>();
                                    hm_td.put("content1", "*******" + sd.getRows().get(i).getTailPhone());
                                    hm_td.put("content2", sd.getRows().get(i).getTimestamp());
                                    hm_td.put("content3", sd.getRows().get(i).isSendSuccess());
                                    hm_td.put("content4", sd.getRows().get(i).getMessage());
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
                    sd = null;
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
