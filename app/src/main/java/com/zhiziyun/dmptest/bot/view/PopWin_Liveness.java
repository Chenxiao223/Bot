package com.zhiziyun.dmptest.bot.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiziyun.dmptest.bot.R;
import com.zhiziyun.dmptest.bot.ui.activity.UserActiveActivity;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public class PopWin_Liveness extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    private View view;
    private RelativeLayout rl_high_active, rl_moderate_active, rl_low_active, rl_sleep;
    private TextView tv_determine;
    private Boolean bl_1 = false, bl_2 = false, bl_3 = false, bl_4 = false;
    private HashMap<String, String> hashMap = new HashMap<>();


    public PopWin_Liveness(final Context mContext, View.OnClickListener itemsOnClick, int flag) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.view_liveness, null);
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
        this.setAnimationStyle(R.style.take_photo_anim);
//        mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }

        });

    }

    public void initView() {
        tv_determine = (TextView) view.findViewById(R.id.tv_determine);
        tv_determine.setOnClickListener(this);
        rl_high_active = (RelativeLayout) view.findViewById(R.id.rl_high_active);
        rl_high_active.setOnClickListener(this);
        rl_moderate_active = view.findViewById(R.id.rl_moderate_active);
        rl_moderate_active.setOnClickListener(this);
        rl_low_active = (RelativeLayout) view.findViewById(R.id.rl_low_active);
        rl_low_active.setOnClickListener(this);
        rl_sleep = (RelativeLayout) view.findViewById(R.id.rl_sleep);
        rl_sleep.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_high_active://高活跃
                final CheckBox cb = v.findViewById(R.id.cb_1);
                if (bl_1) {
                    bl_1 = false;
                    cb.setChecked(false);
                    hashMap.put("DATERANGEH", "");
                } else {
                    bl_1 = true;
                    cb.setChecked(true);
                    hashMap.put("DATERANGEH", "3");
                }
                break;
            case R.id.rl_moderate_active://中活跃
                final CheckBox cb2 = v.findViewById(R.id.cb_2);
                if (bl_2) {
                    bl_2 = false;
                    cb2.setChecked(false);
                    hashMap.put("DATERANGEM", "");
                } else {
                    bl_2 = true;
                    cb2.setChecked(true);
                    hashMap.put("DATERANGEM", "2");
                }
                break;
            case R.id.rl_low_active://低活跃
                final CheckBox cb3 = v.findViewById(R.id.cb_3);
                if (bl_3) {
                    bl_3 = false;
                    cb3.setChecked(false);
                    hashMap.put("DATERANGEL", "");
                } else {
                    bl_3 = true;
                    cb3.setChecked(true);
                    hashMap.put("DATERANGEL", "1");
                }
                break;
            case R.id.rl_sleep://沉睡
                final CheckBox cb4 = v.findViewById(R.id.cb_4);
                if (bl_4) {
                    bl_4 = false;
                    cb4.setChecked(false);
                    hashMap.put("DATERANGES", "");
                } else {
                    bl_4 = true;
                    cb4.setChecked(true);
                    hashMap.put("DATERANGES", "0");
                }
                break;
            case R.id.tv_determine://确定
                if (hashMap.size() != 0) {
                    int i = 0;
                    String str = "";
                    //遍历hashmap，确定选中了几条
                    Iterator it = hashMap.keySet().iterator();
                    while (it.hasNext()) {
                        String key = (String) it.next();
                        if (!TextUtils.isEmpty(hashMap.get(key))) {
                            if (i == 0) {
                                str = str + hashMap.get(key);
                            } else {
                                str = str + "|" + hashMap.get(key);
                            }
                            i++;
                        }
                    }
                    UserActiveActivity.activity.active = str;
                }
                dismiss();
                UserActiveActivity.activity.old();
                break;
        }
    }

}
