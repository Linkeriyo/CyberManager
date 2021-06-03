package com.linkeriyo.cybermanger.requests;

import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BusinessService {

    @POST("businesses/check_business/")
    @FormUrlEncoded
    Call<String> checkBusiness(@Field(Tags.DATA) JSONObject data);

    @POST("businesses/get_businesses_by_user/")
    @FormUrlEncoded
    Call<String> getBusinessesByUser(@Field(Tags.TOKEN) String token);

    @POST("businesses/get_posts_by_business_id/")
    @FormUrlEncoded
    Call<String> getPostsByBusinessId(@Field(Tags.DATA) JSONObject data);
}
