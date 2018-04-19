package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.CrowdDetails;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/4.
 * 人群详情
 */

public class CrowdDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_crowd, tv_date, tv_state, tv_equipment;
    private Intent intent;
    private LinearLayout line_page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd_details);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        intent = getIntent();
        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        tv_crowd = (TextView) findViewById(R.id.tv_crowd);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_equipment = (TextView) findViewById(R.id.tv_equipment);

        findViewById(R.id.iv_back).setOnClickListener(this);

        getData();
    }

    public void getData() {
        //查询任务详情
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", intent.getStringExtra("siteId"));
                    json.put("segmentId", Integer.parseInt(intent.getStringExtra("id")));
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
                            .url(BaseUrl.BaseWang + "deviceSegmentInfo/probetrans.action")
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
                                Message message = new Message();
                                message.what = 1;
                                message.obj = gson.fromJson(response.body().string(), CrowdDetails.class);
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
                    CrowdDetails cd = (CrowdDetails) msg.obj;
                    if (cd.isSuccess()) {
                        try {
                            line_page.setVisibility(View.GONE);
                            tv_crowd.setText(cd.getObj().getName());
                            tv_date.setText(cd.getObj().getCreateTime());
                            tv_state.setText(getState(cd.getObj().getStatus()));
                            tv_equipment.setText(cd.getObj().getCount() + "个");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.showShort(CrowdDetailsActivity.this, "无数据");
                        line_page.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };

    public String getState(int num) {
        switch (num) {
            case 0:
                return "暂停";
            case 1:
                return "正在转化";
            case 2:
                return "预算已用完";
            case 3:
                return "转化结束";
        }
        return "未知";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.line_page:
                try {
                    getData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
