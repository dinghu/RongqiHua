package com.fkh.support.engine.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.fkh.support.engine.SupportEngine;
import com.fkh.support.engine.retrofit.interceptor.HeaderInterceptor;
import com.fkh.support.engine.retrofit.interceptor.HttpLoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private String baseUrl;
    private Retrofit mRetrofit;
    private final long READ_TIMEOUT_MILLIS = 30;
    private final long WRITE_TIMEOUT_MILLIS = 30;
    private final long CACHESIZE = 10 * 1024 * 1024;

    private static RetrofitHelper instance;

    public static synchronized RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static <T> void sendRequest(Call<T> call, final ResponseListener<T> responseListener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                try {
                    if (response.isSuccessful()) {
                        responseListener.onSuccess(response.body());
                    } else {
                        responseListener.onFail("" + response.code(), response.errorBody().string());
                    }
                } catch (Exception e) {
                    responseListener.onFail("" + response.code(), e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                responseListener.onFail("-1", t.getMessage());
            }
        });
    }

    public void init(Context context, String baseUrl) {
        mRetrofit = getRetrofit(context, baseUrl);
    }

    /**
     * 设置Header
     *
     * @return
     */
    private Interceptor getHeaderInterceptor() {
        return new HeaderInterceptor();
    }

    public Retrofit getRetrofit(Context context, String baseUrl) {
        File httpCacheDirectory = new File(context.getExternalCacheDir(), "responses");
        if (httpCacheDirectory != null && !httpCacheDirectory.exists()) {
            httpCacheDirectory.mkdirs();
        }
        Cache cache = null;
        if (httpCacheDirectory.exists()) {
            cache = new Cache(httpCacheDirectory, CACHESIZE);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(READ_TIMEOUT_MILLIS, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT_MILLIS, TimeUnit.SECONDS);
        builder.cache(cache);
        builder.addInterceptor(getHeaderInterceptor());
        builder.addInterceptor(getHttpLoggingInterceptor());
        OkHttpClient client = builder.build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器（gson）
                .client(client)
                .build();

        return mRetrofit;
    }


    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (!TextUtils.isEmpty(message)) {
                    Log.i("http", message);
                }
            }
        });
        httpLoggingInterceptor.setLogRequetBody(true);
        httpLoggingInterceptor.setLogResponseBody(true);
        return httpLoggingInterceptor;
    }

//    private String getSign(String appSecret,String nonce,String timestamp) {
//        StringBuilder toSign = new StringBuilder(appSecret).append(nonce).append(timestamp);
//        return SHA1.hexSHA1(toSign.toString());
//    }
//
//    /**
//     * 设置Header
//     *
//     * @return
//     */
//    private Interceptor getRongYunHeaderInterceptor() {
//        return new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
//                Request original = chain.request();
//                String appSecret = "gSX2xXdp760k"; // 开发者平台分配的 App Secret。
//                String nonce = String.valueOf(Math.random() * 1000000);
//                String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
//                // 根据body和secret计算sign，注意，结果一定要转小写，这个官网文档上没提，但大写就通不过。
//                String sign = getSign(appSecret,nonce,timestamp);
//
//                Request.Builder requestBuilder = original.newBuilder()
//                        .addHeader("App-Key", "sfci50a7s39fi")
//                        .addHeader("Nonce", nonce)
//                        .addHeader("Timestamp", timestamp)
//                        .addHeader("Signature", sign);
//
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        };
//
//    }

}
