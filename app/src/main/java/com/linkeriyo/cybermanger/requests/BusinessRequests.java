package com.linkeriyo.cybermanger.requests;

import android.util.Log;
import android.widget.Toast;

import com.linkeriyo.cybermanger.activities.SelectCafeActivity;
import com.linkeriyo.cybermanger.dialogs.QRScannedDialog;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessRequests {

    private static final String TAG = "BusinessRequests";

    public static void checkBusiness(QRScannedDialog dialog, final String businessId) {
        Call<String> call = RetrofitClient.getClient()
                .create(BusinessService.class)
                .checkBusiness(businessId);
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "checkBusiness response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        CyberCafe cyberCafe = new CyberCafe(jsonResponse.getJSONObject(Tags.BUSINESS));
                        dialog.showCyberCafeIfMatched(cyberCafe);
                    } else {
                        dialog.showCyberCafeIfMatched(null);
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    dialog.showCyberCafeIfMatched(null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "checkBusiness failure");
                Toast.makeText(
                        dialog.getContext(),
                        "An error occurred when handling the server's response.",
                        Toast.LENGTH_SHORT
                ).show();
                dialog.dismiss();
            }
        });
    }

    public static void getBusinessesByUser(SelectCafeActivity activity, final String token) {
        Call<String> call = RetrofitClient.getClient()
                .create(BusinessService.class)
                .getBusinessesByUser(token);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "getBusinessesByUser response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        ArrayList<CyberCafe> cafes = new ArrayList<>();

                        JSONArray jsonCafes = jsonResponse.getJSONArray(Tags.BUSINESS_LIST);
                        System.out.println(jsonCafes);

                        for (int i = 0; i < jsonCafes.length(); i++) {
                            cafes.add(new CyberCafe(jsonCafes.getJSONObject(i)));
                        }
                        System.out.println(cafes.get(0).toJSON());
                        activity.setCafes(cafes);
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "getBusinessesByUser failure");
            }
        });
    }
}
