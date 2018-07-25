package com.zhiziyun.dmptest.bot.network;

import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiziyun.dmptest.bot.gsonconverter.GsonConverterFactory;
import com.zhiziyun.dmptest.bot.gsonconverter.NullOnEmptyConverterFactory;
import com.zhiziyun.dmptest.bot.network.exception.ApiException;
import com.zhiziyun.dmptest.bot.network.exception.ErrorParse;
import com.zhiziyun.dmptest.bot.util.IsEmpty;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Chailin on 2017/9/14.
 * Shanghai Deaon Information Technology Co.,Ltd
 */

public class ApiClient {
    private static String baseUrlBaseTest = "http://test2.api.zhiziyun.com/api/v1/";
    private static String baseUrlBaseZhang = "http://test2.api.zhiziyun.com/";
    private static Retrofit platformClient;
    private static Retrofit baseTestClient;
    private static Retrofit baseZhangClient;
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    // 拦截器 处理请求的status code
    private static Interceptor requestErrorInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            ApiException e = ErrorParse.handleException(response.code());
            if (!IsEmpty.object(e)) {
                throw e;
            }
            return response;
        }
    };


    // 获取随机token并设置token到header
    private static Interceptor setUserCookie = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request;
            request = chain.request().newBuilder()
                    .addHeader("content-type", "application/x-www-form-urlencoded").build();
            return chain.proceed(request);
        }
    };


    // 没有Token
    private static Interceptor getUserCookie = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    };


    // 没有Token  baseUrlBaseZhang请求
    public static Retrofit platformClient() {
        if (null != platformClient) {
            return platformClient;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okClient = new OkHttpClient.Builder().addInterceptor(getUserCookie)
                .addInterceptor(requestErrorInterceptor).retryOnConnectionFailure(true)
                .addInterceptor(logging).build();
        platformClient = new Retrofit.Builder().baseUrl(baseUrlBaseZhang).client(okClient)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        return platformClient;
    }


    // 在header里添加了token的retrofit对象 baseUrlBaseTest请求
    public static Retrofit baseTestClient() {
        if (null != baseTestClient) {
            return baseTestClient;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(setUserCookie).addInterceptor(requestErrorInterceptor)
                .addInterceptor(logging).connectTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true)
                .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
        baseTestClient = new Retrofit.Builder().baseUrl(baseUrlBaseTest)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient).build();

        return baseTestClient;
    }

    public static Retrofit baseZhangClient() {
        if (null != baseZhangClient()) {
            return baseZhangClient;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(setUserCookie).addInterceptor(requestErrorInterceptor)
                .addInterceptor(logging).connectTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true)
                .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
        baseZhangClient = new Retrofit.Builder().baseUrl(baseUrlBaseZhang)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient).build();

        return baseZhangClient;
    }

    public static RequestBody createBody(Map<String, Object> params) {
        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; " + "charset=utf-8"),
                (new JSONObject(params)).toString());
        return body;
    }

    public static <T> RequestBody createBodyFromBean(T t) {
        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; " + "charset=utf-8"), new Gson().toJson(t));
        Log.d("REQUEST_BODY_STRING", body.toString());
        return body;
    }


}
