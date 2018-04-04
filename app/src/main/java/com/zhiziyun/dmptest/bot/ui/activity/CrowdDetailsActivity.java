package com.zhiziyun.dmptest.bot.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

/**
 * Created by Administrator on 2018/4/4.
 * 人群详情
 */

public class CrowdDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_crowd, tv_date, tv_budget, tv_equipment, tv_cost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd_details);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        tv_crowd = (TextView) findViewById(R.id.tv_crowd);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_budget = (TextView) findViewById(R.id.tv_budget);
        tv_equipment = (TextView) findViewById(R.id.tv_equipment);
        tv_cost = (TextView) findViewById(R.id.tv_cost);

        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
