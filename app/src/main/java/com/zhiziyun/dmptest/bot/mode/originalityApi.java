package com.zhiziyun.dmptest.bot.mode;


import com.zhiziyun.dmptest.bot.mode.acount.request.BFinancialResult;
import com.zhiziyun.dmptest.bot.mode.acount.request.BRechargeDetailResult;
import com.zhiziyun.dmptest.bot.mode.acount.request.BRechargeResult;
import com.zhiziyun.dmptest.bot.mode.originality.friend.BMoneyDetailResult;
import com.zhiziyun.dmptest.bot.mode.originality.friend.BTradeDetailResult;
import com.zhiziyun.dmptest.bot.mode.originality.mateialdetail.BMaterialResult;
import com.zhiziyun.dmptest.bot.mode.originality.mateialdetail.BianjiResult;
import com.zhiziyun.dmptest.bot.mode.originality.origincreat.BOriginalityResult;
import com.zhiziyun.dmptest.bot.mode.originality.uploadmaterial.BCreateDetailResult;
import com.zhiziyun.dmptest.bot.mode.wifi.store.BStoreResult;
import com.zhiziyun.dmptest.bot.mode.wifi.store.BWifiResult;
import com.zhiziyun.dmptest.bot.network.response.Response;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

import static com.zhiziyun.dmptest.bot.network.NetWorkApi.accountDetail;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.accountSettleType;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.allSettleType;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.createCreativeActivity;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.editCreativeActivity;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.financialStatem;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.getCreativeActivityDetail;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.getCreativeTemplatePackageDetail;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.getCreativeTemplatePackageRealyPreview;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.originality;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.sendWithMac;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.sendWithOneMac;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.sendWithSegment;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.storeList;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.wechatAccount;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.wechatAccountDetail;
import static com.zhiziyun.dmptest.bot.network.NetWorkApi.wifiSegmentInfo;

/**
 * Created by zhiziyun on 2018/7/19.
 */

public interface originalityApi {
    @POST(originality)
    Observable<BOriginalityResult> originality(@Query("agentid") int agentid,
                                               @Query("token") String token,
                                               @Query("json") String json);

    @POST(getCreativeTemplatePackageDetail)
    Observable<BCreateDetailResult> getCreativeTemplatePackageDetail(@Query("agentid") int agentid,
                                                                     @Query("token") String token,
                                                                     @Query("json") JSONObject json);

    @Multipart
    @POST(getCreativeTemplatePackageRealyPreview)
    Observable<String> getCreativeTemplatePackageRealyPreview(@Query("agentid") int agentid,
                                                              @Query("token") String token,
                                                              @Query("json") JSONObject jsonObject,
                                                              @Part() List<MultipartBody.Part> parts);

    @POST(createCreativeActivity)
    Observable<BCreateDetailResult> createCreativeActivity(@Query("agentid") int agentid,
                                                           @Query("token") String token,
                                                           @Query("json") String json);

    @POST(wechatAccount)
    Observable<BMoneyDetailResult> wechatAccount(@Query("agentid") int agentid,
                                                 @Query("token") String token,
                                                 @Query("json") String json);

    @POST(wechatAccountDetail)
    Observable<BTradeDetailResult> wechatAccountDetail(@Query("agentid") int agentid,
                                                       @Query("token") String token,
                                                       @Query("json") String json);

    @POST(getCreativeActivityDetail)
    Observable<String> getCreativeActivityDetail(@Query("agentid") int agentid,
                                                 @Query("token") String token,
                                                 @Query("json") String json);

    @POST(editCreativeActivity)
    Observable<Response> editCreativeActivity(@Query("agentid") int agentid,
                                              @Query("token") String token,
                                              @Query("json") String json);

    @POST(storeList)
    Observable<BStoreResult> storeList(@Query("agentid") int agentid,
                                       @Query("token") String token,
                                       @Query("json") String json);

    @POST(wifiSegmentInfo)
    Observable<BWifiResult> wifiSegmentInfo(@Query("agentid") int agentid,
                                            @Query("token") String token,
                                            @Query("json") String json);

    @POST(sendWithOneMac)
    Observable<Response> sendWithOneMac(@Query("agentid") int agentid,
                                        @Query("token") String token,
                                        @Query("json") String json);

    @POST(sendWithSegment)
    Observable<Response> sendWithSegment(@Query("agentid") int agentid,
                                         @Query("token") String token,
                                         @Query("json") String json);

    @POST(sendWithMac)
    Observable<Response> sendWithMac(@Query("agentid") int agentid,
                                     @Query("token") String token,
                                     @Query("json") String json);

    @POST(accountSettleType)
    Observable<BRechargeResult> accountSettleType(@Query("agentid") int agentid,
                                                  @Query("token") String token
    );

    @POST(financialStatem)
    Observable<BFinancialResult> financialStatem(@Query("agentid") int agentid,
                                                 @Query("token") String token,
                                                 @Query("json") String json
    );

    @POST(accountDetail)
    Observable<BRechargeDetailResult> accountDetail(@Query("agentid") int agentid,
                                                    @Query("token") String token,
                                                    @Query("json") String json
    );

    @POST(allSettleType)
    Observable<String> allSettleType(@Query("agentid") int agentid,
                                                  @Query("token") String token
    );
}
