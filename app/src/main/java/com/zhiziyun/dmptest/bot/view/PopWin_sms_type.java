package com.zhiziyun.dmptest.bot.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.AddSmsActivity;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public class PopWin_sms_type extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View view;
    private TextView tv_duanxin, tv_shanxin;


    public PopWin_sms_type(final Context mContext, View.OnClickListener itemsOnClick, int flag) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_sms_type, null);
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
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        // 设置弹出窗体的背景
//        this.setBackgroundDrawable(dw);

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
        tv_duanxin = (TextView) view.findViewById(R.id.tv_duanxin);
        tv_shanxin = (TextView) view.findViewById(R.id.tv_shanxin);
        tv_duanxin.setOnClickListener(this);
        tv_shanxin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_duanxin://短信
                AddSmsActivity.addSmsActivity.tv_smstype.setText("短信");
                AddSmsActivity.addSmsActivity.tv_smstype.setTextColor(mContext.getResources().getColor(R.color.defaultcolor));
                dismiss();
                break;
            case R.id.tv_shanxin://闪信
                AddSmsActivity.addSmsActivity.tv_smstype.setText("闪信");
                AddSmsActivity.addSmsActivity.tv_smstype.setTextColor(mContext.getResources().getColor(R.color.defaultcolor));
                dismiss();
                break;
        }
    }

}
