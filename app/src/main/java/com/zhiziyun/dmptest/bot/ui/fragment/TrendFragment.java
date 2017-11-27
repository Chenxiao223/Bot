package com.zhiziyun.dmptest.bot.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.TimeSlotAdapter;
import com.zhiziyun.dmptest.bot.entity.ChartEntity;
import com.zhiziyun.dmptest.bot.entity.Trend;
import com.zhiziyun.dmptest.bot.http.DESCoder;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.widget.LineChart_pot;
import com.zhiziyun.dmptest.bot.xListView.XListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
public class TrendFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener {
    private Spinner spn_shop, spn_tanzhen;
    private List<String> list_shop = new ArrayList<>();
    private List<String> list_tanzhen = new ArrayList<>();
    private ArrayAdapter<String> adp_shop;
    private ArrayAdapter<String> adp_tanzhen;
    private LinearLayout line_date;
    private XListView xlistview;
    private HashMap<String, String> hm_store = new HashMap<String, String>();
    private HashMap<String, String> hm_probe = new HashMap<String, String>();
    private String token;
    private int microprobeId = 0;
    private int storeId = 0;
    private String beginTime, endTime;
    private Trend trend;
    private HashMap<String, String> hm_trend;
    private ArrayList<HashMap<String, String>> list_trend = new ArrayList<>();
    private TimeSlotAdapter adapter;
    private int pageNum = 1;

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //初始化接口没有的数据
            list_shop.add("全部门店");
            list_tanzhen.add("全部探针");
            hm_store.put("全部门店", "0");
            hm_probe.put("全部探针", "0");
            getSiteOption();
        } else {
            clearAllData();
        }
    }

    //清空数据
    public void clearAllData() {
        list_shop.clear();
        list_tanzhen.clear();
        hm_store.clear();
        hm_probe.clear();
        token = null;
        pageNum = 1;
    }

    public void initView() {
        spn_shop = getView().findViewById(R.id.spn_shop);
        spn_tanzhen = getView().findViewById(R.id.spn_tanzhen);
        line_date = getView().findViewById(R.id.line_date);
        line_date.setOnClickListener(this);

        xlistview = getView().findViewById(R.id.xlistview);
        xlistview.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        adapter = new TimeSlotAdapter(getContext(), list_trend);
        xlistview.setAdapter(adapter);
        xlistview.setXListViewListener(this);
    }

    public void getSiteOption() {
        //token加密
        try {
            token = DESCoder.encrypt("1" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取站点选项
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", "0zoTLi29XRgq");
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(token, "utf-8") + "&json=" + json.toString();
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
                            list_trend.clear();
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
                            list_trend.clear();
                            getTrend(1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 3:
                    adapter.notifyDataSetChanged();
                    onLoad();//数据加载完后就停止刷新
                    //线性图
                    LineChart_pot lineChart = getView().findViewById(R.id.linechart_pot);
                    List<ChartEntity> data = new ArrayList<>();
                    List<ChartEntity> data2 = new ArrayList<>();
                    for (int i=0;i<trend.getRows().size();i++){
                        data.add(new ChartEntity(trend.getRows().get(i).getStatDate(), Float.parseFloat(trend.getRows().get(i).getPv())));
                        data2.add(new ChartEntity(trend.getRows().get(i).getStatDate(), Float.parseFloat(trend.getRows().get(i).getUv())));
                    }
                    lineChart.setData(data,data2);
                    break;
                case 4:
                    Toast.makeText(getActivity(), "无数据", Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void getTrend(final int page) {
        //token加密
        try {
            token = DESCoder.encrypt("1" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取站点选项
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", "0zoTLi29XRgq");
                    json.put("beginTime", beginTime);
                    json.put("endTime", endTime);
                    json.put("microprobeId", microprobeId);
                    json.put("storeId", storeId);
                    json.put("page", page);
                    json.put("rows", 10);//固定每次请求十行
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(token, "utf-8") + "&json=" + json.toString();
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
                            if (trend.getRows().size() == 0) {//如果没数据就提示
                                handler.sendEmptyMessage(4);
                            } else {
                                for (int i = 0; i < trend.getRows().size(); i++) {
                                    hm_trend = new HashMap<String, String>();
                                    hm_trend.put("content1", trend.getRows().get(i).getStatDate());
                                    hm_trend.put("content2", trend.getRows().get(i).getPv());
                                    hm_trend.put("content3", trend.getRows().get(i).getUv());
                                    list_trend.add(hm_trend);
                                }
                                handler.sendEmptyMessage(3);//通知刷新适配器
                                pageNum++;
                            }
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

                        list_trend.clear();
                        getTrend(1);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        hm_trend.clear();
        clearAllData();
        getTrend(pageNum);
    }

    // 加载更多
    @Override
    public void onLoadMore() {
        if (pageNum < ((trend.getTotal() / 10) + 3)) {
            getTrend(pageNum);
        } else {
            Toast.makeText(getActivity(), "最后一页了", Toast.LENGTH_SHORT).show();
        }
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
