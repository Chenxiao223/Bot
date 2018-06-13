package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/30.
 * 短信活动详情
 */

public class SmsDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_name, tv_customer_source, tv_smstype, tv_sms, tv_type, tv_mark, tv_test_phone;
    private Intent intent;
    private ArrayList<Integer> list_type;
    private ArrayList<Integer> list_mark;
    private ArrayList<String> list_testphone;
    private RelativeLayout rl_type, rl_mark;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_details);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        try {
            findViewById(R.id.iv_back).setOnClickListener(this);
            intent = getIntent();
            rl_type = findViewById(R.id.rl_type);
            rl_type.setOnClickListener(this);
            rl_mark = findViewById(R.id.rl_mark);
            rl_mark.setOnClickListener(this);
            list_type = intent.getIntegerArrayListExtra("type");
            list_mark = intent.getIntegerArrayListExtra("mark");
            list_testphone = intent.getStringArrayListExtra("testphone");
            tv_name = findViewById(R.id.tv_name);
            tv_name.setText(intent.getStringExtra("name"));
            tv_customer_source = findViewById(R.id.tv_customer_source);
            tv_customer_source.setText(intent.getStringExtra("customersource"));
            tv_smstype = findViewById(R.id.tv_smstype);
            try {
                if (intent.getIntExtra("smstype", 2) == 0) {
                    tv_smstype.setText("短信");
                } else if (intent.getIntExtra("smstype", 2) == 1) {
                    tv_smstype.setText("闪信");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            tv_sms = findViewById(R.id.tv_sms);
            tv_sms.setText(intent.getStringExtra("sms"));
            try {
                tv_type = findViewById(R.id.tv_type);
                tv_type.setText(list_type.size() + "个");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                tv_mark = findViewById(R.id.tv_mark);
                tv_mark.setText(list_mark.size() + "个");
            } catch (Exception e) {
                e.printStackTrace();
            }
            tv_test_phone = findViewById(R.id.tv_test_phone);
            tv_test_phone.setText(list_testphone.toString() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_type:
                try {
                    if (list_type.size() != 0) {
                        Intent it_type = new Intent(SmsDetailsActivity.this, SmsChooseActivity.class);
                        it_type.putExtra("title", "客户类型");
                        it_type.putExtra("flag", 1);//区分类型和状态
                        it_type.putExtra("from", "sms");
                        it_type.putIntegerArrayListExtra("list", list_type);
                        startActivityForResult(it_type, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_mark:
                try {
                    if (list_mark.size() != 0) {
                        Intent it_mark = new Intent(SmsDetailsActivity.this, SmsChooseActivity.class);
                        it_mark.putExtra("title", "跟进状态");
                        it_mark.putExtra("flag", 2);
                        it_mark.putExtra("from", "sms");
                        it_mark.putIntegerArrayListExtra("list", list_mark);
                        startActivityForResult(it_mark, 2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
