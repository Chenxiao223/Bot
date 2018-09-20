package com.zhiziyun.dmptest.bot.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.AddCorwdActivity;
import com.zhiziyun.dmptest.bot.ui.activity.AdvertisingActivity;
import com.zhiziyun.dmptest.bot.ui.fragment.AdvertisingFragment;

/**
 * Created by Administrator on 2018/5/17 0019.
 */
public class PopWin_advertising extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View view;
    private int position;

    public PopWin_advertising(final Context mContext, View.OnClickListener itemsOnClick, int position) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_advertising, null);
        this.position = position;
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
        view.findViewById(R.id.iv_close).setOnClickListener(this);
        view.findViewById(R.id.line_alter).setOnClickListener(this);
        view.findViewById(R.id.line_examine).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.line_alter:
                AdvertisingFragment.fragment.alter(position);
                dismiss();
                break;
            case R.id.line_examine:
                AdvertisingFragment.fragment.examine(position);
                dismiss();
                break;
        }
    }

}
