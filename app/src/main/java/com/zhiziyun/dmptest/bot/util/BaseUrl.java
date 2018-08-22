package com.zhiziyun.dmptest.bot.util;

import com.zhiziyun.dmptest.bot.BuildConfig;

/**
 * Created by Administrator on 2017/12/21.
 */

public class BaseUrl {
    //版本号
    public static final String BaseVersion = BuildConfig.versionName;
    //日志采集
    public static final String BaseJiang = "http://trace.zhiziyun.com/app/v1/";

    //通用测试环境接口
//    public static final String BaseTest = "http://test2.api.zhiziyun.com/api/v1/";
//    public static final String BaseZhang = "http://test2.api.zhiziyun.com/";
    //李俊鹏本地调试用
//    public static final String BaseZhang = "http://lisocket.viphk.ngrok.org/api-app/";

    //正式环境
    public static final String BaseTest = "http://m.zhiziyun.com/api/v1/";
    public static final String BaseZhang = "http://m.zhiziyun.com/";

    public static String BaseAPPName = BuildConfig.BASENAME;

}
