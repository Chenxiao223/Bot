package com.zhiziyun.dmptest.bot.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.TanzhenListAdapter;
import com.zhiziyun.dmptest.bot.entity.TanzhenList;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.EditDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.SlideListView;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.PopWin_bind_tanzhen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/5.
 * 探针列表页
 */

public class TanzhenListActivity extends BaseActivity implements View.OnClickListener {
    public static TanzhenListActivity tanzhenListActivity;
    private SharedPreferences share;
    private TanzhenList tanzhenList;
    private SlideListView lv_tanzhen;
    private TanzhenListAdapter adapter;
    private HashMap<String, String> hm_tanzhen;
    public ArrayList<HashMap<String, String>> list_tanzhen = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;
    private int pageNum = 1;
    private Intent intent;
    private MyDialog dialog;
    private LinearLayout line_page;
    public int flag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanzhenlist);
        initView();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void clearAllData() {
        pageNum = 1;
        list_tanzhen.clear();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        tanzhenListActivity = this;
        line_page = this.findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        intent = getIntent();
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_addstory).setOnClickListener(this);
        findViewById(R.id.iv_search).setOnClickListener(this);
        lv_tanzhen = (SlideListView) findViewById(R.id.lv_store);
        adapter = new TanzhenListAdapter(this, lv_tanzhen, list_tanzhen);
        lv_tanzhen.setAdapter(adapter);
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    hm_tanzhen.clear();
                    clearAllData();
                    gettanzhenList(pageNum, "");
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }
        });
        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if ((tanzhenList.getTotal() - (pageNum - 1) * 10) > 0) {
                    gettanzhenList(pageNum, "");
                    ToastUtils.showShort(TanzhenListActivity.this, pageNum + "/" + ((tanzhenList.getTotal() / 10) + 1));
                } else {
                    ToastUtils.showShort(TanzhenListActivity.this, "最后一页了");
                    smartRefreshLayout.finishLoadmore(0);
                }
            }
        });
        findViewById(R.id.traceroute_rootview).setOnClickListener(this);
    }

    //以便刷新页面
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        try {
            if (hm_tanzhen == null) {//第一次进来
                //加载动画
                dialog = MyDialog.showDialog(this);
                dialog.show();
                gettanzhenList(1, "");//第二个参数为空就是查所有
            } else {//第二次进来
                if (flag == 0) {
                    dialog.show();
                    hm_tanzhen.clear();
                    clearAllData();
                    gettanzhenList(pageNum, "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                Intent it = new Intent(this, SearchPageActivity.class);
                it.putExtra("activity", "TanzhenListActivity");
                startActivity(it);
                break;
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.iv_addstory:
                try {
                    //隐藏软键盘
                    InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm3.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    //让背景变暗
                    WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                    lp2.alpha = 0.7f;
                    getWindow().setAttributes(lp2);
                    getWindow().setAttributes(lp2);
                    //弹出pop窗体
                    PopWin_bind_tanzhen popWin_bind_tanzhen = new PopWin_bind_tanzhen(this, null, 1);
                    popWin_bind_tanzhen.showAtLocation(findViewById(R.id.traceroute_rootview), Gravity.BOTTOM, 0, 0);//125
                    //监听popwin是否关闭，关闭的话让背景恢复
                    popWin_bind_tanzhen.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                            lp2.alpha = 1f;
                            getWindow().setAttributes(lp2);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.traceroute_rootview:
                //让软键盘隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        clearAllData();
                        gettanzhenList(pageNum, "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void getTanzhen() {
        try {
            //申请相机权限
            if (ContextCompat.checkSelfPermission(TanzhenListActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(TanzhenListActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                Intent it = new Intent();
                it.setClass(TanzhenListActivity.this, CaptureActivity.class);
                //返回一个二维码的信息
                startActivityForResult(it, 98);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inputTanzhen(final View view) {
        //点击弹出对话框
        final EditDialog editDialog = new EditDialog(this);
        editDialog.setTitle("请输入探针");
        editDialog.setYesOnclickListener("确定", new EditDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String phone) {
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort(TanzhenListActivity.this, "请输入探针");
                } else {
                    Intent it = new Intent(TanzhenListActivity.this, BindingActivity.class);
                    it.putExtra("flag", 123);
                    it.putExtra("lat", intent.getFloatExtra("lat", 0));
                    it.putExtra("lon", intent.getFloatExtra("lon", 0));
                    it.putExtra("floorArea", intent.getStringExtra("area"));
                    it.putExtra("mac", phone.toLowerCase());//将大写改成小写
                    it.putExtra("storeId", intent.getStringExtra("id"));
                    startActivity(it);
                    editDialog.dismiss();
                }
            }
        });
        editDialog.setNoOnclickListener("取消", new EditDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                //隐藏软键盘
                InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm3.hideSoftInputFromWindow(view.getWindowToken(), 0);
                editDialog.dismiss();
            }
        });
        editDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                try {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Intent it = new Intent();
                        it.setClass(TanzhenListActivity.this, CaptureActivity.class);
                        //返回一个二维码的信息
                        startActivityForResult(it, 98);
                    } else {
                        ToastUtils.showShort(this, "请在应用管理中打开“相机”访问权限！");
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
        if (requestCode == 98 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            //返回二维码扫描的信息
            String result = bundle.get("result").toString();
            Intent it = new Intent(this, BindingActivity.class);
            it.putExtra("lat", intent.getFloatExtra("lat", 0));
            it.putExtra("lon", intent.getFloatExtra("lon", 0));
            it.putExtra("floorArea", intent.getStringExtra("area"));
            it.putExtra("mac", result);
            it.putExtra("storeId", intent.getStringExtra("id"));
            startActivity(it);
        }
    }

    //第二个参数为空就是查所有
    public void gettanzhenList(final int page, final String name) {
        //获取探针列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("storeId", intent.getStringExtra("id"));
                    json.put("page", page);
                    json.put("rows", 10);
                    json.put("name", name);
                    json.put("sort", "updateTime");
                    json.put("order", "desc");
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
                            .url(BaseUrl.BaseTest + "probe/list")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Gson gson = new Gson();
                            tanzhenList = gson.fromJson(response.body().string(), TanzhenList.class);
                            handler.sendEmptyMessage(1);
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
        final EditDialog editDialog = new EditDialog(TanzhenListActivity.this);
        editDialog.setTitle("请输入建筑半径");
        editDialog.setYesOnclickListener("确定", new EditDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String phone) {
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort(TanzhenListActivity.this, "请输入建筑半径");
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
                            ToastUtils.showShort(TanzhenListActivity.this, "超出长度");
                        }
                    } else {//如果不是数字
                        ToastUtils.showShort(TanzhenListActivity.this, "请输入数字");
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
                                Message message = new Message();
                                message.what = 4;
                                message.obj = json.get("msg");
                                handler.sendMessage(message);
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
                    try {
                        if (tanzhenList != null) {
                            if (tanzhenList.getTotal() == 0) {
                                ToastUtils.showShort(TanzhenListActivity.this, "无探针");
                                line_page.setVisibility(View.VISIBLE);
                                smartRefreshLayout.finishRefresh(0);
                                smartRefreshLayout.finishLoadmore(0);
                                dialog.dismiss();
                            } else {
                                for (int i = 0; i < tanzhenList.getRows().size(); i++) {
                                    hm_tanzhen = new HashMap<>();
                                    hm_tanzhen.put("content1", tanzhenList.getRows().get(i).getName());
                                    hm_tanzhen.put("content2", tanzhenList.getRows().get(i).getMac());
                                    String area = tanzhenList.getRows().get(i).getFloorArea();
                                    double r = Double.parseDouble(area) / Math.PI;//根据面积计算出半径的平方
                                    double radius = Math.round(Math.sqrt(r));//根据半径的平方计算半径（平方根）
                                    hm_tanzhen.put("content3", radius + "m");
                                    hm_tanzhen.put("id", tanzhenList.getRows().get(i).getId());
                                    hm_tanzhen.put("valid", tanzhenList.getRows().get(i).isValid());
                                    list_tanzhen.add(hm_tanzhen);
                                }
                                pageNum++;
                                adapter.notifyDataSetChanged();
                                line_page.setVisibility(View.GONE);
                            }
                            smartRefreshLayout.finishRefresh(0);
                            smartRefreshLayout.finishLoadmore(0);
                            dialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        list_tanzhen.clear();
                        pageNum = 1;
                        dialog.show();
                        gettanzhenList(pageNum, "");//第二个参数为空就是查所有
                        ToastUtils.showShort(TanzhenListActivity.this, "删除成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    ToastUtils.showShort(TanzhenListActivity.this, "删除失败");
                    break;
                case 4:
                    try {
                        ToastUtils.showShort(TanzhenListActivity.this, msg.obj.toString());
                        //刷新
                        hm_tanzhen.clear();
                        clearAllData();
                        gettanzhenList(pageNum, "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };


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
                                    handler.sendEmptyMessage(2);
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
                    share = null;
                    tanzhenList = null;
                    lv_tanzhen.setAdapter(null);
                    adapter = null;
                    list_tanzhen.clear();
                    smartRefreshLayout = null;
                    intent = null;
                    hm_tanzhen.clear();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }
}
