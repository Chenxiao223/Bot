package com.zhiziyun.dmptest.bot.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.Visitorsselfparticulars;
import com.zhiziyun.dmptest.bot.http.DESCoder;

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
 * Created by Administrator on 2017/11/27.
 * 访客详情页
 */

public class VisitorsselfActivity extends Activity implements View.OnClickListener {
    String token;
    private ImageView iv_head;
    private TextView tv_age, tv_marriage, tv_gender, tv_brands, tv_model, tv_did, tv_date;
    private Visitorsselfparticulars vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitorsself);

        initView();
    }

    private void initView() {
        iv_head = findViewById(R.id.iv_head);
        tv_age = findViewById(R.id.tv_age);
        tv_marriage = findViewById(R.id.tv_marriage);
        tv_gender = findViewById(R.id.tv_gender);
        tv_brands = findViewById(R.id.tv_brands);
        tv_model = findViewById(R.id.tv_model);
        tv_did = findViewById(R.id.tv_did);
        tv_date = findViewById(R.id.tv_date);

        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        String mac = getIntent().getStringExtra("mac");
        getData(mac);
    }

    public void getData(final String mac) {
        //token加密
        try {
            token = DESCoder.encrypt("1" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取站点选项
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("mac", mac);
                    Log.i("jjj","mac:"+mac+" ,token:"+token);
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(token, "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url("http://dmptest.zhiziyun.com/api/v1/deviceVisit/queryTags.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

//                            Gson gson = new Gson();
//                            vp = gson.fromJson(response.body().string(), Visitorsselfparticulars.class);
//                            handler.sendEmptyMessage(1);
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
            switch (msg.what) {
                case 1:
                    if (TextUtils.isEmpty(vp.getDid())) {//如果没数据就提示
                        Toast.makeText(VisitorsselfActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                    } else {
//                        if (vp)
                        break;
                    }
                    super.handleMessage(msg);
            }
        }
    };

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
            }
        }
    }
