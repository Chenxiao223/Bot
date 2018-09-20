package com.zhiziyun.dmptest.bot.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.fragment.SMSFragment;

/**
 * Created by Administrator on 2018/5/17 0019.
 */
public class PopWin_sms extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View view;
    private int position;
    private String activityid, type;

    public PopWin_sms(final Context mContext, String activityid, String type, int position) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_sms, null);
        this.position = position;
        this.activityid = activityid;
        this.type = type;
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
        view.findViewById(R.id.line_detail).setOnClickListener(this);
        view.findViewById(R.id.line_examine).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.line_detail:
                SMSFragment.smsFragment.detail(activityid, type);
                dismiss();
                break;
            case R.id.line_examine:
                SMSFragment.smsFragment.examine(position);
                dismiss();
                dismiss();
                break;
        }
    }

}
