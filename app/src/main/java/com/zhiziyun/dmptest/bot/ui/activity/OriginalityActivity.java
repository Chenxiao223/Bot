package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.OriginalityAdapter;
import com.zhiziyun.dmptest.bot.adapter.OriginalityAdapter.ViewHold;
import com.zhiziyun.dmptest.bot.entity.Originality;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONArray;
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
 * Created by Administrator on 2018/1/4.
 * 选择创意页面
 */

public class OriginalityActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout smartRefreshLayout;
    private SharedPreferences share;
    private int pageNum = 1;
    private ListView lv_creative;
    private OriginalityAdapter adapter;
    private Originality originality;
    private ArrayList<HashMap<String, Object>> list_originality = new ArrayList<>();
    private HashMap<String, Object> hashMap;
    private List<String> listStr = new ArrayList<String>();
    private Intent it;
    private MyDialog dialog;
    private final int FLAG = 1527;
    private int flag = 0;
    private LinearLayout line_page;
    private int screenWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_originality);
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

        //获取屏幕宽度
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;

        it = getIntent();
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        dialog = MyDialog.showDialog(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_add_originality).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        lv_creative = (ListView) findViewById(R.id.lv_creative);
        adapter = new OriginalityAdapter(OriginalityActivity.this, list_originality);
        lv_creative.setAdapter(adapter);
        lv_creative.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHold viewHold = (ViewHold) view.getTag();
                viewHold.cb.toggle();
                list_originality.get(position).put("boolean", viewHold.cb.isChecked());
                adapter.notifyDataSetChanged();
                if (viewHold.cb.isChecked()) {//被选中状态
                    listStr.add(list_originality.get(position).get("creativeId").toString());
                } else {//从选中状态转化为未选中
                    listStr.remove(list_originality.get(position).get("creativeId").toString());
                }
            }
        });

        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        flag = getIntent().getIntExtra("flag", 0);

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
                if ((originality.getResponse().getTotal() - (pageNum - 1) * 10) > 0) {
                    getCreative(pageNum);
                    ToastUtils.showShort(OriginalityActivity.this, pageNum + "/" + ((originality.getResponse().getTotal() / 10) + 1));
                } else {
                    ToastUtils.showShort(OriginalityActivity.this, "最后一页了");
                    smartRefreshLayout.finishLoadmore(0);
                }
            }
        });

    }

    //以便刷新页面
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (hashMap == null) {//第一次进来
            //加载动画
            dialog.show();
            getCreative(1);
        } else {//第二次进来
            dialog.show();
            clearAllData();
            getCreative(pageNum);
        }
    }

    //清空所有数据
    public void clearAllData() {
        try {
            hashMap.clear();
            list_originality.clear();
            listStr.clear();
            pageNum = 1;
            originality = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.tv_add_originality:
//                Intent it2 = new_guest Intent(OriginalityActivity.this, AddOriginalityActivity.class);
//                it2.putExtra("flag", 1253);
//                startActivity(it2);
                break;
            case R.id.tv_commit:
                try {
                    if (listStr.size() == 0) {
                        ToastUtils.showShort(OriginalityActivity.this, "请选择创意");
                    } else {
                        if (!TextUtils.isEmpty(it.getStringExtra("activityId"))) {
                            //如果是从创意列表进来就执行
                            JSONArray jsonArray = new JSONArray();
                            for (int i = 0; i < listStr.size(); i++) {
                                jsonArray.put(listStr.get(i));
                            }
                            addCreative(jsonArray);
                        } else {
                            Intent intent = new Intent();
                            intent.putStringArrayListExtra("list", (ArrayList<String>) listStr);
                            setResult(FLAG, intent);
                            finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void getCreative(final int page) {
        lv_creative.setEnabled(false);//加载数据时让listview无法点击
        //广告查询
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    try {//这里如果报错就继续往下执行，这个参数不是必填项
                        String str = AdvertisingActivity.advertisingActivity.tv_AD_types.getText().toString();
                        if (str.equals("静态广告")) {
                            json.put("activityType", "1");
                        } else if (str.equals("信息流")) {
                            json.put("activityType", "4");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String activityId = it.getStringExtra("activityId");
                    if (!TextUtils.isEmpty(activityId)) {
                        json.put("activityId", activityId);//广告活动添加广告时必填
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
                            .url(BaseUrl.BaseZhang + "api/creativeApp/getCreative")
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
                            originality = gson.fromJson(response.body().string(), Originality.class);
                            if (originality != null) {
                                try {
                                    if (originality.getResponse().getData().size() == 0) {
                                        handler.sendEmptyMessage(2);
                                    } else {
                                        for (int i = 0; i < originality.getResponse().getData().size(); i++) {
                                            hashMap = new HashMap();
                                            hashMap.put("image", getBitmap(originality.getResponse().getData().get(i).getCreativeUrl()));
                                            hashMap.put("creativeId", originality.getResponse().getData().get(i).getCreativeId());//广告编号
                                            if (flag != 0) {//如果不是携带数据进来的就不执行
                                                for (int j = 0; j < getIntent().getStringArrayListExtra("list").size(); j++) {
                                                    if (originality.getResponse().getData().get(i).getCreativeId().
                                                            equals(getIntent().getStringArrayListExtra("list").get(j))) {
                                                        hashMap.put("boolean", true);
                                                        //为保持一致性，将数据存入集合
                                                        listStr.add(getIntent().getStringArrayListExtra("list").get(j));
                                                        break;//如果为true就跳出当前循环，否则true会被覆盖
                                                    } else {
                                                        hashMap.put("boolean", false);
                                                    }
                                                }
                                            } else {
                                                hashMap.put("boolean", false);
                                            }
                                            list_originality.add(hashMap);
                                        }
                                        pageNum++;
                                        line_page.setVisibility(View.GONE);
                                    }
                                    handler.sendEmptyMessage(1);
                                } catch (Exception e) {
                                    dialog.dismiss();
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

    public void addCreative(final JSONArray jsonArray) {
        dialog.show();
        //活动创意添加
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    String activityId = it.getStringExtra("activityId");
                    if (!TextUtils.isEmpty(activityId)) {
                        json.put("activityId", activityId);//广告活动添加广告时必填
                    }
                    json.put("creativeIds", jsonArray);//广告编号
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
                            .url(BaseUrl.BaseZhang + "api/creativeApp/addActivityCreative")
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
                                if (jsonObject.get("status").toString().equals("true")) {
                                    handler.sendEmptyMessage(3);
                                } else {
                                    handler.sendEmptyMessage(4);
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
                        adapter.notifyDataSetChanged();
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        dialog.dismiss();
                        lv_creative.setEnabled(true);//加载完后才可以点击listview
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    line_page.setVisibility(View.VISIBLE);
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    ToastUtils.showShort(OriginalityActivity.this, "无数据");
                    dialog.dismiss();
                    lv_creative.setEnabled(true);//加载完后才可以点击listview
                    break;
                case 3:
                    ToastUtils.showShort(OriginalityActivity.this, "添加成功");
                    dialog.dismiss();
                    finish();
                    break;
                case 4:
                    ToastUtils.showShort(OriginalityActivity.this, "添加失败");
                    dialog.dismiss();
                    break;
            }
        }
    };

    public Bitmap getBitmap(String url) {
        try {
            Bitmap bitmap = Glide.with(this).load(url)
                    .asBitmap() //必须
                    .centerCrop()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//图片大小
                    .get();
            // 获得图片的宽高
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            // 计算缩放比例
            float scaleWidth = ((float) screenWidth) / width;
            float scaleHeight = ((float) screenWidth * bitmap.getHeight() / bitmap.getWidth()) / height;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            //通过计算，重新设置bitmap的尺寸，一遍适配手机宽度
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
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
                    adapter = null;
                    originality = null;
                    list_originality.clear();
                    listStr.clear();
                    hashMap.clear();
                    Glide.get(OriginalityActivity.this).clearMemory();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
