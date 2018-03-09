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
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
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
import com.zhiziyun.dmptest.bot.entity.ExpenditureStatistics;
import com.zhiziyun.dmptest.bot.entity.GetHead;
import com.zhiziyun.dmptest.bot.entity.HomePage;
import com.zhiziyun.dmptest.bot.ui.activity.AddStoryActivity;
import com.zhiziyun.dmptest.bot.ui.activity.PassengerFlowAnalysisActivity;
import com.zhiziyun.dmptest.bot.ui.activity.TransactionDetailsActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.NetWorkUtil;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.ValueShape;
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
    private TextView tv_companyname, tv_story, tv_tanzhen, tv_person, tv_newguest, tv_oldguest, tv_new, tv_old, tv_advertising_expenditure, tv_sms_expenditure, tv_tody_expenditure;
    private ImageView iv_addstory, iv_head;
    private RelativeLayout rl_today_people;
    private PieChartView pie_chart;//饼形图控件
    private PieChartData pieChardata;//数据
    List<SliceValue> values = new ArrayList<SliceValue>();
    //折线图
    private LineChartView chartView;
    private LineChartData lineData;
    private HomePage page;
    private SharedPreferences share;
    private ExpenditureStatistics expenditureStatistics;
    private boolean bool = true;

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
        getHead();//从后台获取头像
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
        tv_advertising_expenditure = getView().findViewById(R.id.tv_advertising_expenditure);
        tv_sms_expenditure = getView().findViewById(R.id.tv_sms_expenditure);
        tv_tody_expenditure = getView().findViewById(R.id.tv_tody_expenditure);
        getView().findViewById(R.id.rl_trading).setOnClickListener(this);
        getPictrue();//获取头像
        getTableInfo();
        getData();

        srl = getView().findViewById(R.id.srl);
        srl.setRefreshing(true);//是否一开始就刷新
        srl.setProgressBackgroundColorSchemeResource(android.R.color.white);
        srl.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    //下拉刷新
                    //清空状态标志位
                    values.clear();
                    getData();
                    getHead();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //饼状图
        pie_chart = getView().findViewById(R.id.pie_chart);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (bool) {
                bool = false;
            } else {
                //第一次进来不执行，会报错
                values.clear();
                getData();
                getHead();
            }
            //每次滑动当前碎片就刷新头像路径
            getPictrue();//获取头像
        }
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
            case R.id.rl_trading:
                startActivity(new Intent(getActivity(), TransactionDetailsActivity.class));
                break;
        }
    }

    //获得头像
    public void getPictrue() {
        try {
            final String filepath = "/sdcard/Bot/img_head.jpg";
            File file = new File(filepath);
            if (hasSdcard()) {
                if (file.exists()) {
                    Bitmap bm = BitmapFactory.decodeFile(filepath);
                    //将图片显示到ImageView中
                    iv_head.setImageBitmap(createCircleImage(bm));
                }
            } else {
                Toast.makeText(getActivity(), "没有SD卡", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public void getData() {
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
                            .url(BaseUrl.BaseWang + "report/siteStatistics.action")
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
                                if (json.get("store").toString().equals("0")) {//如果没有门店就直接跳转添加门店
                                    startActivity(new Intent(getActivity(), AddStoryActivity.class));
                                    startApp(true);//首次打开应用
                                    tagging();//打标签
                                } else {
                                    startApp(false);//开启应用
                                }
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
                            .url(BaseUrl.BaseWang + "report/todayStatistics.action")
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

        //广告主今日消耗查询
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "advertiserApp/todayConsumption")
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
                                expenditureStatistics = gson.fromJson(response.body().string(), ExpenditureStatistics.class);
                                if (expenditureStatistics != null) {
                                    myHandler.sendEmptyMessage(6);
                                } else {
                                    myHandler.sendEmptyMessage(4);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void getTableInfo() {
        //整体客流分析，趋势分析
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("token", Token.gettoken());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("beginTime", getPastDate(6));
                    jsonObject.put("endTime", getDate());
                    jsonObject.put("microprobeId", 0);
                    jsonObject.put("storeId", 0);
                    Log.i("info", jsonObject.toString());
                    OkHttpClient client = new OkHttpClient();
                    String url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "report/visitDay.action")
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
                            Log.i("info", str);
                            Gson gson = new Gson();
                            page = gson.fromJson(str, HomePage.class);
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
                    tv_tanzhen.setText(bundle.get("probe").toString() + "个   ");
                    break;
                case 2:
                    Bundle bundle1 = msg.getData();
                    tv_person.setText(bundle1.get("all").toString());
                    tv_newguest.setText(bundle1.get("new").toString());
                    tv_oldguest.setText(bundle1.get("old").toString());
                    if (bundle1.get("new").toString().equals("0")) {
                        tv_new.setText("0%");
                    } else {
                        tv_new.setText(" " + computations(Float.parseFloat(bundle1.get("all").toString()), Float.parseFloat(bundle1.get("new").toString())));
                    }
                    if (bundle1.get("old").equals("0")) {
                        tv_old.setText("0%");
                    } else {
                        tv_old.setText(" " + computations(Float.parseFloat(bundle1.get("all").toString()), Float.parseFloat(bundle1.get("old").toString())));
                    }
                    setPieChartData(Float.parseFloat(bundle1.get("new").toString()), Float.parseFloat(bundle1.get("old").toString()));
                    initPieChart(bundle1.get("all").toString());
                    srl.setRefreshing(false);//关闭加载动画
                    break;
                case 3:
                    generateInitialLineData(page);
                    break;
                case 4:
                    ToastUtils.showShort(getActivity(), "无数据");
                    srl.setRefreshing(false);//关闭加载动画
                    break;
                case 5:
                    srl.setRefreshing(false);//关闭加载动画
                    break;
                case 6:
                    tv_tody_expenditure.setText(expenditureStatistics.getResponse().getTotalConsumption());//今日总花费
                    tv_advertising_expenditure.setText(expenditureStatistics.getResponse().getCreativeConsumption());//广告花费
                    tv_sms_expenditure.setText(expenditureStatistics.getResponse().getSmsConsumption());//短信花费
                    break;
            }
            super.handleMessage(msg);
        }
    };


    /**
     * 获取数据
     */
    private void setPieChartData(float a, float b) {
        SliceValue sliceValue = new SliceValue(a, Color.parseColor("#75BBC8"));//新客
        values.add(sliceValue);
        SliceValue sliceValue2 = new SliceValue(b, Color.parseColor("#ee5b42"));//老客
        values.add(sliceValue2);
    }

    /**
     * 初始化饼状图
     */
    private void initPieChart(String str) {
        pieChardata = new PieChartData();
        pieChardata.setHasLabels(true);//显示表情
        pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChardata.setHasCenterCircle(true);//是否是环形显示
        pieChardata.setValues(values);//填充数据
        pieChardata.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
        pieChardata.setCenterCircleScale(0.5f);//设置环形的大小级别
        pieChardata.setValueLabelBackgroundEnabled(false);//去掉标签的背景颜色
        pieChardata.setValueLabelsTextColor(0xFF000000);//标签的颜色（75）
        if (str.equals("0")) {//如果到店总数为0，就显示无数据
            pieChardata.setCenterText1("无数据");//环形中间的文字
        } else {
            pieChardata.setCenterText1("人 数");//环形中间的文字
        }
        pieChardata.setCenterText1Color(Color.BLACK);//文字颜色
        pieChardata.setCenterText1FontSize(10);//文字大小
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
        line.setColor(Color.parseColor("#ee5b42")).setHasLabels(true).setShape(ValueShape.CIRCLE)
                .setCubic(false).setPointRadius(3).setStrokeWidth(1).setFilled(true);//setCubic(false)false是折线，true是曲线
        Line line2 = new Line(values2);
        line2.setColor(Color.parseColor("#12a47d")).setHasLabels(true).setShape(ValueShape.CIRCLE)
                .setCubic(false).setPointRadius(3).setStrokeWidth(1).setFilled(true);//setPointRadius(2).setStrokeWidth(1)是点和线的粗细，setFilled是是否要填充色
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        lines.add(line2);
        lineData = new LineChartData(lines);

        lineData.setValueLabelBackgroundEnabled(false);//去掉标签的背景颜色
        lineData.setValueLabelsTextColor(0xFFC4C4C4);//标签的颜色（75）
        lineData.setValueLabelTextSize(10);//标签的字体大小

        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(5));
        chartView.setLineChartData(lineData);
        chartView.setViewportCalculationEnabled(true);
        chartView.setZoomType(ZoomType.HORIZONTAL);
    }

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
                            .url(BaseUrl.BaseZhang + "advertiserApp/getLogoImg")
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

    // 保存图片到手机指定目录
    public void savaBitmap(String imgName, byte[] bytes) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String filePath = null;
                FileOutputStream fos = null;
                try {
                    filePath = Environment.getExternalStorageDirectory().getPath() + "/Bot/";
                    File imgDir = new File(filePath);
                    if (!imgDir.exists()) {
                        imgDir.mkdirs();
                    }
                    imgName = filePath + imgName;
                    fos = new FileOutputStream(imgName);
                    fos.write(bytes);
                    Log.i("infos", "图片已保存到" + filePath);
                    getPictrue();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                ToastUtils.showShort(getActivity(), "请检查SD卡是否可用");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHead(String url) {
        Glide.with(this).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    savaBitmap("img_head.jpg", bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public void tagging() {
        //打标签
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
                    String url = "http://trace.zhiziyun.com/open/oem/cm.gif?did=" + imei + "&tagid=x2RIi0u7FKg";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        Response response = call.execute();
                        Log.i("response", "打标签:" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    Log.i("response", e.toString());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void startApp(final boolean bol) {
        //记录打开APP
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteid", "0zoTLi29XRgq");
                    json.put("zzid", "0zoTLha93ySI");//广告活动编号
                    json.put("appid", "43357325432");//应用编号
                    TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
                    json.put("deviceid", imei);//设备编号
                    json.put("imei", imei);
                    json.put("idfa", "");
                    json.put("idfy", "");
                    json.put("channelid", "");//渠道id
                    json.put("install", bol);
                    TimeZone tz = TimeZone.getDefault();
                    String strTz = tz.getDisplayName(false, TimeZone.SHORT);
                    json.put("tx", strTz);//时区
                    json.put("devicetype", android.os.Build.MODEL);//设备类型
                    json.put("op", getSimOperatorInfo());//运营商
                    json.put("netword", NetWorkUtil.getNetworkType(getActivity()));//联网方式
                    json.put("os", 0);
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = json.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseJiang + "startup")
                            .addHeader("apiid", "0zoTLi29XRgq")
                            .addHeader("token", URLEncoder.encode(Token.gettoken2(), "utf-8"))
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("response", "打开APP返回：" + e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.i("response", "打开APP返回：" + response.body().string());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //获取运营商
    public String getSimOperatorInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String operatorString = telephonyManager.getSimOperator();

        if (operatorString == null) {
            return "未知";
        }

        if (operatorString.equals("46000") || operatorString.equals("46002")) {
            //中国移动
            return "中国移动";
        } else if (operatorString.equals("46001")) {
            //中国联通
            return "中国联通";
        } else if (operatorString.equals("46003")) {
            //中国电信
            return "中国电信";
        }
        //error
        return "未知";
    }
}
