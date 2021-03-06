package com.zhiziyun.dmptest.bot.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.ChildrenCustomerSourceAdapter;
import com.zhiziyun.dmptest.bot.adapter.ParentCategoryAdapter;
import com.zhiziyun.dmptest.bot.entity.CrowdList;
import com.zhiziyun.dmptest.bot.ui.fragment.CustomerFragment;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 选择PopupWindow
 *
 * @author ansen
 * @create time 2018-5-30
 */
public class PopWin_customer_source extends PopupWindow {
    private SelectCategory selectCategory;

    private String[] parentStrings;
    private String[][] childrenStrings;

    private ListView lvParentCategory = null;
    private ListView lvChildrenCategory = null;
    private ParentCategoryAdapter parentCategoryAdapter = null;
    private ChildrenCustomerSourceAdapter childrenAdapter = null;
    private int p1 = 0, p2 = 0, p3 = 0, parentP = 0;
    private String[] str = {"全部客户类型", "普通客户", "低价值客户", "积极客户", "高价值客户"};
    private SharedPreferences share;
    private CrowdList crowdList;
    private List<String> list = new ArrayList<>();
    private MyDialog dialog;

    /**
     * @param parentStrings   字类别数据
     * @param childrenStrings 字类别二位数组
     * @param activity
     * @param selectCategory  回调接口注入
     */
    public PopWin_customer_source(String[] parentStrings, String[][] childrenStrings, Activity activity, SelectCategory selectCategory) {
        this.selectCategory = selectCategory;
        this.parentStrings = parentStrings;
        this.childrenStrings = childrenStrings;
        share = activity.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        View contentView = LayoutInflater.from(activity).inflate(R.layout.view_customer_source, null);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小

        this.setContentView(contentView);
        this.setWidth(dm.widthPixels);
        this.setHeight(dm.heightPixels * 7 / 10);

        dialog = MyDialog.showDialog(activity);

        /* 设置背景显示 */
        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.pop_bg));
        /* 设置触摸外面时消失 */
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */
        /**
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);

        //父类别适配器
        lvParentCategory = (ListView) contentView.findViewById(R.id.lv_parent_category);
        parentCategoryAdapter = new ParentCategoryAdapter(activity, parentStrings);
        lvParentCategory.setAdapter(parentCategoryAdapter);

        //子类别适配器
        lvChildrenCategory = (ListView) contentView.findViewById(R.id.lv_children_category);
        childrenAdapter = new ChildrenCustomerSourceAdapter(activity, list);
        lvChildrenCategory.setAdapter(childrenAdapter);

        lvParentCategory.setOnItemClickListener(parentItemClickListener);
        lvChildrenCategory.setOnItemClickListener(childrenItemClickListener);
        getCrowd(0);
    }

    public void getCrowd(final int position) {
        dialog.show();
        //移动人群查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("page", 1);
                    json.put("rows", Integer.MAX_VALUE);//int的最大值
                    json.put("sort", "UpdateTime");
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
                            .url(BaseUrl.BaseTest + "deviceSegmentInfo/segmentList")
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
                                list.clear();
                                Gson gson = new Gson();
                                crowdList = gson.fromJson(response.body().string(), CrowdList.class);
                                for (int i = 0; i < crowdList.getRows().size(); i++) {
                                    list.add(crowdList.getRows().get(i).getName());
                                }
                                Message message = new Message();
                                message.what = 1;
                                message.obj = position;
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

    public void getWifiCrowd(final int position) {
        dialog.show();
        //wifi人群查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("sort", "UpdateTime");
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
                            .url(BaseUrl.BaseTest + "wifiSegmentInfo/list")
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
                                list.clear();
                                Gson gson = new Gson();
                                crowdList = gson.fromJson(response.body().string(), CrowdList.class);
                                for (int i = 0; i < crowdList.getRows().size(); i++) {
                                    list.add(crowdList.getRows().get(i).getName());
                                }
                                Message message = new Message();
                                message.what = 1;
                                message.obj = position;
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

    public void getClickCrowd(final int position) {
        dialog.show();
        //点击人群查询接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("sort", "UpdateTime");//按更新时间排序
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
                            .url(BaseUrl.BaseTest + "adClickSegmentInfo/list")
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
                                list.clear();
                                Gson gson = new Gson();
                                crowdList = gson.fromJson(response.body().string(), CrowdList.class);
                                for (int i = 0; i < crowdList.getRows().size(); i++) {
                                    list.add(crowdList.getRows().get(i).getName());
                                }
                                Message message = new Message();
                                message.what = 1;
                                message.obj = position;
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
                    childrenAdapter.setDatas(list);
                    childrenAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    break;
            }
        }
    };

    /**
     * 子类别点击事件
     */
    private OnItemClickListener childrenItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (selectCategory != null) {
                selectCategory.selectCategory(parentCategoryAdapter.getPos(), position);
                CustomerFragment.fragment.clearAllData();
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(crowdList.getRows().get(position).getId() + "");
                CustomerFragment.fragment.jsonArray = jsonArray;
                CustomerFragment.fragment.getData(1, "", jsonArray);
                CustomerFragment.fragment.tv_customer_source.setText(crowdList.getRows().get(position).getName());
            }
            dismiss();
        }
    };

    /**
     * 父类别点击事件
     */
    private OnItemClickListener parentItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            parentP = position;
            switch (position) {
                case 0://到店人群
                    getCrowd(position);
                    break;
                case 1://wifi人群
                    getWifiCrowd(position);
                    break;
                case 2://点击人群
                    getClickCrowd(position);
                    break;
                case 3://不限
                    CustomerFragment.fragment.jsonArray = null;
                    CustomerFragment.fragment.clearAllData();
                    CustomerFragment.fragment.getData(1, "", null);
                    CustomerFragment.fragment.tv_customer_source.setText("不限");
                    dismiss();
                    break;
            }
//            childrenAdapter.setDatas(list);
//            childrenAdapter.notifyDataSetChanged();
//
            parentCategoryAdapter.setSelectedPosition(position);
            parentCategoryAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 选择成功回调
     *
     * @author apple
     */
    public interface SelectCategory {
        /**
         * 把选中的下标通过方法回调回来
         *
         * @param parentSelectposition   父类别选中下标
         * @param childrenSelectposition 子类别选中下标
         */
        public void selectCategory(int parentSelectposition, int childrenSelectposition);

    }

}
