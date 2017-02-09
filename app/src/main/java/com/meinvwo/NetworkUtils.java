package com.meinvwo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述:
 * <p>
 * Created by ruanzb on 2017/2/8.
 */

public class NetworkUtils {

    private static GankApi sGankApi;
    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(8000, TimeUnit.MILLISECONDS)
            .readTimeout(8000, TimeUnit.MILLISECONDS)
            .addInterceptor(new LoggerInterceptor(LoggerInterceptor.Level.HEADERS))
            .build();

    public static GankApi getGankNetApi() {
        if (sGankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://gank.io/api/")
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            sGankApi = retrofit.create(GankApi.class);
        }
        return sGankApi;
    }
}
