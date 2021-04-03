package com.linkeriyo.cybermanger.requests;

import android.app.Activity;
import android.widget.Toast;

import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRequests {

    public static void login(final Activity activity, final String username, final String password) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .login(JSONTemplates.createLoginJSON(username, password));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    System.out.println(call.request().toString());
                    System.out.println("login response");
                    System.out.println(response.body());

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK_STRING)) {
                        Preferences.setUsername(jsonResponse.getString(Tags.USERNAME));
                        Preferences.setEmail(jsonResponse.getString(Tags.EMAIL));
                        Preferences.setToken(jsonResponse.getString(Tags.TOKEN));

                        // Succesfully logged in.
                        activity.finish();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(activity, "failure", Toast.LENGTH_SHORT).show();
                System.out.println("failure");
            }
        });
    }
}
