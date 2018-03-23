package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.alipay.PayResult;
import com.zhiziyun.dmptest.bot.entity.PayOrder;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2018/3/14.
 * 充值中心
 */

public class TopupCenterActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_balance, tv_companyname;
    private EditText et_amount;
    private RelativeLayout rl_zhifubao;
    private CheckBox cb_zhifubao;
    private SharedPreferences share;
    private Boolean bl_zhifubao = false;
    private MyDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//沙箱环境
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_center);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_balance.setText(getIntent().getStringExtra("amount"));
        tv_companyname = (TextView) findViewById(R.id.tv_companyname);
        tv_companyname.setText(share.getString("company", ""));
        et_amount = (EditText) findViewById(R.id.et_amount);
        rl_zhifubao = (RelativeLayout) findViewById(R.id.rl_zhifubao);
        cb_zhifubao = (CheckBox) findViewById(R.id.cb_1);
        rl_zhifubao.setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.linearLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                toFinish();
                break;
            case R.id.rl_zhifubao:
                if (bl_zhifubao) {
                    bl_zhifubao = false;
                    cb_zhifubao.setChecked(false);
                } else {
                    bl_zhifubao = true;
                    cb_zhifubao.setChecked(true);
                }
                break;
            case R.id.btn_commit:
                if (!TextUtils.isEmpty(et_amount.getText().toString())) {
                    if (bl_zhifubao) {
                        payOrder();
                    } else {
                        ToastUtils.showShort(TopupCenterActivity.this, "请选择支付方式");
                    }
                } else {
                    ToastUtils.showShort(TopupCenterActivity.this, "请输入充值金额");
                }
                break;
            case R.id.linearLayout:
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                        JSONObject json = new JSONObject(resultInfo);
                        JSONObject jsons = new JSONObject(json.get("alipay_trade_app_pay_response").toString());

                        String resultStatus = payResult.getResultStatus();
                        Intent intent = new Intent(TopupCenterActivity.this, TopupStateActivity.class);
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(TopupCenterActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                            intent.putExtra("state", true);
                            intent.putExtra("amount", jsons.get("total_amount").toString());
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(TopupCenterActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            intent.putExtra("state", false);
                        }
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public void payOrder() {
        //加载动画
//        dialog = MyDialog.showDialog(this);
//        dialog.show();
        //支付订单生成接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("subject", "账户充值");
                    json.put("body", "充值账户");
                    json.put("totalAmount", Float.parseFloat(et_amount.getText().toString()));
                    String data = json.toString().replace("\\", "");
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + URLEncoder.encode(data, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "payApp/createPayOrder")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                Gson gson = new Gson();
                                PayOrder payOrder = gson.fromJson(response.body().string(), PayOrder.class);
                                //支付请求
                                PayTask alipay = new PayTask(TopupCenterActivity.this);
                                Map<String, String> result = alipay.payV2(payOrder.getResponse().getOrderInfo(), true);
                                Log.i("msp", result.toString());

                                payresults(result.get("result"), result.get("resultStatus"));

                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = result;
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

    public void payresults(final String resultData, final String resultStatus) {
        //支付结果验证接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("resultData", new JSONObject(resultData));
                    json.put("resultStatus", Integer.parseInt(resultStatus));
                    String data = json.toString().replace("\\", "");
                    Log.i("infos",data);
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + URLEncoder.encode(data, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "payApp/confirmPayResult")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                Log.i("infos", response.body().string());
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
                    tv_balance = null;
                    tv_companyname = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
