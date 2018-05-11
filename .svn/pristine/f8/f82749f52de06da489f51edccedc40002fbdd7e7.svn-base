package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CheckBoxAdapter;
import com.zhiziyun.dmptest.bot.entity.Advertising;
import com.zhiziyun.dmptest.bot.entity.Crowd;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/9.
 * 广告列表页
 */

public class AdListActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout smartRefreshLayout;
    private ListView lv_crowd;
    private SharedPreferences share;
    private List<HashMap<String, Object>> list = new ArrayList<>();
    HashMap<String, Object> map;
    private CheckBoxAdapter cbAdapter;
    private List<String> listStr = new ArrayList<String>();
    private MyDialog dialog;
    private Advertising advertising;
    private final int FLAG = 1702;
    private int page = 1;
    private int flag = 0;
    private LinearLayout line_page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adlist);
        initView();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (map == null) {//根据这个值来判断是第一次进来还是第二次进来
            //加载动画
            dialog = MyDialog.showDialog(this);
            dialog.show();
            requestCrowd(1);//第二个参数为空就是查所有
        } else {//第二次
            try {
                dialog.show();
                map.clear();
                list.clear();
                listStr.clear();
                page = 1;
                requestCrowd(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        lv_crowd = (ListView) findViewById(R.id.lv_crowd);

        flag = getIntent().getIntExtra("flag", 0);

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    list.clear();
                    listStr.clear();
                    page = 1;
                    requestCrowd(page);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if ((advertising.getResponse().getTotal() - (page - 1) * 10) > 0) {
                    requestCrowd(page);
                } else {
                    ToastUtils.showShort(AdListActivity.this, "最后一页了");
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                }
            }
        });
    }

    public void requestCrowd(final int page) {
        ////广告活动查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("page", page);
                    json.put("rows", 10);
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
                            .url(BaseUrl.BaseZhang + "activityApp/getCreativeActivity")
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
                                advertising = gson.fromJson(response.body().string(), Advertising.class);
                                if (advertising != null) {
                                    handler.sendEmptyMessage(1);
                                } else {
                                    handler.sendEmptyMessage(2);//无数据
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
                        if (advertising.getResponse().getData().size() == 0) {
                            line_page.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            smartRefreshLayout.finishLoadmore(0);//停止刷新
                            ToastUtils.showShort(AdListActivity.this, "无数据");
                        } else {
                            for (int i = 0; i < advertising.getResponse().getData().size(); i++) {
                                map = new HashMap<>();
                                map.put("name", advertising.getResponse().getData().get(i).getActivityName());
                                map.put("tagIds", advertising.getResponse().getData().get(i).getActivityId());
                                if (flag != 0) {//如果不是携带数据进来的就不执行
                                    for (int j = 0; j < getIntent().getStringArrayListExtra("list").size(); j++) {
                                        if (advertising.getResponse().getData().get(i).getActivityId().
                                                equals(getIntent().getStringArrayListExtra("list").get(j))) {
                                            map.put("boolean", true);
                                            //为保持一致性，将数据存入集合
                                            listStr.add(getIntent().getStringArrayListExtra("list").get(j));
                                            break;//如果为true就跳出当前循环，否则true会被覆盖
                                        } else {
                                            map.put("boolean", false);
                                        }
                                    }
                                } else {
                                    map.put("boolean", false);
                                }
                                list.add(map);
                            }
                            page++;
                            line_page.setVisibility(View.GONE);
                        }
                        cbAdapter = new CheckBoxAdapter(AdListActivity.this, list);
                        lv_crowd.setAdapter(cbAdapter);
                        dialog.dismiss();
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        lv_crowd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                CheckBoxAdapter.ViewCache viewCache = (CheckBoxAdapter.ViewCache) view.getTag();
                                viewCache.cb.toggle();
                                list.get(position).put("boolean", viewCache.cb.isChecked());
                                cbAdapter.notifyDataSetChanged();
                                if (viewCache.cb.isChecked()) {//被选中状态
                                    listStr.add(list.get(position).get("tagIds").toString());
                                } else {//从选中状态转化为未选中
                                    listStr.remove(list.get(position).get("tagIds").toString());
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    line_page.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    ToastUtils.showShort(AdListActivity.this, "无数据");
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                toFinish();
                finish();
                break;
            case R.id.tv_commit:
                try {
                    if (listStr.size() == 0) {
                        ToastUtils.showShort(AdListActivity.this, "请选择人群");
                    } else {
                        //从添加广告活动进来
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra("list", (ArrayList<String>) listStr);
                        setResult(FLAG, intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        list.clear();
                        listStr.clear();
                        page = 1;
                        requestCrowd(page);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
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
                    lv_crowd.setAdapter(null);
                    list.clear();
                    map.clear();
                    listStr.clear();
                    cbAdapter = null;
                    advertising = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
