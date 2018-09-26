package com.zhiziyun.dmptest.bot.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.adapter.GeneralAdapter;
import com.zhiziyun.dmptest.bot.ui.activity.TransactionDetailsActivity;
import com.zhiziyun.dmptest.bot.ui.activity.VisitorsViewActivity;
import com.zhiziyun.dmptest.bot.ui.fragment.AdvertisingFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.CustomerFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.FriendsFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.SMSFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.TimeSlotFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.TrendFragment;
import com.zhiziyun.dmptest.bot.ui.fragment.VisitorsselfFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 所有下拉popwin共用，通过activityname区分
 */

public class PopWin_general extends PopupWindow {
    private Context mContext;
    private View view;
    private ListView listView;
    private GeneralAdapter cbAdapter;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private String activityname;

    public PopWin_general(final Context mContext, String activityname, ArrayList<HashMap<String, Object>> list_shop) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_general, null);
        this.list = list_shop;
        this.activityname = activityname;
        initView();
        // 设置外部可点击
        this.setOutsideTouchable(true);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);//高
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);//宽

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.popwin_anim);
//        mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.pop_layout).getHeight();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }

        });
    }

    private void initView() {
        listView = view.findViewById(R.id.listView);
        cbAdapter = new GeneralAdapter(mContext, list);
        listView.setAdapter(cbAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choose(activityname, position);
            }
        });
    }

    public void choose(String activity, int position) {
        switch (activity) {//通过名字来判断执行那一段
            case "VisitorsselfFragment":
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    VisitorsselfFragment.fragment.list_shop.get(i).put("boolean", false);
                }
                VisitorsselfFragment.fragment.list_shop.get(position).put("boolean", true);
                String str = list.get(position).get("mac").toString();
                VisitorsselfFragment.fragment.storeId = Integer.parseInt(str);
                VisitorsselfFragment.fragment.clearAllData();
                VisitorsselfFragment.fragment.getData(1, "");
                VisitorsselfFragment.fragment.tv_shop.setText(list.get(position).get("name").toString());
                dismiss();
                break;
            case "VisitorsselfFragment_two":
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    VisitorsselfFragment.fragment.list_tanzhen.get(i).put("boolean", false);
                }
                VisitorsselfFragment.fragment.list_tanzhen.get(position).put("boolean", true);
                String str2 = list.get(position).get("mac").toString();
                if (str2.equals("0")) {
                    VisitorsselfFragment.fragment.microprobeId = "0";
                } else {
                    VisitorsselfFragment.fragment.microprobeId = str2.substring(0, str2.indexOf("_"));
                }
                VisitorsselfFragment.fragment.clearAllData();
                VisitorsselfFragment.fragment.getData(1, "");
                VisitorsselfFragment.fragment.tv_tanzhen.setText(list.get(position).get("name").toString());
                dismiss();
                break;
            case "CustomerFragment_customer":
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    CustomerFragment.fragment.list_customer.get(i).put("boolean", false);
                }
                CustomerFragment.fragment.list_customer.get(position).put("boolean", true);
                CustomerFragment.fragment.dialog.show();
                CustomerFragment.fragment.tv_customer.setText(list.get(position).get("name").toString());
                CustomerFragment.fragment.m_type = Integer.parseInt(list.get(position).get("mac").toString());
                CustomerFragment.fragment.clearAllData();
                CustomerFragment.fragment.getData(1, "", null);
                dismiss();
                break;
            case "CustomerFragment_follow":
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    CustomerFragment.fragment.list_follow.get(i).put("boolean", false);
                }
                CustomerFragment.fragment.list_follow.get(position).put("boolean", true);
                CustomerFragment.fragment.dialog.show();
                CustomerFragment.fragment.tv_follow.setText(list.get(position).get("name").toString());
                CustomerFragment.fragment.m_mark = Integer.parseInt(list.get(position).get("mac").toString());
                CustomerFragment.fragment.clearAllData();
                CustomerFragment.fragment.getData(1, "", null);
                dismiss();
                break;
            case "TransactionDetailsActivity"://交易明细
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    TransactionDetailsActivity.activity.list.get(i).put("boolean", false);
                }
                TransactionDetailsActivity.activity.list.get(position).put("boolean", true);
                TransactionDetailsActivity.activity.dialog.show();
                TransactionDetailsActivity.activity.tv_shop.setText(list.get(position).get("name").toString());
                TransactionDetailsActivity.activity.clearAllData();
                TransactionDetailsActivity.activity.getData(1, Integer.parseInt(list.get(position).get("mac").toString()));
                dismiss();
                break;
            case "AdvertisingFragment"://投广告
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    AdvertisingFragment.fragment.list_state.get(i).put("boolean", false);
                }
                AdvertisingFragment.fragment.list_state.get(position).put("boolean", true);
                AdvertisingFragment.fragment.dialog.show();
                AdvertisingFragment.fragment.tv_state.setText(list.get(position).get("name").toString());
                AdvertisingFragment.fragment.clearAllData();
                AdvertisingFragment.fragment.activityStatus = Integer.parseInt(list.get(position).get("mac").toString());
                AdvertisingFragment.fragment.getData(1, "", AdvertisingFragment.fragment.activityStatus);
                dismiss();
                break;
            case "SMSFragment"://发短信
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    SMSFragment.smsFragment.list_state.get(i).put("boolean", false);
                }
                SMSFragment.smsFragment.list_state.get(position).put("boolean", true);
                SMSFragment.smsFragment.dialog.show();
                SMSFragment.smsFragment.tv_state.setText(list.get(position).get("name").toString());
                SMSFragment.smsFragment.clearAllData();
                SMSFragment.smsFragment.status = Integer.parseInt(list.get(position).get("mac").toString());
                SMSFragment.smsFragment.getData(1, "", SMSFragment.smsFragment.status);
                dismiss();
                break;
            case "FriendsFragment"://朋友圈
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    FriendsFragment.friendsFragment.list_state.get(i).put("boolean", false);
                }
                FriendsFragment.friendsFragment.list_state.get(position).put("boolean", true);
                FriendsFragment.friendsFragment.dialog.show();
                FriendsFragment.friendsFragment.tv_state.setText(list.get(position).get("name").toString());
                FriendsFragment.friendsFragment.clearAllData();
                FriendsFragment.friendsFragment.configuredStatus = list.get(position).get("mac").toString();
                FriendsFragment.friendsFragment.getData(1, "", FriendsFragment.friendsFragment.configuredStatus);
                dismiss();
                break;
            case "VisitorsViewActivity_one"://画像1
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    VisitorsViewActivity.visitorsViewActivity.list_shop.get(i).put("boolean", false);
                }
                VisitorsViewActivity.visitorsViewActivity.list_shop.get(position).put("boolean", true);
                String str1 = list.get(position).get("mac").toString();
                VisitorsViewActivity.visitorsViewActivity.storeId = Integer.parseInt(str1);
                VisitorsViewActivity.visitorsViewActivity.getTable();
                VisitorsViewActivity.visitorsViewActivity.tv_shop.setText(list.get(position).get("name").toString());
                dismiss();
                break;
            case "VisitorsViewActivity_two"://画像2
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    VisitorsViewActivity.visitorsViewActivity.list_tanzhen.get(i).put("boolean", false);
                }
                VisitorsViewActivity.visitorsViewActivity.list_tanzhen.get(position).put("boolean", true);
                String str3 = list.get(position).get("mac").toString();
                if (str3.equals("0")) {
                    VisitorsViewActivity.visitorsViewActivity.microprobeId = 0;
                } else {
                    VisitorsViewActivity.visitorsViewActivity.microprobeId = Integer.parseInt(str3.substring(str3.indexOf("_") + 1));
                }
                VisitorsViewActivity.visitorsViewActivity.getTable();
                VisitorsViewActivity.visitorsViewActivity.tv_tanzhen.setText(list.get(position).get("name").toString());
                dismiss();
                break;
            case "TimeSlotFragment_one"://时段1
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    TimeSlotFragment.fragment.list_shop.get(i).put("boolean", false);
                }
                TimeSlotFragment.fragment.list_shop.get(position).put("boolean", true);
                String str_shop = list.get(position).get("mac").toString();
                TimeSlotFragment.fragment.storeId = Integer.parseInt(str_shop);
                TimeSlotFragment.fragment.hm_timeslot.clear();
                TimeSlotFragment.fragment.list_timeslot.clear();
                TimeSlotFragment.fragment.getvisitHour();
                TimeSlotFragment.fragment.tv_shop.setText(list.get(position).get("name").toString());
                dismiss();
                break;
            case "TimeSlotFragment_two"://时段2
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    TimeSlotFragment.fragment.list_tanzhen.get(i).put("boolean", false);
                }
                TimeSlotFragment.fragment.list_tanzhen.get(position).put("boolean", true);
                String str_tanzhen = list.get(position).get("mac").toString();
                if (str_tanzhen.equals("0")) {
                    TimeSlotFragment.fragment.microprobeId = 0;
                } else {
                    TimeSlotFragment.fragment.microprobeId = Integer.parseInt(str_tanzhen.substring(str_tanzhen.indexOf("_") + 1));
                }
                TimeSlotFragment.fragment.hm_timeslot.clear();
                TimeSlotFragment.fragment.list_timeslot.clear();
                TimeSlotFragment.fragment.getvisitHour();
                TimeSlotFragment.fragment.tv_tanzhen.setText(list.get(position).get("name").toString());
                dismiss();
                break;
            case "TrendFragment_shop"://趋势—门店
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    TrendFragment.fragment.list_shop.get(i).put("boolean", false);
                }
                TrendFragment.fragment.list_shop.get(position).put("boolean", true);
                String str_trendFragment_shop = list.get(position).get("mac").toString();
                TrendFragment.fragment.storeId = Integer.parseInt(str_trendFragment_shop);
                TrendFragment.fragment.hm_trend.clear();
                TrendFragment.fragment.clearAllData();
                TrendFragment.fragment.getTrend();
                TrendFragment.fragment.tv_shop.setText(list.get(position).get("name").toString());
                dismiss();
                break;
            case "TrendFragment_tanzhen"://趋势—探针
                for (int i = 0; i < list.size(); i++) {//先把之前的选中状态清空
                    TrendFragment.fragment.list_tanzhen.get(i).put("boolean", false);
                }
                TrendFragment.fragment.list_tanzhen.get(position).put("boolean", true);
                String str_trendFragment_tanzhen = list.get(position).get("mac").toString();
                if (str_trendFragment_tanzhen.equals("0")) {
                    TrendFragment.fragment.microprobeId = 0;
                } else {
                    TrendFragment.fragment.microprobeId = Integer.parseInt(str_trendFragment_tanzhen.substring(str_trendFragment_tanzhen.indexOf("_") + 1));
                }
                TrendFragment.fragment.hm_trend.clear();
                TrendFragment.fragment.clearAllData();
                TrendFragment.fragment.getTrend();
                TrendFragment.fragment.tv_tanzhen.setText(list.get(position).get("name").toString());
                dismiss();
                break;

        }
    }

}
