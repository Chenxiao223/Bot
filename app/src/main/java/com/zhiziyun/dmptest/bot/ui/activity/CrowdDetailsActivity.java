package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private TextView tv_crowd, tv_date, tv_state, tv_effective, tv_completed, tv_dupDev, tv_addedGuest, tv_expectedGuest;
    private Intent intent;
    private LinearLayout line_page;
    private RelativeLayout rl_device;

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
        tv_effective = (TextView) findViewById(R.id.tv_effective);
        tv_completed = (TextView) findViewById(R.id.tv_completed);
        tv_dupDev = (TextView) findViewById(R.id.tv_dupDev);
        tv_addedGuest = (TextView) findViewById(R.id.tv_addedGuest);
        tv_expectedGuest = (TextView) findViewById(R.id.tv_expectedGuest);
        rl_device = findViewById(R.id.rl_device);

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
                    json.put("segmentType", intent.getStringExtra("segmentType"));
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
                            .url(BaseUrl.BaseTest + "deviceSegmentInfo/probetrans")
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
                    try {
                        CrowdDetails cd = (CrowdDetails) msg.obj;
                        if (cd.isSuccess()) {
                            try {
                                line_page.setVisibility(View.GONE);
                                tv_crowd.setText(cd.getObj().getName());
                                tv_date.setText(cd.getObj().getCreateTime());
                                tv_state.setText(getState(cd.getObj().getStatus()));
                                tv_effective.setText(cd.getObj().getEffectiveDevCount() + "个");
                                tv_completed.setText(cd.getObj().getCompletedDevCount() + "个");
                                if (cd.getObj().getDupDevCount() == 0) {//如果重复数为0,就隐藏这一项
                                    rl_device.setVisibility(View.GONE);
                                } else {
                                    tv_dupDev.setText(cd.getObj().getDupDevCount() + "个");
                                }
                                tv_addedGuest.setText(cd.getObj().getCount() + "个");
                                tv_expectedGuest.setText(cd.getObj().getExpectedGuestCount() + "个");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShort(CrowdDetailsActivity.this, "无数据");
                            line_page.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                    tv_crowd = null;
                    tv_date = null;
                    tv_state = null;
                    tv_effective = null;
                    tv_completed = null;
                    tv_dupDev = null;
                    tv_addedGuest = null;
                    tv_expectedGuest = null;
                    line_page = null;
                    intent = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
