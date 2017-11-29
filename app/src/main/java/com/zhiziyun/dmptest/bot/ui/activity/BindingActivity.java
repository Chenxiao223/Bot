package com.zhiziyun.dmptest.bot.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zhiziyun.dmptest.bot.R;
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
 * Created by Administrator on 2017/11/29.
 * 绑定探针
 */

public class BindingActivity extends Activity implements View.OnClickListener {
    private ImageView tv_back;
    private EditText et_name, et_area;
    private String token;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        initView();
    }

    private void initView() {
        intent = getIntent();
        et_name = findViewById(R.id.et_name);
        et_area = findViewById(R.id.et_area);
        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    public double computations(double sqrt) {
        double[] domain = {1.47, 1.62, 1.78, 1.96, 2.15, 2.37, 2.61, 2.87, 3.16, 3.48, 3.83,
                4.22, 4.64, 5.11, 5.62, 6.19, 6.81, 7.50, 8.25, 9.09, 10.00, 11.01, 12.12,
                13.34, 14.68, 16.16, 17.78, 19.57, 21.54, 23.71, 26.10, 28.73, 31.62,
                34.81, 38.31, 42.17, 46.42, 51.09, 56.23, 61.90, 68.13, 74.99, 82.54, 90.85};
        double[] range = {56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73,
                74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 96, 98, 99};

        if (sqrt <= 1.47) {
            return 56;
        } else if (sqrt >= 90.85) {
            return 99;
        }

        for (int i = 0; i < domain.length - 1; i++) {
            if (domain[i] <= sqrt && sqrt <= domain[i + 1]) {
                return range[i + 1];
            }
        }
        return 0;
    }

    public void complete(View view) {
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
                    float lat = Float.parseFloat(intent.getStringExtra("lat"));
                    float lon = Float.parseFloat(intent.getStringExtra("lon"));
                    final JSONObject json = new JSONObject();
                    double signalStrength=Double.parseDouble(et_area.getText().toString());
                    json.put("siteId", "0zoTLi29XRgq");
                    json.put("storeId", intent.getStringExtra("storeId"));
                    json.put("name", et_name.getText().toString());
                    json.put("mac", intent.getStringExtra("mac"));
                    json.put("floorArea", intent.getStringExtra("floorArea"));
                    json.put("signalStrength", (int) computations(signalStrength));
                    json.put("longitude", (float) (Math.round(lat * 100)) / 1000);
                    json.put("latitude", (float) (Math.round(lon * 100)) / 1000);
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
                            .url("http://dmptest.zhiziyun.com/api/v1/probe/add.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
