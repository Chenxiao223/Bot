package com.zhiziyun.dmptest.bot.ui.fragment.fragmentaddoriginality;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.RealtyActivity;


/**
 * Created by Administrator on 2018/1/4.
 * 房地产
 */

public class RealtyFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ori_realty_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public void initView(){
        getView().findViewById(R.id.iv_template).setOnClickListener(this);
        getView().findViewById(R.id.iv_template2).setOnClickListener(this);
        getView().findViewById(R.id.iv_template3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_template:
                Intent it = new Intent(getActivity(), RealtyActivity.class);
                it.putExtra("flag", 1);//用以区分点击了哪个模板
                it.putExtra("size","640*100");//将选择的模板的尺寸传过去
                startActivity(it);
                getActivity().finish();
                break;
            case R.id.iv_template2:
                Intent it2 = new Intent(getActivity(), RealtyActivity.class);
                it2.putExtra("flag", 2);//用以区分点击了哪个模板
                it2.putExtra("size","640*100");//将选择的模板的尺寸传过去
                startActivity(it2);
                getActivity().finish();
                break;
            case R.id.iv_template3:
                Intent it3 = new Intent(getActivity(), RealtyActivity.class);
                it3.putExtra("flag", 3);//用以区分点击了哪个模板
                it3.putExtra("size","640*100");//将选择的模板的尺寸传过去
                startActivity(it3);
                getActivity().finish();
                break;
        }
    }
}
