package com.zhiziyun.dmptest.bot.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.AddAdvertisingCrowd;
import com.zhiziyun.dmptest.bot.ui.activity.AddCorwdActivity;
import com.zhiziyun.dmptest.bot.ui.activity.AddWifiCorwdActivity;
import com.zhiziyun.dmptest.bot.ui.activity.CrowdActivity;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

/**
 * Created by Administrator on 2018/5/7 0019.
 */
public class PopWin_AddCrowd_type extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View view;
    private TextView tv_a, tv_b, tv_c;


    public PopWin_AddCrowd_type(final Context mContext, View.OnClickListener itemsOnClick, int flag) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_addcrowd_type, null);
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
            case R.id.tv_a://添加到店人群
                CrowdActivity.crowdActivity.startActivity(new Intent(mContext, AddCorwdActivity.class));
                dismiss();
                break;
            case R.id.tv_b://添加广告点击人群
                CrowdActivity.crowdActivity.startActivity(new Intent(mContext, AddAdvertisingCrowd.class));
                dismiss();
                break;
            case R.id.tv_c://添加wifi人群
                if (!isWifiConnected(mContext)) {
                    ToastUtils.showShort(mContext, "请连接wifi");
                } else {
                    CrowdActivity.crowdActivity.startActivity(new Intent(mContext, AddWifiCorwdActivity.class));
                }
                dismiss();
                break;
        }
    }

    //是否连接WIFI
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }


}
