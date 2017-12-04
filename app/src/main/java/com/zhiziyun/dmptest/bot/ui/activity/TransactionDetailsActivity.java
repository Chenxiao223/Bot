package com.zhiziyun.dmptest.bot.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.xListView.XListView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

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

public class TransactionDetailsActivity extends Activity implements View.OnClickListener {
    private String beginTime;
    private String endTime;
    private SharedPreferences share;
    private XListView xlistview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        initView();
    }

    private void initView() {
        share=getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        TextView tv_date=findViewById(R.id.tv_date);
        tv_date.setOnClickListener(this);
        ImageView tv_back=findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
                        getData();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }

    public void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("accountid", share.getString("accountid",""));
                    json.put("startDate",beginTime);
                    json.put("endDate",endTime);
                    json.put("page",1);
                    json.put("row",10);
                    Log.i("jsons",json.toString());
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
                            .url("http://test.zhiziyun.com/api-service/agentApp/accountDetail")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.i("jsons",response.body().string());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
