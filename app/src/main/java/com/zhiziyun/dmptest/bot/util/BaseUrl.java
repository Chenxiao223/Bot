package com.zhiziyun.dmptest.bot.util;

/**
 * Created by Administrator on 2017/12/21.
 */

public class BaseUrl {
    //版本号
    public static final String BaseVersion = "v1.3.1";
    //日志采集
    public static final String BaseJiang = "http://trace.zhiziyun.com/app/v1/";
    //测试环境
//    public static final String BaseLogin = "http://test2.zhiziyun.com/loginuser/login.action";
//    public static final String BaseZhang = "http://test2.zhiziyun.com/api-service/api/";
//    public static final String BaseWang = "http://test2.zhiziyun.com/box/api/v1/";

    //正式环境
    //专门用于登录
    public static final String BaseLogin = "http://mc.zhiziyun.com/loginuser/login.action";
    //张伟提供的接口，用于：查询广告主logo、修改广告主logo、结算账户总览、结算账户消费详情
    public static final String BaseZhang = "http://mc.zhiziyun.com/api-service/api/";
    //王柏浩提供的接口,用于所有剩下的接口
    public static final String BaseWang = "http://mc.zhiziyun.com/box/api/v1/";
}
