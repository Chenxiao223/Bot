package com.zhiziyun.dmptest.bot.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.Visitorsselfparticulars;
import com.zhiziyun.dmptest.bot.util.MyDialog;
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
 * Created by Administrator on 2017/11/27.
 * 访客详情页
 */

public class VisitorsselfActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_head;
    private TextView tv_age, tv_marriage, tv_gender, tv_brands, tv_model, tv_did, tv_date, tv_num;
    private Visitorsselfparticulars vp;
    private MyDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitorsself);

        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system= (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height=(int) getStatusBarHeight(this);//设置当前控件布局的高度

        iv_head = (ImageView) findViewById(R.id.iv_head);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_marriage = (TextView) findViewById(R.id.tv_marriage);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_brands = (TextView) findViewById(R.id.tv_brands);
        tv_model = (TextView) findViewById(R.id.tv_model);
        tv_did = (TextView) findViewById(R.id.tv_did);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_num = (TextView) findViewById(R.id.tv_num);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        Intent it=getIntent();
        String mac = it.getStringExtra("mac");
        getData(mac);
        if (TextUtils.isEmpty(it.getStringExtra("brands"))){
            tv_brands.setText("未知");
        }else {
            tv_brands.setText(it.getStringExtra("brands"));
        }
        if (TextUtils.isEmpty(it.getStringExtra("model"))) {
            tv_model.setText("未知");
        }else {
            tv_model.setText(it.getStringExtra("model"));
        }
    }

    public void getData(final String mac) {
        //加载动画
        dialog = MyDialog.showDialog(this);
        dialog.show();
        //获取站点选项
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("mac", mac);
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
                            Gson gson = new Gson();
                            vp = gson.fromJson(response.body().string(), Visitorsselfparticulars.class);
                            if (vp != null) {
                                if (vp.getPopulation() != null) {
                                    Message message = new Message();
                                    message.what=1;
                                    Bundle bundle = new Bundle();
                                    for (int i = 0; i < vp.getPopulation().size(); i++) {
                                        String temp = vp.getPopulation().get(i).getName();
                                        Log.i("info", temp);
                                        if (front(temp).equals("性别")) {
                                            bundle.putString("gender", bk(temp));
                                        } else if (front(temp).equals("婚姻")) {
                                            bundle.putString("marriage", bk(temp));
                                        } else if (front(temp).equals("年龄")) {
                                            bundle.putString("age", bk(temp));
                                        }
                                    }
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                }
                            } else {
                                Toast.makeText(VisitorsselfActivity.this, "没数据", Toast.LENGTH_SHORT).show();
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
                    if (TextUtils.isEmpty(vp.getDid())) {//如果没数据就提示
                        Toast.makeText(VisitorsselfActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                    } else {
                        tv_did.setText(vp.getDid());
                        tv_date.setText(vp.getVisittime().substring(0, vp.getVisittime().indexOf(" ")));
                        tv_num.setText(vp.getProbe_log().size() + "");
                        tv_gender.setText(msg.getData().getString("gender", "未知"));
                        setImage(msg.getData().getString("gender", "未知"));
                        tv_marriage.setText(msg.getData().getString("marriage", "未知"));
                        tv_age.setText(msg.getData().getString("age", "未知"));
                        dialog.dismiss();
                    }
                    break;
            }
        }
    };

    public void setImage(String gender){
        switch (gender){
            case "男":
                iv_head.setImageResource(R.drawable.man);
                break;
            case "女":
                iv_head.setImageResource(R.drawable.woman);
                break;
            case "未知":
                iv_head.setImageResource(R.drawable.unknown);
                break;
        }
    }

    public String front(String str) {
        if (!str.equals("性别未知") && judgment(str)) {
            return str.substring(0, str.indexOf("-"));
        }
        return "未知";
    }

    public String bk(String str) {
        if (!str.equals("性别未知") && judgment(str)) {
            return str.substring(str.indexOf("-") + 1);
        }
        return "未知";
    }

    public boolean judgment(String str) {
        if (str.indexOf("性别") != -1 || str.indexOf("年龄") != -1 || str.indexOf("婚姻") != -1) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
