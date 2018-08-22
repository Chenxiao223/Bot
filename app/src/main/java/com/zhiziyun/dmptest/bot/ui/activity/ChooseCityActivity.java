package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CheckBoxAdapter;
import com.zhiziyun.dmptest.bot.adapter.ChooseCityAdapter;
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
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2018/7/30.
 * 选择城市
 */

public class ChooseCityActivity extends BaseActivity implements View.OnClickListener {
    private MyDialog dialog;
    private ListView lv_city, lv_area;
    private ChooseCityAdapter cityAdapter;
    private CheckBoxAdapter areaAdapter;
    private HashMap<String, Object> hm_city;
    private List<HashMap<String, Object>> list_city = new ArrayList<>();
    private HashMap<String, Object> hm_area;
    private List<HashMap<String, Object>> list_area = new ArrayList<>();
    private String Json;
    private List<String> listStr = new ArrayList<>();
    private Intent it_ChooseCity;
    private final int FLAG = 8243;
    private int cityPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
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
        it_ChooseCity = getIntent();
        lv_city = findViewById(R.id.lv_city);
        lv_area = findViewById(R.id.lv_area);

        findViewById(R.id.tv_commit).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        //城市列表
        cityAdapter = new ChooseCityAdapter(ChooseCityActivity.this, list_city);
        lv_city.setAdapter(cityAdapter);
        //区列表
        areaAdapter = new CheckBoxAdapter(ChooseCityActivity.this, list_area);
        lv_area.setAdapter(areaAdapter);
        //点击区列表
        lv_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBoxAdapter.ViewCache viewCache = (CheckBoxAdapter.ViewCache) view.getTag();
                viewCache.cb.toggle();
                list_area.get(position).put("boolean", viewCache.cb.isChecked());//改变选中状态
                areaAdapter.notifyDataSetChanged();
                if (viewCache.cb.isChecked()) {//被选中状态
                    listStr.add(list_area.get(position).get("tagIds").toString());//将选中的添加进集合
                } else {//从选中状态转化为未选中
                    listStr.remove(list_area.get(position).get("tagIds").toString());//将选中的剔除出集合
                }
                list_city.get(cityPosition).put("boolean", false);
                cityAdapter.notifyDataSetChanged();
            }
        });

        //点击城市列表,区列表全选或全不选
        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                cityPosition = position;
                selected(cityPosition);//先显示区列表再改选中状态
                ChooseCityAdapter.ViewCache viewCache = (ChooseCityAdapter.ViewCache) view.getTag();
                viewCache.cb.toggle();
                list_city.get(position).put("boolean", viewCache.cb.isChecked());
                cityAdapter.setSelectedPosition(position);//让item背景变色
                cityAdapter.notifyDataSetChanged();
                setChoose(viewCache.cb.isChecked(), position);
            }
        });

        //点击textview,显示区列表
        cityAdapter.setClick(new ChooseCityAdapter.OnClick() {
            @Override
            public void setClick(View v, Boolean b, int position) {
                selected(position);
            }
        });
        getFlagInfo();
        if (getIntent().getIntExtra("flag", 0) == 123) {//说明是携带数据进来
            listStr.addAll(getIntent().getStringArrayListExtra("list"));//同步下显示的数据
        }
    }

    public void selected(int position) {
        //显示区列表
        try {
            cityPosition = position;
            list_area.clear();
            JSONObject json = new JSONObject(Json);
            HashMap<String, String> maps = JSON.parseObject(json.get(list_city.get(position).get("name").toString()
                    + ":" + list_city.get(position).get("tagIds").toString()).toString(), HashMap.class);
            for (Object map : maps.entrySet()) {
                String strs = ((Map.Entry) map).getKey().toString();
                hm_area = new HashMap<String, Object>();
                hm_area.put("name", strs.substring(0, strs.indexOf(":")));
                hm_area.put("tagIds", strs.substring(strs.indexOf(":") + 1));
                if (listStr.size() != 0) {//如果listStr没有保存数据就不执行
                    for (int i = 0; i < listStr.size(); i++) {
                        if (listStr.get(i).equals(strs.substring(strs.indexOf(":") + 1).toString())) {
                            hm_area.put("boolean", true);
                            break;//跳出当前循环
                        } else {
                            hm_area.put("boolean", false);
                        }
                    }
                } else {
                    hm_area.put("boolean", false);
                }
                hm_area.put("position", position);
                list_area.add(hm_area);
            }
            areaAdapter.notifyDataSetChanged();
            cityAdapter.setSelectedPosition(position);//让item背景变色
            cityAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChoose(final boolean b, final int position) {
        //b为true时全部选中，反之全不被选中
        try {
            JSONObject json = new JSONObject(Json);
            HashMap<String, String> maps = JSON.parseObject(json.get(list_city.get(position).get("name").toString()
                    + ":" + list_city.get(position).get("tagIds").toString()).toString(), HashMap.class);
            for (int i = 0; i < maps.size(); i++) {
                list_area.get(i).put("boolean", b);//全部选中或不选中
                if (b) {//入股是true就添加，false就删除
                    if (!listStr.contains(list_area.get(i).get("tagIds").toString())) {//重复的就不添加
                        listStr.add(list_area.get(i).get("tagIds").toString());//将选中的添加进集合
                    }
                } else {
                    listStr.remove(list_area.get(i).get("tagIds").toString());//将选中的剔除出集合
                }
            }
            areaAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFlagInfo() {
        //加载动画
        dialog = MyDialog.showDialog(this);
        dialog.show();
        //微信朋友圈活动地域信息获取
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("geoLocationType", it_ChooseCity.getStringExtra("type"));
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "api/tencentWechatActivityApp/getGeoLocationes")
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
                                String str = response.body().string();
                                JSONObject jsonObject = new JSONObject(str);
                                if (jsonObject.get("status").toString().equals("true")) {
                                    JSONObject json = new JSONObject(jsonObject.get("response").toString());
                                    Json = json.getString("geoLocationes");
                                    HashMap<String, String> maps = JSON.parseObject(Json, HashMap.class);
                                    for (Object map : maps.entrySet()) {
                                        String strs = ((Map.Entry) map).getKey().toString();
                                        hm_city = new HashMap<String, Object>();
                                        hm_city.put("name", strs.substring(0, strs.indexOf(":")));
                                        hm_city.put("tagIds", strs.substring(strs.indexOf(":") + 1));
                                        hm_city.put("boolean", false);
                                        list_city.add(hm_city);
                                    }
                                }
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
                    cityAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    //显示第一个城市的区，因此位置填的都是0
                    try {
                        JSONObject json = new JSONObject(Json);
                        HashMap<String, String> maps = JSON.parseObject(json.get(list_city.get(0).get("name").toString()
                                + ":" + list_city.get(0).get("tagIds").toString()).toString(), HashMap.class);
                        for (Object map : maps.entrySet()) {
                            String strs = ((Map.Entry) map).getKey().toString();
                            hm_area = new HashMap<String, Object>();
                            hm_area.put("name", strs.substring(0, strs.indexOf(":")));
                            hm_area.put("tagIds", strs.substring(strs.indexOf(":") + 1));
                            if (listStr.size() != 0) {//如果listStr没有保存数据就不执行
                                for (int i = 0; i < listStr.size(); i++) {
                                    if (listStr.get(i).equals(strs.substring(strs.indexOf(":") + 1).toString())) {
                                        hm_area.put("boolean", true);
                                        break;//跳出当前循环
                                    } else {
                                        hm_area.put("boolean", false);
                                    }
                                }
                            } else {
                                hm_area.put("boolean", false);
                            }
                            hm_area.put("position", 0);
                            list_area.add(hm_area);
                        }
                        areaAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
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
                    if (listStr.size() != 0) {
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra("list", (ArrayList<String>) listStr);
                        setResult(FLAG, intent);
                        finish();
                    } else {
                        ToastUtils.showShort(this, "请选择地域");
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
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
