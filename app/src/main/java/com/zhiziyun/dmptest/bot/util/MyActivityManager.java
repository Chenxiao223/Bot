package com.zhiziyun.dmptest.bot.util;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by zhiziyun on 2018/7/27.
 */

public class MyActivityManager {
    private static LinkedList<Activity> acys = new LinkedList<Activity>();

    //添加activity
    public static void add(Activity acy){
        acys.add(acy);
    }

    //关闭所有的activity
    public static void close(){
        Activity acy;
        while (acys.size() != 0){

            //poll()方法检索并移除此列表的头元素(第一个元素)
            acy = acys.poll();
            if (!acy.isFinishing()){
                acy.finish();
            }
        }
    }
}
