package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.ClickCrowd;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;


import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/7.
 * 添加广告点击人群
 */

public class AddAdvertisingCrowd extends BaseActivity implements View.OnClickListener {
    private TextView tv_beginTime, tv_endTime, tv_advertising, tv_title;
    private EditText et_name;
    private final int Flag_ad = 1702;
    private List<String> list_ad = new ArrayList<>();
    private String m_plan = "";
    private MyDialog dialog;
    private SharedPreferences share;
    private Intent intent;
    private Button btn_commit;
    private RelativeLayout rl_advertising, rl_beginTime, rl_endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising_crowd);
        initView();
        getDate();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        dialog = MyDialog.showDialog(this);
        intent = getIntent();
        tv_beginTime = (TextView) findViewById(R.id.tv_beginTime);
        tv_endTime = (TextView) findViewById(R.id.tv_endTime);
        tv_advertising = (TextView) findViewById(R.id.tv_advertising);
        et_name = (EditText) findViewById(R.id.et_name);
        tv_title = findViewById(R.id.tv_title);

        findViewById(R.id.iv_back).setOnClickListener(this);
        rl_beginTime = findViewById(R.id.rl_beginTime);
        rl_beginTime.setOnClickListener(this);
        rl_endTime = findViewById(R.id.rl_endTime);
        rl_endTime.setOnClickListener(this);
        rl_advertising = findViewById(R.id.rl_advertising);
        rl_advertising.setOnClickListener(this);
        btn_commit = findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
        findViewById(R.id.page).setOnClickListener(this);

        if (intent.getIntExtra("flag", 0) == 324) {
            try {
                tv_title.setText("点击广告人群详情");
                et_name.setFocusable(false);
                et_name.setFocusableInTouchMode(false);
                et_name.setClickable(false);
                rl_advertising.setClickable(false);
                rl_beginTime.setClickable(false);
                rl_endTime.setClickable(false);
                btn_commit.setVisibility(View.GONE);
                getClickSCorwd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.rl_beginTime:
                //隐藏软键盘
                hidden(v);
                //显示日期选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        //所选日期要大于等于前90天的日期
                        if (Long.valueOf(getTime(date).replaceAll("-", "")) >= Long.valueOf(getDate().replaceAll("-", ""))) {
                            tv_beginTime.setText(getTime(date) + " 00:00:00");
                            tv_beginTime.setTextColor(AddAdvertisingCrowd.this.getResources().getColor(R.color.defaultcolor));
                        } else {
                            ToastUtils.showShort(AddAdvertisingCrowd.this, "开始时间不能早于" + getDate());
                        }
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 对应年月日时分秒
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();

                pvTime.show();
                break;
            case R.id.rl_endTime:
                hidden(v);
                //显示日期选择器
                TimePickerView pvTime2 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_endTime.setText(getTime(date) + " 23:59:59");
                        tv_endTime.setTextColor(AddAdvertisingCrowd.this.getResources().getColor(R.color.defaultcolor));
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
            case R.id.rl_advertising:
                try {
                    //通过判断list_corwd有没有值来确定要不要将值传入进入显示选中状态
                    if (list_ad.isEmpty()) {//无值
                        Intent it = new Intent(AddAdvertisingCrowd.this, AdListActivity.class);
                        startActivityForResult(it, Flag_ad);
                    } else {//有值的话将值传过去显示选中状态
                        Intent it = new Intent(AddAdvertisingCrowd.this, AdListActivity.class);
                        it.putExtra("flag", 123);
                        it.putStringArrayListExtra("list", (ArrayList<String>) list_ad);
                        startActivityForResult(it, Flag_ad);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(et_name.getText().toString()) || tv_advertising.getText().toString().equals("请选择")
                        || tv_beginTime.getText().toString().equals("请选择") || tv_endTime.getText().toString().equals("请选择")) {
                    ToastUtils.showShort(AddAdvertisingCrowd.this, "请将带星号的数据填完整");
                } else {
                    addClickSCorwd();
                }
                break;
            case R.id.page:
                hidden(v);
                break;
        }
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -90);
        return sdf.format(calendar.getTime());
    }

    public void hidden(View v) {
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //区分从哪个页面回传的数据
            case Flag_ad://广告
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        //用集合来接收这个集合
                        list_ad = data.getStringArrayListExtra("list");
                        m_plan = "";
                        int num = list_ad.size();
                        if (num != 0) {
                            tv_advertising.setText(num + "个");
                            tv_advertising.setTextColor(this.getResources().getColor(R.color.defaultcolor));
                            for (int i = 0; i < num; i++) {
                                m_plan += list_ad.get(i) + ",";
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void addClickSCorwd() {
        //加载动画
        dialog.show();
        //新建点击广告人群
        new Thread(new Runnable() {
            @RequiresApi(api = 26)
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("name", et_name.getText().toString());
                    json.put("plan", m_plan);
                    if (!tv_beginTime.getText().toString().equals("请选择")) {
                        json.put("beginTime", tv_beginTime.getText().toString());
                    }
                    if (!tv_endTime.getText().toString().equals("请选择")) {
                        json.put("endTime", tv_endTime.getText().toString());
                    }
                    json.put("userId", share.getString("userid", ""));
                    json.put("userName", share.getString("userName", ""));
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
                            .url(BaseUrl.BaseTest + "adClickSegmentInfo/insert")
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
                                Message message = new Message();
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.get("success").toString().equals("true")) {
                                    message.what = 1;
                                    message.obj = jsonObject.get("msg").toString();
                                } else {
                                    message.what = 2;
                                    message.obj = jsonObject.get("msg").toString();
                                }
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

    public void getClickSCorwd() {
        //加载动画
        dialog.show();
        //获取指定WIFI人群
        new Thread(new Runnable() {
            @RequiresApi(api = 26)
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("id", intent.getIntExtra("id", -333));
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
                            .url(BaseUrl.BaseTest + "adClickSegmentInfo/select")
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
                                message.what = 3;
                                message.obj = gson.fromJson(response.body().string(), ClickCrowd.class);
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
                    ToastUtils.showShort(AddAdvertisingCrowd.this, msg.obj.toString());
                    dialog.dismiss();
                    toFinish();
                    finish();
                    break;
                case 2:
                    ToastUtils.showShort(AddAdvertisingCrowd.this, msg.obj.toString());
                    dialog.dismiss();
                    break;
                case 3:
                    try {
                        ClickCrowd clickCrowd = (ClickCrowd) msg.obj;
                        et_name.setText(clickCrowd.getObj().getName());
                        List<String> list = Arrays.asList(clickCrowd.getObj().getPlan().split(","));
                        tv_advertising.setText(list.size() + "个");
                        tv_beginTime.setText(clickCrowd.getObj().getBeginTime());
                        tv_endTime.setText(clickCrowd.getObj().getEndTime());
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
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
                    tv_beginTime = null;
                    tv_endTime = null;
                    list_ad.clear();
                    m_plan = "";
                    tv_advertising = null;
                    et_name = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
