package com.zhiziyun.dmptest.bot.mode.acount;

import com.zhiziyun.dmptest.bot.mode.originalityApi;
import com.zhiziyun.dmptest.bot.network.BaseUseCase;

import rx.Observable;

/**
 * Created by zhiziyun on 2018/9/14.
 */

public class AcountTypeCase extends BaseUseCase<originalityApi> {

    private int agentid;
    private String token;


    public AcountTypeCase(int agentid, String token) {
        this.agentid = agentid;
        this.token = token;

    }

    @Override
    protected Observable buildUseCaseObservable() {
        return baseZhangClient().accountSettleType(agentid, token);
    }
}
