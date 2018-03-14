package com.zhiziyun.dmptest.bot.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

/**
 * Created by Administrator on 2018/3/14.
 */

public class TopupStateActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_state, tv_topup_state, tv_balance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_state);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_topup_state = (TextView) findViewById(R.id.tv_topup_state);
        tv_balance = (TextView) findViewById(R.id.tv_balance);

        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                finish();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
