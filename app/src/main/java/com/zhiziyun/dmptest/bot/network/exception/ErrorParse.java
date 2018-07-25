package com.zhiziyun.dmptest.bot.network.exception;


import android.content.Intent;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

/**
 * Created by Chailin on 2017/7/5.
 * 解析网络请求的错误信息
 */
public class ErrorParse {

    //服务器返回码
    /**
     * 用户名或者密码不正确
     */
    private static final int USER_NOT_EXIST = 201;
    /**
     * 用户名与密码不能为空
     */
    private static final int USER_PWD_ISNULL = 202;
    /**
     * token错误
     */
    private static final int TOKEN_ERROR = 203;
    /**
     * 该用户名已存在
     */
    private static final int USER_EXISTS = 204;
    /**
     * 用户权限不足
     */
    public static final int USER_NO_PERMISSION = 205;
    /**
     * 记录不存在
     */
    private static final int RECORD_NOT_EXISTS = 206;
    /**
     * 创建失败
     */
    public static final int ADD_RECORD_FAILED = 207;
    /**
     * 账号冻结
     */
    private static final int ACCOUNT_FROZEN = 2003;
    /**
     * 账号删除
     */
    private static final int ACCOUNT_DELETED = 2004;
    /**
     * 缺少必要的检查
     */
    private static final int CHECK_ABSENT = 2010;
    /**
     * 系统错误 未知错误
     */
    private static final int ERROR = 9999;
    /**
     * 计划已存在
     */
    private static final int PLAN_EXISTS = 800;
    /**
     * 计划时间冲突
     */
    private static final int PLAN_TIME_EXISTS = 2800;


    //HTTP的状态码
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


//    public static void parse(Subscriber subscriber, Throwable e) {
//        e.printStackTrace();
//        if (e instanceof SocketTimeoutException) {
//            subscriber.onError(new Exception("网络链接超时,请稍后重试"));
//        } else if (e instanceof ConnectException) {
//            subscriber.onError(new Exception("网络链接失败,请检查网络设置"));
//        } else if (e instanceof JSONException) {
//            subscriber.onError(new Exception("数据解析失败,请稍候重试"));
//        } else if (e instanceof IOException) {
//            subscriber.onError(new Exception("内部错误,请稍候重试"));
//        } else {
//            subscriber.onError(new Exception("未知错误"));
//        }
//    }

    public static Throwable parse(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            return new Throwable("网络链接超时,请稍后重试");
        } else if (e instanceof ConnectException) {
            return new Throwable("网络链接失败,请检查网络设置");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            return new Throwable("数据解析失败,请稍候重试");
        } else {
            return e;
        }
    }


    public static Throwable parsa(int code) {
        ApiException e = null;
        switch (code) {
            case USER_NOT_EXIST:
                e = new ApiException("用户名或者密码不正确");
                break;
            case USER_PWD_ISNULL:
                e = new ApiException("用户名与密码不能为空");
                break;
            case USER_EXISTS:
                e = new ApiException("该用户名已存在");
                break;
            case USER_NO_PERMISSION:
                e = new ApiException("用户权限不足");
                break;
            case RECORD_NOT_EXISTS:
                e = new ApiException("记录不存在");
                break;
            case ADD_RECORD_FAILED:
                e = new ApiException("创建失败");
                break;
            case ACCOUNT_FROZEN:
                e = new ApiException("账号已冻结，不能正常使用");
                break;
            case ACCOUNT_DELETED:
                e = new ApiException("账号已删除");
                break;
            case CHECK_ABSENT:
                e = new ApiException("账户缺少必要的检查，请在库中配置");
                break;
            case ERROR:
                e = new ApiException("系统错误");
                break;
            case PLAN_EXISTS:
                e = new ApiException("计划已存在");
                break;
            case PLAN_TIME_EXISTS:
                e = new ApiException("计划时间冲突");
                break;
            default:
                e = new ApiException("出错了，请稍候重试");
                break;
        }
        return e;
    }

    public static ApiException handleException(int code) {
        ApiException e = null;
        switch (code) {
            case TOKEN_ERROR:
                Intent intent = new Intent("tokenError");
//                SmartKittyApp.getInstance().getApplicationContext().sendBroadcast(intent);
                break;
            case FORBIDDEN:
                e = new ApiException("禁止访问");
                break;
            case NOT_FOUND:
                e = new ApiException("页面不存在");
                break;
            case SERVICE_UNAVAILABLE:
            case BAD_GATEWAY:
            case INTERNAL_SERVER_ERROR:
                e = new ApiException("服务器内部错误!");
                break;
            case GATEWAY_TIMEOUT:
                e = new ApiException("服务器未响应");
                break;

        }
        return e;
    }


}
