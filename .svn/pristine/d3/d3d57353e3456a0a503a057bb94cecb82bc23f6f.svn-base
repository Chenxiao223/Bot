package com.zhiziyun.dmptest.bot.ui.fragment.fragmentaddoriginality;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.ShowTemplateActivity;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

/**
 * Created by Administrator on 2018/6/4.
 */

public class GeneralTemplateFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ori_general_template, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public void initView() {
        getView().findViewById(R.id.rl_a).setOnClickListener(this);
        getView().findViewById(R.id.rl_b).setOnClickListener(this);
        getView().findViewById(R.id.rl_c).setOnClickListener(this);
        getView().findViewById(R.id.rl_d).setOnClickListener(this);
        getView().findViewById(R.id.rl_e).setOnClickListener(this);
        getView().findViewById(R.id.rl_f).setOnClickListener(this);
        getView().findViewById(R.id.rl_g).setOnClickListener(this);
        getView().findViewById(R.id.rl_h).setOnClickListener(this);
        getView().findViewById(R.id.rl_i).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_a:
                Intent it = new Intent(getActivity(), ShowTemplateActivity.class);
                it.putExtra("flag", 1);
                startActivity(it);
                break;
            case R.id.rl_b:
//                Intent it2 = new Intent(getActivity(), ShowTemplateActivity.class);
//                it2.putExtra("flag", 2);
//                startActivity(it2);
                ToastUtils.showShort(getActivity(), "暂缺");
                break;
            case R.id.rl_c:
                Intent it3 = new Intent(getActivity(), ShowTemplateActivity.class);
                it3.putExtra("flag", 3);
                startActivity(it3);
                break;
            case R.id.rl_d:
                Intent it4 = new Intent(getActivity(), ShowTemplateActivity.class);
                it4.putExtra("flag", 4);
                startActivity(it4);
                break;
            case R.id.rl_e:
                Intent it5 = new Intent(getActivity(), ShowTemplateActivity.class);
                it5.putExtra("flag", 5);
                startActivity(it5);
                break;
            case R.id.rl_f:
                ToastUtils.showShort(getActivity(), "暂缺");
                break;
            case R.id.rl_g:
                ToastUtils.showShort(getActivity(), "暂缺");
                break;
            case R.id.rl_h:
                ToastUtils.showShort(getActivity(), "暂缺");
                break;
            case R.id.rl_i:
                Intent it9 = new Intent(getActivity(), ShowTemplateActivity.class);
                it9.putExtra("flag", 9);
                startActivity(it9);
                break;
        }
    }
}
