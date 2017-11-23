package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.ChartEntity;
import com.zhiziyun.dmptest.bot.entity.PieDataEntity;
import com.zhiziyun.dmptest.bot.http.DESCoder;
import com.zhiziyun.dmptest.bot.ui.activity.AddStoryActivity;
import com.zhiziyun.dmptest.bot.ui.activity.PassengerFlowAnalysisActivity;
import com.zhiziyun.dmptest.bot.widget.HollowPieNewChart;
import com.zhiziyun.dmptest.bot.widget.LineChart;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * 首页
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {
    private SwipeRefreshLayout srl;
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private TextView tv_companyname, tv_story, tv_tanzhen, tv_person, tv_newguest, tv_oldguest;
    private ImageView iv_addstory;
    private RelativeLayout rl_today_people;
    private boolean b_flag1 = false;
    private boolean b_flag2 = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        initView();
    }

    public void initView() {
        getTableInfo();
        tv_companyname = getView().findViewById(R.id.tv_companyname);
        iv_addstory = getView().findViewById(R.id.iv_addstory);
        iv_addstory.setOnClickListener(this);
        rl_today_people = getView().findViewById(R.id.rl_today_people);
        rl_today_people.setOnClickListener(this);
        tv_story = getView().findViewById(R.id.tv_story);
        tv_tanzhen = getView().findViewById(R.id.tv_tanzhen);
        tv_person = getView().findViewById(R.id.tv_person);
        tv_newguest = getView().findViewById(R.id.tv_newguest);
        tv_oldguest = getView().findViewById(R.id.tv_oldguest);

        getData();

        //下拉刷新
        srl = getView().findViewById(R.id.srl);
        srl.setRefreshing(false);//禁止一开始就刷新
        srl.setProgressBackgroundColorSchemeResource(android.R.color.white);
        srl.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                if (b_flag1 == true && b_flag2 == true) {//如果所有接口都拿到数据才停止刷新
                    // 加载完数据设置为不刷新状态，将下拉进度收起来
                    srl.setRefreshing(false);
                }
            }
        });

        //线性图
        LineChart lineChart = getView().findViewById(R.id.linechart);
        List<ChartEntity> data = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            data.add(new ChartEntity(getWeek(i), (float) (20000)));
        }
        lineChart.setData(data);
    }

    private String getWeek(int i) {
        String week = null;
        switch (i) {
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
            case 7:
                week = "星期日";
                break;
        }
        return week;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addstory:
                startActivity(new Intent(getActivity(), AddStoryActivity.class));
                break;
            case R.id.rl_today_people:
                startActivity(new Intent(getActivity(), PassengerFlowAnalysisActivity.class));
                break;
        }
    }

    String token;

    public void getData() {
        //token加密
        try {
            token = DESCoder.encrypt("1" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
            Log.i("info", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取门店数和探针数
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
                            .url("http://dmptest.zhiziyun.com/api/v1/report/siteStatistics.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            b_flag1=true;
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                JSONObject json = new JSONObject(jsonObject.get("obj").toString());
                                Message message = new Message();
                                message.what = 1;
                                Bundle bundle = new Bundle();
                                bundle.putString("store", json.get("store").toString());
                                bundle.putString("probe", json.get("probe").toString());
                                message.setData(bundle);
                                myHandler.sendMessage(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("erro", e.toString());
                }
            }
        }).start();

        //今日到店统计
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", "0zoTLi29XRgq");
                    Log.i("json",json.toString());
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(token, "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url("http://dmptest.zhiziyun.com/api/v1/report/todayStatistics.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            b_flag2=true;

                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                JSONObject json = new JSONObject(jsonObject.get("obj").toString());
                                Message message = new Message();
                                message.what = 2;
                                Bundle bundle = new Bundle();
                                bundle.putString("all", json.get("all").toString());
                                bundle.putString("new", json.get("new").toString());
                                bundle.putString("old", json.get("old").toString());
                                message.setData(bundle);
                                myHandler.sendMessage(message);
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

    public void getTableInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //token加密
                    String token = DESCoder.encrypt("1" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", "0zoTLi29XRgq");
                    jsonObject.put("beginTime", getDate());
                    jsonObject.put("endTime", getPastDate(6));
                    jsonObject.put("microprobeId", 0);
                    jsonObject.put("storeId", 0);
                    OkHttpClient client = new OkHttpClient();
                    String url = "agentId=1&token=" + URLEncoder.encode(token, "utf-8") + "&json=" + jsonObject.toString();
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url("http://dmptest.zhiziyun.com/api/v1/report/visitDay.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    Response response = null;
                    response = client.newCall(request).execute();
                    Log.i("info", response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());
    }

    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    tv_story.setText(bundle.get("store").toString() + "个");
                    tv_tanzhen.setText(bundle.get("probe").toString() + "个");
                    break;
                case 2:
                    Bundle bundle1 = msg.getData();
                    tv_person.setText(bundle1.get("all").toString());
                    tv_newguest.setText(bundle1.get("new").toString());
                    tv_oldguest.setText(bundle1.get("old").toString());
                    drawpiechart(Integer.parseInt(bundle1.get("new").toString()), Integer.parseInt(bundle1.get("old").toString()));
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //绘制饼状图
    public void drawpiechart(int a, int b) {
        //饼状图
        HollowPieNewChart pieChart = getView().findViewById(R.id.chart);
        List<PieDataEntity> dataEntities = new ArrayList<>();
        PieDataEntity entity1 = new PieDataEntity("name" + 0, a, mColors[0]);
        dataEntities.add(entity1);
        PieDataEntity entity2 = new PieDataEntity("name" + 1, b, mColors[1]);
        dataEntities.add(entity2);
        pieChart.setDataList(dataEntities);

        pieChart.setOnItemPieClickListener(new HollowPieNewChart.OnItemPieClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    Toast.makeText(getActivity(), "新客", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getActivity(), "老客", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
