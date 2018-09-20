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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.xiaosu.view.text.DataSetAdapter;
import com.xiaosu.view.text.VerticalRollingTextView;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.ExpenditureStatistics;
import com.zhiziyun.dmptest.bot.entity.GetHead;
import com.zhiziyun.dmptest.bot.entity.HomePage;
import com.zhiziyun.dmptest.bot.mode.originality.friend.FriendMoneyDetailActivity;
import com.zhiziyun.dmptest.bot.ui.activity.CrowdActivity;
import com.zhiziyun.dmptest.bot.ui.activity.HomePageActivity;
import com.zhiziyun.dmptest.bot.ui.activity.PassengerFlowAnalysisActivity;
import com.zhiziyun.dmptest.bot.ui.activity.TransactionDetailsActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.PopWin_account;

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

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.github.mikephil.charting.data.LineDataSet.Mode.HORIZONTAL_BEZIER;


/**
 * 首页
 */
public class HomePageFragment extends Fragment implements View.OnClickListener, VerticalRollingTextView.OnItemClickListener {
    public static HomePageFragment homePageFragment;
    private SwipeRefreshLayout srl;
    private TextView tv_companyname, tv_story, tv_tanzhen, tv_person, tv_newguest,
            tv_oldguest, tv_new, tv_old, tv_tody_expenditure, tv_tel, tv_sms, tv_ad, tv_wechat;
    private ImageView iv_head;
    private RelativeLayout rl_today_people;
    private PieChartView pie_chart;//饼形图控件
    private PieChartData pieChardata;//数据
    private List<SliceValue> values = new ArrayList<SliceValue>();
    private LinearLayout line_friend, line_ad;
    //折线图
    private LineChart chartView;
    private HomePage page;
    private SharedPreferences share;
    private ExpenditureStatistics expenditureStatistics;
    private boolean bool = true;
    private boolean bool_chart = true;
    private VerticalRollingTextView verticalRollingView;
    private List<CharSequence> mDataSet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public void initView() {
        homePageFragment = this;
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        getHead();//从后台获取头像
        //折线图
        chartView = getView().findViewById(R.id.linechart);
        tv_new = getView().findViewById(R.id.tv_new);
        tv_old = getView().findViewById(R.id.tv_old);
        iv_head = getView().findViewById(R.id.iv_head);
        rl_today_people = getView().findViewById(R.id.rl_today_people);
        rl_today_people.setOnClickListener(this);
        tv_story = getView().findViewById(R.id.tv_story);
        tv_companyname = getView().findViewById(R.id.tv_companyname);
        tv_companyname.setText(share.getString("company", "无数据"));
        tv_tanzhen = getView().findViewById(R.id.tv_tanzhen);
        tv_person = getView().findViewById(R.id.tv_person);
        tv_newguest = getView().findViewById(R.id.tv_newguest);
        tv_oldguest = getView().findViewById(R.id.tv_oldguest);
        tv_tody_expenditure = getView().findViewById(R.id.tv_tody_expenditure);
        tv_tel = getView().findViewById(R.id.tv_tel);
        tv_sms = getView().findViewById(R.id.tv_sms);
        tv_ad = getView().findViewById(R.id.tv_ad);
        tv_wechat = getView().findViewById(R.id.tv_wechat);
        line_friend = getView().findViewById(R.id.line_friend);
        getView().findViewById(R.id.rl_trading).setOnClickListener(this);
        getView().findViewById(R.id.line_crowd).setOnClickListener(this);
        getView().findViewById(R.id.line_phone).setOnClickListener(this);
        getView().findViewById(R.id.line_sms).setOnClickListener(this);
        line_ad = getView().findViewById(R.id.line_ad);
        line_ad.setOnClickListener(this);
        if (!share.getBoolean("isShowPlanAds", false)) {//如果不显示投广告
            line_ad.setVisibility(View.GONE);
        }

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
                    getTableInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //饼状图
        pie_chart = getView().findViewById(R.id.pie_chart);

        if (!share.getBoolean("isShowTencent", false)) {
            line_friend.setVisibility(View.GONE);
        }

        mDataSet = new ArrayList<>();
        mDataSet.add("你的账户余额已不足，请尽快充值");
        verticalRollingView = getView().findViewById(R.id.verticalRollingView);
        verticalRollingView.setDataSetAdapterQuiet(new DataSetAdapter<CharSequence>(mDataSet) {

            @Override
            protected CharSequence text(CharSequence charSequence) {
                return charSequence;
            }
        });
        verticalRollingView.run();
        verticalRollingView.setOnItemClickListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            try {
                if (bool) {
                    bool = false;
                } else {
                    //第一次进来不执行，会报错
                    values.clear();
                    getData();
                    getHead();
                    getTableInfo();
                }
                //每次滑动当前碎片就刷新头像路径
                getPictrue();//获取头像
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_crowd:
                startActivity(new Intent(getActivity(), CrowdActivity.class));
                break;
            case R.id.line_phone:
                HomePageActivity.activity.pager.setCurrentItem(3);
                HomePageActivity.activity.changeColor(false, false, false, true, false);
                break;
            case R.id.line_sms:
                HomePageActivity.activity.pager.setCurrentItem(2);
                HomePageActivity.activity.changeColor(false, false, true, false, false);
                break;
            case R.id.line_ad:
                HomePageActivity.activity.pager.setCurrentItem(2);
                HomePageActivity.activity.changeColor(false, false, true, false, false);
                break;
            case R.id.rl_today_people:
                startActivity(new Intent(getActivity(), PassengerFlowAnalysisActivity.class));
                break;
            case R.id.rl_trading:
                try {
                    if (share.getBoolean("isShowTencent", false)) {//如果有显示朋友圈的权限
                        //隐藏软键盘
                        InputMethodManager imm4 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm4.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        //让背景变暗
                        WindowManager.LayoutParams lp3 = getActivity().getWindow().getAttributes();
                        lp3.alpha = 0.7f;
                        getActivity().getWindow().setAttributes(lp3);
                        getActivity().getWindow().setAttributes(lp3);
                        //弹出pop窗体
                        PopWin_account popWin_account = new PopWin_account(getActivity(), null, 0);
                        popWin_account.showAtLocation(getActivity().findViewById(R.id.main_view), Gravity.BOTTOM, 0, 0);//125
                        //监听popwin是否关闭，关闭的话让背景恢复
                        popWin_account.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                WindowManager.LayoutParams lp3 = getActivity().getWindow().getAttributes();
                                lp3.alpha = 1f;
                                getActivity().getWindow().setAttributes(lp3);
                            }
                        });
                    } else {//如果当前账户不显示朋友圈，那么不弹出选择框，直接跳转到账户明细
                        startActivity(new Intent(getActivity(), TransactionDetailsActivity.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void jump(int num) {
        if (num == 1) {
            //推广账户
            startActivity(new Intent(getActivity(), TransactionDetailsActivity.class));
        } else {
            //朋友圈账户
            startActivity(new Intent(getActivity(), FriendMoneyDetailActivity.class));
        }
    }

    //获得头像
    public void getPictrue() {
        try {
            final String filepath = "/sdcard/" + BaseUrl.BaseAPPName + "/" + share.getString("userid", "") + "/img_head.jpg";
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
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "report/siteStatistics")
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
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "report/todayStatistics")
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
                                JSONObject jsonObject = new JSONObject(str);
                                JSONObject json = new JSONObject(jsonObject.get("obj").toString());
                                Message message = new Message();
                                message.what = 2;
                                Bundle bundle = new Bundle();
                                bundle.putString("all", json.get("all").toString());
                                bundle.putString("new_guest", json.get("new").toString());
                                bundle.putString("old_guest", json.get("old").toString());
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
                            .url(BaseUrl.BaseZhang + "api/advertiserApp/yesterdayCost")
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
                                if (expenditureStatistics.isStatus()) {
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
                    OkHttpClient client = new OkHttpClient();
                    String url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "report/visitDay")
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
                    try {
                        Bundle bundle1 = msg.getData();
                        tv_person.setText(bundle1.get("all").toString());
                        tv_newguest.setText(" " + bundle1.get("new_guest").toString());
                        tv_oldguest.setText(" " + bundle1.get("old_guest").toString());
                        if (bundle1.get("new_guest").toString().equals("0")) {
                            tv_new.setText("0.00%");
                        } else {
                            tv_new.setText("" + computations(Float.parseFloat(bundle1.get("all").toString()), Float.parseFloat(bundle1.get("new_guest").toString())));
                        }
                        if (bundle1.get("old_guest").equals("0")) {
                            tv_old.setText("0.00%");
                        } else {
                            tv_old.setText("" + computations(Float.parseFloat(bundle1.get("all").toString()), Float.parseFloat(bundle1.get("old_guest").toString())));
                        }
                        setPieChartData(Float.parseFloat(bundle1.get("new_guest").toString()), Float.parseFloat(bundle1.get("old_guest").toString()));
                        initPieChart(bundle1.get("all").toString());
                        srl.setRefreshing(false);//关闭加载动画
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        //显示曲线图
                        initChart(chartView, page);
                        chartView.setData(generateInitialLineData(page));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        srl.setRefreshing(false);//关闭加载动画
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    srl.setRefreshing(false);//关闭加载动画
                    break;
                case 6:
                    try {
                        tv_tody_expenditure.setText(expenditureStatistics.getResponse().getTotalCost());//今日总花费
                        tv_tel.setText(expenditureStatistics.getResponse().getCallCost());//电话消耗
                        tv_sms.setText(expenditureStatistics.getResponse().getSmsCost());//短信消耗
                        tv_ad.setText(expenditureStatistics.getResponse().getAdCost());//广告消耗
                        tv_wechat.setText(expenditureStatistics.getResponse().getWechatCost());//朋友圈消耗
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


    /**
     * 获取数据
     */
    private void setPieChartData(float a, float b) {
        try {
            SliceValue sliceValue = new SliceValue(a, Color.parseColor("#361fb0"));//新客
            values.add(sliceValue);
            SliceValue sliceValue2 = new SliceValue(b, Color.parseColor("#746edb"));//老客
            values.add(sliceValue2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化饼状图
     */
    private void initPieChart(String str) {
        try {
            pieChardata = new PieChartData();
            pieChardata.setHasLabels(false);//显示标签
            pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
            pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
            pieChardata.setHasCenterCircle(true);//是否是环形显示
            pieChardata.setValues(values);//填充数据
            pieChardata.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
            pieChardata.setCenterCircleScale(0.8f);//设置环形的大小级别
            pieChardata.setValueLabelBackgroundEnabled(false);//去掉标签的背景颜色
            pieChardata.setValueLabelsTextColor(0xFF000000);//标签的颜色（75）
            if (str.equals("0")) {//如果到店总数为0，就显示无数据
                pieChardata.setCenterText1("无数据");//环形中间的文字
            } else {
                pieChardata.setCenterText1("人 数");//环形中间的文字
            }
            pieChardata.setSlicesSpacing(0);//设置扇形之间的间距大小
            pieChardata.setCenterText1Color(Color.BLACK);//文字颜色
            pieChardata.setCenterText1FontSize(12);//文字大小
            pie_chart.setPieChartData(pieChardata);
            pie_chart.setValueSelectionEnabled(false);//选择饼图某一块变大
            pie_chart.setAlpha(0.9f);//设置透明度
            pie_chart.setCircleFillRatio(1f);//设置饼图大小
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //计算百分比
    public String computations(float sum, float num) {
        return String.format("%.2f", num * 100 / sum) + "%";
    }

    //折线图
    private LineData generateInitialLineData(HomePage homePage) {
        int numValues = homePage.getRows().size();
        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();
        for (int i = 0; i < numValues; ++i) {
            values1.add(new Entry(i, Float.parseFloat(homePage.getRows().get(i).getUv().toString())));//进店客流
            values2.add(new Entry(i, Float.parseFloat(homePage.getRows().get(i).getTotalUV())));//环境客流
        }
        LineDataSet dataSetA = new LineDataSet(values1, "新客");
        dataSetA.setCircleSize(0f);
        dataSetA.setDrawCircleHole(true); //是否定制节点圆心的颜色，若为false，则节点为单一的同色点，若为true则可以设置节点圆心的颜色
        dataSetA.setCircleColorHole(Color.parseColor("#ff5d92"));  //设置节点圆心的颜色
        dataSetA.setMode(HORIZONTAL_BEZIER);
        dataSetA.setValueTextColor(Color.GRAY);
        dataSetA.setColor(Color.parseColor("#ff5d92"));
        dataSetA.setDrawFilled(true);//设置是否开启填充，默认为false
        dataSetA.setFillColor(Color.parseColor("#ff5d92"));//设置填充颜色
        dataSetA.setFillAlpha(55);//设置填充区域透明度，默认值为85
        //设置折线数据 getChartData返回一个List<Entry>键值对集合标识 折线点的横纵坐标，"A"代表折线标识
        LineDataSet dataSetB = new LineDataSet(values2, "老客");
        dataSetB.setCircleSize(0f);
        dataSetB.setDrawCircleHole(true); //是否定制节点圆心的颜色，若为false，则节点为单一的同色点，若为true则可以设置节点圆心的颜色
        dataSetB.setCircleColorHole(Color.parseColor("#5cabf8"));  //设置节点圆心的颜色
        dataSetB.setMode(HORIZONTAL_BEZIER);
        dataSetB.setValueTextColor(Color.GRAY);
        dataSetB.setColor(Color.parseColor("#5cabf8"));
        dataSetB.setDrawFilled(true);//设置是否开启填充，默认为false
        dataSetB.setFillColor(Color.parseColor("#5cabf8"));//设置填充颜色
        dataSetB.setFillAlpha(55);//设置填充区域透明度，默认值为85

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA);
        dataSets.add(dataSetB);
        LineData data = new LineData(dataSets);
        return data;
    }

    public LineChart initChart(LineChart chart, final HomePage homePage) {
        //设置边框颜色
        chart.setBorderColor(Color.WHITE);
        //是否展示网格线
        chart.setDrawGridBackground(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);
        //是否显示边界
        chart.setDrawBorders(true);
        //是否可以拖动
        chart.setDragEnabled(false);
        //是否有触摸事件
        chart.setTouchEnabled(true);
        if (bool_chart) {
            //设置XY轴动画效果
            chart.animateY(1500);
            bool_chart = false;//只执行一次
        }
        // 不显示数据描述
        chart.getDescription().setEnabled(false);
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("暂无数据");
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        // 不可以缩放
        chart.setScaleEnabled(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        // 向左偏移15dp，抵消y轴向右偏移的30dp
        chart.setExtraLeftOffset(-15);

        XAxis xAxis = chart.getXAxis();
        // 不显示x轴
        xAxis.setDrawAxisLine(false);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(10);
        xAxis.setGridColor(Color.parseColor("#30FFFFFF"));
        // 设置x轴数据偏移量
        xAxis.setYOffset(5);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //X轴显示的数据
                String tradeDate = homePage.getRows().get((int) value).getStatDate().substring(5);
                return tradeDate;
            }
        });
//        xAxis.setLabelCount(6, false);

        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTextColor(Color.GRAY);
        yAxis.setTextSize(10);
        // 设置y轴数据偏移量
        yAxis.setXOffset(20);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);

        chart.invalidate();
        return chart;
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
        //查询广告主LOGO
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
                            .url(BaseUrl.BaseZhang + "api/advertiserApp/getLogoImg")
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

    // 保存图片到手机指定目录
    public void savaBitmap(String imgName, byte[] bytes) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String filePath = null;
                FileOutputStream fos = null;
                try {
                    filePath = Environment.getExternalStorageDirectory().getPath() + "/" + BaseUrl.BaseAPPName + "/" + share.getString("userid", "") + "/";
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
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        setHead(String.valueOf(msg.obj));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    ToastUtils.showShort(getActivity(), "头像为空，请上传头像");
                    break;
            }
        }
    };

    //点击verticalRollingView返回
    @Override
    public void onItemClick(VerticalRollingTextView view, int index) {
        ToastUtils.showShort(getActivity(), mDataSet.get(index));
    }
}
