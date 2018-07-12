package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youth.banner.Banner;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/11.
 */

public class ChooseCreativeActivity extends BaseActivity implements View.OnClickListener {
    private Banner banner;
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_creative);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.rl_template).setOnClickListener(this);
        findViewById(R.id.rl_material).setOnClickListener(this);
        findViewById(R.id.rl_promote).setOnClickListener(this);

        List<String> list = new ArrayList<>();
        list.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180109085038_4A7atU_rakukoo_9_1_2018_8_50_25_276.jpeg");
        list.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180102083655_3t4ytm_Screenshot.jpeg");
        list.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg");

        banner = (Banner) findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        banner.isAutoPlay(false);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.rl_template:
                break;
            case R.id.rl_material:
                startActivity(new Intent(this, UploadMaterialActivity.class));
                break;
            case R.id.rl_promote:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
