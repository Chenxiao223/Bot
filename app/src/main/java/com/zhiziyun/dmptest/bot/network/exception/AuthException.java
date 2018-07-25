package com.zhiziyun.dmptest.bot.network.exception;

/**
 * Created by Chailin on 2017/7/5.
 */
public class AuthException extends Exception {
    public AuthException() {
    }

    public AuthException(String detailMessage) {
        super(detailMessage);
    }

    public AuthException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AuthException(Throwable throwable) {
        super(throwable);
    }
}
