package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.MyActivityManager;
import com.zhiziyun.dmptest.bot.util.ToastUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/27.
 */

public class CrowdSourceActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_crowd, tv_wifi_crowd, tv_click_crowd;
    private final int Flag_corwd = 4325;
    private final int Flag_wifi_corwd = 8436;
    private final int Flag_click_corwd = 9732;
    private String crowdId = null;
    private String crowdName = null;
    private List<String> list_corwd = new ArrayList<>();
    private List<String> list_wifi_corwd = new ArrayList<>();
    private List<String> list_click_corwd = new ArrayList<>();
    private ArrayList<String> mListTotal = new ArrayList<>();
    private Intent intent;
    private String mBean, mCreativeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd_source);
        MyActivityManager.add(CrowdSourceActivity.this);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
//        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_system.getLayoutParams();
//        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        mBean = getIntent().getStringExtra("advert");
        mCreativeId = getIntent().getStringExtra("creativeId");
        intent = getIntent();
        tv_crowd = findViewById(R.id.tv_crowd);
        tv_wifi_crowd = findViewById(R.id.tv_wifi_crowd);
        tv_click_crowd = findViewById(R.id.tv_click_crowd);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.rl_crowd).setOnClickListener(this);
        findViewById(R.id.rl_wifi_crowd).setOnClickListener(this);
        findViewById(R.id.rl_click_crowd).setOnClickListener(this);
        findViewById(R.id.bt_commit).setOnClickListener(this);

        if (intent.getIntExtra("flag", 0) == 123) {//说明携带数据进来
            try {
                list_corwd = intent.getStringArrayListExtra("list");
                list_wifi_corwd = intent.getStringArrayListExtra("list_wifi");
                list_click_corwd = intent.getStringArrayListExtra("list_click");
                tv_crowd.setText(list_corwd.size() + "个");
                tv_wifi_crowd.setText(list_wifi_corwd.size() + "个");
                tv_click_crowd.setText(list_click_corwd.size() + "个");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_crowd:
                //通过判断list_originality有没有值来确定要不要将值传入进入现实选中状态
                if (list_corwd.isEmpty()) {//无值
                    Intent it_crowd = new Intent(CrowdSourceActivity.this, ChooseCrowdActivity.class);
                    it_crowd.putExtra("flags", 1);
                    startActivityForResult(it_crowd, Flag_corwd);
                } else {//有值的话将值传过去显示选中状态
                    Intent it_crowd = new Intent(CrowdSourceActivity.this, ChooseCrowdActivity.class);
                    it_crowd.putExtra("flags", 1);
                    it_crowd.putExtra("flag", 123);
                    it_crowd.putStringArrayListExtra("list", (ArrayList<String>) list_corwd);
                    startActivityForResult(it_crowd, Flag_corwd);
                }
                break;
            case R.id.rl_wifi_crowd:
                //通过判断list_originality有没有值来确定要不要将值传入进入现实选中状态
                if (list_wifi_corwd.isEmpty()) {//无值
                    Intent it_wifi_crowd = new Intent(CrowdSourceActivity.this, ChooseCrowdActivity.class);
                    it_wifi_crowd.putExtra("flags", 2);
                    startActivityForResult(it_wifi_crowd, Flag_wifi_corwd);
                } else {//有值的话将值传过去显示选中状态
                    Intent it_wifi_crowd = new Intent(CrowdSourceActivity.this, ChooseCrowdActivity.class);
                    it_wifi_crowd.putExtra("flags", 2);
                    it_wifi_crowd.putExtra("flag", 123);
                    it_wifi_crowd.putStringArrayListExtra("list", (ArrayList<String>) list_wifi_corwd);
                    startActivityForResult(it_wifi_crowd, Flag_wifi_corwd);
                }
                break;
            case R.id.rl_click_crowd:
                //通过判断list_originality有没有值来确定要不要将值传入进入现实选中状态
                if (list_click_corwd.isEmpty()) {//无值
                    Intent it_click_crowd = new Intent(CrowdSourceActivity.this, ChooseCrowdActivity.class);
                    it_click_crowd.putExtra("flags", 3);
                    startActivityForResult(it_click_crowd, Flag_click_corwd);
                } else {//有值的话将值传过去显示选中状态
                    Intent it_click_crowd = new Intent(CrowdSourceActivity.this, ChooseCrowdActivity.class);
                    it_click_crowd.putExtra("flags", 3);
                    it_click_crowd.putExtra("flag", 123);
                    it_click_crowd.putStringArrayListExtra("list", (ArrayList<String>) list_click_corwd);
                    startActivityForResult(it_click_crowd, Flag_click_corwd);
                }
                break;
            case R.id.bt_commit:
                try {
                    if (list_corwd.isEmpty() && list_wifi_corwd.isEmpty() && list_click_corwd.isEmpty()) {
                        ToastUtils.showShort(CrowdSourceActivity.this, "请选择人群");
                    } else {
                        mListTotal.clear();
                        Intent intent = new Intent(CrowdSourceActivity.this, ChooseCreativeActivity.class);
//                        it.putStringArrayListExtra("list", (ArrayList<String>) list_corwd);
//                        it.putStringArrayListExtra("list_wifi", (ArrayList<String>) list_wifi_corwd);
//                        it.putStringArrayListExtra("list_click", (ArrayList<String>) list_click_corwd);
//                        setResult(1702, it);
//                        finish();
                        mListTotal.addAll(list_corwd);
                        mListTotal.addAll(list_wifi_corwd);
                        mListTotal.addAll(list_click_corwd);
                        intent.putStringArrayListExtra("Total", mListTotal);
                        intent.putExtra("advert", mBean);
                        intent.putExtra("flag", "1");
                        intent.putExtra("creativeId", mCreativeId);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //区分从哪个页面回传的数据
            case Flag_corwd://人群
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        //用集合来接收这个集合
                        list_corwd = data.getStringArrayListExtra("list");
                        int num = list_corwd.size();
                        if (num != 0) {
                            tv_crowd.setText(num + "个");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Flag_wifi_corwd:
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        //用集合来接收这个集合
                        list_wifi_corwd = data.getStringArrayListExtra("list");
                        int num = list_wifi_corwd.size();
                        if (num != 0) {
                            tv_wifi_crowd.setText(num + "个");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Flag_click_corwd:
                try {
                    if (!data.getStringArrayListExtra("list").isEmpty()) {
                        //用集合来接收这个集合
                        list_click_corwd = data.getStringArrayListExtra("list");
                        int num = list_click_corwd.size();
                        if (num != 0) {
                            tv_click_crowd.setText(num + "个");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
