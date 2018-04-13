package com.zhiziyun.dmptest.bot.ui.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.CallInfo;
import com.zhiziyun.dmptest.bot.entity.Customer;
import com.zhiziyun.dmptest.bot.ui.fragment.CustomerFragment;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.EditDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import co.lujun.androidtagview.TagContainerLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/4.
 * 客户详情
 */

public class DetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_desc, tv_charger, tv_user, tv_type, tv_mark;
    private Intent intent;
    private SharedPreferences share;
    private SharedPreferences.Editor editors;
    public String bindCode = "";
    private CallInfo callInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        editors = share.edit();
        intent = getIntent();
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_type.setText(intent.getStringExtra("type"));
        tv_mark = (TextView) findViewById(R.id.tv_mark);
        tv_mark.setText(intent.getStringExtra("mark"));

        findViewById(R.id.rl_desc).setOnClickListener(this);
        findViewById(R.id.rl_charger).setOnClickListener(this);
        findViewById(R.id.rl_user).setOnClickListener(this);
        findViewById(R.id.rl_type).setOnClickListener(this);
        findViewById(R.id.rl_mark).setOnClickListener(this);
        findViewById(R.id.rl_record).setOnClickListener(this);

        findViewById(R.id.line_write).setOnClickListener(this);
        findViewById(R.id.line_sms).setOnClickListener(this);
        findViewById(R.id.line_call).setOnClickListener(this);

        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_desc.setText(intent.getStringExtra("desc"));
        tv_charger = (TextView) findViewById(R.id.tv_charger);
        tv_charger.setText(intent.getStringExtra("charger"));
        tv_user = (TextView) findViewById(R.id.tv_user);
        tv_user.setText(intent.getStringExtra("name"));

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.line_page).setOnClickListener(this);

        getCustomerSource();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user:
                Intent it_user = new Intent(DetailsActivity.this, EditContentActivity.class);
                it_user.putExtra("title", "编辑客户");
                it_user.putExtra("flag", 0);
                it_user.putExtra("msg", "客户");
                it_user.putExtra("id", intent.getStringExtra("id"));
                it_user.putExtra("content", tv_user.getText().toString());
                startActivityForResult(it_user, 0);
                break;
            case R.id.rl_charger:
                Intent it_charger = new Intent(DetailsActivity.this, EditContentActivity.class);
                it_charger.putExtra("title", "编辑负责人");
                it_charger.putExtra("flag", 1);
                it_charger.putExtra("msg", "负责人");
                it_charger.putExtra("id", intent.getStringExtra("id"));
                it_charger.putExtra("content", tv_charger.getText().toString());
                startActivityForResult(it_charger, 1);
                break;
            case R.id.rl_desc:
                Intent it_desc = new Intent(DetailsActivity.this, EditContentActivity.class);
                it_desc.putExtra("title", "编辑备注");
                it_desc.putExtra("flag", 2);
                it_desc.putExtra("msg", "备注");
                it_desc.putExtra("id", intent.getStringExtra("id"));
                it_desc.putExtra("content", tv_desc.getText().toString());
                startActivityForResult(it_desc, 2);
                break;
            case R.id.rl_type:
                Intent it_type = new Intent(DetailsActivity.this, ChooseActivity.class);
                it_type.putExtra("title", "客户类型");
                it_type.putExtra("flag", 1);
                it_type.putExtra("id", intent.getStringExtra("id"));
                startActivityForResult(it_type, 3);
                break;
            case R.id.rl_mark:
                Intent it_mark = new Intent(DetailsActivity.this, ChooseActivity.class);
                it_mark.putExtra("title", "跟进状态");
                it_mark.putExtra("flag", 2);
                it_mark.putExtra("id", intent.getStringExtra("id"));
                startActivityForResult(it_mark, 4);
                break;
            case R.id.rl_record:
                Intent it_record = new Intent(DetailsActivity.this, CommunicationRecordActivity.class);
                it_record.putExtra("id", intent.getStringExtra("id"));
                startActivity(it_record);
                break;
            case R.id.iv_back:
                //重新加载页面
                CustomerFragment.fragment.clearAllData();
                CustomerFragment.fragment.getData(1, "");
                finish();
                toFinish();
                break;
            case R.id.line_page:
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                break;
            case R.id.line_write:
                if (ClickUtils.isFastClick()) {//防止多次点击
                    Intent it_write = new Intent(DetailsActivity.this, EditContentActivity.class);
                    it_write.putExtra("title", "写跟进");
                    it_write.putExtra("flag", 9527);
                    it_write.putExtra("id", intent.getStringExtra("id"));
                    it_write.putExtra("msg", "跟进内容");
                    startActivity(it_write);
                }
                break;
            case R.id.line_sms:
                if (ClickUtils.isFastClick()) {//防止多次点击
                }
                break;
            case R.id.line_call:
                if (ClickUtils.isFastClick()) {//防止多次点击
                    phone(intent.getStringExtra("id"), v);
                    //打完电话跳转到写跟进页面
                    Intent it_write = new Intent(DetailsActivity.this, EditContentActivity.class);
                    it_write.putExtra("title", "写跟进");
                    it_write.putExtra("flag", 9527);
                    it_write.putExtra("id", intent.getStringExtra("id"));
                    it_write.putExtra("msg", "跟进内容");
                    startActivity(it_write);
                }
                break;
        }
    }

    public void phone(final String id, final View v) {
        bindCode = "";
        //判断是否为android6.0系统版本，如果是，需要动态添加权限
        if (Build.VERSION.SDK_INT >= 23) {
            //拨打电话权限
            if (ContextCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(DetailsActivity.this,
                        Manifest.permission.CALL_PHONE)) {
                    /**
                     * 返回值：
                     如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                     如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                     如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                     弹窗需要解释为何需要该权限，再次请求授权
                     */
                    ToastUtils.showShort(DetailsActivity.this, "请授权！");

                    // 跳转到该应用的设置界面，让用户手动授权
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getAppProcessName(DetailsActivity.this), null);
                    intent.setData(uri);
                    startActivity(intent);
                    ToastUtils.showShort(DetailsActivity.this, "请开启电话权限！");
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
            }
        }

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(DetailsActivity.this.TELEPHONY_SERVICE);
        final String te1 = telephonyManager.getLine1Number();//获取本机号码
        if (TextUtils.isEmpty(te1) && TextUtils.isEmpty(share.getString("phone", ""))) {
            //如果获取不到手机号码就手动输入
            //点击弹出对话框
            final EditDialog editDialog = new EditDialog(DetailsActivity.this);
            editDialog.setTitle("请输入电话号码");
            editDialog.setYesOnclickListener("确定", new EditDialog.onYesOnclickListener() {
                @Override
                public void onYesClick(String phone) {
                    if (TextUtils.isEmpty(phone)) {
                        ToastUtils.showShort(DetailsActivity.this, "请输入电话号码");
                    } else {
                        if (isMobile(phone)) {//如果手机格式正确
                            editors.putString("phone", phone);
                            editors.commit();
                            editDialog.dismiss();
                            //让软键盘隐藏
                            InputMethodManager imm = (InputMethodManager) DetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                        } else {
                            ToastUtils.showShort(DetailsActivity.this, "手机号不合法");
                        }
                    }
                }
            });
            editDialog.setNoOnclickListener("取消", new EditDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    editDialog.dismiss();
                }
            });
            editDialog.show();
        } else {
            //拨打电话接口
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject json = new JSONObject();
                        json.put("siteId", share.getString("siteid", ""));
                        if (TextUtils.isEmpty(te1)) {
                            //如果获取不到手机号就从缓存获取
                            json.put("phoneNumber", share.getString("phone", "").replace("+86", ""));//将电话号码前的+86去掉
                        } else {
                            //能够获取手机号就直接使用
                            json.put("phoneNumber", te1.replace("+86", ""));
                        }
                        json.put("guestId", id);
                        OkHttpClient client = new OkHttpClient();
                        String url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                        RequestBody body = RequestBody.create(mediaType, url);
                        Request request = new Request.Builder()
                                .url(BaseUrl.BaseWang + "dial/phone.action")
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
                                    Gson gson = new Gson();
                                    callInfo = gson.fromJson(response.body().string(), CallInfo.class);
                                    if (callInfo.isSuccess()) {
                                        bindCode = callInfo.getObj();
                                        Message message = new Message();
                                        message.what = 2;
                                        message.obj = callInfo.getMsg();
                                        handler.sendMessage(message);
                                    } else {
                                        handler.sendEmptyMessage(3);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (!TextUtils.isEmpty(bindCode)) {
                Timer timer = new Timer();// 实例化Timer类
                timer.schedule(new TimerTask() {
                    public void run() {
                        handup(bindCode);
                        this.cancel();
                    }
                }, 10000);//10秒之后执行解绑
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handup(final String bindcode) {
        //挂断电话接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    json.put("bindCode", bindcode);
                    OkHttpClient client = new OkHttpClient();
                    String url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "dial/handup.action")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //判断如果解绑成功，就让bindCode清空
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.get("success").toString().equals("true")) {
                                    Log.i("infos", "解绑成功");
                                    bindCode = "";
                                }
                            } catch (JSONException e) {
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

    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
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
        //"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String num = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    public void getCustomerSource() {
        //获取客户来源
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    json.put("guestId", intent.getStringExtra("id"));
                    json.put("siteId", share.getString("siteid", ""));
                    OkHttpClient client = new OkHttpClient();
                    String url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "guestFromProbe/listOriginal.action")
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
                                Gson gson = new Gson();
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = gson.fromJson(response.body().string(), Customer.class);
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
                    Customer customer = (Customer) msg.obj;
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < customer.getRows().size(); i++) {
                        list.add(customer.getRows().get(i).getSegmentName());
                    }
                    setCustomerSource(list);
                    break;
                case 2:
                    if (ActivityCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + msg.obj.toString())));//拨打电话
                    break;
                case 3:
                    ToastUtils.showShort(DetailsActivity.this, "您拨打的电话暂时无法接通，请稍后再重试");
                    break;
            }
        }
    };

    public void setCustomerSource(List list) {
        TagContainerLayout mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tcl_customer_source);
        mTagContainerLayout.setTags(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                try {
                    tv_user.setText(data.getStringExtra("content"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    tv_charger.setText(data.getStringExtra("content"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    tv_desc.setText(data.getStringExtra("content"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    tv_type.setText(data.getStringExtra("content"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    tv_mark.setText(data.getStringExtra("content"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //重新加载页面
        CustomerFragment.fragment.clearAllData();
        CustomerFragment.fragment.getData(1, "");
        toFinish();
        finish();
    }

    //清空内存
    private void toFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    tv_desc = null;
                    tv_charger = null;
                    tv_user = null;
                    tv_type = null;
                    tv_mark = null;
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }

}