package com.zhiziyun.dmptest.bot.util;

import android.util.Log;

import com.zhiziyun.dmptest.bot.http.DESCoder;

/**
 * Created by Administrator on 2017/12/1.
 */

public class Token {
    public static String gettoken() throws Exception {
            return DESCoder.encrypt("1" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
    }
}
