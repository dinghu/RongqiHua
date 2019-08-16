package com.rongqi.hua.rongqihua.service;

import com.rongqi.hua.rongqihua.entity.req.AccountReq;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dinghu on 2019/8/16.
 */
public interface ApiService {
    @POST("admin/insert")
    Call<BaseResp> adminInsert(@Body AccountReq accountReq);

    @POST("admin/selectByAdmin")
    Call<BaseResp> login(@Body AccountReq accountReq);
}
