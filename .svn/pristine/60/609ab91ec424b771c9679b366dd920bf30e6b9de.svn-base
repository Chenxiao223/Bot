package com.zhiziyun.dmptest.bot.mode;



import com.zhiziyun.dmptest.bot.network.response.Response;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import static com.zhiziyun.dmptest.bot.network.NetWorkApi.originality;

/**
 * Created by zhiziyun on 2018/7/19.
 */

public interface originalityApi {
    @POST(originality)
    Observable<Response> originality(@Query("agentid") int agentid,
                                     @Query("token") String token);

}
