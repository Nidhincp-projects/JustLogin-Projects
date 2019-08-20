package com.example.git_post_retrofit_api_example.network.retrofit;

import com.example.git_post_retrofit_api_example.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    public static Retrofit getClient(String URL) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES);
        setInterceptor(clientBuilder);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static void setInterceptor(OkHttpClient.Builder clientBuilder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(logging);  // <-- this is the important line!
    }
    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(120, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(120, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(120, TimeUnit.SECONDS);
        okHttpBuilder.addInterceptor(getInterceptor());
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addNetworkInterceptor(loggingInterceptor);
        }
        return okHttpBuilder.build();
    }

    private static Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                Request request = builder
                        .addHeader("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };
    }
}
