package com.zhiziyun.dmptest.bot.util;

/**
 * Created by Administrator on 2018/1/22.
 * 广告尺寸
 */

public class AdvertisingSize {
    //选择尺寸
    public static String chooseSize(String size) {
        if (size.equals("320*50")) {
            return "53";
        } else if (size.equals("640*100")) {
            return "46";
        } else if (size.equals("600*500")) {
            return "56";
        } else if (size.equals("300*250")) {
            return "1";
        } else if (size.equals("480*320")) {
            return "303";
        } else if (size.equals("640*960")) {
            return "236";
        } else if (size.equals("728*90")) {
            return "8";
        } else if (size.equals("600*300")) {
            return "320";
        } else if (size.equals("1280*720")) {
            return "230";
        } else if (size.equals("580*240")) {
            return "663";
        } else if (size.equals("600*400")) {
            return "664";
        }
        return null;
    }

    //选择信息流
//    public static String chooseInfo(String info){
//        if (info.equals("750*350")){
//            return "lRUk90RuK*u";
//        }else if (info.equals("640*100")){
//            return "krH*I10w3eM";
//        }else if (info.equals("600*500")){
//            return "bVCHD0F*fvW";
//        }else if (info.equals("300*250")){
//            return "krHdD0vTG6I";
//        }else if (info.equals("480*320")){
//            return "sgClK0QpecE";
//        }else if (info.equals("480*320")){
//            return "236";
//        }
//        return null;
//    }
}
