package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.OriginalityAdapter;
import com.zhiziyun.dmptest.bot.entity.Originality;
import com.zhiziyun.dmptest.bot.ui.activity.AddOriginalityActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
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
 * 静态广告
 */
public class StaticAdvertisingFragment extends Fragment implements View.OnClickListener {
    public static StaticAdvertisingFragment fragment;
    private SmartRefreshLayout smartRefreshLayout;
    private SharedPreferences share;
    private int pageNum = 1;
    private ListView lv_creative;
    private OriginalityAdapter adapter;
    private Originality originality;
    private ArrayList<HashMap<String, Object>> list_originality = new ArrayList<>();
    private HashMap<String, Object> hashMap;
    private LinearLayout line_page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_static_advertising, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        fragment = this;
        initView();
    }

    public void initView() {
        getView().findViewById(R.id.iv_addoriginality).setOnClickListener(this);
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        lv_creative = getView().findViewById(R.id.lv_creative);
        adapter = new OriginalityAdapter(getActivity(), list_originality);
        adapter.hiddenCheckBox(12);//只要参数不等于0就可以隐藏CheckBox
        lv_creative.setAdapter(adapter);

        smartRefreshLayout = getView().findViewById(R.id.refreshLayout);

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getCreative(pageNum);
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
                    if ((originality.getResponse().getTotal() - (pageNum - 1) * 10) > 0) {
                        getCreative(pageNum);
                    } else {
                        ToastUtils.showShort(getActivity(), "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getCreative(1);
    }

    //清空所有数据
    public void clearAllData() {
        try {
            hashMap.clear();
            list_originality.clear();
            pageNum = 1;
            originality = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addoriginality:
                Intent intent = new Intent(getActivity(), AddOriginalityActivity.class);
                intent.putExtra("flag", 4323);
                intent.putExtra("type", "静态广告");
                startActivity(intent);
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        clearAllData();
                        getCreative(pageNum);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void getCreative(final int page) {
        //广告查询
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
//                    json.put("siteId", "tuOiZ0TghUl");
                    try {//这里如果报错就继续往下执行，这个参数不是必填项
                        json.put("activityType", "1");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                            .url(BaseUrl.BaseZhang + "creativeApp/getCreative")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String str = response.body().string();
                            Gson gson = new Gson();
                            originality = gson.fromJson(str, Originality.class);
                            if (originality != null) {
                                try {
                                    if (originality.getResponse().getData().size() == 0) {
                                        handler.sendEmptyMessage(2);
                                    } else {
                                        for (int i = 0; i < originality.getResponse().getData().size(); i++) {
                                            hashMap = new HashMap();
                                            hashMap.put("image", getBitmap(originality.getResponse().getData().get(i).getCreativeUrl()));
                                            hashMap.put("creativeId", originality.getResponse().getData().get(i).getCreativeId());//广告编号
                                            hashMap.put("boolean", false);
                                            list_originality.add(hashMap);
                                        }
                                        pageNum++;
                                        handler.sendEmptyMessage(1);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                handler.sendEmptyMessage(2);
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
                        line_page.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    line_page.setVisibility(View.VISIBLE);
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    ToastUtils.showShort(getActivity(), "无数据");
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

    //清空内存
    public void clearMemory() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.get(getActivity()).clearMemory();
                    System.gc();
                    adapter = null;
                    originality = null;
                    list_originality.clear();
                    lv_creative.setAdapter(null);
                    hashMap.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
