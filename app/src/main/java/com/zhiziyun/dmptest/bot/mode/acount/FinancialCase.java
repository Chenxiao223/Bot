package com.zhiziyun.dmptest.bot.mode.acount;

import com.zhiziyun.dmptest.bot.mode.originalityApi;
import com.zhiziyun.dmptest.bot.network.BaseUseCase;

import rx.Observable;

/**
 * Created by zhiziyun on 2018/9/14.
 */

public class FinancialCase extends BaseUseCase<originalityApi> {
    private int agentid;
    private String token;
    private String json;

    public FinancialCase(int agentid, String token, String json) {
        this.agentid = agentid;
        this.token = token;
        this.json = json;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return baseZhangClient().financialStatem(agentid, token, json);
    }
}
