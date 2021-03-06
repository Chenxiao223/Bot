package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.AppImage;
import com.zhiziyun.dmptest.bot.entity.Visitorsselfparticulars;
import com.zhiziyun.dmptest.bot.ui.fragment.VisitorsselfFragment;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ch.ielse.view.SwitchView;
import co.lujun.androidtagview.TagContainerLayout;
import lib.homhomlib.design.SlidingLayout;
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
    private TextView tv_age, tv_marriage, tv_gender, tv_brands, tv_model, tv_did, tv_date, tv_mac, tv_text;
    private MyDialog dialog;
    private AppImage app;
    private SlidingLayout slidingLayout;
    private RelativeLayout line_brands, line_model, line_mac;
    private ImageView iv_head;
    private SwitchView swich;
    private RelativeLayout rl_swich;
    private SharedPreferences share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitorsself);
        initView();
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        dialog = MyDialog.showDialog(this);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_marriage = (TextView) findViewById(R.id.tv_marriage);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_brands = (TextView) findViewById(R.id.tv_brands);
        tv_model = (TextView) findViewById(R.id.tv_model);
        tv_did = (TextView) findViewById(R.id.tv_did);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_mac = (TextView) findViewById(R.id.tv_mac);
        tv_text = findViewById(R.id.tv_text);
        line_brands = findViewById(R.id.line_brands);
        line_model = findViewById(R.id.line_model);
        line_mac = findViewById(R.id.line_mac);
        iv_head = findViewById(R.id.iv_head);
        swich = findViewById(R.id.swich);
        rl_swich = findViewById(R.id.rl_swich);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        Intent it = getIntent();
        String mac = it.getStringExtra("mac");
        queryDeviceToDemo(mac);
        supportAgentDemo();
        getData(mac);
        ///
        if (TextUtils.isEmpty(it.getStringExtra("brands"))) {
            line_brands.setVisibility(View.GONE);
        } else {
            tv_brands.setText(it.getStringExtra("brands"));
        }
        if (TextUtils.isEmpty(it.getStringExtra("mac"))) {
            line_mac.setVisibility(View.GONE);
        } else {
            tv_mac.setText(it.getStringExtra("mac"));
        }

        //果冻弹跳效果
        slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
        slidingLayout.setSlidingOffset(0.2f);

        if (getIntent().getStringExtra("is_new").equals("true")) {
            tv_date.setVisibility(View.GONE);
        }

        swich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpened = swich.isOpened();
                if (isOpened) {
                    addDeviceToDemo();
                } else {
                    delDeviceToDemo();
                }
            }
        });
    }

    public void queryDeviceToDemo(final String mac) {
        //查询设备是否开启了一键演示功能
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("mac", mac);
                    json.put("siteId", share.getString("siteid", ""));
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
                            .url(BaseUrl.BaseTest + "deviceVisit/queryDeviceToDemo")
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
                                JSONObject json = new JSONObject(response.body().string());
                                Message msg = new Message();
                                if (json.get("success").toString().equals("true")) {
                                    msg.what = 7;
                                    msg.obj = json.get("obj").toString();
                                } else {
                                    msg.what = 8;
                                    msg.obj = json.get("msg").toString();
                                }
                                handler.sendMessage(msg);
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

    public void getData(final String mac) {
        //加载动画
        dialog.show();
        //访客详情接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("mac", mac);
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
                            .url(BaseUrl.BaseTest + "deviceVisit/queryTags")
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
                                Visitorsselfparticulars vp = gson.fromJson(response.body().string(), Visitorsselfparticulars.class);
                                if (vp != null) {
                                    if (vp.getPopulation() != null) {
                                        Message message = new Message();
                                        message.what = 1;
                                        Bundle bundle = new Bundle();
                                        for (int i = 0; i < vp.getPopulation().size(); i++) {
                                            String temp = vp.getPopulation().get(i).getName();
                                            if (front(temp).equals("性别")) {
                                                bundle.putString("gender", bk(temp));
                                            } else if (front(temp).equals("婚姻")) {
                                                bundle.putString("marriage", bk(temp));
                                            } else if (front(temp).equals("年龄")) {
                                                bundle.putString("age", bk(temp));
                                            }
                                        }
                                        //如果为空就不执行
                                        if (vp.getMostused_apps() != null) {
                                            String str = "";
                                            for (int i = 0; i < vp.getMostused_apps().size(); i++) {
                                                if (i == 0) {
                                                    str += vp.getMostused_apps().get(i);
                                                } else {
                                                    str += "," + vp.getMostused_apps().get(i);
                                                }
                                            }
                                            getImage(str);
                                        }
                                        message.obj = vp;
                                        message.setData(bundle);
                                        handler.sendMessage(message);
                                    }
                                } else {
                                    handler.sendEmptyMessage(3);
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

    public void supportAgentDemo() {
        //查询当前账户是否支持一键演示
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
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
                            .url(BaseUrl.BaseTest + "deviceVisit/supportAgentDemo")
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
                                JSONObject jsonObject = new JSONObject(str);
                                if (jsonObject.get("obj").toString().equals("true")) {
                                    handler.sendEmptyMessage(9);
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

    public void addDeviceToDemo() {
        //添加到店访客到一键演示
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("userId", share.getString("userid", ""));
                    json.put("mac", getIntent().getStringExtra("mac"));
                    TelephonyManager telephonyManager = (TelephonyManager) VisitorsselfActivity.this.getSystemService(VisitorsselfActivity.this.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
                    json.put("deviceId", imei);
                    json.put("siteId", share.getString("siteid", ""));
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
                            .url(BaseUrl.BaseTest + "deviceVisit/addDeviceToDemo")
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
                                JSONObject jsonObject = new JSONObject(str);
                                if (jsonObject.get("success").toString().equals("true")) {
                                    handler.sendEmptyMessage(4);
                                } else {
                                    Message message = new Message();
                                    message.what = 5;
                                    message.obj = jsonObject.get("msg").toString();
                                    handler.sendMessage(message);
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

    public void delDeviceToDemo() {
        //撤销到店访客的一键演示
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("userId", share.getString("userid", ""));
                    json.put("mac", getIntent().getStringExtra("mac"));
                    TelephonyManager telephonyManager = (TelephonyManager) VisitorsselfActivity.this.getSystemService(VisitorsselfActivity.this.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
                    json.put("deviceId", imei);
                    json.put("siteId", share.getString("siteid", ""));
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
                            .url(BaseUrl.BaseTest + "deviceVisit/delDeviceToDemo")
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
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.get("success").toString().equals("true")) {
                                    handler.sendEmptyMessage(6);
                                } else {
                                    Message message = new Message();
                                    message.what = 5;
                                    message.obj = jsonObject.get("msg").toString();
                                    handler.sendMessage(message);
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

    //获取app图标接口
    public void getImage(final String parameter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("ids", parameter);
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
                            .url(BaseUrl.BaseTest + "deviceVisit/queryIcons")
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
                                app = gson.fromJson(response.body().string(), AppImage.class);
                                if (app != null) {
                                    for (int i = 0; i < app.getTotal(); i++) {
                                        Message msg = new Message();
                                        msg.what = 2;
                                        Bitmap bitmap = getAppBitmap(app.getRows().get(i).getIcon());
                                        msg.obj = bitmap;
                                        handler.sendMessage(msg);
                                    }
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

    public Bitmap getAppBitmap(String url) {
        try {
            return Glide.with(this).load(url)
                    .asBitmap() //必须
                    .centerCrop()
                    .into(90, 90)//图片大小
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //添加APP图标
    public void appImage(Bitmap bitmap) {
        LinearLayout line_image = (LinearLayout) findViewById(R.id.line_image);
        LinearLayout.LayoutParams r1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        r1.setMargins(5, 0, 0, 0);
        LinearLayout view = new LinearLayout(this);
        view.setLayoutParams(r1);//设置布局参数
        view.setOrientation(LinearLayout.HORIZONTAL);// 设置子View的Linearlayout// 为水平方向布局
        //定义子View中两个元素的布局
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView image = new ImageView(this);
        image.setLayoutParams(vlp);
        image.setImageBitmap(bitmap);
        view.addView(image);
        line_image.addView(view);
    }

    //添加app
    public void addApp(List list) {
        TagContainerLayout mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tcl_app);
        mTagContainerLayout.setTags(list);
    }

    //添加游戏
    public void addGame(List list) {
        TagContainerLayout mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tcl_game);
        mTagContainerLayout.setTags(list);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        Visitorsselfparticulars vp = (Visitorsselfparticulars) msg.obj;
                        if (TextUtils.isEmpty(vp.getDid())) {//如果没数据就提示
                            ToastUtils.showShort(VisitorsselfActivity.this, "无数据");
                        } else {
                            try {
                                tv_did.setText(vp.getDid());//设备号
                                if (msg.getData().getString("gender", "未知").equals("未知")) {
                                    tv_gender.setVisibility(View.GONE);
                                } else {
                                    if (msg.getData().getString("gender", "未知").equals("男")) {
                                        iv_head.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.iv_man));
                                    } else {
                                        iv_head.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.iv_woman));
                                    }
                                    tv_gender.setText(msg.getData().getString("gender", "未知"));
                                }
                                if (msg.getData().getString("marriage", "未知").equals("未知")) {
                                    tv_marriage.setVisibility(View.INVISIBLE);
                                } else {
                                    tv_marriage.setText(msg.getData().getString("marriage", "未知"));
                                }
                                if (msg.getData().getString("age", "未知").equals("未知")) {
                                    tv_age.setVisibility(View.GONE);
                                } else {
                                    tv_age.setText(msg.getData().getString("age", "未知"));
                                }
                                for (int i = 0; i < vp.getProbe_log().size(); i++) {
                                    if (getIntent().getStringExtra("probemac").equals(vp.getProbe_log().get(i).getProbe_mac())) {
                                        List<String> list = new ArrayList<>();
                                        double counts = 0;
                                        for (int j = 0; j < vp.getProbe_log().get(i).getVisit().size(); j++) {
                                            list.add(vp.getProbe_log().get(i).getVisit().get(j).getVisit_date());
                                            counts += vp.getProbe_log().get(i).getVisit().get(j).getVisit_length();
                                        }
                                        DecimalFormat df = new DecimalFormat("######0.00");//保留两位小数
                                        tv_text.setText("到店" + vp.getProbe_log().get(i).getVisit_count() + "次,平均时长" + df.format(counts / vp.getProbe_log().get(i).getVisit().size()) + "分钟");
                                        //实现排序方法
                                        Collections.sort(list);
                                        if (list.size() > 1) {
                                            tv_date.setText("上次到店 " + list.get(list.size() - 2));
                                            if (TextUtils.isEmpty(tv_date.getText().toString())) {
                                                tv_date.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }

                                for (int i = 0; i < vp.getBasic().size(); i++) {
                                    String model = vp.getBasic().get(i).getName();
                                    if (TextUtils.isEmpty(model)) {
                                        line_model.setVisibility(View.GONE);
                                    } else {
                                        if (isContains(model)) {
                                            line_model.setVisibility(View.VISIBLE);
                                            tv_model.setText(model.substring(model.indexOf("-") + 1));
                                            break;
                                        } else {
                                            line_model.setVisibility(View.GONE);
                                        }
                                    }
                                }

                                List<String> list_app = new ArrayList();
                                for (int i = 0; i < vp.getApp().size(); i++) {
                                    list_app.add(vp.getApp().get(i).getName());
                                }
                                addApp(list_app);//动态添加APP

                                List<String> list_game = new ArrayList();
                                for (int i = 0; i < vp.getGame().size(); i++) {
                                    list_game.add(vp.getGame().get(i).getName());
                                }
                                addGame(list_game);//动态添加APP
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        dialog.dismiss();//关闭动画加载
                    } catch (Exception e) {
                        dialog.dismiss();//关闭动画加载
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    appImage(bitmap);
                    break;
                case 3:
                    ToastUtils.showShort(VisitorsselfActivity.this, "无数据");
                    break;
                case 4:
                    ToastUtils.showShort(VisitorsselfActivity.this, "操作成功");
                    VisitorsselfFragment.fragment.list_visitors.get(getIntent().getIntExtra("position", 0)).put("isShown", "true");
                    VisitorsselfFragment.fragment.adapter.notifyDataSetChanged();
                    break;
                case 5:
                    ToastUtils.showShort(VisitorsselfActivity.this, msg.obj.toString());
                    break;
                case 6:
                    ToastUtils.showShort(VisitorsselfActivity.this, "操作成功");
                    VisitorsselfFragment.fragment.list_visitors.get(getIntent().getIntExtra("position", 0)).put("isShown", "false");
                    VisitorsselfFragment.fragment.adapter.notifyDataSetChanged();
                    break;
                case 7:
                    try {
                        if (msg.obj.toString().equals("true")) {
                            swich.setOpened(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 8:
                    ToastUtils.showShort(VisitorsselfActivity.this, msg.obj.toString());
                    break;
                case 9:
                    rl_swich.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    public Boolean isContains(String str) {
        if (str.indexOf("standardModel") != -1) {
            return true;
        }
        return false;
    }

    private String getTime() {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
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
                toFinish();
                finish();
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
                    tv_age = null;
                    tv_marriage = null;
                    tv_gender = null;
                    tv_brands = null;
                    tv_model = null;
                    tv_did = null;
                    tv_date = null;
                    app = null;
                    Glide.get(VisitorsselfActivity.this).clearMemory();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
