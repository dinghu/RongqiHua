package com.rongqi.hua.rongqihua.service;

import com.rongqi.hua.rongqihua.entity.req.AccountReq;
import com.rongqi.hua.rongqihua.entity.req.RegistInfoReq;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dinghu on 2019/8/16.
 */
public interface ApiService {
    @POST("admin/insert")
    Call<BaseResp> adminInsert(@Body AccountReq accountReq);

    @POST("admin/selectByAdmin")
    Call<BaseResp> login(@Body AccountReq accountReq);

    //1.新增合伙人
    @POST("teacher/insert")
    Call<BaseResp> teacherInsert(@Query("invCode") String invCode, @Body RegistInfoReq registInfoReq);

    @POST("rela/orderInfo")
    Call<ResponseBody> relaOrderInfo(@Query("num") Integer num);
}
