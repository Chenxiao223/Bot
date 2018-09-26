package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.zhiziyun.dmptest.bot.mode.wifi.WifiAdverActivity;
import com.zhiziyun.dmptest.bot.ui.activity.AddFriendsClubActivity;
import com.zhiziyun.dmptest.bot.ui.activity.AddSmsActivity;
import com.zhiziyun.dmptest.bot.ui.activity.AdvertisingActivity;
import com.zhiziyun.dmptest.bot.ui.activity.SearchPageActivity;
import com.zhiziyun.dmptest.bot.ui.activity.WeChatBindActivity;
import com.zhiziyun.dmptest.bot.util.CustomDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 推广
 */
public class GeneralizeFragment extends Fragment implements View.OnClickListener {
    private List<Fragment> list;
    private String[] titles;
    private SharedPreferences share;
    private ViewPager viewPager;
    private int positions = 0;
    private int flags = 1;

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
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        final TabLayout tabLayout;
        MyAdapter adapter;
        viewPager = getView().findViewById(R.id.vp_view);
        viewPager.setOffscreenPageLimit(4);
        tabLayout = getView().findViewById(R.id.tabs);
        getView().findViewById(R.id.iv_add).setOnClickListener(this);
        getView().findViewById(R.id.iv_search).setOnClickListener(this);
        if (share.getBoolean("isShowPlanAds", false)) {//显示投广告
            if (share.getBoolean("isShowTencent", false)) {//显示朋友圈和wifi广告，条件相同
                titles = new String[]{"投广告", "发短信", "朋友圈", "WiFi广告"};
            } else {
                titles = new String[]{"投广告", "发短信"};
            }
        } else {
            if (share.getBoolean("isShowTencent", false)) {
                titles = new String[]{"发短信", "朋友圈", "WiFi广告"};
            } else {
                titles = new String[]{"发短信"};
            }
        }

        //页面，数据源
        list = new ArrayList<>();
        if (share.getBoolean("isShowPlanAds", false)) {//登录时会获取这个布尔值，如果为false就隐藏
            list.add(new AdvertisingFragment());
        }
        list.add(new SMSFragment());
        if (share.getBoolean("isShowTencent", false)) {//显示朋友圈
            list.add(new FriendsFragment());
            list.add(new WIFIADFragment());
        }
        //ViewPager的适配器
        adapter = new MyAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);
        //Tab的点击事件
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab == null) return;
            //这里使用到反射，拿到Tab对象后获取Class
            Class c = tab.getClass();
            try {
                //Filed “字段、属性”的意思,c.getDeclaredField 获取私有属性。
                //"mView"是Tab的私有属性名称(可查看TabLayout源码),类型是 TabView,TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
                //如果不这样会报如下错误
                // java.lang.IllegalAccessException:
                //Class com.test.accessible.Main
                //can not access
                //a member of class com.test.accessible.AccessibleTest
                //with modifiers "private"
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) return;
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) view.getTag();
                        //这里就可以根据业务需求处理点击事件了。
                        if (share.getBoolean("isShowTencent", false)) {//显示朋友圈和wifi广告，条件相同
                            //如果有显示朋友圈和wifi广告的权限才做点击判断
                            if (titles[position].equals("朋友圈")) {//如果点击的是朋友圈
                                if (!(share.getBoolean("isBindingWeChatSubscription", false) && share.getBoolean("isAuthorizationAd", false)
                                        && share.getBoolean("isOpenWeChatSubscriptionAdvertiser", false))) {//三个条件必须同时满足
                                    //点击弹出对话框
                                    final CustomDialog customDialog = new CustomDialog(getActivity());
                                    customDialog.setTitle("绑定公众号");
                                    customDialog.setMessage("您还没有绑定朋友圈广告投放的公众号，现在去绑定？");
                                    customDialog.setYesOnclickListener("立即绑定", new CustomDialog.onYesOnclickListener() {
                                        @Override
                                        public void onYesClick() {
                                            startActivity(new Intent(getActivity(), WeChatBindActivity.class));
                                            customDialog.dismiss();
                                        }
                                    });
                                    customDialog.setNoOnclickListener("暂不绑定", new CustomDialog.onNoOnclickListener() {
                                        @Override
                                        public void onNoClick() {
                                            viewPager.setCurrentItem(positions);//跳转
                                            customDialog.dismiss();
                                            select(positions);
                                        }
                                    });
                                    customDialog.show();
                                }
                            } else if (titles[position].equals("WiFi广告")) {//如果点击的是wifi广告
                                if (!(share.getBoolean("isBindingWeChatSubscription", false) && share.getBoolean("isAuthorizationAd", false))) {//2个条件必须同时满足
                                    //点击弹出对话框
                                    final CustomDialog customDialog = new CustomDialog(getActivity());
                                    customDialog.setTitle("绑定公众号");
                                    customDialog.setMessage("您还没有绑定朋友圈广告投放的公众号，现在去绑定？");
                                    customDialog.setYesOnclickListener("立即绑定", new CustomDialog.onYesOnclickListener() {
                                        @Override
                                        public void onYesClick() {
                                            startActivity(new Intent(getActivity(), WeChatBindActivity.class));
                                            customDialog.dismiss();
                                        }
                                    });
                                    customDialog.setNoOnclickListener("暂不绑定", new CustomDialog.onNoOnclickListener() {
                                        @Override
                                        public void onNoClick() {
                                            viewPager.setCurrentItem(positions);//跳转
                                            customDialog.dismiss();
                                            select(positions);
                                        }
                                    });
                                    customDialog.show();
                                }
                            } else {
                                positions = position;//跳转
                            }
                        }
                        select(position);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void select(int position) {
        switch (titles[position]) {
            case "投广告":
                flags = 1;
                break;
            case "发短信":
                flags = 2;
                break;
            case "朋友圈":
                flags = 3;
                break;
            case "WiFi广告":
                flags = 4;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                switch (flags) {
                    case 1:
                        startActivity(new Intent(getActivity(), AdvertisingActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), AddSmsActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), AddFriendsClubActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getContext(), WifiAdverActivity.class));
                        break;
                }
                break;
            case R.id.iv_search:
                Intent it = new Intent(getActivity(), SearchPageActivity.class);
                switch (flags) {
                    case 1:
                        it.putExtra("activity", "AdvertisingFragment");
                        break;
                    case 2:
                        it.putExtra("activity", "SMSFragment");
                        break;
                    case 3:
                        it.putExtra("activity", "FriendsFragment");
                        break;
                    case 4:
                        it.putExtra("activity", "WIFIADFragment");
                        break;
                }
                startActivity(it);
                break;
        }
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
