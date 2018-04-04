package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.MainCrowdAdapter;
import com.zhiziyun.dmptest.bot.ui.activity.CrowdEditActivity;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/4/4.
 * 底部导航-人群
 */

public class MainCrowdFragment extends Fragment {
    private ListView lv_crowd;
    private MainCrowdAdapter adapter;
    private HashMap<String, String> hm_crowd;
    private ArrayList<HashMap<String, String>> list_crowd = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_crowd, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        initView();
        getData();
    }

    private void initView() {
        lv_crowd = getView().findViewById(R.id.lv_crowd);
        adapter = new MainCrowdAdapter(getActivity(), list_crowd);
        lv_crowd.setAdapter(adapter);
        //点击电话图标
        adapter.setOnCall(new MainCrowdAdapter.OnCall() {
            @Override
            public void setInfo(String nm, int position) {
                ToastUtils.showShort(getActivity(), "" + position);
            }
        });
        //点击编辑图标
        adapter.setOnEdit(new MainCrowdAdapter.OnEdit() {
            @Override
            public void setInfo(String nm, int position) {
                startActivity(new Intent(getActivity(), CrowdEditActivity.class));
            }
        });
    }

    public void getData() {
        for (int i = 0; i < 10; i++) {
            hm_crowd = new HashMap<>();
            hm_crowd.put("content1", "普通用户");
            hm_crowd.put("content2", "新转入");
            hm_crowd.put("content3", "张新生");
            hm_crowd.put("content4", "王经理");
            hm_crowd.put("content5", "xxxxx");
            list_crowd.add(hm_crowd);
            adapter.notifyDataSetChanged();
        }
    }
}
