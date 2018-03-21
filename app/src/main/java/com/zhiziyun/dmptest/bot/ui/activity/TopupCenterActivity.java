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
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.alipay.PayResult;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

import java.util.Map;


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
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
                        //支付请求
                        final String orderInfo = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018032002414716&biz_content=%7B%22body%22%3A%22%E6%B5%8B%E8%AF%95%E4%BD%BF%E7%94%A8%E7%9A%84%E6%94%AF%E4%BB%98%22%2C%22enable_pay_channels%22%3A%22moneyFund%2CbankPay%2Cbalance%22%2C%22goods_type%22%3A%220%22%2C%22out_trade_no%22%3A%2220180321161453000002%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%99%BA%E5%AD%90%E4%BA%91%E6%9C%8D%E5%8A%A1%22%2C%22timeout_express%22%3A%2260m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Flocalhost%3A8080%2Fapi-service%2FpayApp%2FnoticePayResult&sign=U6LHH0H%2FZlcF9KOgUCg8fzKNsX09jre30j%2BADxfFwQSPB%2Bfzr8%2BCf41MbSO5wFxcLJLlxhGfe3wKkqGPBQaI4u4783iq7Nz%2BVQN%2BpNcRhOT331u7rJ89MFZEY9FcnRjpTCB72esaxAmINfU7Gt%2FwP3zdhPB8GKHAcs3QKP6Tx7Fd32b8P8ozZLdMjFFhy6Ay9WEAb9Jty5PL1u76oLi3fBcUNef0jrQG%2FkFoyEOJXhMndhg11fkvIe2nw8TMGcyYEeprZTQ93MANbKQwCaWr%2F85U1eA4a%2BhSU3cT%2FimqKXyLW%2F4s%2FfdZ%2BcoUAK4h1%2FHUSD0V33UFDEZkVljPcC652g%3D%3D&sign_type=RSA2&timestamp=2018-03-21+16%3A14%3A55&version=1.0";
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(TopupCenterActivity.this);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                Log.i("msp", result.toString());

                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = result;
                                handler.sendMessage(msg);
                            }
                        };

                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
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
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Intent intent = new Intent(TopupCenterActivity.this, TopupStateActivity.class);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(TopupCenterActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        intent.putExtra("state", true);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(TopupCenterActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        intent.putExtra("state", false);
                    }
                    startActivity(intent);
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
