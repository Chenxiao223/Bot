package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.wifi_probe.WifiProbeManager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/9.
 * 添加wifi人群
 */

public class AddWifiCorwdActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences share;
    private EditText edit_name;
    private String m_mac = "";
    private MyDialog dialog;
    private ListView mWifi_list;
    //wifi探针
    private int WiFi_time = 30 * 1000;//30秒扫描一次
    private List<String> mMacList = new ArrayList<>(); //存放mac的集合
    private ArrayAdapter<String> mAdapter;
    private WifiProbeManager mProbe;
    private Timer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wifi_corwd);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        mProbe = new WifiProbeManager();
        mWifi_list = (ListView) findViewById(R.id.list_wifi);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMacList);
        findViewById(R.id.iv_back).setOnClickListener(this);
        edit_name = (EditText) findViewById(R.id.edit_name);

        if (!isWifiConnected(this)) {
            ToastUtils.showShort(this, "请连接wifi");
        } else {
            initScan();
        }
        findViewById(R.id.tv_commit).setOnClickListener(this);
    }

    /**
     * 在一定时间内进行扫描（也就是读取文件夹内的mac地址）
     */
    private void initScan() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startScan();
            }
        }, 0, WiFi_time);
    }

    /**
     * 开始扫描 ， 如果wifi断开了数据就会没有了 ， 如果wifi地址变了内容也会变的
     */
    private void startScan() {
        mProbe.startScan(new WifiProbeManager.MacListListener() {
            @Override
            public void macList(final List<String> macList) {
                //因为在线程中进行扫描的，所以要切换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMacList.clear();
                        mMacList.addAll(macList);
                        mAdapter.notifyDataSetChanged();
                        mWifi_list.setAdapter(mAdapter);
                    }
                });
            }
        });
    }

    public String getMac() {
        m_mac = "";
        for (int i = 0; i < mMacList.size(); i++) {
            m_mac += mMacList.get(i) + ",";
        }
        return m_mac;
    }

    public void addWifiCorwd() {
        //加载动画
        dialog = MyDialog.showDialog(this);
        dialog.show();
        //新增wifi人群
        new Thread(new Runnable() {
            @RequiresApi(api = 26)
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("name", edit_name.getText().toString());
                    json.put("mac", getMac().replace(":", ""));
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url("http://test2.api.zhiziyun.com/api/v1/wifiSegmentInfo/insert")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                Message message = new Message();
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.get("success").toString().equals("true")) {
                                    message.what = 2;
                                    message.obj = jsonObject.get("msg").toString();
                                } else {
                                    message.what = 3;
                                    message.obj = jsonObject.get("msg").toString();
                                }
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    ToastUtils.showShort(AddWifiCorwdActivity.this, msg.obj.toString());
                    dialog.dismiss();
                    toFinish();
                    finish();
                    break;
                case 3:
                    ToastUtils.showShort(AddWifiCorwdActivity.this, msg.obj.toString());
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (!isWifiConnected(this)) {
            ToastUtils.showShort(this, "请连接wifi");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.tv_commit:
                if (TextUtils.isEmpty(edit_name.getText().toString())) {
                    ToastUtils.showShort(AddWifiCorwdActivity.this, "请输入名称");
                } else {
                    if (isWifiConnected(this)) {
                        addWifiCorwd();
                    } else {
                        ToastUtils.showShort(this, "请连接wifi");
                    }
                }
                break;
        }
    }

    //是否连接WIFI
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
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
                    mMacList.clear();
                    edit_name = null;
                    m_mac = "";
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
