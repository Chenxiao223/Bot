package com.zhiziyun.dmptest.bot.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zhiziyun.dmptest.bot.R;

/**
 * Created by Administrator on 2017/11/17.
 */

public class LoginActivity extends Activity {
    private EditText tv_username, tv_password;
    private LinearLayout traceroute_rootview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        tv_username = findViewById(R.id.edit_username);
        tv_password = findViewById(R.id.edit_password);
        tv_username.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        tv_password.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        traceroute_rootview=findViewById(R.id.traceroute_rootview);
        traceroute_rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
    }

    public void login(View view) {
//        TelephonyManager tm= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);//获取设备号
//        String deviceid=tm.getDeviceId();
        if (tv_username.getText().toString().equals("")) {
            tv_username.setError("账号不能为空！");
        } else if (tv_password.getText().toString().equals("")) {
            tv_password.setError("密码不能为空！");
        } else {
            startActivity(new Intent(this, HomePageActivity.class));
            finish();
        }
    }
}
