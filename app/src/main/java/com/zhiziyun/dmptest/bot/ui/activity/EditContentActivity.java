package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2018/4/11.
 * 编辑或写跟进
 */

public class EditContentActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title, tv_date, tv_content;
    private EditText et_content;
    private Intent intent;
    private MyDialog dialog;
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_edit);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        intent = getIntent();
        dialog = MyDialog.showDialog(EditContentActivity.this);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(intent.getStringExtra("title"));
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(gettodayDate());
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(intent.getStringExtra("msg"));
        et_content = (EditText) findViewById(R.id.et_content);
        if (intent.getIntExtra("flag", 3) != 9527) {
            et_content.setText(intent.getStringExtra("content"));
            et_content.setSelection(intent.getStringExtra("content").length());
        }
        if (intent.getIntExtra("flag", 3) == 9527) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_save).setOnClickListener(this);
        findViewById(R.id.page).setOnClickListener(this);
        tv_date.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                hiddenKeyboard(v);
                finish();
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_content.getText().toString())) {
                    ToastUtils.showShort(EditContentActivity.this, "请将数据填完整");
                } else {
                    if (intent.getIntExtra("flag", 3) == 9527) {
                        saveFollow();
                    } else {
                        saveData(intent.getIntExtra("flag", 3));
                    }
                }
                break;
            case R.id.page:
                hiddenKeyboard(v);
                break;
            case R.id.tv_date:
                hiddenKeyboard(v);
                //显示日期选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tv_date.setText(getTime(date));
                        tv_date.setTextColor(EditContentActivity.this.getResources().getColor(R.color.defaultcolor));
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, true})// 对应年月日时分秒
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setLabel("年", "月", "日", "时", "分", "秒")
                        .build();

                pvTime.show();
                break;
        }
    }

    public void hiddenKeyboard(View v) {
        //隐藏键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public void saveData(final int flag) {
        dialog.show();
        //编辑
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    json.put("id", intent.getStringExtra("id"));
                    if (flag == 0) {
                        json.put("name", et_content.getText().toString());
                    } else if (flag == 1) {
                        json.put("charger", et_content.getText().toString());
                    } else if (flag == 2) {
                        json.put("desc", et_content.getText().toString());
                    }
                    OkHttpClient client = new OkHttpClient();
                    String url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "guestFromProbe/edit.action")
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
                                String str = response.body().string();
                                JSONObject jsonObject = new JSONObject(str);
                                if (jsonObject.get("success").toString().equals("true")) {
                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = jsonObject.get("msg");
                                    handler.sendMessage(message);
                                } else {
                                    Message message = new Message();
                                    message.what = 2;
                                    message.obj = jsonObject.get("msg");
                                    handler.sendMessage(message);
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

    public void saveFollow() {
        dialog.show();
        //插入沟通记录
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    json.put("guestId", intent.getStringExtra("id"));
                    json.put("time", tv_date.getText().toString());
                    json.put("desc", et_content.getText().toString());
                    json.put("duration", 60);
                    OkHttpClient client = new OkHttpClient();
                    String url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "guestFromProbe/insertSchedule.action")
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
                                if (jsonObject.get("success").toString().equals("true")) {
                                    Message message = new Message();
                                    message.what = 3;
                                    message.obj = jsonObject.get("msg");
                                    handler.sendMessage(message);
                                } else {
                                    Message message = new Message();
                                    message.what = 4;
                                    message.obj = jsonObject.get("msg");
                                    handler.sendMessage(message);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    ToastUtils.showShort(EditContentActivity.this, msg.obj.toString());
                    dialog.dismiss();
                    Intent it = new Intent();
                    it.putExtra("content", et_content.getText().toString());
                    setResult(intent.getIntExtra("flag", 3), it);
                    finish();
                    break;
                case 2:
                    ToastUtils.showShort(EditContentActivity.this, msg.obj.toString());
                    dialog.dismiss();
                    break;
                case 3:
                    ToastUtils.showShort(EditContentActivity.this, msg.obj.toString());
                    dialog.dismiss();
                    finish();
                    break;
                case 4:
                    ToastUtils.showShort(EditContentActivity.this, msg.obj.toString());
                    dialog.dismiss();
                    break;
            }
        }
    };
}
