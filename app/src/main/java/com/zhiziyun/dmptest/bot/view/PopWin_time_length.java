package com.zhiziyun.dmptest.bot.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.AddCorwdActivity;
import com.zhiziyun.dmptest.bot.ui.activity.AddSmsActivity;

/**
 * Created by Administrator on 2018/5/7 0019.
 */
public class PopWin_time_length extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View view;
    private TextView tv_a, tv_b, tv_c, tv_d, tv_e, tv_f, tv_g;


    public PopWin_time_length(final Context mContext, View.OnClickListener itemsOnClick, int flag) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_time_length, null);
        initView();
        // 设置外部可点击
        this.setOutsideTouchable(true);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);//高
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);//宽

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);
//        mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }

        });

    }

    public void initView() {
        tv_a = (TextView) view.findViewById(R.id.tv_a);
        tv_b = (TextView) view.findViewById(R.id.tv_b);
        tv_c = (TextView) view.findViewById(R.id.tv_c);
        tv_d = (TextView) view.findViewById(R.id.tv_d);
        tv_e = (TextView) view.findViewById(R.id.tv_e);
        tv_f = (TextView) view.findViewById(R.id.tv_f);
        tv_g = view.findViewById(R.id.tv_g);
        tv_a.setOnClickListener(this);
        tv_b.setOnClickListener(this);
        tv_c.setOnClickListener(this);
        tv_d.setOnClickListener(this);
        tv_e.setOnClickListener(this);
        tv_f.setOnClickListener(this);
        tv_g.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_a://0-10分钟
                AddCorwdActivity.addCorwdActivity.tv_length.setText("0-10分钟");
                AddCorwdActivity.addCorwdActivity.tv_length.setTextColor(mContext.getResources().getColor(R.color.defaultcolor));
                AddCorwdActivity.addCorwdActivity.stayDuration = 0;
                dismiss();
                break;
            case R.id.tv_b://10-30分钟
                AddCorwdActivity.addCorwdActivity.tv_length.setText("10-30分钟");
                AddCorwdActivity.addCorwdActivity.tv_length.setTextColor(mContext.getResources().getColor(R.color.defaultcolor));
                AddCorwdActivity.addCorwdActivity.stayDuration = 1;
                dismiss();
                break;
            case R.id.tv_c://30-60分钟
                AddCorwdActivity.addCorwdActivity.tv_length.setText("30-60分钟");
                AddCorwdActivity.addCorwdActivity.tv_length.setTextColor(mContext.getResources().getColor(R.color.defaultcolor));
                AddCorwdActivity.addCorwdActivity.stayDuration = 2;
                dismiss();
                break;
            case R.id.tv_d://60-120分钟
                AddCorwdActivity.addCorwdActivity.tv_length.setText("60-120分钟");
                AddCorwdActivity.addCorwdActivity.tv_length.setTextColor(mContext.getResources().getColor(R.color.defaultcolor));
                AddCorwdActivity.addCorwdActivity.stayDuration = 3;
                dismiss();
                break;
            case R.id.tv_e://120-180分钟
                AddCorwdActivity.addCorwdActivity.tv_length.setText("120-180分钟");
                AddCorwdActivity.addCorwdActivity.tv_length.setTextColor(mContext.getResources().getColor(R.color.defaultcolor));
                AddCorwdActivity.addCorwdActivity.stayDuration = 4;
                dismiss();
                break;
            case R.id.tv_f://180分钟以上
                AddCorwdActivity.addCorwdActivity.tv_length.setText("180分钟以上");
                AddCorwdActivity.addCorwdActivity.tv_length.setTextColor(mContext.getResources().getColor(R.color.defaultcolor));
                AddCorwdActivity.addCorwdActivity.stayDuration = 5;
                dismiss();
                break;
            case R.id.tv_g://不限
                AddCorwdActivity.addCorwdActivity.tv_brand.setText("不限");
                AddCorwdActivity.addCorwdActivity.tv_length.setText("不限");
                AddCorwdActivity.addCorwdActivity.tv_length.setTextColor(mContext.getResources().getColor(R.color.defaultcolor));
                AddCorwdActivity.addCorwdActivity.stayDuration = 6;
                dismiss();
                break;
        }
    }

}
