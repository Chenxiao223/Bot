package com.zhiziyun.dmptest.bot.ui.activity;

import android.app.Activity;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.TanzhenListAdapter;
import com.zhiziyun.dmptest.bot.entity.TanzhenList;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.SlideListView;
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
 * 探针列表页
 */

public class TanzhenListActivity extends BaseActivity implements View.OnClickListener {
    public static TanzhenListActivity tanzhenListActivity;
    private SharedPreferences share;
    private TanzhenList tanzhenList;
    private SlideListView lv_tanzhen;
    private TanzhenListAdapter adapter;
    private HashMap<String, String> hm_tanzhen;
    private ArrayList<HashMap<String, String>> list_tanzhen = new ArrayList<>();
    private EditText et_text;
    private SmartRefreshLayout smartRefreshLayout;
    private int pageNum = 1;
    private Intent intent;
    private MyDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanzhenlist);
        initView();
    }

    public void clearAllData() {
        pageNum = 1;
        list_tanzhen.clear();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        tanzhenListActivity = this;
        intent = getIntent();
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_addstory).setOnClickListener(this);
        lv_tanzhen = (SlideListView) findViewById(R.id.lv_store);
        adapter = new TanzhenListAdapter(this, lv_tanzhen, list_tanzhen);
        lv_tanzhen.setAdapter(adapter);
        et_text = (EditText) findViewById(R.id.et_text);
        //点击搜索键的监听
        et_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    TanzhenListActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //以下是搜索逻辑
                    list_tanzhen.clear();
                    //查询门店
                    gettanzhenList(1, et_text.getText().toString());
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
                    hm_tanzhen.clear();
                    clearAllData();
                    gettanzhenList(pageNum, "");
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }
        });
        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (pageNum < ((tanzhenList.getTotal() / 10) + 3)) {
                    gettanzhenList(pageNum, "");
                } else {
                    Toast.makeText(TanzhenListActivity.this, "最后一页了", Toast.LENGTH_SHORT).show();
                    smartRefreshLayout.finishLoadmore(0);
                }
            }
        });
        findViewById(R.id.traceroute_rootview).setOnClickListener(this);
    }

    //以便刷新页面
    @Override
    protected void onResume() {
        super.onResume();
        if (hm_tanzhen == null) {//第一次进来
            gettanzhenList(1, "");//第二个参数为空就是查所有
        } else {//第二次进来
            hm_tanzhen.clear();
            clearAllData();
            gettanzhenList(pageNum, "");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.iv_addstory:
                Intent it = new Intent();
                it.setClass(TanzhenListActivity.this, CaptureActivity.class);
                //返回一个二维码的信息
                startActivityForResult(it, 98);
                break;
            case R.id.traceroute_rootview:
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 98 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            //返回二维码扫描的信息
            String result = bundle.get("result").toString();
            Intent it = new Intent(this, BindingActivity.class);
            it.putExtra("lat", intent.getFloatExtra("lat", 0));
            it.putExtra("lon", intent.getFloatExtra("lon", 0));
            it.putExtra("floorArea", intent.getStringExtra("area"));
            it.putExtra("mac", result);
            it.putExtra("storeId", intent.getStringExtra("id"));
            startActivity(it);
        }
    }

    //第二个参数为空就是查所有
    public void gettanzhenList(final int page, final String name) {
        //加载动画
        dialog = MyDialog.showDialog(this);
        dialog.show();
        //获取门店列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("storeId", intent.getStringExtra("id"));
                    json.put("page", page);
                    json.put("rows", 10);
                    json.put("name", name);
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
                            .url("http://dmptest.zhiziyun.com/api/v1/probe/list.action")
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
                            tanzhenList = gson.fromJson(response.body().string(), TanzhenList.class);
                            handler.sendEmptyMessage(1);
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
                    if (tanzhenList != null) {
                        for (int i = 0; i < tanzhenList.getRows().size(); i++) {
                            hm_tanzhen = new HashMap<>();
                            hm_tanzhen.put("content1", tanzhenList.getRows().get(i).getName());
                            hm_tanzhen.put("content2", tanzhenList.getRows().get(i).getMac());
                            hm_tanzhen.put("content3", tanzhenList.getRows().get(i).getFloorArea());
                            hm_tanzhen.put("id", tanzhenList.getRows().get(i).getId());
                            list_tanzhen.add(hm_tanzhen);
                        }
                        smartRefreshLayout.finishRefresh(0);
                        smartRefreshLayout.finishLoadmore(0);
                        pageNum++;
                        dialog.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 2:
                    list_tanzhen.clear();
                    gettanzhenList(1, "");//第二个参数为空就是查所有
                    Toast.makeText(TanzhenListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(TanzhenListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public void unbindTanzhen(final String id) {
        //解绑探针接口
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
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url("http://dmptest.zhiziyun.com/api/v1/probe/delete.action")
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
                                if (jsonObject.get("msg").equals("解除探针成功")) {
                                    handler.sendEmptyMessage(2);
                                } else {
                                    handler.sendEmptyMessage(3);
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

    //清空内存
    private void toFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    share = null;
                    tanzhenList = null;
                    lv_tanzhen.setAdapter(null);
                    adapter = null;
                    list_tanzhen.clear();
                    et_text = null;
                    smartRefreshLayout = null;
                    intent = null;
                    hm_tanzhen.clear();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
