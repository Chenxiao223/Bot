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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.CustomerAdapter;
import com.zhiziyun.dmptest.bot.adapter.SpinnerArrayAdapter;
import com.zhiziyun.dmptest.bot.entity.CallInfo;
import com.zhiziyun.dmptest.bot.entity.CrowdInfo;
import com.zhiziyun.dmptest.bot.ui.activity.DetailsActivity;
import com.zhiziyun.dmptest.bot.ui.activity.EditContentActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.EditDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

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
    private CustomerAdapter adapter;
    private HashMap<String, String> hm_crowd;
    private ArrayList<HashMap<String, String>> list_crowd = new ArrayList<>();
    private Spinner spn_type, spn_state;
    private ArrayList<String> list_type = new ArrayList<>();
    private ArrayAdapter<String> adp_type;
    private ArrayList<String> list_state = new ArrayList<>();
    private ArrayAdapter<String> adp_state;
    private SharedPreferences share;
    private SmartRefreshLayout smartRefreshLayout;
    private EditText et_text;
    private int pageNum = 1;
    private int m_type = 4;
    private int m_mark = 4;
    private boolean b_type = true;
    private boolean b_mark = true;
    private CrowdInfo crowdInfo;
    private LinearLayout line_page;
    private CallInfo callInfo;
    public String bindCode = "";
    private SharedPreferences.Editor editors;

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
        getData(pageNum, et_text.getText().toString());
    }

    private void initView() {
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        editors = share.edit();
        lv_crowd = getView().findViewById(R.id.lv_crowd);
        spn_type = getView().findViewById(R.id.spn_type);
        spn_state = getView().findViewById(R.id.spn_state);
        et_text = getView().findViewById(R.id.et_text);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        adapter = new CustomerAdapter(getActivity(), list_crowd);
        lv_crowd.setAdapter(adapter);
        smartRefreshLayout = (SmartRefreshLayout) getView().findViewById(R.id.refreshLayout);
        //点击列表
        lv_crowd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ClickUtils.isFastClick()) {//防止多次点击
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("id", list_crowd.get(position).get("guestId"));//客户id
                    intent.putExtra("name", list_crowd.get(position).get("content3"));//客户名字
                    intent.putExtra("charger", list_crowd.get(position).get("content4"));//负责人
                    intent.putExtra("desc", list_crowd.get(position).get("content5"));//备注
                    intent.putExtra("mark", list_crowd.get(position).get("content2"));//将状态也传进去
                    intent.putExtra("type", list_crowd.get(position).get("content1"));
                    startActivity(intent);
                }
            }
        });
        //点击电话图标
        adapter.setOnCall(new CustomerAdapter.OnCall() {
            @Override
            public void setInfo(String phoneNumber, String guestId, final int position) {
                if (ClickUtils.isFastClick()) {//防止多次点击
                    phone(guestId);
                    //打完电话跳转到写跟进页面
                    Intent intent = new Intent(getActivity(), EditContentActivity.class);
                    intent.putExtra("title", "写跟进");
                    intent.putExtra("flag", 9527);
                    intent.putExtra("id", list_crowd.get(position).get("guestId"));
                    intent.putExtra("msg", "跟进内容");
                    startActivity(intent);
                }
            }
        });

        //点击搜索键的监听
        et_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getActivity()
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //以下是搜索逻辑
                    list_crowd.clear();
                    //查询门店
                    pageNum = 1;
                    getData(pageNum, et_text.getText().toString());
                    return true;
                }
                return false;
            }
        });

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getData(pageNum, et_text.getText().toString());
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
                        getData(pageNum, "");
                    } else {
                        ToastUtils.showShort(getActivity(), "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getSiteOption();
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

    public void getData(final int pageNum, final String name) {
        //客户列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("id", "");
                    json.put("charger", "");
                    if (m_mark != 4) {
                        json.put("marks", m_mark);
                    }
                    if (m_type != 4) {
                        json.put("types", m_type);
                    }
                    json.put("name", name);
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("page", pageNum);
                    json.put("rows", 10);
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + json.toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "guestFromProbe/list.action")
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
                                Gson gson = new Gson();
                                crowdInfo = gson.fromJson(str, CrowdInfo.class);
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
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
                            hm_crowd.put("content3", ci.getRows().get(i).getName());
                            hm_crowd.put("content4", ci.getRows().get(i).getCharger());
                            hm_crowd.put("content5", ci.getRows().get(i).getDesc());
                            hm_crowd.put("content6", ci.getRows().get(i).getHidePhoneNumber());
                            hm_crowd.put("phoneNumber", String.valueOf(ci.getRows().get(i).getPhoneNumber()));
                            hm_crowd.put("guestId", ci.getRows().get(i).getId());
                            list_crowd.add(hm_crowd);
                        }
                        pageNum++;
                        line_page.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        smartRefreshLayout.finishRefresh(0);//停止刷新
                        smartRefreshLayout.finishLoadmore(0);//停止加载
                    }
                    break;
                case 2:
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + msg.obj.toString())));//拨打电话
                    break;
                case 3:
                    ToastUtils.showShort(getActivity(), "您拨打的电话暂时无法接通，请稍后再重试");
                    break;
            }
        }
    };

    public void phone(final String id) {
        bindCode = "";
        //判断是否为android6.0系统版本，如果是，需要动态添加权限
        if (Build.VERSION.SDK_INT >= 23) {
            //拨打电话权限
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.CALL_PHONE)) {
                    /**
                     * 返回值：
                     如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                     如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                     如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                     弹窗需要解释为何需要该权限，再次请求授权
                     */
                    ToastUtils.showShort(getActivity(), "请授权！");

                    // 跳转到该应用的设置界面，让用户手动授权
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getAppProcessName(getActivity()), null);
                    intent.setData(uri);
                    startActivity(intent);
                    ToastUtils.showShort(getActivity(), "请开启电话权限！");
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
            }
        }

        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
        final String te1 = telephonyManager.getLine1Number();//获取本机号码
        if (TextUtils.isEmpty(te1) && TextUtils.isEmpty(share.getString("phone", ""))) {
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
                            editors.putString("phone", phone);
                            editors.commit();
                            editDialog.dismiss();
                            //让软键盘隐藏
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getApplicationWindowToken(), 0);
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

    public void getSiteOption() {
        list_type.add("全部客户类型");
        list_type.add("普通客户");
        list_type.add("低价值客户");
        list_type.add("积极客户");
        list_type.add("高价值客户");
        list_state.add("全部跟进状态");
        list_state.add("新转入");
        list_state.add("暂无意向");
        list_state.add("持续跟进");
        list_state.add("已成交");
        adp_type = new SpinnerArrayAdapter(getActivity(), list_type);
        spn_type.setAdapter(adp_type);
        adp_state = new SpinnerArrayAdapter(getActivity(), list_state);
        spn_state.setAdapter(adp_state);
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (b_mark) {//spinner初始化的时候不执行点击事件
                    b_mark = false;
                } else {
                    m_mark = getState(list_state.get(position));
                    clearAllData();
                    getData(pageNum, et_text.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (b_type) {//spinner初始化的时候不执行点击事件
                    b_type = false;
                } else {
                    m_type = getType(list_type.get(position));
                    clearAllData();
                    getData(pageNum, et_text.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                    getData(pageNum, et_text.getText().toString());
                }
                break;
        }
    }
}
