package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.CrowdInfo;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.Token;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/8.
 * 通话记录
 */

public class CallRecordsActivity extends BaseActivity implements View.OnClickListener {
    private ExpandableListView expandableListView;
    private SharedPreferences share;
    //Model：定义的数据
    private String[] groups = {"数据一", "数据二", "数据三"};
    private String[][] childs = {{"数据一", "数据二", "数据三", "数据四"}, {"数据一", "数据二", "数据三", "数据四"}, {"数据一", "数据二", "数据三", "数据四"}};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_records);
        initView();
        getCallRecords();
    }

    private void initView() {
        //设置系统栏颜色
        ImageView iv_system = (ImageView) findViewById(R.id.iv_system);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_system.getLayoutParams();
        params.height = (int) getStatusBarHeight(this);//设置当前控件布局的高度

        share = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setGroupIndicator(null);//将控件默认的左边箭头去掉，
        expandableListView.setAdapter(new MyExpandableListView());
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void getCallRecords() {
        //获取通话记录
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("siteId", share.getString("siteid", ""));
                    json.put("userid", share.getString("accountid", ""));
//                    json.put("phoneA","");
//                    json.put("phoneB","");
//                    json.put("guestId","");
//                    json.put("binded","");
                    Log.i("infos", json.toString());
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
                            .url(BaseUrl.BaseWang + "dial/listPhoneLineSum.action")
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
                                Log.i("infos", str);
//                                Gson gson = new Gson();
//                                crowdInfo = gson.fromJson(response.body().string(), CrowdInfo.class);
//                                Message message = new Message();
//                                message.what = 1;
//                                message.obj = crowdInfo;
//                                handler.sendMessage(message);
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

    //为ExpandableListView自定义适配器
    class MyExpandableListView extends BaseExpandableListAdapter {

        //返回一级列表的个数
        @Override
        public int getGroupCount() {
            return groups.length;
        }

        //返回每个二级列表的个数
        @Override
        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
            return childs[groupPosition].length;
        }

        //返回一级列表的单个item（返回的是对象）
        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        //返回二级列表中的单个item（返回的是对象）
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childs[groupPosition][childPosition];  //不要误写成groups[groupPosition][childPosition]
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //每个item的id是否是固定？一般为true
        @Override
        public boolean hasStableIds() {
            return true;
        }

        //【重要】填充一级列表
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_group_title, null);
                holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
                holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
                holder.iv_jiantou = convertView.findViewById(R.id.iv_jiantou);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_date.setText(groups[groupPosition]);
            holder.tv_num.setText(groups[groupPosition]);
            holder.tv_amount.setText(groups[groupPosition]);
            //判断isExpanded就可以控制是按下还是关闭，同时更换图片
            if (isExpanded) {
                holder.iv_jiantou.setBackgroundResource(R.drawable.down);
            } else {
                holder.iv_jiantou.setBackgroundResource(R.drawable.up);
            }

            //让listview交替变色
            if (groupPosition % 2 == 0) {
                //偶数
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                //奇数
                convertView.setBackgroundColor(Color.parseColor("#e7e9ea"));
            }

            return convertView;
        }

        //【重要】填充二级列表
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_child_content, null);
                holder.tv_child = (TextView) convertView.findViewById(R.id.tv_child);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //iv_child.setImageResource(resId);
            holder.tv_child.setText(childs[groupPosition][childPosition]);

            //让listview交替变色
            if (groupPosition % 2 == 0) {
                //偶数
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                //奇数
                convertView.setBackgroundColor(Color.parseColor("#e7e9ea"));
            }

            return convertView;
        }

        //二级列表中的item是否能够被选中？可以改为true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public class ViewHolder {
            TextView tv_date, tv_num, tv_amount, tv_child;
            ImageView iv_jiantou;
        }
    }
}
