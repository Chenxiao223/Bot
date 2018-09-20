package com.zhiziyun.dmptest.bot.mode.acount;

import com.zhiziyun.dmptest.bot.mode.originalityApi;
import com.zhiziyun.dmptest.bot.network.BaseUseCase;

import rx.Observable;

/**
 * Created by zhiziyun on 2018/9/14.
 */

public class AllTypeCase extends BaseUseCase<originalityApi> {

    private int agentid;
    private String token;


    public AllTypeCase(int agentid, String token) {
        this.agentid = agentid;
        this.token = token;

    }

    @Override
    protected Observable buildUseCaseObservable() {
        return baseTestClient().allSettleType(agentid, token);
    }
}
