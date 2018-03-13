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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.fragment.SMSFragment;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/14.
 * 添加短信活动页面
 */

public class AddSmsActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name;
    private TextView tv_send_object, tv_sms;
    private List<String> list_corwd = new ArrayList<>();
    private String smsid;
    private final int Flag_corwd = 1702;
    private final int Flag_sms = 1298;
    private JSONArray json_corwd = new JSONArray();
    private SharedPreferences share;
    private MyDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sms);
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
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = this.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        dialog = MyDialog.showDialog(this);
        tv_send_object = (TextView) findViewById(R.id.tv_send_object);
        tv_sms = (TextView) findViewById(R.id.tv_sms);
        findViewById(R.id.linearLayout).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.rl_sms).setOnClickListener(this);
        findViewById(R.id.rl_send_object).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.edit_name);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.linearLayout:
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                break;
            case R.id.rl_send_object:
                //通过判断list_corwd有没有值来确定要不要将值传入进入显示选中状态
                if (list_corwd.isEmpty()) {//无值
                    Intent it = new Intent(AddSmsActivity.this, CrowdActivity.class);
                    it.putExtra("flag_sms", 139);
                    startActivityForResult(it, Flag_corwd);
                } else {//有值的话将值传过去显示选中状态
                    Intent it = new Intent(AddSmsActivity.this, CrowdActivity.class);
                    it.putExtra("flag", 139);
                    it.putStringArrayListExtra("list", (ArrayList<String>) list_corwd);
                    startActivityForResult(it, Flag_corwd);
                }
                break;
            case R.id.rl_sms:
                startActivityForResult(new Intent(AddSmsActivity.this, ChooseSmsActivity.class), Flag_sms);
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(et_name.getText().toString()) || tv_send_object.getText().toString().equals("请选择")
                        || tv_sms.getText().toString().equals("请选择")) {
                    ToastUtils.showShort(this, "请将数据填完整");
                } else {
                    smsCreate();
                }
                break;
        }
    }

    public void smsCreate() {
        dialog.show();
        //短信活动创建接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("activityName", et_name.getText().toString());
                    jsonObject.put("tagIds", json_corwd);
                    jsonObject.put("smsId", smsid);
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
                            .url(BaseUrl.BaseZhang + "activityApp/createSmsActivity")
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
                                    handler.sendEmptyMessage(2);
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
                    ToastUtils.showShort(AddSmsActivity.this, "添加成功");
                    dialog.dismiss();
                    finish();
                    SMSFragment.smsFragment.clearAllData();
                    SMSFragment.smsFragment.getData(1, "");
                    break;
                case 2:
                    ToastUtils.showShort(AddSmsActivity.this, "添加失败");
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //区分从哪个页面回传的数据
            case Flag_corwd://人群
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        //用集合来接收这个集合
                        list_corwd = data.getStringArrayListExtra("list");
                        int num = list_corwd.size();
                        if (num != 0) {
                            json_corwd = new JSONArray();
                            tv_send_object.setText(num + "个");
                            tv_send_object.setTextColor(this.getResources().getColor(R.color.defaultcolor));
                            for (int i = 0; i < num; i++) {
                                json_corwd.put(list_corwd.get(i));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Flag_sms:
                try {
                    //用集合来接收这个集合
                    smsid = data.getStringExtra("smsId");
                    tv_sms.setText(data.getStringExtra("smsName"));
                    tv_sms.setTextColor(this.getResources().getColor(R.color.defaultcolor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                    et_name = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
