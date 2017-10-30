package com.alexandergor.uvapp;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UVSApiClient {

    @GET("profile")
    Call<modelProfile> getProfile();

    @GET("missions")
    Call<List<modelMission>> listMissions();

    @POST("profile/fcm")
    @FormUrlEncoded
    Call<ResponseBody> registerFCM(@Field("fcm_id") String fcm_id);

    @POST("missions/{id}/apply")
    Call<modelMission> applyMission(@Path("id") String id);

    @POST("missions/{id}/refuse")
    @FormUrlEncoded
    Call<modelMission> refuseMission(@Path("id") String id, @Field("comment") String comment);

}
