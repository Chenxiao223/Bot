package com.zhiziyun.dmptest.bot.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.AddCircleFriendsActivity;

/**
 * Created by Administrator on 2018/5/17 0019.
 */
public class PopWin_choose_city extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View view;
    private TextView tv_a, tv_b, tv_c;

    public PopWin_choose_city(final Context mContext, View.OnClickListener itemsOnClick, int flag) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_choose_city, null);
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
        tv_a.setOnClickListener(this);
        tv_b.setOnClickListener(this);
        tv_c.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_a://核心城市
                AddCircleFriendsActivity.addCircleFriendsActivity.jump("CITY_LEVEL_CORE");
                AddCircleFriendsActivity.addCircleFriendsActivity.tv_city.setText("核心城市");
                AddCircleFriendsActivity.addCircleFriendsActivity.tv_scope.setText("可出价范围：100-300元");
                dismiss();
                break;
            case R.id.tv_b://重点城市
                AddCircleFriendsActivity.addCircleFriendsActivity.jump("CITY_LEVEL_IMPORTANT");
                AddCircleFriendsActivity.addCircleFriendsActivity.tv_city.setText("重点城市");
                AddCircleFriendsActivity.addCircleFriendsActivity.tv_scope.setText("可出价范围：60-200元");
                dismiss();
                break;
            case R.id.tv_c://其他城市
                AddCircleFriendsActivity.addCircleFriendsActivity.jump("CITY_LEVEL_OTHER");
                AddCircleFriendsActivity.addCircleFriendsActivity.tv_city.setText("其他城市");
                AddCircleFriendsActivity.addCircleFriendsActivity.tv_scope.setText("可出价范围：30-200元");
                dismiss();
                break;
        }
    }

}
