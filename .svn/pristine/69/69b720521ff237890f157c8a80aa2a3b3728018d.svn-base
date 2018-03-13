package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.OriginalityList;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.CHScrollView_Activity;
import com.zhiziyun.dmptest.bot.util.DoubleDatePickerDialog;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by Administrator on 2018/1/18.
 * 创意列表
 */

public class OriginalityListActivity extends BaseActivity implements View.OnClickListener {
    private ListView mListView;
    //方便测试，直接写的public
    public HorizontalScrollView mTouchView;
    //装入所有的HScrollView
    protected List<CHScrollView_Activity> mHScrollViews = new ArrayList<CHScrollView_Activity>();
    private SmartRefreshLayout smartRefreshLayout;
    private SharedPreferences share;
    private OriginalityList originalityList;
    private List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    private Map<String, Object> data = null;
    private int pageNum = 1;
    private Intent it;
    private String beginTime, endTime;
    private SimpleAdapter adapter;
    private MyDialog dialog;
    private List<String> list_creativeId = new ArrayList<>();
    private LinearLayout line_page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_originality_list);
        initView();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        it = getIntent();
        line_page = this.findViewById(R.id.line_page).findViewById(R.id.line_page);
        line_page.setOnClickListener(this);
        dialog = MyDialog.showDialog(OriginalityListActivity.this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_addoriginality).setOnClickListener(this);
        findViewById(R.id.iv_date).setOnClickListener(this);
        beginTime = gettodayDate();
        endTime = beginTime;
        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        CHScrollView_Activity headerScroll = (CHScrollView_Activity) findViewById(R.id.item_scroll_title);
        //添加头滑动事件
        mHScrollViews.add(headerScroll);
        mListView = (ListView) findViewById(R.id.scroll_list);

        adapter = new OriginalityListActivity.ScrollAdapter(OriginalityListActivity.this, datas, R.layout.chscrollview_item2
                , new String[]{"title", "data_1", "data_2", "data_3", "data_4", "data_5", "data_6", "data_7",}
                , new int[]{R.id.item_title
                , R.id.item_data1
                , R.id.item_data2
                , R.id.item_data3
                , R.id.item_data4
                , R.id.item_data5
                , R.id.item_data6
                , R.id.item_data7});
        mListView.setAdapter(adapter);

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    clearAllData();
                    getData(pageNum);
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
                    if ((originalityList.getResponse().getTotal() - (pageNum - 1) * 10) > 0) {
                        getData(pageNum);
                    } else {
                        ToastUtils.showShort(OriginalityListActivity.this, "最后一页了");
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //以便刷新页面
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (data == null) {//第一次进来
            //加载动画
            dialog.show();
            getData(1);//第二个参数为空就是查所有
        } else {//第二次进来
            dialog.show();
            clearAllData();
            getData(pageNum);
        }
    }

    //获取当天的日期
    public String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }

    //清空所有数据
    public void clearAllData() {
        try {
            datas.clear();
            data.clear();
            pageNum = 1;
            originalityList = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData(final int page) {
        //活动创意列表查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("activityId", it.getStringExtra("activityId"));//活动编号
//                    jsonObject.put("activityId", "omQai06QFpe");//活动编号
                    jsonObject.put("startDate", beginTime);
                    jsonObject.put("endDate", endTime);
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
                            .url(BaseUrl.BaseZhang + "creativeApp/getActivityCreative")
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
                                originalityList = gson.fromJson(response.body().string(), OriginalityList.class);
                                if (originalityList != null) {
                                    if (originalityList.getResponse().getData().size() == 0) {
                                        line_page.setVisibility(View.VISIBLE);
                                    } else {
                                        list_creativeId.clear();//清空数据再添加
                                        for (int i = 0; i < originalityList.getResponse().getData().size(); i++) {
                                            list_creativeId.add(originalityList.getResponse().getData().get(i).getCreativeId());//此集合专门用于存放creativeid，以便传输
                                            data = new HashMap<String, Object>();
                                            data.put("title", getBitmap(originalityList.getResponse().getData().get(i).getCreativeUrl()));//缩略图
                                            data.put("data_" + 1, originalityList.getResponse().getData().get(i).getExposure());//曝光
                                            data.put("data_" + 2, originalityList.getResponse().getData().get(i).getClick());//点击
                                            data.put("data_" + 3, originalityList.getResponse().getData().get(i).getClickRate() + "%");//点击率
                                            data.put("data_" + 4, originalityList.getResponse().getData().get(i).getCpm());//cmp
                                            data.put("data_" + 5, originalityList.getResponse().getData().get(i).getCpc());//cpc
                                            data.put("data_" + 6, originalityList.getResponse().getData().get(i).getSpend());//花费
                                            data.put("data_" + 7, "移除");//操作
                                            data.put("creativeId", originalityList.getResponse().getData().get(i).getCreativeId());//广告编号
                                            datas.add(data);
                                        }
                                        pageNum++;
                                        handler.sendEmptyMessage(1);//通知刷新适配器
                                        line_page.setVisibility(View.GONE);
                                    }
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try {
                        adapter.notifyDataSetChanged();
                        smartRefreshLayout.finishLoadmore(0);//停止刷新
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    ToastUtils.showShort(OriginalityListActivity.this, "无数据");
                    smartRefreshLayout.finishLoadmore(0);//停止刷新
                    line_page.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    break;
                case 3:
                    ToastUtils.showShort(OriginalityListActivity.this, "删除失败");
                    break;
                case 4:
                    //加载动画
                    dialog.show();
                    clearAllData();
                    getData(pageNum);
                    ToastUtils.showShort(OriginalityListActivity.this, "删除成功");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                toFinish();
                finish();
                break;
            case R.id.iv_addoriginality:
                Intent intent = new Intent(this, OriginalityActivity.class);
                intent.putExtra("flag", 123);
                intent.putExtra("activityId", it.getStringExtra("activityId"));
                intent.putStringArrayListExtra("list", (ArrayList<String>) list_creativeId);
                startActivity(intent);
                break;
            case R.id.iv_date:
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

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
                        getData(pageNum);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
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
                addHViews((CHScrollView_Activity) v.findViewById(R.id.item_scroll));
                View[] views = new View[to.length];
                for (int i = 0; i < to.length; i++) {
                    View tv = v.findViewById(to[i]);
//                    tv.setOnClickListener(clickListener);
                    views[i] = tv;
                }
                v.setTag(views);
            }

            //点击缩略图
            ImageView item_title = v.findViewById(R.id.item_title);
            item_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bitmap", (Bitmap) datas.get(position).get("title"));
                    Intent it = new Intent(OriginalityListActivity.this, ShowImageActivity.class);
                    it.putExtras(bundle);
                    startActivity(it);
                    overridePendingTransition(R.anim.stat_activity_anim, 0);
                }
            });

            //点击移除
            TextView item_data11 = v.findViewById(R.id.item_data7);
            item_data11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteOriginality((String) datas.get(position).get("creativeId"));
                }
            });

            View[] holders = (View[]) v.getTag();
            int len = holders.length;
            for (int i = 0; i < len; i++) {
                if (i == 0) {
                    ((ImageView) holders[i]).setImageBitmap((Bitmap) this.datas.get(position).get(from[i]));
                } else {
                    ((TextView) holders[i]).setText(this.datas.get(position).get(from[i]).toString());
                }
            }

            //让listview交替变色
            if (position % 2 == 0) {
                //偶数
                v.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                //奇数
                v.setBackgroundColor(Color.parseColor("#e7e9ea"));
            }

            return v;
        }
    }

    public void addHViews(final CHScrollView_Activity hScrollView) {
        if (!mHScrollViews.isEmpty()) {
            int size = mHScrollViews.size();
            CHScrollView_Activity scrollView = mHScrollViews.get(size - 1);
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
        for (CHScrollView_Activity scrollView : mHScrollViews) {
            //防止重复滑动
            if (mTouchView != scrollView)
                scrollView.smoothScrollTo(l, t);
        }
    }

    public void deleteOriginality(final String creativeId) {
        //活动创意删除接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("siteId", share.getString("siteid", ""));
                    jsonObject.put("activityId", it.getStringExtra("activityId"));//活动编号
//                    jsonObject.put("activityId", "omQai06QFpe");//活动编号
                    jsonObject.put("creativeId", creativeId);
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
                            .url(BaseUrl.BaseZhang + "creativeApp/delActivityCreative")
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
                                if (json.get("status").toString().equals("true")) {//如果删除成功,就刷新数据
                                    handler.sendEmptyMessage(4);
                                } else {
                                    handler.sendEmptyMessage(3);
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
                    mListView = null;
                    mTouchView = null;
                    mHScrollViews.clear();
                    smartRefreshLayout = null;
                    originalityList = null;
                    datas.clear();
                    data.clear();
                    beginTime = null;
                    endTime = null;
                    adapter = null;
                    list_creativeId.clear();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }

}
