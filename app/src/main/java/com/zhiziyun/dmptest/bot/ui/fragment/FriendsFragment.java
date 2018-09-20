package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.FriendsAdapter;
import com.zhiziyun.dmptest.bot.entity.WechatActivityList;
import com.zhiziyun.dmptest.bot.ui.activity.AddFriendsClubActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.SelfDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;
import com.zhiziyun.dmptest.bot.view.PopWin_general;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/7/17.
 * 朋友圈
 */

public class FriendsFragment extends Fragment implements View.OnClickListener {
    public static FriendsFragment friendsFragment;
    private ListView lv_friends;
    private SmartRefreshLayout smartRefreshLayout;
    private String beginTime, endTime;
    private SharedPreferences share;
    private WechatActivityList wechatActivityList;
    private ArrayList<HashMap<String, Object>> list_friends = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> list_state = new ArrayList<>();
    private HashMap<String, Object> hm_friends;
    private int pageNum = 1;
    private FriendsAdapter adapter;
    private MyDialog dialog;
    private LinearLayout line_page;
    private boolean m_flag = false;
    private ImageView iv_picture;
    public TextView tv_state;
    private ImageView iv_state;
    private String[] str_state = {"不限", "投放中", "暂停", "未投放", "已结束"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    //当滑到当前碎片时调用该方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (ClickUtils.isFastClick()) {//防止快速切换而闪退
                try {
                    //这里与下拉刷新代码一样
                    clearAllData();
                    getData(pageNum, "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initView() {
        friendsFragment = this;
        dialog = MyDialog.showDialog(getActivity());
        share = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        line_page = getView().findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        iv_picture = getView().findViewById(R.id.iv_picture);
        iv_state = getView().findViewById(R.id.iv_state);
        tv_state=getView().findViewById(R.id.tv_state);
        getView().findViewById(R.id.line_state).setOnClickListener(this);
        smartRefreshLayout = getView().findViewById(R.id.refreshLayout);
        getView().findViewById(R.id.line_date).setOnClickListener(this);
        beginTime = gettodayDate();
        endTime = beginTime;

        for (int i = 0; i < str_state.length; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", str_state[i]);
            hashMap.put("mac", "");
            hashMap.put("boolean", false);
            list_state.add(hashMap);
        }

        //添加头滑动事件
        lv_friends = getView().findViewById(R.id.lv_friends);
        adapter = new FriendsAdapter(getActivity(), list_friends);
        lv_friends.setAdapter(adapter);
        //点击开关
        adapter.setOnCheck(new FriendsAdapter.OnCheck() {
            @Override
            public void setInfo(String id, String activityStatus, int position) {
                updateWechatActivityStatus(id, activityStatus);
            }
        });
        //点击文字
        adapter.setOnclick(new FriendsAdapter.Onclick() {
            @Override
            public void setclick(int position) {
                try {
                    if (list_friends.get(position).get("content8").toString().equals("审核不通过")) {
                        final SelfDialog selfDialog = new SelfDialog(getActivity());
                        selfDialog.setTitle("审核失败原因");
                        selfDialog.setMessage(list_friends.get(position).get("rejectMessage").toString());
                        selfDialog.setYesOnclickListener("知道了", new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                selfDialog.dismiss();
                            }
                        });
                        selfDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //点击列表
        lv_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent it = new Intent(getActivity(), AddFriendsClubActivity.class);
                    it.putExtra("wechatActivityId", String.valueOf(list_friends.get(position).get("wechatActivityId")));
                    it.putExtra("flag", 123);
                    it.putExtra("state", list_friends.get(position).get("content8").toString());//将审核状态传过去
                    startActivity(it);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                    if ((wechatActivityList.getResponse().getTotal() - (pageNum - 1) * 10) > 0) {
                        getData(pageNum, "");
                        ToastUtils.showShort(getActivity(), pageNum + "/" + ((wechatActivityList.getResponse().getTotal() / 10) + 1));
                    } else {
                        ToastUtils.showShort(getActivity(), "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getData(1, "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_state:
                iv_state.setColorFilter(getResources().getColor(R.color.blue));
                //让背景变暗
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.7f;
                getActivity().getWindow().setAttributes(lp);
                //弹出窗体
                PopWin_general popWin_tanzhen = new PopWin_general(getActivity(), "FriendsFragment", list_state);
                popWin_tanzhen.showAsDropDown(getView().findViewById(R.id.line));//从这个控件下弹出
                //监听popwin是否关闭，关闭的话让背景恢复
                popWin_tanzhen.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1f;
                        getActivity().getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.line_date:
                try {
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
                            clearAllData();
                            //加载动画
                            dialog.show();
                            getData(pageNum, "");
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
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

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    public void getData(final int page, final String name) {
        //微信朋友圈活动列表
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tencentId", share.getString("tencentid", ""));
                    jsonObject.put("wechatActivityName", name);
                    jsonObject.put("startDate", beginTime);
                    jsonObject.put("endDate", endTime);
                    jsonObject.put("page", page);
                    jsonObject.put("row", 10);
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
                            .url(BaseUrl.BaseZhang + "api/tencentWechatActivityApp/getWechatActivityList")
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
                                String str = response.body().string();
                                Gson gson = new Gson();
                                wechatActivityList = gson.fromJson(str, WechatActivityList.class);
                                if (wechatActivityList.isStatus()) {
                                    if (wechatActivityList.getResponse().getWechatActivities().size() == 0) {
                                        handler.sendEmptyMessage(2);
                                    } else {
                                        for (int i = 0; i < wechatActivityList.getResponse().getWechatActivities().size(); i++) {
                                            hm_friends = new HashMap<String, Object>();
                                            hm_friends.put("content1", wechatActivityList.getResponse().getWechatActivities().get(i).getWechatActivityName());//名称
                                            hm_friends.put("content2", wechatActivityList.getResponse().getWechatActivities().get(i).getDelivery());//曝光
                                            hm_friends.put("content3", wechatActivityList.getResponse().getWechatActivities().get(i).getClicks());//点击
                                            hm_friends.put("content4", "");//点击率
                                            hm_friends.put("content5", "");//花费
                                            hm_friends.put("content6", wechatActivityList.getResponse().getWechatActivities().get(i).getBidAmount());//出价
                                            hm_friends.put("content7", wechatActivityList.getResponse().getWechatActivities().get(i).getDailyBudget());//日预算
                                            hm_friends.put("content8", wechatActivityList.getResponse().getWechatActivities().get(i).getSystemStatus());//审核状态
                                            hm_friends.put("content9", wechatActivityList.getResponse().getWechatActivities().get(i).getConfiguredStatus());//投放状态
                                            hm_friends.put("wechatActivityId", wechatActivityList.getResponse().getWechatActivities().get(i).getWechatActivityId());//朋友圈活动编号
                                            hm_friends.put("rejectMessage", wechatActivityList.getResponse().getWechatActivities().get(i).getRejectMessage());//审核失败原因
                                            list_friends.add(hm_friends);
                                        }
                                        pageNum++;
                                        handler.sendEmptyMessage(1);
                                    }
                                } else {
                                    handler.sendEmptyMessage(2);
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

    public void updateWechatActivityStatus(final String id, final String type) {
        //微信朋友圈活动投放状态更新
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tencentId", share.getString("tencentid", ""));
                    jsonObject.put("wechatActivityId", id);
                    jsonObject.put("adStatusType", type);
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
                            .url(BaseUrl.BaseZhang + "api/tencentWechatActivityApp/updateWechatActivityStatus")
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
                                String str = response.body().string();
                                JSONObject json = new JSONObject(str);
                                if (json.get("status").toString().equals("true")) {
                                    handler.sendEmptyMessage(4);
                                } else {
                                    Message msg = new Message();
                                    msg.what = 3;
                                    msg.obj = json.get("errormsg").toString();
                                    handler.sendMessage(msg);
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
                    try {
                        adapter.notifyDataSetChanged();
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        line_page.setVisibility(View.GONE);
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        smartRefreshLayout.finishRefresh(0);
                        line_page.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    ToastUtils.showShort(getActivity(), msg.obj.toString());
                    break;
                case 4:
                    try {
                        clearAllData();
                        getData(pageNum, "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    //清空所有数据
    public void clearAllData() {
        try {
            list_friends.clear();
            hm_friends.clear();
            pageNum = 1;
            wechatActivityList = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
