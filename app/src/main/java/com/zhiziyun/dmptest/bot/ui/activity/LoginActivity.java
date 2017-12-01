package com.zhiziyun.dmptest.bot.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/17.
 */

public class LoginActivity extends Activity {
    private EditText tv_username, tv_password;
    private LinearLayout traceroute_rootview;
    SharedPreferences.Editor editors;
    private MyDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        SharedPreferences sharedPreferences=getSharedPreferences("logininfo",Context.MODE_PRIVATE);
        editors=sharedPreferences.edit();

        tv_username = (EditText) findViewById(R.id.edit_username);
        tv_password = (EditText) findViewById(R.id.edit_password);
        tv_username.setText(getemail());

        tv_password.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        traceroute_rootview = (LinearLayout) findViewById(R.id.traceroute_rootview);
        traceroute_rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
    }

    public String getemail(){
        SharedPreferences shared=getSharedPreferences("logininfo",Context.MODE_PRIVATE);
        return shared.getString("email","");
    }

    public void login(View view) {
        if (tv_username.getText().toString().equals("")) {
            tv_username.setError("账号不能为空！");
        } else if (tv_password.getText().toString().equals("")) {
            tv_password.setError("密码不能为空！");
        } else {
            editors.putString("email",tv_username.getText().toString());
            Login();
        }
    }

    public void Login() {
        //加载动画
        dialog = MyDialog.showDialog(this);
        dialog.show();
        //登录
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("email", tv_username.getText().toString())
                            .add("password", tv_password.getText().toString()).build();

                    final Request request = new Request.Builder()
                            .url("http://test2.zhiziyun.com/site-zcloud-v2/loginuser/login.action")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject json=new JSONObject(response.body().string());
                                if (!TextUtils.isEmpty(json.get("siteid").toString())) {//返回为true表示登录成功
                                    editors.putString("accountid",json.get("accountid").toString());
                                    editors.putString("siteid",json.get("siteid").toString());
                                    editors.putString("company",json.get("company").toString());
                                    editors.putString("logourl",json.get("logourl").toString());
                                    editors.commit();//提交
                                    startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                                    finish();
                                    dialog.dismiss();
                                } else {
                                    handler.sendEmptyMessage(1);
                                }
                            } catch (JSONException e) {
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
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
