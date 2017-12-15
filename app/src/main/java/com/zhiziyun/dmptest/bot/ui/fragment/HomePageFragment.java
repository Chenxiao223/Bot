package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.GetHead;
import com.zhiziyun.dmptest.bot.entity.HomePage;
import com.zhiziyun.dmptest.bot.ui.activity.AddStoryActivity;
import com.zhiziyun.dmptest.bot.ui.activity.PassengerFlowAnalysisActivity;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 首页
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {
    private SwipeRefreshLayout srl;
    private TextView tv_companyname, tv_story, tv_tanzhen, tv_person, tv_newguest, tv_oldguest, tv_new, tv_old;
    private ImageView iv_addstory, iv_head;
    private RelativeLayout rl_today_people;
    private PieChartView pie_chart;//饼形图控件
    private PieChartData pieChardata;//数据
    List<SliceValue> values = new ArrayList<SliceValue>();
    //折线图
    private LineChartView chartView;
    private LineChartData lineData;
    private HomePage page;
    private MyDialog dialog;
    private SharedPreferences share;

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
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        getHead();//获取头像
        //折线图
        chartView = getView().findViewById(R.id.linechart);
        tv_new = getView().findViewById(R.id.tv_new);
        tv_old = getView().findViewById(R.id.tv_old);
        tv_companyname = getView().findViewById(R.id.tv_companyname);
        tv_companyname.setText(share.getString("company", "无数据"));
        iv_addstory = getView().findViewById(R.id.iv_addstory);
        iv_addstory.setOnClickListener(this);
        iv_head = getView().findViewById(R.id.iv_head);
        rl_today_people = getView().findViewById(R.id.rl_today_people);
        rl_today_people.setOnClickListener(this);
        tv_story = getView().findViewById(R.id.tv_story);
        tv_tanzhen = getView().findViewById(R.id.tv_tanzhen);
        tv_person = getView().findViewById(R.id.tv_person);
        tv_newguest = getView().findViewById(R.id.tv_newguest);
        tv_oldguest = getView().findViewById(R.id.tv_oldguest);
        getTableInfo();
        getData();

        srl = getView().findViewById(R.id.srl);
        srl.setRefreshing(false);//禁止一开始就刷新
        srl.setProgressBackgroundColorSchemeResource(android.R.color.white);
        srl.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                getHead();
                //清空状态标志位
                values.clear();
                getData();
            }
        });

        //饼状图
        pie_chart = getView().findViewById(R.id.pie_chart);
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

    public void getData() {
        //加载动画
        dialog = MyDialog.showDialog(getActivity());
        dialog.show();
        //获取门店数和探针数
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
                                myHandler.sendEmptyMessage(5);//加载完后停止刷新
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

        //今日到店统计
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
                    Log.i("jsons", Token.gettoken());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("beginTime", getPastDate(6));
                    jsonObject.put("endTime", getDate());
                    jsonObject.put("microprobeId", 0);
                    jsonObject.put("storeId", 0);
                    OkHttpClient client = new OkHttpClient();
                    String url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
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
                            page = gson.fromJson(response.body().string(), HomePage.class);
                            if (page != null) {
                                if (page.getRows().size() == 0) {//如果没数据就提示
                                    myHandler.sendEmptyMessage(4);
                                } else {
                                    myHandler.sendEmptyMessage(3);
                                }
                            } else {
                                myHandler.sendEmptyMessage(4);
                            }
                        }
                    });
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
                    tv_new.setText("  (" + computations(Float.parseFloat(bundle1.get("all").toString()), Float.parseFloat(bundle1.get("new").toString())) + ")");
                    tv_old.setText("  (" + computations(Float.parseFloat(bundle1.get("all").toString()), Float.parseFloat(bundle1.get("old").toString())) + ")");
                    setPieChartData(Float.parseFloat(bundle1.get("new").toString()), Float.parseFloat(bundle1.get("old").toString()));
                    initPieChart();
                    dialog.dismiss();//关闭加载动画
                    break;
                case 3:
                    generateInitialLineData(page);
                    break;
                case 4:
                    Toast.makeText(getActivity(), "无数据", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();//关闭加载动画
                    break;
                case 5:
                    srl.setRefreshing(false);
                    dialog.dismiss();//关闭加载动画
                    break;
            }
            super.handleMessage(msg);
        }
    };


    /**
     * 获取数据
     */
    private void setPieChartData(float a, float b) {
        SliceValue sliceValue = new SliceValue(a, Color.parseColor("#2bc208"));
        values.add(sliceValue);
        SliceValue sliceValue2 = new SliceValue(b, Color.parseColor("#44a8c8"));
        values.add(sliceValue2);
    }

    /**
     * 初始化饼状图
     */
    private void initPieChart() {
        pieChardata = new PieChartData();
        pieChardata.setHasLabels(true);//显示表情
        pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChardata.setHasCenterCircle(true);//是否是环形显示
        pieChardata.setValues(values);//填充数据
        pieChardata.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
        pieChardata.setCenterCircleScale(0.5f);//设置环形的大小级别
        pie_chart.setPieChartData(pieChardata);
        pie_chart.setValueSelectionEnabled(false);//选择饼图某一块变大
        pie_chart.setAlpha(0.9f);//设置透明度
        pie_chart.setCircleFillRatio(1f);//设置饼图大小
    }


    //计算百分比
    public String computations(float sum, float num) {
        return String.format("%.2f", num * 100 / sum) + "%";
    }

    //折线图
    private void generateInitialLineData(HomePage homePage) {
        int numValues = homePage.getRows().size();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        List<PointValue> values2 = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, Float.parseFloat(homePage.getRows().get(i).getUv().toString())));//进店客流
            values2.add(new PointValue(i, Float.parseFloat(homePage.getRows().get(i).getTotalUV())));//环境客流
            axisValues.add(new AxisValue(i).setLabel(homePage.getRows().get(i).getStatDate().substring(5)));//为缩短字段去掉年份2017去掉
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

    //获取头像接口
    public void getHead() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
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
                            .url("http://test.zhiziyun.com/api-service/advertiserApp/getLogoImg")
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
                            GetHead getHead = gson.fromJson(response.body().string(), GetHead.class);
                            if (getHead != null) {
                                if (getHead.getResponse().getLogoUrl() != null) {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = getHead.getResponse().getLogoUrl();
                                    handler.sendMessage(msg);
                                } else {
                                    handler.sendEmptyMessage(2);
                                }
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setHead(String url) {
        Glide.with(getActivity()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                iv_head.setImageBitmap(createCircleImage(resource));
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setHead(String.valueOf(msg.obj));
                    break;
                case 2:
                    ToastUtils.showShort(getActivity(), "头像为空，请上传头像");
                    break;
            }
        }
    };

    /**
     * 将图片剪裁为圆形
     */
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
