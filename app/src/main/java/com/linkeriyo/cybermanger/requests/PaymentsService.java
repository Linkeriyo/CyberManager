package com.linkeriyo.cybermanger.requests;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PaymentsService {

    @POST("payments/get_methods_by_user/")
    @FormUrlEncoded
    Call<String> getMethodsByUser(@Field(Tags.TOKEN) String token);

    @POST("payments/register_new_card/")
    @FormUrlEncoded
    Call<String> registerNewCard(@Field(Tags.DATA) JSONObject data);

    @POST("payments/remove_card/")
    @FormUrlEncoded
    Call<String> removeCard(@Field(Tags.DATA) JSONObject data);

    @POST("payments/get_cybergold/")
    @FormUrlEncoded
    Call<String> getCybergold(@Field(Tags.DATA) JSONObject data);
}
