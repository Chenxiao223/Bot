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
import com.zhiziyun.dmptest.bot.adapter.SendPlanAdapter;
import com.zhiziyun.dmptest.bot.ui.activity.SendPlanActivity;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Administrator on 2017/7/17 0017.
 * 发短信
 */
public class SMSFragment extends Fragment implements View.OnClickListener {
    public static SMSFragment smsFragment;
    private ListView lv_sms;
    public SendPlanAdapter sendPlanAdapter;
    public HashMap<String, String> hm_sms;
    public ArrayList<HashMap<String, String>> list_sms = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sms, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        initView();
    }

    public void initView() {
        smsFragment = this;
        getView().findViewById(R.id.iv_add).setOnClickListener(this);
        lv_sms = getView().findViewById(R.id.lv_sms);
        sendPlanAdapter = new SendPlanAdapter(getActivity(), list_sms);
        lv_sms.setAdapter(sendPlanAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                startActivity(new Intent(getActivity(), SendPlanActivity.class));
                break;
        }
    }

}
