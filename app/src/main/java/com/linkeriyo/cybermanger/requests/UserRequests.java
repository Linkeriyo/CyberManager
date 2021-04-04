package com.linkeriyo.cybermanger.requests;

import android.app.Activity;
import android.widget.Toast;

import com.linkeriyo.cybermanger.activities.LoginActivity;
import com.linkeriyo.cybermanger.activities.MainActivity;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRequests {

    public static void login(final LoginActivity loginActivity, final String username, final String password) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .login(JSONTemplates.createLoginJSON(username, password));
        System.out.println(call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                try {
                    System.out.println("login response");
                    System.out.println(response.body());

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        Preferences.setUsername(jsonResponse.getString(Tags.USERNAME));
                        Preferences.setEmail(jsonResponse.getString(Tags.EMAIL));
                        Preferences.setToken(jsonResponse.getString(Tags.TOKEN));

                        // Succesfully logged in.
                        loginActivity.finish();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Toast.makeText(loginActivity, "login failure", Toast.LENGTH_SHORT).show();
                System.out.println("login failure");

            }
        });
    }

    public static void logout(final MainActivity mainActivity, final String token) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .logout(token);
        System.out.println(call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                try {
                    System.out.println("logout response");
                    System.out.println(response.body());

                    JSONObject jsonResponse = new JSONObject(response.body());

                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        Preferences.removeToken();
                        mainActivity.startLoginActivity();
                    } else {
                        Toast.makeText(mainActivity, "couldn't logout", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Toast.makeText(mainActivity, "logout failure", Toast.LENGTH_SHORT).show();
                System.out.println("logout failure");
            }
        });
    }

    public static void signup(final LoginActivity loginActivity, final String username, final String email, final String password) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .signUp(JSONTemplates.createSignUpJSON(username, email, password));
        System.out.println(call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                try {
                    System.out.println("signup response");
                    System.out.println(response.body());

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        Preferences.setUsername(jsonResponse.getString(Tags.USERNAME));
                        Preferences.setEmail(jsonResponse.getString(Tags.EMAIL));
                        Preferences.setToken(jsonResponse.getString(Tags.TOKEN));

                        // Succesfully logged in.
                        loginActivity.finish();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Toast.makeText(loginActivity, "login failure", Toast.LENGTH_SHORT).show();
                System.out.println("login failure");

            }
        });
    }
}
