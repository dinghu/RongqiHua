package com.rongqi.hua.rongqihua.service;

import com.rongqi.hua.rongqihua.entity.req.AccountReq;
import com.rongqi.hua.rongqihua.entity.req.RegistInfoReq;
import com.rongqi.hua.rongqihua.entity.req.UserLoginReq;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;
import com.rongqi.hua.rongqihua.entity.resp.Child;
import com.rongqi.hua.rongqihua.entity.resp.DataResp;
import com.rongqi.hua.rongqihua.entity.resp.LoginResp;
import com.rongqi.hua.rongqihua.entity.resp.TUserInfo;

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
    Call<LoginResp> login(@Body AccountReq accountReq);

    //1.新增合伙人
    @POST("teacher/insert")
    Call<DataResp<TUserInfo>> teacherInsert(@Query("invCode") String invCode, @Body RegistInfoReq registInfoReq);

    @POST("rela/orderInfo")
    Call<ResponseBody> relaOrderInfo(@Query("num") Integer num);

    //我的合伙人
    @POST("teacher/selectByTid")
    Call<ResponseBody> mydevelopTeachers(@Query("uuId") String uuId);

    //我的业绩
    @POST("student/selectByTid")
    Call<ResponseBody> myYejipersons(@Query("uuId") String uuId);

    @POST("userlog/insert")
    Call<ResponseBody> userlogInsert(@Body UserLoginReq userLoginReq);

    @POST("userlog/updateByPrimaryKey")
    Call<BaseResp> userlogUpdateByPrimaryKey(@Body UserLoginReq userLoginReq);

    @POST("teacher/selectByName")
    Call<ResponseBody> searchDeveloperByName(@Query("name") String name);

    @POST("rela/insert")
    Call<BaseResp> relaInsert(@Query("nid") String nid, @Query("uuid") String uuid);


    @POST("teacher/selectByTid")
    Call<ResponseBody> getDevelpersByUuid(@Query("uuId") String uuId);

    //    当合伙人招入学生（或者工人、汽车销售等）时，需调用此接口。新增成功的前提条件是：1.招入学生（或者工人、汽车销售等）信息的人必须已经注册合伙人。
//            2. 学生（或者工人、汽车销售等）信息为从未被其他合伙人招入。此接口的信息，不需要全部填写，比如当点选的人员类型为工人就不需要填写专业与毕业院校。
//    typeId:人员类型，uuId:合伙人微信编号，name：学生（或者工人、汽车销售等）名字，nid:身份证号码，phone:电话号码，sId: 学生（或者工人、汽车销售等）编号，sex:性别，major:专业，graduate：毕业学校
    @POST("student/insert")
    Call<BaseResp> studentInsert(@Query("typeId") Integer typeId, @Query("uuId") String uuId, @Body Child child);
}
