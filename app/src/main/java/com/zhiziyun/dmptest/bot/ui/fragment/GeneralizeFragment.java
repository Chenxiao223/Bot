package com.zhiziyun.dmptest.bot.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiziyun.dmptest.bot.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 推广
 */
public class GeneralizeFragment extends Fragment {

    private List<Fragment> list;
    private String[] titles = {"投广告", "发短信"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_generalize, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        initView();
    }

    public void initView() {
        TabLayout tabLayout;
        ViewPager viewPager;
        MyAdapter adapter;
        viewPager = getView().findViewById(R.id.vp_view);
        tabLayout = getView().findViewById(R.id.tabs);

        //页面，数据源
        list = new ArrayList<>();
        list.add(new AdvertisingFragment());
        list.add(new SMSFragment());
        //ViewPager的适配器
        adapter = new MyAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    private class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public MyAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
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
