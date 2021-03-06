package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.commonsdk.UMConfigure;
import com.zhiziyun.dmptest.bot.util.StorageMgr;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        //window.setStatusBarColor(Color.BLACK);
        //友盟日志
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(true);
        StorageMgr.init(BaseActivity.this);
    }

    //获取系统栏高度
    public static double getStatusBarHeight(Context context) {
        double statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
        return statusBarHeight;
    }
}
