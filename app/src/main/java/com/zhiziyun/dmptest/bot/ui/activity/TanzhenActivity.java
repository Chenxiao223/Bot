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
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CheckBoxAdapter;
import com.zhiziyun.dmptest.bot.adapter.CheckBoxAdapter.ViewCache;
import com.zhiziyun.dmptest.bot.entity.Tanzhen;
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
 * Created by Administrator on 2018/1/8.
 * 探针页面
 */

public class TanzhenActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout smartRefreshLayout;
    private ListView lv_tanzhen;
    private SharedPreferences share;
    private List<HashMap<String, Object>> list;
    private CheckBoxAdapter cbAdapter;
    private List<String> listStr = new ArrayList<String>();
    private Tanzhen tanzhen;
    private MyDialog dialog;
    private final int FLAG = 1407;
    private int flag = 0;
    private LinearLayout line_page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanzhen);
        initView();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
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

        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        smartRefreshLayout.setEnableLoadmore(false);//屏蔽掉上拉加载的效果
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        lv_tanzhen = (ListView) findViewById(R.id.lv_tanzhen);

        //加载动画
        dialog = MyDialog.showDialog(this);
        dialog.show();
        requestTanzhen();

        flag = getIntent().getIntExtra("flag", 0);

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    list.clear();
                    listStr.clear();
                    requestTanzhen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void requestTanzhen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("microProbe_siteId", share.getString("siteid", ""));
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
                            .url(BaseUrl.BaseWang + "builtCrowd/microProbelist.action")
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
                            tanzhen = gson.fromJson(response.body().string(), Tanzhen.class);
                            if (tanzhen != null) {
                                handler.sendEmptyMessage(1);
                            } else {
                                handler.sendEmptyMessage(2);//无数据
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
                        list = new ArrayList<>();
                        if (tanzhen.getRows().size() == 0) {
                            line_page.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            smartRefreshLayout.finishLoadmore(0);//停止刷新
                            ToastUtils.showShort(TanzhenActivity.this, "无数据");
                        } else {
                            for (int i = 0; i < tanzhen.getRows().size(); i++) {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("name", tanzhen.getRows().get(i).getName());
                                map.put("mac", tanzhen.getRows().get(i).getMac());
                                if (flag != 0) {//如果不是携带数据进来的就不执行
                                    for (int j = 0; j < getIntent().getStringArrayListExtra("list").size(); j++) {
                                        if (tanzhen.getRows().get(i).getMac().
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
                            line_page.setVisibility(View.GONE);
                        }
                        cbAdapter = new CheckBoxAdapter(TanzhenActivity.this, list);
                        lv_tanzhen.setAdapter(cbAdapter);
                        dialog.dismiss();
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        lv_tanzhen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ViewCache viewCache = (ViewCache) view.getTag();
                                viewCache.cb.toggle();
                                list.get(position).put("boolean", viewCache.cb.isChecked());
                                cbAdapter.notifyDataSetChanged();
                                if (viewCache.cb.isChecked()) {//被选中状态
                                    listStr.add(list.get(position).get("mac").toString());
                                } else {//从选中状态转化为未选中
                                    listStr.remove(list.get(position).get("mac").toString());
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        line_page.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        ToastUtils.showShort(TanzhenActivity.this, "无数据");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                        ToastUtils.showShort(TanzhenActivity.this, "请选择探针");
                    } else {
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
                        requestTanzhen();
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
                    list.clear();
                    listStr.clear();
                    tanzhen = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
