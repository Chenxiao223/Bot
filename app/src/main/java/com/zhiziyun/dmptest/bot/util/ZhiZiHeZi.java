package com.zhiziyun.dmptest.bot.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ZhiZiHeZi extends Application {
    private static ZhiZiHeZi mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        AppFrontBackHelper helper = new AppFrontBackHelper();
        helper.register(ZhiZiHeZi.this, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
                tagging();
                startApp(false);
            }

            @RequiresApi(api = 26)
            @Override
            public void onBack() {
                //应用切到后台处理
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(new Intent(ZhiZiHeZi.this, MyService.class));
                } else {
                    startService(new Intent(ZhiZiHeZi.this, MyService.class));//启动服务
                }
            }
        });
    }

    public static ZhiZiHeZi getInstance() {
        return mInstance;
    }

    /**
     * 获取全局context
     *
     * @return
     */
    public static Context getAppContext() {
        return mInstance;
    }

    public void tagging() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    json.put("appid", getPackageName());//应用编号
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(ZhiZiHeZi.this.TELEPHONY_SERVICE);
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
                    json.put("netword", NetWorkUtil.getNetworkType(ZhiZiHeZi.this));//联网方式
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
        //错误
        return "未知";
    }
}
