package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhiziyun.dmptest.bot.R;

/**
 * Created by Administrator on 2017/12/29.
 * 人群
 */

public class CrowdActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_add_crowd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_add_crowd:
                startActivity(new Intent(CrowdActivity.this,AddCorwdActivity.class));
                break;

        }
    }
}
