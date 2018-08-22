package com.zhiziyun.dmptest.bot.mode.originality;

import com.zhiziyun.dmptest.bot.mode.originalityApi;
import com.zhiziyun.dmptest.bot.network.BaseUseCase;

import rx.Observable;


/**
 * Created by zhiziyun on 2018/7/19.
 */

public class ChooseCretiveCase extends BaseUseCase<originalityApi> {
    private int agentid;
    private String token;
    private String activityType;


    public ChooseCretiveCase(int agentid, String token, String activityType) {
        this.agentid = agentid;
        this.token = token;
        this.activityType = activityType;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return baseZhangClient().originality(agentid, token, activityType);
    }
}
