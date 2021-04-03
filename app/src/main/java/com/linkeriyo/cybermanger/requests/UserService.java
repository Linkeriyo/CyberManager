package com.linkeriyo.cybermanger.requests;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @POST("users/login/")
    @FormUrlEncoded
    Call<String> login(@Field("data") JSONObject data);

    @POST("users/logout/")
    @FormUrlEncoded
    Call<String> logout(@Field("token") String token);
}
