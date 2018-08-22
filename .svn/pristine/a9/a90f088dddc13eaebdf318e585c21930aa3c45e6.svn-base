package com.zhiziyun.dmptest.bot.mode.originality;


import com.zhiziyun.dmptest.bot.mode.originalityApi;
import com.zhiziyun.dmptest.bot.network.BaseUseCase;

import org.json.JSONObject;

import java.util.List;

import rx.Observable;

/**
 * Created by zhiziyun on 2018/7/26.
 */

public class AdvertCreatCase extends BaseUseCase<originalityApi> {
    private int agentid;
    private String token;
    private String json;

    public AdvertCreatCase(int agentid, String token, String json) {
        this.agentid = agentid;
        this.token = token;
        this.json = json;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return baseZhangClient().createCreativeActivity(agentid, token, json);
    }
}
