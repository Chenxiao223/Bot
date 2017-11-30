package com.zhiziyun.dmptest.bot.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
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
import com.zhiziyun.dmptest.bot.adapter.VisitorsselfAdapter;
import com.zhiziyun.dmptest.bot.entity.Visitorsself;
import com.zhiziyun.dmptest.bot.http.DESCoder;
import com.zhiziyun.dmptest.bot.ui.activity.VisitorsselfActivity;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.xListView.XListView;

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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/7/17 0017.
 * 访客
 */
public class VisitorsselfFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener {
    private Spinner spn_shop, spn_tanzhen;
    private List<String> list_shop = new ArrayList<>();
    private List<String> list_tanzhen = new ArrayList<>();
    private ArrayAdapter<String> adp_shop;
    private ArrayAdapter<String> adp_tanzhen;
    private LinearLayout line_date;
    private String token;
    private HashMap<String, String> hm_store = new HashMap<String, String>();
    private HashMap<String, String> hm_probe = new HashMap<String, String>();
    private String beginTime, endTime;
    private int microprobeId = 0;
    private int storeId = 0;
    private XListView xlistview;
    private VisitorsselfAdapter adapter;
    private HashMap<String, String> hm_visitors;
    private ArrayList<HashMap<String, String>> list_visitors = new ArrayList<>();
    private int pageNum = 1;
    private Visitorsself visitorsself;
    private MyDialog dialog;

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

    //清空所有数据
    public void clearAllData() {
        beginTime=gettodayDate();
        endTime=beginTime;

        list_shop.clear();
        list_tanzhen.clear();
        token = null;
        hm_store.clear();
        hm_probe.clear();
        microprobeId = 0;
        storeId = 0;
        pageNum = 1;
        list_visitors.clear();
    }

    public void initView() {
        //初始化接口没有的数据
        list_shop.add("全部门店");
        list_tanzhen.add("全部探针");
        hm_store.put("全部门店", "0");
        hm_probe.put("全部探针", "0");
        getSiteOption();
        beginTime=gettodayDate();
        endTime=beginTime;

        xlistview = getView().findViewById(R.id.xlistview);
        xlistview.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        adapter = new VisitorsselfAdapter(getContext(), list_visitors);
        xlistview.setAdapter(adapter);
        xlistview.setXListViewListener(this);
        xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), VisitorsselfActivity.class);
                intent.putExtra("mac",list_visitors.get(position).get("mac"));
                startActivity(intent);
            }
        });

        spn_shop = getView().findViewById(R.id.spn_shop);
        spn_tanzhen = getView().findViewById(R.id.spn_tanzhen);
        line_date = getView().findViewById(R.id.line_date);
        line_date.setOnClickListener(this);

        adp_shop = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_shop);
        adp_shop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adp_tanzhen = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_tanzhen);
        adp_tanzhen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_shop.setAdapter(adp_shop);
        spn_tanzhen.setAdapter(adp_tanzhen);

        getData(1);
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
                            list_visitors.clear();
                            getData(1);
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
                            list_visitors.clear();
                            getData(1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 3:
                    adapter.notifyDataSetChanged();
                    onLoad();//数据加载完后就停止刷新
                    dialog.dismiss();//关闭加载动画
                    break;
                case 4:
                    Toast.makeText(getActivity(), "无数据", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();//关闭加载动画
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void getData(final int page) {
        //加载动画
        dialog = MyDialog.showDialog(getActivity());
        dialog.show();
        //token加密
        try {
            token = DESCoder.encrypt("1" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取访客信息列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", "0zoTLi29XRgq");
                    jsonObject.put("starttime", beginTime);
                    jsonObject.put("endtime", endTime);
                    jsonObject.put("page", page);
                    jsonObject.put("rows", 10);
                    jsonObject.put("storeId", storeId);
                    if (storeId != 0) {//如果storeId为0就不用传参数macs了
                        jsonObject.put("macs", "[" + microprobeId + "]");
                    }
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(token, "utf-8") + "&json=" + jsonObject.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url("http://dmptest.zhiziyun.com/api/v1/deviceVisit/list.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Gson gson = new Gson();
                            visitorsself = gson.fromJson(response.body().string(), Visitorsself.class);
                            if (visitorsself.getRows().size()==0){//如果没数据就提示
                                handler.sendEmptyMessage(4);
                            }else {
                                for (int i = 0; i < visitorsself.getRows().size(); i++) {
                                    hm_visitors = new HashMap<String, String>();
                                    hm_visitors.put("content1", visitorsself.getRows().get(i).getVisittime());
                                    hm_visitors.put("content2", visitorsself.getRows().get(i).getBrand());
                                    hm_visitors.put("content3", visitorsself.getRows().get(i).getModel());
                                    hm_visitors.put("content4", getPosion(visitorsself.getRows().get(i).getRssi()));
                                    hm_visitors.put("content5", visitorsself.getRows().get(i).getGender());
                                    hm_visitors.put("mac", visitorsself.getRows().get(i).getMac());//这个值与适配器无关
                                    list_visitors.add(hm_visitors);
                                }
                                handler.sendEmptyMessage(3);//通知刷新适配器
                                pageNum++;
                            }
                        }
                    });
                } catch (JSONException e) {
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
                final Calendar c = Calendar.getInstance();
                //显示日期选择器
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        if (view.isShown()) { // 这里判断,防止点击事件执行两次
                            beginTime = (String) DateFormat.format("yyy-MM-dd", c);
                            endTime = beginTime;

                            list_visitors.clear();
                            getData(1);
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        hm_visitors.clear();
        clearAllData();
        getData(pageNum);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        if (pageNum < ((visitorsself.getTotal() / 10) + 3)) {
            getData(pageNum);
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

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }
}
