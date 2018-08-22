package com.zhiziyun.dmptest.bot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MasterC on 2017/10/16.
 * Shanghai Deaon Information Technology Co.,Ltd
 */

public class DateUtil {

    //获取指定时间 的时间戳字符串
    public static String createTimeStrTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static String gettodayDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    public static String getDateMon() {
        //过去一月
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    public static String date(String date) {
        int index1 = date.indexOf("-");
        int index2 = index1 + date.substring(date.indexOf("-") + 1).indexOf("-") + 1;
        String year = date.substring(0, index1);
        String month = date.substring(index1 + 1, index2).length() == 1 ? "0" + date.substring(index1 + 1, index2) : date.substring(index1 + 1, index2);
        String day = date.substring(index2 + 1).length() == 1 ? "0" + date.substring(index2 + 1) : date.substring(index2 + 1);
        return year + "-" + month + "-" + day;
    }

}
