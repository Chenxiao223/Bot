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

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.TimeSlotAdapter;
import com.zhiziyun.dmptest.bot.entity.TimeSlot;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
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
 * 时段
 */
public class TimeSlotFragment extends Fragment implements View.OnClickListener {
    public static TimeSlotFragment fragment;
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
    private TimeSlot timeSlot;
    private HashMap<String, String> hm_timeslot;
    private ArrayList<HashMap<String, String>> list_timeslot = new ArrayList<>();
    private TimeSlotAdapter adapter;
    //折线图
    private LineChartView chartView;
    private LineChartData lineData;
    private SharedPreferences share;
    private SmartRefreshLayout smartRefreshLayout;
    private boolean shop = true;
    private boolean tanzhen = true;
    private LinearLayout line_page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_slot, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        fragment = this;
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearAllData();
    }

    //清空数据
    public void clearAllData() {
        list_timeslot.clear();
    }

    public void initView() {
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = getView().findViewById(R.id.refreshLayout);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        //初始化接口没有的数据
        list_shop.add("全部门店");
        list_tanzhen.add("全部探针");
        hm_store.put("全部门店", "0");
        hm_probe.put("全部探针", "0");
        getSiteOption();
        beginTime = gettodayDate();
        endTime = gettodayDate();

        //折线图
        chartView = getView().findViewById(R.id.chart);

        spn_shop = getView().findViewById(R.id.spn_shop);
        spn_tanzhen = getView().findViewById(R.id.spn_tanzhen);
        line_date = getView().findViewById(R.id.line_date);
        line_date.setOnClickListener(this);

        xlistview = getView().findViewById(R.id.xlistview);
        adapter = new TimeSlotAdapter(getContext(), list_timeslot);
        xlistview.setAdapter(adapter);
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    hm_timeslot.clear();
                    getvisitHour();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getvisitHour();
    }

    //折线图
    private void generateDefaultData(TimeSlot timeSlot) {
        int numValues = timeSlot.getObj().size();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        List<PointValue> values2 = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i) {
            values2.add(new PointValue(i, Float.parseFloat(timeSlot.getObj().get(i).getTotalUV().toString())));//环境客流
            values.add(new PointValue(i, Float.parseFloat(timeSlot.getObj().get(i).getUv())));//到店客流
            axisValues.add(new AxisValue(i).setLabel(timeSlot.getObj().get(i).getHour() + ""));
        }
        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(false).setPointRadius(2).setStrokeWidth(1).setFilled(true);//false是折线，true是曲线
        Line line2 = new Line(values2);
        line2.setColor(ChartUtils.COLOR_BLUE).setCubic(false).setPointRadius(2).setStrokeWidth(1).setFilled(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        lines.add(line2);
        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(5));
        chartView.setLineChartData(lineData);
        chartView.setViewportCalculationEnabled(true);//这个地方坑了我很久，每次数据更新Y轴都不变，把这里改成true就解决了
        chartView.setZoomType(ZoomType.HORIZONTAL);
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
                            .url(BaseUrl.BaseWang + "option/siteOption.action")
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

    //获取时段信息
    public void getvisitHour() {
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
                            .url(BaseUrl.BaseWang + "report/visitHour.action")
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
                            timeSlot = gson.fromJson(response.body().string(), TimeSlot.class);
                            handler.sendEmptyMessage(3);//通知刷新适配器
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
                    spn_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (shop) {//spinner初始化的时候不执行点击事件
                                shop = false;
                            } else {
                                storeId = Integer.parseInt(hm_store.get(list_shop.get(position)));
                                getvisitHour();
                            }
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
                    spn_tanzhen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (tanzhen) {//spinner初始化的时候不执行点击事件
                                tanzhen = false;
                            } else {
                                microprobeId = Integer.parseInt(hm_probe.get(list_tanzhen.get(position)));
                                getvisitHour();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 3:
                    if (timeSlot.getObj().isEmpty()) {//如果没数据就提示
                        handler.sendEmptyMessage(4);
                    } else {
                        list_timeslot.clear();
                        for (int i = 0; i < timeSlot.getObj().size(); i++) {
                            hm_timeslot = new HashMap<String, String>();
                            hm_timeslot.put("content1", timeSlot.getObj().get(i).getHour());
                            hm_timeslot.put("content2", timeSlot.getObj().get(i).getTotalUV());//环境客流
                            hm_timeslot.put("content3", timeSlot.getObj().get(i).getUv());//到店客流
                            list_timeslot.add(hm_timeslot);
                        }
                    }
                    line_page.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    //折线图
                    generateDefaultData(timeSlot);
                    break;
                case 4:
                    line_page.setVisibility(View.VISIBLE);
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    ToastUtils.showShort(getActivity(), "无数据");
                    break;
            }
            super.handleMessage(msg);
        }
    };

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
                        getvisitHour();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
            case R.id.line_page:
                getvisitHour();
                break;
        }
    }

    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
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
            timeSlot = null;
            hm_timeslot.clear();
            list_timeslot.clear();
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
