package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.fragment.GeneralizeFragment;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/14.
 * 发送计划页面
 */

public class SendPlanActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name;
    private RadioButton rb_1;
    private CheckBox cb_1, cb_2, cb_3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_plan);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        findViewById(R.id.linearLayout).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        rb_1 = (RadioButton) findViewById(R.id.rb_1);
        cb_1 = (CheckBox) findViewById(R.id.cb_1);
        cb_2 = (CheckBox) findViewById(R.id.cb_2);
        cb_3 = (CheckBox) findViewById(R.id.cb_3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.linearLayout:
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                break;
        }
    }

    public void commit(View view) {
        if (!TextUtils.isEmpty(et_name.getText().toString())) {
            if (rb_1.isChecked()) {
                if (cb_1.isChecked() || cb_2.isChecked() || cb_3.isChecked()) {
                    setData();
                    GeneralizeFragment.generalizeFragment.sendPlanAdapter.notifyDataSetChanged();
                    toFinish();
                    finish();
                } else {
                    ToastUtils.showShort(this, "请选择发送对象");
                }
            } else {
                ToastUtils.showShort(this, "请选择短信");
            }
        } else {
            ToastUtils.showShort(this, "请填写名称");
        }
    }

    public void setData() {
        GeneralizeFragment.generalizeFragment.hm_sms = new HashMap<>();
        GeneralizeFragment.generalizeFragment.hm_sms.put("content1", et_name.getText().toString());
        GeneralizeFragment.generalizeFragment.hm_sms.put("content2", "发送中");
        GeneralizeFragment.generalizeFragment.hm_sms.put("content3", getData());
        GeneralizeFragment.generalizeFragment.hm_sms.put("content4", "0");
        GeneralizeFragment.generalizeFragment.list_sms.add(GeneralizeFragment.generalizeFragment.hm_sms);
    }

    public String getData() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sDateFormat.format(new java.util.Date());
    }

    //清空内存
    private void toFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    et_name = null;
                    rb_1 = null;
                    cb_1 = null;
                    cb_2 = null;
                    cb_3 = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
