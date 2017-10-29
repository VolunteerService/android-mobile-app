package com.alexandergor.uvapp;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UVSApiClient {

    @GET("profile")
    Call<modelProfile> getProfile();

    @GET("missions")
    Call<List<modelMission>> listMissions();

    @POST("profile/fcm")
    @FormUrlEncoded
    Call<ResponseBody> registerFCM(@Field("fcm_id") String fcm_id);

}
