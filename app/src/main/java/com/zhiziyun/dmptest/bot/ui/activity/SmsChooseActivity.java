package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.ToastUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 * 从添加短信活动进来选择客户类型和跟进状态
 */

public class SmsChooseActivity extends BaseActivity implements View.OnClickListener {
    private TextView text1, text2, text3, text4, tv_title;
    private CheckBox cb1, cb2, cb3, cb4;
    private Boolean bl_1 = false, bl_2 = false, bl_3 = false, bl_4 = false;
    private Intent intent;
    private HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_choose);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        intent = getIntent();
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(intent.getStringExtra("title"));
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        setContent(intent.getIntExtra("flag", 3));
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.rl_a).setOnClickListener(this);
        findViewById(R.id.rl_b).setOnClickListener(this);
        findViewById(R.id.rl_c).setOnClickListener(this);
        findViewById(R.id.rl_d).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);

        try {
            ArrayList<Integer> list = intent.getIntegerArrayListExtra("list");
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    setState(list.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContent(int flag) {
        switch (flag) {
            case 1:
                text1.setText("普通客户");
                text2.setText("低价值客户");
                text3.setText("积极客户");
                text4.setText("高价值客户");
                break;
            case 2:
                text1.setText("新转入");
                text2.setText("暂无意向");
                text3.setText("持续跟进");
                text4.setText("已成交");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_a:
                if (bl_1) {
                    bl_1 = false;
                    cb1.setChecked(false);
                    map.put("a", "");
                } else {
                    bl_1 = true;
                    cb1.setChecked(true);
                    map.put("a", "0");
                }
                break;
            case R.id.rl_b:
                if (bl_2) {
                    bl_2 = false;
                    cb2.setChecked(false);
                    map.put("b", "");
                } else {
                    bl_2 = true;
                    cb2.setChecked(true);
                    map.put("b", "1");
                }
                break;
            case R.id.rl_c:
                if (bl_3) {
                    bl_3 = false;
                    cb3.setChecked(false);
                    map.put("c", "");
                } else {
                    bl_3 = true;
                    cb3.setChecked(true);
                    map.put("c", "2");
                }
                break;
            case R.id.rl_d:
                if (bl_4) {
                    bl_4 = false;
                    cb4.setChecked(false);
                    map.put("d", "");
                } else {
                    bl_4 = true;
                    cb4.setChecked(true);
                    map.put("d", "3");
                }
                break;
            case R.id.btn_commit:
                try {
                    if (map.size() != 0) {
                        //遍历map，确定选中了几条
                        Iterator it = map.keySet().iterator();
                        List<Integer> list = new ArrayList<>();
                        while (it.hasNext()) {
                            String key = (String) it.next();
                            if (!TextUtils.isEmpty(map.get(key))) {
                                list.add(Integer.parseInt(map.get(key)));
                            }
                        }
                        Intent iti = new Intent();
                        iti.putIntegerArrayListExtra("list", (ArrayList<Integer>) list);
                        setResult(intent.getIntExtra("flag", 3), iti);
                        finish();
                    } else {
                        ToastUtils.showShort(SmsChooseActivity.this, "请选择");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void setState(int num) {
        switch (num) {
            case 0:
                bl_1 = true;
                cb1.setChecked(true);
                map.put("a", "0");
                break;
            case 1:
                bl_2 = true;
                cb2.setChecked(true);
                map.put("b", "1");
                break;
            case 2:
                bl_3 = true;
                cb3.setChecked(true);
                map.put("c", "2");
                break;
            case 3:
                bl_4 = true;
                cb4.setChecked(true);
                map.put("d", "3");
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
                    text1 = null;
                    text2 = null;
                    text3 = null;
                    text4 = null;
                    tv_title = null;
                    map.clear();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
