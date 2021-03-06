package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.race604.drawable.wave.WaveDrawable;
import com.zhiziyun.dmptest.bot.BuildConfig;
import com.zhiziyun.dmptest.bot.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/4/25.
 */

public class StartPageActivity extends BaseActivity {
    private ImageView mImageView;
    private WaveDrawable mWaveDrawable;
    private TextView tv_version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度
        mImageView = (ImageView) findViewById(R.id.image);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText(BuildConfig.ENVIRONMENT);
        if (BuildConfig.BASENAME.equals("bot")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.zhiziyunlogo);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("tkb")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.tkbbg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("yxxcr")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.yxxcrbg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("lkb")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.lkbbg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("shly")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.shlybg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("xmgy")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.xmgybg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("syhz")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.syhzbg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("zhid")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.zhidianstart);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("jkb")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.jkb_bg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("xmf")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.xmf_bg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("emigou")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.xmf_bg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("marketing")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.marketingbg);
            initWaveDrawable();
        }
        if (BuildConfig.BASENAME.equals("skl")) {
            mWaveDrawable = new WaveDrawable(this, R.drawable.sklbg);
            initWaveDrawable();
        }
        final Intent it = new Intent(this, LoginActivity.class); //你要转向的Activity
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
                toFinish();
                finish();
            }
        };
        timer.schedule(task, 1000 * 3); //3秒后
    }

    @Override
    public void onBackPressed() {
        toFinish();
        finish();
    }

    //清空内存
    private void toFinish() {
        try {
            mImageView = null;
            mWaveDrawable = null;
            tv_version = null;
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initWaveDrawable() {
        mImageView.setImageDrawable(mWaveDrawable);
        mWaveDrawable.setLevel(3935);
        mWaveDrawable.setWaveAmplitude(11);
        mWaveDrawable.setWaveLength(160);
        mWaveDrawable.setWaveSpeed(5);
        mWaveDrawable.setIndeterminate(true);
    }
}
