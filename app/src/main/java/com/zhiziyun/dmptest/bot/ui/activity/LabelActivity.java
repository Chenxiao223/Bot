package com.zhiziyun.dmptest.bot.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.entity.FlagInfo;
import com.zhiziyun.dmptest.bot.util.BaseUrl;
import com.zhiziyun.dmptest.bot.util.MyDialog;
import com.zhiziyun.dmptest.bot.util.ToastUtils;
import com.zhiziyun.dmptest.bot.util.Token;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 2018/1/9.
 * 标签页，包括：游戏兴趣，应用兴趣，人口属性
 */

public class LabelActivity extends BaseActivity implements View.OnClickListener {
    private ExpandableListView expandableListView;
    private FlagInfo flagInfo;
    private int FLAGS = 9527;
    private MyDialog dialog;
    private HashMap<String, Boolean> hashMap_child = new HashMap<>();//记录子标签选中状态
    private HashMap<String, Boolean> hashMap_group = new HashMap<>();//记录父标签选中状态

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        initView();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
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

        FLAGS = getIntent().getIntExtra("flag", 9527);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setGroupIndicator(null);//将控件默认的左边箭头去掉，
        findViewById(R.id.tv_commit).setOnClickListener(this);
        //子节点的点击事件
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                MyExpandableListView.ViewHolder viewHolder = (MyExpandableListView.ViewHolder) v.getTag();
                viewHolder.cb_child.toggle();
                if (viewHolder.cb_child.isChecked()) {//被选中状态
                    hashMap_child.put("" + groupPosition + childPosition, true);
                    AddCorwdActivity.addCorwdActivity.list_lable.add(flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getChildren().get(childPosition).getId() + "");
                } else {//从选中状态转化为未选中
                    hashMap_child.put("" + groupPosition + childPosition, false);
                    AddCorwdActivity.addCorwdActivity.list_lable.remove(flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getChildren().get(childPosition).getId() + "");
                }
                return true;
            }
        });
        //父节点点击事件
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {//判断当前是否是展开状态
                    parent.collapseGroup(groupPosition);//如果是就关闭
                } else {
                    parent.expandGroup(groupPosition);//如果不是就展开
                    //判断子节点是否为空，若为空则提示;之所以放这里就是让其打开的时候判断，关闭就不判断了
                    if (flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getChildren().isEmpty()) {
                        ToastUtils.showShort(LabelActivity.this, "无子节点");
                    }
                }
                return true;
            }
        });
        findViewById(R.id.tv_back).setOnClickListener(this);
        getFlagInfo();
    }

    public void getFlagInfo() {
        //加载动画
        dialog = MyDialog.showDialog(this);
        dialog.show();
        //获取标签列表接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = null;
                    try {
                        url = "agentId=1&token=" + URLEncoder.encode(Token.gettoken(), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, url);
                    final Request request = new Request.Builder()
                            .url(BaseUrl.BaseWang + "builtCrowd/tagslist.action")
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
                            flagInfo = gson.fromJson(response.body().string(), FlagInfo.class);
                            handler.sendEmptyMessage(1);
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
                    for (int i = 0; i < flagInfo.getRows().get(FLAGS).getChildren().size(); i++) {
                        hashMap_group.put("" + i, false);
                        for (int j = 0; j < flagInfo.getRows().get(FLAGS).getChildren().get(i).getChildren().size(); j++) {
                            hashMap_child.put("" + i + j, false);
                        }
                    }
                    expandableListView.setAdapter(new MyExpandableListView(LabelActivity.this, hashMap_child, hashMap_group));
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                toFinish();
                finish();
                break;
            case R.id.tv_commit:
                if (FLAGS == 0) {//游戏兴趣
                    AddCorwdActivity.addCorwdActivity.tv_game.setText(calculate() + "个");
                    AddCorwdActivity.addCorwdActivity.tv_game.setTextColor(this.getResources().getColor(R.color.defaultcolor));
                } else if (FLAGS == 1) {//应用兴趣
                    AddCorwdActivity.addCorwdActivity.tv_app.setText(calculate() + "个");
                    AddCorwdActivity.addCorwdActivity.tv_app.setTextColor(this.getResources().getColor(R.color.defaultcolor));
                } else if (FLAGS == 2) {//人口属性
                    AddCorwdActivity.addCorwdActivity.tv_property.setText(calculate() + "个");
                    AddCorwdActivity.addCorwdActivity.tv_property.setTextColor(this.getResources().getColor(R.color.defaultcolor));
                }
                finish();
                break;
        }
    }

    public int calculate() {
        int num = 0;
        Iterator iter_group = hashMap_group.entrySet().iterator();
        while (iter_group.hasNext()) {
            Map.Entry entry = (Map.Entry) iter_group.next();
            if ((boolean) entry.getValue() == true) {
                num += 1;
            }
        }
        Iterator iter_child = hashMap_child.entrySet().iterator();
        while (iter_child.hasNext()) {
            Map.Entry entry = (Map.Entry) iter_child.next();
            if ((boolean) entry.getValue() == true) {
                num += 1;
            }
        }
        return num;
    }

    //为ExpandableListView自定义适配器
    class MyExpandableListView extends BaseExpandableListAdapter {
        private Context context;
        private HashMap<String, Boolean> hashMaps_child;
        private HashMap<String, Boolean> hashMaps_group;

        public MyExpandableListView(Context context, HashMap<String, Boolean> hashMaps_child, HashMap<String, Boolean> hashMaps_group) {
            this.context = context;
            this.hashMaps_group = hashMaps_group;//list中checkbox状态为false
            this.hashMaps_child = hashMaps_child;
        }

        //返回一级列表的个数
        @Override
        public int getGroupCount() {
            return flagInfo.getRows().get(FLAGS).getChildren().size();
        }

        //返回每个二级列表的个数
        @Override
        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
            return flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getChildren().size();
        }

        //返回一级列表的单个item（返回的是对象）
        @Override
        public Object getGroup(int groupPosition) {
            return flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getName();
        }

        //返回二级列表中的单个item（返回的是对象）
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getChildren().get(childPosition).getName();  //不要误写成groups[groupPosition][childPosition]
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
            return false;
        }

        //【重要】填充一级列表
        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_group, null);
                holder.tv_group = convertView.findViewById(R.id.tv_group);
                holder.iv_arrows = convertView.findViewById(R.id.iv_jiantou);
                holder.cb_group = convertView.findViewById(R.id.cb_group);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_group.setText(flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getName());
            //判断isExpanded就可以控制是按下还是关闭，同时更换图片
            if (isExpanded) {
                holder.iv_arrows.setBackgroundResource(R.drawable.up);
            } else {
                holder.iv_arrows.setBackgroundResource(R.drawable.down);
            }
            holder.cb_group.setChecked(hashMaps_group.get("" + groupPosition));//记录哪些CheckBox被选中
            holder.cb_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CheckBox cb = v.findViewById(R.id.cb_group);
                    if (cb.isChecked()) {//被选中状态
                        hashMap_group.put("" + groupPosition, true);
                        AddCorwdActivity.addCorwdActivity.list_lable.add(flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getId() + "");
                    } else {//从选中状态转化为未选中
                        hashMap_group.put("" + groupPosition, false);
                        AddCorwdActivity.addCorwdActivity.list_lable.remove(flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getId() + "");
                    }
                }
            });
            return convertView;
        }

        //【重要】填充二级列表
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_child, null);
                holder.cb_child = convertView.findViewById(R.id.cb_child);
                holder.tv_child = convertView.findViewById(R.id.tv_child);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.cb_child.setChecked(hashMaps_child.get("" + groupPosition + childPosition));//记录哪些CheckBox被选中
            holder.tv_child.setText(flagInfo.getRows().get(FLAGS).getChildren().get(groupPosition).getChildren().get(childPosition).getName());
            return convertView;
        }

        //二级列表中的item是否能够被选中？可以改为true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public class ViewHolder {
            TextView tv_group, tv_child;
            ImageView iv_arrows;
            CheckBox cb_child, cb_group;
        }
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
                flagInfo = null;
                hashMap_child.clear();
                hashMap_group.clear();
                System.gc();
            }
        }, 500);
    }
}