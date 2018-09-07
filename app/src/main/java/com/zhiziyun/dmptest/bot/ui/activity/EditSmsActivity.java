package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
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

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.Industry;
import com.zhiziyun.dmptest.bot.entity.SmsSignature;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.CustomDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.SelfDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.PopWin_industry;
import com.zhiziyun.dmptest.bot.view.PopWin_sms_type;
import com.zhiziyun.dmptest.bot.wheelview.WheelView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/29.
 * 编辑短信和添加短信共用
 */

public class EditSmsActivity extends BaseActivity implements View.OnClickListener {
    public static EditSmsActivity editSmsActivity;
    private EditText et_title, et_content, et_signature;
    public TextView tv_state, tv_smstitle, tv_word, tv_prompt, tv_type, tv_industry, tv_industry_type;
    private SharedPreferences share;
    private MyDialog dialog;
    private Intent intent;
    private int flag = 0;
    private LinearLayout line, line2, line_type;
    private SmsSignature smsSignature;
    private String signature = null;
    private final int MAXLENGTH = 63;
    private Button btn_commit;
    private SharedPreferences.Editor editors;
    private boolean smg = true;
    private ArrayList<String> list_industry = new ArrayList<>();
    private ArrayList<String> list_industry_type = new ArrayList<>();
    private ArrayList<String> list_industry_id = new ArrayList<>();
    private PopWin_industry popWin_industry;
    private Industry industry;
    private String smsCategoryId = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editsms);
        initView();
    }

    private void initView() {
//        //设置系统栏颜色
//        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_system.getLayoutParams();
//        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        editSmsActivity = this;
        intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        share = this.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        dialog = MyDialog.showDialog(this);
        //让textview里的某几个字变色
        TextView text = (TextView) findViewById(R.id.text);
        String str = "<font color='#bfbfbf'>【xxx官方旗舰店】</font>年货节来啦，每晚20:00积分福利社200积分限抢50或100优惠券呦，戳一戳{c.tb.cn/c.qP5u}<font color='#bfbfbf'>退订回T</font>";
        text.setTextSize(14);
        text.setText(Html.fromHtml(str));

        tv_smstitle = (TextView) findViewById(R.id.tv_smstitle);
        line = (LinearLayout) findViewById(R.id.line);
        line2 = (LinearLayout) findViewById(R.id.line2);
        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_prompt = (TextView) findViewById(R.id.tv_prompt);
        tv_word = (TextView) findViewById(R.id.tv_word);
        tv_word.setText("63字");
        tv_type = findViewById(R.id.tv_type);
        et_signature = (EditText) findViewById(R.id.et_signature);
        tv_industry = findViewById(R.id.tv_industry);
        tv_industry.setOnClickListener(this);
        tv_industry_type = findViewById(R.id.tv_industry_type);
        tv_industry_type.setOnClickListener(this);
        et_content.addTextChangedListener(watcher_content);
        et_signature.addTextChangedListener(watcher_signature);
        et_signature.setOnClickListener(this);
        btn_commit = (Button) findViewById(R.id.btn_commit);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.traceroute_rootview).setOnClickListener(this);
        line_type = findViewById(R.id.line_type);
        line_type.setOnClickListener(this);
        btn_commit.setOnClickListener(this);

        if (flag != 0) {//这里说明是点击列表进来的,编辑短信
            line2.setVisibility(View.GONE);
            tv_smstitle.setText("编辑短信");
            et_title.setText(intent.getStringExtra("title"));
            et_content.setText(intent.getStringExtra("content"));
            String state = intent.getStringExtra("state");
            signature(intent.getStringExtra("smsId"));
            if (intent.getStringExtra("type").equals("0")) {
                tv_type.setText("短信");
            } else {
                tv_type.setText("闪信");
            }
            tv_industry.setClickable(false);
            tv_industry_type.setClickable(false);
            line_type.setClickable(false);
            if (state.equals("未提交")) {
                tv_state.setText("预审中");
            } else {
                tv_state.setText(state);
                tv_prompt.setText(getIntent().getStringExtra("verifyMessage"));
                tv_prompt.setVisibility(View.VISIBLE);
            }
            if (state.equals("审核通过") || state.equals("审核中")) {
                //禁止输入
                et_title.setFocusable(false);
                et_title.setFocusableInTouchMode(false);
                et_title.setClickable(false);

                et_content.setFocusable(false);
                et_content.setFocusableInTouchMode(false);
                et_content.setClickable(false);

                et_signature.setFocusable(false);
                et_signature.setFocusableInTouchMode(false);
                et_signature.setClickable(false);

                tv_prompt.setVisibility(View.VISIBLE);
                btn_commit.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_commit.setClickable(false);

                line_type.setClickable(false);
            }
        } else {//创建短信
            line.setVisibility(View.GONE);
            signatureQuery();
        }
        getSmsCategory();//查行业
    }

    public void getIndustry(String id) {
        try {
            String parentId = null;
            for (int i = 0; i < industry.getRows().size(); i++) {
                if (industry.getRows().get(i).getEntityId().equals(id)) {
                    tv_industry_type.setText(industry.getRows().get(i).getName());
                    parentId = industry.getRows().get(i).getParentId();
                }
            }
            for (int i = 0; i < industry.getRows().size(); i++) {
                if (industry.getRows().get(i).getEntityId().equals(parentId) && !industry.getRows().get(i).isLeaf()) {
                    tv_industry.setText(industry.getRows().get(i).getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    TextWatcher watcher_content = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
        }

        @Override
        public void afterTextChanged(Editable s) {
            //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
            int num = MAXLENGTH - et_signature.length() - s.length();
            tv_word.setText(num + "字");
            InputFilter[] filters = {new InputFilter.LengthFilter(MAXLENGTH - et_signature.length())};
            et_content.setFilters(filters); //最大输入长度
            if (num > 8) {
                InputFilter[] filters2 = {new InputFilter.LengthFilter(8)};
                et_signature.setFilters(filters2); //最大输入长度
            } else {
                InputFilter[] filters2 = {new InputFilter.LengthFilter(MAXLENGTH - et_content.length())};
                et_signature.setFilters(filters2); //最大输入长度
            }
        }
    };

    TextWatcher watcher_signature = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
                int num = MAXLENGTH - et_content.length() - s.length();
                tv_word.setText(num + "字");
                InputFilter[] filters = {new InputFilter.LengthFilter(MAXLENGTH - et_signature.length())};
                et_content.setFilters(filters); //最大输入长度
                if (num > 8) {
                    InputFilter[] filters2 = {new InputFilter.LengthFilter(8)};
                    et_signature.setFilters(filters2); //最大输入长度
                } else {
                    InputFilter[] filters2 = {new InputFilter.LengthFilter(MAXLENGTH - et_content.length())};
                    et_signature.setFilters(filters2); //最大输入长度
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_industry:
                try {
                    View outerView1 = LayoutInflater.from(this).inflate(R.layout.view_industry, null);
                    //滚轮
                    final WheelView wv1 = (WheelView) outerView1.findViewById(R.id.wv1);
                    wv1.setItems(list_industry, 0);
                    TextView tv_ok = (TextView) outerView1.findViewById(R.id.tv_ok);
                    TextView tv_cancel = (TextView) outerView1.findViewById(R.id.tv_cancel);
                    //点击确定
                    tv_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            popWin_industry.dismiss();
                            String selectDateTimeStrToShow;
                            String mSelectDate = wv1.getSelectedItem();
                            selectDateTimeStrToShow = mSelectDate;
                            tv_industry.setText(selectDateTimeStrToShow);
                            //
                            list_industry_type.clear();
                            list_industry_id.clear();
                            for (int i = 0; i < industry.getRows().size(); i++) {
                                if (industry.getRows().get(i).getName().equals(selectDateTimeStrToShow)) {
                                    for (int j = 0; j < industry.getRows().size(); j++) {
                                        if (industry.getRows().get(i).getEntityId().equals(industry.getRows().get(j).getParentId())) {
                                            list_industry_type.add(industry.getRows().get(j).getName());
                                            list_industry_id.add(industry.getRows().get(j).getEntityId());
                                        }
                                    }
                                }
                            }
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_industry_type:
                try {
                    View outerView2 = LayoutInflater.from(this).inflate(R.layout.view_industry, null);
                    //滚轮
                    final WheelView wv2 = (WheelView) outerView2.findViewById(R.id.wv1);
                    wv2.setItems(list_industry_type, 0);
                    TextView tv_ok2 = (TextView) outerView2.findViewById(R.id.tv_ok);
                    TextView tv_cancel2 = (TextView) outerView2.findViewById(R.id.tv_cancel);
                    //点击确定
                    tv_ok2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            popWin_industry.dismiss();
                            String selectDateTimeStrToShow;
                            String mSelectDate = wv2.getSelectedItem();
                            selectDateTimeStrToShow = mSelectDate;
                            tv_industry_type.setText(selectDateTimeStrToShow);
                            for (int k = 0; k < list_industry_type.size(); k++) {
                                if (selectDateTimeStrToShow.equals(list_industry_type.get(k))) {
                                    smsCategoryId = list_industry_id.get(k);
                                }
                            }
                            for (int i = 0; i < industry.getRows().size(); i++) {
                                if (industry.getRows().get(i).getEntityId().equals(smsCategoryId)) {
                                    String str = industry.getRows().get(i).getSignature();
                                    if (!TextUtils.isEmpty(str)) {//如果不为空就显示返回的签名
                                        et_signature.setText(str);
                                        signature = str;
                                    } else {//如果为空就显示原有的签名
                                        et_signature.setText(smsSignature.getResponse().getSmsSignature());
                                        signature = smsSignature.getResponse().getSmsSignature();
                                    }
                                }
                            }
                        }
                    });
                    //点击取消
                    tv_cancel2.setOnClickListener(new View.OnClickListener() {
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
                    popWin_industry.setContentView(outerView2);
                    popWin_industry.show();//显示对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.line_type:
                try {
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //让背景变暗
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 0.7f;
                    getWindow().setAttributes(lp);
                    getWindow().setAttributes(lp);
                    //弹出窗体
                    PopWin_sms_type popWin_sms_type = new PopWin_sms_type(this, null, 0);
                    popWin_sms_type.setFlag(2);
                    popWin_sms_type.showAtLocation(findViewById(R.id.traceroute_rootview), Gravity.BOTTOM, 0, 0);//125
                    //监听popwin是否关闭，关闭的话让背景恢复
                    popWin_sms_type.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.alpha = 1f;
                            getWindow().setAttributes(lp);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.et_signature:
                try {
                    if (TextUtils.isEmpty(et_signature.getText().toString())) {
                        if (smg) {
                            //隐藏软键盘
                            InputMethodManager imms = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imms.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            final SelfDialog selfDialog = new SelfDialog(EditSmsActivity.this);
                            selfDialog.setTitle("消息提示");
                            selfDialog.setMessage("签名将自动插入短信内容的最前面。可填写品牌名称，3-8个字符，确认后不可修改");
                            selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick() {
                                    smg = false;
                                    //获得焦点
                                    et_signature.setFocusable(true);
                                    et_signature.setFocusableInTouchMode(true);
                                    selfDialog.dismiss();
                                }
                            });
                            selfDialog.show();
                        } else {
                            //获得焦点
                            et_signature.setFocusable(true);
                            et_signature.setFocusableInTouchMode(true);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_back:
                finish();
                toFinish();
                break;
            case R.id.traceroute_rootview:
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.btn_commit:
                try {
                    //判断数据是否填写完整
                    if (TextUtils.isEmpty(et_title.getText().toString()) ||
                            TextUtils.isEmpty(et_signature.getText().toString())
                            || TextUtils.isEmpty(et_content.getText().toString()) || tv_industry_type.getText().toString().equals("二级行业")) {
                        ToastUtils.showShort(EditSmsActivity.this, "请将数据填写完整");
                    } else {
                        //如果都填完整了，那就要提示签名是不是确认，如果是就保存下来，以后自动填进去并且无法修改
                        if (flag != 0) {//这里表示是携带数据进来的，用修改接口
                            String state = intent.getStringExtra("state");
                            if (state.equals("审核通过")) {
                                ToastUtils.showShort(EditSmsActivity.this, "审核通过的短信无法编辑");
                            } else if (state.equals("审核中")) {
                                ToastUtils.showShort(EditSmsActivity.this, "审核中的短信无法编辑");
                            } else {
                                smsEdit();
                            }
                        } else {
                            //新增短信
                            if (TextUtils.isEmpty(signature)) {//如果没有签名就提示，签了则不提示
                                final CustomDialog customDialog = new CustomDialog(EditSmsActivity.this);
                                customDialog.setTitle("消息提示");
                                customDialog.setMessage("以后创建的短信都用此签名【" + et_signature.getText().toString() + "】，是否确认？");
                                customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                                    @Override
                                    public void onYesClick() {
                                        isAddSms();
                                        customDialog.dismiss();
                                    }
                                });
                                customDialog.setNoOnclickListener("取消", new CustomDialog.onNoOnclickListener() {
                                    @Override
                                    public void onNoClick() {
                                        customDialog.dismiss();
                                    }
                                });
                                customDialog.show();
                                customDialog.setCancelable(false);//禁止点击回退键
                            } else {
                                isAddSms();
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void getSmsCategory() {
        //短信所属行业列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    if (tv_type.getText().toString().equals("短信")) {
                        jsonObject.put("type", "SMS");//SMS:短信行业类别
                    } else {
                        jsonObject.put("type", "FMS");//FMS:闪信行业类别
                    }
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "sms/getSmsCategory")
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
                                String str = response.body().string();
                                Gson gson = new Gson();
                                industry = gson.fromJson(str, Industry.class);
                                Message message = new Message();
                                message.what = 9;
                                message.obj = industry;
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

    public void isAddSms() {
        dialog.show();
        //是否可以创建短信接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "api/advertiserApp/canCreateSms")
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
                                JSONObject json = new JSONObject(response.body().string());
                                if (json.get("status").toString().equals("true")) {
                                    JSONObject jsonObject1 = new JSONObject(json.get("response").toString());
                                    if (jsonObject1.get("canBeCreated").toString().equals("true")) {
                                        //为true的话说明可以添加短信
                                        smsCreate();
                                    } else {
                                        if (jsonObject1.get("reasonCode").toString().equals("0")) {
                                            //为0说明无短信资质
                                            handler.sendEmptyMessage(8);
                                        } else {
                                            //资质未通过审核，操作终止
                                            handler.sendEmptyMessage(6);
                                        }
                                    }
                                } else {
                                    handler.sendEmptyMessage(7);
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

    public void smsCreate() {
        //短信创建接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("name", et_title.getText().toString());
                    jsonObject.put("content", et_content.getText().toString());
                    if (TextUtils.isEmpty(signature)) {//没签名的时候传输入的签名
                        jsonObject.put("signature", et_signature.getText().toString());
                    } else {//有签名的时候直接传签名
                        jsonObject.put("signature", signature);
                    }
                    if (tv_type.getText().toString().equals("短信")) {
                        jsonObject.put("type", 0);//短信0
                    } else {
                        jsonObject.put("type", 2);//闪信2
                    }
                    jsonObject.put("smsCategoryId", smsCategoryId);
                    jsonObject.put("userId", share.getString("userid", ""));
                    jsonObject.put("userName", share.getString("userName", ""));
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "sms/add")
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
                                JSONObject json = new JSONObject(response.body().string());
                                if (json.get("success").toString().equals("true")) {
                                    handler.sendEmptyMessage(1);
                                } else {
                                    Message message = new Message();
                                    message.what = 2;
                                    message.obj = json.get("msg").toString();
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

    public void signatureQuery() {
        //广告主短信签名查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "api/advertiserApp/getSmsSignature")
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
                                Gson gson = new Gson();
                                smsSignature = gson.fromJson(response.body().string(), SmsSignature.class);
                                handler.sendEmptyMessage(5);
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

    public void signature(final String id) {
        //获取短信签名
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "sms/signature")
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
                                JSONObject jsonObject1 = new JSONObject(response.body().string());
                                Message msg = new Message();
                                if (jsonObject1.get("success").toString().equals("true")) {
                                    msg.what = 10;
                                    msg.obj = jsonObject1.get("obj").toString();
                                } else {
                                    msg.what = 11;
                                    msg.obj = jsonObject1.get("msg").toString();
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

    public void smsEdit() {
        dialog.show();
        //短信编辑接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("entityId", intent.getStringExtra("smsId"));
                    jsonObject.put("name", et_title.getText().toString());
                    jsonObject.put("content", et_content.getText().toString());
                    jsonObject.put("userId", share.getString("userid", ""));
                    jsonObject.put("userName", share.getString("userName", ""));
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "sms/edit")
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
                                JSONObject json = new JSONObject(response.body().string());
                                if (json.get("success").toString().equals("true")) {
                                    handler.sendEmptyMessage(3);
                                } else {
                                    Message message = new Message();
                                    message.what = 4;
                                    message.obj = json.get("msg").toString();
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastUtils.showShort(EditSmsActivity.this, "添加成功");
                    dialog.dismiss();
                    finish();
                    break;
                case 2:
                    String str = (String) msg.obj;
                    ToastUtils.showShort(EditSmsActivity.this, str);
                    dialog.dismiss();
                    break;
                case 3:
                    ToastUtils.showShort(EditSmsActivity.this, "编辑成功");
                    dialog.dismiss();
                    finish();
                    break;
                case 4:
                    String strr = (String) msg.obj;
                    ToastUtils.showShort(EditSmsActivity.this, strr);
                    dialog.dismiss();
                    break;
                case 5:
                    try {
                        signature = smsSignature.getResponse().getSmsSignature();
                        if (!TextUtils.isEmpty(signature)) {//如果存在签名，就禁止输入
                            et_signature.setText(signature);
                            et_signature.setFocusable(false);
                            et_signature.setFocusableInTouchMode(false);
                            et_signature.setClickable(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    try {
                        dialog.dismiss();
                        final SelfDialog selfDialog = new SelfDialog(EditSmsActivity.this);
                        selfDialog.setTitle("消息提示");
                        selfDialog.setMessage("资质未通过审核，请耐心等待");
                        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                selfDialog.dismiss();
                                finish();
                            }
                        });
                        selfDialog.show();
                        selfDialog.setCancelable(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    dialog.dismiss();
                    ToastUtils.showShort(EditSmsActivity.this, "无数据");
                    break;
                case 8:
                    try {
                        dialog.dismiss();
                        final CustomDialog customDialog = new CustomDialog(EditSmsActivity.this);
                        customDialog.setTitle("消息提示");
                        customDialog.setMessage("无短信资质，请上传信息安全责任书照片");
                        customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                startActivity(new Intent(EditSmsActivity.this, AddQualificationActivity.class));
                                customDialog.dismiss();
                            }
                        });
                        customDialog.setNoOnclickListener("取消", new CustomDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                customDialog.dismiss();
                            }
                        });
                        customDialog.show();
                        customDialog.setCancelable(false);//禁止点击回退键
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 9:
                    try {
                        list_industry.clear();
                        final Industry industry = (Industry) msg.obj;
                        for (int i = 0; i < industry.getRows().size(); i++) {
                            if (!industry.getRows().get(i).isLeaf()) {
                                list_industry.add(industry.getRows().get(i).getName());
                            }
                        }
                        if (flag != 0) {
                            getIndustry(intent.getStringExtra("smsCategoryId"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 10:
                    try {
                        et_signature.setText(msg.obj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 11:
                    ToastUtils.showShort(EditSmsActivity.this, msg.obj.toString());
                    break;
            }
        }
    };

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
                    et_title = null;
                    et_content = null;
                    tv_state = null;
                    line = null;
                    list_industry.clear();
                    list_industry_type.clear();
                    list_industry_id.clear();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
