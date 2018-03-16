package com.zhiziyun.dmptest.bot.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.VersionUpdate;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.CustomDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.NetWorkUtil;
import com.zhiziyun.dmptest.bot.util.NoDoubleClickListener;
import com.zhiziyun.dmptest.bot.util.SelfDialog;
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
import java.util.TimeZone;

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
    private SharedPreferences.Editor editors;
    private MyDialog dialog;
    private VersionUpdate versionUpdate;
    private SharedPreferences.Editor editorss;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        if (!getState()) {
            //第一次进来
            saveState();
            startApp(true);//首次打开应用
        } else {
            startApp(false);
        }
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
                    if (versionUpdate.getResponse().isNeedForcedUpdate()) {//是否需要强制更新
                        //强制更新没有取消选项
                        final SelfDialog selfDialog = new SelfDialog(LoginActivity.this);
                        selfDialog.setTitle(versionUpdate.getResponse().getTitle());
                        selfDialog.setMessage(versionUpdate.getResponse().getMessage());
                        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                loadNewVersionProgress(versionUpdate.getResponse().getDownloadUrl());//下载最新的版本程序
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.show();
                        selfDialog.setCancelable(false);//禁止点击回退键
                    } else {
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
                        customDialog.setCancelable(false);//禁止点击回退键
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
        final ProgressDialog pd;//进度条对话框
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
        pd.setCancelable(false);//禁止点击回退键
    }

    /**
     * 从服务器获取apk文件的代码
     * 传入网址uri，进度条对象即可获得一个File文件
     * （要在子线程中执行哦）
     */
    public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            long time = System.currentTimeMillis();//当前时间的毫秒数
            File file = new File(Environment.getExternalStorageDirectory(), time + "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    /**
     * 安装apk
     */
    protected void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判读版本是否在7.0以上,安装完成后打开APP
        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(this, "com.zhiziyun.dmptest.bot", file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
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
                    json.put("version", BaseUrl.BaseVersion);
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
                                //判断是否需要更新
                                if (versionUpdate.getResponse().isNeedUpdate()) {
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

    public void saveState() {
        SharedPreferences sharedPreferences = getSharedPreferences("state", Context.MODE_PRIVATE);
        editorss = sharedPreferences.edit();
        editorss.putBoolean("state", true);
        editorss.commit();//提交
    }

    public boolean getState() {
        SharedPreferences shared = getSharedPreferences("state", Context.MODE_PRIVATE);
        return shared.getBoolean("state", false);
    }

    public void tagging() {
        //打标签
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        final String imei = telephonyManager.getDeviceId();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().get().url("http://trace.zhiziyun.com/open/oem/cm.gif?tagid=x2RIi0u7FKg&dpid=" + imei).build();
                Call newCall = client.newCall(request);
                try {
                    Response execute = newCall.execute();
                    if (execute.isSuccessful()) {
                        String string = execute.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void startApp(final boolean bol) {
        //记录打开APP
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteid", "0zoTLi29XRgq");
                    json.put("zzid", "0zoTLha93ySI");//广告活动编号
                    json.put("appid", "com.zhiziyun.dmptest.bot");//应用编号
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(LoginActivity.this.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
                    json.put("deviceid", imei);//设备编号
                    json.put("imei", imei);
                    json.put("idfa", "");
                    json.put("idfy", "");
                    json.put("channelid", "");//渠道id
                    json.put("install", bol);
                    TimeZone tz = TimeZone.getDefault();
                    String strTz = tz.getDisplayName(false, TimeZone.SHORT);
                    json.put("tx", strTz);//时区
                    json.put("devicetype", android.os.Build.MODEL);//设备类型
                    json.put("op", getSimOperatorInfo());//运营商
                    json.put("netword", NetWorkUtil.getNetworkType(LoginActivity.this));//联网方式
                    json.put("os", 0);
                    json.put("appzzid", "0zoTLi29XRgq");
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
                            .url(BaseUrl.BaseJiang + "startup")
                            .addHeader("apiid", "0zoTLi29XRgq")
                            .addHeader("token", URLEncoder.encode(Token.gettoken2(), "utf-8"))
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("response", "打开APP返回：" + e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.i("response", "打开APP返回：" + response.body().string());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //获取运营商
    public String getSimOperatorInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String operatorString = telephonyManager.getSimOperator();

        if (operatorString == null) {
            return "未知";
        }

        if (operatorString.equals("46000") || operatorString.equals("46002")) {
            //中国移动
            return "中国移动";
        } else if (operatorString.equals("46001")) {
            //中国联通
            return "中国联通";
        } else if (operatorString.equals("46003")) {
            //中国电信
            return "中国电信";
        }
        //error
        return "未知";
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        //判断是否为android6.0系统版本，如果是，需要动态添加权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ToastUtils.showShort(this, "没有权限,请手动开启定位权限");
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 2);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
                }
            }
        }
        tagging();//打标签
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        switch (requestCode) {
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    ToastUtils.showShort(this, "请允许操作SDCard！！");
                }
                break;
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                } else {
                    // 没有获取到权限，做特殊处理
                    ToastUtils.showShort(this, "获取位置权限失败，请手动开启");
                }
                break;
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
