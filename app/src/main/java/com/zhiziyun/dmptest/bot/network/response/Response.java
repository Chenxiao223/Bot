package com.zhiziyun.dmptest.bot.network.response;

/**
 * Created by Chailin on 2017/7/3.
 */

public class Response<T> {
    private int code;

    private T data;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSuccess() {
        if (code == 200) {
            return true;
        } else {
            return false;
        }
    }
}
