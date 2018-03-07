package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.SmsSignature;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

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
    private EditText et_title, et_content, et_signature;
    private TextView tv_state, tv_smstitle, tv_word, tv_prompt;
    private SharedPreferences share;
    private MyDialog dialog;
    private Intent intent;
    private int flag = 0;
    private LinearLayout line, line2;
    private SmsSignature smsSignature;
    private String signature = null;
    private final int MAXLENGTH = 64;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editsms);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

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
        tv_word.setText("64字");
        et_signature = (EditText) findViewById(R.id.et_signature);
        et_content.addTextChangedListener(watcher_content);
        et_signature.addTextChangedListener(watcher_signature);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.traceroute_rootview).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        if (flag != 0) {//这里说明是点击列表进来的
            line2.setVisibility(View.GONE);
            tv_smstitle.setText("编辑短信");
            et_title.setText(intent.getStringExtra("title"));
            et_content.setText(intent.getStringExtra("content"));
            String state = intent.getStringExtra("state");
            if (state.equals("未提交")) {
                tv_state.setText("预审中");
            } else {
                tv_state.setText(state);
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
            }
        } else {
            line.setVisibility(View.GONE);
        }
        signatureQuery();
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

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                //判断数据是否填写完整
                if (TextUtils.isEmpty(et_title.getText().toString()) ||
                        TextUtils.isEmpty(et_signature.getText().toString())
                        || TextUtils.isEmpty(et_content.getText().toString())) {
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
                            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("以后创建的短信都用此签名【" + et_signature.getText().toString() + "】，是否确认？")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            isAddSms();
                                        }
                                    })
                                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        } else {
                            isAddSms();
                        }
                    }

                }
                break;
        }
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
                            .url(BaseUrl.BaseZhang + "advertiserApp/canCreateSms")
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
                } catch (JSONException e) {
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
                    jsonObject.put("smsName", et_title.getText().toString());
                    jsonObject.put("smsContent", et_content.getText().toString());
                    if (TextUtils.isEmpty(signature)) {
                        jsonObject.put("smsSignature", et_signature.getText().toString());
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
                            .url(BaseUrl.BaseZhang + "smsApp/createSms")
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
                                    handler.sendEmptyMessage(1);
                                } else {
                                    Message message = new Message();
                                    message.what = 2;
                                    message.obj = json.get("errormsg").toString();
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
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
                            .url(BaseUrl.BaseZhang + "advertiserApp/getSmsSignature")
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
                } catch (JSONException e) {
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
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("smsId", intent.getStringExtra("smsId"));
                    jsonObject.put("smsName", et_title.getText().toString());
                    jsonObject.put("smsContent", et_content.getText().toString());
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
                            .url(BaseUrl.BaseZhang + "smsApp/editSms")
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
                                    handler.sendEmptyMessage(3);
                                } else {
                                    Message message = new Message();
                                    message.what = 4;
                                    message.obj = json.get("errormsg").toString();
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
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
                    signature = smsSignature.getResponse().getSmsSignature();
                    if (!TextUtils.isEmpty(signature)) {//如果存在签名，就禁止输入
                        et_signature.setText(signature);
                        et_signature.setFocusable(false);
                        et_signature.setFocusableInTouchMode(false);
                        et_signature.setClickable(false);
                    }
                    break;
                case 6:
                    dialog.dismiss();
                    new AlertDialog.Builder(EditSmsActivity.this).setTitle("温馨提示").setMessage("资质未通过审核，请耐心等待")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //资质未通过审核，操作终止
                                    finish();
                                }
                            }).show();
                    break;
                case 7:
                    dialog.dismiss();
                    ToastUtils.showShort(EditSmsActivity.this, "无数据");
                    break;
                case 8:
                    dialog.dismiss();
                    new AlertDialog.Builder(EditSmsActivity.this).setTitle("温馨提示").setMessage("无短信资质，请跳转到添加短信资质页")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(EditSmsActivity.this, AddQualificationActivity.class));
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
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
                et_title = null;
                et_content = null;
                tv_state = null;
                line = null;
                System.gc();
            }
        }, 500);
    }
}
