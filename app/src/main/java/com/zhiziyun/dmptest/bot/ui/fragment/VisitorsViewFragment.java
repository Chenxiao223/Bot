package com.zhiziyun.dmptest.bot.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.simpleArrayAdapter;
import com.zhiziyun.dmptest.bot.entity.PieDataEntity;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.widget.PieChart_active;
import com.zhiziyun.dmptest.bot.widget.PieChart_age;
import com.zhiziyun.dmptest.bot.widget.PieChart_mpb;
import com.zhiziyun.dmptest.bot.widget.PieChart_mpm;
import com.zhiziyun.dmptest.bot.widget.PieChart_mpp;
import com.zhiziyun.dmptest.bot.widget.PieChart_sex;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Administrator on 2017/7/17 0017.
 * 画像
 */
public class VisitorsViewFragment extends Fragment implements View.OnClickListener {
    private Spinner spn_shop, spn_tanzhen;
    private List<String> list_shop = new ArrayList<>();
    private List<String> list_tanzhen = new ArrayList<>();
    private simpleArrayAdapter<String> adp_shop;
    private simpleArrayAdapter<String> adp_tanzhen;
    private LinearLayout line_date;
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00, 0xFF800000};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visitors_view, container, false);
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
        adp_shop = new simpleArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_shop);
        adp_shop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp_tanzhen = new simpleArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_tanzhen);
        adp_tanzhen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_shop.setAdapter(adp_shop);
        spn_tanzhen.setAdapter(adp_tanzhen);

        //饼状图-年龄
        PieChart_age pieChart = getView().findViewById(R.id.chart_age);
        List<PieDataEntity> dataEntities = new ArrayList<>();
        PieDataEntity entity1 = new PieDataEntity("name" + 0, 2, mColors[0]);
        dataEntities.add(entity1);
        PieDataEntity entity2 = new PieDataEntity("name" + 1, 2, mColors[1]);
        dataEntities.add(entity2);
        PieDataEntity entity3 = new PieDataEntity("name" + 2, 3, mColors[2]);
        dataEntities.add(entity3);
        PieDataEntity entity4 = new PieDataEntity("name" + 3, 2, mColors[3]);
        dataEntities.add(entity4);
        pieChart.setDataList(dataEntities);

        pieChart.setOnItemPieClickListener(new PieChart_age.OnItemPieClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    Toast.makeText(getActivity(), "新客", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getActivity(), "老客", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //饼状图-性别
        PieChart_sex pieChart_sex = getView().findViewById(R.id.chart_sex);
        List<PieDataEntity> dataEntitiessex = new ArrayList<>();
        PieDataEntity entitysex1 = new PieDataEntity("name" + 0, 2, mColors[0]);
        dataEntitiessex.add(entitysex1);
        PieDataEntity entitysex2 = new PieDataEntity("name" + 1, 2, mColors[1]);
        dataEntitiessex.add(entitysex2);
        pieChart_sex.setDataList(dataEntitiessex);

        pieChart_sex.setOnItemPieClickListener(new PieChart_sex.OnItemPieClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    Toast.makeText(getActivity(), "新客", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getActivity(), "老客", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //饼状图-手机品牌
        PieChart_mpb pieChart_mpb = getView().findViewById(R.id.chart_mpb);
        List<PieDataEntity> dataEntitiesmpb = new ArrayList<>();
        PieDataEntity entitympb1 = new PieDataEntity("name" + 0, 2, mColors[0]);
        dataEntitiesmpb.add(entitympb1);
        PieDataEntity entitympb2 = new PieDataEntity("name" + 1, 2, mColors[1]);
        dataEntitiesmpb.add(entitympb2);
        PieDataEntity entitympb3 = new PieDataEntity("name" + 3, 2, mColors[2]);
        dataEntitiesmpb.add(entitympb3);
        PieDataEntity entitympb4 = new PieDataEntity("name" + 4, 2, mColors[3]);
        dataEntitiesmpb.add(entitympb4);
        PieDataEntity entitympb5 = new PieDataEntity("name" + 5, 2, mColors[4]);
        dataEntitiesmpb.add(entitympb5);
        PieDataEntity entitympb6 = new PieDataEntity("name" + 6, 2, mColors[5]);
        dataEntitiesmpb.add(entitympb6);
        PieDataEntity entitympb7 = new PieDataEntity("name" + 7, 2, mColors[6]);
        dataEntitiesmpb.add(entitympb7);
        pieChart_mpb.setDataList(dataEntitiesmpb);

        pieChart_sex.setOnItemPieClickListener(new PieChart_sex.OnItemPieClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    Toast.makeText(getActivity(), "新客", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getActivity(), "老客", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //饼状图-手机型号
        PieChart_mpm pieChart_mpm = getView().findViewById(R.id.chart_mpm);
        List<PieDataEntity> dataEntitiesmpm = new ArrayList<>();
        PieDataEntity entitympm1 = new PieDataEntity("name" + 0, 2, mColors[0]);
        dataEntitiesmpm.add(entitympm1);
        PieDataEntity entitympm2 = new PieDataEntity("name" + 1, 2, mColors[1]);
        dataEntitiesmpm.add(entitympm2);
        PieDataEntity entitympm3 = new PieDataEntity("name" + 3, 2, mColors[2]);
        dataEntitiesmpm.add(entitympm3);
        PieDataEntity entitympm4 = new PieDataEntity("name" + 4, 2, mColors[3]);
        dataEntitiesmpm.add(entitympm4);
        PieDataEntity entitympm5 = new PieDataEntity("name" + 5, 2, mColors[4]);
        dataEntitiesmpm.add(entitympm5);
        PieDataEntity entitympm6 = new PieDataEntity("name" + 6, 2, mColors[5]);
        dataEntitiesmpm.add(entitympm6);
        PieDataEntity entitympm7 = new PieDataEntity("name" + 7, 2, mColors[6]);
        dataEntitiesmpm.add(entitympm7);
        PieDataEntity entitympm8 = new PieDataEntity("name" + 8, 2, mColors[7]);
        dataEntitiesmpm.add(entitympm8);
        PieDataEntity entitympm9 = new PieDataEntity("name" + 9, 2, mColors[8]);
        dataEntitiesmpm.add(entitympm9);
        PieDataEntity entitympm10 = new PieDataEntity("name" + 10, 2, mColors[9]);
        dataEntitiesmpm.add(entitympm10);
        pieChart_mpm.setDataList(dataEntitiesmpm);

        //饼状图-手机价格
        PieChart_mpp pieChart_mpp = getView().findViewById(R.id.chart_mpp);
        List<PieDataEntity> dataEntitiesmpp = new ArrayList<>();
        PieDataEntity entitympp1 = new PieDataEntity("name" + 0, 2, mColors[0]);
        dataEntitiesmpp.add(entitympp1);
        PieDataEntity entitympp2 = new PieDataEntity("name" + 1, 2, mColors[1]);
        dataEntitiesmpp.add(entitympp2);
        PieDataEntity entitympp3 = new PieDataEntity("name" + 2, 3, mColors[2]);
        dataEntitiesmpp.add(entitympp3);
        PieDataEntity entitympp4 = new PieDataEntity("name" + 3, 2, mColors[3]);
        dataEntitiesmpp.add(entitympp4);
        pieChart_mpp.setDataList(dataEntitiesmpp);

        //饼状图-活跃度
        PieChart_active pieChart_active = getView().findViewById(R.id.chart_active);
        List<PieDataEntity> dataEntitiesactive = new ArrayList<>();
        PieDataEntity entityactive1 = new PieDataEntity("name" + 0, 2, mColors[0]);
        dataEntitiesactive.add(entityactive1);
        pieChart_active.setDataList(dataEntitiesactive);
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
