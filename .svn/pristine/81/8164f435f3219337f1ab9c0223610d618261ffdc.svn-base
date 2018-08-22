package com.zhiziyun.dmptest.bot.mode.originality;



import com.zhiziyun.dmptest.bot.mode.originalityApi;
import com.zhiziyun.dmptest.bot.network.BaseUseCase;


import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by zhiziyun on 2018/7/23.
 */

public class UploadCase extends BaseUseCase<originalityApi> {
    private int agentid;
    private String token;
    private JSONObject json;
    private List<MultipartBody.Part> maps;

    public UploadCase(int agentid, String token, JSONObject json,List<MultipartBody.Part> maps) {
        this.agentid = agentid;
        this.token = token;
        this.json = json;
        this.maps = maps;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return baseTestClient().getCreativeTemplatePackageRealyPreview(agentid, token, json, maps);
    }
}
