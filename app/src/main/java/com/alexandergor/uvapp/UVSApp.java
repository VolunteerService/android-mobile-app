package com.alexandergor.uvapp;

import android.app.Application;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23;

import com.facebook.AccessToken;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UVSApp extends Application {

    private static UVSApiClient uvsApiClient;
    private static Retrofit retrofit;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static String authToken;

    public static UVSApiClient getUvsApiClient(String newToken) {

        if(!Objects.equals(newToken, authToken)) {
            return buildApiClient(newToken);
        }

        return uvsApiClient;
    }

    public static UVSApiClient buildApiClient(final String authToken) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://dev.vscard.org/api/v1.0/")
                .addConverterFactory(GsonConverterFactory.create());

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder builder = original.newBuilder()
                        .header("Authorization", "Bearer " + authToken);

                Request request = builder.build();
                return chain.proceed(request);
            }
        });

        builder.client(httpClient.build());

        retrofit = builder.build();

        return retrofit.create(UVSApiClient.class);
    }
}
