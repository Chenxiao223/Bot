package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.ToastUtils;


/**
 * Created by Administrator on 2018/5/7.
 * 修改绑定号码
 */

public class ChangePhoneNMActivity extends BaseActivity implements View.OnClickListener {
    private EditText edit_phone_number;
    private Intent intent;
    private SharedPreferences.Editor editors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pn);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        intent = getIntent();
        edit_phone_number = (EditText) findViewById(R.id.edit_phone_number);
        edit_phone_number.setText(intent.getStringExtra("phoneNM"));

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.page).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.btn_commit:
                String text = edit_phone_number.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    if (isMobile(text)) {
                        saveTel(text);
                        SettingActivity.activity.tv_phone_number.setText(text);
                        toFinish();
                        finish();
                    } else {
                        ToastUtils.showShort(ChangePhoneNMActivity.this, "手机号不合法");
                    }
                } else {
                    ToastUtils.showShort(ChangePhoneNMActivity.this, "请输入电话号码");
                }
                break;
            case R.id.page:
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }

    public void saveTel(String tel) {
        SharedPreferences sharedPreferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
        editors = sharedPreferences.edit();
        editors.putString("tel", tel);
        editors.commit();//提交
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        //"[1]"代表第1位为数字1，"[345789]"代表第二位可以为3、4、5、7、8、9中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String num = "[1][345789]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
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
                    edit_phone_number = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }

}
