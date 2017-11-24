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

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.PieDataEntity;
import com.zhiziyun.dmptest.bot.http.DESCoder;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.widget.PieChart_active;
import com.zhiziyun.dmptest.bot.widget.PieChart_age;
import com.zhiziyun.dmptest.bot.widget.PieChart_mpb;
import com.zhiziyun.dmptest.bot.widget.PieChart_mpm;
import com.zhiziyun.dmptest.bot.widget.PieChart_mpp;
import com.zhiziyun.dmptest.bot.widget.PieChart_sex;

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
 * 画像
 */
public class VisitorsViewFragment extends Fragment implements View.OnClickListener {
    public static VisitorsViewFragment visitorsViewFragment;
    private Spinner spn_shop, spn_tanzhen;
    private List<String> list_shop = new ArrayList<>();
    private List<String> list_tanzhen = new ArrayList<>();
    private ArrayAdapter<String> adp_shop;
    private ArrayAdapter<String> adp_tanzhen;
    private LinearLayout line_date;
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00, 0xFF800000};
    private String token;
    private HashMap<String, String> hm_store = new HashMap<String, String>();
    private HashMap<String, String> hm_probe = new HashMap<String, String>();
    private String beginTime, endTime;
    private int microprobeId = 0;
    private int storeId = 0;
    private HashMap<String, String> hm_age = new HashMap<>();//年龄分布
    private HashMap<String, String> hm_gender = new HashMap<>();//性别分布
    private HashMap<String, String> hm_model = new HashMap<>();//手机品牌分布
    private HashMap<String, String> hm_brand = new HashMap<>();//手机型号分布
    private HashMap<String, String> hm_price = new HashMap<>();//手机价格分布
    private HashMap<String, String> hm_activity = new HashMap<>();//活跃度分布分布
    public ArrayList<String> list_model = new ArrayList<>();
    public ArrayList<String> list_brand = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visitors_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        visitorsViewFragment = this;
        initView();
    }

    //当滑到当前碎片时调用该方法
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
        } else {//清空数据
            list_shop.clear();
            list_tanzhen.clear();
            token = null;
            hm_store.clear();
            hm_probe.clear();
            endTime = null;
            microprobeId = 0;
            storeId = 0;
            hm_age.clear();
            hm_gender.clear();
            hm_model.clear();
            hm_brand.clear();
            hm_price.clear();
            hm_activity.clear();
            list_model.clear();
            list_brand.clear();
        }
    }

    public void initView() {
        spn_shop = getView().findViewById(R.id.spn_shop);
        spn_tanzhen = getView().findViewById(R.id.spn_tanzhen);
        line_date = getView().findViewById(R.id.line_date);
        line_date.setOnClickListener(this);

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
//                                Log.i("key",response.body().string());
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
                    spn_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            storeId = Integer.parseInt(hm_store.get(list_shop.get(position)));
                            getTable();
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
                            microprobeId = Integer.parseInt(hm_probe.get(list_tanzhen.get(position)));
                            getTable();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 3:
                    //饼状图-年龄
                    PieChart_age pieChart = getView().findViewById(R.id.chart_age);
                    List<PieDataEntity> dataEntities = new ArrayList<>();
                    PieDataEntity entity1 = new PieDataEntity("name" + 0, Integer.parseInt(hm_age.get("36-45岁")), mColors[0]);
                    dataEntities.add(entity1);
                    PieDataEntity entity2 = new PieDataEntity("name" + 1, Integer.parseInt(hm_age.get("26-35岁")), mColors[1]);
                    dataEntities.add(entity2);
                    PieDataEntity entity3 = new PieDataEntity("name" + 2, Integer.parseInt(hm_age.get("46-55岁")), mColors[2]);
                    dataEntities.add(entity3);
                    PieDataEntity entity4 = new PieDataEntity("name" + 3, Integer.parseInt(hm_age.get("55岁以上")), mColors[3]);
                    dataEntities.add(entity4);
                    PieDataEntity entity5 = new PieDataEntity("name" + 4, Integer.parseInt(hm_age.get("19-25岁")), mColors[4]);
                    dataEntities.add(entity5);
                    pieChart.setDataList(dataEntities);

//                    pieChart.setOnItemPieClickListener(new PieChart_age.OnItemPieClickListener() {
//                        @Override
//                        public void onClick(int position) {
//                            if (position == 0) {
//                                Toast.makeText(getActivity(), "新客", Toast.LENGTH_SHORT).show();
//                            } else if (position == 1) {
//                                Toast.makeText(getActivity(), "老客", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                    break;
                case 4:
                    //饼状图-性别
                    PieChart_sex pieChart_sex = getView().findViewById(R.id.chart_sex);
                    List<PieDataEntity> dataEntitiessex = new ArrayList<>();
                    PieDataEntity entitysex1 = new PieDataEntity("name" + 0, Integer.parseInt(hm_gender.get("女")), mColors[0]);
                    dataEntitiessex.add(entitysex1);
                    PieDataEntity entitysex2 = new PieDataEntity("name" + 1, Integer.parseInt(hm_gender.get("男")), mColors[1]);
                    dataEntitiessex.add(entitysex2);
                    pieChart_sex.setDataList(dataEntitiessex);

                    break;
                case 5:
                    //饼状图-手机品牌
                    PieChart_mpb pieChart_mpb = getView().findViewById(R.id.chart_mpb);
                    List<PieDataEntity> dataEntitiesmpb = new ArrayList<>();
                    PieDataEntity entitympb1 = new PieDataEntity("name" + 0, Integer.parseInt(hm_brand.get(list_brand.get(0))), mColors[0]);
                    dataEntitiesmpb.add(entitympb1);
                    PieDataEntity entitympb2 = new PieDataEntity("name" + 1, Integer.parseInt(hm_brand.get(list_brand.get(1))), mColors[1]);
                    dataEntitiesmpb.add(entitympb2);
                    PieDataEntity entitympb3 = new PieDataEntity("name" + 3, Integer.parseInt(hm_brand.get(list_brand.get(2))), mColors[2]);
                    dataEntitiesmpb.add(entitympb3);
                    PieDataEntity entitympb4 = new PieDataEntity("name" + 4, Integer.parseInt(hm_brand.get(list_brand.get(3))), mColors[3]);
                    dataEntitiesmpb.add(entitympb4);
                    PieDataEntity entitympb5 = new PieDataEntity("name" + 5, Integer.parseInt(hm_brand.get(list_brand.get(4))), mColors[4]);
                    dataEntitiesmpb.add(entitympb5);
                    PieDataEntity entitympb6 = new PieDataEntity("name" + 6, Integer.parseInt(hm_brand.get(list_brand.get(5))), mColors[5]);
                    dataEntitiesmpb.add(entitympb6);
                    pieChart_mpb.setDataList(dataEntitiesmpb);
                    break;
                case 6:
                    //饼状图-手机型号
                    PieChart_mpm pieChart_mpm = getView().findViewById(R.id.chart_mpm);
                    List<PieDataEntity> dataEntitiesmpm = new ArrayList<>();
                    PieDataEntity entitympm1 = new PieDataEntity("name" + 0, Integer.parseInt(hm_model.get(list_model.get(0))), mColors[0]);
                    dataEntitiesmpm.add(entitympm1);
                    PieDataEntity entitympm2 = new PieDataEntity("name" + 1, Integer.parseInt(hm_model.get(list_model.get(1))), mColors[1]);
                    dataEntitiesmpm.add(entitympm2);
                    PieDataEntity entitympm3 = new PieDataEntity("name" + 3, Integer.parseInt(hm_model.get(list_model.get(2))), mColors[2]);
                    dataEntitiesmpm.add(entitympm3);
                    PieDataEntity entitympm4 = new PieDataEntity("name" + 4, Integer.parseInt(hm_model.get(list_model.get(3))), mColors[3]);
                    dataEntitiesmpm.add(entitympm4);
                    PieDataEntity entitympm5 = new PieDataEntity("name" + 5, Integer.parseInt(hm_model.get(list_model.get(4))), mColors[4]);
                    dataEntitiesmpm.add(entitympm5);
                    PieDataEntity entitympm6 = new PieDataEntity("name" + 6, Integer.parseInt(hm_model.get(list_model.get(5))), mColors[5]);
                    dataEntitiesmpm.add(entitympm6);
                    pieChart_mpm.setDataList(dataEntitiesmpm);
                    break;
                case 7:
                    //饼状图-手机价格
                    PieChart_mpp pieChart_mpp = getView().findViewById(R.id.chart_mpp);
                    List<PieDataEntity> dataEntitiesmpp = new ArrayList<>();
                    PieDataEntity entitympp1 = new PieDataEntity("name" + 0, Integer.parseInt(hm_price.get("1000-1999")), mColors[0]);
                    dataEntitiesmpp.add(entitympp1);
                    PieDataEntity entitympp2 = new PieDataEntity("name" + 1, Integer.parseInt(hm_price.get("4000及以上")), mColors[1]);
                    dataEntitiesmpp.add(entitympp2);
                    PieDataEntity entitympp3 = new PieDataEntity("name" + 2, Integer.parseInt(hm_price.get("500-999")), mColors[2]);
                    dataEntitiesmpp.add(entitympp3);
                    PieDataEntity entitympp4 = new PieDataEntity("name" + 3, Integer.parseInt(hm_price.get("2000-3999")), mColors[3]);
                    dataEntitiesmpp.add(entitympp4);
                    pieChart_mpp.setDataList(dataEntitiesmpp);
                    break;
                case 8:
                    //饼状图-活跃度
//                    Log.i("data", hm_activity.get("高活跃度客户"));
                    PieChart_active pieChart_active = getView().findViewById(R.id.chart_active);
                    List<PieDataEntity> dataEntitiesactive = new ArrayList<>();
                    PieDataEntity entityactive1 = new PieDataEntity("name" + 0, 1, mColors[0]);
                    dataEntitiesactive.add(entityactive1);
                    pieChart_active.setDataList(dataEntitiesactive);
                    break;
                case 9:
                    Toast.makeText(getActivity(), "查询无效", Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void getTable() {
        //token加密
        try {
            token = DESCoder.encrypt("1" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取图表信息
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
                    json.put("limit", 7);
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
                            .url("http://dmptest.zhiziyun.com/api/v1/report/visitorInfo.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
//                            Log.i("json",response.body().string());
                            try {
//                                //年龄分布
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                JSONObject json_obj = new JSONObject(jsonObject.get("obj").toString());
                                JSONObject json_store = new JSONObject(json_obj.get("age").toString());
                                //如果没有数据就不执行
                                if (json_store.names() == null || json_store.names().isNull(0)) {
                                    handler.sendEmptyMessage(9);
                                } else {
                                    Iterator<String> age = json_store.keys();
                                    while (age.hasNext()) {
                                        // 获得key
                                        String key = age.next();
                                        hm_age.put(key, json_store.getString(key));
                                    }
                                    handler.sendEmptyMessage(3);
                                    //性别分布
                                    JSONObject json_gender = new JSONObject(json_obj.get("gender").toString());
                                    Iterator<String> gender = json_gender.keys();
                                    while (gender.hasNext()) {
                                        // 获得key
                                        String key = gender.next();
                                        hm_gender.put(key, json_gender.getString(key));
                                    }
                                    handler.sendEmptyMessage(4);
                                    //手机品牌分布
                                    JSONObject json_model = new JSONObject(json_obj.get("model").toString());
                                    Iterator<String> model = json_model.keys();
                                    while (model.hasNext()) {
                                        // 获得key
                                        String key = model.next();
                                        if (!key.equals("UNKNOWN")) {
                                            hm_model.put(key, json_model.getString(key));
                                            list_model.add(key);
                                        }
                                    }
                                    handler.sendEmptyMessage(5);
                                    //手机型号分布
                                    JSONObject json_brand = new JSONObject(json_obj.get("brand").toString());
                                    Iterator<String> brand = json_brand.keys();
                                    while (brand.hasNext()) {
                                        // 获得key
                                        String key = brand.next();
                                        if (!key.equals("UNKNOWN")) {
                                            hm_brand.put(key, json_brand.getString(key));
                                            list_brand.add(key);
                                        }
                                    }
                                    handler.sendEmptyMessage(6);
                                    //手机价格分布
                                    JSONObject json_price = new JSONObject(json_obj.get("price").toString());
                                    Iterator<String> price = json_price.keys();
                                    while (price.hasNext()) {
                                        // 获得key
                                        String key = price.next();
                                        hm_price.put(key, json_price.getString(key));
                                    }
                                    handler.sendEmptyMessage(7);
                                    //活跃度分布
                                    JSONObject json_activity = new JSONObject(json_obj.get("activity").toString());
                                    Iterator<String> activity = json_activity.keys();
                                    while (activity.hasNext()) {
                                        // 获得key
                                        String key = activity.next();
                                        hm_activity.put(key, json_activity.getString(key));
                                    }
                                    handler.sendEmptyMessage(8);
                                }
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
                        getTable();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
        }
    }


}
