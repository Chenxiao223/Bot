package com.zhiziyun.dmptest.bot.mode.wifi.store;

import com.zhiziyun.dmptest.bot.mode.originalityApi;
import com.zhiziyun.dmptest.bot.network.BaseUseCase;

import rx.Observable;

/**
 * Created by zhiziyun on 2018/8/30.
 */

public class Wificase extends BaseUseCase<originalityApi> {
    private int agentid;
    private String token;
    private String json;

    public Wificase(int agentid, String token, String json) {
        this.agentid = agentid;
        this.token = token;
        this.json = json;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return baseZhangClient().wifiSegmentInfo(agentid, token, json);
    }
}

