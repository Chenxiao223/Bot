package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.fragment.TimeSlotFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.TrendFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 * 客流分析
 */

public class PassengerFlowAnalysisActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list;
    private MyAdapter adapter;
    private String[] titles = {"时段", "趋势"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_flow_analysis);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        iv_back = (ImageView) findViewById(R.id.iv_back);
        viewPager = (ViewPager) findViewById(R.id.vp_view);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 40, 40);
            }
        });
        iv_back.setOnClickListener(this);

        //页面，数据源
        list = new ArrayList<>();
        list.add(new TimeSlotFragment());
        list.add(new TrendFragment());
        //ViewPager的适配器
        adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public void onBackPressed() {
        toFinish();
        finish();
    }

    //设置TabLayout下划线的宽度
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        try {
            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //清空内存
    private void toFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeSlotFragment.fragment.clearmemory();
                    TrendFragment.fragment.clearmemory();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
