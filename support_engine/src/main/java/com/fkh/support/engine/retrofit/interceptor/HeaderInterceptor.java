package com.fkh.support.engine.retrofit.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();
//                if (!TextUtils.isEmpty(LoginManager.getToken())) {
//                    requestBuilder.addHeader("token", LoginManager.getToken());
//                }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
