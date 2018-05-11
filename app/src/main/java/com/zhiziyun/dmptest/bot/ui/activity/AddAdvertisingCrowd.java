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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.zhiziyun.dmptest.bot.R;
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
 * 添加点击广告人群
 */

public class AddAdvertisingCrowd extends BaseActivity implements View.OnClickListener {
    private TextView tv_beginTime, tv_endTime, tv_advertising;
    private EditText et_name;
    private final int Flag_ad = 1702;
    private List<String> list_ad = new ArrayList<>();
    private String m_plan = "";
    private MyDialog dialog;
    private SharedPreferences share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising_crowd);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        tv_beginTime = (TextView) findViewById(R.id.tv_beginTime);
        tv_endTime = (TextView) findViewById(R.id.tv_endTime);
        tv_advertising = (TextView) findViewById(R.id.tv_advertising);
        et_name = (EditText) findViewById(R.id.et_name);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.rl_beginTime).setOnClickListener(this);
        findViewById(R.id.rl_endTime).setOnClickListener(this);
        findViewById(R.id.rl_advertising).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.page).setOnClickListener(this);
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
                        tv_beginTime.setText(getTime(date));
                        tv_beginTime.setTextColor(AddAdvertisingCrowd.this.getResources().getColor(R.color.defaultcolor));
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, true})// 对应年月日时分秒
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .build();

                pvTime.show();
                break;
            case R.id.rl_endTime:
                hidden(v);
                //显示日期选择器
                TimePickerView pvTime2 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_endTime.setText(getTime(date));
                        tv_endTime.setTextColor(AddAdvertisingCrowd.this.getResources().getColor(R.color.defaultcolor));
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setLabel("年", "月", "日", "时", "分", "秒")
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
                        || tv_beginTime.getText().toString().equals("请选择")) {
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

    public void hidden(View v){
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
        dialog = MyDialog.showDialog(this);
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
                            .url("http://test2.api.zhiziyun.com/api/v1/adClickSegmentInfo/insert")
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
            }
        }
    };

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
