package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CheckBoxAdapter;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 * 选择手机品牌
 */

public class ChooseBrandActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_crowd;
    private SharedPreferences share;
    private List<HashMap<String, Object>> list = new ArrayList<>();
    HashMap<String, Object> map;
    private CheckBoxAdapter cbAdapter;
    private List<String> listStr = new ArrayList<String>();
    private final int FLAG = 1702;
    private int flag = 0;
    private LinearLayout line_page;
    private String[] noLimit = {"苹果", "三星", "华为", "OPPO", "VIVO", "小米", "魅族", "索尼"};
    private String[] apple = {"苹果"};
    private String[] android = {"三星", "华为", "OPPO", "VIVO", "小米", "魅族", "索尼"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_brand);
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
        flag = getIntent().getIntExtra("flag", 0);
        line_page = findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        lv_crowd = (ListView) findViewById(R.id.lv_crowd);

        if (getIntent().getStringExtra("brand").toString().equals("不限")) {
            NoLimit();
        } else if (getIntent().getStringExtra("brand").toString().equals("安卓")) {
            Android();
        } else {
            Apple();
        }
    }

    public void NoLimit() {
        for (int i = 0; i < noLimit.length; i++) {
            map = new HashMap<>();
            map.put("name", noLimit[i]);
            map.put("tagIds", noLimit[i]);
            if (flag != 0) {//如果不是携带数据进来的就不执行
                for (int j = 0; j < getIntent().getStringArrayListExtra("list").size(); j++) {
                    if (noLimit[i].equals(getIntent().getStringArrayListExtra("list").get(j))) {
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
            cbAdapter = new CheckBoxAdapter(ChooseBrandActivity.this, list);
            lv_crowd.setAdapter(cbAdapter);
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
        }
    }

    public void Android() {
        for (int i = 0; i < android.length; i++) {
            map = new HashMap<>();
            map.put("name", android[i]);
            map.put("tagIds", android[i]);
            if (flag != 0) {//如果不是携带数据进来的就不执行
                for (int j = 0; j < getIntent().getStringArrayListExtra("list").size(); j++) {
                    if (android[i].equals(getIntent().getStringArrayListExtra("list").get(j))) {
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
            cbAdapter = new CheckBoxAdapter(ChooseBrandActivity.this, list);
            lv_crowd.setAdapter(cbAdapter);
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
        }
    }

    public void Apple() {
        for (int i = 0; i < apple.length; i++) {
            map = new HashMap<>();
            map.put("name", apple[i]);
            map.put("tagIds", apple[i]);
            if (flag != 0) {//如果不是携带数据进来的就不执行
                for (int j = 0; j < getIntent().getStringArrayListExtra("list").size(); j++) {
                    if (apple[i].equals(getIntent().getStringArrayListExtra("list").get(j))) {
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
            cbAdapter = new CheckBoxAdapter(ChooseBrandActivity.this, list);
            lv_crowd.setAdapter(cbAdapter);
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
        }
    }

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
                        ToastUtils.showShort(ChooseBrandActivity.this, "请选择品牌");
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
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
