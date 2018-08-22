package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CheckBoxAdapter;
import com.zhiziyun.dmptest.bot.util.ToastUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/5.
 * //选择学历或婚姻状况
 */

public class FriendsChooseActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private ListView listView;
    private List<HashMap<String, Object>> list = new ArrayList<>();
    private CheckBoxAdapter adapter;
    private List<String> list_value = new ArrayList<>();
    private List<String> list_key = new ArrayList<>();
    private final int Flag_educationTypes = 3827;
    private final int Flag_relationshipStatus = 8277;
    private List<String> listStr = new ArrayList<String>();
    private int FLAG = 0;
    private int FLAGS = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_choose);
        initView();
    }

    private void initView() {
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        FLAG = getIntent().getIntExtra("flag", 0);
        FLAGS = getIntent().getIntExtra("flags", 0);
        tv_title = findViewById(R.id.tv_title);
        listView = findViewById(R.id.listView);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);

        adapter = new CheckBoxAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBoxAdapter.ViewCache viewCache = (CheckBoxAdapter.ViewCache) view.getTag();
                viewCache.cb.toggle();
                list.get(position).put("boolean", viewCache.cb.isChecked());
                adapter.notifyDataSetChanged();
                if (viewCache.cb.isChecked()) {//被选中状态
                    listStr.add(list.get(position).get("mac").toString());
                } else {//从选中状态转化为未选中
                    listStr.remove(list.get(position).get("mac").toString());
                }
            }
        });
        getJson(getIntent().getStringExtra("json"));
        //通过判断从哪里进来来显示标题
        if (getIntent().getIntExtra("flag", 0) == Flag_educationTypes) {
            tv_title.setText("选择学历");
        } else if (getIntent().getIntExtra("flag", 0) == Flag_relationshipStatus) {
            tv_title.setText("选择婚姻状况");
        }
    }

    @Override
    public void onClick(View v) {//点击完成
        switch (v.getId()) {
            case R.id.btn_confirm:
                try {
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("list", (ArrayList<String>) listStr);
                    setResult(FLAG, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void getJson(String strs) {
        try {
            list_key.clear();
            list_value.clear();
            Map maps = (Map) JSON.parse(strs);
            for (Object map : maps.entrySet()) {
                list_key.add(((Map.Entry) map).getKey().toString());
                list_value.add(((Map.Entry) map).getValue().toString());
            }
            for (int i = 0; i < list_key.size(); i++) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", list_value.get(i));
                hashMap.put("mac", list_key.get(i));
                if (FLAGS != 0) {
                    for (int j = 0; j < getIntent().getStringArrayListExtra("list").size(); j++) {
                        if (list_key.get(i).
                                equals(getIntent().getStringArrayListExtra("list").get(j))) {
                            hashMap.put("boolean", true);
                            //为保持一致性，将数据存入集合
                            listStr.add(getIntent().getStringArrayListExtra("list").get(j));
                            break;
                        } else {
                            hashMap.put("boolean", false);
                        }
                    }
                } else {
                    hashMap.put("boolean", false);
                }
                list.add(hashMap);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
