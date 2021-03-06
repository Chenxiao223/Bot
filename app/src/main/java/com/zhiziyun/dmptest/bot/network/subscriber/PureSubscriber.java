package com.zhiziyun.dmptest.bot.network.subscriber;

/**
 * Created by Chailin on 2017/7/4.
 */

public abstract class PureSubscriber<T> extends rx.Subscriber<T> {
    @Override
    public void onCompleted() {
        if (isUnsubscribed()) {
            unsubscribe();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        onFailure(throwable);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onFailure(Throwable throwable);

    public abstract void onSuccess(T data);

}
