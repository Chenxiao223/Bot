package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.QueryCrowd;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.PopWin_Os;
import com.zhiziyun.dmptest.bot.view.PopWin_time_length;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * 新建人群和查看人群共用
 */

public class AddCorwdActivity extends BaseActivity implements View.OnClickListener {
    public static AddCorwdActivity addCorwdActivity;
    private SlidingLayout slidingLayout;
    public TextView tv_beginTime, tv_endTime, tv_tanzhen, edit_name, tv_liveness, tv_game, tv_app, tv_property, tv_system, tv_brand, tv_length, tv_smstitle;
    private final int FLAG = 1407;
    private final int FLAG_GAME = 1732;
    private final int FLAG_APP = 9233;
    private final int FLAG_POPULATION = 5746;
    private final int FLAG_ACTIVE = 4232;
    private final int Flag_brand = 1702;
    private SharedPreferences share;
    public String activity = "";
    private String brand = "";
    private String probes = "";
    private String lable = "";
    public ArrayList<String> list_lable = new ArrayList<>();
    public ArrayList<String> list_corwd = new ArrayList<>();
    private MyDialog dialog;
    private int visitorType = -1;
    private EditText edit_distance;
    public String m_os = "";
    public List<String> list_brand = new ArrayList<>();
    private Button btn_commit;
    public int stayDuration = 6;
    private RelativeLayout rl_beginTime, rl_endTime, rl_system, rl_brand, rl_length, rl_liveness;

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
        tv_system = (TextView) findViewById(R.id.tv_system);
        tv_brand = (TextView) findViewById(R.id.tv_brand);
        btn_commit = findViewById(R.id.btn_commit);
        edit_distance = (EditText) findViewById(R.id.edit_distance);
        edit_distance.setText("100");//默认是100
        tv_length = findViewById(R.id.tv_length);
        rl_beginTime = findViewById(R.id.rl_beginTime);
        rl_endTime = findViewById(R.id.rl_endTime);
        rl_system = findViewById(R.id.rl_system);
        rl_brand = findViewById(R.id.rl_brand);
        rl_length = findViewById(R.id.rl_length);
        rl_liveness = findViewById(R.id.rl_liveness);

        //初始化日期
        tv_beginTime.setText(gettodayDate() + " 00:00:01");
        tv_endTime.setText(gettodayDate() + " 23:59:59");

        findViewById(R.id.tv_back).setOnClickListener(this);
        rl_beginTime.setOnClickListener(this);
        rl_endTime.setOnClickListener(this);
        findViewById(R.id.rl_tanzhen).setOnClickListener(this);
        rl_liveness.setOnClickListener(this);
        findViewById(R.id.rl_game).setOnClickListener(this);
        findViewById(R.id.rl_app).setOnClickListener(this);
        findViewById(R.id.rl_property).setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        findViewById(R.id.linearLayout).setOnClickListener(this);
        rl_system.setOnClickListener(this);
        rl_brand.setOnClickListener(this);
        rl_length.setOnClickListener(this);

        //果冻弹跳效果
        slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
        slidingLayout.setSlidingOffset(0.2f);

        if (getIntent().getIntExtra("flag", 0) == 123) {
            btn_commit.setVisibility(View.GONE);
            tv_smstitle = findViewById(R.id.tv_smstitle);
            tv_smstitle.setText("查看人群");
            //edit_name不能编辑
            edit_name.setFocusable(false);
            edit_name.setFocusableInTouchMode(false);
            edit_name.setClickable(false);
            edit_distance.setFocusable(false);
            edit_distance.setFocusableInTouchMode(false);
            edit_distance.setClickable(false);
            rl_beginTime.setClickable(false);
            rl_endTime.setClickable(false);
            rl_length.setClickable(false);
            rl_system.setClickable(false);
            rl_brand.setClickable(false);
            rl_liveness.setClickable(false);
            setCorwd();
        }
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
            case R.id.rl_brand:
                //通过判断list_corwd有没有值来确定要不要将值传入进入显示选中状态
                if (list_brand.isEmpty()) {//无值
                    Intent it = new Intent(AddCorwdActivity.this, ChooseBrandActivity.class);
                    it.putExtra("brand", tv_system.getText().toString());
                    startActivityForResult(it, Flag_brand);
                } else {//有值的话将值传过去显示选中状态
                    Intent it = new Intent(AddCorwdActivity.this, ChooseBrandActivity.class);
                    it.putExtra("flag", 123);
                    it.putStringArrayListExtra("list", (ArrayList<String>) list_brand);
                    it.putExtra("brand", tv_system.getText().toString());
                    startActivityForResult(it, Flag_brand);
                }
                break;
            case R.id.rl_system:
                //让背景变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                getWindow().setAttributes(lp);
                //弹出窗体
                PopWin_Os popWin_os = new PopWin_Os(AddCorwdActivity.this, null, 0);
                popWin_os.showAtLocation(findViewById(R.id.linearLayout), Gravity.BOTTOM, 0, 0);//125
                //监听popwin是否关闭，关闭的话让背景恢复
                popWin_os.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
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
                        .setType(new boolean[]{true, true, true, true, true, true})// 对应年月日时分秒
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setLabel("年", "月", "日", "时", "分", "秒")
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
                        .setType(new boolean[]{true, true, true, true, true, true})// 对应年月日时分秒
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .build();

                pvTime2.show();
                break;
            case R.id.rl_tanzhen:
                try {
                    //通过判断list_corwd有没有值来确定要不要将值传入进入显示选中状态
                    if (list_corwd.isEmpty()) {//无值
                        startActivityForResult(new Intent(AddCorwdActivity.this, TanzhenActivity.class), FLAG);
                    } else {//有值的话将值传过去显示选中状态
                        Intent it = new Intent(AddCorwdActivity.this, TanzhenActivity.class);
                        it.putExtra("flag", 123);
                        it.putStringArrayListExtra("list", (ArrayList<String>) list_corwd);
                        startActivityForResult(it, FLAG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_commit://点击提交
                try {
                    if (TextUtils.isEmpty(edit_name.getText().toString()) || tv_tanzhen.getText().equals("请选择")) {
                        ToastUtils.showShort(AddCorwdActivity.this, "请将带星号部分填写完整");
                    } else {
                        if (Integer.parseInt(edit_distance.getText().toString()) > 100) {
                            ToastUtils.showShort(AddCorwdActivity.this, "请输入0-100的数");
                        } else {
                            dialog = MyDialog.showDialog(this);
                            dialog.show();
                            createCorwd();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
//                Intent it_property = new Intent(this, LabelActivity.class);
//                it_property.putExtra("flag", 2);
//                startActivityForResult(it_property, FLAG_POPULATION);
                //通过判断list_originality有没有值来确定要不要将值传入进入现实选中状态
                if (list_lable.isEmpty()) {//无值
                    Intent it_lable = new Intent(this, LabelActivity.class);
                    startActivityForResult(it_lable, FLAG_POPULATION);
                } else {//有值的话将值传过去显示选中状态
                    Intent it_lable = new Intent(this, LabelActivity.class);
                    it_lable.putExtra("flag", 123);
                    it_lable.putStringArrayListExtra("list", (ArrayList<String>) list_lable);
                    startActivityForResult(it_lable, FLAG_POPULATION);
                }
                break;
            case R.id.rl_liveness://用户活跃度
                Intent it_active = new Intent(AddCorwdActivity.this, UserActiveActivity.class);
                it_active.putExtra("flag", 3);
                startActivityForResult(it_active, FLAG_ACTIVE);
                break;
            case R.id.rl_length:
                //隐藏软键盘
                InputMethodManager imm4 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm4.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //让背景变暗
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha = 0.7f;
                getWindow().setAttributes(lp2);
                getWindow().setAttributes(lp2);
                //弹出窗体
                PopWin_time_length popWin_time_length = new PopWin_time_length(this, null, 0);
                popWin_time_length.showAtLocation(findViewById(R.id.linearLayout), Gravity.BOTTOM, 0, 0);//125
                //监听popwin是否关闭，关闭的话让背景恢复
                popWin_time_length.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FLAG_POPULATION:
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        lable = "";//为了防止第二次进去选中回来后叠加，在这里归零
                        //用集合来接收这个集合
                        list_lable = data.getStringArrayListExtra("list");
                        int num = list_lable.size();
                        if (num != 0) {
                            tv_property.setText(num + "个");
                        }
                        for (int i = 0; i < list_lable.size(); i++) {
                            if (i == 0) {
                                lable = lable + list_lable.get(i);
                            } else {
                                lable = lable + "|" + list_lable.get(i);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
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
            case Flag_brand:
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        brand = "";//为了防止第二次进去选中回来后叠加，在这里归零
                        //以后用集合来接收这个集合
                        list_brand = data.getStringArrayListExtra("list");
                        int num = list_brand.size();
                        tv_brand.setText(num + "个");
                        tv_brand.setTextColor(this.getResources().getColor(R.color.defaultcolor));
                        for (int i = 0; i < num; i++) {
                            if (i == 0) {
                                brand = brand + data.getStringArrayListExtra("list").get(i);
                            } else {
                                brand = brand + "," + data.getStringArrayListExtra("list").get(i);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                    json.put("endTime", tv_endTime.getText().toString());
                    json.put("tags", lable);
                    json.put("probes", probes);
                    if (!TextUtils.isEmpty(activity)) {//如果为空就不填
                        json.put("activity", activity);
                    }
                    json.put("probeDistance", Integer.parseInt(edit_distance.getText().toString()));
                    if (!TextUtils.isEmpty(m_os)) {
                        json.put("os", m_os);
                    }
                    if (tv_brand.getText().toString().equals("不限")) {
                        json.put("phoneBrand", brand);
                    }
                    if (stayDuration != 6) {
                        json.put("stayDuration", stayDuration);
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
                            .url(BaseUrl.BaseTest + "builtCrowd/insert")
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

    public void setCorwd() {
        //查询单个人群
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("id", Integer.parseInt(getIntent().getStringExtra("id")));
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
                            .url(BaseUrl.BaseTest + "builtCrowd/queryById")
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
                                QueryCrowd queryCrowd = gson.fromJson(response.body().string(), QueryCrowd.class);
                                Message message = new Message();
                                message.what = 4;
                                message.obj = queryCrowd;
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
                case 4:
                    try {
                        QueryCrowd queryCrowd = (QueryCrowd) msg.obj;
                        edit_name.setText(queryCrowd.getObj().getName());
                        tv_beginTime.setText(queryCrowd.getObj().getBeginTime());
                        tv_endTime.setText(queryCrowd.getObj().getEndTime());
                        List<String> list_tanzhen = java.util.Arrays.asList(queryCrowd.getObj().getProbes().split("\\|"));
                        tv_tanzhen.setText(list_tanzhen.size() + "个");
                        list_corwd = setArrayList(list_tanzhen);
                        tv_liveness.setText(queryCrowd.getObj().getActivity());
                        edit_distance.setText(queryCrowd.getObj().getProbeDistance() + "");
                        if (TextUtils.isEmpty(queryCrowd.getObj().getStayDuration())) {
                            tv_length.setText(getTimeLength("6"));
                        } else {
                            tv_length.setText(getTimeLength(queryCrowd.getObj().getStayDuration()));
                        }
                        try {
                            if (queryCrowd.getObj().getOs().toString().equals("imei")) {
                                tv_system.setText("安卓");
                            } else {
                                tv_system.setText("苹果");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tv_brand.setText(queryCrowd.getObj().getPhoneBrand());
                        tv_property.setText("");
                        list_lable = new ArrayList<>();
                        String id = queryCrowd.getObj().getTags() + "";
                        if (!TextUtils.isEmpty(id)) {
                            String str[] = id.split("\\|");//"|"要转义
                            for (int i = 0; i < str.length; i++) {
                                list_lable.add(str[i]);
                            }
                        }
                        tv_property.setText(list_lable.size() + "个");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    //List转ArrayList方法
    public ArrayList<String> setArrayList(List<String> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(list.get(i));
        }
        return arrayList;
    }

    public String getTimeLength(String num) {
        switch (num) {
            case "0":
                return "0-10分钟";
            case "1":
                return "10-30分钟";
            case "2":
                return "30-60分钟";
            case "3":
                return "60-120分钟";
            case "4":
                return "120-180分钟";
            case "5":
                return "180分钟以上";
            case "6":
                return "不限";
        }
        return "";
    }

//    public String getTags() {
//        //为了能够从Set集合取到某个位置的元素，把它转成ArrayList
//        List<String> list = new ArrayList<>(list_lable);
//        String str = "";
//        for (int i = 0; i < list.size(); i++) {
//            if (i == 0) {
//                str = str + list.get(i);
//            } else {
//                str = str + "|" + list.get(i);
//            }
//        }
//        return str;
//    }

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
