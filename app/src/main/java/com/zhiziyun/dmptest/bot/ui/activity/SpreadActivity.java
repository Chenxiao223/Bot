package com.zhiziyun.dmptest.bot.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.fragment.HaveSpreadFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.MadeSpreadFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传素材
 */

public class SpreadActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mSpreadTable;
    private ViewPager mSpreadVp;
    private List<Fragment> mFragmentList;
    Adapter mAdapter;
    private String[] titles = {"制作推广页", "已有推广页"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spread);
        mSpreadTable = findViewById(R.id.spread_table);
        mSpreadVp = findViewById(R.id.spread_vp);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new MadeSpreadFragment());
        mFragmentList.add(new HaveSpreadFragment());
        mAdapter = new Adapter(getSupportFragmentManager());
        mSpreadVp.setAdapter(mAdapter);
        mSpreadTable.setupWithViewPager(mSpreadVp);
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    class Adapter extends FragmentPagerAdapter {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
