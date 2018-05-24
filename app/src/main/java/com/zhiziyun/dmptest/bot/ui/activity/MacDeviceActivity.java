package com.zhiziyun.dmptest.bot.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.view.PopWin_Mac;

/**
 * Created by Administrator on 2018/5/17.
 */

public class MacDeviceActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac_device);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        findViewById(R.id.tv_more).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more:
                //让背景变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                getWindow().setAttributes(lp);
                //弹出窗体
                PopWin_Mac popWin_Mck = new PopWin_Mac(MacDeviceActivity.this, null, 0);
                popWin_Mck.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                //监听popwin是否关闭，关闭的话让背景恢复
                popWin_Mck.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
