package com.zhiziyun.dmptest.bot.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.WechatActivityList;
import com.zhiziyun.dmptest.bot.ui.activity.AddFriendsClubActivity;
import com.zhiziyun.dmptest.bot.ui.activity.ShowImageActivity;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.CHScrollView_fragment2;
import com.zhiziyun.dmptest.bot.util.ClickUtils;
import com.zhiziyun.dmptest.bot.util.CustomDialog;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.SelfDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ListView mListView;
    public HorizontalScrollView mTouchView;
    //装入所有的HScrollView
    protected List<CHScrollView_fragment2> mHScrollViews = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;
    private String beginTime, endTime;
    private SharedPreferences share;
    private WechatActivityList wechatActivityList;
    private List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    private Map<String, Object> data = null;
    private int pageNum = 1;
    private SimpleAdapter adapter;
    private TextView et_text;
    private MyDialog dialog;
    private LinearLayout line_page;
    private boolean m_flag = false;
    private ImageView iv_picture;

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
                    getData(pageNum, et_text.getText().toString());
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
        getView().findViewById(R.id.iv_add_friends).setOnClickListener(this);
        smartRefreshLayout = getView().findViewById(R.id.refreshLayout);
        CHScrollView_fragment2 headerScroll = getView().findViewById(R.id.item_scroll_title);
        headerScroll.setFragment(this);
        getView().findViewById(R.id.iv_date).setOnClickListener(this);
        beginTime = gettodayDate();
        endTime = beginTime;
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
        //添加头滑动事件
        mHScrollViews.add(headerScroll);
        mListView = getView().findViewById(R.id.scroll_list);
        adapter = new FriendsFragment.ScrollAdapter(getActivity(), datas, R.layout.chscrollview_item3
                , new String[]{"title", "data_1", "data_2", "data_3", "data_4", "data_5", "data_6",
                "data_7", "data_8", "data_9", "data_10", "data_11", "data_12",}
                , new int[]{R.id.item_title
                , R.id.item_data1
                , R.id.item_data2
                , R.id.item_data3
                , R.id.item_data4
                , R.id.item_data5
                , R.id.item_data6
                , R.id.item_data7
                , R.id.item_data8
                , R.id.item_data9
                , R.id.item_data10
                , R.id.item_data11
                , R.id.item_data12});
        mListView.setAdapter(adapter);

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
            case R.id.iv_add_friends:
                startActivity(new Intent(getActivity(), AddFriendsClubActivity.class));
                break;
            case R.id.iv_date:
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
                                            data = new HashMap<String, Object>();
                                            data.put("title", wechatActivityList.getResponse().getWechatActivities().get(i).getWechatActivityName());//名称
                                            data.put("data_" + 1, wechatActivityList.getResponse().getWechatActivities().get(i).getDelivery());//曝光
                                            data.put("data_" + 2, wechatActivityList.getResponse().getWechatActivities().get(i).getClicks());//点击
                                            data.put("data_" + 3, wechatActivityList.getResponse().getWechatActivities().get(i).getLikeOrComment());//点赞评论
                                            data.put("data_" + 4, wechatActivityList.getResponse().getWechatActivities().get(i).getFollow());//关注量
                                            data.put("data_" + 5, wechatActivityList.getResponse().getWechatActivities().get(i).getShare());//转发量
                                            data.put("data_" + 6, wechatActivityList.getResponse().getWechatActivities().get(i).getConfiguredStatus());//投放状态
                                            data.put("data_" + 7, wechatActivityList.getResponse().getWechatActivities().get(i).getSystemStatus());//审核状态
                                            data.put("data_" + 8, wechatActivityList.getResponse().getWechatActivities().get(i).getBidAmount());//出价
                                            data.put("data_" + 9, wechatActivityList.getResponse().getWechatActivities().get(i).getDailyBudget());//日预算
                                            String state = wechatActivityList.getResponse().getWechatActivities().get(i).getSize();//创意尺寸
                                            data.put("data_" + 10, state);//创意尺寸
                                            data.put("data_" + 11, getBitmap(wechatActivityList.getResponse().getWechatActivities().get(i).getImageUrl()));//缩略图
                                            data.put("data_" + 12, "编辑");
                                            data.put("wechatActivityId", wechatActivityList.getResponse().getWechatActivities().get(i).getWechatActivityId());//朋友圈活动编号
                                            data.put("rejectMessage", wechatActivityList.getResponse().getWechatActivities().get(i).getRejectMessage());//审核失败原因
                                            datas.add(data);
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
                        getData(pageNum, et_text.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public Bitmap getBitmap(String url) {
        try {
            return Glide.with(this).load(url)
                    .asBitmap() //必须
                    .centerCrop()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//图片大小
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //清空所有数据
    public void clearAllData() {
        try {
            datas.clear();
            data.clear();
            pageNum = 1;
            wechatActivityList = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addHViews(final CHScrollView_fragment2 hScrollView) {
        hScrollView.setFragment(this);
        if (!mHScrollViews.isEmpty()) {
            int size = mHScrollViews.size();
            CHScrollView_fragment2 scrollView = mHScrollViews.get(size - 1);
            final int scrollX = scrollView.getScrollX();
            //第一次满屏后，向下滑动，有一条数据在开始时未加入
            if (scrollX != 0) {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        //当listView刷新完成之后，把该条移动到最终位置
                        hScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
        }
        mHScrollViews.add(hScrollView);
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (l == 0) {
            iv_picture.setVisibility(View.VISIBLE);
        } else {
            iv_picture.setVisibility(View.GONE);
        }
        for (CHScrollView_fragment2 scrollView : mHScrollViews) {
            //防止重复滑动
            if (mTouchView != scrollView) {
                scrollView.smoothScrollTo(l, t);
            }
        }
    }

    class ScrollAdapter extends SimpleAdapter {

        private List<? extends Map<String, ?>> datas;
        private int res;
        private String[] from;
        private int[] to;
        private Context context;

        public ScrollAdapter(Context context,
                             List<? extends Map<String, ?>> data, int resource,
                             String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.datas = data;
            this.res = resource;
            this.from = from;
            this.to = to;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = LayoutInflater.from(context).inflate(res, null);
                //第一次初始化的时候装进来
                addHViews((CHScrollView_fragment2) v.findViewById(R.id.item_scroll));
                View[] views = new View[to.length];
                for (int i = 0; i < to.length; i++) {
                    View tv = v.findViewById(to[i]);
//                    tv.setOnClickListener(clickListener);
                    views[i] = tv;
                }
                v.setTag(views);
            }

            //点击状态
            TextView item_state = v.findViewById(R.id.item_data6);
            item_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        switch (datas.get(position).get("data_6").toString()) {
                            case "NORMAL"://开启
                                //点击弹出对话框
                                final CustomDialog customDialog = new CustomDialog(getActivity());
                                customDialog.setTitle("消息提示");
                                customDialog.setMessage("是否暂停朋友圈投放？");
                                customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                                    @Override
                                    public void onYesClick() {
                                        String wechatActivityId = datas.get(position).get("wechatActivityId").toString();
                                        updateWechatActivityStatus(wechatActivityId, "SUSPEND");
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
                            case "SUSPEND"://暂停
                                //点击弹出对话框
                                final CustomDialog customDialog3 = new CustomDialog(getActivity());
                                customDialog3.setTitle("消息提示");
                                customDialog3.setMessage("是否开启朋友圈投放？");
                                customDialog3.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                                    @Override
                                    public void onYesClick() {
                                        String wechatActivityId = datas.get(position).get("wechatActivityId").toString();
                                        updateWechatActivityStatus(wechatActivityId, "NORMAL");
                                        customDialog3.dismiss();
                                    }
                                });
                                customDialog3.setNoOnclickListener("取消", new CustomDialog.onNoOnclickListener() {
                                    @Override
                                    public void onNoClick() {
                                        customDialog3.dismiss();
                                    }
                                });
                                customDialog3.show();
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //数据没刷出来点击会报错
                        ToastUtils.showShort(getActivity(), "操作频率过快");
                    }
                }
            });

            //点击编辑
            TextView operation = v.findViewById(R.id.item_data12);
            operation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent it = new Intent(getActivity(), AddFriendsClubActivity.class);
                        it.putExtra("wechatActivityId", String.valueOf(datas.get(position).get("wechatActivityId")));
                        it.putExtra("flag", 123);
                        it.putExtra("state", datas.get(position).get("data_7").toString());//将审核状态传过去
                        startActivity(it);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            //点击审核不通过
            TextView auditfail = v.findViewById(R.id.item_data7);
            auditfail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (datas.get(position).get("data_7").toString().equals("审核不通过")) {
                            final SelfDialog selfDialog = new SelfDialog(getActivity());
                            selfDialog.setTitle("审核失败原因");
                            selfDialog.setMessage(datas.get(position).get("rejectMessage").toString());
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

            //点击缩略图
            ImageView imageView = v.findViewById(R.id.item_data11);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bundle bundle = new Bundle();
                        Bitmap bitmap = (Bitmap) datas.get(position).get("data_11");
                        bundle.putParcelable("bitmap", ratio(bitmap, 240f, 120f));
                        Intent it = new Intent(getActivity(), ShowImageActivity.class);
                        it.putExtras(bundle);
                        startActivity(it);
                        getActivity().overridePendingTransition(R.anim.stat_activity_anim, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            View[] holders = (View[]) v.getTag();
            int len = holders.length;
            try {
                for (int i = 0; i < len; i++) {
                    try {
                        if (i == 6) {
                            if (this.datas.get(position).get(from[i]).toString().equals("NORMAL")) {
                                ((TextView) holders[i]).setText("开启");

                            } else {
                                ((TextView) holders[i]).setText("暂停");
                            }
                            ((TextView) holders[i]).setTextColor(Color.parseColor("#247ab7"));
                            ((TextView) holders[i]).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//加下滑线
                            ((TextView) holders[i]).getPaint().setAntiAlias(true);//抗锯齿
                        } else if (i == 7) {
                            if (this.datas.get(position).get(from[i]).toString().equals("审核不通过")) {
                                ((TextView) holders[i]).setTextColor(Color.parseColor("#247ab7"));
                                ((TextView) holders[i]).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//加下滑线
                            } else {
                                ((TextView) holders[i]).setTextColor(Color.parseColor("#555555"));
                                ((TextView) holders[i]).getPaint().setFlags(Paint.EMBEDDED_BITMAP_TEXT_FLAG);
                            }
                            ((TextView) holders[i]).getPaint().setAntiAlias(true);//抗锯齿
                            ((TextView) holders[i]).setText(this.datas.get(position).get(from[i]).toString());
                        } else if (i == 11) {
                            ((ImageView) holders[i]).setImageBitmap((Bitmap) this.datas.get(position).get(from[i]));
                        } else {
                            ((TextView) holders[i]).setText(this.datas.get(position).get(from[i]).toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
//                        ToastUtils.showShort(context, "未知错误");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //让listview交替变色
            if (position % 2 == 0) {
                //偶数
                v.setBackgroundColor(Color.parseColor("#f4f5f7"));
            } else {
                //奇数
                v.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            return v;
        }
    }

    public Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 3072) {//判断如果图片大于3M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }
}
