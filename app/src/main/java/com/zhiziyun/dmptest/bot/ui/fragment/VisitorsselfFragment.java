package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.SpinnerArrayAdapter;
import com.zhiziyun.dmptest.bot.adapter.VisitorsselfAdapter;
import com.zhiziyun.dmptest.bot.entity.Visitorsself;
import com.zhiziyun.dmptest.bot.ui.activity.VisitorsselfActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * 访客列表
 */
public class VisitorsselfFragment extends Fragment implements View.OnClickListener {
    public static VisitorsselfFragment fragment;
    private Spinner spn_shop, spn_tanzhen;
    private ArrayList<String> list_shop = new ArrayList<>();
    private ArrayList<String> list_tanzhen = new ArrayList<>();
    private ArrayAdapter<String> adp_shop;
    private ArrayAdapter<String> adp_tanzhen;
    private LinearLayout line_date;
    private HashMap<String, String> hm_store = new HashMap<String, String>();
    private HashMap<String, String> hm_probe = new HashMap<String, String>();
    private String beginTime, endTime;
    private String microprobeId;
    private int storeId = 0;
    private ListView xlistview;
    private VisitorsselfAdapter adapter;
    private HashMap<String, String> hm_visitors;
    private ArrayList<HashMap<String, String>> list_visitors = new ArrayList<>();
    private int pageNum = 1;
    private SharedPreferences share;
    private List<String> list_brands = new ArrayList<>();
    private List<String> list_model = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;
    private boolean shop = true;
    private boolean tanzhen = true;
    private LinearLayout line_page;
    private Visitorsself v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visitorsself, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        fragment = this;
        initView();
    }

    //清空所有数据
    public void clearAllData() {
        try {
            hm_visitors.clear();
            list_visitors.clear();
            pageNum = 1;
            list_brands.clear();
            list_model.clear();
            v = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //当滑到当前碎片时调用该方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (ClickUtils.isFastClick()) {//防止切换过快导致报错
                requestNT();
            }
        }
    }

    public void requestNT() {
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
    }

    public void initView() {
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = getView().findViewById(R.id.refreshLayout);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        //初始化接口没有的数据
        list_shop.clear();
        list_tanzhen.clear();
        hm_store.clear();
        hm_probe.clear();
        list_shop.add("全部门店");
        list_tanzhen.add("全部探针");
        hm_store.put("全部门店", "0");
        hm_probe.put("全部探针", "0");
        getSiteOption();
        beginTime = gettodayDate();
        endTime = beginTime;

        xlistview = getView().findViewById(R.id.xlistview);
        adapter = new VisitorsselfAdapter(getContext(), list_visitors);
        xlistview.setAdapter(adapter);
        xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VisitorsselfActivity.class);
                intent.putExtra("mac", list_visitors.get(position).get("mac"));
                intent.putExtra("brands", list_brands.get(position));
                intent.putExtra("model", list_model.get(position));
                startActivity(intent);
            }
        });

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getData(pageNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                try {
                    if ((v.getTotal() - (pageNum - 1) * 10) > 0) {
                        getData(pageNum);
                    } else {
                        ToastUtils.showShort(getActivity(), "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        spn_shop = getView().findViewById(R.id.spn_shop);
        spn_tanzhen = getView().findViewById(R.id.spn_tanzhen);
        line_date = getView().findViewById(R.id.line_date);
        line_date.setOnClickListener(this);

        getData(1);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adp_shop = new SpinnerArrayAdapter(getActivity(), list_shop);
                    spn_shop.setAdapter(adp_shop);
                    spn_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (shop) {//spinner初始化的时候不执行点击事件
                                shop = false;
                            } else {
                                storeId = Integer.parseInt(hm_store.get(list_shop.get(position)));
                                clearAllData();
                                getData(pageNum);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 2:
                    adp_tanzhen = new SpinnerArrayAdapter(getActivity(), list_tanzhen);
                    spn_tanzhen.setAdapter(adp_tanzhen);
                    spn_tanzhen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (tanzhen) {//spinner初始化的时候不执行点击事件
                                tanzhen = false;
                            } else {
                                microprobeId = hm_probe.get(list_tanzhen.get(position));
                                clearAllData();
                                getData(pageNum);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 3:
                    try {
                        Visitorsself visitorsself = (Visitorsself) msg.obj;
                        if (visitorsself.getRows().size() == 0) {//如果没数据就提示
                            line_page.setVisibility(View.VISIBLE);
                            ToastUtils.showShort(getActivity(), "无数据");
                        } else {
                            for (int i = 0; i < visitorsself.getRows().size(); i++) {
                                hm_visitors = new HashMap<String, String>();
                                hm_visitors.put("content1", visitorsself.getRows().get(i).getVisittime());
                                hm_visitors.put("content2", visitorsself.getRows().get(i).getBrand());
                                list_brands.add(visitorsself.getRows().get(i).getBrand());
                                hm_visitors.put("content3", visitorsself.getRows().get(i).getModel());
                                list_model.add(visitorsself.getRows().get(i).getModel());
                                hm_visitors.put("content4", getPosion(visitorsself.getRows().get(i).getRssi()));
                                hm_visitors.put("mac", visitorsself.getRows().get(i).getMac());
                                list_visitors.add(hm_visitors);
                            }
                            pageNum++;
                            line_page.setVisibility(View.GONE);
                        }
                        smartRefreshLayout.finishRefresh(0);//停止刷新
                        smartRefreshLayout.finishLoadmore(0);//停止加载
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    line_page.setVisibility(View.VISIBLE);
                    smartRefreshLayout.finishRefresh(0);//停止刷新
                    smartRefreshLayout.finishLoadmore(0);//停止加载
                    ToastUtils.showShort(getActivity(), "无数据");
                    break;
                case 5:
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + msg.obj.toString())));//拨打电话
                    break;
                case 6:
                    ToastUtils.showShort(getActivity(), msg.obj.toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void getData(final int page) {
        //获取访客信息列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("starttime", beginTime);
                    jsonObject.put("endtime", endTime);
                    jsonObject.put("page", page);
                    jsonObject.put("rows", 10);
                    jsonObject.put("storeId", storeId);
                    jsonObject.put("sort", "visittime");
                    jsonObject.put("order", "desc");
                    if (storeId != 0) {//如果storeId为0就不用传参数macs了
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(microprobeId);
                        jsonObject.put("macs", jsonArray);
                    }
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "deviceVisit/list.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                Gson gson = new Gson();
                                v = gson.fromJson(response.body().string(), Visitorsself.class);
                                if (v != null) {
                                    Message message = Message.obtain();
                                    message.what = 3;
                                    message.obj = v;
                                    handler.sendMessage(message);//通知刷新适配器
                                }
                            } catch (Exception e) {
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

    public String getPosion(int rssi) {
        if (rssi > 99) {
            return "'大于90米'";
        }
        if (rssi < 56) {
            return "小于1米";
        }
        double[] domain = {1.47, 1.62, 1.78, 1.96, 2.15, 2.37, 2.61, 2.87, 3.16, 3.48, 3.83,
                4.22, 4.64, 5.11, 5.62, 6.19, 6.81, 7.50, 8.25, 9.09, 10.00, 11.01, 12.12,
                13.34, 14.68, 16.16, 17.78, 19.57, 21.54, 23.71, 26.10, 28.73, 31.62,
                34.81, 38.31, 42.17, 46.42, 51.09, 56.23, 61.90, 68.13, 74.99, 82.54, 90.85};
        double[] range = {56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73,
                74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 96, 98, 99};
        for (int i = 0; i < range.length; i++) {
            if (range[i] <= rssi && rssi <= range[i + 1]) {
                return domain[i] + "米至" + domain[i + 1] + "米";
            }
        }
        return "未知";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_date:
                //显示日期选择器
                TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        beginTime = getTime(date);
                        endTime = beginTime;
                        clearAllData();
                        getData(pageNum);
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();

                pvTime.show();
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        clearAllData();
                        getData(pageNum);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }
}
