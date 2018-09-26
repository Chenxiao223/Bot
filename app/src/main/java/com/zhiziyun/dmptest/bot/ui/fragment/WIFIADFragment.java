package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.WifiADAdapter;
import com.zhiziyun.dmptest.bot.entity.QueryTaskList;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/8/29.
 * wifi广告
 */

public class WIFIADFragment extends Fragment implements View.OnClickListener {
    public static WIFIADFragment fragment;
    private ListView lv_wifi_ad;
    public WifiADAdapter wifiADAdapter;
    public HashMap<String, Object> hm_wifi_ad;
    public ArrayList<HashMap<String, Object>> list_wifi_ad = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;
    private SharedPreferences share;
    private int pageNum = 1;
    private LinearLayout line_page;
    private QueryTaskList queryTaskList;
    public TextView tv_state, tv_beginTime, tv_endTime;
    private ImageView iv_state;
    private String beginTime, endTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wifi_ad, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    //当滑到当前碎片时调用该方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (ClickUtils.isFastClick()) {//防止快速切换而闪退
                try {
                    //这里与下拉刷新代码一样
                    clearAllData();
                    getData(pageNum, "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clearAllData() {
        try {
            pageNum = 1;
            list_wifi_ad.clear();
            queryTaskList = null;
            hm_wifi_ad.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        fragment = this;
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        tv_state = getView().findViewById(R.id.tv_state);
        iv_state = getView().findViewById(R.id.iv_state);
        tv_beginTime = getView().findViewById(R.id.tv_beginTime);
        tv_endTime = getView().findViewById(R.id.tv_endTime);
        getView().findViewById(R.id.line_date).setOnClickListener(this);

        smartRefreshLayout = getView().findViewById(R.id.refreshLayout);
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
                    if ((queryTaskList.getTotal() - (pageNum - 1) * 10) > 0) {
                        getData(pageNum, "");
                        ToastUtils.showShort(getActivity(), pageNum + "/" + ((queryTaskList.getTotal() / 10) + 1));
                    } else {
                        ToastUtils.showShort(getActivity(), "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        lv_wifi_ad = getView().findViewById(R.id.lv_wifi_ad);
        wifiADAdapter = new WifiADAdapter(getActivity(), list_wifi_ad);
        lv_wifi_ad.setAdapter(wifiADAdapter);
        getData(1, "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_date:
                try {
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
                            tv_beginTime.setText(beginTime);
                            tv_endTime.setText(endTime);

                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public void getData(final int page, final String name) {
        //获取微信wifi广告活动列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("name", name);
                    jsonObject.put("page", page);
                    jsonObject.put("rows", 10);
                    jsonObject.put("order", "desc");
                    jsonObject.put("sort", "createTime");
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
                            .url(BaseUrl.BaseTest + "wx_wifi_ad/queryTaskList")
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
                                queryTaskList = gson.fromJson(str, QueryTaskList.class);
                                if (queryTaskList.getTotal() != 0) {
                                    handler.sendEmptyMessage(1);
                                } else {
                                    handler.sendEmptyMessage(2);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        if (queryTaskList.getTotal() == 0) {
                            line_page.setVisibility(View.VISIBLE);
                            smartRefreshLayout.finishLoadmore(0);//停止刷新
                        } else {
                            for (int i = 0; i < queryTaskList.getRows().size(); i++) {
                                hm_wifi_ad = new HashMap<>();
                                hm_wifi_ad.put("content1", queryTaskList.getRows().get(i).getName());
                                hm_wifi_ad.put("content2", queryTaskList.getRows().get(i).getCount());
                                int count = queryTaskList.getRows().get(i).getCount();
                                int total = queryTaskList.getRows().get(i).getTotal();
                                if (total == 0) {//分母不能为0
                                    hm_wifi_ad.put("content3", "0%");
                                } else {
                                    hm_wifi_ad.put("content3", count / total * 100 + "%");
                                }
                                list_wifi_ad.add(hm_wifi_ad);
                            }
                            pageNum++;
                            wifiADAdapter.notifyDataSetChanged();
                            smartRefreshLayout.finishLoadmore(0);//停止刷新
                            line_page.setVisibility(View.GONE);
                            lv_wifi_ad.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    line_page.setVisibility(View.VISIBLE);
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    break;
            }
        }
    };
}