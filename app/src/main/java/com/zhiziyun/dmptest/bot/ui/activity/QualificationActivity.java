package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.Qualification;
import com.zhiziyun.dmptest.bot.entity.QueryStatus;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/8.
 * 资质页面
 */

public class QualificationActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences share;
    private TextView tv_business_license, tv_icp, tv_isrs, tv_bind_wechat, tv_call;
    private HashMap<String, String> hm_url = new HashMap<>();
    private final int flag_a = 5342;
    private final int flag_b = 6432;
    private final int flag_c = 9420;
    private final int flag_d = 2702;
    private LinearLayout line_friend;
    private String call_url;
    private SharedPreferences.Editor editors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);

        tv_business_license = findViewById(R.id.tv_business_license);
        tv_icp = findViewById(R.id.tv_icp);
        tv_isrs = findViewById(R.id.tv_isrs);
        tv_bind_wechat = findViewById(R.id.tv_bind_wechat);
        tv_call = findViewById(R.id.tv_call);
        line_friend = findViewById(R.id.line_friend);

        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.rl_business_license).setOnClickListener(this);
        findViewById(R.id.rl_icp).setOnClickListener(this);
        findViewById(R.id.rl_isrs).setOnClickListener(this);
        findViewById(R.id.rl_bind_wechat).setOnClickListener(this);
        findViewById(R.id.rl_call).setOnClickListener(this);

        if (!share.getBoolean("isShowTencent", false)) {//不显示朋友圈
            line_friend.setVisibility(View.GONE);
        }
        //是否绑定微信朋友圈、是否授权广告投放、是否开通微信广告主，三个条件必须同时满足
        if (share.getBoolean("isBindingWeChatSubscription", false) && share.getBoolean("isAuthorizationAd", false)
                && share.getBoolean("isOpenWeChatSubscriptionAdvertiser", false)) {
            tv_bind_wechat.setText("已完成");
        } else {
            tv_bind_wechat.setText("未完成");
        }
    }

    //以便刷新页面
    @Override
    protected void onResume() {
        super.onResume();
        try {
            qualificationList();
            queryStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_call:
                Intent it_call = new Intent(this, BusinessLicenseActivity.class);
                it_call.putExtra("flag", flag_d);
                if (!TextUtils.isEmpty(call_url)) {
                    it_call.putExtra("通话资质", call_url);
                }
                it_call.putExtra("qualificationName", "通话资质");//这个只用来显示标题栏
                startActivity(it_call);
                break;
            case R.id.tv_back:
                toFinish();
                finish();
                break;
            case R.id.rl_business_license://营业执照
                Intent it_business_license = new Intent(this, BusinessLicenseActivity.class);
                if (!TextUtils.isEmpty(hm_url.get("营业执照"))) {
                    it_business_license.putExtra("营业执照", hm_url.get("营业执照"));
                }
                it_business_license.putExtra("qualificationName", "营业执照");
                it_business_license.putExtra("flag", flag_a);
                startActivity(it_business_license);
                break;
            case R.id.rl_icp://ICP备案
                Intent it_icp = new Intent(this, BusinessLicenseActivity.class);
                if (!TextUtils.isEmpty(hm_url.get("ICP备案"))) {
                    it_icp.putExtra("ICP备案", hm_url.get("ICP备案"));
                }
                it_icp.putExtra("qualificationName", "ICP备案");
                it_icp.putExtra("flag", flag_b);
                startActivity(it_icp);
                break;
            case R.id.rl_isrs://信息安全责任书
                Intent it_isrs = new Intent(this, BusinessLicenseActivity.class);
                if (!TextUtils.isEmpty(hm_url.get("信息安全责任书"))) {
                    it_isrs.putExtra("信息安全责任书", hm_url.get("信息安全责任书"));
                }
                it_isrs.putExtra("qualificationName", "信息安全责任书");
                it_isrs.putExtra("flag", flag_c);
                startActivity(it_isrs);
                break;
            case R.id.rl_bind_wechat://绑定微信公众号
                if (share.getBoolean("isBindingWeChatSubscription", false) && share.getBoolean("isAuthorizationAd", false)
                        && share.getBoolean("isOpenWeChatSubscriptionAdvertiser", false)) {
                    startActivity(new Intent(this, WeChatSubscription.class));
                } else {
                    //只要有一个条件不满足，就执行这里
                    startActivity(new Intent(this, WeChatBindActivity.class));
                }
                break;
        }
    }

    public void qualificationList() {
        //短信资质状态查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
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
                            .url(BaseUrl.BaseZhang + "api/advertiserApp/getSmsQualificationStatus")
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
                                Qualification qualification = gson.fromJson(response.body().string(), Qualification.class);
                                Message msg = new Message();
                                msg.obj = qualification;
                                if (qualification.isStatus()) {
                                    msg.what = 1;
                                    handler.sendMessage(msg);
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

    public void queryStatus() {
        //通话资质状态查询
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
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
                            .url(BaseUrl.BaseZhang + "api/callQulification/queryStatus")
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
                                QueryStatus queryStatus = gson.fromJson(response.body().string(), QueryStatus.class);
                                Message msg = new Message();
                                msg.obj = queryStatus;
                                if (queryStatus.isStatus()) {
                                    msg.what = 2;
                                    handler.sendMessage(msg);
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
                        hm_url.clear();
                        Qualification qualification = (Qualification) msg.obj;
                        for (int i = 0; i < qualification.getResponse().getQualifications().size(); i++) {
                            switch (qualification.getResponse().getQualifications().get(i).getQualificationName()) {
                                case "营业执照":
                                    if (!TextUtils.isEmpty(qualification.getResponse().getQualifications().get(i).getQualificationUrl())) {
                                        hm_url.put("营业执照", qualification.getResponse().getQualifications().get(i).getQualificationUrl());
                                        tv_business_license.setText("已上传");
                                    } else {
                                        tv_business_license.setText("未上传");
                                    }
                                    break;
                                case "ICP备案":
                                    if (!TextUtils.isEmpty(qualification.getResponse().getQualifications().get(i).getQualificationUrl())) {
                                        hm_url.put("ICP备案", qualification.getResponse().getQualifications().get(i).getQualificationUrl());
                                        tv_icp.setText("已上传");
                                    } else {
                                        tv_icp.setText("未上传");
                                    }
                                    break;
                                case "信息安全责任书":
                                    if (!TextUtils.isEmpty(qualification.getResponse().getQualifications().get(i).getQualificationUrl())) {
                                        hm_url.put("信息安全责任书", qualification.getResponse().getQualifications().get(i).getQualificationUrl());
                                        tv_isrs.setText("已上传");
                                    } else {
                                        tv_isrs.setText("未上传");
                                    }
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        QueryStatus queryStatus = (QueryStatus) msg.obj;
                        if (!queryStatus.getResponse().getQualificationStatus().equals("未提交")) {
                            tv_call.setText("已上传");
                            call_url = queryStatus.getResponse().getQualifications().get(0).getQualificationUrl();//目前只有一条数据，所以索引写成0
                        } else {
                            tv_call.setText("未上传");
                            call_url = "";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                    Glide.get(QualificationActivity.this).clearMemory();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
