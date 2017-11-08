package com.alexandergor.uvapp;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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

    public static modelProfile UserProfile;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .migration(new ModelsMigration())
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
        Realm realm = Realm.getDefaultInstance();


        Log.i("Realm", realm.getPath());
    }

    public static UVSApiClient getUvsApiClient(String newToken) {

        if(!Objects.equals(newToken, authToken)) {
            return buildApiClient(newToken);
        }

        return uvsApiClient;
    }

    public static UVSApiClient buildApiClient(final String authToken) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://dev.vscard.org/api/v1.0/")
                .addConverterFactory(GsonConverterFactory.create(gson));

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
