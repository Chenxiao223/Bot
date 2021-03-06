package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.FriendInfo;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.PopWin_industry;
import com.zhiziyun.dmptest.bot.wheelview.WheelView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/7/18.
 * 添加朋友圈活动(1)
 */

public class AddFriendsClubActivity extends BaseActivity implements View.OnClickListener {
    public static AddFriendsClubActivity activity;
    private String beginTime, endTime;
    private TextView tv_beginTime, tv_endTime, tv_AD_types, tv_time_slot;
    private PopWin_industry popWin_industry;
    private List<String> list_value = new ArrayList<>();
    private List<String> list_key = new ArrayList<>();
    private String productTypes;
    private EditText edit_name, edit_budget;
    private String timeSeries = null;
    private final int FLAG = 1407;
    public ArrayList<String> list_str = new ArrayList<>();
    private SharedPreferences share;
    public FriendInfo friendInfo;
    private RelativeLayout rl_beginTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        initView();
    }

    private void initView() {
        activity = this;
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        beginTime = gettodayDate(0);//今天
        endTime = gettodayDate(1);//明天

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        tv_beginTime = findViewById(R.id.tv_beginTime);
        tv_beginTime.setText(beginTime);
        tv_endTime = findViewById(R.id.tv_endTime);
        tv_endTime.setText(endTime);
        tv_AD_types = findViewById(R.id.tv_AD_types);
        edit_name = findViewById(R.id.edit_name);
        edit_budget = findViewById(R.id.edit_budget);
        tv_time_slot = findViewById(R.id.tv_time_slot);
        rl_beginTime = findViewById(R.id.rl_beginTime);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.rl_beginTime).setOnClickListener(this);
        findViewById(R.id.rl_endTime).setOnClickListener(this);
        findViewById(R.id.rl_AD_types).setOnClickListener(this);
        findViewById(R.id.line_page).setOnClickListener(this);
        findViewById(R.id.rl_time_slot).setOnClickListener(this);
        getData();
        if (getIntent().getIntExtra("flag", 0) != 0) {
            if (getIntent().getStringExtra("state").equals("有效")) {//如果审核通过（有效）就不让编辑名称
                edit_name.setFocusable(false);
                edit_name.setFocusableInTouchMode(false);
                edit_name.setClickable(false);
            }
            //说明从编辑页面进来
            getWechatActivityDetail(getIntent().getStringExtra("wechatActivityId"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_time_slot://点击投放时段
                try {
                    //通过判断list_corwd有没有值来确定要不要将值传入进入显示选中状态
                    if (list_str.isEmpty()) {//无值
                        startActivityForResult(new Intent(AddFriendsClubActivity.this, TimeSeriesActivity.class), FLAG);
                    } else {//有值的话将值传过去显示选中状态
                        Intent it = new Intent(AddFriendsClubActivity.this, TimeSeriesActivity.class);
                        it.putExtra("flag", 123);
                        it.putStringArrayListExtra("list", (ArrayList<String>) list_str);
                        startActivityForResult(it, FLAG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.line_page:
                //隐藏软键盘
                InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm3.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.rl_AD_types:
                try {
                    if (getIntent().getIntExtra("flag", 0) != 0) {
                        ToastUtils.showShort(this, "广告类型无法编辑");
                    } else {
                        View outerView1 = LayoutInflater.from(this).inflate(R.layout.view_industry, null);
                        //滚轮
                        final WheelView wv1 = (WheelView) outerView1.findViewById(R.id.wv1);
                        wv1.setItems(list_value, 0);
                        TextView tv_ok = (TextView) outerView1.findViewById(R.id.tv_ok);
                        TextView tv_cancel = (TextView) outerView1.findViewById(R.id.tv_cancel);
                        //点击确定
                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                String mSelectDate = wv1.getSelectedItem();
                                tv_AD_types.setText(mSelectDate);
                                for (int i = 0; i < list_value.size(); i++) {
                                    if (mSelectDate.equals(list_value.get(i))) {
                                        productTypes = list_key.get(i);
                                    }
                                }
                                popWin_industry.dismiss();
                            }
                        });
                        //点击取消
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                popWin_industry.dismiss();
                            }
                        });
                        //防止弹出两个窗口
                        if (popWin_industry != null && popWin_industry.isShowing()) {
                            return;
                        }

                        popWin_industry = new PopWin_industry(this, R.style.ActionSheetDialogStyle);
                        //将布局设置给Dialog
                        popWin_industry.setContentView(outerView1);
                        popWin_industry.show();//显示对话框
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_next:
                if (!TextUtils.isEmpty(edit_name.getText().toString()) && !tv_AD_types.getText().toString().equals("请选择")
                        && !TextUtils.isEmpty(edit_budget.getText().toString())) {
                    if (!tv_time_slot.getText().toString().equals("不限")) {//如果时段不是不限，那么投放时段要大于6个时段
                        if (list_str.size() < 6) {
                            ToastUtils.showShort(this, "投放时段要大于6个时段");
                        } else {
                            if (Integer.parseInt(edit_budget.getText().toString()) >= 1000) {
                                Intent intent = new Intent(AddFriendsClubActivity.this, AddCircleFriendsActivity.class);
                                intent.putExtra("wechatActivityName", edit_name.getText().toString());
                                intent.putExtra("productType", productTypes);
                                intent.putExtra("startDate", tv_beginTime.getText().toString());
                                intent.putExtra("endDate", tv_endTime.getText().toString());
                                if (!TextUtils.isEmpty(timeSeries)) {
                                    intent.putExtra("timeSeries", timeSeries);
                                } else {
                                    //如果时段为不限，就填0000
                                    timeSeries = "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
                                    intent.putExtra("timeSeries", timeSeries);
                                }
                                intent.putExtra("dailyBudget", edit_budget.getText().toString());
                                if (getIntent().getIntExtra("flag", 0) != 0) {
                                    intent.putExtra("flag", 123);//如果从编辑进来，就把这个值传过去
                                    intent.putExtra("wechatActivityId", getIntent().getStringExtra("wechatActivityId"));
                                }
                                startActivity(intent);
                            } else {
                                ToastUtils.showShort(this, "预算要大于等于1000");
                            }
                        }
                    } else {
                        if (Integer.parseInt(edit_budget.getText().toString()) >= 1000) {
                            Intent intent = new Intent(AddFriendsClubActivity.this, AddCircleFriendsActivity.class);
                            intent.putExtra("wechatActivityName", edit_name.getText().toString());
                            intent.putExtra("productType", productTypes);
                            intent.putExtra("startDate", tv_beginTime.getText().toString());
                            intent.putExtra("endDate", tv_endTime.getText().toString());
                            if (!TextUtils.isEmpty(timeSeries)) {
                                intent.putExtra("timeSeries", timeSeries);
                            } else {
                                //如果时段为不限，就填0000
                                timeSeries = "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
                                intent.putExtra("timeSeries", timeSeries);
                            }
                            intent.putExtra("dailyBudget", edit_budget.getText().toString());
                            if (getIntent().getIntExtra("flag", 0) != 0) {
                                intent.putExtra("flag", 123);//如果从编辑进来，就把这个值传过去
                                intent.putExtra("wechatActivityId", getIntent().getStringExtra("wechatActivityId"));
                            }
                            startActivity(intent);
                        } else {
                            ToastUtils.showShort(this, "预算要大于等于1000");
                        }
                    }
                } else {
                    ToastUtils.showShort(this, "请将带星号的数据填完整");
                }
                break;
            case R.id.iv_back:
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
                        beginTime = getTime(date);
                        //投放需要小于等于10个自然日
                        if (differentDays(beginTime, endTime) <= 10) {
                            tv_beginTime.setText(getTime(date));
                        } else {
                            ToastUtils.showShort(AddFriendsClubActivity.this, "投放需要小于等于10个自然日");
                        }
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
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
                        endTime = getTime(date);
                        //投放需要小于等于10个自然日
                        if (differentDays(beginTime, endTime) <= 10) {
                            tv_endTime.setText(getTime(date));
                        } else {
                            ToastUtils.showShort(AddFriendsClubActivity.this, "投放需要小于等于10个自然日");
                        }
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();

                pvTime2.show();
                break;
        }
    }

    public Long differentDays(String a, String b) {
        long days = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //跨年不会出现问题
            //如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0
            Date fDate = sdf.parse(a);
            Date oDate = sdf.parse(b);
            days = (oDate.getTime() - fDate.getTime()) / (1000 * 3600 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FLAG:
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        timeSeries = "";//为了防止第二次进去选中回来后叠加，在这里归零
                        //以后用集合来接收这个集合
                        list_str = data.getStringArrayListExtra("list");
                        int num = list_str.size();
                        tv_time_slot.setText(num + "个");
                        //拼接字符串
                        for (int i = 0; i < 24; i++) {
                            if (list_str.contains(i + "")) {
                                timeSeries += "11";
                            } else {
                                timeSeries += "00";
                            }
                        }
                        //将一份复制成七份
                        String str = timeSeries;
                        for (int j = 0; j < 6; j++) {
                            timeSeries += str;
                        }
                    } else {
                        tv_time_slot.setText("不限");
                        timeSeries = "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";//不限的话全部补零
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void getData() {
        //微信朋友圈活动列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + "";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "api/tencentWechatActivityApp/creativeWechatActivitySelectItem")
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
                                list_key.clear();
                                list_value.clear();
                                String str = response.body().string();
                                JSONObject jsonObject = new JSONObject(str);
                                JSONObject json = new JSONObject(jsonObject.get("response").toString());
                                Map maps = (Map) JSON.parse(json.get("productTypes").toString());
                                for (Object map : maps.entrySet()) {
                                    list_key.add(((Map.Entry) map).getKey().toString());
                                    list_value.add(((Map.Entry) map).getValue().toString());
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

    public void getWechatActivityDetail(final String str) {
        //微信朋友圈活动详情获取
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("tencentId", share.getString("tencentid", ""));
//                    json.put("tencentId", "100000001");
                    json.put("wechatActivityId", str);
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
                            .url(BaseUrl.BaseZhang + "api/tencentWechatActivityApp/getWechatActivityDetail")
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
                                Gson gson = new Gson();
                                friendInfo = gson.fromJson(str, FriendInfo.class);
                                if (friendInfo != null) {
                                    handler.sendEmptyMessage(1);
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
                    try {
                        rl_beginTime.setClickable(false);
                        edit_name.setText(friendInfo.getResponse().getTabItem1().getWechatActivityName());
                        if (friendInfo.getResponse().getTabItem1().getProductType().equals("LINK_WECHAT")) {
                            tv_AD_types.setText("微信品牌页");
                        } else {
                            tv_AD_types.setText("微信本地门店推广");
                        }
                        productTypes = friendInfo.getResponse().getTabItem1().getProductType();
                        tv_beginTime.setText(friendInfo.getResponse().getTabItem1().getStartDate());
                        tv_endTime.setText(friendInfo.getResponse().getTabItem1().getEndDate());
                        timeSeries = friendInfo.getResponse().getTabItem1().getTimeSeries();
                        String time = friendInfo.getResponse().getTabItem1().getTimeSeries().substring(0, 48);
                        list_str.clear();
                        for (int i = 0; i < 24; i++) {
                            if (time.substring(0, 2).equals("11")) {
                                list_str.add(i + "");
                            }
                            time = time.substring(2, time.length());//去掉前两个
                        }
                        if (list_str.size() == 0) {
                            tv_time_slot.setText("不限");
                        } else {
                            tv_time_slot.setText(list_str.size() + "个");
                        }
                        edit_budget.setText(friendInfo.getResponse().getTabItem1().getDailyBudget() + "");
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

    //获取当天的日期
    public String gettodayDate(int num) {
//        Date d = new_guest Date();
//        SimpleDateFormat sdf = new_guest SimpleDateFormat("yyyy-MM-dd");
//        return sdf.format(d);
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, num);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }
}
