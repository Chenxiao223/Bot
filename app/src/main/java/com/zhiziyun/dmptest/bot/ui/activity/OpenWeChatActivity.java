package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

/**
 * Created by Administrator on 2018/8/3.
 * 显示web网页
 */

public class OpenWeChatActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences share;
    private TextView tv_title;
    private WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_wechat);
        initView();
    }

    private void initView() {
        try {
            share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
            //设置系统栏颜色
            ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
            params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

            tv_title = findViewById(R.id.tv_title);
            webview = findViewById(R.id.webview);
            findViewById(R.id.tv_back).setOnClickListener(this);
            if (getIntent().getIntExtra("flag", 0) == 0) {
                tv_title.setText("申请微信公众号");
                webview.loadUrl("http://static.ak39.com/wx/wx.html");
            } else {
                tv_title.setText("绑定微信公众号");
                webview.loadUrl("http://mc.zhiziyun.com/tencent/tencentOAuth/mobileWechatAuthorizationPage.action?tencentId=100001235&otherRedirect=MOBILE");
            }
            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);//允许使用js
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
            //让网页居中显示
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            //支持屏幕缩放
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            //不显示webview缩放按钮
            webSettings.setDisplayZoomControls(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
