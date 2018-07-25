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
import com.zhiziyun.dmptest.bot.ui.activity.EditSmsActivity;

/**
 * Created by Administrator on 2017/12/19 0019.
 */
public class PopWin_sms_type extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View view;
    private TextView tv_duanxin, tv_shanxin;
    private int flag = 0;


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

    public void setFlag(int flag) {
        this.flag = flag;
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
                if (flag == 1) {
                    AddSmsActivity.addSmsActivity.tv_smstype.setText("短信");
                    AddSmsActivity.addSmsActivity.tv_smstype.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    AddSmsActivity.addSmsActivity.tv_sms.setText("请选择");
                } else if (flag == 2) {
                    EditSmsActivity.editSmsActivity.tv_type.setText("短信");
                    EditSmsActivity.editSmsActivity.tv_type.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    EditSmsActivity.editSmsActivity.getSmsCategory();
                    EditSmsActivity.editSmsActivity.tv_industry.setText("一级行业");
                    EditSmsActivity.editSmsActivity.tv_industry_type.setText("二级行业");
                }
                dismiss();
                break;
            case R.id.tv_shanxin://闪信
                if (flag == 1) {
                    AddSmsActivity.addSmsActivity.tv_smstype.setText("闪信");
                    AddSmsActivity.addSmsActivity.tv_smstype.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    AddSmsActivity.addSmsActivity.tv_sms.setText("请选择");
                } else if (flag == 2) {
                    EditSmsActivity.editSmsActivity.tv_type.setText("闪信");
                    EditSmsActivity.editSmsActivity.tv_type.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                    EditSmsActivity.editSmsActivity.getSmsCategory();
                    EditSmsActivity.editSmsActivity.tv_industry.setText("一级行业");
                    EditSmsActivity.editSmsActivity.tv_industry_type.setText("二级行业");
                }
                dismiss();
                break;
        }
    }

}
