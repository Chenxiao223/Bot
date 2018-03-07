package com.zhiziyun.dmptest.bot.util;

import com.zhiziyun.dmptest.bot.http.DESCoder;

/**
 * Created by Administrator on 2017/12/1.
 */

public class Token {
    public static String gettoken() throws Exception {
            return DESCoder.encrypt("1" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
    }

    public static String gettoken2() throws Exception {
        return DESCoder.encrypt("0zoTLi29XRgq_" + System.currentTimeMillis(), "510be9ce-c796-4d2d-a8b6-9ca8a426ec63");
    }
}
