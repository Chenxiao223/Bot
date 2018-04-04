package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CrowdAdapter;
import com.zhiziyun.dmptest.bot.ui.activity.AddCorwdActivity;
import com.zhiziyun.dmptest.bot.ui.activity.CrowdDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/4/3.
 * 人群
 */

public class CrowdFragment extends Fragment implements View.OnClickListener {
    public static CrowdFragment crowdFragment;
    private ListView lv_crowd;
    private SharedPreferences share;
    private HashMap<String, Object> hm_crowd;
    public ArrayList<HashMap<String, Object>> list_crowd = new ArrayList<>();
    public CrowdAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;
    private LinearLayout line_page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crowd, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        crowdFragment = this;
        initView();
        getData();
    }

    private void initView() {
        lv_crowd = getView().findViewById(R.id.lv_crowd);
        smartRefreshLayout = (SmartRefreshLayout) getView().findViewById(R.id.refreshLayout);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);

        getView().findViewById(R.id.iv_addcrowd).setOnClickListener(this);

        adapter = new CrowdAdapter(getActivity(), list_crowd);
        lv_crowd.setAdapter(adapter);
        lv_crowd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), CrowdDetailsActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_page:
                break;
            case R.id.iv_addcrowd:
                startActivity(new Intent(getActivity(), AddCorwdActivity.class));
                break;
        }
    }

    public void getData() {
        for (int i = 0; i < 10; i++) {
            hm_crowd = new HashMap<>();
            hm_crowd.put("content1", "招商会");
            hm_crowd.put("content2", "2018-1-23 15:53:09");
            hm_crowd.put("content3", false);
            hm_crowd.put("content4", false);
            list_crowd.add(hm_crowd);
            adapter.notifyDataSetChanged();
        }
    }
}