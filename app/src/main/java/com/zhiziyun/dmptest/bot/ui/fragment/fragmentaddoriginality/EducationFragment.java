package com.zhiziyun.dmptest.bot.ui.fragment.fragmentaddoriginality;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.EducationActivity;


/**
 * Created by Administrator on 2018/1/4.
 * 教育培训
 */

public class EducationFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ori_education_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public void initView() {
        getView().findViewById(R.id.iv_template_1).setOnClickListener(this);
        getView().findViewById(R.id.iv_template_2).setOnClickListener(this);
        getView().findViewById(R.id.iv_template_3).setOnClickListener(this);
        getView().findViewById(R.id.iv_template_4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_template_1:
                Intent it = new Intent(getActivity(), EducationActivity.class);
                it.putExtra("flag", 1);//用以区分点击了哪个模板
                it.putExtra("size", "640*100");//将选择的模板的尺寸传过去
                startActivity(it);
                getActivity().finish();
                break;
            case R.id.iv_template_2:
                Intent it2 = new Intent(getActivity(), EducationActivity.class);
                it2.putExtra("flag", 2);//用以区分点击了哪个模板
                it2.putExtra("size", "640*100");//将选择的模板的尺寸传过去
                startActivity(it2);
                getActivity().finish();
                break;
            case R.id.iv_template_3:
                Intent it3 = new Intent(getActivity(), EducationActivity.class);
                it3.putExtra("flag", 3);//用以区分点击了哪个模板
                it3.putExtra("size", "640*100");//将选择的模板的尺寸传过去
                startActivity(it3);
                getActivity().finish();
                break;
            case R.id.iv_template_4:
                Intent it4 = new Intent(getActivity(), EducationActivity.class);
                it4.putExtra("flag", 4);//用以区分点击了哪个模板
                it4.putExtra("size", "640*100");//将选择的模板的尺寸传过去
                startActivity(it4);
                getActivity().finish();
                break;
        }
    }
}
