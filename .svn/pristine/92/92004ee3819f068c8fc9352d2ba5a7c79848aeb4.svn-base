package com.zhiziyun.dmptest.bot.mode;

import com.zhiziyun.dmptest.bot.network.BaseUseCase;

import rx.Observable;


/**
 * Created by zhiziyun on 2018/7/19.
 */

public class ChooseCretiveCase extends BaseUseCase<originalityApi> {
    private  int agentid;
    private  String token;

    public ChooseCretiveCase(int agentid, String token) {
        this.agentid = agentid;
        this.token = token;
    }


    @Override
    protected Observable buildUseCaseObservable() {
        return baseZhangClient().originality(agentid,token);
    }
}
