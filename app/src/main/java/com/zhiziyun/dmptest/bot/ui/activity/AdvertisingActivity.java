package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.view.PopWin_AD_type;
import com.zhiziyun.dmptest.bot.view.PopWin_Bit_Way;
import com.zhiziyun.dmptest.bot.view.PopWin_Crowd;

import java.text.SimpleDateFormat;
import java.util.Date;

import lib.homhomlib.design.SlidingLayout;

/**
 * Created by Administrator on 2017/12/28.
 * 广告页面
 */

public class AdvertisingActivity extends BaseActivity implements View.OnClickListener {
    public static AdvertisingActivity advertisingActivity;
    public TextView tv_AD_types, tv_beginTime, tv_endTime, tv_time_slot, tv_Bid_way, tv_crowd, tv_consumption, tv_advertising;
    private SlidingLayout slidingLayout;
    protected EditText edit_planname, edit_budget, edit_overall_budget, edit_offer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);
        initVeiw();
    }

    private void initVeiw() {
        advertisingActivity = this;
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        edit_planname = (EditText) findViewById(R.id.edit_planname);
        edit_budget = (EditText) findViewById(R.id.edit_budget);
        edit_overall_budget = (EditText) findViewById(R.id.edit_overall_budget);
        edit_offer = (EditText) findViewById(R.id.edit_offer);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.rl_AD_types).setOnClickListener(this);
        findViewById(R.id.rl_beginTime).setOnClickListener(this);
        findViewById(R.id.rl_endTime).setOnClickListener(this);
        findViewById(R.id.rl_time_slot).setOnClickListener(this);
        findViewById(R.id.rl_Bid_way).setOnClickListener(this);
        findViewById(R.id.rl_crowd).setOnClickListener(this);
        findViewById(R.id.rl_consumption).setOnClickListener(this);
        findViewById(R.id.rl_advertising).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);

        tv_AD_types = (TextView) findViewById(R.id.tv_AD_types);
        tv_beginTime = (TextView) findViewById(R.id.tv_beginTime);
        tv_endTime = (TextView) findViewById(R.id.tv_endTime);
        tv_time_slot = (TextView) findViewById(R.id.tv_time_slot);
        tv_Bid_way = (TextView) findViewById(R.id.tv_Bid_way);
        tv_crowd = (TextView) findViewById(R.id.tv_crowd);
        tv_consumption = (TextView) findViewById(R.id.tv_consumption);
        tv_advertising = (TextView) findViewById(R.id.tv_advertising);

        //果冻弹跳效果
        slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
        slidingLayout.setSlidingOffset(0.2f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_AD_types:
                PopWin_AD_type popWin_ad_type = new PopWin_AD_type(this, null, 0);
                popWin_ad_type.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                break;
            case R.id.rl_beginTime:
                //显示日期选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_beginTime.setText(getTime(date));
                        tv_beginTime.setTextColor(AdvertisingActivity.this.getResources().getColor(R.color.defaultcolor));
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();

                pvTime.show();
                break;
            case R.id.rl_endTime:
                //显示日期选择器
                TimePickerView pvTime2 = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_endTime.setText(getTime(date));
                        tv_endTime.setTextColor(AdvertisingActivity.this.getResources().getColor(R.color.defaultcolor));
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();

                pvTime2.show();
                break;
            case R.id.rl_time_slot:
                break;
            case R.id.rl_Bid_way:
                PopWin_Bit_Way popWin_bit_way = new PopWin_Bit_Way(this, null, 0);
                popWin_bit_way.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                break;
            case R.id.rl_crowd:
                PopWin_Crowd popWin_crowd = new PopWin_Crowd(this, null, 0);
                popWin_crowd.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                break;
            case R.id.rl_consumption:
                startActivity(new Intent(AdvertisingActivity.this, CrowdActivity.class));
                break;
            case R.id.rl_advertising:
                break;
            case R.id.btn_commit:
                if (!TextUtils.isEmpty(edit_planname.getText().toString()) && !tv_beginTime.getText().equals("请选择") &&
                        !tv_endTime.getText().equals("请选择") && !TextUtils.isEmpty(edit_budget.getText().toString()) &&
                        !TextUtils.isEmpty(edit_offer.getText().toString())) {
                    ToastUtils.showShort(this, "提交");
                } else {
                    ToastUtils.showShort(this, "请将带星号的数据填写完整");
                }
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
