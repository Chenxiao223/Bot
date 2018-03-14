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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.SelfDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lib.homhomlib.design.SlidingLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/29.
 * 新建人群
 */

public class AddCorwdActivity extends BaseActivity implements View.OnClickListener {
    public static AddCorwdActivity addCorwdActivity;
    private SlidingLayout slidingLayout;
    public TextView tv_beginTime, tv_endTime, tv_tanzhen, edit_name, tv_liveness, tv_game, tv_app, tv_property;
    private final int FLAG = 1407;
    private final int FLAG_GAME = 1732;
    private final int FLAG_APP = 9233;
    private final int FLAG_POPULATION = 5746;
    private final int FLAG_ACTIVE = 4232;
    private SharedPreferences share;
    public String activity = "";
    private String probes = "";
    public ArrayList<String> list_lable = new ArrayList<>();
    public ArrayList<String> list_corwd = new ArrayList<>();
    private MyDialog dialog;
    private int visitorType = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcrowd);
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

    private void initView() {
        addCorwdActivity = this;
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        tv_beginTime = (TextView) findViewById(R.id.tv_beginTime);
        tv_endTime = (TextView) findViewById(R.id.tv_endTime);
        tv_tanzhen = (TextView) findViewById(R.id.tv_tanzhen);
        tv_liveness = (TextView) findViewById(R.id.tv_liveness);
        tv_game = (TextView) findViewById(R.id.tv_game);
        tv_app = (TextView) findViewById(R.id.tv_app);
        tv_property = (TextView) findViewById(R.id.tv_property);
        edit_name = (TextView) findViewById(R.id.edit_name);

        //初始化日期
        tv_beginTime.setText(gettodayDate());
        tv_endTime.setText("不限");

        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.rl_beginTime).setOnClickListener(this);
        findViewById(R.id.rl_endTime).setOnClickListener(this);
        findViewById(R.id.rl_tanzhen).setOnClickListener(this);
        findViewById(R.id.rl_liveness).setOnClickListener(this);
        findViewById(R.id.rl_game).setOnClickListener(this);
        findViewById(R.id.rl_app).setOnClickListener(this);
        findViewById(R.id.rl_property).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.linearLayout).setOnClickListener(this);
        findViewById(R.id.tv_begin).setOnClickListener(this);
        findViewById(R.id.iv_begin).setOnClickListener(this);
        findViewById(R.id.tv_end).setOnClickListener(this);
        findViewById(R.id.iv_end).setOnClickListener(this);

        //果冻弹跳效果
        slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
        slidingLayout.setSlidingOffset(0.2f);
    }

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                toFinish();
                finish();
                break;
            case R.id.rl_beginTime:
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //显示日期选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_beginTime.setText(getTime(date));
                        tv_beginTime.setTextColor(AddCorwdActivity.this.getResources().getColor(R.color.defaultcolor));
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();

                pvTime.show();
                break;
            case R.id.rl_endTime:
                //隐藏软键盘
                InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //显示日期选择器
                TimePickerView pvTime2 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_endTime.setText(getTime(date));
                        tv_endTime.setTextColor(AddCorwdActivity.this.getResources().getColor(R.color.defaultcolor));
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();

                pvTime2.show();
                break;
            case R.id.rl_tanzhen:
                //通过判断list_corwd有没有值来确定要不要将值传入进入显示选中状态
                if (list_corwd.isEmpty()) {//无值
                    startActivityForResult(new Intent(AddCorwdActivity.this, TanzhenActivity.class), FLAG);
                } else {//有值的话将值传过去显示选中状态
                    Intent it = new Intent(AddCorwdActivity.this, TanzhenActivity.class);
                    it.putExtra("flag", 123);
                    it.putStringArrayListExtra("list", (ArrayList<String>) list_corwd);
                    startActivityForResult(it, FLAG);
                }
                break;
            case R.id.btn_commit://点击提交
                if (TextUtils.isEmpty(edit_name.getText().toString()) || tv_tanzhen.getText().equals("请选择")) {
                    ToastUtils.showShort(AddCorwdActivity.this, "请将带星号部分填写完整");
                } else {
                    dialog = MyDialog.showDialog(this);
                    dialog.show();
                    createCorwd();
                }
                break;
            case R.id.linearLayout:
                //让软键盘隐藏
                InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm3.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                break;
            case R.id.rl_game://游戏兴趣
                Intent it_game = new Intent(this, LabelActivity.class);
                it_game.putExtra("flag", 0);
                startActivityForResult(it_game, FLAG_GAME);
                break;
            case R.id.rl_app://应用兴趣
                Intent it_app = new Intent(this, LabelActivity.class);
                it_app.putExtra("flag", 1);
                startActivityForResult(it_app, FLAG_APP);
                break;
            case R.id.rl_property://人口属性
                Intent it_property = new Intent(this, LabelActivity.class);
                it_property.putExtra("flag", 2);
                startActivityForResult(it_property, FLAG_POPULATION);
                break;
            case R.id.rl_liveness://用户活跃度
                Intent it_active = new Intent(AddCorwdActivity.this, UserActiveActivity.class);
                it_active.putExtra("flag", 3);
                startActivityForResult(it_active, FLAG_ACTIVE);
                break;
            case R.id.tv_begin:
                show_begin();
                break;
            case R.id.iv_begin:
                show_begin();
                break;
            case R.id.tv_end:
                show_end();
                break;
            case R.id.iv_end:
                show_end();
                break;
        }
    }

    public void show_begin() {
        final SelfDialog selfDialog = new SelfDialog(AddCorwdActivity.this);
        selfDialog.setTitle("开始时间");
        selfDialog.setMessage("开始时间指采集人群的时间，只能选择最近三十天采集的人群");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }

    public void show_end() {
        final SelfDialog selfDialog = new SelfDialog(AddCorwdActivity.this);
        selfDialog.setTitle("结束时间");
        selfDialog.setMessage("设置结束日期，则只采集日期范围内的人群；不设置，则一直采集");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FLAG:
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        probes = "";//为了防止第二次进去选中回来后叠加，在这里归零
                        //以后用集合来接收这个集合
                        list_corwd = data.getStringArrayListExtra("list");
                        int num = list_corwd.size();
                        tv_tanzhen.setText(num + "个");
                        tv_tanzhen.setTextColor(this.getResources().getColor(R.color.defaultcolor));
                        for (int i = 0; i < num; i++) {
                            if (i == 0) {
                                probes = probes + data.getStringArrayListExtra("list").get(i);
                            } else {
                                probes = probes + "|" + data.getStringArrayListExtra("list").get(i);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case FLAG_ACTIVE:
                try {
                    visitorType = data.getIntExtra("visitorType", -1);//0:老访客; 1：新访客; -1:不限
                    tv_liveness.setText(data.getStringExtra("name"));
                    if (visitorType == 0) {
                        activity = data.getStringExtra("active");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public void createCorwd() {
        //新建人群接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("name", edit_name.getText().toString());//人群名称
                    json.put("visitorType", visitorType);//访客属性,默认-1
                    json.put("beginTime", tv_beginTime.getText().toString());
                    if (tv_endTime.getText().toString().equals("不限")) {
                        json.put("endTime", "");//如果填的是不限就传个空字符串
                    } else {
                        json.put("endTime", tv_endTime.getText().toString());
                    }
                    json.put("tags", getTags());
                    json.put("probes", probes);
                    if (!TextUtils.isEmpty(activity)) {//如果为空就不填
                        json.put("activity", activity);
                    }
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
                            .url(BaseUrl.BaseWang + "builtCrowd/insert.action")
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
                                String str = jsonObject.getString("msg");
                                if (!TextUtils.isEmpty(str)) {
                                    if ("新建人群成功".equals(str)) {
                                        Message message = new Message();
                                        message.what = 1;
                                        message.obj = str;
                                        handler.sendMessage(message);
                                    } else {
                                        Message message = new Message();
                                        message.what = 3;
                                        message.obj = str;
                                        handler.sendMessage(message);
                                    }
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
                    ToastUtils.showShort(AddCorwdActivity.this, (String) msg.obj);
                    dialog.dismiss();
                    toFinish();
                    finish();
                    break;
                case 2:
                    ToastUtils.showShort(AddCorwdActivity.this, "新建人群失败");
                    dialog.dismiss();
                    break;
                case 3:
                    ToastUtils.showShort(AddCorwdActivity.this, (String) msg.obj);
                    dialog.dismiss();
                    break;
            }
        }
    };

    public String getTags() {
        String str = "";
        for (int i = 0; i < list_lable.size(); i++) {
            if (i == 0) {
                str = str + list_lable.get(i);
            } else {
                str = str + "|" + list_lable.get(i);
            }
        }
        return str;
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
                    tv_beginTime = null;
                    tv_endTime = null;
                    tv_tanzhen = null;
                    edit_name = null;
                    tv_liveness = null;
                    tv_game = null;
                    tv_app = null;
                    tv_property = null;
                    list_lable.clear();
                    list_corwd.clear();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}