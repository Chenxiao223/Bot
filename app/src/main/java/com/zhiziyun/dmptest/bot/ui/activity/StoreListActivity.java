package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.StoreListAdapter;
import com.zhiziyun.dmptest.bot.entity.StoreList;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.SelfDialog;
import com.zhiziyun.dmptest.bot.util.SlideListView;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
 * Created by Administrator on 2017/12/5.
 * 门店列表列
 */

public class StoreListActivity extends BaseActivity implements View.OnClickListener {
    public static StoreListActivity storeListActivity;
    private SharedPreferences share;
    private StoreList storeList;
    private SlideListView lv_store;
    private StoreListAdapter adapter;
    private HashMap<String, String> hm_store;
    private ArrayList<HashMap<String, String>> list_store = new ArrayList<>();
    private EditText et_text;
    private SmartRefreshLayout smartRefreshLayout;
    private int pageNum = 1;
    private MyDialog dialog;
    private LinearLayout line_page;
    private SharedPreferences.Editor editors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist);
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
        try {
            if (hm_store == null) {//根据这个值来判断是第一次进来还是第二次进来
                //加载动画
                dialog = MyDialog.showDialog(this);
                dialog.show();
                getstoreList(1, "");//第二个参数为空就是查所有
            } else {//第二次
                dialog.show();
                hm_store.clear();
                clearAllData();
                getstoreList(pageNum, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAllData() {
        pageNum = 1;
        list_store.clear();
    }

    private void initView() {
        if (!getState()) {
            //第一次进来
            saveState();
            final SelfDialog selfDialog = new SelfDialog(StoreListActivity.this);
            selfDialog.setTitle("消息提示");
            selfDialog.setMessage("向左滑动，显示操作菜单，可编辑门店，查看探针");
            selfDialog.setYesOnclickListener("知道了", new SelfDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    selfDialog.dismiss();
                }
            });
            selfDialog.show();
        }

        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        storeListActivity = this;
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_addstory).setOnClickListener(this);
        lv_store = (SlideListView) findViewById(R.id.lv_store);
        adapter = new StoreListAdapter(this, lv_store, list_store);
        lv_store.setAdapter(adapter);
        et_text = (EditText) findViewById(R.id.et_text);
        //点击搜索键的监听
        et_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    StoreListActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //以下是搜索逻辑
                    list_store.clear();
                    //查询门店
                    pageNum = 1;
                    getstoreList(pageNum, et_text.getText().toString());
                    return true;
                }
                return false;
            }
        });
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    hm_store.clear();
                    clearAllData();
                    getstoreList(pageNum, "");
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
                    if ((storeList.getTotal() - (pageNum - 1) * 10) > 0) {
                        getstoreList(pageNum, "");
                    } else {
                        ToastUtils.showShort(StoreListActivity.this, "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.traceroute_rootview).setOnClickListener(this);
    }

    public void saveState() {
        SharedPreferences sharedPreferences = getSharedPreferences("strorestate", Context.MODE_PRIVATE);
        editors = sharedPreferences.edit();
        editors.putBoolean("state", true);
        editors.commit();//提交
    }

    public boolean getState() {
        SharedPreferences shared = getSharedPreferences("strorestate", Context.MODE_PRIVATE);
        return shared.getBoolean("state", false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.iv_addstory:
                startActivity(new Intent(this, AddStoryActivity.class));
                break;
            case R.id.traceroute_rootview:
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        hm_store.clear();
                        clearAllData();
                        getstoreList(pageNum, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    //第二个参数为空就是查所有
    public void getstoreList(final int page, final String name) {
        //获取门店列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("page", page);
                    json.put("rows", 10);
                    json.put("name", name);
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
                            .url(BaseUrl.BaseTest + "store/list")
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
                                storeList = gson.fromJson(response.body().string(), StoreList.class);
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
                    try {
                        if (storeList != null) {
                            if (storeList.getRows().size() == 0) {
                                line_page.setVisibility(View.VISIBLE);
                                ToastUtils.showShort(StoreListActivity.this, "无数据");
                                smartRefreshLayout.finishRefresh(0);//停止刷新
                                smartRefreshLayout.finishLoadmore(0);//停止加载
                                dialog.dismiss();
                            } else {
                                for (int i = 0; i < storeList.getRows().size(); i++) {
                                    hm_store = new HashMap<>();
                                    hm_store.put("content1", storeList.getRows().get(i).getName());
                                    String area = storeList.getRows().get(i).getArea();
                                    double r = Double.parseDouble(area) / Math.PI;//根据面积计算出半径的平方
                                    double radius = Math.round(Math.sqrt(r));//根据半径的平方计算半径（平方根）
                                    hm_store.put("content2", "" + radius);//以前显示面积，现在显示半径
                                    hm_store.put("content3", storeList.getRows().get(i).getProbeCount());
                                    hm_store.put("lat", storeList.getRows().get(i).getLatitude());
                                    hm_store.put("lon", storeList.getRows().get(i).getLongitude());
                                    hm_store.put("id", String.valueOf(storeList.getRows().get(i).getId()));
                                    hm_store.put("area", storeList.getRows().get(i).getArea());
                                    list_store.add(hm_store);
                                }
                                pageNum++;
                                line_page.setVisibility(View.GONE);
                            }
                            adapter.notifyDataSetChanged();
                            smartRefreshLayout.finishRefresh(0);//停止刷新
                            smartRefreshLayout.finishLoadmore(0);//停止加载
                            dialog.dismiss();
                        } else {
                            line_page.setVisibility(View.VISIBLE);
                            ToastUtils.showShort(StoreListActivity.this, "无数据");
                            smartRefreshLayout.finishRefresh(0);//停止刷新
                            smartRefreshLayout.finishLoadmore(0);//停止加载
                            dialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        hm_store.clear();
                        clearAllData();
                        dialog.show();
                        getstoreList(pageNum, "");
                        ToastUtils.showShort(StoreListActivity.this, String.valueOf(msg.obj));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public void editeStore(String name, String area, String lat, String lon, String id) {
        Intent it = new Intent(this, EditeStoryActivity.class);
        it.putExtra("name", name);
        it.putExtra("area", area);
        it.putExtra("lat", lat);
        it.putExtra("lon", lon);
        it.putExtra("id", id);
        startActivity(it);
    }

    public void deleteStore(final String id) {
        //删除门店接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("id", id);
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
                            .url(BaseUrl.BaseTest + "store/delete")
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
                                Message message = new Message();
                                message.what = 2;
                                message.obj = jsonObject.get("msg");
                                handler.sendMessage(message);
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

    public void checkTanzhen(String id, float lat, float lon, String area) {
        Intent it = new Intent(this, TanzhenListActivity.class);
        it.putExtra("id", id);
        it.putExtra("lat", lat);
        it.putExtra("lon", lon);
        it.putExtra("area", area);
        startActivity(it);
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
                    share = null;
                    storeList = null;
                    lv_store.setAdapter(null);
                    adapter = null;
                    list_store.clear();
                    et_text = null;
                    smartRefreshLayout = null;
                    hm_store.clear();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
