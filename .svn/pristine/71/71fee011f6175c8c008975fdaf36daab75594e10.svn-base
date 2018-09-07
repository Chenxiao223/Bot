package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

/**
 * Created by Administrator on 2018/7/18.
 * 微信绑定页面
 */

public class WeChatBindActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_bind);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = this.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.iv_not_bind).setOnClickListener(this);
        findViewById(R.id.iv_bind).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bind:
                Intent intent = new Intent(this, WebShowActivity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
                break;
            case R.id.tv_back:
                if (share.getBoolean("isBindingWeChatSubscription", false) && share.getBoolean("isAuthorizationAd", false)
                        && share.getBoolean("isOpenWeChatSubscriptionAdvertiser", false)) {//三个条件必须同时满足
                    finish();
                } else {
                    ToastUtils.showShort(this, "请绑定微信公众号");
                }
                break;
            case R.id.iv_not_bind:
                startActivity(new Intent(this, NotBindWeChatActivity.class));
                break;
        }
    }
}
