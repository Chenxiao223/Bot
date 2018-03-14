package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

/**
 * Created by Administrator on 2018/3/14.
 * 账户
 */

public class AccountActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_balance;
    private Button btn_commit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        tv_balance = (TextView) findViewById(R.id.tv_balance);
        btn_commit = (Button) findViewById(R.id.btn_commit);
        try {
            String balance = getIntent().getStringExtra("detail");
            tv_balance.setText(balance.replaceAll("余额：", "￥"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_commit.setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_detail).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_detail:
                startActivity(new Intent(AccountActivity.this, TransactionDetailsActivity.class));
                break;
            case R.id.btn_commit:
                Intent intent = new Intent(AccountActivity.this, TopupCenterActivity.class);
                intent.putExtra("amount", tv_balance.getText().toString().replaceAll("￥", ""));
                startActivity(intent);
                break;
        }
    }
}
