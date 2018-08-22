package com.zhiziyun.dmptest.bot.mode.originality;


import com.zhiziyun.dmptest.bot.mode.originalityApi;
import com.zhiziyun.dmptest.bot.network.BaseUseCase;

import org.json.JSONObject;

import rx.Observable;

/**
 * Created by zhiziyun on 2018/7/19.
 */

public class Uploadmaterialcase extends BaseUseCase<originalityApi> {
    private int agentid;
    private String token;
    private JSONObject json;

    public Uploadmaterialcase(int agentid, String token, JSONObject templatePackageId) {
        this.agentid = agentid;
        this.token = token;
        this.json = templatePackageId;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return baseZhangClient().getCreativeTemplatePackageDetail(agentid, token, json);
    }
}
