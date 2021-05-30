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

    @POST("users/signup/")
    @FormUrlEncoded
    Call<String> signUp(@Field("data") JSONObject data);

    @POST("users/get_computers/")
    @FormUrlEncoded
    Call<String> getComputers(@Field("data") String token);

    @POST("users/check_user_extra_data/")
    @FormUrlEncoded
    Call<String> checkUserExtraData(@Field("data") JSONObject data);

    @POST("users/set_user_extra_data/")
    @FormUrlEncoded
    Call<String> setUserExtraData(@Field("data") JSONObject data);

    @POST("users/add_cybercafe_to_user/")
    @FormUrlEncoded
    Call<String> addCybercafeToUser(@Field("data") JSONObject data);
}
