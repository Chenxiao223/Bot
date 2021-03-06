package com.zhiziyun.dmptest.bot.network;


/**
 * Created by Chailin on 2017/6/29.
 * Shanghai Deaon Information Technology Co.,Ltd
 */

public class NetWorkApi {
    //    获取创意模板包列表
    public static final String originality = "api/creativeTemplateApp/getCreativeTemplatePackageList";
    //    获取创意模板包详情
    public static final String getCreativeTemplatePackageDetail = "api/creativeTemplateApp/getCreativeTemplatePackageDetail";
    //    获取创意模包真实预览
    public static final String getCreativeTemplatePackageRealyPreview = "api/creativeTemplateApp/getCreativeTemplatePackageRealyPreview";
    //    广告活动创建
    public static final String createCreativeActivity = "api/activityApp/createCreativeActivity";
    //    朋友圈账户总览
    public static final String wechatAccount = "api/advertiserApp/wechatAccount";
    //    朋友圈账户消费详情
    public static final String wechatAccountDetail = "api/advertiserApp/wechatAccountDetail";
    //    广告活动详情
    public static final String getCreativeActivityDetail = "api/activityApp/getCreativeActivityDetail";
    //    广告活动编辑
    public static final String editCreativeActivity = "api/activityApp/editCreativeActivity";
    //    获取公众号门店列表
    public static final String storeList = "api/v1/wx_wifi_ad/listShop";
    //    获取WIFI人群列表
    public static final String wifiSegmentInfo = "api/v1/wifiSegmentInfo/list";
    //    向单个mac发送微信wifi广告
    public static final String sendWithOneMac = "api/v1/wx_wifi_ad/sendWithOneMac";
    //    根据wifi人群发送微信wifi广告
    public static final String sendWithSegment = "api/v1/wx_wifi_ad/sendWithSegment";
    //    根据现场scan得到的mac发送微信wifi广告
    public static final String sendWithMac = "api/v1/wx_wifi_ad/sendWithMac";
    public static final String accountSettleType = "api/optionsApp/accountSettleType";
    //    获取账户结算类型
    public static final String allSettleType = "api/optionsApp/accountSettleType";
    //    账户财务报表
    public static final String financialStatem = "api/advertiserApp/financialStatem";
    //    结算账户消费详情
    public static final String accountDetail = "api/advertiserApp/accountDetail";
//    public static final String wechatAccountDetail = "api/advertiserApp/accountDetail";
}
