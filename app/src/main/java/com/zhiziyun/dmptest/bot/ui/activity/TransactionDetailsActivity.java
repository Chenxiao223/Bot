package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.TransactionDetailsAdapter;
import com.zhiziyun.dmptest.bot.entity.TransactionDetails;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.xListView.XListView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/2.
 * 交易明细页
 */

public class TransactionDetailsActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    private String beginTime;
    private String endTime;
    private SharedPreferences share;
    private XListView xlistview;
    private TransactionDetails td;
    private HashMap<String, String> hm_td;
    private ArrayList<HashMap<String, String>> list_td = new ArrayList<>();
    private TransactionDetailsAdapter adapter;
    private int pageNum = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        TextView tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setOnClickListener(this);
        ImageView tv_back = (ImageView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);

        xlistview = (XListView) findViewById(R.id.xlistview);
        xlistview.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        adapter = new TransactionDetailsAdapter(this, list_td);
        xlistview.setAdapter(adapter);
        xlistview.setXListViewListener(this);

        getData(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
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
                        beginTime = textString.substring(0, index);
                        endTime = textString.substring(index + 1, textString.length());
                        getData(1);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    public void getData(final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("accountid", share.getString("accountid", ""));
                    json.put("startDate", beginTime);
                    json.put("endDate", endTime);
                    json.put("page", page);
                    json.put("row", 10);
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
                            .url("http://test.zhiziyun.com/api-service/advertiserApp/accountDetail")
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
                                td = gson.fromJson(response.body().string(), TransactionDetails.class);
                                handler.sendEmptyMessage(1);
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
                    if (td.getResponse().getData() != null) {
                        for (int i = 0; i < td.getResponse().getData().size(); i++) {
                            hm_td = new HashMap<>();
                            hm_td.put("content1", td.getResponse().getData().get(i).getSettleType());
                            hm_td.put("content2", td.getResponse().getData().get(i).getSettleDate());
                            hm_td.put("content3", td.getResponse().getData().get(i).getFee());
                            list_td.add(hm_td);
                        }
                        pageNum++;
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    //清空所有数据
    public void clearAllData() {
        beginTime = gettodayDate();
        endTime = beginTime;
        pageNum = 1;
        list_td.clear();
    }

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    @Override//下拉刷新
    public void onRefresh() {
        try {
            hm_td.clear();
            clearAllData();
            getData(pageNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override//上拉加载
    public void onLoadMore() {
        if (pageNum < ((td.getResponse().getTotal() / 10) + 3)) {
            getData(pageNum);
        } else {
            onLoad();
            Toast.makeText(this, "最后一页了", Toast.LENGTH_SHORT).show();
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
}
