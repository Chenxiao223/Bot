package com.zhiziyun.dmptest.bot.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.support.design.widget.TabLayout;
import android.widget.LinearLayout;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.fragment.TimeSlotFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.TrendFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.VisitorsViewFragment;

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
        ImageView iv_system= (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height=(int) getStatusBarHeight(this);//设置当前控件布局的高度

        iv_back= (ImageView) findViewById(R.id.iv_back);
        viewPager= (ViewPager) findViewById(R.id.vp_view);
        tabLayout= (TabLayout) findViewById(R.id.tabs);
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
        switch (view.getId()){
            case R.id.iv_back:
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
}
