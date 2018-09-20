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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.VisitorsselfAdapter;
import com.zhiziyun.dmptest.bot.entity.Visitorsself;
import com.zhiziyun.dmptest.bot.ui.activity.SearchPageActivity;
import com.zhiziyun.dmptest.bot.ui.activity.VisitorsViewActivity;
import com.zhiziyun.dmptest.bot.ui.activity.VisitorsselfActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.PopWin_general;

import org.json.JSONArray;
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
 * 访客列表
 */
public class VisitorsselfFragment extends Fragment implements View.OnClickListener {
    public static VisitorsselfFragment fragment;
    public ArrayList<HashMap<String, Object>> list_shop = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> list_tanzhen = new ArrayList<>();
    private LinearLayout line_date;
    private HashMap<String, Object> hm_store;
    private HashMap<String, Object> hm_probe;
    private String beginTime, endTime;
    public String microprobeId = "0";
    public int storeId = 0;
    private ListView xlistview;
    public VisitorsselfAdapter adapter;
    private HashMap<String, String> hm_visitors;
    public ArrayList<HashMap<String, String>> list_visitors = new ArrayList<>();
    private int pageNum = 1;
    private SharedPreferences share;
    private List<String> list_brands = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;
    private LinearLayout line_page;
    private Visitorsself v;
    public TextView tv_total_number, tv_shop, tv_tanzhen;
    private ImageView iv_date, iv_tanzhen;


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
        try {
            hm_store.clear();
            list_shop.clear();
            hm_store = new HashMap<String, Object>();
            hm_store.put("name", "全部门店");//名字
            hm_store.put("mac", "0");//id
            hm_store.put("boolean", false);
            list_shop.add(hm_store);

            hm_probe.clear();
            list_tanzhen.clear();
            hm_probe = new HashMap<>();
            hm_probe.put("name", "全部探针");//名字
            hm_probe.put("mac", "0");//id
            hm_probe.put("boolean", false);
            list_tanzhen.add(hm_probe);
            getSiteOption();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initView() {
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = getView().findViewById(R.id.refreshLayout);
        tv_total_number = getView().findViewById(R.id.tv_total_number);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        tv_shop = getView().findViewById(R.id.tv_shop);
        tv_tanzhen = getView().findViewById(R.id.tv_tanzhen);
        iv_date = getView().findViewById(R.id.iv_date);
        iv_tanzhen = getView().findViewById(R.id.iv_tanzhen);
        getView().findViewById(R.id.line_shop).setOnClickListener(this);
        getView().findViewById(R.id.line_tanzhen).setOnClickListener(this);
        //初始化接口没有的数据
        list_shop.clear();
        hm_store = new HashMap<String, Object>();
        hm_store.put("name", "全部门店");//名字
        hm_store.put("mac", "0");//id
        hm_store.put("boolean", false);
        list_shop.add(hm_store);
        list_tanzhen.clear();
        hm_probe = new HashMap<>();
        hm_probe.put("name", "全部探针");//名字
        hm_probe.put("mac", "0");//id
        hm_probe.put("boolean", false);
        list_tanzhen.add(hm_probe);
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
                intent.putExtra("probemac", list_visitors.get(position).get("probemac"));
                intent.putExtra("is_new", list_visitors.get(position).get("is_new"));
                intent.putExtra("storeId", storeId);
                intent.putExtra("isShown", list_visitors.get(position).get("isShown"));
                intent.putExtra("date", beginTime);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getData(pageNum, "");
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
                        getData(pageNum, "");
                        ToastUtils.showShort(getActivity(), pageNum + "/" + ((v.getTotal() / 10) + 1));
                    } else {
                        ToastUtils.showShort(getActivity(), "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        line_date = getView().findViewById(R.id.line_date);
        line_date.setOnClickListener(this);
        getView().findViewById(R.id.tv_view).setOnClickListener(this);
        getView().findViewById(R.id.iv_search).setOnClickListener(this);

        getData(1, "");
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
                                    // 获得key
                                    String key = store.next();
                                    hm_store = new HashMap<String, Object>();
                                    hm_store.put("name", key);//名字
                                    hm_store.put("mac", json_store.getString(key));//id
                                    if (tv_shop.getText().toString().equals(key)) {
                                        hm_store.put("boolean", true);
                                    } else {
                                        hm_store.put("boolean", false);
                                    }
                                    list_shop.add(hm_store);
                                }
                                JSONObject json_probe = new JSONObject(json_obj.get("probe").toString());
                                Iterator<String> probe = json_probe.keys();
                                while (probe.hasNext()) {
                                    // 获得key
                                    String key = probe.next();
                                    hm_probe = new HashMap<>();
                                    hm_probe.put("name", key);//名字
                                    hm_probe.put("mac", json_probe.getString(key));//id
                                    if (tv_tanzhen.getText().toString().equals(key)) {
                                        hm_probe.put("boolean", true);
                                    } else {
                                        hm_probe.put("boolean", false);
                                    }
                                    list_tanzhen.add(hm_probe);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    try {
                        Visitorsself visitorsself = (Visitorsself) msg.obj;
                        if (visitorsself.getRows().size() == 0) {//如果没数据就提示
                            line_page.setVisibility(View.VISIBLE);
                            tv_total_number.setText("0");
                        } else {
                            tv_total_number.setText(visitorsself.getTotal() + "");
                            for (int i = 0; i < visitorsself.getRows().size(); i++) {
                                hm_visitors = new HashMap<String, String>();
                                hm_visitors.put("content1", visitorsself.getRows().get(i).getVisittime());
                                hm_visitors.put("content2", visitorsself.getRows().get(i).getBrand());
                                list_brands.add(visitorsself.getRows().get(i).getBrand());
                                hm_visitors.put("content4", getPosion(visitorsself.getRows().get(i).getRssi()));
                                hm_visitors.put("mac", visitorsself.getRows().get(i).getMac());
                                hm_visitors.put("probemac", visitorsself.getRows().get(i).getProbemac());//门店mac
                                hm_visitors.put("is_new", String.valueOf(visitorsself.getRows().get(i).isIs_new()));
                                hm_visitors.put("isShown", visitorsself.getRows().get(i).getIsShown() + "");
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

    public void getData(final int page, final String mac) {
        //获取访客信息列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("starttime", beginTime + " 00:00:00");
                    jsonObject.put("endtime", endTime + " 23:59:59");
                    jsonObject.put("page", page);
                    jsonObject.put("rows", 10);
                    jsonObject.put("storeId", storeId);
                    jsonObject.put("sort", "visittime");
                    jsonObject.put("order", "desc");
                    jsonObject.put("dmac", mac);
                    if (!microprobeId.equals("0")) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(microprobeId);
                        jsonObject.put("macs", jsonArray);
                    }
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
                            .url(BaseUrl.BaseTest + "deviceVisit/list")
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
                                String str = response.body().string();
                                Gson gson = new Gson();
                                v = gson.fromJson(str, Visitorsself.class);
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
            case R.id.line_tanzhen:
                iv_tanzhen.setColorFilter(getResources().getColor(R.color.blue));
                //让背景变暗
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.7f;
                getActivity().getWindow().setAttributes(lp);
                //弹出窗体
                PopWin_general popWin_tanzhen = new PopWin_general(getActivity(), "VisitorsselfFragment_two", list_tanzhen);
                popWin_tanzhen.showAsDropDown(getView().findViewById(R.id.line));//从这个控件下弹出
                //监听popwin是否关闭，关闭的话让背景恢复
                popWin_tanzhen.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1f;
                        getActivity().getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.line_shop:
                //让背景变暗
                WindowManager.LayoutParams lp2 = getActivity().getWindow().getAttributes();
                lp2.alpha = 0.7f;
                getActivity().getWindow().setAttributes(lp2);
                //弹出窗体
                PopWin_general popWin_general = new PopWin_general(getActivity(), "VisitorsselfFragment", list_shop);
                popWin_general.showAsDropDown(getView().findViewById(R.id.relativeLayout));//从这个控件下弹出
                //监听popwin是否关闭，关闭的话让背景恢复
                popWin_general.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1f;
                        getActivity().getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.line_date:
                iv_date.setColorFilter(getResources().getColor(R.color.blue));
                //显示日期选择器
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
                        beginTime = date(textString.substring(0, index));
                        endTime = date(textString.substring(index + 1, textString.length()));
                        clearAllData();
                        getData(pageNum, "");
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        clearAllData();
                        getData(pageNum, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_search:
                Intent intent = new Intent(getActivity(), SearchPageActivity.class);
                intent.putExtra("activity", "VisitorsselfFragment");
                startActivity(intent);
                break;
            case R.id.tv_view:
                startActivity(new Intent(getActivity(), VisitorsViewActivity.class));
                break;
        }
    }

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
}
