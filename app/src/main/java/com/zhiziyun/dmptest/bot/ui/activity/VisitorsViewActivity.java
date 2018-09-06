package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.SpinnerArrayAdapter;
import com.zhiziyun.dmptest.bot.entity.PieDataEntity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.Token;
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
 * Created by Administrator on 2018/8/30.
 */

public class VisitorsViewActivity extends BaseActivity implements View.OnClickListener {
    public static VisitorsViewActivity visitorsViewActivity;
    private Spinner spn_shop, spn_tanzhen;
    private ArrayList<String> list_shop = new ArrayList<>();
    private ArrayList<String> list_tanzhen = new ArrayList<>();
    private ArrayAdapter<String> adp_shop;
    private ArrayAdapter<String> adp_tanzhen;
    private LinearLayout line_date;
    //这里的颜色0xFF前面的四位是固定的，后面六位才是颜色的值
    private int[] mColors = {0xFFDD634C, 0xFF84BAD8, 0xFF28556C, 0xFFCDFC99, 0xFFCA5A89, 0xFFF8D195, 0xFFF0919E,
            0xFFA1E8FD};
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
    public ArrayList<String> list_price = new ArrayList<>();
    public ArrayList<String> list_sex = new ArrayList<>();
    public ArrayList<String> list_activity = new ArrayList<>();
    public ArrayList<String> list_age = new ArrayList<>();
    private TextView tv_age, tv_sex, tv_mpb, tv_mpm, tv_mpp, tv_active;
    private SharedPreferences share;
    private boolean shop = true;
    private boolean tanzhen = true;
    private SmartRefreshLayout smartRefreshLayout;
    private LinearLayout line_page;
    private int m_Position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitors_view);
        initView();
    }

    public void requestNT() {
        list_shop.clear();
        list_tanzhen.clear();
        hm_store.clear();
        hm_probe.clear();
        //初始化接口没有的数据
        list_shop.add("全部门店");
        list_tanzhen.add("全部盒子");
        hm_store.put("全部门店", "0");
        hm_probe.put("全部盒子", "0");
        getSiteOption();
    }

    private void initView() {
        visitorsViewActivity = this;
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        findViewById(R.id.tv_back).setOnClickListener(this);
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = findViewById(R.id.refreshLayout);
        smartRefreshLayout.setEnableLoadmore(false);//屏蔽掉上拉加载的效果

        //初始化接口没有的数据
        list_shop.clear();
        list_tanzhen.clear();
        hm_store.clear();
        hm_probe.clear();
        list_shop.add("全部门店");
        list_tanzhen.add("全部盒子");
        hm_store.put("全部门店", "0");
        hm_probe.put("全部盒子", "0");
        getSiteOption();
        beginTime = gettodayDate();
        endTime = beginTime;

        tv_age = findViewById(R.id.tv_age);
        tv_sex = findViewById(R.id.tv_sex);
        tv_mpb = findViewById(R.id.tv_mpb);
        tv_mpm = findViewById(R.id.tv_mpm);
        tv_mpp = findViewById(R.id.tv_mpp);
        tv_active = findViewById(R.id.tv_active);

        spn_shop = findViewById(R.id.spn_shop);
        spn_tanzhen = findViewById(R.id.spn_tanzhen);
        line_date = findViewById(R.id.line_date);
        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        line_date.setOnClickListener(this);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    getTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getTable();
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
                                    try {
                                        // 获得key
                                        String key = store.next();
                                        hm_store.put(key, json_store.getString(key));
                                        list_shop.add(key);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                handler.sendEmptyMessage(1);

                                JSONObject json_probe = new JSONObject(json_obj.get("probe").toString());
                                Iterator<String> probe = json_probe.keys();
                                while (probe.hasNext()) {
                                    try {
                                        // 获得key
                                        String key = probe.next();
                                        hm_probe.put(key, json_probe.getString(key));
                                        list_tanzhen.add(key);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
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
                    try {
                        adp_shop = new SpinnerArrayAdapter(VisitorsViewActivity.this, list_shop);
                        spn_shop.setAdapter(adp_shop);
                        spn_shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (shop) {//spinner初始化的时候不执行点击事件
                                    shop = false;
                                } else {
                                    storeId = Integer.parseInt(hm_store.get(list_shop.get(position)));
                                    getTable();
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
                        adp_tanzhen = new SpinnerArrayAdapter(VisitorsViewActivity.this, list_tanzhen);
                        spn_tanzhen.setAdapter(adp_tanzhen);
                        spn_tanzhen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (tanzhen) {//spinner初始化的时候不执行点击事件
                                    tanzhen = false;
                                } else {
                                    String str = hm_probe.get(list_tanzhen.get(position));
                                    if (str.equals("0")) {
                                        microprobeId = 0;
                                    } else {
                                        microprobeId = Integer.parseInt(str.substring(str.indexOf("_") + 1));
                                    }
                                    getTable();
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
                        //饼状图-年龄
                        PieChart_age pieChart = findViewById(R.id.chart_age);
                        List<PieDataEntity> dataEntities = new ArrayList<>();
                        for (int i = 0; i < list_age.size(); i++) {
                            PieDataEntity entity = new PieDataEntity("name" + i, Integer.parseInt(hm_age.get(list_age.get(i))), mColors[i]);
                            dataEntities.add(entity);
                        }
                        pieChart.setDataList(dataEntities);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        //饼状图-性别
                        PieChart_sex pieChart_sex = findViewById(R.id.chart_sex);
                        List<PieDataEntity> dataEntitiessex = new ArrayList<>();
                        for (int i = 0; i < list_sex.size(); i++) {
                            PieDataEntity entity = new PieDataEntity("name" + i, Integer.parseInt(hm_gender.get(list_sex.get(i))), mColors[i]);
                            dataEntitiessex.add(entity);
                        }
                        pieChart_sex.setDataList(dataEntitiessex);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    try {
                        //饼状图-手机品牌
                        PieChart_mpb pieChart_mpb = findViewById(R.id.chart_mpb);
                        List<PieDataEntity> dataEntitiesmpb = new ArrayList<>();
                        for (int i = 0; i < list_brand.size(); i++) {
                            PieDataEntity entity = new PieDataEntity("name" + i, Integer.parseInt(hm_brand.get(list_brand.get(i))), mColors[i]);
                            dataEntitiesmpb.add(entity);
                        }
                        pieChart_mpb.setDataList(dataEntitiesmpb);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    try {
                        //饼状图-手机型号
                        PieChart_mpm pieChart_mpm = findViewById(R.id.chart_mpm);
                        List<PieDataEntity> dataEntitiesmpm = new ArrayList<>();
                        Log.i("infos", list_model.toString());
                        Log.i("infos", hm_model.toString());
                        for (int i = 0; i < list_model.size(); i++) {
                            PieDataEntity entity = new PieDataEntity("name" + i, Integer.parseInt(hm_model.get(list_model.get(i))), mColors[i]);
                            dataEntitiesmpm.add(entity);
                        }
                        pieChart_mpm.setDataList(dataEntitiesmpm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    try {
                        //饼状图-手机价格
                        PieChart_mpp pieChart_mpp = findViewById(R.id.chart_mpp);
                        List<PieDataEntity> dataEntitiesmpp = new ArrayList<>();
                        for (int i = 0; i < list_price.size(); i++) {
                            PieDataEntity entity = new PieDataEntity("name" + i, Integer.parseInt(hm_price.get(list_price.get(i))), mColors[i]);
                            dataEntitiesmpp.add(entity);
                        }
                        pieChart_mpp.setDataList(dataEntitiesmpp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 8:
                    try {
                        //饼状图-活跃度
                        PieChart_active pieChart_active = findViewById(R.id.chart_active);
                        List<PieDataEntity> dataEntitiesactive = new ArrayList<>();
                        for (int i = 0; i < list_activity.size(); i++) {
                            PieDataEntity entity = new PieDataEntity("name" + i, Integer.parseInt(hm_activity.get(list_activity.get(i))), mColors[i]);
                            dataEntitiesactive.add(entity);
                        }
                        pieChart_active.setDataList(dataEntitiesactive);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //显示表体布局
                    tv_age.setVisibility(View.VISIBLE);
                    tv_sex.setVisibility(View.VISIBLE);
                    tv_mpb.setVisibility(View.VISIBLE);
                    tv_mpm.setVisibility(View.VISIBLE);
                    tv_mpp.setVisibility(View.VISIBLE);
                    tv_active.setVisibility(View.VISIBLE);
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    line_page.setVisibility(View.GONE);
                    break;
                case 9:
                    try {
                        line_page.setVisibility(View.VISIBLE);
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void getTable() {
        //访客信息报表
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
                    json.put("limit", 7);
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
                            .url(BaseUrl.BaseTest + "report/visitorInfo")
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
                                String str = response.body().string();
                                Log.i("infos", str);
                                //年龄分布
                                JSONObject jsonObject = new JSONObject(str);
                                JSONObject json_obj = new JSONObject(jsonObject.get("obj").toString());
                                JSONObject json_store = new JSONObject(json_obj.get("age").toString());
                                //如果没有数据就不执行
                                if (json_store.names() == null || json_store.names().isNull(0)) {
                                    handler.sendEmptyMessage(9);
                                } else {
                                    Iterator<String> age = json_store.keys();
                                    list_age.clear();
                                    while (age.hasNext()) {
                                        try {
                                            // 获得key
                                            String key = age.next();
                                            hm_age.put(key, json_store.getString(key));
                                            list_age.add(key);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    handler.sendEmptyMessage(3);
                                    //性别分布
                                    JSONObject json_gender = new JSONObject(json_obj.get("gender").toString());
                                    Iterator<String> gender = json_gender.keys();
                                    list_sex.clear();
                                    while (gender.hasNext()) {
                                        try {
                                            // 获得key
                                            String key = gender.next();
                                            hm_gender.put(key, json_gender.getString(key));
                                            list_sex.add(key);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    handler.sendEmptyMessage(4);
                                    //手机品牌分布
                                    JSONObject json_model = new JSONObject(json_obj.get("model").toString());
                                    list_model.clear();
                                    Iterator<String> model = json_model.keys();
                                    while (model.hasNext()) {
                                        try {
                                            // 获得key
                                            String key = model.next();
                                            if (!key.equals("UNKNOWN")) {
                                                hm_model.put(key, json_model.getString(key));
                                                list_model.add(key);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    handler.sendEmptyMessage(5);
                                    //手机型号分布
                                    JSONObject json_brand = new JSONObject(json_obj.get("brand").toString());
                                    Iterator<String> brand = json_brand.keys();
                                    list_brand.clear();
                                    while (brand.hasNext()) {
                                        try {
                                            // 获得key
                                            String key = brand.next();
                                            if (!key.equals("UNKNOWN")) {
                                                hm_brand.put(key, json_brand.getString(key));
                                                list_brand.add(key);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    handler.sendEmptyMessage(6);
                                    //手机价格分布
                                    JSONObject json_price = new JSONObject(json_obj.get("price").toString());
                                    Iterator<String> price = json_price.keys();
                                    list_price.clear();
                                    while (price.hasNext()) {
                                        try {
                                            // 获得key
                                            String key = price.next();
                                            hm_price.put(key, json_price.getString(key));
                                            list_price.add(key);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    handler.sendEmptyMessage(7);
                                    //活跃度分布
                                    JSONObject json_activity = new JSONObject(json_obj.get("activity").toString());
                                    Iterator<String> activity = json_activity.keys();
                                    list_activity.clear();
                                    while (activity.hasNext()) {
                                        try {
                                            // 获得key
                                            String key = activity.next();
                                            if (!key.equals("UNKNOWN")) {
                                                hm_activity.put(key, json_activity.getString(key));
                                                list_activity.add(key);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
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
            case R.id.tv_back:
                toFinish();
                finish();
                break;
            case R.id.line_date:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String textString = String.format("%d-%d-%d %d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        int index = textString.indexOf(" ");
                        beginTime = date(textString.substring(0, index));
                        endTime = date(textString.substring(index + 1, textString.length()));
                        getTable();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        getTable();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 将例如原来的日期2018-1-1改成了2018-01-01，后台只能识别后者，因此自己写了个方法，
     * 先把日期拆分成年、月、日，然后判断月和日，如果是单数就在前添加一个0，是双数就不变，
     * 这里用到了二元表达式来判断
     */
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

    @Override
    public void onBackPressed() {
        toFinish();
        finish();
    }

    public void toFinish() {
        try {
            //网络请求前先清空数据
            hm_age.clear();
            list_age.clear();
            hm_gender.clear();
            list_sex.clear();
            hm_model.clear();
            list_model.clear();
            hm_brand.clear();
            list_brand.clear();
            hm_price.clear();
            list_price.clear();
            hm_activity.clear();
            list_activity.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
