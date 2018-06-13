package com.zhiziyun.dmptest.bot.ui.fragment;

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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CustomerAdapter;
import com.zhiziyun.dmptest.bot.entity.CallInfo;
import com.zhiziyun.dmptest.bot.entity.CrowdInfo;
import com.zhiziyun.dmptest.bot.ui.activity.CustomerDetailsActivity;
import com.zhiziyun.dmptest.bot.ui.activity.SearchPageActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.CustomDialog;
import com.zhiziyun.dmptest.bot.util.EditDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.PopWin_customer_source;
import com.zhiziyun.dmptest.bot.view.PopWin_screening;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/4.
 * 客户列表
 */

public class CustomerFragment extends Fragment implements View.OnClickListener {
    public static CustomerFragment fragment;
    private ListView lv_crowd;
    public CustomerAdapter adapter;
    public HashMap<String, String> hm_crowd;
    public ArrayList<HashMap<String, String>> list_crowd = new ArrayList<>();
    private SharedPreferences share;
    private SmartRefreshLayout smartRefreshLayout;
    private int pageNum = 1;
    public int m_type = 4;
    public int m_mark = 4;
    private int hasDial = 0;
    private CrowdInfo crowdInfo;
    private LinearLayout line_page;
    private CallInfo callInfo;
    public String bindCode = "";
    private SharedPreferences.Editor editors;
    private PopWin_screening mPopupWindow = null;
    private PopWin_customer_source mPopcustomer = null;
    public TextView tv_customer_source, tv_screening;
    public JSONArray jsonArray = null;
    private String[] crowdparentStrings = {"到店人群", "wifi人群", "点击人群", "不限"};
    private String[][] crowdchildrenStrings = {
            {"到店人群1", "到店人群2", "到店人群3", "到店人群4", "到店人群5", "到店人群6", "到店人群7", "到店人群8", "到店人群9", "到店人群10", "到店人群11", "到店人群12", "到店人群13", "到店人群14", "到店人群15"},
            {"wifi人群1", "wifi人群2", "wifi人群3", "wifi人群4", "wifi人群5", "wifi人群6", "wifi人群7", "wifi人群8", "wifi人群9", "wifi人群10", "wifi人群11", "wifi人群12", "wifi人群13", "wifi人群14", "wifi人群15"},
            {"点击人群1", "点击人群2", "点击人群3", "点击人群4", "点击人群5", "点击人群6", "点击人群7", "点击人群8", "点击人群9", "点击人群10", "点击人群11", "点击人群12", "点击人群13", "点击人群14", "点击人群15"},
    };
    private String[] parentStrings = {"客户类型", "跟进状态", "拨打状态"};
    private String[][] childrenStrings = {
            {"全部客户类型", "普通客户", "低价值客户", "积极客户", "高价值客户"},
            {"全部跟进状态", "新转入", "暂无意向", "持续跟进", "已成交"},
            {"不限", "已拨打", "未拨打"},
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        fragment = this;
        initView();
        getData(pageNum, "", jsonArray);
    }

    private void initView() {
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        editors = share.edit();
        lv_crowd = getView().findViewById(R.id.lv_crowd);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        getView().findViewById(R.id.iv_search).setOnClickListener(this);
        tv_customer_source = getView().findViewById(R.id.tv_customer_source);
        tv_customer_source.setOnClickListener(this);
        tv_screening = getView().findViewById(R.id.tv_screening);
        tv_screening.setOnClickListener(this);
        adapter = new CustomerAdapter(getActivity(), list_crowd);
        lv_crowd.setAdapter(adapter);
        smartRefreshLayout = (SmartRefreshLayout) getView().findViewById(R.id.refreshLayout);
        //点击列表
        lv_crowd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ClickUtils.isFastClick()) {//防止多次点击
                    Intent intent = new Intent(getActivity(), CustomerDetailsActivity.class);
                    intent.putExtra("id", list_crowd.get(position).get("guestId"));//客户id
                    intent.putExtra("name", list_crowd.get(position).get("content3"));//客户名字
                    intent.putExtra("charger", list_crowd.get(position).get("content4"));//负责人
                    intent.putExtra("desc", list_crowd.get(position).get("content5"));//备注
                    intent.putExtra("mark", list_crowd.get(position).get("content2"));//将状态也传进去
                    intent.putExtra("type", list_crowd.get(position).get("content1"));
                    intent.putExtra("position", position);//将位置传过去
                    intent.putExtra("hasDial", list_crowd.get(position).get("hasDial"));
                    startActivity(intent);
                }
            }
        });
        //点击电话图标
        adapter.setOnCall(new CustomerAdapter.OnCall() {
            @Override
            public void setInfo(String phoneNumber, final String guestId, final int position) {
                if (ClickUtils.isFastClick()) {//防止多次点击
                    //点击弹出对话框
                    final CustomDialog customDialog = new CustomDialog(getActivity());
                    customDialog.setTitle("消息提示");
                    customDialog.setMessage("确定要拨打电话吗");
                    customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            phone(guestId, position);
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
                    //打完电话跳转到写跟进页面
//                    Intent intent = new Intent(getActivity(), EditContentActivity.class);
//                    intent.putExtra("title", "写跟进");
//                    intent.putExtra("flag", 9527);
//                    intent.putExtra("id", list_crowd.get(position).get("guestId"));
//                    intent.putExtra("msg", "跟进内容");
//                    startActivity(intent);
                }
            }
        });

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getData(pageNum, "", jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                try {
                    if ((crowdInfo.getTotal() - (pageNum - 1) * 10) > 0) {
                        getData(pageNum, "", jsonArray);
                    } else {
                        ToastUtils.showShort(getActivity(), "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getTypes(int position) {
        m_type = getType(childrenStrings[0][position]);
    }

    public void getMark(int position) {
        m_mark = getState(childrenStrings[1][position]);
    }

    public void hasDial(int position) {
        hasDial = position;
    }

    //清空所有数据
    public void clearAllData() {
        try {
            hm_crowd.clear();
            list_crowd.clear();
            pageNum = 1;
            callInfo = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData(final int pageNum, final String name, final JSONArray original) {
        //客户列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("charger", "");
                    if (m_mark != 4) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(m_mark + "");//装成数组
                        json.put("mark", jsonArray);
                    }
                    if (m_type != 4) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(m_type + "");//装成数组
                        json.put("type", jsonArray);
                    }
                    if (name.matches("[0-9]+")) {//如果是纯数字就表示是电话号码
                        json.put("tailPhone", name);
                    } else {
                        json.put("name", name);
                    }
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("page", pageNum);
                    json.put("rows", 10);
                    if (hasDial != 0) {//不传表示查所有
                        if (hasDial == 1) {
                            json.put("hasDial", true);//true:查询已拨打过电话的客户
                        } else if (hasDial == 2) {
                            json.put("hasDial", false);//false:查询未拨打过电话的客户
                        }
                    }
                    if (original != null) {
                        json.put("original", original);
                    }
                    json.put("sort", "CreateTime");//按更新时间排序
                    json.put("order", "desc");//排序方式,倒序
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
                            .url(BaseUrl.BaseTest + "guestFromProbe/list")
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
                                crowdInfo = gson.fromJson(response.body().string(), CrowdInfo.class);
                                Message message = new Message();
                                message.what = 1;
                                message.obj = crowdInfo;
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

    public void edit(final String id, final int position) {
        //编辑
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("id", id);
                    json.put("hasDial", true);
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
                            .url(BaseUrl.BaseTest + "guestFromProbe/edit")
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
                                Message msg = new Message();
                                msg.what = 4;
                                msg.obj = position;
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
                    try {
                        CrowdInfo ci = (CrowdInfo) msg.obj;
                        if (ci.getTotal() == 0) {
                            ToastUtils.showShort(getActivity(), "无数据");
                            line_page.setVisibility(View.VISIBLE);
                            smartRefreshLayout.finishRefresh(0);//停止刷新
                            smartRefreshLayout.finishLoadmore(0);//停止加载
                            //让软键盘隐藏
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getApplicationWindowToken(), 0);
                        } else {
                            for (int i = 0; i < ci.getRows().size(); i++) {
                                hm_crowd = new HashMap<>();
                                hm_crowd.put("content1", gettype(ci.getRows().get(i).getType()));
                                hm_crowd.put("content2", getstate(ci.getRows().get(i).getMark()));
                                if (TextUtils.isEmpty(ci.getRows().get(i).getName())) {
                                    hm_crowd.put("content3", "--");
                                } else {
                                    hm_crowd.put("content3", ci.getRows().get(i).getName());
                                }
                                if (TextUtils.isEmpty(ci.getRows().get(i).getCharger())) {
                                    hm_crowd.put("content4", "--");
                                } else {
                                    hm_crowd.put("content4", ci.getRows().get(i).getCharger());
                                }
                                if (TextUtils.isEmpty(ci.getRows().get(i).getDesc())) {
                                    hm_crowd.put("content5", "--");
                                } else {
                                    hm_crowd.put("content5", ci.getRows().get(i).getDesc());
                                }
                                hm_crowd.put("content6", "*******" + ci.getRows().get(i).getTailPhone());
                                hm_crowd.put("phoneNumber", String.valueOf(ci.getRows().get(i).getPhoneNumber()));
                                hm_crowd.put("guestId", ci.getRows().get(i).getId());
                                hm_crowd.put("hasDial", String.valueOf(ci.getRows().get(i).isHasDial()));
                                list_crowd.add(hm_crowd);
                            }
                            pageNum++;
                            line_page.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            smartRefreshLayout.finishRefresh(0);//停止刷新
                            smartRefreshLayout.finishLoadmore(0);//停止加载
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + msg.obj.toString())));//拨打电话
                    break;
                case 3:
                    ToastUtils.showShort(getActivity(), msg.obj.toString());
                    break;
                case 4:
                    try {
                        list_crowd.get((Integer) msg.obj).put("hasDial", "true");
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public void saveTel(String tel) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("phone", Context.MODE_PRIVATE);
        editors = sharedPreferences.edit();
        editors.putString("tel", tel);
        editors.commit();//提交
    }

    public String getTel() {
        SharedPreferences shared = getActivity().getSharedPreferences("phone", Context.MODE_PRIVATE);
        return shared.getString("tel", "");
    }

    public void phone(final String id, final int position) {
        try {
            bindCode = "";
            if (TextUtils.isEmpty(getTel())) {
                //如果没有保存号码就获取号码
                //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                Manifest.permission.CALL_PHONE)) {
                            //点击弹出对话框
                            final CustomDialog customDialog = new CustomDialog(getActivity());
                            customDialog.setTitle("消息提示");
                            customDialog.setMessage("请到权限管理打开电话权限");
                            customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick() {
                                    // 跳转到该应用的设置界面，让用户手动授权
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getAppProcessName(getActivity()), null);
                                    intent.setData(uri);
                                    startActivity(intent);
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
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                        }
                    } else {
                        //有权限才执行
                        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
                        final String te1 = telephonyManager.getLine1Number();//获取本机号码
                        if (TextUtils.isEmpty(te1) && TextUtils.isEmpty(getTel())) {
                            //如果获取不到手机号码就手动输入
                            //点击弹出对话框
                            final EditDialog editDialog = new EditDialog(getActivity());
                            editDialog.setTitle("请输入电话号码");
                            editDialog.setYesOnclickListener("确定", new EditDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick(String phone) {
                                    if (TextUtils.isEmpty(phone)) {
                                        ToastUtils.showShort(getActivity(), "请输入电话号码");
                                    } else {
                                        if (isMobile(phone)) {//如果手机格式正确
                                            saveTel(phone);
                                            //让软键盘隐藏
                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(getView().getApplicationWindowToken(), 0);
                                            editDialog.dismiss();
                                        } else {
                                            ToastUtils.showShort(getActivity(), "手机号不合法");
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
                                            json.put("phoneNumber", getTel().replace("+86", ""));//将电话号码前的+86去掉
                                        } else {
                                            //能够获取手机号就直接使用
                                            json.put("phoneNumber", te1.replace("+86", ""));
                                            saveTel(te1.replace("+86", ""));
                                        }
                                        json.put("guestId", id);
                                        OkHttpClient client = new OkHttpClient();
                                        String url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                                        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                                        RequestBody body = RequestBody.create(mediaType, url);
                                        Request request = new Request.Builder()
                                                .url(BaseUrl.BaseTest + "dial/phone")
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
                                                    Message message = new Message();
                                                    if (callInfo.isSuccess()) {
                                                        bindCode = callInfo.getObj();
                                                        message.what = 2;
                                                        message.obj = callInfo.getMsg();
                                                        handler.sendMessage(message);
                                                    } else {
                                                        message.what = 3;
                                                        message.obj = callInfo.getMsg();
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
                            edit(id, position);//编辑接口，放这里保险
                        }
                    }
                } else {//如果是6.0一下的版本
                    TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
                    final String te1 = telephonyManager.getLine1Number();//获取本机号码
                    if (TextUtils.isEmpty(te1) && TextUtils.isEmpty(getTel())) {
                        //如果获取不到手机号码就手动输入
                        //点击弹出对话框
                        final EditDialog editDialog = new EditDialog(getActivity());
                        editDialog.setTitle("请输入电话号码");
                        editDialog.setYesOnclickListener("确定", new EditDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick(String phone) {
                                if (TextUtils.isEmpty(phone)) {
                                    ToastUtils.showShort(getActivity(), "请输入电话号码");
                                } else {
                                    if (isMobile(phone)) {//如果手机格式正确
                                        saveTel(phone);
                                        //让软键盘隐藏
                                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(getView().getApplicationWindowToken(), 0);
                                        editDialog.dismiss();
                                    } else {
                                        ToastUtils.showShort(getActivity(), "手机号不合法");
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
                                        json.put("phoneNumber", getTel().replace("+86", ""));//将电话号码前的+86去掉
                                    } else {
                                        //能够获取手机号就直接使用
                                        json.put("phoneNumber", te1.replace("+86", ""));
                                        saveTel(te1);
                                    }
                                    json.put("guestId", id);
                                    OkHttpClient client = new OkHttpClient();
                                    String url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                                    RequestBody body = RequestBody.create(mediaType, url);
                                    Request request = new Request.Builder()
                                            .url(BaseUrl.BaseTest + "dial/phone")
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
                                                Message message = new Message();
                                                if (callInfo.isSuccess()) {
                                                    bindCode = callInfo.getObj();
                                                    message.what = 2;
                                                    message.obj = callInfo.getMsg();
                                                    handler.sendMessage(message);
                                                } else {
                                                    message.what = 3;
                                                    message.obj = callInfo.getMsg();
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
                        edit(id, position);//编辑接口，放这里保险
                    }
                }
            } else {
                //如果保存有号码了就直接拨打
                //拨打电话接口
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject();
                            json.put("siteId", share.getString("siteid", ""));
                            json.put("phoneNumber", getTel().replace("+86", ""));
                            json.put("guestId", id);
                            OkHttpClient client = new OkHttpClient();
                            String url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                            RequestBody body = RequestBody.create(mediaType, url);
                            Request request = new Request.Builder()
                                    .url(BaseUrl.BaseTest + "dial/phone")
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
                                        Message message = new Message();
                                        if (callInfo.isSuccess()) {
                                            bindCode = callInfo.getObj();
                                            message.what = 2;
                                            message.obj = callInfo.getMsg();
                                            handler.sendMessage(message);
                                        } else {
                                            message.what = 3;
                                            message.obj = callInfo.getMsg();
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

                edit(id, position);//编辑接口，放这里保险
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public int getState(String str) {
        switch (str) {
            case "新转入":
                return 0;
            case "暂无意向":
                return 1;
            case "持续跟进":
                return 2;
            case "已成交":
                return 3;
            case "全部跟进状态":
                return 4;
        }
        return 0;
    }

    public String getstate(int state) {
        switch (state) {
            case 0:
                return "新转入";
            case 1:
                return "暂无意向";
            case 2:
                return "持续跟进";
            case 3:
                return "已成交";
            case 4:
                return "全部跟进状态";
        }
        return "";
    }

    public int getType(String str) {
        switch (str) {
            case "普通客户":
                return 0;
            case "低价值客户":
                return 1;
            case "积极客户":
                return 2;
            case "高价值客户":
                return 3;
            case "全部客户类型":
                return 4;
        }
        return 0;
    }

    public String gettype(int state) {
        switch (state) {
            case 0:
                return "普通客户";
            case 1:
                return "低价值客户";
            case 2:
                return "积极客户";
            case 3:
                return "高价值客户";
            case 4:
                return "全部客户类型";
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_page:
                if (ClickUtils.isFastClick()) {
                    clearAllData();
                    getData(pageNum, "", jsonArray);
                }
                break;
            case R.id.iv_search:
                Intent it = new Intent(getActivity(), SearchPageActivity.class);
                it.putExtra("activity", "CustomerFragment");
                startActivity(it);
                break;
            case R.id.tv_customer_source://访客来源
                try {
                    if (mPopcustomer == null) {
                        mPopcustomer = new PopWin_customer_source(crowdparentStrings, childrenStrings, getActivity(), selectCateg);
                    }
                    mPopcustomer.showAsDropDown(tv_customer_source, -5, 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_screening://筛选
                try {
                    if (mPopupWindow == null) {
                        mPopupWindow = new PopWin_screening(parentStrings, childrenStrings, getActivity(), selectCategory);
                    }
                    mPopupWindow.showAsDropDown(tv_screening, -5, 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 选择完成回调接口
     */
    private PopWin_screening.SelectCategory selectCategory = new PopWin_screening.SelectCategory() {
        @Override
        public void selectCategory(int parentSelectposition, int childrenSelectposition) {
            String parentStr = parentStrings[parentSelectposition];
            String childrenStr = childrenStrings[parentSelectposition][childrenSelectposition];

        }
    };

    /**
     * 选择完成回调接口
     */
    private PopWin_customer_source.SelectCategory selectCateg = new PopWin_customer_source.SelectCategory() {
        @Override
        public void selectCategory(int parentSelectposition, int childrenSelectposition) {
//            String parentStr = parentStrings[parentSelectposition];
//            String childrenStr = childrenStrings[parentSelectposition][childrenSelectposition];

        }
    };

}
