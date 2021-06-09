package com.linkeriyo.cybermanger.requests;

import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.activities.ExtraDataActivity;
import com.linkeriyo.cybermanger.activities.LoginActivity;
import com.linkeriyo.cybermanger.activities.MainActivity;
import com.linkeriyo.cybermanger.activities.SelectCafeActivity;
import com.linkeriyo.cybermanger.dialogs.QRScannedDialog;
import com.linkeriyo.cybermanger.fragments.login.SignUpFragment;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRequests {

    private static final String TAG = "UserRequests";

    public static void login(final LoginActivity loginActivity, final String username, final String password) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .login(JSONTemplates.createLoginJSON(username, password));
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                try {
                    Log.v(TAG, "login response");
                    Log.v(TAG, response.body() + "");

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
                Log.v(TAG, "login failure");

            }
        });
    }

    public static void logout(final MainActivity mainActivity, final String token) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .logout(token);
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                try {
                    Log.v(TAG, "logout response");
                    Log.v(TAG, response.body());

                    JSONObject jsonResponse = new JSONObject(response.body());

                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        Preferences.notifyLoggedOut();
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
                Log.v(TAG, "logout failure");
            }
        });
    }

    public static void signup(final SignUpFragment fragment, final String username, final String email, final String password) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .signUp(JSONTemplates.createSignUpJSON(username, email, password));
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                try {
                    Log.v(TAG, "signup response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        Toast.makeText(fragment.getContext(),
                                fragment.getString(R.string.account_created),
                                Toast.LENGTH_SHORT
                        ).show();
                        fragment.navigateToLoginFragment();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Toast.makeText(fragment.getContext(), "signup failure", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "signup failure");
                t.printStackTrace();
            }
        });
    }

    public static void checkUserExtraData(final MainActivity mainActivity, final String token, final String username) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .checkUserExtraData(JSONTemplates.createCheckExtraDataJSON(token, username));
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "check_user_extra_data response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        if (jsonResponse.getBoolean(Tags.HAS_EXTRA_DATA)) {
                            mainActivity.checkSelectedCafe();
                            Log.v(TAG, jsonResponse.getString(Tags.USER_EXTRA_DATA));
                        } else {
                            mainActivity.startExtraDataActivity();
                        }
                    } else if (jsonResponse.getString(Tags.MESSAGE).contains("token is null")) {
                        mainActivity.startLoginActivity();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void setUserExtraData(final ExtraDataActivity extraDataActivity,
                                        final String token, final String username,
                                        final String name, final String surname,
                                        final String phono, final String address,
                                        final String city, final String province,
                                        final String zipCode
    ) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .setUserExtraData(JSONTemplates.createSetExtraDataJSON(
                        token, username, name, surname, phono, address, city, province, zipCode
                ));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "set_user_extra_data response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        Toast.makeText(extraDataActivity,
                                extraDataActivity.getString(R.string.data_set),
                                Toast.LENGTH_SHORT
                        ).show();
                        extraDataActivity.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void addCybercafeToUser(final Dialog dialog, final String token, final CyberCafe cyberCafe) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .addCybercafeToUser(JSONTemplates.createAddCybercafeToUserJSON(token, cyberCafe));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "add_cybercafe_to_user response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        if (jsonResponse.getString(Tags.MESSAGE).contains("already added")) {
                            Toast.makeText(dialog.getContext(), R.string.already_added_cafe, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(dialog.getContext(), R.string.cafe_added, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void removeCybercafeFromUser(final SelectCafeActivity activity, final String token, final CyberCafe cyberCafe) {
        Call<String> call = RetrofitClient.getClient()
                .create(UserService.class)
                .removeCybercafeFromUser(JSONTemplates.createAddCybercafeToUserJSON(token, cyberCafe));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "remove_cybercafe_from_user response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        BusinessRequests.getBusinessesByUser(activity, Preferences.getToken());
                        Toast.makeText(activity, R.string.cafe_removed, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
