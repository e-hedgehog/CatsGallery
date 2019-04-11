package com.ehedgehog.android.catsgallery.network;

import android.support.annotation.NonNull;

import com.ehedgehog.android.catsgallery.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("x-api-key", BuildConfig.API_KEY)
                .build();
        return chain.proceed(newRequest);
    }
}
