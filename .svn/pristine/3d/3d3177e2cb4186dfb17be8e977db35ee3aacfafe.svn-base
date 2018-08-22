package com.zhiziyun.dmptest.bot.mode.originality;

import com.zhiziyun.dmptest.bot.mode.originalityApi;
import com.zhiziyun.dmptest.bot.network.BaseUseCase;

import rx.Observable;

/**
 * Created by zhiziyun on 2018/7/31.
 */

public class TradeDetailCase extends BaseUseCase<originalityApi> {
    private int agentid;
    private String token;
    private String json;

    public TradeDetailCase(int agentid, String token, String json) {
        this.agentid = agentid;
        this.token = token;
        this.json = json;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return baseZhangClient().wechatAccountDetail(agentid, token, json);
    }
}
