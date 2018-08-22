package com.zhiziyun.dmptest.bot.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by chailin on 2017/6/29.
 * Json数据处理类
 */

public class GsonUtil {

    private static Gson gson;


    /**
     * 将json字符串转为 java对象
     */
    synchronized public static <T> T parse(String json, Class<T> classOfT) {
        return getGsonInstance(false).fromJson(json, classOfT);
    }

    /**
     * 将json字符串转为 java列表对象
     */
    synchronized public static <T> ArrayList<T> parse(String json, Type type) {
        return getGsonInstance(false).fromJson(json, type);

    }

    /**
     * 将obj对象转为json格式数据
     */
    synchronized public static String toJson(Object obj) {
        return getGsonInstance(false).toJson(obj);
    }

    /**
     * 获取gson实例
     */
    public static final Gson getGsonInstance(boolean isExplain) {
        if (!isExplain) {
            gson = new Gson();
        } else {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .create();
        }
        return gson;
    }

    /**
     * 获取gson实例(排除了FINAL、TRANSIENT、STATIC)
     */
    public static final Gson getGsonInstance() {
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls().create();
        return gson;
    }

}
