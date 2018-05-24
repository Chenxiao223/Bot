package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.SearchPageActivity;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.view.PopWin_Crowd_type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 访客
 */
public class VisitorsFragment extends Fragment implements View.OnClickListener {
    private List<Fragment> list;
    private String[] titles = {"访客", "画像", "人群"};
    private ImageView iv_more, iv_search;

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
        final TabLayout tabLayout;
        ViewPager viewPager;
        MyAdapter adapter;
        iv_more = getView().findViewById(R.id.iv_more);
        iv_search = getView().findViewById(R.id.iv_search);
        viewPager = getView().findViewById(R.id.vp_view);
        viewPager.setOffscreenPageLimit(3);//
        tabLayout = getView().findViewById(R.id.tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 30, 30);
            }
        });

        iv_more.setOnClickListener(this);
        iv_search.setOnClickListener(this);

        //页面，数据源
        list = new ArrayList<>();
        list.add(new VisitorsselfFragment());
        list.add(new VisitorsViewFragment());
        list.add(new CrowdFragment());
        //ViewPager的适配器
        adapter = new MyAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String str = (String) tab.getText();
                if (str.equals("访客")) {
                    iv_search.setVisibility(View.VISIBLE);
                    iv_more.setVisibility(View.GONE);
                } else if (str.equals("人群")) {
                    iv_more.setVisibility(View.VISIBLE);
                    iv_search.setVisibility(View.GONE);
                } else if (str.equals("画像")) {
                    iv_more.setVisibility(View.GONE);
                    iv_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    //当滑到当前碎片时调用该方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (ClickUtils.isFastClick()) {//防止过快点击而闪退
                VisitorsselfFragment.fragment.requestNT();
                VisitorsViewFragment.visitorsViewFragment.requestNT();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
                //让背景变暗
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.7f;
                getActivity().getWindow().setAttributes(lp);
                getActivity().getWindow().setAttributes(lp);
                //弹出窗体
                PopWin_Crowd_type popWin_crowd_type = new PopWin_Crowd_type(getActivity(), null, 0);
                popWin_crowd_type.showAtLocation(getView().findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                //监听popwin是否关闭，关闭的话让背景恢复
                popWin_crowd_type.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1f;
                        getActivity().getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.iv_search:
                Intent intent = new Intent(getActivity(), SearchPageActivity.class);
                intent.putExtra("activity", "VisitorsselfFragment");
                startActivity(intent);
                break;
        }
    }

    private class MyAdapter extends FragmentPagerAdapter {

        private MyAdapter(FragmentManager fm) {
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
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
