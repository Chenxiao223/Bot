package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.AppImage;
import com.zhiziyun.dmptest.bot.entity.Visitorsselfparticulars;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    private TextView tv_age, tv_marriage, tv_gender, tv_brands, tv_model, tv_did, tv_date, tv_num, tv_time, tv_mac;
    private Visitorsselfparticulars vp;
    private MyDialog dialog;
    private AppImage app;
    private SlidingLayout slidingLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitorsself);

        initView();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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

        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_marriage = (TextView) findViewById(R.id.tv_marriage);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_brands = (TextView) findViewById(R.id.tv_brands);
        tv_model = (TextView) findViewById(R.id.tv_model);
        tv_did = (TextView) findViewById(R.id.tv_did);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_mac = (TextView) findViewById(R.id.tv_mac);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        Intent it = getIntent();
        String mac = it.getStringExtra("mac");
        getData(mac);
        ///
        if (TextUtils.isEmpty(it.getStringExtra("brands"))) {
            tv_brands.setText("未知");
        } else {
            tv_brands.setText(it.getStringExtra("brands"));
        }
        if (TextUtils.isEmpty(it.getStringExtra("model"))) {
            tv_model.setText("未知");
        } else {
            tv_model.setText(it.getStringExtra("model"));
        }
        if (TextUtils.isEmpty(it.getStringExtra("mac"))) {
            tv_mac.setText("未知");
        } else {
            tv_mac.setText(it.getStringExtra("mac"));
        }

        //果冻弹跳效果
        slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
        slidingLayout.setSlidingOffset(0.2f);
    }

    public void getData(final String mac) {
        //加载动画
        dialog = MyDialog.showDialog(this);
        dialog.SHOW();
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
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "deviceVisit/queryTags.action")
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
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                }
                            } else {
                                ToastUtils.showShort(VisitorsselfActivity.this, "无数据");
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
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "deviceVisit/queryIcons.action")
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
                    .into(80, 80)//图片大小
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
                    if (TextUtils.isEmpty(vp.getDid())) {//如果没数据就提示
                        ToastUtils.showShort(VisitorsselfActivity.this, "无数据");
                    } else {
                        try {
                            String did = vp.getDid();//设备号
                            //设备号从第十四位开始，后四位改为星号，其余不变。也就是从14到17位变为星号；
                            StringBuffer buffer = new StringBuffer(did);
                            tv_did.setText(buffer.replace(13, 17, "****"));//参数一：开始位置，参数二：结束位置，参数三：替换的字符串
                            tv_date.setText(vp.getVisittime().substring(0, vp.getVisittime().indexOf(" ")));
                            tv_num.setText(vp.getProbe_log().size() + "");
                            tv_gender.setText(msg.getData().getString("gender", "未知"));
                            tv_marriage.setText(msg.getData().getString("marriage", "未知"));
                            tv_age.setText(msg.getData().getString("age", "未知"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        double sum = 0;
                        int size = vp.getProbe_log().get(0).getVisit_count();//后台（王柏浩）说取第一条数据就行了
                        for (int i = 0; i < size; i++) {
                            sum += vp.getProbe_log().get(0).getVisit().get(i).getVisit_length();
                        }
                        try {
                            DecimalFormat df = new DecimalFormat("######0.00");//保留两位小数
                            tv_time.setText(df.format(sum / size) + "");
                        } catch (Exception e) {
                            e.printStackTrace();
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
                    }
                    dialog.dismiss();//关闭动画加载
                    break;
                case 2:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    appImage(bitmap);
                    break;
            }
        }
    };

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
                tv_age = null;
                tv_marriage = null;
                tv_gender = null;
                tv_brands = null;
                tv_model = null;
                tv_did = null;
                tv_date = null;
                tv_num = null;
                tv_time = null;
                vp = null;
                app = null;
                Glide.get(VisitorsselfActivity.this).clearMemory();
                System.gc();
            }
        }, 500);
    }
}
