package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.zhiziyun.dmptest.bot.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/7.
 * 添加广告人群
 */

public class AddAdvertisingCrowd extends BaseActivity implements View.OnClickListener {
    private TextView tv_beginTime, tv_endTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising_crowd);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        tv_beginTime = (TextView) findViewById(R.id.tv_beginTime);
        tv_endTime = (TextView) findViewById(R.id.tv_endTime);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.rl_beginTime).setOnClickListener(this);
        findViewById(R.id.rl_endTime).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.rl_beginTime:
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //显示日期选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_beginTime.setText(getTime(date));
                        tv_beginTime.setTextColor(AddAdvertisingCrowd.this.getResources().getColor(R.color.defaultcolor));
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, true})// 对应年月日时分秒
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .build();

                pvTime.show();
                break;
            case R.id.rl_endTime:
                //隐藏软键盘
                InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //显示日期选择器
                TimePickerView pvTime2 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_endTime.setText(getTime(date));
                        tv_endTime.setTextColor(AddAdvertisingCrowd.this.getResources().getColor(R.color.defaultcolor));
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .build();

                pvTime2.show();
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @Override
    public void onBackPressed() {
        toFinish();
        finish();
    }

    //清空内存
    private void toFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    tv_beginTime = null;
                    tv_endTime = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
