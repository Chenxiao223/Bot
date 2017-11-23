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
 * Created by Administrator on 2017/7/17 0017.
 * 访客
 */
public class VisitorsFragment extends Fragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list;
    private MyAdapter adapter;
    private String[] titles = {"访客", "画像"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visitors, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        initView();
    }

    public void initView() {
        viewPager= getView().findViewById(R.id.vp_view);
        tabLayout= getView().findViewById(R.id.tabs);

        //页面，数据源
        list = new ArrayList<>();
        list.add(new VisitorsselfFragment());
        list.add(new VisitorsViewFragment());
        //ViewPager的适配器
        adapter = new MyAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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
