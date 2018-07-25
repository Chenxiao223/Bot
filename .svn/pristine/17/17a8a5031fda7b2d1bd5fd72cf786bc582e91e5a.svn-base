package com.zhiziyun.dmptest.bot.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Chailin on 2017/7/15.
 * Shanghai Deaon Information Technology Co.,Ltd
 */

public class StringUtil {

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证车牌号
     */
    public static final String REGEX_PLATE = "^[冀豫云辽黑湘皖鲁苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼渝京津沪新军空海北沈兰济南广成使领][A-HJ-NP-Z][A-HJ-NP-Z0-9]{5,6}";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,10}$";
    /**
     * 正则表达式：验证用户名
     * 大于等于1小于等于5
     */
    public static final String REGEX_USERNAME = "^[\u4e00-\u9fa5]{2,5}$";

    /**
     * 去除String类型数字小数点后面部分
     */
    public static String stringInt(String str) {
        return str.substring(0, str.indexOf("."));
    }

    /**
     * MD5加密所需字符
     */
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验车牌号
     *
     * @param plate
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPlate(String plate) {
        return Pattern.matches(REGEX_PLATE, plate);
    }

    //字符串截取
    public static String substringBefore(String str, String separator) {
        if (isEmpty(str) || separator == null)
            return str;
        if (separator.length() == 0)
            return "";
        int pos = str.indexOf(separator);
        if (pos == -1)
            return str;
        else
            return str.substring(0, pos);
    }

    /**
     * 获得字符串的md5值
     *
     * @param str 待加密的字符串
     * @return md5加密后的字符串
     */
    public static String getMD5String(String str) {
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes2Hex(bytes);

    }

    /**
     * 获得加盐md5，算法过程是原字符串md5后连接加盐字符串后再进行md5
     *
     * @param str  待加密的字符串
     * @param salt 盐
     * @return 加盐md5
     */
    public static String getMD5AndSalt(String str, String salt) {

        return getMD5String(str.concat(salt));
    }

    /**
     * bytes数组转16进制String
     *
     * @param data bytes数组
     * @return 转化结果
     */

    private static String bytes2Hex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = hexDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = hexDigits[0x0F & data[i]];
        }
        return new String(out);
    }
}
