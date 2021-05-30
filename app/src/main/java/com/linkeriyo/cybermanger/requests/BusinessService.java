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
    Call<String> checkBusiness(@Field(Tags.BUSINESS_ID) String businessId);

    @POST("businesses/get_businesses_by_user/")
    @FormUrlEncoded
    Call<String> getBusinessesByUser(@Field(Tags.TOKEN) String token);
}
