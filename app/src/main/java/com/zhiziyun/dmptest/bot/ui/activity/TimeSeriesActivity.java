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
import com.zhiziyun.dmptest.bot.adapter.CheckBoxAdapter.ViewCache;
import com.zhiziyun.dmptest.bot.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2018/1/8.
 * 投放时段
 */

public class TimeSeriesActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_tanzhen;
    private SharedPreferences share;
    private List<HashMap<String, Object>> list;
    private CheckBoxAdapter cbAdapter;
    private List<String> listStr = new ArrayList<String>();
    private final int FLAG = 1407;
    private int flag = 0;
    private String[] str_time = {"0:00 - 0:59", "1:00 - 1:59", "2:00 - 2:59", "3:00 - 3:59", "4:00 - 4:59", "5:00 - 5:59"
            , "6:00 - 6:59", "7:00 - 7:59", "8:00 - 8:59", "9:00 - 9:59", "10:00 - 10:59", "11:00 - 11:59", "12:00 - 12:59"
            , "13:00 - 13:59", "14:00 - 14:59", "15:00 - 15:59", "16:00 - 16:59", "17:00 - 17:59", "18:00 - 18:59"
            , "19:00 - 19:59", "20:00 - 20:59", "21:00 - 21:59", "22:00 - 22:59", "23:00 - 23:59"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeseries);
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

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        lv_tanzhen = (ListView) findViewById(R.id.lv_tanzhen);

        flag = getIntent().getIntExtra("flag", 0);
        requestTanzhen();
    }

    public void requestTanzhen() {
        list = new ArrayList<>();
        for (int i = 0; i < str_time.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", str_time[i]);
            map.put("mac", i + "");
            if (flag != 0) {//如果不是携带数据进来的就不执行
                for (int j = 0; j < getIntent().getStringArrayListExtra("list").size(); j++) {
                    if (getIntent().getStringArrayListExtra("list").contains("" + i)) {
                        map.put("boolean", true);
                        //为保持一致性，将数据存入集合
                        listStr.add("" + i);
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
        cbAdapter = new CheckBoxAdapter(TimeSeriesActivity.this, list);
        lv_tanzhen.setAdapter(cbAdapter);
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
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("list", (ArrayList<String>) listStr);
                    setResult(FLAG, intent);
                    finish();
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
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
