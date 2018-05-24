package com.zhiziyun.dmptest.bot.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.BaseUrl;

/**
 * Created by Administrator on 2018/5/10.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    public static SettingActivity activity;
    public TextView tv_phone_number, tv_version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        activity = this;
        tv_phone_number = (TextView) findViewById(R.id.tv_phone_number);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText(BaseUrl.BaseVersion);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.rl_phone_number).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.rl_version).setOnClickListener(this);
        findViewById(R.id.rl_mac).setOnClickListener(this);
        if (TextUtils.isEmpty(getTel())) {
            //如果没有填号码就从手机获取，否则从缓存获取
            tv_phone_number.setText(getphoneNM());
        } else {
            tv_phone_number.setText(getTel());
        }
    }

    public String getTel() {
        SharedPreferences shared = SettingActivity.this.getSharedPreferences("phone", Context.MODE_PRIVATE);
        return shared.getString("tel", "");
    }

    public String getphoneNM() {
        try {
            //判断是否为android6.0系统版本，如果是，需要动态添加权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    //有权限才执行
                    TelephonyManager telephonyManager = (TelephonyManager) SettingActivity.this.getSystemService(SettingActivity.this.TELEPHONY_SERVICE);
                    final String te1 = telephonyManager.getLine1Number();//获取本机号码
                    return te1.replace("+86", "");
                }
            } else {//6.0以下版本直接获取
                TelephonyManager telephonyManager = (TelephonyManager) SettingActivity.this.getSystemService(SettingActivity.this.TELEPHONY_SERVICE);
                final String te1 = telephonyManager.getLine1Number();//获取本机号码
                return te1.replace("+86", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_phone_number:
                Intent it = new Intent(SettingActivity.this, EditPhoneNMActivity.class);
                it.putExtra("phoneNM", tv_phone_number.getText().toString());
                startActivityForResult(it, 543);
                break;
            case R.id.btn_commit:
                finish();
                clear();
                HomePageActivity.activity.finish();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                break;
            case R.id.rl_version:
                break;
            case R.id.rl_mac:
                startActivity(new Intent(SettingActivity.this, MacDeviceActivity.class));
                break;
        }
    }

    public void clear() {
        SharedPreferences sharedPreferences = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editors = sharedPreferences.edit();
        editors.putString("email", "");
        editors.putString("password", "");
        editors.commit();//提交
    }
}
