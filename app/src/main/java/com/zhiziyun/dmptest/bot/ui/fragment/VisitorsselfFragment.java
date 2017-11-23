package com.zhiziyun.dmptest.bot.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Administrator on 2017/7/17 0017.
 * 访客
 */
public class VisitorsselfFragment extends Fragment implements View.OnClickListener {
    private Spinner spn_shop, spn_tanzhen;
    private List<String> list_shop = new ArrayList<>();
    private List<String> list_tanzhen = new ArrayList<>();
    private ArrayAdapter<String> adp_shop;
    private ArrayAdapter<String> adp_tanzhen;
    private LinearLayout line_date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visitorsself, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        initView();
    }

    public void initView() {
        spn_shop = getView().findViewById(R.id.spn_shop);
        spn_tanzhen = getView().findViewById(R.id.spn_tanzhen);
        line_date = getView().findViewById(R.id.line_date);
        line_date.setOnClickListener(this);
        for (int i = 1; i < 4; i++) {
            list_shop.add("数据" + i);
            list_tanzhen.add("数据" + i);
        }
        adp_shop = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_shop);
        adp_shop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp_tanzhen = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_tanzhen);
        adp_tanzhen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_shop.setAdapter(adp_shop);
        spn_tanzhen.setAdapter(adp_tanzhen);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_date:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(getActivity(), 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String textString = String.format("开始时间：%d-%d-%d\n结束时间：%d-%d-%d\n", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        Log.i("date", textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
        }
    }


}
