package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhiziyun.dmptest.bot.R;

/**
 * Created by Administrator on 2018/6/4.
 */

public class ShowTemplateActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_gt1, iv_gt2, iv_gt3, iv_gt4, iv_gt5, iv_gt6, iv_gt7, iv_gt8, iv_gt9, iv_gt10, iv_gt11;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_template);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        intent = getIntent();
        iv_gt1 = findViewById(R.id.iv_gt1);
        iv_gt1.setOnClickListener(this);
        iv_gt2 = findViewById(R.id.iv_gt2);
        iv_gt2.setOnClickListener(this);
        iv_gt3 = findViewById(R.id.iv_gt3);
        iv_gt3.setOnClickListener(this);
        iv_gt4 = findViewById(R.id.iv_gt4);
        iv_gt4.setOnClickListener(this);
        iv_gt5 = findViewById(R.id.iv_gt5);
        iv_gt5.setOnClickListener(this);
        iv_gt6 = findViewById(R.id.iv_gt6);
        iv_gt6.setOnClickListener(this);
        iv_gt7 = findViewById(R.id.iv_gt7);
        iv_gt7.setOnClickListener(this);
        iv_gt8 = findViewById(R.id.iv_gt8);
        iv_gt8.setOnClickListener(this);
        iv_gt9 = findViewById(R.id.iv_gt9);
        iv_gt9.setOnClickListener(this);
        iv_gt10 = findViewById(R.id.iv_gt10);
        iv_gt10.setOnClickListener(this);
        iv_gt11 = findViewById(R.id.iv_gt11);
        iv_gt11.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        if (intent.getIntExtra("flag", 0) == 1) {
            iv_gt1.setVisibility(View.VISIBLE);
            iv_gt2.setVisibility(View.VISIBLE);
        } else if (getIntent().getIntExtra("flag", 0) == 2) {
            iv_gt3.setVisibility(View.VISIBLE);
            iv_gt4.setVisibility(View.VISIBLE);
        } else if (getIntent().getIntExtra("flag", 0) == 3) {
            iv_gt5.setVisibility(View.VISIBLE);
            iv_gt6.setVisibility(View.VISIBLE);
        } else if (getIntent().getIntExtra("flag", 0) == 5) {
            iv_gt7.setVisibility(View.VISIBLE);
            iv_gt8.setVisibility(View.VISIBLE);
        } else if (getIntent().getIntExtra("flag", 0) == 4) {
            iv_gt9.setVisibility(View.VISIBLE);
            iv_gt10.setVisibility(View.VISIBLE);
        } else if (getIntent().getIntExtra("flag", 0) == 9) {
            iv_gt11.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_gt1:
                Intent it = new Intent(this, GeneralTemplateA.class);
                it.putExtra("flag", 1);//用以区分点击了哪个模板
                it.putExtra("size", "320*50");//将选择的模板的尺寸传过去
                startActivity(it);
                break;
            case R.id.iv_gt2:
                Intent it2 = new Intent(this, GeneralTemplateA.class);
                it2.putExtra("flag", 2);//用以区分点击了哪个模板
                it2.putExtra("size", "320*50");//将选择的模板的尺寸传过去
                startActivity(it2);
                break;
            case R.id.iv_gt3:

                break;
            case R.id.iv_gt4:

                break;
            case R.id.iv_gt5:
                Intent it5 = new Intent(this, GeneralTemplateA.class);
                it5.putExtra("flag", 1);//用以区分点击了哪个模板
                it5.putExtra("size", "640*100");//将选择的模板的尺寸传过去
                it5.putExtra("flags", 2);
                startActivity(it5);
                break;
            case R.id.iv_gt6:
                Intent it6 = new Intent(this, GeneralTemplateA.class);
                it6.putExtra("flag", 2);//用以区分点击了哪个模板
                it6.putExtra("size", "640*100");//将选择的模板的尺寸传过去
                it6.putExtra("flags", 2);
                startActivity(it6);
                break;
            case R.id.iv_gt7:
                Intent it7 = new Intent(this, GeneralTemplateC.class);
                it7.putExtra("flag", 1);//用以区分点击了哪个模板
                it7.putExtra("size", "728*90");//将选择的模板的尺寸传过去
                startActivity(it7);
                break;
            case R.id.iv_gt8:
                Intent it8 = new Intent(this, GeneralTemplateC.class);
                it8.putExtra("flag", 2);//用以区分点击了哪个模板
                it8.putExtra("size", "728*90");//将选择的模板的尺寸传过去
                startActivity(it8);
                break;
            case R.id.iv_gt9:
                Intent it3 = new Intent(this, GeneralTemplateB.class);
                it3.putExtra("flag", 1);//用以区分点击了哪个模板
                it3.putExtra("size", "640*960");//将选择的模板的尺寸传过去
                startActivity(it3);
                break;
            case R.id.iv_gt10:
                Intent it4 = new Intent(this, GeneralTemplateB.class);
                it4.putExtra("flag", 2);//用以区分点击了哪个模板
                it4.putExtra("size", "640*960");//将选择的模板的尺寸传过去
                startActivity(it4);
                break;
            case R.id.iv_gt11:
                Intent it11 = new Intent(this, GeneralTemplateB.class);
                it11.putExtra("flag", 3);//用以区分点击了哪个模板
                it11.putExtra("size", "600*500");//将选择的模板的尺寸传过去
                startActivity(it11);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
