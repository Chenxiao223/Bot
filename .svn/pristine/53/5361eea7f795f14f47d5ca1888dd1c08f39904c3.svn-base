package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.SpinnerArrayAdapter;
import com.zhiziyun.dmptest.bot.adapter.TimeSlotAdapter;
import com.zhiziyun.dmptest.bot.entity.HomePage;
import com.zhiziyun.dmptest.bot.entity.Trend;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.github.mikephil.charting.data.LineDataSet.Mode.HORIZONTAL_BEZIER;


/**
 * 趋势
 */
public class TrendFragment extends Fragment implements View.OnClickListener {
    public static TrendFragment fragment;
    private Spinner spn_shop, spn_tanzhen;
    private ArrayList<String> list_shop = new ArrayList<>();
    private ArrayAdapter<String> adp_shop;
    private ArrayList<String> list_tanzhen = new ArrayList<>();
    private ArrayAdapter<String> adp_tanzhen;
    private LinearLayout line_date;
    private ListView xlistview;
    private HashMap<String, String> hm_store = new HashMap<String, String>();
    private HashMap<String, String> hm_probe = new HashMap<String, String>();
    private int microprobeId = 0;
    private int storeId = 0;
    private String beginTime, endTime;
    private Trend trend;
    private HashMap<String, String> hm_trend;
    private ArrayList<HashMap<String, String>> list_trend = new ArrayList<>();
    private TimeSlotAdapter adapter;
    //折线图
    private LineChart chartView;
    private LineChartData lineData;
    private SharedPreferences share;
    private SmartRefreshLayout smartRefreshLayout;
    private boolean shop = true;
    private boolean tanzhen = true;
    private LinearLayout line_page;
    private int m_Position = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        fragment = this;
        initView();
    }

    //清空数据
    public void clearAllData() {
        list_trend.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearAllData();
    }

    public void initView() {
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = getView().findViewById(R.id.refreshLayout);
        smartRefreshLayout.setEnableLoadmore(false);//屏蔽掉上拉加载的效果
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        //初始化接口没有的数据
        list_shop.add("全部门店");
        list_tanzhen.add("全部探针");
        hm_store.put("全部门店", "0");
        hm_probe.put("全部探针", "0");
        getSiteOption();
        beginTime = getPastDate(6);
        endTime = gettodayDate();

        //折线图
        chartView = getView().findViewById(R.id.linechart_pot);

        spn_shop = getView().findViewById(R.id.spn_shop);
        spn_tanzhen = getView().findViewById(R.id.spn_tanzhen);
        line_date = getView().findViewById(R.id.line_date);
        line_date.setOnClickListener(this);

        xlistview = getView().findViewById(R.id.xlistview);
        adapter = new TimeSlotAdapter(getContext(), list_trend);
        xlistview.setAdapter(adapter);
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    hm_trend.clear();
                    clearAllData();
                    getTrend();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //初始化时查询第一页
        getTrend();
    }

    public void getSiteOption() {
        //获取站点选项
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("id", storeId);
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "option/probeOption")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                JSONObject json_obj = new JSONObject(jsonObject.get("obj").toString());
                                JSONObject json_store = new JSONObject(json_obj.get("store").toString());
                                Iterator<String> store = json_store.keys();
                                while (store.hasNext()) {
                                    // 获得key
                                    String key = store.next();
                                    hm_store.put(key, json_store.getString(key));
                                    list_shop.add(key);
                                }
                                handler.sendEmptyMessage(1);

                                JSONObject json_probe = new JSONObject(json_obj.get("probe").toString());
                                Iterator<String> probe = json_probe.keys();
                                while (probe.hasNext()) {
                                    // 获得key
                                    String key = probe.next();
                                    hm_probe.put(key, json_probe.getString(key));
                                    list_tanzhen.add(key);
                                }
                                handler.sendEmptyMessage(2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void requestNT() {
        try {
            list_shop.clear();
            list_tanzhen.clear();
            hm_store.clear();
            hm_probe.clear();
            //初始化接口没有的数据
            list_shop.add("全部门店");
            list_tanzhen.add("全部探针");
            hm_store.put("全部门店", "0");
            hm_probe.put("全部探针", "0");
            getSiteOption();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        adp_shop = new SpinnerArrayAdapter(getActivity(), list_shop);
                        spn_shop.setAdapter(adp_shop);
                        spn_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (shop) {
                                    shop = false;
                                } else {
                                    storeId = Integer.parseInt(hm_store.get(list_shop.get(position)));
                                    list_trend.clear();
                                    getTrend();
                                    //为了防止无限循环
                                    requestNT();
                                    shop = true;
                                    m_Position = position;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        spn_shop.setSelection(m_Position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        adp_tanzhen = new SpinnerArrayAdapter(getActivity(), list_tanzhen);
                        spn_tanzhen.setAdapter(adp_tanzhen);
                        spn_tanzhen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (tanzhen) {
                                    tanzhen = false;
                                } else {
                                    String str = hm_probe.get(list_tanzhen.get(position));
                                    if (str.equals("0")) {
                                        microprobeId = 0;
                                    } else {
                                        microprobeId = Integer.parseInt(str.substring(str.indexOf("_") + 1));
                                    }
                                    list_trend.clear();
                                    getTrend();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        if (trend.getRows().size() == 0) {//如果没数据就提示
                            handler.sendEmptyMessage(4);
                        } else {
                            for (int i = 0; i < trend.getRows().size(); i++) {
                                hm_trend = new HashMap<String, String>();
                                hm_trend.put("content1", trend.getRows().get(i).getStatDate());
                                hm_trend.put("content2", trend.getRows().get(i).getTotalUV());
                                hm_trend.put("content3", trend.getRows().get(i).getUv());
                                list_trend.add(hm_trend);
                            }
                            line_page.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                        smartRefreshLayout.finishRefresh(0);//停止刷新
                        //线性图
                        //显示曲线图
                        initChart(chartView, trend);
                        chartView.setData(generateInitialLineData(trend));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        line_page.setVisibility(View.VISIBLE);
                        ToastUtils.showShort(getActivity(), "无数据");
                        smartRefreshLayout.finishRefresh(0);//停止刷新
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public LineChart initChart(LineChart chart, final Trend trend) {
        //设置边框颜色
        chart.setBorderColor(Color.WHITE);
        //是否展示网格线
        chart.setDrawGridBackground(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);
        //是否显示边界
        chart.setDrawBorders(true);
        //是否可以拖动
        chart.setDragEnabled(false);
        //是否有触摸事件
        chart.setTouchEnabled(true);
        //设置XY轴动画效果
        chart.animateY(1500);
        // 不显示数据描述
        chart.getDescription().setEnabled(false);
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("暂无数据");
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        // 不可以缩放
        chart.setScaleEnabled(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        // 向左偏移15dp，抵消y轴向右偏移的30dp
        chart.setExtraLeftOffset(-15);

        XAxis xAxis = chart.getXAxis();
        // 不显示x轴
        xAxis.setDrawAxisLine(false);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(10);
        xAxis.setGridColor(Color.parseColor("#30FFFFFF"));
        // 设置x轴数据偏移量
        xAxis.setYOffset(5);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = trend.getRows().get((int) value).getStatDate().substring(5);
                return tradeDate;
            }
        });
//        xAxis.setLabelCount(6, false);

        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTextColor(Color.GRAY);
        yAxis.setTextSize(10);
        // 设置y轴数据偏移量
        yAxis.setXOffset(20);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);

        chart.invalidate();
        return chart;
    }

    public void getTrend() {
        //趋势接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("beginTime", beginTime);
                    json.put("endTime", endTime);
                    json.put("microprobeId", microprobeId);
                    json.put("storeId", storeId);
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "report/visitDay")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            trend = gson.fromJson(str, Trend.class);
                            handler.sendEmptyMessage(3);//通知刷新适配器
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                        String textString = String.format("%d-%d-%d %d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        int index = textString.indexOf(" ");
                        beginTime = date(textString.substring(0, index));
                        endTime = date(textString.substring(index + 1, textString.length()));

                        list_trend.clear();
                        getTrend();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        list_trend.clear();
                        getTrend();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public String date(String date) {
        int index1 = date.indexOf("-");
        int index2 = index1 + date.substring(date.indexOf("-") + 1).indexOf("-") + 1;
        String year = date.substring(0, index1);
        String month = date.substring(index1 + 1, index2).length() == 1 ? "0" + date.substring(index1 + 1, index2) : date.substring(index1 + 1, index2);
        String day = date.substring(index2 + 1).length() == 1 ? "0" + date.substring(index2 + 1) : date.substring(index2 + 1);
        return year + "-" + month + "-" + day;
    }

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    //获取过去某天的日期
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 初始化数据到chart中
     */
    private LineData generateInitialLineData(Trend trend) {
        int numValues = trend.getRows().size();
        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();
        for (int i = 0; i < numValues; ++i) {
            values1.add(new Entry(i, Float.parseFloat(trend.getRows().get(i).getUv().toString())));//进店客流
            values2.add(new Entry(i, Float.parseFloat(trend.getRows().get(i).getTotalUV())));//环境客流
        }
        LineDataSet dataSetA = new LineDataSet(values1, "新客");
        dataSetA.setCircleSize(0f);
        dataSetA.setDrawCircleHole(true); //是否定制节点圆心的颜色，若为false，则节点为单一的同色点，若为true则可以设置节点圆心的颜色
        dataSetA.setCircleColorHole(Color.parseColor("#ff5d92"));  //设置节点圆心的颜色
        dataSetA.setMode(HORIZONTAL_BEZIER);
        dataSetA.setValueTextColor(Color.GRAY);
        dataSetA.setColor(Color.parseColor("#ff5d92"));
        dataSetA.setDrawFilled(true);//设置是否开启填充，默认为false
        dataSetA.setFillColor(Color.parseColor("#ff5d92"));//设置填充颜色
        dataSetA.setFillAlpha(55);//设置填充区域透明度，默认值为85
        //设置折线数据 getChartData返回一个List<Entry>键值对集合标识 折线点的横纵坐标，"A"代表折线标识
        LineDataSet dataSetB = new LineDataSet(values2, "老客");
        dataSetB.setCircleSize(0f);
        dataSetB.setDrawCircleHole(true); //是否定制节点圆心的颜色，若为false，则节点为单一的同色点，若为true则可以设置节点圆心的颜色
        dataSetB.setCircleColorHole(Color.parseColor("#5cabf8"));  //设置节点圆心的颜色
        dataSetB.setMode(HORIZONTAL_BEZIER);
        dataSetB.setValueTextColor(Color.GRAY);
        dataSetB.setColor(Color.parseColor("#5cabf8"));
        dataSetB.setDrawFilled(true);//设置是否开启填充，默认为false
        dataSetB.setFillColor(Color.parseColor("#5cabf8"));//设置填充颜色
        dataSetB.setFillAlpha(55);//设置填充区域透明度，默认值为85

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA);
        dataSets.add(dataSetB);
        LineData data = new LineData(dataSets);
        return data;
    }

    public void clearmemory() {
        try {
            spn_shop.setAdapter(null);
            spn_tanzhen.setAdapter(null);
            list_shop.clear();
            list_tanzhen.clear();
            adp_shop.clear();
            adp_tanzhen.clear();
            line_date = null;
            xlistview = null;
            hm_store.clear();
            hm_probe.clear();
            microprobeId = 0;
            storeId = 0;
            beginTime = null;
            endTime = null;
            trend = null;
            hm_trend.clear();
            list_trend.clear();
            adapter = null;
            chartView = null;
            lineData = null;
            share = null;
            smartRefreshLayout = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
