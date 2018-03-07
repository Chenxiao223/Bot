package com.zhiziyun.dmptest.bot.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.VersionUpdate;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.CustomDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.NoDoubleClickListener;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/17.
 */

public class LoginActivity extends BaseActivity {
    private EditText tv_username, tv_password;
    private LinearLayout traceroute_rootview;
    SharedPreferences.Editor editors;
    private MyDialog dialog;
    private VersionUpdate versionUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        versionUpdate();//版本更新
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        SharedPreferences sharedPreferences = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        editors = sharedPreferences.edit();

        tv_username = (EditText) findViewById(R.id.edit_username);
        tv_password = (EditText) findViewById(R.id.edit_password);
        tv_username.setText(getemail());
        tv_username.setSelection(getemail().length());//将光标定位到最后
        tv_password.setText(getpassword());
        traceroute_rootview = (LinearLayout) findViewById(R.id.traceroute_rootview);
        traceroute_rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
        //点击登录
        findViewById(R.id.btn_login).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (tv_username.getText().toString().equals("")) {
                    tv_username.setError("账号不能为空！");
                } else if (tv_password.getText().toString().equals("")) {
                    tv_password.setError("密码不能为空！");
                } else {
                    editors.putString("email", tv_username.getText().toString());
                    editors.putString("password", tv_password.getText().toString());
                    recordLogin();
                    Login();
                }
            }
        });
    }

    public String getemail() {
        SharedPreferences shared = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        return shared.getString("email", "");
    }

    public String getpassword() {
        SharedPreferences shared = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        return shared.getString("password", "");
    }

    public void Login() {
        //加载动画
        dialog = MyDialog.showDialog(this);
        dialog.SHOW();
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
                            .url(BaseUrl.BaseLogin)
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String str = response.body().string();
                            Log.i("infos", str);
                            try {
                                JSONObject json = new JSONObject(str);
                                if (json.get("success").toString().equals("true")) {//返回为true表示登录成功
                                    editors.putString("accountid", json.get("accountid").toString());
                                    editors.putString("siteid", json.get("siteid").toString());
//                                    editors.putString("siteid", "tuOiZ0TghUl");
                                    editors.putString("company", json.get("company").toString());
                                    editors.putString("logourl", json.get("logourl").toString());
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
                    ToastUtils.showShort(LoginActivity.this, "账号或密码错误");
                    break;
                case 2:
                    if (versionUpdate.getResponse().isNeedUpdate()) {
                        //点击弹出对话框
                        final CustomDialog customDialog = new CustomDialog(LoginActivity.this);
                        customDialog.setTitle(versionUpdate.getResponse().getTitle());
                        customDialog.setMessage(versionUpdate.getResponse().getMessage());
                        customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                loadNewVersionProgress(versionUpdate.getResponse().getDownloadUrl());//下载最新的版本程序
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
                    }
                    break;
            }
        }
    };

    /**
     * 下载新版本程序，需要子线程
     */
    private void loadNewVersionProgress(String url) {
        final String uri = url;
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        //启动子线程下载任务
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(uri, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    //下载apk失败
                    ToastUtils.showShort(LoginActivity.this, "下载新版本失败");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(uri);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time= System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time+"updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    public void recordLogin() {
        //记录用户登录
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteid", "0zoTLi29XRgq");
                    json.put("zzid", "0zoTLha93ySI");//广告活动编号
                    json.put("appid", "4324321");//应用编号
                    TelephonyManager telephonyManager = (TelephonyManager) LoginActivity.this.getSystemService(LoginActivity.this.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
                    json.put("imei", imei);
                    json.put("who", "");//注册用户编号
                    json.put("idfa", "");
                    json.put("idfy", "");
                    json.put("channelid", "");//渠道id
                    json.put("os", 0);//系统安桌是0
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = json.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseJiang + "logup")
                            .addHeader("apiid", "0zoTLi29XRgq")
                            .addHeader("token", URLEncoder.encode(Token.gettoken2(), "utf-8"))
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("response", "登录返回：" + e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.i("response", "登录返回：" + response.body().string());
                        }
                    });

                } catch (Exception e) {
                    Log.i("response", e.toString());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void versionUpdate() {
        //版本更新
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("version", "v1.1.3");
                    json.put("device", "android");
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "versionApp/checkAppUpdate")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String str = response.body().string();
                            Log.i("infos", str);
                            try {
                                Gson gson = new Gson();
                                versionUpdate = gson.fromJson(str, VersionUpdate.class);
                                //如果版本不一样就更新
                                if (!versionUpdate.getResponse().getVersion().equals("v1.1.3")) {
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
}
