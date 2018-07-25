package com.zhiziyun.dmptest.bot.network.subscriber;


import com.zhiziyun.dmptest.bot.network.exception.ErrorParse;
import com.zhiziyun.dmptest.bot.network.response.Response;

/**
 * Created by Chailin on 2017/7/4.
 */

public abstract class ParseSubscriber<T> extends rx.Subscriber<Response<T>> {
    @Override
    public void onCompleted() {
        if (isUnsubscribed()) {
            unsubscribe();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        onFailure(ErrorParse.parse(throwable), null);
        onFailure(ErrorParse.parse(throwable));
    }

    @Override
    public void onNext(Response<T> t) {
        if (t.isSuccess()) {
            onSuccess(t.getData());
            onSuccess(t.getData(), t);
        } else {
//            使用服务器返回信息
//
//            使用自定义异常解析
            onFailure(ErrorParse.parsa(t.getCode()), t);
            onFailure(ErrorParse.parsa(t.getCode()));
        }
    }

    public void onFailure(Throwable throwable, Response response) {
    }

    public void onFailure(Throwable throwable) {
    }

    public abstract void onSuccess(T data);

    public void onSuccess(T data, Response response) {

    }

}
