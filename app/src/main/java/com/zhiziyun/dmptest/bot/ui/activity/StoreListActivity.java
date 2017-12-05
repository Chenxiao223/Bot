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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.StoreListAdapter;
import com.zhiziyun.dmptest.bot.entity.StoreList;
import com.zhiziyun.dmptest.bot.util.Token;

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
 */

public class StoreListActivity extends Activity implements View.OnClickListener {
    private SharedPreferences share;
    private StoreList storeList;
    private ListView lv_store;
    private StoreListAdapter adapter;
    private HashMap<String, String> hm_store;
    private ArrayList<HashMap<String, String>> list_store = new ArrayList<>();
    private EditText et_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist);
        initView();
    }

    private void initView() {
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_addstory).setOnClickListener(this);
        lv_store = findViewById(R.id.lv_store);
        adapter = new StoreListAdapter(this, list_store);
        lv_store.setAdapter(adapter);
        et_text = findViewById(R.id.et_text);
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
                    //实现自己的搜索逻辑
                    Toast.makeText(StoreListActivity.this, "搜索了", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        getstoreList(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_addstory:
                startActivity(new Intent(this,AddStoryActivity.class));
                break;
        }
    }

    public void getstoreList(final int page) {
        //获取门店列表
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
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url("http://dmptest.zhiziyun.com/api/v1/store/list.action")
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
                            storeList = gson.fromJson(response.body().string(), StoreList.class);
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
                    if (storeList != null) {
                        for (int i = 0; i < storeList.getRows().size(); i++) {
                            hm_store = new HashMap<>();
                            hm_store.put("content1", storeList.getRows().get(i).getName());
                            hm_store.put("content2", storeList.getRows().get(i).getArea());
                            hm_store.put("content3", storeList.getRows().get(i).getProbeCount());
                            hm_store.put("lat", storeList.getRows().get(i).getLatitude());
                            hm_store.put("lon", storeList.getRows().get(i).getLatitude());
                            list_store.add(hm_store);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
}
