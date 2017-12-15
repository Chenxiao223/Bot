package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.TimeSlotAdapter;
import com.zhiziyun.dmptest.bot.entity.Trend;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
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
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/7/17 0017.
 * 趋势
 */
public class TrendFragment extends Fragment implements View.OnClickListener {
    public static TrendFragment fragment;
    private Spinner spn_shop, spn_tanzhen;
    private List<String> list_shop = new ArrayList<>();
    private List<String> list_tanzhen = new ArrayList<>();
    private ArrayAdapter<String> adp_shop;
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
    private int pageNum = 1;
    //折线图
    private LineChartView chartView;
    private LineChartData lineData;
    private SharedPreferences share;
    private SmartRefreshLayout smartRefreshLayout;

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
//        list_shop.clear();
//        list_tanzhen.clear();
//        hm_store.clear();
//        hm_probe.clear();
        pageNum = 1;
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
                    pageNum = 1;
                    getTrend(pageNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (pageNum < ((trend.getTotal() / 10) + 3)) {
                    getTrend(pageNum);
                } else {
                    smartRefreshLayout.finishLoadmore(0);//停止加载
                    Toast.makeText(getActivity(), "最后一页了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //初始化时查询第一页
        getTrend(1);
    }

    public void getSiteOption() {
        //获取站点选项
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url("http://dmptest.zhiziyun.com/api/v1/option/siteOption.action")
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


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adp_shop = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_shop);
                    adp_shop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_shop.setAdapter(adp_shop);
                    spn_shop.setSelection(0, false);//防止不点击spinner也执行点击监听
                    spn_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            storeId = Integer.parseInt(hm_store.get(list_shop.get(position)));
                            getTrend(1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 2:
                    adp_tanzhen = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_tanzhen);
                    adp_tanzhen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_tanzhen.setAdapter(adp_tanzhen);
                    spn_tanzhen.setSelection(0, false);//防止不点击spinner也执行点击监听
                    spn_tanzhen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            microprobeId = Integer.parseInt(hm_probe.get(list_tanzhen.get(position)));
                            getTrend(1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 3:
                    if (trend.getRows().size() == 0) {//如果没数据就提示
                        handler.sendEmptyMessage(4);
                    } else {
                        list_trend.clear();
                        for (int i = 0; i < trend.getRows().size(); i++) {
                            hm_trend = new HashMap<String, String>();
                            hm_trend.put("content1", trend.getRows().get(i).getStatDate());
                            hm_trend.put("content2", trend.getRows().get(i).getTotalUV());
                            hm_trend.put("content3", trend.getRows().get(i).getUv());
                            list_trend.add(hm_trend);
                        }
                        pageNum++;
                    }
                    adapter.notifyDataSetChanged();
                    smartRefreshLayout.finishRefresh(0);//停止刷新
                    //线性图
                    generateInitialLineData(trend);
                    break;
                case 4:
                    Toast.makeText(getActivity(), "无数据", Toast.LENGTH_SHORT).show();
                    smartRefreshLayout.finishRefresh(0);//停止刷新
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void getTrend(final int page) {
        //获取站点选项
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
                    json.put("page", page);
                    json.put("rows", 10);//固定每次请求十行
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url("http://dmptest.zhiziyun.com/api/v1/report/visitDay.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Gson gson = new Gson();
                            trend = gson.fromJson(response.body().string(), Trend.class);
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
                        beginTime = textString.substring(0, index);
                        endTime = textString.substring(index + 1, textString.length());

                        getTrend(1);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
        }
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
    private void generateInitialLineData(Trend trend) {
        int numValues = trend.getRows().size();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        List<PointValue> values2 = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i) {
            values2.add(new PointValue(i, Float.parseFloat(trend.getRows().get(i).getTotalUV().toString())));//环境客流
            values.add(new PointValue(i, Float.parseFloat(trend.getRows().get(i).getUv())));//到店客流
            axisValues.add(new AxisValue(i).setLabel(trend.getRows().get(i).getStatDate().substring(2)));//为缩小长度，将2017改为17
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(false).setPointRadius(2).setStrokeWidth(1);//false是折线，true是曲线
        Line line2 = new Line(values2);
        line2.setColor(ChartUtils.COLOR_BLUE).setCubic(false).setPointRadius(2).setStrokeWidth(1);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        lines.add(line2);
        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(5));
        chartView.setLineChartData(lineData);
        chartView.setViewportCalculationEnabled(false);
        chartView.setZoomType(ZoomType.HORIZONTAL);
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
            pageNum = 1;
            chartView = null;
            lineData = null;
            share = null;
            smartRefreshLayout = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
