package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Intent;
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
import com.zhiziyun.dmptest.bot.ui.activity.AdvertisingActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 投广告
 */
public class AdvertisingFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advertising, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        initView();
    }

    public void initView() {
        getView().findViewById(R.id.iv_Advertising).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_Advertising:
                startActivity(new Intent(getActivity(), AdvertisingActivity.class));
                break;
        }
    }

}
