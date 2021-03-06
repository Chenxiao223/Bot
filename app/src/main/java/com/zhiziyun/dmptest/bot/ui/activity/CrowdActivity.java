package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.VisitorsAdapter;
import com.zhiziyun.dmptest.bot.view.PopWin_AddCrowd_type;

/**
 * Created by Administrator on 2018/8/31.
 */

public class CrowdActivity extends BaseActivity implements View.OnClickListener {
    public static CrowdActivity crowdActivity;
    private TextView tv_crowd, tv_wifi_crowd, tv_click_crowd;
    private VisitorsAdapter adapter = null;
    public ViewPager pager = null;
    private String activityName = "ShopCrowdFragment";
    private View view_crowd, view_wifi_crowd, view_click_crowd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        crowdActivity = this;
        tv_crowd = findViewById(R.id.tv_crowd);
        tv_wifi_crowd = findViewById(R.id.tv_wifi_crowd);
        tv_click_crowd = findViewById(R.id.tv_click_crowd);
        view_crowd = findViewById(R.id.view_crowd);
        view_wifi_crowd = findViewById(R.id.view_wifi_crowd);
        view_click_crowd = findViewById(R.id.view_click_crowd);

        tv_crowd.setOnClickListener(this);
        tv_wifi_crowd.setOnClickListener(this);
        tv_click_crowd.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_add).setOnClickListener(this);
        findViewById(R.id.iv_search).setOnClickListener(this);

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);//
        adapter = new VisitorsAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    changeColor(true, false, false);
                } else if (position == 1) {
                    changeColor(false, true, false);
                } else {
                    changeColor(false, false, true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //一进来就显示首页的fragment
        pager.setCurrentItem(0);
        changeColor(true, false, false);
    }

    public void select(int n) {
        switch (n) {
            case 1:
                view_crowd.setVisibility(View.VISIBLE);
                view_wifi_crowd.setVisibility(View.INVISIBLE);
                view_click_crowd.setVisibility(View.INVISIBLE);
                break;
            case 2:
                view_crowd.setVisibility(View.INVISIBLE);
                view_wifi_crowd.setVisibility(View.VISIBLE);
                view_click_crowd.setVisibility(View.INVISIBLE);
                break;
            case 3:
                view_crowd.setVisibility(View.INVISIBLE);
                view_wifi_crowd.setVisibility(View.INVISIBLE);
                view_click_crowd.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void changeColor(boolean crowd, boolean wificrowd, boolean clickcrowd) {
        if (crowd) {
            tv_crowd.setTextColor(this.getResources().getColor(R.color.white));
        } else {
            tv_crowd.setTextColor(this.getResources().getColor(R.color.grey));
        }

        if (wificrowd) {
            tv_wifi_crowd.setTextColor(this.getResources().getColor(R.color.white));
        } else {
            tv_wifi_crowd.setTextColor(this.getResources().getColor(R.color.grey));
        }

        if (clickcrowd) {
            tv_click_crowd.setTextColor(this.getResources().getColor(R.color.white));
        } else {
            tv_click_crowd.setTextColor(this.getResources().getColor(R.color.grey));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                //让背景变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                getWindow().setAttributes(lp);
                //弹出窗体
                PopWin_AddCrowd_type popWin_crowd_type = new PopWin_AddCrowd_type(this, null, 0);
                popWin_crowd_type.showAtLocation(findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                //监听popwin是否关闭，关闭的话让背景恢复
                popWin_crowd_type.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                Intent it = new Intent(CrowdActivity.this, SearchPageActivity.class);
                it.putExtra("activity", activityName);
                startActivity(it);
                break;
            case R.id.tv_crowd:
                select(1);
                activityName = "ShopCrowdFragment";
                pager.setCurrentItem(0);
                changeColor(true, false, false);
                break;
            case R.id.tv_wifi_crowd:
                select(2);
                activityName = "WifiCrowdFragment";
                pager.setCurrentItem(1);
                changeColor(false, true, false);
                break;
            case R.id.tv_click_crowd:
                select(3);
                activityName = "ClickCrowdFragment";
                pager.setCurrentItem(2);
                changeColor(false, false, true);
                break;
        }
    }
}
