package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.CustomDialog;
import com.zhiziyun.dmptest.bot.util.EditDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/9/19.
 */

public class TanzhenDetails extends BaseActivity implements View.OnClickListener {
    private TextView tv_name, tv_stores, tv_tanzhen;
    private Intent it;
    private SharedPreferences share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanzhen_details);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        it = getIntent();
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(it.getStringExtra("name"));
        tv_stores = findViewById(R.id.tv_stores);
        tv_stores.setText(it.getStringExtra("stores"));
        tv_tanzhen = findViewById(R.id.tv_tanzhen);
        tv_tanzhen.setText(it.getStringExtra("mac"));

        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_edit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                //点击弹出对话框
                final CustomDialog customDialog = new CustomDialog(this);
                customDialog.setTitle("消息提示");
                customDialog.setMessage("是否要解绑该探针");
                customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        unbindTanzhen(it.getStringExtra("id"));
                        customDialog.dismiss();
                    }
                });
                customDialog.setNoOnclickListener("取消", new CustomDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit://编辑
                if (it.getStringExtra("valid").toString().equals("true")) {
                    inputArea(v, Integer.parseInt(it.getStringExtra("id")));
                } else {
                    ToastUtils.showShort(this, "已过期，请续费");
                }
                break;
        }
    }

    public void unbindTanzhen(final String id) {
        //解绑探针接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("id", id);
                    json.put("userId", share.getString("userid", ""));
                    json.put("userName", share.getString("userName", ""));
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "probe/delete")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.get("msg").equals("解除探针成功")) {
                                    handler.sendEmptyMessage(3);
                                } else {
                                    handler.sendEmptyMessage(4);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void inputArea(final View view, final int id) {
        //点击弹出对话框,与输入手机号码的对话框共用
        final EditDialog editDialog = new EditDialog(TanzhenDetails.this);
        editDialog.setTitle("请输入建筑半径");
        editDialog.setYesOnclickListener("确定", new EditDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String phone) {
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort(TanzhenDetails.this, "请输入建筑半径");
                } else {
                    if (phone.matches("[0-9]+")) {//如果是数字
                        if (phone.length() <= 40) {//如果手机格式正确
                            try {
                                //先根据半径计算出面积
                                float area = (float) Math.PI * Float.parseFloat(phone) * Float.parseFloat(phone);
                                editArea(area, id);
                                //让软键盘隐藏
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                                editDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShort(TanzhenDetails.this, "超出长度");
                        }
                    } else {//如果不是数字
                        ToastUtils.showShort(TanzhenDetails.this, "请输入数字");
                    }
                }
            }
        });

        editDialog.setNoOnclickListener("取消", new EditDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                editDialog.dismiss();
            }
        });
        editDialog.show();
    }

    public void editArea(final float area, final int id) {
        //修改探针
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("id", id);
                    json.put("floorArea", area);
                    json.put("userId", share.getString("userid", ""));
                    json.put("userName", share.getString("userName", ""));
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseTest + "probe/edit")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject json = new JSONObject(response.body().string());
                                Message msg = new Message();
                                if (json.get("success").toString().equals("true")) {
                                    msg.what = 1;
                                    msg.obj = json.get("msg");
                                } else {
                                    msg.what = 2;
                                    msg.obj = json.get("msg");
                                }
                                handler.sendMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastUtils.showShort(TanzhenDetails.this, msg.obj.toString());
                    finish();
                    break;
                case 2:
                    ToastUtils.showShort(TanzhenDetails.this, msg.obj.toString());
                    break;
                case 3:
                    ToastUtils.showShort(TanzhenDetails.this, "删除成功");
                    finish();
                    break;
                case 4:
                    ToastUtils.showShort(TanzhenDetails.this, "删除失败");
                    break;
            }
        }
    };
}
