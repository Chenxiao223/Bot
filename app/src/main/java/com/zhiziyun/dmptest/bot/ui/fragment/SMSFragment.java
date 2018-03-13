package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.AddSmsAdapter;
import com.zhiziyun.dmptest.bot.entity.SmsActivityConsumptionQuery;
import com.zhiziyun.dmptest.bot.entity.SmsFragment;
import com.zhiziyun.dmptest.bot.ui.activity.AddSmsActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.CustomDialog;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.SelfDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 发短信
 */
public class SMSFragment extends Fragment implements View.OnClickListener {
    public static SMSFragment smsFragment;
    private ListView lv_sms;
    public AddSmsAdapter addSmsAdapter;
    public HashMap<String, Object> hm_sms;
    public ArrayList<HashMap<String, Object>> list_sms = new ArrayList<>();
    private String beginTime, endTime;
    private SmartRefreshLayout smartRefreshLayout;
    private SharedPreferences share;
    private int pageNum = 1;
    private SmsFragment sms;
    private EditText et_text;
    private SmsActivityConsumptionQuery smsConsumption;
    private LinearLayout line_page;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sms, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        initView();
    }

    //当滑到当前碎片时调用该方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            try {
                //这里与下拉刷新代码一样
                clearAllData();
                getData(pageNum, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initView() {
        smsFragment = this;
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        getView().findViewById(R.id.iv_add).setOnClickListener(this);
        getView().findViewById(R.id.iv_date).setOnClickListener(this);
        smartRefreshLayout = getView().findViewById(R.id.refreshLayout);
        et_text = getView().findViewById(R.id.et_text);
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
                    clearAllData();
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
                    getData(pageNum, "");
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
                    if ((sms.getResponse().getTotal() - (pageNum - 1) * 10) > 0) {
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

        lv_sms = getView().findViewById(R.id.lv_sms);
        addSmsAdapter = new AddSmsAdapter(getActivity(), list_sms);
        lv_sms.setAdapter(addSmsAdapter);
        //接口回调，待发送可点击
        addSmsAdapter.setOnClick(new AddSmsAdapter.OnClick() {
            @Override
            public void setInfo(final String activityid, int position) {
                if (ClickUtils.isFastClick()) {//防止点击多次
                    if (list_sms.get(position).get("smsStatus").equals("审核通过")) {
                        smsActivityConsumptionQuery(activityid);
                    } else {//如果短信审核未通过
                        //点击弹出对话框
                        final SelfDialog selfDialog = new SelfDialog(getActivity());
                        selfDialog.setTitle("消息提示");
                        selfDialog.setMessage("短信尚未审核，无法发送！");
                        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.show();
                    }
                }
            }
        });

        getData(1, "");
    }

    public void clearAllData() {
        try {
            pageNum = 1;
            list_sms.clear();
            sms = null;
            hm_sms.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                startActivity(new Intent(getActivity(), AddSmsActivity.class));
                break;
            case R.id.iv_date:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(getActivity(), 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String textString = String.format("%d-%d-%d %d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        int index = textString.indexOf(" ");
                        beginTime = date(textString.substring(0, index));
                        endTime = date(textString.substring(index + 1, textString.length()));

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                break;
            case R.id.line_page:
                try {
                    if (ClickUtils.isFastClick()) {
                        clearAllData();
                        getData(pageNum, et_text.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public String date(String date) {
        int index1 = date.indexOf("-");
        int index2 = index1 + date.substring(date.indexOf("-") + 1).indexOf("-") + 1;
        String year = date.substring(0, index1);
        String month = date.substring(index1 + 1, index2).length() == 1 ? "0" + date.substring(index1 + 1, index2) : date.substring(index1 + 1, index2);
        String day = date.substring(index2 + 1).length() == 1 ? "0" + date.substring(index2 + 1) : date.substring(index2 + 1);
        return year + "-" + month + "-" + day;
    }

    public void smsActivityConsumptionQuery(final String activityId) {
        //短信活动消耗查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("activityId", activityId);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "activityApp/getSmsActivityCost")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                Gson gson = new Gson();
                                smsConsumption = gson.fromJson(response.body().string(), SmsActivityConsumptionQuery.class);
                                handler.sendEmptyMessage(3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getData(final int page, final String name) {
        lv_sms.setEnabled(false);
        //短信活动查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("activityName", name);
                    jsonObject.put("page", page);
                    jsonObject.put("rows", 10);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "activityApp/getSmsActivity")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                Gson gson = new Gson();
                                sms = gson.fromJson(response.body().string(), SmsFragment.class);
                                if (sms != null) {
                                    handler.sendEmptyMessage(1);//通知刷新适配器
                                } else {
                                    handler.sendEmptyMessage(2);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendSms(final String activityid) {
        //短信发送接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("activityId", activityid);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentid=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8") + "&json=" + jsonObject.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseZhang + "activityApp/sendSms")
                            .post(body)
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject json = new JSONObject(response.body().string());
                                if (json.get("status").toString().equals("true")) {
                                    //成功
                                    handler.sendEmptyMessage(4);
                                } else {
                                    //失败
                                    Message message = new Message();
                                    message.what = 5;
                                    message.obj = json.get("errormsg").toString();
                                    handler.sendMessage(message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
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
                        if (sms.getResponse().getTotal() == 0) {
                            ToastUtils.showShort(getActivity(), "无数据");
                            line_page.setVisibility(View.VISIBLE);
                            smartRefreshLayout.finishLoadmore(0);//停止刷新
                        } else {
                            for (int i = 0; i < sms.getResponse().getData().size(); i++) {
                                hm_sms = new HashMap<>();
                                hm_sms.put("content1", sms.getResponse().getData().get(i).getActivityName());//活动名称
                                hm_sms.put("content2", sms.getResponse().getData().get(i).getActivityStatus());//活动状态
                                hm_sms.put("content3", sms.getResponse().getData().get(i).getSendTime());//发送日期
                                hm_sms.put("content4", sms.getResponse().getData().get(i).getSendNum() + "");//送达数
                                hm_sms.put("activityId", sms.getResponse().getData().get(i).getActivityId());//短信活动编号
                                hm_sms.put("smsStatus", sms.getResponse().getData().get(i).getSmsStatus());//短信发送状态
                                if (sms.getResponse().getData().get(i).getActivityStatus().equals("待发送")) {
                                    hm_sms.put("issend", true);
                                } else {
                                    hm_sms.put("issend", false);
                                }
                                list_sms.add(hm_sms);
                            }
                            pageNum++;
                            addSmsAdapter.notifyDataSetChanged();
                            smartRefreshLayout.finishLoadmore(0);//停止刷新
                            line_page.setVisibility(View.GONE);
                            lv_sms.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    ToastUtils.showShort(getActivity(), "无数据");
                    line_page.setVisibility(View.VISIBLE);
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    lv_sms.setEnabled(true);
                    break;
                case 3:
                    //点击弹出对话框
                    final CustomDialog customDialog = new CustomDialog(getActivity());
                    customDialog.setTitle("消息提示");
                    customDialog.setMessage("所选人群覆盖" + smsConsumption.getResponse().getMacCount().toString() + "个设备，预计冻结" + smsConsumption.getResponse().getSmsCost().toString() + "元（实际扣费以最终发送成功数结算），你确定要发送吗？");
                    customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            sendSms(smsConsumption.getResponse().getActivityId().toString());
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
                case 4:
                    final SelfDialog selfDialog = new SelfDialog(getActivity());
                    selfDialog.setTitle("消息提示");
                    selfDialog.setMessage("已发送，短信发送需要较长时间，请稍后再来查看!");
                    selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            try {
                                selfDialog.dismiss();
                                clearAllData();
                                getData(pageNum, "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    selfDialog.show();
                    break;
                case 5:
                    String str = (String) msg.obj;//传入错误信息
                    ToastUtils.showShort(getActivity(), str);
                    break;
            }
        }
    };

}
