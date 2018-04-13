package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CommunicationRecordAdapter;
import com.zhiziyun.dmptest.bot.entity.CommunicationRecord;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/12.
 * 跟进记录
 */

public class CommunicationRecordActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_record;
    private CommunicationRecordAdapter adpter;
    private HashMap<String, String> hm_record;
    private List<HashMap<String, String>> list_record = new ArrayList<>();
    private Intent intent;
    private CommunicationRecord cr;
    private LinearLayout line_page;
    private MyDialog dialog;
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_record);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (hm_record == null) {//根据这个值来判断是第一次进来还是第二次进来
            //加载动画
            dialog = MyDialog.showDialog(this);
            dialog.show();
            getRecord();
        } else {//第二次,就要清空数据
            dialog.show();
            hm_record.clear();
            clearAllData();
            getRecord();
        }
    }

    private void clearAllData() {
        try {
            list_record.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        intent = getIntent();
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        smartRefreshLayout.setEnableLoadmore(false);//屏蔽掉上拉加载的效果
        lv_record = (ListView) findViewById(R.id.lv_record);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_write).setOnClickListener(this);
        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);

        adpter = new CommunicationRecordAdapter(this, list_record);
        lv_record.setAdapter(adpter);
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getRecord();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getRecord() {
        //获取沟通记录
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    json.put("guestId", intent.getStringExtra("id"));
                    OkHttpClient client = new OkHttpClient();
                    String url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "guestFromProbe/listSchedule.action")
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
                                String str = response.body().string();
                                Log.i("infos", str);
                                Gson gson = new Gson();
                                cr = null;
                                cr = gson.fromJson(str, CommunicationRecord.class);
                                if (cr.getRows().size() != 0) {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = cr;
                                    handler.sendMessage(msg);
                                } else {
                                    handler.sendEmptyMessage(2);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    line_page.setVisibility(View.GONE);
                    CommunicationRecord c = (CommunicationRecord) msg.obj;
                    for (int i = 0; i < c.getRows().size(); i++) {
                        hm_record = new HashMap<>();
                        hm_record.put("content1", c.getRows().get(i).getDesc());
                        hm_record.put("content2", c.getRows().get(i).getTime());
                        //根据长度来判断使用哪种布局
                        if (c.getRows().get(i).getDesc().length() > 10) {
                            hm_record.put("content3", "0");
                        } else {
                            hm_record.put("content3", "1");
                        }
                        list_record.add(hm_record);
                    }
                    adpter.notifyDataSetChanged();
                    dialog.dismiss();
                    smartRefreshLayout.finishRefresh(0);//停止刷新
                    break;
                case 2:
                    line_page.setVisibility(View.VISIBLE);
                    ToastUtils.showShort(CommunicationRecordActivity.this, "无数据");
                    dialog.dismiss();
                    smartRefreshLayout.finishRefresh(0);//停止刷新
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        clearAllData();
                        getRecord();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_write:
                Intent it_write = new Intent(CommunicationRecordActivity.this, EditContentActivity.class);
                it_write.putExtra("title", "写跟进");
                it_write.putExtra("flag", 9527);
                it_write.putExtra("id", intent.getStringExtra("id"));
                it_write.putExtra("msg", "跟进内容");
                startActivity(it_write);
                break;
        }
    }
}
