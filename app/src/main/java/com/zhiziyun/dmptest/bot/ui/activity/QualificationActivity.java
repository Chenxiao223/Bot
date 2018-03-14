package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.QualificationListAdapter;
import com.zhiziyun.dmptest.bot.entity.Qualification;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/8.
 * 资质列表页面
 */

public class QualificationActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences share;
    private ListView lv_qualification;
    private ArrayList<HashMap<String, Object>> list_qualification = new ArrayList<>();
    private HashMap<String, Object> hm_qualification;
    private QualificationListAdapter adapter;
    private Qualification qualification;
    private MyDialog dialog;
    private TextView tv_state, tv_why;
    private LinearLayout line;
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);
        initView();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        smartRefreshLayout.setEnableLoadmore(false);//屏蔽掉上拉加载的效果
        findViewById(R.id.tv_back).setOnClickListener(this);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_why = (TextView) findViewById(R.id.tv_why);
        line = (LinearLayout) findViewById(R.id.line);
        lv_qualification = (ListView) findViewById(R.id.lv_qualification);
        adapter = new QualificationListAdapter(this, list_qualification);
        lv_qualification.setAdapter(adapter);
        lv_qualification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //只有在审核失败的情况下才可点击
                if (tv_state.getText().toString().equals("审核失败")) {
                    //如果点的是“法人代表身份证照片”就跳转
                    if (list_qualification.get(position).get("content1").toString().equals("法人代表身份证照片")) {//点了法人代表身份证照片
                        startActivity(new Intent(QualificationActivity.this, AddQualificationActivity.class));
                    }
                }
            }
        });
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    hm_qualification.clear();
                    list_qualification.clear();
                    qualificationList();
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }
        });
    }

    //以便刷新页面
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (hm_qualification == null) {//第一次进来
            //加载动画
            dialog = MyDialog.showDialog(this);
            dialog.show();
            qualificationList();
        } else {//第二次进来
            try {
                dialog.show();
                hm_qualification.clear();
                list_qualification.clear();
                qualificationList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                toFinish();
                finish();
                break;
        }
    }

    public void qualificationList() {
        //短信资质状态查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject jsonObject = new JSONObject();
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
                            .url(BaseUrl.BaseZhang + "advertiserApp/getSmsQualificationStatus")
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
                                qualification = gson.fromJson(response.body().string(), Qualification.class);
                                if (qualification.isStatus()) {
                                    for (int i = 0; i < qualification.getResponse().getQualifications().size(); i++) {
                                        hm_qualification = new HashMap<String, Object>();
                                        hm_qualification.put("content1", qualification.getResponse().getQualifications().get(i).getQualificationName());
                                        hm_qualification.put("content2", getBitmap(qualification.getResponse().getQualifications().get(i).getQualificationUrl()));
                                        hm_qualification.put("state", qualification.getResponse().getSmsQualificationStatus());
                                        list_qualification.add(hm_qualification);
                                    }
                                    handler.sendEmptyMessage(1);
                                } else {
                                    handler.sendEmptyMessage(2);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter.notifyDataSetChanged();
                    tv_state.setText(qualification.getResponse().getSmsQualificationStatus());
                    //如果失败原因有的话（smsQualificationFailReason）就显示出来
                    if (!TextUtils.isEmpty(qualification.getResponse().getSmsQualificationFailReason())) {
                        line.setVisibility(View.VISIBLE);
                        tv_why.setText(qualification.getResponse().getSmsQualificationFailReason());
                    }
                    dialog.dismiss();
                    smartRefreshLayout.finishLoadmore(0);
                    break;
                case 2:
                    ToastUtils.showShort(QualificationActivity.this, "无数据");
                    dialog.dismiss();
                    smartRefreshLayout.finishLoadmore(0);
                    break;
            }
        }
    };

    public Bitmap getBitmap(String url) {
        try {
            return Glide.with(this).load(url)
                    .asBitmap() //必须
                    .centerCrop()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//图片大小
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        toFinish();
        finish();
    }

    //清空内存
    private void toFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    lv_qualification.setAdapter(null);
                    adapter = null;
                    list_qualification.clear();
                    hm_qualification.clear();
                    qualification = null;
                    tv_state = null;
                    tv_why = null;
                    line = null;
                    Glide.get(QualificationActivity.this).clearMemory();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}