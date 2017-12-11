package com.zhiziyun.dmptest.bot.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.HomePageAdapter;

public class HomePageActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout lv_homepage, lv_generalize, lv_visitors, lv_my;
    private ImageView iv_homepage, iv_visitors, iv_generalize, iv_my;
    private TextView tv_homepage, tv_visitors, tv_generalize, tv_my;
    private HomePageAdapter homePageAdapter = null;
    public ViewPager pager = null;
    //退出时的时间
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        iv_homepage = (ImageView) findViewById(R.id.iv_homepage);
        iv_visitors = (ImageView) findViewById(R.id.iv_visitors);
        iv_generalize = (ImageView) findViewById(R.id.iv_generalize);
        iv_my = (ImageView) findViewById(R.id.iv_my);

        tv_homepage = (TextView) findViewById(R.id.tv_homepage);
        tv_visitors = (TextView) findViewById(R.id.tv_visitors);
        tv_generalize = (TextView) findViewById(R.id.tv_generalize);
        tv_my = (TextView) findViewById(R.id.tv_my);

        lv_homepage = (LinearLayout) findViewById(R.id.lv_homepage);
        lv_visitors = (LinearLayout) findViewById(R.id.lv_visitors);
        lv_generalize = (LinearLayout) findViewById(R.id.lv_generalize);
        lv_my = (LinearLayout) findViewById(R.id.lv_my);
        lv_homepage.setOnClickListener(this);
        lv_visitors.setOnClickListener(this);
        lv_generalize.setOnClickListener(this);
        lv_my.setOnClickListener(this);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(4);//
        homePageAdapter = new HomePageAdapter(getSupportFragmentManager());
        pager.setAdapter(homePageAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    changeColor(true, false, false, false);
                } else if (position == 1) {
                    changeColor(false, true, false, false);
                } else if (position == 2) {
                    changeColor(false, false, true, false);
                } else {
                    changeColor(false, false, false, true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //一进来就显示首页的fragment
        pager.setCurrentItem(0);
        changeColor(true, false, false, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lv_homepage:
                pager.setCurrentItem(0);
                changeColor(true, false, false, false);
                break;
            case R.id.lv_visitors:
                pager.setCurrentItem(1);
                changeColor(false, true, false, false);
                break;
            case R.id.lv_generalize:
                pager.setCurrentItem(2);
                changeColor(false, false, true, false);
                break;
            case R.id.lv_my:
                pager.setCurrentItem(3);
                changeColor(false, false, false, true);
                break;
        }
    }

    public void changeColor(boolean homepage, boolean visitors, boolean generalize, boolean my) {
        if (homepage) {
            iv_homepage.setImageResource(R.drawable.home_b);
            tv_homepage.setTextColor(this.getResources().getColor(R.color.blue));
        } else {
            iv_homepage.setImageResource(R.drawable.home);
            tv_homepage.setTextColor(this.getResources().getColor(R.color.gray));
        }

        if (visitors) {
            iv_visitors.setImageResource(R.drawable.visitors_b);
            tv_visitors.setTextColor(this.getResources().getColor(R.color.blue));
        } else {
            iv_visitors.setImageResource(R.drawable.visitors);
            tv_visitors.setTextColor(this.getResources().getColor(R.color.gray));
        }

        if (generalize) {
            iv_generalize.setImageResource(R.drawable.generalize_b);
            tv_generalize.setTextColor(this.getResources().getColor(R.color.blue));
        } else {
            iv_generalize.setImageResource(R.drawable.generalize);
            tv_generalize.setTextColor(this.getResources().getColor(R.color.gray));
        }

        if (my) {
            iv_my.setImageResource(R.drawable.account_b);
            tv_my.setTextColor(this.getResources().getColor(R.color.blue));
        } else {
            iv_my.setImageResource(R.drawable.account);
            tv_my.setTextColor(this.getResources().getColor(R.color.gray));
        }
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        //判断是否为android6.0系统版本，如果是，需要动态添加权限
        if (Build.VERSION.SDK_INT>=23){
            showContacts();
        }
    }

    public void showContacts(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(HomePageActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                }
                break;
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
