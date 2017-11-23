package com.zhiziyun.dmptest.bot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhiziyun.dmptest.bot.ui.fragment.AccountFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.GeneralizeFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.HomePageFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.VisitorsFragment;

/**
 * Created by Administrator on 2017/7/17 0017.
 */
public class HomePageAdapter extends FragmentPagerAdapter {
    public HomePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new HomePageFragment();
        }else if(position==1){
            return new VisitorsFragment();
        }else if(position==2){
            return new GeneralizeFragment();
        }else{
            return new AccountFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
