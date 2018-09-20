package com.zhiziyun.dmptest.bot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.util.CustomDialog;

import java.util.ArrayList;
import java.util.HashMap;

import ch.ielse.view.SwitchView;

/**
 * Created by Administrator on 2018/4/4.
 * 朋友圈的adapter
 */

public class FriendsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, Object>> list;
    private OnCheck onCheck;
    private Onclick onclick;

    public FriendsAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setOnCheck(FriendsAdapter.OnCheck onCheck) {
        this.onCheck = onCheck;
    }

    public void setOnclick(FriendsAdapter.Onclick onclick) {
        this.onclick = onclick;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.listview_friends, null);
            viewHold.tv_title = convertView.findViewById(R.id.tv_title);
            viewHold.tv_exposure = convertView.findViewById(R.id.tv_exposure);
            viewHold.tv_click = convertView.findViewById(R.id.tv_click);
            viewHold.tv_exposure_rate = convertView.findViewById(R.id.tv_exposure_rate);
            viewHold.tv_cost = convertView.findViewById(R.id.tv_cost);
            viewHold.tv_offer = convertView.findViewById(R.id.tv_offer);
            viewHold.tv_budget = convertView.findViewById(R.id.tv_budget);
            viewHold.tv_audit = convertView.findViewById(R.id.tv_audit);
            viewHold.swich = convertView.findViewById(R.id.swich);
            viewHold.image = convertView.findViewById(R.id.image);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        try {
            viewHold.tv_title.setText(list.get(position).get("content1").toString());
            viewHold.tv_exposure.setText(list.get(position).get("content2").toString());
            viewHold.tv_click.setText(list.get(position).get("content3").toString());
            viewHold.tv_exposure_rate.setText(list.get(position).get("content4").toString());
            viewHold.tv_cost.setText(list.get(position).get("content5").toString());
            viewHold.tv_offer.setText(list.get(position).get("content6").toString());
            viewHold.tv_budget.setText(list.get(position).get("content7").toString());
            viewHold.tv_audit.setText(list.get(position).get("content8").toString());
            if (list.get(position).get("content8").toString().equals("审核不通过")) {
                viewHold.tv_audit.setTextColor(Color.parseColor("#247ab7"));
                viewHold.tv_audit.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//加下滑线
            } else {
                viewHold.tv_audit.setTextColor(Color.parseColor("#a0a3af"));
                viewHold.tv_audit.getPaint().setFlags(Paint.EMBEDDED_BITMAP_TEXT_FLAG);
            }
            viewHold.tv_audit.getPaint().setAntiAlias(true);//抗锯齿
            switch (list.get(position).get("content9").toString()) {
                case "NORMAL":
                    viewHold.swich.setOpened(true);
                    viewHold.swich.setVisibility(View.VISIBLE);
                    break;
                case "SUSPEND":
                    viewHold.swich.setOpened(false);
                    viewHold.swich.setVisibility(View.VISIBLE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ViewHold finalViewHold = viewHold;
        viewHold.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //点击弹出对话框
                final CustomDialog customDialog = new CustomDialog(context);
                customDialog.setTitle("消息提示");
                if (list.get(position).get("content9").toString().equals("NORMAL")) {
                    customDialog.setMessage("是否暂停朋友圈投放？");
                } else if (list.get(position).get("content9").toString().equals("SUSPEND")) {
                    customDialog.setMessage("是否开启朋友圈投放？");
                }
                customDialog.setYesOnclickListener("确定", new CustomDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        try {
                            finalViewHold.swich.performClick();//使用代码主动去调用控件的点击事件（模拟人手去触摸控件）
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
            }
        });

        viewHold.swich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SwitchView sv = v.findViewById(R.id.swich);
                    if (sv.isOpened()) {
                        //参数：广告活动编号、投放状态、位置
                        onCheck.setInfo(list.get(position).get("wechatActivityId").toString(), "SUSPEND", position);//暂停
                    } else {
                        //参数：广告活动编号、投放状态、位置
                        onCheck.setInfo(list.get(position).get("wechatActivityId").toString(), "NORMAL", position);//开启
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        viewHold.tv_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.setclick(position);
            }
        });

        return convertView;
    }

    public static class ViewHold {
        private TextView tv_title, tv_exposure, tv_click, tv_exposure_rate, tv_cost, tv_offer, tv_budget, tv_audit;
        private SwitchView swich;
        private ImageView image;
    }

    public interface OnCheck {
        public void setInfo(String id, String activityStatus, int position);
    }

    public interface Onclick {
        public void setclick(int position);
    }

}
