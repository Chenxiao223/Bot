package com.zhiziyun.dmptest.bot.util;

import android.content.Context;
import android.content.SharedPreferences;



import java.lang.reflect.Type;
import java.util.ArrayList;


public class StorageMgr {
    private static SharedPreferences storage;

    public static void init(Context context) {
        storage = context.getSharedPreferences("smartKitty", Context.MODE_PRIVATE);
    }

    // 设置缓存信息
    private static void setStorage(String key, String value) {
        SharedPreferences.Editor editor = storage.edit();
        editor.putString(key, value);
        editor.apply(); // 先提交内存，再异步提交硬盘
    }

    /**
     * 获取对应key的缓存
     *
     * @param key 键值
     * @param t   缓存的类
     * @return
     * @throws Exception
     */
    public static <T> void set(String key, T t) throws RuntimeException {
        setStorage(key, GsonUtil.toJson(t));
    }

    /**
     * 设置key对应缓存
     *
     * @param key   键值
     * @param value 字符串
     * @return
     * @throws Exception
     */
    public static void set(String key, String value) throws RuntimeException {
        setStorage(key, value);
    }

    // 获取缓存信息
    private static String getStorage(String key) {
        return storage.getString(key, null);
    }

    /**
     * 获取对应key的缓存
     *
     * @param key 键值
     * @param c   需要序列化的类
     * @return
     * @throws Exception
     */
    public static <T> T get(String key, Class<T> c) {
        String value = get(key);
        if (value == null) {
            return null;
        }
        return GsonUtil.parse(value, c);
    }

    /**
     * 获取对应key的缓存
     *
     * @param key 键值
     * @return
     * @throws Exception
     */
    public static <T> ArrayList<T> get(String key, Type type) {
        String value = get(key);
        if (value == null) {
            return null;
        }
        return GsonUtil.parse(value, type);
    }

    /**
     * 获取对应key的缓存
     *
     * @param key 键值
     * @throws Exception
     */
    public static String get(String key) {
        return getStorage(key);
    }
}
