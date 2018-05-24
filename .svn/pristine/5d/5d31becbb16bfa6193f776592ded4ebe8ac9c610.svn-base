package com.zhiziyun.dmptest.bot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhiziyun.dmptest.bot.ui.fragment.ClickCrowdFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.ShopCrowdFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.WifiCrowdFragment;

/**
 *
 */
public class VisitorsAdapter extends FragmentPagerAdapter {
    public VisitorsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ShopCrowdFragment();
        } else if (position == 1) {
            return new WifiCrowdFragment();
        } else {
            return new ClickCrowdFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
