package com.zhiziyun.dmptest.bot.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.zhiziyun.dmptest.bot.ui.activity.HomePageActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;

/**
 * Created by Administrator on 2018/3/19.
 */

public class MyService extends Service {
    private static final String CHANNEL_ID = "11111";
    private static final String CHANNEL_NAME = "ForegroundServiceChannel";

    @RequiresApi(api = 26)
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
                Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID).build();
                startForeground(1, notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String appid = getPackageName();
        //如果既不是智子盒子也不是探客宝就不调用此接口
        if (appid.equals("com.zhiziyun.dmptest.bot")) {
            closeApp("B1tyi0cgW5W");
        } else if (appid.equals("com.zhiziyun.dmptest.tkb")) {
            closeApp("B1txn0rBNhm");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void closeApp(final String appid) {
        //记录用户退出
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteid", "0zoTLi29XRgq");
                    json.put("zzid", "0zoTLha93ySI");//广告活动编号
                    json.put("appid", appid);//应用编号
                    TelephonyManager telephonyManager = (TelephonyManager) HomePageActivity.activity.getSystemService(HomePageActivity.activity.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
                    json.put("imei", imei);
                    json.put("who", "");//注册用户编号
                    json.put("idfa", "");
                    json.put("idfy", "");
                    json.put("channelid", "");//渠道id
                    json.put("os", 0);//系统安桌是0
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
                            .url(BaseUrl.BaseJiang + "out")
                            .addHeader("apiid", "0zoTLi29XRgq")
                            .addHeader("token", URLEncoder.encode(Token.gettoken2(), "utf-8"))
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.get("status").toString().equals("0")) {
                                    Log.i("response", "退出成功");
                                    handler.sendEmptyMessage(1);
                                }
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
                    stopService(new Intent(HomePageActivity.activity, MyService.class));
                    break;
            }
        }
    };
}
