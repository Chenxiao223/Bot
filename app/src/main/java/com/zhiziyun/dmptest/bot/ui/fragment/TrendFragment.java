package com.zhiziyun.dmptest.bot.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.simpleArrayAdapter;
import com.zhiziyun.dmptest.bot.entity.ChartEntity;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.widget.LineChart_pot;
import com.zhiziyun.dmptest.bot.xListView.XListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Administrator on 2017/7/17 0017.
 * 趋势
 */
public class TrendFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener {
    private Spinner spn_shop, spn_tanzhen;
    private List<String> list_shop = new ArrayList<>();
    private List<String> list_tanzhen = new ArrayList<>();
    private simpleArrayAdapter<String> adp_shop;
    private simpleArrayAdapter<String> adp_tanzhen;
    private LinearLayout line_date;
    private XListView xlistview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trend, container, false);
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

        //线性图
        LineChart_pot lineChart = getView().findViewById(R.id.linechart_pot);
        List<ChartEntity> data = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            data.add(new ChartEntity("数据" + i, (float) (20000)));
        }
        lineChart.setData(data);

        xlistview = (XListView) getView().findViewById(R.id.xlistview);
        xlistview.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        xlistview.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, getData()));
        xlistview.setXListViewListener(this);
        xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "点击了" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        return data;
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

    //刷新
    @Override
    public void onRefresh() {
        onLoad();
    }

    // 加载更多
    @Override
    public void onLoadMore() {
        onLoad();
    }

    /**
     * 停止刷新，
     */
    private void onLoad() {
        xlistview.stopRefresh();
        xlistview.stopLoadMore();
        xlistview.setRefreshTime("刚刚");
    }
}
