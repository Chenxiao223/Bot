package com.zhiziyun.dmptest.bot.util;

/**
 * Created by Administrator on 2017/12/21.
 */

public class BaseUrl {
    //测试接口
//    public static final String BaseLogin="http://test2.zhiziyun.com/site-zcloud-v2/loginuser/login.action";
//    public static final String BaseZhang="http://test.zhiziyun.com/api-service/";
//    public static final String BaseWang="http://dmptest.zhiziyun.com/api/v1/";

    //正式接口
    //专门用于登录
    public static final String BaseLogin="http://mc.zhiziyun.com/loginuser/login.action";
    //张伟提供的接口，用于：查询广告主logo、修改广告主logo、结算账户总览、结算账户消费详情
    public static final String BaseZhang="http://app.zhiziyun.com/api-service/";
    //王柏浩提供的接口,用于所有剩下的接口
    public static final String BaseWang="http://mc.zhiziyun.com/box/api/v1/";
}
