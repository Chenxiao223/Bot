package com.zhiziyun.dmptest.bot.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.SubmitFriendActivitty;
import com.zhiziyun.dmptest.bot.ui.fragment.AccountFragment;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public class PopWin_album extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View view;
    private TextView tv_cancel, tv_photo_album;
    private int flags = 0;


    public PopWin_album(final Context mContext, View.OnClickListener itemsOnClick, int flag) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_album, null);
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

    public void setFlags(int num) {
        flags = num;
    }

    public void initView() {
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_photo_album = (TextView) view.findViewById(R.id.tv_photo_album);
        tv_cancel.setOnClickListener(this);
        tv_photo_album.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel://取消
                dismiss();
                break;
            case R.id.tv_photo_album://相册
                SubmitFriendActivitty.submitFriendActivitty.photoAlbum();
                dismiss();
                break;
        }
    }

}
