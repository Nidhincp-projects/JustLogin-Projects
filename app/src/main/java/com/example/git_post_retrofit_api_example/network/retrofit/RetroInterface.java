package com.example.git_post_retrofit_api_example.network.retrofit;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetroInterface {
    @POST("login")
    Call<ResponseBody> LoginTrack(@Body JsonObject main);


    @GET("kiosk")
    Call<ResponseBody> sendForgetPasswordRequest(@Header("AccessToken") String requestTypeLogin);
}
