package com.rongqi.hua.rongqihua.service;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by dinghu on 2019/8/16.
 */
public interface CommonService {
    @POST//resp.string()
    Observable<ResponseBody> postFullPath(@Url String url, @FieldMap Map<String, Object> map);
}
